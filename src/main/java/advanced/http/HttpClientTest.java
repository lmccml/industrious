package advanced.http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lmc
 * @date 2020/3/4 14:03
 */
public class HttpClientTest {
    public static void main(String[] args) {
        get();
        post();

    }

    public static void get() {
        // 1. 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 2. 创建HttpGet对象
        HttpGet httpGet = new HttpGet(
                "http://localhost:8080/query?userName=test&password=123456");
        CloseableHttpResponse response = null;
        try {
            // 3. 执行GET请求
            response = httpClient.execute(httpGet);
            System.out.println(response.getStatusLine());
            // 4. 获取响应实体
            HttpEntity entity = response.getEntity();
            // 5. 处理响应实体
            if (entity != null) {
                System.out.println("长度：" + entity.getContentLength());
                System.out.println("内容：" + EntityUtils.toString(entity));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 6. 释放资源
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void post() {
        // 1. 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 2. 创建HttpPost对象
        HttpPost post = new HttpPost(
                "http://localhost:8080/query");
        // 3. 设置POST请求传递参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("userName", "test"));
        params.add(new BasicNameValuePair("password", "12356"));
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
            post.setEntity(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 4. 执行请求并处理响应
        try {
            CloseableHttpResponse response = httpClient.execute(post);
            HttpEntity entity = response.getEntity();
            if (entity != null){
                System.out.println("响应内容：");
                System.out.println(EntityUtils.toString(entity));
            }
            response.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

