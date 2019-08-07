package com.pos.mahmoud.pos.Network;

import android.content.Context;

import com.pos.mahmoud.pos.DataBase.TerminalDatabase;
import com.pos.mahmoud.pos.R;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl, Context c) {
      //  if (retrofit==null) {

        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getSSLClient(c))

                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // }
        return retrofit;
    }
    private static OkHttpClient getSSLClient(Context context) throws
            NoSuchAlgorithmException,
            KeyStoreException,
            KeyManagementException,
            CertificateException,
            IOException {

        OkHttpClient client;
        SSLContext sslContext;
        SSLSocketFactory sslSocketFactory;
        TrustManager[] trustManagers;
        TrustManagerFactory trustManagerFactory;
        X509TrustManager trustManager;

        trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(readKeyStore(context));
        trustManagers = trustManagerFactory.getTrustManagers();

        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }

        trustManager = (X509TrustManager) trustManagers[0];

        sslContext = SSLContext.getInstance("TLS");

        sslContext.init(null, new TrustManager[]{trustManager}, null);

        sslSocketFactory = sslContext.getSocketFactory();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA256)
                .supportsTlsExtensions(true)
                .build();
        ArrayList specs = new ArrayList<>();
        specs.add(spec);
        int time=Integer.parseInt(TerminalDatabase.getInstance(context).daoAccess().fetchConfigration("1").getTWK());
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
             //   .connectionSpecs(specs)
                .readTimeout(time,TimeUnit.MINUTES)
                .connectTimeout(time,TimeUnit.MINUTES)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
               // .sslSocketFactory(sslSocketFactory, trustManager)
                .build();
        return client;
    }

    /**
     * Get keys store. Key file should be encrypted with pkcs12 standard. It    can be done with standalone encrypting java applications like "keytool". File password is also required.
     *
     * @param context Activity or some other context.
     * @return Keys store.
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    private static KeyStore readKeyStore(Context context) throws
            KeyStoreException,
            CertificateException,
            NoSuchAlgorithmException,
            IOException {
        KeyStore keyStore;
        char[] PASSWORD = "123456789".toCharArray();
        ArrayList<InputStream> certificates;
        int certificateIndex;
      // InputStream certificate = null;

        certificates = new ArrayList<>();
        certificates.add(context.getResources().openRawResource(R.raw.cert));

        keyStore = KeyStore.getInstance("pkcs12");

        for (InputStream certificate : certificates) {
            try {
                keyStore.load(certificate, PASSWORD);
            } finally {
                if (certificate != null) {
                    certificate.close();
                }
            }
        }
        return keyStore;
    }
}
