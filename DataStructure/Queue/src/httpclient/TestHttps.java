package httpclient;

import com.write.AES;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TestHttps {
    private static final int TIME_OUT = 8 * 1000;                          //超时时间
    private static final String CHARSET = "utf-8";                         //编码格式
    private static final String PREFIX = "--";                            //前缀
    private static final String BOUNDARY = UUID.randomUUID().toString();  //边界标识 随机生成
    private static final String CONTENT_TYPE = "multipart/form-data";     //内容类型
    private static final String LINE_END = "\r\n";                        //换行

	public static void main(String[] args) throws Exception {

        JSONObject header = new JSONObject();
        JSONArray arraydata = new JSONArray();
        JSONObject requestBody = new JSONObject();

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost post=new HttpPost("http://221.228.101.165:8068/api/Domain/test/tts/warehouse");

        header.put("Content-Type","application/json");
        JSONObject body = new JSONObject();
        body.put("UserName","xhl001");
        body.put("PassWord","nvDOxtGqs0JpOVS54t1M8Q==");
        body.put("type","11");
        body.put("data", JSONArray.fromObject(arraydata));
        requestBody.put("header",header);
        requestBody.put("body", JSONObject.fromObject(body));

        HttpEntity entity = new StringEntity(requestBody.toString(),"utf-8");
        ((StringEntity)entity).setContentType("application/json");

        post.setEntity(entity);
        post.setHeader("Content-Type","application/json");//传输类型
        HttpResponse response =httpClient.execute(post);
        if(response!=null){//表示NC服务器调用不到ESB接口
            if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK) {//状态码200
                HttpEntity resultEntity=response.getEntity();
                String string=EntityUtils.toString(resultEntity);
                JSONObject object=JSONObject.fromObject(string);
                System.out.println(object);
            }else {

            }
        }


//	    Client client = new Client();
//		HttpClient httpClient = new SSLClient();
//        HttpClient httpClient = client.getIgnoreSslCertificateHttpClient();
//		HttpGet hg = new HttpGet("https://mobil-qa.winsafe.cn/DERPController/upload.do");
//		HttpResponse chr = httpClient.execute(hg);
//		System.out.println(EntityUtils.toString(chr.getEntity()));
//        StringBuilder sb2 = null;
//        Map<String,String> strParams = new HashMap<String, String>();
//        strParams.put("type","11");
//        strParams.put("UserName","xhl001");
////        strParams.put("PassWord",Encrypted("a123456"));
//        strParams.put("PassWord","FS4taGUYije4w2H4S3Owuw==");
//        File file = new File("20180930135157.json");
//		URL url = new URL("https://mobil-qa.winsafe.cn/DERPController/upload.do");
//		HttpsURLConnection  connection = (HttpsURLConnection)url.openConnection();
//        connection.setSSLSocketFactory(new TSLSocketConnectionFactory());
//        connection.setSSLSocketFactory(new DOSSLSocketFactory());

//        connection.setRequestMethod("POST");
//        connection.setDoOutput(true);
//        connection.setDoInput(true);
//        connection.setUseCaches(false);
//
//        //设置请求头
//        connection.setRequestProperty("Connection", "Keep-Alive");
//        connection.setRequestProperty("Charset", "UTF-8");
//        connection.setRequestProperty("Content-Type","multipart/form-data; BOUNDARY=" + BOUNDARY);
//
//        /**
//         * 请求体
//         */
//        //上传参数
//        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
//        //getStrParams()为一个
//        dos.writeBytes( getStrParams(strParams).toString() );
//        dos.flush();
//
//        //上传文件
//        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
////        out.writeUTF("--"+BOUNDARY+"\r\n"
////                +"Content-Disposition: form-data; name=\"file\"; filename=\"" +file.getName()+"\" \r\n"
////                +"Content-Type: application/octet-stream; charset=utf-8"+"\r\n\r\n");
//        StringBuffer sb = new StringBuffer();
//        sb.append(PREFIX);
//        sb.append(BOUNDARY);
//        sb.append(LINE_END);
//        // name里面的值为服务器端需要的Form表单对应的key
//        // filename是文件的名字，包含后缀名的
//        sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\""
//                + LINE_END);
//        sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END); //此处待优化，应该根据不同文件类型生成不同的Content-Type
//        sb.append(LINE_END);
//        out.write(sb.toString().getBytes());
//        InputStream in = new FileInputStream(file);
//        byte[] b = new byte[8*1024];
//        int l = 0;
//        while((l = in.read(b)) != -1) out.write(b,0,l); // 写入文件
////        out.writeUTF("\r\n--"+BOUNDARY+"--\r\n");
//        in.close();
//        out.write(LINE_END.getBytes());
//        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
//        out.write(end_data); // 刷新发送数据
//        out.flush(); // 得到响应码
//        int res = connection.getResponseCode();
//        StringBuffer response = null;
//        // 上传成功返回200
//        if (res == 200) {
//            BufferedReader bufferedReader = new BufferedReader(
//                    new InputStreamReader(connection.getInputStream(), "UTF-8"));
//            String readLine = null;
//            response = new StringBuffer();
//            while (null != (readLine = bufferedReader.readLine())) {
//                response.append(readLine);
//            }
//        }
//        connection.disconnect();
//        System.out.println(response.toString());


//        connection.connect();//连接
//		BufferedReader bufferedReader = new BufferedReader(
//				new InputStreamReader(connection.getInputStream(), "UTF-8"));
//		String readLine = null;
//		StringBuffer response = new StringBuffer();
//		while (null != (readLine = bufferedReader.readLine())) {
//			response.append(readLine);
//		}
//
//		bufferedReader.close();
//		String responseBody = response.toString();
//		System.out.println(responseBody);
	}

    /**
     * 对post参数进行编码处理
     * */
    private static StringBuilder getStrParams(Map<String,String> strParams){
        StringBuilder strSb = new StringBuilder();
        for (Map.Entry<String, String> entry : strParams.entrySet() ){
            strSb.append(PREFIX)
                    .append(BOUNDARY)
                    .append(LINE_END)
                    .append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END)
                    .append("Content-Type: text/plain; charset=" + CHARSET + LINE_END)
                    .append("Content-Transfer-Encoding: 8bit" + LINE_END)
                    .append(LINE_END)// 参数头设置完以后需要两个换行，然后才是参数内容
                    .append(entry.getValue())
                    .append(LINE_END);
        }
        return strSb;
    }

    private static String Encrypted(String password) {
        com.write.AES mAes = new AES();
        Security.addProvider(new BouncyCastleProvider());
        String pwd = mAes.encrypt(password.getBytes());
        System.out.println(pwd);
        return pwd;
    }
}
