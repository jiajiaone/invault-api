package encry.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname RSAUtils
 * @Date 2021/8/24 15:01
 * @Created by chenrujia
 * @Description
 */
public class RSAUtils {
   // 密码长度
   private static final int RSA_KEY_SIZE = 2048;
   // 算法名称
   private static final String KEY_ALGORITHM = "RSA";
   // 编码格式
   private static final String CODE_UTF8 = "UTF-8";
   // 最大加密字符长度
   private static final Integer MAX_ENCRYPT_BLOCK = 245;
   // 最大解密字符长度
   private static final Integer MAX_DECRYPT_BLOCK = 256;

   /**
    * 随机生成密钥对
    *
    * @throws NoSuchAlgorithmException
    */
   public static Map<String, String> genKeyPair(){
      // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
      KeyPairGenerator keyPairGen = null;
      try {
         keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
      } catch (NoSuchAlgorithmException e) {
         e.printStackTrace();
      }
      // 初始化密钥对生成器
      keyPairGen.initialize(RSA_KEY_SIZE, new SecureRandom());
      // 生成一个密钥对，保存在keyPair中
      KeyPair keyPair = keyPairGen.generateKeyPair();
      // 得到公私钥
      RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
      RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
      // 得到公钥
      String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
      // 得到私钥字符串
      String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
      Map<String,String> keyMap = new HashMap<>();
      keyMap.put("publicKey",publicKeyString);
      keyMap.put("privateKey",privateKeyString);
      return keyMap;
   }

   /**
    * RSA公钥加密
    *
    * @param str       加密字符串
    * @param publicKey 公钥
    * @return 密文
    * @throws Exception 加密过程中的异常信息
    */
   public static String encrypt(String str, String publicKey){
      try {
         // base64编码的公钥
         byte[] decoded = Base64.decodeBase64(publicKey);
         RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
         // RSA加密
         Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
         cipher.init(Cipher.ENCRYPT_MODE, pubKey);
         String outStr = Base64.encodeBase64String(doCrypt(str.getBytes(CODE_UTF8),cipher,MAX_ENCRYPT_BLOCK));
         return outStr;
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   /**
    * RSA私钥解密
    *
    * @param str        加密字符串
    * @param privateKey 私钥
    * @return 铭文
    * @throws
    */
   public static String decrypt(String str, String privateKey){
      try {
         // 64位解码加密后的字符串
         byte[] inputByte = Base64.decodeBase64(str.getBytes(CODE_UTF8));
         // base64编码的私钥
         byte[] decoded = Base64.decodeBase64(privateKey);
         RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decoded));
         // RSA解密
         Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
         cipher.init(Cipher.DECRYPT_MODE, priKey);
         String outStr = new String(doCrypt(inputByte,cipher,MAX_DECRYPT_BLOCK));
         return outStr;
      } catch (Exception e) {
         throw new RuntimeException();
      }
   }

   /**
    * 私钥加密
    *
    * @param data       加密数据
    * @param privateKey 私钥
    * @return
    */
   public static String encryptByPrivateKey(String data, String privateKey) {
      try {
         byte[] kb = Base64.decodeBase64(privateKey.getBytes(CODE_UTF8));
         PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(kb);
         KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
         PrivateKey key = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
         Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
         cipher.init(Cipher.ENCRYPT_MODE, key);
         byte[] b = data.getBytes(CODE_UTF8);
         byte[] encrypt = doCrypt(b,cipher,MAX_ENCRYPT_BLOCK);
         return Base64.encodeBase64String(encrypt);
      } catch (Exception e) {
         throw new RuntimeException();
      }
   }

   /**
    * 公钥解密
    *
    * @param data      解密数据
    * @param publicKey 公钥
    * @return
    */
   public static String decryptByPublicKey(String data, String publicKey) {
      try {
         byte[] kb = Base64.decodeBase64(publicKey.getBytes(CODE_UTF8));
         X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(kb);
         KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
         PublicKey key = keyFactory.generatePublic(x509EncodedKeySpec);
         Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
         cipher.init(Cipher.DECRYPT_MODE, key);
         byte[] b = data.getBytes(CODE_UTF8);
         byte[] decrypt = doCrypt(Base64.decodeBase64(b),cipher,MAX_DECRYPT_BLOCK);
         return new String(decrypt, CODE_UTF8);
      } catch (Exception e) {
         throw new RuntimeException();
      }
   }

   /**
    * 分段加解密
    *
    * @param data     要加解密的内容数组
    * @param cipher   加解密对象
    * @param maxBlock 分段大小
    * @return 结果
    */
   private static byte[] doCrypt(byte[] data, Cipher cipher, Integer maxBlock) {
      try {
         int inputLength = data.length;
         // 标识
         int offSet = 0;
         byte[] resultBytes = {};
         byte[] cache;
         while (inputLength - offSet > 0) {
            if (inputLength - offSet > maxBlock) {
               cache = cipher.doFinal(data, offSet, maxBlock);
               offSet += maxBlock;
            } else {
               cache = cipher.doFinal(data, offSet, inputLength - offSet);
               offSet = inputLength;
            }
            resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
            System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
         }
         return resultBytes;
      } catch (Exception e) {
         throw new RuntimeException();
      }
   }
}
