package practice.spring;

import java.util.ServiceLoader;

public class spi_test {
    public static void main(String[] args) {
        //classpath目录下建立META-INF
        ServiceLoader<spi_interface> shouts = ServiceLoader.load(spi_interface.class);
        for (spi_interface s : shouts) {
            s.sout();
        }
    }
}
