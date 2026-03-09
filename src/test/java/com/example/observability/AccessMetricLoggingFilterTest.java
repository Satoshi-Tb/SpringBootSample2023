package com.example.observability;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("AccessMetricLoggingFilter テスト")
class AccessMetricLoggingFilterTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("/api/** へのリクエストで構造化アクセスログが出力されること")
    void shouldOutputStructuredAccessLogForApiRequest(CapturedOutput output) throws Exception {
        var response = testRestTemplate.exchange("/api/department/all", HttpMethod.GET, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JsonNode accessLog = findAccessLog(output, "/api/department/all");
        assertThat(accessLog).isNotNull();
        assertThat(accessLog.path("service").path("name").asText()).isEqualTo("spring-boot-sample-2023");
        assertThat(accessLog.path("event").path("dataset").asText()).isEqualTo("spring-boot-sample-2023.access");
        assertThat(accessLog.path("http").path("request").path("method").asText()).isEqualTo("GET");
        assertThat(accessLog.path("http").path("response").path("status_code").asInt()).isEqualTo(200);
        assertThat(accessLog.path("labels").path("endpoint").asText()).isEqualTo("/api/department/all");
        assertThat(accessLog.path("labels").path("duration_ms").isNumber()).isTrue();
        assertThat(accessLog.path("user").path("id").asText()).isEqualTo("anonymous");
    }

    @Test
    @DisplayName("画面系 URL ではアクセスログを出力しないこと")
    void shouldNotOutputStructuredAccessLogForScreenRequest(CapturedOutput output) {
        var response = testRestTemplate.exchange("/login", HttpMethod.GET, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(findAccessLog(output, "/login")).isNull();
    }

    private JsonNode findAccessLog(CapturedOutput output, String path) {
        return Arrays.stream(output.getOut().split("\\R"))
                .map(this::readJson)
                .filter(node -> node != null)
                .filter(node -> "spring-boot-sample-2023.access".equals(node.path("event").path("dataset").asText()))
                .filter(node -> path.equals(node.path("url").path("path").asText()))
                .findFirst()
                .orElse(null);
    }

    private JsonNode readJson(String line) {
        try {
            return objectMapper.readTree(line);
        } catch (Exception ex) {
            return null;
        }
    }
}
