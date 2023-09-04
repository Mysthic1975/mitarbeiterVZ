package com.example.mitarbeiter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// @EnableOAuth2Sso :ToDo: funktioniert nicht, vielleicht nicht kompatibel mehr

public class WebSecurityConfig {
    private final KeycloakLogoutHandler keycloakLogoutHandler;
    WebSecurityConfig(KeycloakLogoutHandler keycloakLogoutHandler) {
        this.keycloakLogoutHandler = keycloakLogoutHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        // .requestMatchers("/", "/list").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(Customizer.withDefaults())
                .logout(logout -> logout
                        .addLogoutHandler(keycloakLogoutHandler)
                        .logoutSuccessUrl("/"))
        ;

        return http.build();
    }

    /*@Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {

        ClientRegistration keycloak = keycloakClientRegistration();
        return new InMemoryClientRegistrationRepository(keycloak);
    }

    private ClientRegistration keycloakClientRegistration() {

        return ClientRegistration.withRegistrationId("employee")
                .clientId("employee-app")
                .clientSecret("KycVwdaZQAiCjbj5iueDMXiopVbXpvsL")
                .redirectUri("http://localhost:8080/login/oauth2/code/employee-app")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .issuerUri("http://localhost:8888/realms/employee")
                .authorizationUri("http://localhost:8888/realms/employee/protocol/openid-connect/auth")
                .tokenUri("http://localhost:8888/realms/employee/protocol/openid-connect/token")
                .userInfoUri("http://localhost:8888/realms/employee/protocol/openid-connect/userinfo")
                .build();
    }*/
}