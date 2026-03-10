package com.example.observability;

import java.io.IOException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class AccessMetricLoggingFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessMetricLoggingFilter.class);
    private static final Logger ACCESS_LOG = LoggerFactory.getLogger("ACCESS_JSON");
    private static final Pattern NUMERIC_SEGMENT = Pattern.compile("/\\d+(?=/|$)");
    private static final Pattern UUID_SEGMENT = Pattern.compile(
            "/[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}(?=/|$)");
    private static final Pattern EMAIL_SEGMENT = Pattern.compile("/[^/]+@[^/]+(?=/|$)");

    private final ObjectMapper objectMapper;
    private final String serviceName;

    public AccessMetricLoggingFilter(
            ObjectMapper objectMapper,
            @Value("${spring.application.name:spring-boot-sample-2023}") String serviceName) {
        this.objectMapper = objectMapper;
        this.serviceName = serviceName;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = getRequestPath(request);
        // PoC では API アクセスだけを観測対象にする。
        return !path.equals("/api") && !path.startsWith("/api/");
    }

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return true;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return true;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // wall clock は event.start/end 用、monotonic clock は duration 計測用に分ける。
        Instant start = Instant.now();
        long startNanos = System.nanoTime();
        Throwable failure = null;

        try {
            filterChain.doFilter(request, response);
        } catch (Throwable ex) {
            failure = ex;
            rethrow(ex);
        } finally {
            // 正常系・例外系を問わず 1 リクエスト 1 イベントを残す。
            Instant end = Instant.now();
            long durationNanos = System.nanoTime() - startNanos;
            writeAccessLog(request, response, start, end, durationNanos, failure);
        }
    }

    private void writeAccessLog(
            HttpServletRequest request,
            HttpServletResponse response,
            Instant start,
            Instant end,
            long durationNanos,
            Throwable failure) {
        // Elastic Discover / Lens でそのまま扱いやすい ECS 寄りの JSON を組み立てる。
        Map<String, Object> event = new LinkedHashMap<>();
        event.put("@timestamp", end.toString());
        event.put("service", Map.of("name", serviceName));
        event.put("event", createEvent(start, end, durationNanos));
        event.put("http", createHttp(request, response, failure));
        event.put("url", Map.of("path", getRequestPath(request)));
        event.put("user", Map.of("id", resolveUserId()));
        event.put("labels", createLabels(request, durationNanos));

        try {
            ACCESS_LOG.info(objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException ex) {
            LOGGER.error("Failed to serialize access log", ex);
        }
    }

    private Map<String, Object> createEvent(Instant start, Instant end, long durationNanos) {
        Map<String, Object> event = new LinkedHashMap<>();
        event.put("dataset", serviceName + ".access");
        event.put("start", start.toString());
        event.put("end", end.toString());
        event.put("duration", durationNanos);
        return event;
    }

    private Map<String, Object> createHttp(HttpServletRequest request, HttpServletResponse response, Throwable failure) {
        Map<String, Object> http = new LinkedHashMap<>();
        http.put("request", Map.of("method", request.getMethod()));
        http.put("response", Map.of("status_code", resolveStatusCode(response, failure)));
        return http;
    }

    private Map<String, Object> createLabels(HttpServletRequest request, long durationNanos) {
        Map<String, Object> labels = new LinkedHashMap<>();
        labels.put("endpoint", resolveEndpoint(request));
        labels.put("duration_ms", durationNanos / 1_000_000L);
        return labels;
    }

    private int resolveStatusCode(HttpServletResponse response, Throwable failure) {
        int status = response.getStatus();
        // 例外送出後にレスポンスへ明示設定されていない場合でも 5xx として記録する。
        if (failure != null && status < 400) {
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        return status;
    }

    private String resolveEndpoint(HttpServletRequest request) {
        Object bestMatchingPattern = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        // Spring MVC が解決したルートパターンを優先すると、集計軸が実装上の URL 定義と揃う。
        if (bestMatchingPattern instanceof String pattern && !pattern.isBlank()) {
            return pattern;
        }

        // フォールバックでは可変セグメントを潰して集計粒度を荒らさないようにする。
        String path = getRequestPath(request);
        path = UUID_SEGMENT.matcher(path).replaceAll("/{id}");
        path = NUMERIC_SEGMENT.matcher(path).replaceAll("/{id}");
        path = EMAIL_SEGMENT.matcher(path).replaceAll("/{id}");
        return path;
    }

    private String resolveUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return "anonymous";
        }
        return authentication.getName();
    }

    private String getRequestPath(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        if (contextPath != null && !contextPath.isEmpty() && uri.startsWith(contextPath)) {
            return uri.substring(contextPath.length());
        }
        return uri;
    }

    private static void rethrow(Throwable ex) throws IOException, ServletException {
        if (ex instanceof IOException ioException) {
            throw ioException;
        }
        if (ex instanceof ServletException servletException) {
            throw servletException;
        }
        if (ex instanceof RuntimeException runtimeException) {
            throw runtimeException;
        }
        if (ex instanceof Error error) {
            throw error;
        }
        throw new ServletException(ex);
    }
}
