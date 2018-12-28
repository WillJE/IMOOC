package httpclient;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpException;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class Client {
	public CloseableHttpClient getIgnoreSslCertificateHttpClient() throws HttpException {

    	SSLContext sslContext = null;
    	try {
    	  sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

    	    @Override
    	    public boolean isTrusted(final X509Certificate[] arg0, final String arg1)
    	      throws CertificateException {

    	      return true;
    	    }
    	  }).build();
    	} catch (Exception e) {
    	  throw new HttpException("can not create http client.", e);
    	}
    	SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
    	  new NoopHostnameVerifier());
    	Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
    	  .<ConnectionSocketFactory> create()
    	  .register("http", PlainConnectionSocketFactory.getSocketFactory())
    	  .register("https", sslSocketFactory).build();
    	PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(
    	  socketFactoryRegistry);
    	return HttpClientBuilder.create().setSslcontext(sslContext).setConnectionManager(connMgr)
    	  .build();
    	}
}
