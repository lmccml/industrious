package tools;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 请求参数工具类
 */
public class ParamUtil {

    /**
     * url中url部分和 参数的分隔符
     **/
    public static final String URL_AND_PARA_SEPARATOR = "?";
    /**
     * url中 参数之间的分隔符
     **/
    public static final String PARAMETERS_SEPARATOR = "&";
    /**
     * url中路径之间的分隔符
     **/
    public static final String PATHS_SEPARATOR = "/";
    /**
     * url中参数名和参数值分隔符，即等号
     **/
    public static final String EQUAL_SIGN = "=";
    /**
     * http get 请求方式
     **/
    public static String HTTP_GET_METHOD = "get";
    /**
     * http post 请求方式
     **/
    public static String HTTP_POST_METHOD = "post";

    /**
     * 创建参数对
     *
     * @param key
     * @param value
     * @return
     */
    public static String createParameter(String key, String value) {
        return createParameter(key, value, false);
    }

    /**
     * 创建参数对
     *
     * @param key     参数名
     * @param value   参数值
     * @param isFirst 是否是第一个参数
     * @return
     */
    public static String createParameter(String key, String value,
                                         boolean isFirst) {
        if (StringUtils.isEmpty(key)) {
            key = "";
        }
        if (StringUtils.isEmpty(value)) {
            value = "";
        }
        return (isFirst ? (key + EQUAL_SIGN + value) : (PARAMETERS_SEPARATOR
                + key + EQUAL_SIGN + value));
    }

    /**
     * 得到带参数的url
     *
     * @param url      url
     * @param parasMap 参数map,value为参数名，value为参数值
     * @return
     */
    public static String getUrlWithParas(String url, Map<String, String> parasMap) {
        StringBuilder urlWithParas = new StringBuilder(StringUtils.isEmpty(url) ? "" : url);
        String paras = getParas(parasMap);
        if (!StringUtils.isEmpty(paras)) {
            urlWithParas.append(URL_AND_PARA_SEPARATOR).append(paras);
        }
        return urlWithParas.toString();
    }

    /**
     * 得到带有序参数串的url
     *
     * @param url      url
     * @param parasMap 参数map,value为参数名，value为参数值
     * @return
     */
    public static String getUrlWithOrderedParas(String url, Map<String, String> parasMap) {
        StringBuilder urlWithParas = new StringBuilder(StringUtils.isEmpty(url) ? "" : url);
        String paras = getOrderedParas(parasMap);
        if (!StringUtils.isEmpty(paras)) {
            urlWithParas.append(URL_AND_PARA_SEPARATOR).append(paras);
        }
        return urlWithParas.toString();
    }

    /**
     * 得到参数串
     *
     * @param parasMap 参数map,value为参数名，value为参数值
     * @return
     */
    public static String getParas(Map<String, String> parasMap) {
        StringBuilder paras = new StringBuilder("");
        if (parasMap != null && parasMap.size() > 0) {
            Iterator<Map.Entry<String, String>> ite = parasMap.entrySet().iterator();
            while (ite.hasNext()) {
                Map.Entry<String, String> entry = ite.next();
                if (StringUtils.isNotBlank(entry.getValue())) {
                    paras.append(entry.getKey()).append(EQUAL_SIGN).append(entry.getValue());
                    if (ite.hasNext()) {
                        paras.append(PARAMETERS_SEPARATOR);
                    }
                }
            }
        }
        if (PARAMETERS_SEPARATOR.equals(String.valueOf(paras.charAt(paras
                .length() - 1)))) {
            paras.deleteCharAt(paras.length() - 1);
        }
        return paras.toString();
    }

    /**
     * 得到有序的参数串
     *
     * @param parasMap 参数map,value为参数名，value为参数值
     * @return
     */
    public static String getOrderedParas(Map<String, String> parasMap) {
        if (parasMap == null || parasMap.size() == 0) {
            return "";
        }
        TreeMap<String, String> orderedParasMap = new TreeMap<>(parasMap);
        return getParas(orderedParasMap);
    }

