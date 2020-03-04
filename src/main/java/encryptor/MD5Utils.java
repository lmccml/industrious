package encryptor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author lmc
 * @date 2020/3/4 11:16
 */
public class MD5Utils {
    public static String MD5Encode(String strSrc) {
        return MD5Encode(strSrc, "",null);
    }
    public static String MD5Encode(String strSrc,String charset) {
        return MD5Encode(strSrc, "",charset);
    }

    /**
     * 27 md5加密产生，产生128位（bit）的mac 28 将128bit Mac转换成16进制代码 29
     *
     * @param strSrc
     *            30
     * @param key
     *            31
     * @return 32
     */
    public static String MD5Encode(String strSrc, String key,String charset) {
        try {
            if (null == charset || "".equals(charset)) {
                charset = "UTF-8";
            }
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(strSrc.getBytes(charset));
            StringBuilder result = new StringBuilder(32);
            byte[] temp;
            temp = md5.digest(key.getBytes(charset));
            for (int i = 0; i < temp.length; i++) {
                result.append(Integer.toHexString(
                        (0x000000ff & temp[i]) | 0xffffff00).substring(6));
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public static void main(String[] args) throws Exception {
        String aaa = "00000";
        String mac128byte2 = MD5Encode("279111000171698", "testmd5","UTF-8");
        System.out.println("md5加密结果32 bit------------->:" + mac128byte2);
    }
}
