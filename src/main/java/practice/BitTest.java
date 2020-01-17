package practice;

/**
 * @author lmc
 * @date 2020/1/8
 */
public class BitTest {
    private static int execute = 1;
    private static int write = 1 << 1;
    private static int read = 1 << 2;

    private static int oldNum = 5;
    private static int newNum = 8;

    public static void main(String[] args) {
        System.out.println(BitTest.canRead(7)); //true 1+2+4
        System.out.println(BitTest.canRead(2)); //false 2

        System.out.println(BitTest.addWrite(1)); //3

        System.out.println(BitTest.delExecute(7)); //6

        BitTest.swap();
        System.out.println(oldNum);
        System.out.println(newNum);

        System.out.println(BitTest.getSingleDog());

        System.out.println(BitTest.setZero(5));

        System.out.println(BitTest.isEven(5));
        System.out.println(BitTest.isEven(6));

    }

    static boolean canRead(int verifyNum) {
        return (BitTest.read & verifyNum) == BitTest.read;
    }

    static int addWrite(int rawNum) {
        return BitTest.write | rawNum;
    }

    static int delExecute(int rawNum) {
        return BitTest.execute ^ rawNum;
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
