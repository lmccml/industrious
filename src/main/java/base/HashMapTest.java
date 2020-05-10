package base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lmc
 * @date 2020/4/4 17:19
 */
public class HashMapTest {
    //HashMap的四种遍历方式
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "value1");
        map.put("2", "value2");
        map.put("3", "value3");
        map.put("4", "value4");

        //第一种   通过Map.entrySet遍历,推荐使用,尤其是容量大时
        System.out.println("通过Map.entrySet遍历key和value: ");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " - Value: " + entry.getValue());
        }

        //第二种   通过Map.entrySet使用iterator遍历
        System.out.println("\n通过Map.entrySet使用iterator遍历key和value: ");
        Iterator map1it = map.entrySet().iterator();
        while (map1it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) map1it.next();
            System.out.println("Key: " + entry.getKey() + " - Value: " + entry.getValue());
        }

        //第三种   通过Map.keySet遍历,二次取值
        System.out.println("\n通过Map.keySet遍历key和value: ");
        for (String key : map.keySet()) {
            System.out.println("Key: " + key + " - Value: " + map.get(key));
        }

        //第四种   通过Map.values()遍历
        System.out.println("\n通过Map.values()遍历所有的value,但不能遍历key: ");
        for (String v : map.values()) {
            System.out.println("The value is " + v);
        }
    }
}
