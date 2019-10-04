package collection;

import java.util.*;

public class map {
    public static void main(String[] args) {
        //Map.put(null, Object)将向集合新增一个Key为null的元素，但只允许存在一个Key为null元素

        //HashMap（由哈希表实现，使用了Hashtable。添加、查询、删除元素的效率都很高）
        //底层数组+链表实现，可以存储null键和null值，线程不安全
        //初始size为16，扩容：newsize = oldsize*2，size一定为2的n次幂
        //扩容针对整个Map，每次扩容时，原来数组中的元素依次重新计算存放位置，并重新插入
        //插入元素后才判断该不该扩容，有可能无效扩容（插入后如果扩容，如果没有再次插入，就会产生无效扩容）
        //当Map中元素总数超过Entry数组的75%，触发扩容操作，为了减少链表长度，元素分配更均匀
        //计算index方法：index = hash & (tab.length – 1)
        HashMap hashMap = new HashMap();

        //LinkedHashMap（由哈希表实现元素的存储，由链表实现元素的顺序。添加、查询、删除元素的效率都高，且元素是有序的）
        LinkedHashMap linkedHashMap = new LinkedHashMap();

        //TreeMap（由二叉树实现。查询效率高，且元素有序的。存放自定义类型的对象需要实现 Comparable接口，重写compareTo方法，提供对象排序的方式）
        TreeMap treeMap = new TreeMap();

        //Hashtable（ 类实现一个哈希表，该哈希表将键映射到相应的值。任何非 null 对象都可以用作键或值。为了成功地在哈希表中存储和获取对象，用作键的对象必须实现 hashCode 方法和 equals 方法）
        //底层数组+链表实现，无论key还是value都不能为null，线程安全，实现线程安全的方式是在修改数据时锁住整个HashTable，效率低，ConcurrentHashMap做了相关优化
        //初始size为11，扩容：newsize = olesize*2+1
        //计算index的方法：index = (hash & 0x7FFFFFFF) % tab.length
        Hashtable hashtable = new Hashtable();
        //无论key还是value都不能为null
        hashtable.put(null, null);
    }


}
