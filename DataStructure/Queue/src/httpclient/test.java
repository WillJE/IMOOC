package httpclient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class test {
    public static void main(String[] args) {
        String url = "https://wcs1.lfuat.net/wms-skiiwisetw/logistic_inventory_snapshot";
        String key = "WMS-SKIIWISETW";
        String requestTime = "20181011142841";
        String sign = "7de1c6ba5db42f44f51097d8f0a22309";
        String version = "5.0";
        String serviceType = "logistic_inventory_snapshot";
        String jsonStr = "";

        System.out.println(test(url, key, requestTime, sign, version,serviceType,jsonStr));
    }

    public static String test(String url, String key, String requestTime, String sign, String version,String serviceType, String jsonStr){
//        String url = "https://wcs1.lfuat.net/wms-skiiwisetw/logistic_inventory_snapshot";
        String resultStr = null;
        HttpPost httpPost = null;
        try {
//            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
//                //信任所有
//                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                    return true;
//                }
//            }).build();
//            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(
//                    sslContext,
//                    new String[]{"TLSv1"}, // Allow TLSv1 protocol only
//                    null,
//                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
//            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(csf).build();
            HttpClient httpClient = HttpClients.createDefault();
//            RequestConfig requestConfig = RequestConfig.custom()
//                    .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
//                    .setSocketTimeout(5000).build();
            httpPost = new HttpPost(url);//根据URL，生成HttpPost对象
//            httpPost.setConfig(requestConfig);
            StringBuilder content = new StringBuilder();
            content.append("Key=");//KEY
            content.append(key);
            content.append("&");
            content.append("RequestTime=");//RequestTime
            content.append(requestTime);// 请求时间
            content.append("&");
            content.append("Sign=");//sign
            content.append(sign);//签名
            content.append("&");
            content.append("Version=");//version
            content.append(version);// version
            content.append("&");
            content.append("ServiceType=");//ServiceType
            content.append(serviceType);
            content.append("&");
            content.append("Data=");//Data
            content.append(jsonStr);
            //String  newSign = MD5Util.md5sign(sign.toString());//生成数字签名:59faa3b179ce155c7e735037e100537e

//	    	httpPost.addHeader("Content-type", "text/xml; charset=utf-8");//XML格式
//			httpPost.addHeader("Key", key);
//			httpPost.addHeader("RequestTime", requestTime);//请求时间
//			httpPost.addHeader("Sign", newSign);//数字签名
//			httpPost.addHeader("Version", version);
//			httpPost.addHeader("ServiceType",  serviceType);
//			httpPost.addHeader("Data",  serviceType);

            httpPost.setEntity(new StringEntity(content.toString() , Charset.forName("UTF-8")));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            InputStream inStream = entity.getContent();
            byte[] buffer = new byte[1024];
            int len = -1;
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            resultStr = outSteam.toString();
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;
    }
}
