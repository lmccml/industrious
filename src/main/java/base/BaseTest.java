package base;

import java.math.BigDecimal;
import java.util.Arrays;

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





    }
}
