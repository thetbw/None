package xyz.thetbw.blog.util;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES加密
 */
public class AESEncodeUtils {
    /**
     * 编码
     */
    private static final String ENCODING = "UTF-8";
    /**
     * 加密算法
     */
    public static final String KEY_ALGORITHM = "AES";
    /**
     * 签名算法
     */
    public static final String SIGN_ALGORITHMS = "SHA1PRNG";

    private static AESEncodeUtils ourInstance = new AESEncodeUtils();

    public static AESEncodeUtils getInstance() {
        return ourInstance;
    }

    private AESEncodeUtils() {
    }

    /**
     * AES加密字符串
     *
     * @param content  需要被加密的字符串
     * @param password 加密需要的密码
     * @return 密文
     */
    public byte[] encrypt(String content, String password) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec keySpec = genKey(password.getBytes());
        Cipher cipher = Cipher.getInstance("AES");
        byte[] c = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE,keySpec);
        return cipher.doFinal(c);
    }


    /**
     * 解密AES加密过的字符串
     *
     * @param content  AES加密过过的内容
     * @param password 加密时的密码
     * @return 明文
     */
    public byte[] decrypt(byte[] content, String password) throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        SecretKeySpec key = genKey(password.getBytes());// 转换为AES专用密钥
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化为解密模式的密码器
        return cipher.doFinal(content);

    }

    private SecretKeySpec genKey(byte[] bytes) throws NoSuchAlgorithmException {
        KeyGenerator kGen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
        kGen.init(128, new SecureRandom(bytes));
        SecretKey secretKey = kGen.generateKey();// 根据用户密码，生成一个密钥
        byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
        return key;
    }

}
