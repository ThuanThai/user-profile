package com.ant.userprofile.service;

import com.ant.userprofile.dto.UserProfileResponse;
import com.ant.userprofile.model.User;
import com.ant.userprofile.repository.UserRepository;
import com.ant.userprofile.util.RSAUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RSAUtil rsaUtil;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public UserProfileResponse getUserById(Long userId) throws Exception {
        // Fetch user from database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Convert user to JSON
        String userJson = objectMapper.writeValueAsString(user);
        
        // Encrypt the response with private key
        byte[] encryptedResponse = rsaUtil.encrypt(userJson);
        String encryptedResponseBase64 = Base64.getEncoder().encodeToString(encryptedResponse);
        
////         Generate digital signature (SHA-256 hash signed with private key)
//        Signature signature = Signature.getInstance("SHA256withRSA");
//        signature.initSign(rsaUtil.getPrivateKey());
//        signature.update(userJson.getBytes());
//        byte[] signatureBytes = signature.sign();
//        String signatureBase64 = Base64.getEncoder().encodeToString(signatureBytes);
        
        // Return encrypted response and signature
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setData(encryptedResponseBase64);
//        userProfileResponse.setChecksum(signatureBase64);
        
        return userProfileResponse;
    }
    
}