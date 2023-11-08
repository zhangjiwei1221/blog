package cn.butterfly.encryption.util;

import org.springframework.util.Base64Utils;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * AES 加密工具类
 *
 * @author zjw
 * @date 2023-10-27
 */
public class AESUtils {
    
    private static final String KEY = "85ffeb27076249ca81c86f8da2fc4966";
	private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    
    private AESUtils() {}

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
            byte[] result = cipher.doFinal(byteContent);
            return Base64Utils.encodeToString(result);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * AES 解密操作
     *
     * @param content 待解密内容
     * @return 解密内容
     */
    public static String decrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
            byte[] result = cipher.doFinal(Base64Utils.decodeFromString(content));
            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
			return null;
        }
    }

	/**
     * 生成加密秘钥
     *
     * @return 加密秘钥
     */
    private static SecretKeySpec getSecretKey() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(KEY.getBytes());
            kg.init(random);
            SecretKey secretKey = kg.generateKey();
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
        } catch (Exception e) {
            return null;
        }
    }
    
}
