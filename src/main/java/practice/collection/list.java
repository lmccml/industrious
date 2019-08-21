package practice.collection;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

public class list {
    public static void main(String[] args) {
        //List.add(null)将向集合新增一个null元素，允许存在多个null元素
        Vector vector = new Vector();
        vector.add(null);

        ArrayList arrayList = new ArrayList();
        arrayList.add(null);

        LinkedList linkedList = new LinkedList();
        linkedList.add(1);
        linkedList.add(null);
        linkedList.add(null);
        System.out.println(linkedList.get(1));

    }
}
