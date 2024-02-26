package com.example.demo.config;

import com.example.demo.jwt.CustomJwt;
import com.example.demo.jwt.CustomJwtConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity // Add this annotation to an @Configuration class to have the Spring Security configuration defined in any WebSecurityConfigurer or more likely by exposing a SecurityFilterChain bean
@EnableMethodSecurity
public class SecurityConfig {

    // This is called ones at startup.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Every http request will go through this first
        http.cors(Customizer.withDefaults())
            .authorizeHttpRequests(matcherRegistry -> 
                matcherRegistry
                    .requestMatchers("/test")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            )
            .oauth2ResourceServer(buildOASC());
//            .oauth2ResourceServer(resourceServerConfigurer ->
//                resourceServerConfigurer.jwt(jwtConfigurer ->
//                    jwtConfigurer.jwtAuthenticationConverter(customJwtConverter()
//                )
//            ));
        return http.build();
    }

//    @Bean
    public Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> buildOASC() {
        return new MyResourceServerConfigurer();
    }
    
    // This is called ones at startup. (Looks like its called during above "requestMatchers(..)" setup)
    // @Bean
//    public Converter<Jwt, CustomJwt> customJwtConverter() {
//        return new CustomJwtConverter();
//    }
    
}
