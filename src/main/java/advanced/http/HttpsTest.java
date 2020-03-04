package advanced.http;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.security.cert.CertificateException;
import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * @author lmc
 * @date 2020/3/4 10:16
 */
public class HttpsTest {
    /**
     *
     * @return SSLSocketFactory
     * @throws CertificateException
     * @throws IOException
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLSocketFactory initSSLHttps() throws CertificateException,
            IOException, KeyStoreException, NoSuchAlgorithmException,
            KeyManagementException, java.security.cert.CertificateException {
        // openssl s_client -connect www.baidu.com:443
        String baidu = "-----BEGIN CERTIFICATE-----\n" +
                "MIIJrzCCCJegAwIBAgIMLO4ZPBiCeOo+Q3VzMA0GCSqGSIb3DQEBCwUAMGYxCzAJ\n" +
                "BgNVBAYTAkJFMRkwFwYDVQQKExBHbG9iYWxTaWduIG52LXNhMTwwOgYDVQQDEzNH\n" +
                "bG9iYWxTaWduIE9yZ2FuaXphdGlvbiBWYWxpZGF0aW9uIENBIC0gU0hBMjU2IC0g\n" +
                "RzIwHhcNMTkwNTA5MDEyMjAyWhcNMjAwNjI1MDUzMTAyWjCBpzELMAkGA1UEBhMC\n" +
                "Q04xEDAOBgNVBAgTB2JlaWppbmcxEDAOBgNVBAcTB2JlaWppbmcxJTAjBgNVBAsT\n" +
                "HHNlcnZpY2Ugb3BlcmF0aW9uIGRlcGFydG1lbnQxOTA3BgNVBAoTMEJlaWppbmcg\n" +
                "QmFpZHUgTmV0Y29tIFNjaWVuY2UgVGVjaG5vbG9neSBDby4sIEx0ZDESMBAGA1UE\n" +
                "AxMJYmFpZHUuY29tMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtMa/\n" +
                "2lMgD+pA87hSF2Y7NgGNErSZDdObbBhTsRkIsPpzRz4NOnlieGEuVDxJfFbawL5h\n" +
                "VdVCcGoQvvW9jWSWIQCTYwmHtxm6DiA+SchT7QKPRgHroQeTc7vt8bPJ4vvd8Dkq\n" +
                "g630QZi8huq6dKim49DlxY6zC7LSrJF0Dv+AECM2YmUItIf1VwwlxwDY9ahduDNB\n" +
                "pypf2/pwniG7rkIWZgdp/hwmKoEPq3Pj1lIgpG2obNRmSKRv8mgKxWWhTr8EekBD\n" +
                "HNN1+3WsGdZKNQVuz9Vl0UTKawxYBMSFTx++LDLR8cYo+/kmNrVt+suWoqDQvPhR\n" +
                "3wdEvY9vZ8DUr9nNwwIDAQABo4IGGTCCBhUwDgYDVR0PAQH/BAQDAgWgMIGgBggr\n" +
                "BgEFBQcBAQSBkzCBkDBNBggrBgEFBQcwAoZBaHR0cDovL3NlY3VyZS5nbG9iYWxz\n" +
                "aWduLmNvbS9jYWNlcnQvZ3Nvcmdhbml6YXRpb252YWxzaGEyZzJyMS5jcnQwPwYI\n" +
                "KwYBBQUHMAGGM2h0dHA6Ly9vY3NwMi5nbG9iYWxzaWduLmNvbS9nc29yZ2FuaXph\n" +
                "dGlvbnZhbHNoYTJnMjBWBgNVHSAETzBNMEEGCSsGAQQBoDIBFDA0MDIGCCsGAQUF\n" +
                "BwIBFiZodHRwczovL3d3dy5nbG9iYWxzaWduLmNvbS9yZXBvc2l0b3J5LzAIBgZn\n" +
                "gQwBAgIwCQYDVR0TBAIwADBJBgNVHR8EQjBAMD6gPKA6hjhodHRwOi8vY3JsLmds\n" +
                "b2JhbHNpZ24uY29tL2dzL2dzb3JnYW5pemF0aW9udmFsc2hhMmcyLmNybDCCA0kG\n" +
                "A1UdEQSCA0AwggM8ggliYWlkdS5jb22CEmNsaWNrLmhtLmJhaWR1LmNvbYIQY20u\n" +
                "cG9zLmJhaWR1LmNvbYIQbG9nLmhtLmJhaWR1LmNvbYIUdXBkYXRlLnBhbi5iYWlk\n" +
                "dS5jb22CEHduLnBvcy5iYWlkdS5jb22CCCouOTEuY29tggsqLmFpcGFnZS5jboIM\n" +
                "Ki5haXBhZ2UuY29tgg0qLmFwb2xsby5hdXRvggsqLmJhaWR1LmNvbYIOKi5iYWlk\n" +
                "dWJjZS5jb22CEiouYmFpZHVjb250ZW50LmNvbYIOKi5iYWlkdXBjcy5jb22CESou\n" +
                "YmFpZHVzdGF0aWMuY29tggwqLmJhaWZhZS5jb22CDiouYmFpZnViYW8uY29tgg8q\n" +
                "LmJjZS5iYWlkdS5jb22CDSouYmNlaG9zdC5jb22CCyouYmRpbWcuY29tgg4qLmJk\n" +
                "c3RhdGljLmNvbYINKi5iZHRqcmN2LmNvbYIRKi5iai5iYWlkdWJjZS5jb22CDSou\n" +
                "Y2h1YW5rZS5jb22CCyouZGxuZWwuY29tggsqLmRsbmVsLm9yZ4ISKi5kdWVyb3Mu\n" +
                "YmFpZHUuY29tghAqLmV5dW4uYmFpZHUuY29tghEqLmZhbnlpLmJhaWR1LmNvbYIR\n" +
                "Ki5nei5iYWlkdWJjZS5jb22CEiouaGFvMTIzLmJhaWR1LmNvbYIMKi5oYW8xMjMu\n" +
                "Y29tggwqLmhhbzIyMi5jb22CDiouaW0uYmFpZHUuY29tgg8qLm1hcC5iYWlkdS5j\n" +
                "b22CDyoubWJkLmJhaWR1LmNvbYIMKi5taXBjZG4uY29tghAqLm5ld3MuYmFpZHUu\n" +
                "Y29tggsqLm51b21pLmNvbYIQKi5zYWZlLmJhaWR1LmNvbYIOKi5zbWFydGFwcHMu\n" +
                "Y26CESouc3NsMi5kdWFwcHMuY29tgg4qLnN1LmJhaWR1LmNvbYINKi50cnVzdGdv\n" +
                "LmNvbYISKi54dWVzaHUuYmFpZHUuY29tggthcG9sbG8uYXV0b4IKYmFpZmFlLmNv\n" +
                "bYIMYmFpZnViYW8uY29tggZkd3ouY26CD21jdC55Lm51b21pLmNvbYIMd3d3LmJh\n" +
                "aWR1LmNughB3d3cuYmFpZHUuY29tLmNuMB0GA1UdJQQWMBQGCCsGAQUFBwMBBggr\n" +
                "BgEFBQcDAjAdBgNVHQ4EFgQUdrXm1kn4+DbqdaltXk1VWzdc/ccwHwYDVR0jBBgw\n" +
                "FoAUlt5h8b0cFilTHMDMfTuDAEDmGnwwggEEBgorBgEEAdZ5AgQCBIH1BIHyAPAA\n" +
                "dgC72d+8H4pxtZOUI5eqkntHOFeVCqtS6BqQlmQ2jh7RhQAAAWqaLuGaAAAEAwBH\n" +
                "MEUCICx7TcD5hUeKLQrAeTvWtLVm+Kr7glitIzb+Frymg5khAiEAwC/NnJkgy32R\n" +
                "X9KLxhMQc7XBVAMzQZ+masUUk89pK2sAdgBvU3asMfAxGdiZAKRRFf93FRwR2QLB\n" +
                "ACkGjbIImjfZEwAAAWqaLt5PAAAEAwBHMEUCIAMyaJ450OtfGWHbpxJpbyhEgQKl\n" +
                "PMKjE9V+mCZfIBqgAiEAp4tis7C0RDLiEf9FjVURLDarKZNEyDRcznw1VzGuqxIw\n" +
                "DQYJKoZIhvcNAQELBQADggEBAKq5zVKO3DZdR9SL8zIXBkaDYKMnBUkpsRtGbjj+\n" +
                "k/4JQ2zSoVgkEkK3q0H4Rwp9ZLV13FpFFLKkGGuctzuPs37SvcBySzUFrg0tGR9Q\n" +
                "c3Ja35cYO9sq895EzmQtwR6EzHYkPjBnIyboT/cL9uxp139RqaBvuMQU4sBKSsQA\n" +
                "XVdqyUHEJSsyGKpiqB5JgXMcgV9e+uSUMsNQbY6qzGxMUwz6j040eZ+lYMD4UHW4\n" +
                "oZ0B5qslIww7JAJAWCT/NAKLlGEQaC+2gOPQX0oKpwLSwJg+HegCyCdxJrKoh7bb\n" +
                "nRBHS8ITYjTG0Dw5CTklj/6i9PP735snPfzQKOht3N0X0x8=\n" +
                "-----END CERTIFICATE-----\n";



        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate ca = cf.generateCertificate(new ByteArrayInputStream(baidu
                .getBytes()));
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(null, null);
        keystore.setCertificateEntry("baiducom", ca);
        TrustManagerFactory tmf = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keystore);


        // Create an SSLContext that uses our TrustManager
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);


        URL url = new URL("https://www.baidu.com");
        HttpsURLConnection urlConnection = (HttpsURLConnection) url
                .openConnection();
        urlConnection.setSSLSocketFactory(context.getSocketFactory());
        InputStream input = urlConnection.getInputStream();


        BufferedReader reader = new BufferedReader(new InputStreamReader(input,
                "UTF-8"));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        System.out.print(result.toString());


        return context.getSocketFactory();
    }

    public static void main(String[] args) throws Exception {
        initSSLHttps();
    }
}
