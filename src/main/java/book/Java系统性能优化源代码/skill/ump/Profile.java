package book.Java系统性能优化源代码.skill.ump;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Profile {

  static Map<Integer, AtomicInteger> countMap = new ConcurrentHashMap<>();

  /**
   * 对调用时间计数
   * @param watch
   */
  public static void addWatch(Watch watch){
    int consumeTime = (int)watch.millis;
    AtomicInteger  count  = countMap.get(consumeTime);
    if(count==null){
      count = new AtomicInteger();
      AtomicInteger old  = countMap.putIfAbsent(consumeTime,count);
      if(old!=null){
        count = old;
      }
    }
    count.incrementAndGet();

  }

  public static  void init(){
	  countMap.clear();
  }


}
