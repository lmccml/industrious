package tools;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Http客户端工具类
 */
public class HttpClientUtil {

    private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    private static int timeout = 10 * 1000;


    /**
     * http get请求
     *
     * @param url     url地址
     * @param timeOut 超时时间，毫秒为单位
     * @param param   参数
     * @return
     */
    public static String doGet(String url, String param, int timeOut) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            if ("https".equalsIgnoreCase(realUrl.getProtocol())) {
                SSLUtil.ignoreSsl();
            }
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 发送GET请求必须设置如下两行
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            if (timeOut > 0) {
                conn.setConnectTimeout(timeOut);
            }
            // flush输出流的缓冲
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String doGet(String url, String param) {

        return doGet(url, param, timeout);
    }

    public static String doGet(String url, Map<String, String> parasMap, int timeOut) {
        return doGet(url, ParamUtil.getOrderedParas(parasMap), timeOut);
    }

    public static String doGet(String url, Map<String, String> parasMap) {

        return doGet(url, parasMap, timeout);
    }

    public static String doGet(String url) {

        return doGet(url, "", timeout);
    }

    /**
     * http post请求
     *
     * @param url     url链接
     * @param paras   url参数
     * @param timeOut 超时时间，毫秒为单位
     * @return
     */
    public static String doPost(String url, String paras, int timeOut, String reqCharset, String ContentType) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            if(StringUtils.isNotEmpty(ContentType)){
                conn.setRequestProperty("Content-Type", ContentType);
            }
            // 设置超时
            if (timeOut > 0) {
                conn.setConnectTimeout(timeOut);
            }
            log.info("======================================发送POST请求URL：" + url);
            log.info("======================================发送POST请求参数："
                    + paras);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(new OutputStreamWriter(
                    conn.getOutputStream(), reqCharset));
            // 发送请求参数
            out.print(paras);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * http post请求
     *
     * @param url     url链接
     * @param paras   url参数
     * @param timeOut 超时时间，毫秒为单位
     * @return
     */
    public static String doPost(String url, String paras, int timeOut, String reqCharset) {
        return doPost(url, paras, timeOut, reqCharset, null);
    }

    public static String doPost(String url, String paras, String reqCharset) {
        return doPost(url, paras, timeout, reqCharset);
    }

    public static String doPost(String url, Map<String, String> parasMap, int timeOut, String reqCharset) {
        return doPost(url, ParamUtil.getOrderedParas(parasMap), timeOut, reqCharset);
    }

    public static String doPost(String url, Map<String, String> parasMap, String reqCharset) {
        return doPost(url, parasMap, timeout, reqCharset);
    }

    public static String doPost(String url, Map<String, String> parasMap) {
        return doPost(url, parasMap, timeout, "UTF-8");
    }


    public static String doPostExc(String url, Map<String, String> parasMap, int timeOut, String reqCharset) throws  Exception {
        return doPostExc(url, ParamUtil.getOrderedParas(parasMap), timeOut, reqCharset);
    }
    /**
     * http post请求 exception
     *
     * @param url     url链接
     * @param paras   url参数
     * @param timeOut 超时时间，毫秒为单位
     * @return
     */
    public static String doPostExc(String url, String paras, int timeOut, String reqCharset) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        URLConnection conn = realUrl.openConnection();
        // 设置通用的请求属性
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // 设置超时
        if (timeOut > 0) {
            conn.setConnectTimeout(timeOut);
        }
        log.info("======================================发送POST请求URL：" + url);
        log.info("======================================发送POST请求参数："
                + paras);
        // 获取URLConnection对象对应的输出流
        out = new PrintWriter(new OutputStreamWriter(
                conn.getOutputStream(), reqCharset));
        // 发送请求参数
        out.print(paras);
        // flush输出流的缓冲
        out.flush();
        // 定义BufferedReader输入流来读取URL的响应
        in = new BufferedReader(new InputStreamReader(
                conn.getInputStream(), "UTF-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }

        return result;
    }
}
