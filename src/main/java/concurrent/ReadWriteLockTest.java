package concurrent;

import java.util.Random;

/**
 * @author lmc
 * @date 2019/12/30
 */
public class ReadWriteLockTest {
    public static void main(String[] args) {
        final ReadWriteObject object = new ReadWriteObject();
        for (int i = 0; i < 5; i++) {
            new Thread() {
                public void run() {
                    while (true) {
                        object.read();
                    }
                }
                ;
            }.start();

            for (int j = 0; j < 5; j++) {
                new Thread() {
                    public void run() {
                        while (true) {
                            object.write(new Random().nextInt(10000));
                        }
                    };
                }.start();
            }
        }

    }
}