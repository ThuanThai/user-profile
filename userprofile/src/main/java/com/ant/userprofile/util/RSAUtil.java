package com.ant.userprofile.util;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.*;
import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.util.Base64;

@Component
@Getter
public class RSAUtil {
    
//    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    
    public RSAUtil() throws Exception {
//        this.privateKey = loadPrivateKey("src/main/resources/private_key.pem");
        this.publicKey = loadPublicKey("src/main/resources/public_key.pem");
    }
    
//    public String decrypt(byte[] data) throws Exception {
////        Cipher cipher = Cipher.getInstance("RSA");
////        cipher.init(Cipher.DECRYPT_MODE, privateKey);
//
//        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
//        OAEPParameterSpec oaepParams = new OAEPParameterSpec(
//                "SHA-256",
//                "MGF1",
//                MGF1ParameterSpec.SHA256,
//                PSource.PSpecified.DEFAULT
//        );
//        cipher.init(Cipher.DECRYPT_MODE, privateKey, oaepParams);
////        cipher.init(Cipher.DECRYPT_MODE, privateKey);
//
//        byte[] decrypted = cipher.doFinal(data);
//        return new String(decrypted, StandardCharsets.UTF_8);
//    }
    
    public byte[] encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        OAEPParameterSpec oaepParams = new OAEPParameterSpec(
                "SHA-256",
                "MGF1",
                MGF1ParameterSpec.SHA256,
                PSource.PSpecified.DEFAULT
        );
        cipher.init(Cipher.ENCRYPT_MODE, publicKey, oaepParams);

        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }
    
//    public byte[] encrypt(String data) throws Exception {
//        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
//    }
    
//    private PrivateKey loadPrivateKey(String filename) throws Exception {
//        String key = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filename)));
//        key = key.replaceAll("-----BEGIN PRIVATE KEY-----", "")
//                .replaceAll("-----END PRIVATE KEY-----", "")
//                .replaceAll("\\s", "");
//
//        byte[] keyBytes = Base64.getDecoder().decode(key);
//        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
//        return KeyFactory.getInstance("RSA").generatePrivate(spec);
//    }
    
    private PublicKey loadPublicKey(String filename) throws Exception {
        String key = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filename)));
        key = key.replaceAll("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }
}
