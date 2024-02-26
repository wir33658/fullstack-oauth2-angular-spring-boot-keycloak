package com.example.demo.config;

import com.example.demo.jwt.CustomJwt;
import com.example.demo.jwt.CustomJwtConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.Jwt;

public class MyResourceServerConfigurer implements Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> {
    @Override
    public void customize(OAuth2ResourceServerConfigurer<HttpSecurity> httpSecurityOAuth2ResourceServerConfigurer) {
        httpSecurityOAuth2ResourceServerConfigurer.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(customJwtConverter()));
    }

    protected Converter<Jwt, CustomJwt> customJwtConverter() {
        return new CustomJwtConverter();
    }
}
