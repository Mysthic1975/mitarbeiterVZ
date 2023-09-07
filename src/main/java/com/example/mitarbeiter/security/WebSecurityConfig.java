package com.example.mitarbeiter.security;

import com.example.mitarbeiter.KeycloakLogoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    private final KeycloakLogoutHandler keycloakLogoutHandler;

    private final String clientName = "employee-app";

    WebSecurityConfig(KeycloakLogoutHandler keycloakLogoutHandler) {
        this.keycloakLogoutHandler = keycloakLogoutHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                //        .requestMatchers(new AntPathRequestMatcher("/, /?continue, /employee/**, employee/create/**, employee/edit/**, employee/delete/**"))
                //        .hasRole("admin, user")
                        .anyRequest().authenticated()
                )
//                .oauth2Login(Customizer.withDefaults())
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
                    httpSecurityOAuth2LoginConfigurer
                            .userInfoEndpoint(userInfoEndpointConfigurer -> {
                                userInfoEndpointConfigurer
                                        .oidcUserService(oidcUserService());
                            })
                            .permitAll();
                })
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt.jwtAuthenticationConverter(keycloakJwtAuthenticationConverter()))
//                )
                .logout(logout -> logout
                        .addLogoutHandler(keycloakLogoutHandler)
                        .logoutSuccessUrl("/"))
        ;

        return http.build();
    }



    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcUserService delegate = new OidcUserService();
//        delegate.setAccessibleScopes(Set.of("profile", "email", "roles"));

        return (userRequest) -> {
            // Delegate to the default implementation for loading a user
            OidcUser oidcUser = delegate.loadUser(userRequest);

            OAuth2AccessToken accessToken = userRequest.getAccessToken();


            Object resourceAccess = oidcUser.getAttribute("resource_access");
            List<String> rolesList = Collections.emptyList();

            if (!(resourceAccess instanceof Map)) {
                rolesList = Collections.emptyList();
            }

            Object serviceResourceAccess = ((Map<String, Object>) resourceAccess).get(clientName);

            if (!(serviceResourceAccess instanceof Map)) {
                rolesList = Collections.emptyList();
            }

            Object serviceRoles = ((Map<?, ?>) serviceResourceAccess).get("roles");
            if (!(serviceRoles instanceof ArrayList<?>)) {
                rolesList = Collections.emptyList();
            }

            rolesList = ((ArrayList<?>) serviceRoles).stream().map(Object::toString).toList();


            Set<GrantedAuthority> mappedAuthorities = new HashSet<>(rolesList.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList());

            // TODO
            // 1) Fetch the authority information from the protected resource using accessToken
            // 2) Map the authority information to one or more GrantedAuthority's and add it to mappedAuthorities

            // 3) Create a copy of oidcUser but use the mappedAuthorities instead
            oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());

            return oidcUser;
        };
    }
}