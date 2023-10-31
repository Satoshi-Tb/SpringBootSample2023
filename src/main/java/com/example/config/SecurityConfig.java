package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
	    		.requestMatchers(new AntPathRequestMatcher("/api/**")).permitAll()  // いったん無制限
	    		.requestMatchers(new AntPathRequestMatcher("/admin")).hasAuthority("ROLE_ADMIN") // 権限制御
	    		.anyRequest().authenticated()) // それ以外は直リンクNG
		    .logout(logout ->logout
	    		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	    		.logoutUrl("/logout")
	            .logoutSuccessUrl("/login?logout")
	        );
        
        //CSRF対策を無効に設定（一時的）
        //http.csrf().disable();
        
        return http.build();
	}
	
	@Bean
	protected PasswordEncoder passwordEncoder() {
		//パスワードの暗号化
		return new BCryptPasswordEncoder();
	}
	
	
	/**
	 * インメモリ認証
	 * */
	@Bean
	public InMemoryUserDetailsManager userDetailsService(){
		
	    UserDetails user = User.builder()
	        .username("user")
	        .password(passwordEncoder().encode("password"))
	        .authorities("ROLE_GENERAL")
	        .build();
	    
	    UserDetails admin = User.builder()
	        .username("admin")
	        .password(passwordEncoder().encode("password"))
	        .authorities("ROLE_ADMIN")
	        .build();
	    
	    return new InMemoryUserDetailsManager(user, admin);
	}
	
	
}
