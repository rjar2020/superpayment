package com.superfintech.superpayment.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Objects;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private Environment env;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(withDefaults())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                );

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        User.UserBuilder userBuilder = User.builder().passwordEncoder(encoder::encode);

        UserDetails user = userBuilder
                .username(getEnvVar("app.user.username"))
                .password(getEnvVar("app.user.password"))
                .roles("app.user.role")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    private String getEnvVar(String envVar) {
        return Objects.requireNonNull(env.getProperty(envVar));
    }
}

