package practice;

/**
 * @author lmc
 * @date 2020/1/8
 */
public class BItTest {
    private static int execute = 1;
    private static int write = 1 << 1;
    private static int read = 1 << 2;

    private static int oldNum = 5;
    private static int newNum = 8;

    public static void main(String[] args) {
        System.out.println(BItTest.canRead(7)); //true 1+2+4
        System.out.println(BItTest.canRead(2)); //false 2

        System.out.println(BItTest.addWrite(1)); //3

        System.out.println(BItTest.delExecute(7)); //6

        BItTest.swap();
        System.out.println(oldNum);
        System.out.println(newNum);

        System.out.println(BItTest.getSingleDog());

        System.out.println(BItTest.setZero(5));

        System.out.println(BItTest.isEven(5));
        System.out.println(BItTest.isEven(6));

    }

    static boolean canRead(int verifyNum) {
        return (BItTest.read & verifyNum) == BItTest.read;
    }

    static int addWrite(int rawNum) {
        return BItTest.write | rawNum;
    }

    static int delExecute(int rawNum) {
        return BItTest.execute ^ rawNum;
    }

    static void swap(){
        oldNum = oldNum ^ newNum;
        newNum = oldNum ^ newNum;
        oldNum = oldNum ^ newNum;
    }

    static int getSingleDog(){
      return 1^2^3^2^1;
    }

    static int setZero(int rawNum){
        return rawNum ^ rawNum;
    }

    /*
    是否偶数
     */
    static boolean isEven(int rawNum) {
        if (((rawNum & 1) == 0)) {
            return true;
        }
        return false;
    }


}
