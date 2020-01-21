package base;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author lmc
 * @date 2020/1/13 14:46
 */
public class BaseTest {
    public static void main(String[] args) {
        float f = 3.333f;//小数默认double双精度，需要加f或者F

        int a = Integer.MAX_VALUE * 2;
        System.out.println(a);

        long aLong = Integer.MAX_VALUE * 2;//默认还是int计算
        System.out.println(aLong);

        long aCorrectLong = Integer.MAX_VALUE * 2L;
        System.out.println(aCorrectLong);

        double d = 10/4;
        System.out.println(d); //2.0,不是2.5

        double dd = 10/4.0; //如果要按小数进行运算，需要将至少一个数表示为小数形式，或者使用强制类型
        System.out.println(dd);

        double ddd = 10/(double)4; //如果要按小数进行运算，需要将至少一个数表示为小数形式，或者使用强制类型
        System.out.println(dd);

        float ff = 0.1f * 0.1f;
        System.out.println(ff); //0.010000001，精度丢失

        double dLost = 0.1 * 0.1;
        System.out.println(dLost); //0.010000000000000002，精度丢失

        BigDecimal bigDecimal = new BigDecimal(0.1);
        BigDecimal bigDecimalCanShow = new BigDecimal(0.5);//0.5恰好可以用二进制表示2的负一次方就是0.5
        BigDecimal bigDecimalCorrect = new BigDecimal("0.1");
        System.out.println(bigDecimal);//0.1000000000000000055511151231257827021181583404541015625
        System.out.println(bigDecimalCanShow); //0.5
        System.out.println(bigDecimalCorrect);//0.1

        int j = 0;
        int jj;
        jj = j++ - 1;
        System.out.println(jj); //-1,先j -1 ，然后j++

        int k = 0;
        int kk;
        kk = ++k - 1;
        System.out.println(kk); //0 ,先++k，然后k-1

        int[] arrA = new int[]{4,5,6};
        int[] arrB = new int[]{1,2,3};
        int arri = 0;
        int arrj = 0;
        arrA[arri++] = arrB[++arrj]; //++arrj  arrA[0] = arrB[1] arri++
        System.out.println(Arrays.toString(arrA));//[2, 5, 6]
        System.out.println(Arrays.toString(arrB));//[1, 2, 3]

        int iTemp = 1;
        boolean booli = true | iTemp++ > 0;
        System.out.println(iTemp); //2

        int jTemp = 1;
        boolean boolj = true || jTemp++ > 0; //会短路，后面的不会执行
        System.out.println(jTemp); //1

        int select = 5;
        switch (select) {
            case 1:
                System.out.println(1);break;//记住一定要break
            case 2:
                System.out.println(2);break;
            case 3:
                System.out.println(3);break;
            case 4:
                System.out.println(4);break;
            case 5:
                System.out.println(5);
            case 6:
                System.out.println(6);
            default:
                System.out.println("default");
        }

        switch (select) {
            case 1:
                System.out.println(1);break;//记住一定要break
            case 2:
                System.out.println(2);break;
            case 3:
                System.out.println(3);break;
            case 4:
                System.out.println(4);break;
            case 5:
                System.out.println(5);
            case 6:
                System.out.println(6);break;//一定要，不然default会被打印
            default:
                System.out.println("default");
        }

        for(;;){
            System.out.println("分号不能省");
            break;
        }

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(3);
        arrayList.add(13);
        arrayList.add(18);
        arrayList.add(5);
        arrayList.add(1);
        Iterator<Integer> iterator = arrayList.iterator();
        //只读模式访问
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        Iterator<Integer> iterator2 = arrayList.iterator();

        while(iterator2.hasNext()) {
            iterator2.next();
            iterator2.remove();
            //该字段表示list结构上被修改的次数。结构上的修改指的是那些改变了list的长度大小或者使得遍历过程中产生不正确的结果的其它方式。
            //该字段被Iterator以及ListIterator的实现类所使用，如果该值被意外更改，Iterator或者ListIterator 将抛出ConcurrentModificationException异常，
            //这是jdk在面对迭代遍历的时候为了避免不确定性而采取的快速失败原则。
            //子类对此字段的使用是可选的，如果子类希望支持快速失败，只需要覆盖该字段相关的所有方法即可。单线程调用不能添加删除terator正在遍历的对象，
            //否则将可能抛出ConcurrentModificationException异常，如果子类不希望支持快速失败，该字段可以直接忽略。
            //arrayList.remove(iterator.next()); //注意it.remove()删除的是最近的一次it.next()获取的元素，而不是当前iterator中游标指向的元素！！
            //说来说去就是迭代的时候，不能用list去增删，list结构变化了就会导致迭代器的索引失效。迭代器自身删除会检查结构性变化，同步modCount

        }
        System.out.println(arrayList);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(3);
        queue.add(5);
        queue.add(1);
        while (null != queue.peek()) {
            System.out.println(queue.poll());
        }

        Deque<Integer> deque = new LinkedList<>();
        deque.push(3);
        deque.push(5);
        deque.push(1);

        while (deque.size() > 0) {
            System.out.println(deque.poll());
        }

        Deque<Integer> deque2 = new ArrayDeque<>();
        deque2.push(3);
        deque2.push(5);
        deque2.push(1);
        //集合方式遍历，元素不会被移除
        for (Integer x : deque) {
            System.out.println(x);
        }
        //队列方式遍历，元素逐个被移除
        while (null != deque2.peek()) {
            System.out.println(deque2.poll());
        }

        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("a", "aa");
        treeMap.put("b", "bb");
        treeMap.put("T", "TT");
        //先输出T，因为T在ASCLL码排前
        for(String str : treeMap.keySet()) {
            System.out.println(str);
        }

        //也可以达到同样效果
        TreeMap<String, String> treeMap3 = new TreeMap<>(Collections.reverseOrder());
        //忽略大小写（也会去重T和t属于一个）
        TreeMap<String, String> treeMap4 = new TreeMap<>(Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
        TreeMap<String, String> treeMap2 = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        treeMap2.put("a", "aa");
        treeMap2.put("b", "bb");
        treeMap2.put("T", "TT");
        //先输出b
        for(String str : treeMap2.keySet()) {
            System.out.println(str);
        }

        EnumMap enumMap = new EnumMap(enum_to_use.Season.class);
        enumMap.put(enum_to_use.Season.SPRING, "1");
        enumMap.put(enum_to_use.Season.SUMMER, "2");
        enumMap.put(enum_to_use.Season.AUTUMN, "3");
        enumMap.put(enum_to_use.Season.WINTER, "4");
        enumMap.put(enum_to_use.Season.SPRING, "2");//会替换1
        System.out.println(enumMap);

        //Set接口的实现类HashSet/TreeSet,它们内部都是用对应的HashMap/TreeMap实现的，
        // 但EnumSet不是，它的实现与EnumMap(数组实现值)没有任何关系，而是用极为精简和高效的位向量实现的。
        EnumSet<rankEnum> enumSet = EnumSet.noneOf(rankEnum.class);
        enumSet.add(rankEnum.Gold);
        enumSet.add(rankEnum.Silver);
        enumSet.add(rankEnum.Bronze);
        enumSet.add(rankEnum.Gold);
        System.out.println(rankEnum.valueOf("Gold").getRemark());
        System.out.println(enumSet);

        BitSet bitSet = new BitSet(Integer.MAX_VALUE); //定义其值域,但要考虑内存消耗哦
        bitSet.set(0);
        bitSet.set(2);
        bitSet.set(3);
        System.out.println(bitSet.get(1));//false，那么数据一定是不存在；但是如果结果为true，可能数据存在
        System.out.println(bitSet.get(2));

        //实现了堆排序的队列
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        










    }
}
