package com.pay.common.vault;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;

@Component
public class VaultAdapter {
    private  final  AESProvider encryptor;

    @Autowired
    public VaultAdapter(VaultTemplate vaultTemplate){
        VaultKeyValueOperations vaultKeyValueOperations =vaultTemplate.opsForKeyValue(
                "kv-v1/data/encrypt",
                VaultKeyValueOperationsSupport.KeyValueBackend.KV_2
        );
        String key = (String) vaultKeyValueOperations.get("dbkey").getData().get("key");
        System.out.println("key = " + key);
        this.encryptor = new AESProvider(key);
    }

    public String encrypt(String plainText){
        try {
            return encryptor.encrypt(plainText);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public String decrypt(String plainText){
        try {
            return encryptor.decrypt(plainText);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
