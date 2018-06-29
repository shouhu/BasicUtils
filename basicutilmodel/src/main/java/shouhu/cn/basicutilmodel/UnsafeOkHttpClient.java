package shouhu.cn.basicutilmodel;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * Created by ZhangXinchao on 2018/1/17.
 */

public class UnsafeOkHttpClient {

    public static OkHttpClient getUnsafeOkHttpClient() {
        final X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {

        }
        try {
            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
        } catch (KeyManagementException e) {

        }

        try {
            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        return new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, trustManager).hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }).build();

//        SSLContext sslContext = null ;
//        try {
//            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            keyStore.load(null);
//            String certificateAlias = Integer.toString(0);
//            keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(ProjectApp.getContext().getAssets().open("srca.cer")));
////            keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(ProjectApp.getContext().getAssets().open(ProjectApp.getWalletConfig().getCert1AssetFileName())));
////            String certificateAlias1 = Integer.toString(1);
////            keyStore.setCertificateEntry(certificateAlias1, certificateFactory.generateCertificate(ProjectApp.getContext().getAssets().open(ProjectApp.getWalletConfig().getCert2AssetFileName())));
//            sslContext = SSLContext.getInstance("TLS");
//            final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init(keyStore);
//            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
//        } catch (CertificateException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        }

    }
}
