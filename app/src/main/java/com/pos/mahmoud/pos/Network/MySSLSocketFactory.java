package com.pos.mahmoud.pos.Network;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class MySSLSocketFactory extends SSLSocketFactory {



    /**
     * the order of ciphers in this list is important here e.g. TLS_DHE_* must not stay above TLS_RSA_*
     */
    private static final String[] APPROVED_CIPHER_SUITES = new String[]{
            "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA",
            "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA",
            "TLS_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_RSA_WITH_AES_128_CBC_SHA",
            "TLS_RSA_WITH_AES_256_CBC_SHA",
            "TLS_DHE_RSA_WITH_AES_128_CBC_SHA",
            "TLS_DHE_RSA_WITH_AES_256_CBC_SHA",
            "TLS_DHE_RSA_WITH_AES_128_GCM_SHA256",
    };

    private SSLSocketFactory factory;
    static TrustManager[] m;
    public MySSLSocketFactory(TrustManager[] m) {
        this.m=m;
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null,  m, new java.security.SecureRandom());
            factory = sslcontext.getSocketFactory();
        } catch (Exception ex) {

        }
    }

    // dirty
    private void injectHostname(InetAddress address, String host) {
        try {
            Field field = InetAddress.class.getDeclaredField("hostName");
            field.setAccessible(true);
            field.set(address, host);
        } catch (Exception ignored) {

        }
    }

    public static SocketFactory getDefault() {
        return new MySSLSocketFactory(m);
    }

    public Socket createSocket() throws IOException {
        return factory.createSocket();
    }

    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
        return factory.createSocket(socket, host, port, autoClose);
    }

    public Socket createSocket(InetAddress addr, int port, InetAddress localAddr, int localPort) throws IOException {
        return factory.createSocket(addr, port, localAddr, localPort);
    }

    public Socket createSocket(InetAddress inaddr, int i) throws IOException {
        return factory.createSocket(inaddr, i);
    }

    public Socket createSocket(String host, int port, InetAddress localAddr, int localPort) throws IOException {
        return factory.createSocket(host, port, localAddr, localPort);
    }

    public Socket createSocket(String host, int port) throws IOException {

        InetAddress addr = InetAddress.getByName(host);
        injectHostname(addr, host);

        Socket socket = factory.createSocket(addr, port);
        ((SSLSocket) socket).setEnabledCipherSuites(getSupportedCipherSuites());
        return socket;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return APPROVED_CIPHER_SUITES;
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return APPROVED_CIPHER_SUITES;
    }
}