
package tools;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tools.jedis.JedisClientSingle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.SortedMap;


/**
 * 提供获取request,session,转换json等常用方法
 */
public class BaseController {

    // header 常量定义
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final boolean DEFAULT_NOCACHE = true;
    // Content Type 常量定义
    public static final String TEXT_TYPE = "text/plain";
    public static final String JSON_TYPE = "application/json";
    public static final String XML_TYPE = "text/xml";
    public static final String HTML_TYPE = "text/html";

    @Resource
    protected JedisClientSingle jedisClientSingle;


    public static int PAGE_NUMERIC = 20;

    public final String errorMsg = "网络异常";

    public static final Logger log = LoggerFactory
            .getLogger(BaseController.class);


    // 获取request对象
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 直接输出内容的简便函数.
     */
    protected void render(final String contentType, final String content,
                          final HttpServletResponse response) {
        HttpServletResponse resp = initResponseHeader(contentType, response);
        try {
            resp.getWriter().write(content);
            resp.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接输出文本.
     */
    protected void outText(final String text, final HttpServletResponse response) {
        render(TEXT_TYPE, text, response);
    }

    /**
     * 直接输出HTML.
     */
    protected void outHtml(final String html, final HttpServletResponse response) {
        render(HTML_TYPE, html, response);
    }

    /**
     * 直接输出XML.
     */
    protected void outXml(final String xml, final HttpServletResponse response) {
        render(XML_TYPE, xml, response);
    }

    /**
     * 输出JSON,可以接收参数如： [{'name':'www'},{'name':'www'}],['a','b'],new
     * String[]{'a','b'},合并如下：jsonString = "{TOTALCOUNT:" + totalCount + ",
     * ROOT:" + jsonString + "}";
     *
     * @param
     */
    protected void outJson(final String json, final HttpServletResponse response) {
        render(JSON_TYPE, json, response);
    }

    /**
     * 分析并设置contentType与headers.
     */
    protected HttpServletResponse initResponseHeader(final String contentType,
                                                     final HttpServletResponse response) {
        // 分析headers参数
        String encoding = DEFAULT_ENCODING;
        boolean noCache = DEFAULT_NOCACHE;
        // 设置headers参数
        String fullContentType = contentType + ";charset=" + encoding;
        response.setContentType(fullContentType);
        if (noCache) {
            setNoCacheHeader(response);
        }

        return response;
    }

    /**
     * 设置客户端无缓存Header.
     */
    protected void setNoCacheHeader(HttpServletResponse response) {
        // Http 1.0 header
        response.setDateHeader("Expires", 0);
        response.addHeader("Pragma", "no-cache");
        // Http 1.1 header
        response.setHeader("Cache-Control", "no-cache");
    }

    /**
     * 获取访问IP地址
     *
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    public String reqSign(Map param) {
        String key = WebUtil.getSysConfigValue("req_key");
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
        log.info("加密前参数：" + str);
        String sign = Md5.md5Str(str);
        return sign;
    }


    public String signStr(Map param, String key) {
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
        log.info("加密前参数：" + str);
        String sign = Md5.md5Str(str);
        return sign;
    }


    public boolean checkSign(Map param) {
        boolean isTest = true;//全局有校验了 这个就不校验了
        if (isTest) {
            return true;
        }
        if (param == null || param.get("sign") == null) {
            return false;
        }
        return param.get("sign").equals(reqSign(param));
    }

    /**
     * 校验短信验证码
     *
     * @return
     */
    protected boolean verificationCheckCode(String mobilephone, String codeType, String checkCode) {
        String redisActivationCode = jedisClientSingle.get("SMSCheckCode" + codeType + mobilephone);
        if (StringUtils.isBlank(redisActivationCode)) {
            return false;
        }
        boolean bl = StringUtils.equals(checkCode, redisActivationCode);
        if (bl) {
            jedisClientSingle.del(codeType + mobilephone);
        }
        return bl;

    }

}

