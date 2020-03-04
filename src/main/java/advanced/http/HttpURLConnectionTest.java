package advanced.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author lmc
 * @date 2020/3/4 13:44
 */
public class HttpURLConnectionTest {
    public static void main(String[] args) {
        post();
        get();
    }

    public static void get() {
        try {
            // 1. 得到访问地址的URL
            URL url = new URL(
                    "http://localhost:8080/query?userName=test&password=123456");
            // 2. 得到网络访问对象java.net.HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            /* 3. 设置请求参数（过期时间，输入、输出流、访问方式），以流的形式进行连接 */
            // 设置是否向HttpURLConnection输出
            connection.setDoOutput(false);
            // 设置是否从httpUrlConnection读入
            connection.setDoInput(true);
            // 设置请求方式
            connection.setRequestMethod("GET");
            // 设置是否使用缓存
            connection.setUseCaches(true);
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            connection.setInstanceFollowRedirects(true);
            // 设置超时时间
            connection.setConnectTimeout(3000);
            // 连接
            connection.connect();
            // 4. 得到响应状态码的返回值 responseCode
            int code = connection.getResponseCode();
            // 5. 如果返回值正常，数据在网络中是以流的形式得到服务端返回的数据
            String msg = "";
            if (code == 200) { // 正常响应
                // 从流中读取响应信息
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String line = null;

                while ((line = reader.readLine()) != null) { // 循环从流中读取
                    msg += line + "\n";
                }
                reader.close(); // 关闭流
            }
            // 6. 断开连接，释放资源
            connection.disconnect();

            // 显示响应结果
            System.out.println(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void post() {
        try{
            // 1. 获取访问地址URL
            URL url = new URL("http://localhost:8080/query");
            // 2. 创建HttpURLConnection对象
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            /* 3. 设置请求参数等 */
            // 请求方式
            connection.setRequestMethod("POST");
            // 超时时间
            connection.setConnectTimeout(3000);
            // 设置是否输出
            connection.setDoOutput(true);
            // 设置是否读入
            connection.setDoInput(true);
            // 设置是否使用缓存
            connection.setUseCaches(false);
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            connection.setInstanceFollowRedirects(true);
            // 设置使用标准编码格式编码参数的名-值对
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 连接
            connection.connect();
            /* 4. 处理输入输出 */
            // 写入参数到请求中
            String params = "userName=test&password=123456";
            OutputStream out = connection.getOutputStream();
            out.write(params.getBytes());
            out.flush();
            out.close();
            // 从连接中读取响应信息
            String msg = "";
            int code = connection.getResponseCode();
            if (code == 200) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    msg += line + "\n";
                }
                reader.close();
            }
            // 5. 断开连接
            connection.disconnect();

            // 处理结果
            System.out.println(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

