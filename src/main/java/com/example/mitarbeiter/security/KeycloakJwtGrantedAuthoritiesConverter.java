package com.example.mitarbeiter.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class KeycloakJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    // get client name of service/application from configuration file
    @Value("${app.security.clientname}") String clientName;

    private static final Collection<String> WELL_KNOWN_AUTHORITIES_CLAIM_NAMES = Arrays.asList("scope", "scp");

    private final Map<String,String> scopes;
    private String authoritiesClaimName = null;
    private String authorityPrefix = "right:";

    KeycloakJwtGrantedAuthoritiesConverter(Map<String,String> scopes) {
        this.scopes = scopes == null ? Collections.emptyMap(): scopes;
    }

    public void setAuthoritiesClaimName(String authoritiesClaimName) {
        this.authoritiesClaimName = authoritiesClaimName;
    }

    public void setAuthorityPrefix(String authorityPrefix) {
        this.authorityPrefix = authorityPrefix;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        Collection<String> tokenScopes = parseScopesClaim(jwt);
        if ( tokenScopes.isEmpty()) {
            return Collections.emptyList();
        }

        HashSet<GrantedAuthority> collect = tokenScopes.stream()
                .map(s -> scopes.getOrDefault(s, s))
                .map(s -> this.authorityPrefix + s)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toCollection(HashSet::new));


        Collection<String> rolesStrings = extractKeycloakResourceAccess(jwt);
        collect.addAll(
                rolesStrings.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );

        return collect;
    }

    protected Collection<String> parseScopesClaim(Jwt jwt) {

        String scopeClaim;

        if ( this.authoritiesClaimName == null ) {
            scopeClaim = WELL_KNOWN_AUTHORITIES_CLAIM_NAMES.stream()
                    .filter(jwt::hasClaim)
                    .findFirst()
                    .orElse(null);

            if ( scopeClaim == null ) {
                return Collections.emptyList();
            }
        }
        else {
            scopeClaim = this.authoritiesClaimName;
        }

        Object v = jwt.getClaim(scopeClaim);
        if ( v == null ) {
            return Collections.emptyList();
        }

        if ( v instanceof String) {
            return Arrays.asList(v.toString().split(" "));
        }
        else if ( v instanceof Collection ) {
            return ((Collection<?>)v).stream()
                    .map(Object::toString)
                    .collect(Collectors.toCollection(HashSet::new));
        }
        return Collections.emptyList();
    }

    protected Collection<String> extractKeycloakResourceAccess(Jwt jwt) {
        Object resourceAccess = jwt.getClaim("resource_access");
        if (!(resourceAccess instanceof Map)) {
            return Collections.emptyList();
        }

        Object serviceResourceAccess = ((Map<String, Object>) resourceAccess).get(clientName);

        if (!(serviceResourceAccess instanceof Map)) {
            return Collections.emptyList();
        }

        Object serviceRoles = ((Map<?, ?>) serviceResourceAccess).get("roles");
        if (!(serviceRoles instanceof ArrayList<?>)) {
            return Collections.emptyList();
        }

        return ((ArrayList<?>) serviceRoles).stream().map(Object::toString).toList();
    }
}