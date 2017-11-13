package app.ray.wechatmements.net.utils;

import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Ray on 2017/11/13.
 */

public class FakeX509TrustManager implements X509TrustManager {

    private static TrustManager[] trustManagers;
    private static final X509Certificate[] _AcceptedIssuers = new
            X509Certificate[0];

    @Override
    public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s)
            throws java.security.cert.CertificateException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s)
            throws java.security.cert.CertificateException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return _AcceptedIssuers;
    }
}
