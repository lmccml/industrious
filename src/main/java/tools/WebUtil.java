package tools;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WebUtil {

    public static String getKey(String seqName) {
        return "";
    }

    /**
     * %012d
     *
     * @param seqName 序列名称
     * @param format  String.format格式
     * @return
     */
    public static String createKey(String seqName, String format) {
        String num = getKey(seqName);
        return String.format(format, new BigInteger(num));
    }

    /**
     * @param seqName      序列名称
     * @param defaultValue 默认值
     * @return @description 90000000000000000000+120000=90000000000000120000
     */
    public static String createKey(String seqName, BigInteger defaultValue) {
        String num = getKey(seqName);
        BigInteger big = new BigInteger(num).add(defaultValue);
        return big.toString();
    }

    /**
     * @param seqName 序列名称
     * @return 自增长的序列，灵活格式化
     */
    public static String createKey(String seqName) {
        return getKey(seqName);
    }

    /**
     * 随机生成全球唯一UUID
     *
     * @return
     */
    public static String genUUID() {

        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    /**
     * 获取数据字典值
     *
     * @return
     */
    public static String getSysConfigValue(String key) {
        return null;
    }

    /**
     * 获取数据字典值
     *
     * @return
     */
    public static List<Map<String,Object>> getNpospSysDictValues(String key,String colums) {
        return null;
    }

}

