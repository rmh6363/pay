package com.pay.common.vault;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultToken;

@Configuration
public class VaultConfig {
    @Value("${spring.cloud.vault.token}")
    private String vaultToken;

    @Value("${spring.cloud.vault.scheme}")
    private String vaultScheme;

    @Value("${spring.cloud.vault.host}")
    private String vaultHost;

    @Value("${spring.cloud.vault.port}")
    private int vaultPort;

    @Bean
    public VaultTemplate vaultTemplate(){
        System.out.println("vaultToken = " + vaultToken);
        System.out.println("vaultScheme = " + vaultScheme);
        System.out.println("vaultHost = " + vaultHost);
        System.out.println("vaultPort = " + vaultPort);
        VaultEndpoint endpoint = VaultEndpoint.create(vaultHost,vaultPort);
        // vaultScheme: http https
        endpoint.setScheme(vaultScheme);
        return new VaultTemplate(endpoint,()-> VaultToken.of(vaultToken));
    }
}
