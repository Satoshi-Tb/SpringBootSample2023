package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig   {
	
	/**
	 * SpringSecurity6対応
	 * */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        	.formLogin(login -> login
		        .loginProcessingUrl("/login")
		        .loginPage("/login")
		        .defaultSuccessUrl("/user/list", true)
		        .failureUrl("/login?error")
		        .usernameParameter("userId")
		        .passwordParameter("password")
		        .permitAll())
		    .authorizeHttpRequests(authz -> authz
	    		.requestMatchers(new AntPathRequestMatcher("/webjars/**")).permitAll()
	    		.requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
	    		.requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
	    		.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
	    		.requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
	    		.requestMatchers(new AntPathRequestMatcher("/user/signup")).permitAll()
	    		.anyRequest().authenticated()
		    );
        
        //CSRF対策を無効に設定（一時的）
        //http.csrf().disable();
        
        return http.build();
	}

	
	
}
