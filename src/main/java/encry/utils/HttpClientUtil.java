package encry.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URI;
import java.util.Map;



/**
 * @Classname HttpClientUtil
 * @Date 2021/11/9 10:22
 * @Created by chenrujia
 * @Description
 */
public class HttpClientUtil {

   private static final String CODE_FORMAT = "UTF-8";

   /**
    * get
    * @param url
    * @param param
    * @return
    */
   public static String doGet(String url, Map<String, String> param) {
     // 创建Httpclient对象
     CloseableHttpClient httpClient = (CloseableHttpClient) SkipHttpsUtil.wrapClient();
     String resultString = "";
     CloseableHttpResponse response = null;
     try {
      // 创建uri
      URIBuilder builder = new URIBuilder(url);
      if (param != null) {
       for (String key : param.keySet()) {
        builder.addParameter(key, param.get(key));
       }
      }
      URI uri = builder.build();
      // 创建http GET请求
      HttpGet httpGet = new HttpGet(uri);
      // 执行请求
      response = httpClient.execute(httpGet);
      resultString = EntityUtils.toString(response.getEntity(), CODE_FORMAT);
     } catch (Exception e) {
      e.printStackTrace();
     } finally {
      try {
       response.close();
       httpClient.close();
      } catch (IOException e) {
       e.printStackTrace();
      }
     }
     return resultString;
  }

   /**
    * post
    * @param url
    * @param json
    * @return
    */
   public static String doPostJson(String keyStr,String sign,String url,String json){
       // 创建Httpclient对象
       CloseableHttpClient httpClient = (CloseableHttpClient) SkipHttpsUtil.wrapClient();
       CloseableHttpResponse response = null;
       String resultString = "";
       try {
           // 创建Http Post请求
           HttpPost httpPost = new HttpPost(url);
           // 设置头部信息
           httpPost.setHeader("timeStamp",String.valueOf(SignUtils.getCurrentTimestamp()));
           httpPost.setHeader("keyStr",keyStr);
           httpPost.setHeader("sign",sign);
           // 创建请求内容
           StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
           httpPost.setEntity(entity);
           // 执行http请求
           response = httpClient.execute(httpPost);
           resultString = EntityUtils.toString(response.getEntity(), CODE_FORMAT);
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           try {
               response.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       return resultString;
   }
}
