package encry.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Classname SignUtils
 * @Date 2021/8/24 18:20
 * @Created by chenrujia
 * @Description
 */
public class SignUtils {

    //加密算法
    private static final String SHA256WithRSA = "SHA256WithRSA";

    private static final String RSA = "RSA";

    /**
     * SHA256加密
     * @param str
     * @return
     */
    public static String SHA256Str(String str){
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
            encdeStr = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encdeStr.toUpperCase();
    }

    /**
     * 获取当前时间戳，单位毫秒
     * @return
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 使用私钥给入参签名
     * @param privateKey 私钥
     * @param param      签名的数据
     * @return            返回入参签名16进制字符串
     * */
    public static String sign(String privateKey, String param) {
        try {
            //获取privatekey
            byte[] privateKeyByte = new org.apache.commons.codec.binary.Base64().decode(privateKey);
            KeyFactory keyfactory = KeyFactory.getInstance(RSA);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
            PrivateKey key = keyfactory.generatePrivate(pkcs8EncodedKeySpec);

            //用私钥给入参加签
            Signature sign = Signature.getInstance(SHA256WithRSA);
            sign.initSign(key);
            sign.update(param.getBytes());

            byte[] signature = sign.sign();
            //将签名的入参转换成16进制字符串
            return bytesToHexStr(signature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用公钥验证签名
     * @param param       入参
     * @param signature   使用私钥签名的入参字符串
     * @param publicKey   公钥
     * @return             返回验证结果
     * */

    public static boolean verifySign(String param,String signature,String publicKey){
        try {
            //获取公钥
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            byte[] publicKeyByte = new Base64().decode(publicKey);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyByte);
            PublicKey key= keyFactory.generatePublic(x509EncodedKeySpec);

            //用获取到的公钥对   入参中未加签参数param 与 入参中的加签之后的参数signature进行验签
            Signature sign=Signature.getInstance(SHA256WithRSA);
            sign.initVerify(key);
            sign.update(param.getBytes());

            //将16进制码转成字符数组
            byte[] hexByte = hexStrToBytes(signature);
            //验证签名
            return sign.verify(hexByte);

        }  catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * byte数组转换成十六进制字符串
     * @param bytes byte数组
     * @return      返回十六进制字符串
     */
    private static String bytesToHexStr(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer("");
        for (int i = 0; i < bytes.length; ++i) {
            stringBuffer.append(Integer.toHexString(0x0100 + (bytes[i] & 0x00FF)).substring(1).toUpperCase());
        }
        return stringBuffer.toString();
    }

    /**
     * 十六进制字符串转成byte数组
     * @param hexStr   十六进制字符串
     * @return          返回byte数组
     * */
    private static byte[] hexStrToBytes(String hexStr) {
        byte[] bytes = new byte[hexStr.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hexStr.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