    /**
     * 解析字符串得到key和value对应的map
     *
     * @param paras 字符串
     * @return
     */
    public static Map<String, String> getParasMap(String paras) {
        return getParasMap(paras, PARAMETERS_SEPARATOR);
    }

    /**
     * 解析字符串得到key和value对应的map
     *
     * @param paras               字符串
     * @param parametersSeparator 参数分隔符
     * @return
     */
    public static Map<String, String> getParasMap(String paras, String parametersSeparator) {
        if (StringUtils.isEmpty(paras)) {
            return null;
        }
        if (StringUtils.isEmpty(parametersSeparator)) {
            parametersSeparator = PARAMETERS_SEPARATOR;
        }
        Map<String, String> parasMap = new HashMap<String, String>();
        String[] parasArray = paras.split(parametersSeparator);
        int keyAndValueIndex = 0;
        for (String para : parasArray) {
            keyAndValueIndex = para.indexOf(EQUAL_SIGN);
            if (keyAndValueIndex != -1) {
                parasMap.put(para.substring(0, keyAndValueIndex), para.substring(keyAndValueIndex + 1));
            }
        }
        return parasMap;
    }

    /**
     * url utf8进行编码
     *
     * @param url 原字符
     * @return
     */
    public static String utf8Encode(String url) {
        if (!StringUtils.isEmpty(url)) {
            try {
                return URLEncoder.encode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return url;
    }

    /**
     * url utf8进行解码
     *
     * @param url 原字符
     * @return
     */
    public static String utf8Decode(String url) {
        if (!StringUtils.isEmpty(url)) {
            try {
                return URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return url;
    }

    /**
     * 根据key得到url中的参数值
     *
     * @param url url
     * @param key 参数key
     * @return
     */
    public static String getQueryParameter(String url, String key) {
        return getQueryParameter(url, key, URL_AND_PARA_SEPARATOR, PARAMETERS_SEPARATOR);
    }

    /**
     * 根据key得到url中的参数值
     *
     * @param url               url
     * @param key               参数key
     * @param paraSeparator     参数之间分隔符
     * @param pathParaSeparator url path和参数之间分隔符
     * @return
     */
    public static String getQueryParameter(String url, String key, String pathParaSeparator, String paraSeparator) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(url)) {
            return null;
        }
        if (url.indexOf(pathParaSeparator) >= 0) {
            String paraPart = url.substring(url.indexOf(pathParaSeparator) + 1);
            if (!StringUtils.isEmpty(paraPart)) {
                String keyWithEqualSign = key + EQUAL_SIGN;
                String[] paraArray = paraPart.split(paraSeparator);
                for (String paraInfo : paraArray) {
                    if (paraInfo.equals(key) || paraInfo.equals(keyWithEqualSign)) {
                        return "";
                    } else if (paraInfo.indexOf(keyWithEqualSign) >= 0) {
                        return paraInfo.substring(paraInfo.indexOf(keyWithEqualSign) + keyWithEqualSign.length());
                    }
                }
            }
        }
        return null;
    }

    /**
     * 返回一个url的非query部分
     *
     * @param url
     * @return
     */
    public static String getUrlPath(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        if (url.indexOf(URL_AND_PARA_SEPARATOR) >= 0) {
            return url.substring(0, url.indexOf(URL_AND_PARA_SEPARATOR));
        }
        return url;
    }

    /***
     * 将“a=1&b=2&c=3”格式的字符串转成JSON字符串
     * @param responseStr 格式要满足“a=1&b=2&c=3”
     * @return
     */
    public static String getJSONStringByStr(String responseStr) {
        JSONObject tmp = new JSONObject();
        String[] strs = responseStr.split("&");
        String[] keyValue;
        for (String str : strs) {
            keyValue = str.split("=");
            tmp.put(keyValue[0], keyValue.length == 2 ? keyValue[1] : null);
        }
        return tmp.toJSONString();
    }
}
