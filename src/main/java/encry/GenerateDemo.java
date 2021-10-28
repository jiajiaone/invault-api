package encry;

import encry.utils.RSAUtils;
import java.util.Map;

/**
 * @Classname demo
 * @Date 2021/8/27 10:19
 * @Created by chenrujia
 * @Description 生成公私密钥对demo
 */
public class GenerateDemo {
    public static void main(String[] args) {
        // 1.生成公私密钥对,仅生成一次即可
        Map<String,String> keyMap = RSAUtils.genKeyPair();
        // 获取私钥，用于加密信息
        String privateKey = keyMap.get("privateKey");
        System.out.println("随机生成的私钥为:" + privateKey);
        // 获取公钥，用于平台绑定
        String publicKey = keyMap.get("publicKey");
        System.out.println("随机生成的公钥为:" + publicKey);
    }
}
