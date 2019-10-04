package collection;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class set {
    public static void main(String[] args) {
        //Set.add(null)将向集合新增一个null元素，但只允许存在一个null元素,线程都不安全

        //HashSet（由哈希表实现，使用了Hashtable。添加、查询、删除元素的效率都很高，缺点是元素无序。通过hashcode与equals方法确保元素的唯一）
        HashSet hashSet = new HashSet();
        hashSet.add(null);
        hashSet.add(null);
        System.out.println(hashSet.size());

        //LinkedHashSet（由哈希表实现元素的存储，由链表实现元素的顺序。添加、查询、删除元素的效率都高，且元素是有序的）
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.add(null);

        //TreeSet（由二叉树实现。查询效率高，且元素有序的。存放自定义类型的对象需要实现 Comparable接口，重写compareTo方法，提供对象排序的方式）
        TreeSet treeSet = new TreeSet();
        treeSet.add(null);



    }
}
