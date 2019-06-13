package com.zephyr.common.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/** 
 ** 
 * AES128 算法，加密模式为ECB，填充模式为 pkcs7（实际就是pkcs5） 
 * 
 * 
 */  
public class AESUtil { 
	
	private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);
	private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
  
    /** 
     * 加密 
     * 
     * @param content 需要加密的内容 
     * @param key  加密密码 
     * @return 
     */  
    public static byte[] encrypt(String content, String key) {  
        try {  
            KeyGenerator kgen = KeyGenerator.getInstance("AES"); 
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");  
            secureRandom.setSeed(key.getBytes());  
            //kgen.init(128, new java.security.SecureRandom(key.getBytes()));  
            kgen.init(128, secureRandom);  
            SecretKey secretKey = kgen.generateKey();  
            byte[] enCodeFormat = secretKey.getEncoded();  
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");  
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);// 创建密码器  
            byte[] byteContent = content.getBytes("UTF-8");  
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);// 初始化  
            byte[] result = cipher.doFinal(byteContent);  
            return result; // 加密  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /**解密 
     * @param content  待解密内容 
     * @param key 解密密钥 
     * @return 
     */  
    public static byte[] decrypt(byte[] content, String key) {  
        try {  
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");  
            secureRandom.setSeed(key.getBytes());  
            //kgen.init(128, new java.security.SecureRandom(key.getBytes()));  
            kgen.init(128, secureRandom);  
            SecretKey secretKey = kgen.generateKey();  
            byte[] enCodeFormat = secretKey.getEncoded();  
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");  
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);// 创建密码器  
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);// 初始化  
            byte[] result = cipher.doFinal(content);  
            return result; // 加密  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            logger.warn(e.getMessage());
        }  
        return null;  
    }  
  
    /** 
     * 字符串加密 
     * @param content 要加密的字符串 
     * @param key 加密的AES Key 
     * @return 
     */  
    public static String encryptString(String content, String key) {  
        return parseByte2HexStr( encrypt(content, key));  
    }  
  
    /** 
     * 字符串解密 
     * @param content 要解密的字符串 
     * @param key 解密的AES Key 
     * @return 
     */  
    public static String decryptString(String content, String key){  
        byte[] decryptFrom = parseHexStr2Byte(content);  
        byte[] decryptResult = decrypt(decryptFrom,key);  
        return decryptResult == null ? null : new String(decryptResult);  
    }  
  
  
    /**将16进制转换为二进制 
     * @param hexStr 
     * @return 
     */  
    public static byte[] parseHexStr2Byte(String hexStr) {  
        if (hexStr.length() < 1)  
            return null;  
        byte[] result = new byte[hexStr.length()/2];  
        for (int i = 0;i< hexStr.length()/2; i++) {  
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
            result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
    }  
  
  
    /**将二进制转换成16进制 
     * @param buf 
     * @return 
     */  
    public static String parseByte2HexStr(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
            String hex = Integer.toHexString(buf[i] & 0xFF);  
            if (hex.length() == 1) {  
                hex = '0' + hex;  
            }  
            sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
    } 
}  