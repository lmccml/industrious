package tools;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

public class CommonUtil {

    private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

    public static String getURL(HttpServletRequest request) {
        String contextPath = request.getContextPath().equals("/") ? ""
                : request.getContextPath();
        String url = "http://" + request.getServerName();
        if (null2Int(request.getServerPort()) != 80) {
            url = url + ":" + null2Int(request.getServerPort()) + contextPath;
        } else {
            url = url + contextPath;
        }
        return url;
    }

    public static int null2Int(Object s) {
        int v = 0;
        if (s != null) {
            try {
                v = Integer.parseInt(s.toString());
            } catch (Exception e) {
            }
        }
        return v;
    }

    //获取本机ip
    public static String getServerIp() {
        StringBuilder IFCONFIG = new StringBuilder();
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                        IFCONFIG.append(inetAddress.getHostAddress().toString() + "\n");
                    }

                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        System.out.println(IFCONFIG);
        return IFCONFIG.toString();
    }

    public static String key = null;

    public static String reqSign(Map param) {
        if (key == null) {//一般不变，只第一次获取？
//            key = WebUtil.getSysConfigValue("req_key");
            key = "testkey";
        }
        String jsonStr = JSONObject.toJSONString(param);
        SortedMap<String, String> sortedMap = JSONObject.parseObject(jsonStr, SortedMap.class);
        String str = "";
        for (String keyStr : sortedMap.keySet()) {
            //去掉加密的md5key参数
            if (keyStr.equals("sign")) {
                continue;
            }
            str += keyStr;
            str += "=";
            str += sortedMap.get(keyStr);
            str += "&";
        }
        str += key;
        log.info("=====加密串==="+str);
        String sign = Md5.md5Str(str);
        log.info("=======加密值==="+sign);
        return sign;
    }

    //客户端单点登录开关
    public static boolean checkLoginKeySwitch() {
        return "1".equals("login_key_switch");
    }

    public static boolean checkSign(Map param) {
        boolean isTest = false;//测试阶段先不校验
        if (isTest) {
            return true;
        }
        if (param == null || param.get("sign") == null) {
            return false;
        }
        return param.get("sign").equals(reqSign(param));
    }

    //返回值类型为Map<String, String>
    public static Map<String, String> getParameterStringMap(HttpServletRequest request) {
        Map<String, String[]> properties = request.getParameterMap();//把请求参数封装到Map<String, String[]>中
        Map<String, String> returnMap = new HashMap<String, String>();
        String name = "";
        String value = "";
        for (Map.Entry<String, String[]> entry : properties.entrySet()) {
            name = entry.getKey();
            String[] values = entry.getValue();
            if (null == values) {
                value = "";
            } else if (values.length > 1) {
                for (int i = 0; i < values.length; i++) { //用于请求参数中有多个相同名称
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = values[0];//用于请求参数中请求参数名唯一
            }
            returnMap.put(name, value);

        }
        return returnMap;
    }

}
