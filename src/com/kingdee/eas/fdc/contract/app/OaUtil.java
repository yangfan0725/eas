package com.kingdee.eas.fdc.contract.app;



import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class OaUtil {
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String DEFAULT_PSWD = "Djk@%&opN!$$*";
    private static final String KEY_ALGORITHM = "AES";
    public static String encrypt(String content) throws Exception {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(DEFAULT_PSWD));
        byte[] result = cipher.doFinal(byteContent);
        return new String(Base64.encodeBase64(result));
    }
    private static SecretKeySpec getSecretKey(final String password) throws NoSuchAlgorithmException {
        KeyGenerator kg = null;
        kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
        secureRandom.setSeed(password.getBytes());
        kg.init(128, secureRandom);
        SecretKey secretKey = kg.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
    }
    public static String decrypt(String content) throws Exception {
      Cipher cipher =  Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE,
                getSecretKey(DEFAULT_PSWD));
        byte[] result =  cipher.doFinal(Base64.decodeBase64(content.getBytes()));
        return new String(result, "utf-8");
    }

}
