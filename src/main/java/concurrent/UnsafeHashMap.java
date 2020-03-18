package concurrent;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author lmc
 * @date 2020/3/18 11:38
 */
public class UnsafeHashMap {
    public static void main(String[] args) throws Exception{
        //jdk1.8之前和之后不同之处就是jdk1.8后是直接把节点放到newtable[j]尾节点，而jdk1.8前是直接放到头节点。
        // 虽然解决了死循环，hashMap在多线程使用下还是会有很多问题，在多线程下最好还是使用ConcurrentHashMap比较好。
        HashMap<String, String> map = new HashMap<String, String>(2);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            map.put(UUID.randomUUID().toString(), "");
                        }
                    }, "ftf" + i).start();
                }
            }
        }, "ftf");
        t.start();
        t.join();
    }
}
