package Alavarse.Ortega.Battery.Commerce.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class EncryptService {
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 256;
    private static final int T_LEN = 128;
    private final SecretKey secretKey;

    public EncryptService(@Value("${api.security.key.secret}") String secretKey) {
        this.secretKey = new SecretKeySpec(Base64.getDecoder().decode(secretKey), ALGORITHM);
    }

    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            byte[] iv = new byte[12];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(iv);
            GCMParameterSpec spec = new GCMParameterSpec(T_LEN, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            byte[] encryptedDataWithIv = new byte[iv.length + encryptedBytes.length];
            System.arraycopy(iv, 0, encryptedDataWithIv, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, encryptedDataWithIv, iv.length, encryptedBytes.length);
            return Base64.getEncoder().encodeToString(encryptedDataWithIv);
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for debugging
            throw new RuntimeException("Encryption error", e);
        }
    }

    public String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            byte[] iv = new byte[12];
            byte[] encryptedBytes = new byte[decodedData.length - iv.length];
            System.arraycopy(decodedData, 0, iv, 0, iv.length);
            System.arraycopy(decodedData, iv.length, encryptedBytes, 0, encryptedBytes.length);
            GCMParameterSpec spec = new GCMParameterSpec(T_LEN, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for debugging
            throw new RuntimeException("Decryption error", e);
        }
    }

}
