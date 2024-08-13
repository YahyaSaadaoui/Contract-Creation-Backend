package com.hps.admindashboardservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.vault.config.VaultProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VaultConfig {

    @Value("${vault.uri}")
    private String vaultUri;

    @Value("${vault.token}")
    private String vaultToken;

    @Bean
    public VaultProperties vaultProperties() {
        VaultProperties properties = new VaultProperties();
        properties.setUri(vaultUri);
        properties.setToken(vaultToken);
        return properties;
    }
}
