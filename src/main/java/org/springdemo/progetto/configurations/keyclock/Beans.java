package org.springdemo.progetto.configurations.keyclock;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {
//    @Value("${keycloak.domain}")
//    static String serverUrl;
//    @Value("${keycloak.realm}")
//    public static String realm;
//    @Value("${keycloak.clientId}")
//    static String clientId;
//    @Value("${keycloak.clientSecret}")
//    static String clientSecret;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8080")
                .realm("fake-store")
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId("api-store")
                .clientSecret("EPIZUwiZGVsAYMiPx7omEIZLVP2sqTNe")
                .build();
    }
}
