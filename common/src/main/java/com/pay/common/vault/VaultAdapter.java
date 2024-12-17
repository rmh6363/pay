package com.pay.common.vault;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;

@Component
public class VaultAdapter {
    private final AESProvider encryptor;

    private final String vaultPath;

    private final String vaultKey;

    @Autowired
    public VaultAdapter(VaultTemplate vaultTemplate, @Value("${spring.cloud.vault.kv.path}") String vaultPath, @Value("${spring.cloud.vault.kv.key}") String vaultKey) {
        this.vaultKey = vaultKey;
        this.vaultPath = vaultPath;
        VaultKeyValueOperations vaultKeyValueOperations = vaultTemplate.opsForKeyValue(
                vaultPath,
                VaultKeyValueOperationsSupport.KeyValueBackend.KV_2
        );
        String key = (String) vaultKeyValueOperations.get(vaultKey).getData().get("key");
        this.encryptor = new AESProvider(key);
    }

    public String encrypt(String plainText) {
        try {
            return encryptor.encrypt(plainText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String plainText) {
        try {
            return encryptor.decrypt(plainText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
