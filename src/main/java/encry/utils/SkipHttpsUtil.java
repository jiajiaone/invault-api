package encry.utils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @Classname SkipHttpsUtil
 * @Date 2021/11/9 11:03
 * @Created by chenrujia
 * @Description
 */
public class SkipHttpsUtil {
   public static HttpClient wrapClient() {
    try {
     SSLContext ctx = SSLContext.getInstance("TLS");
     X509TrustManager tm = new X509TrustManager() {
      @Override
      public X509Certificate[] getAcceptedIssuers() {
       return null;
      }

      @Override
      public void checkClientTrusted(X509Certificate[] arg0,
                                     String arg1) throws CertificateException {
      }

      @Override
      public void checkServerTrusted(X509Certificate[] arg0,
                                     String arg1) throws CertificateException {
      }
     };
     ctx.init(null, new TrustManager[] { tm }, null);
     SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(
             ctx, NoopHostnameVerifier.INSTANCE);
     CloseableHttpClient httpclient = HttpClients.custom()
             .setSSLSocketFactory(ssf).build();
     return httpclient;
    } catch (Exception e) {
     return HttpClients.createDefault();
    }
   }
}
