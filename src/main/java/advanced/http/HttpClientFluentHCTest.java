package advanced.http;

import encryptor.MD5Utils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lmc
 * @date 2020/3/4 10:50
 */
public class HttpClientFluentHCTest {
    public static void main(String[] args) throws Exception {
        String base_url = "http://localhost:8080";
        String request_path = "/query";

        // 封装参数
        Map<String, String> params  = new HashMap<>();
        params.put("userName", "test");
        params.put("queryType", "1");

        // 封装参数,并加密md5
        Form form = Form.form();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            form.add(entry.getKey(), entry.getValue());
        }
        form.add("MD5", md5Data(params, "UTF-8", new String[] {"userName", "queryType"}));
        List<NameValuePair> requestForm = form.build();

        String response = Request.Post(base_url + request_path)
                .bodyForm(requestForm, Consts.UTF_8)
                .execute()
                .returnContent()
                .asString(Consts.UTF_8);
        System.out.println(response);
    }

    /**
     * md5加密
     * @param params   参数
     * @param chareset 加密串编码格式
     * @param keys     params中需要加密的字段
     */
    private static String md5Data(Map<String, String> params, String chareset, String[] keys) {
        StringBuffer sb = new StringBuffer();
        for (String key : keys) {
            sb.append(params.get(key));
        }
        String md5SecretKey = "testmd5";
        sb.append(md5SecretKey);
        return MD5Utils.MD5Encode(sb.toString(), chareset).toUpperCase();
    }
}
