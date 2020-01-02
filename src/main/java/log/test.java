package log;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author lmc
 * @date 2020/1/1
 */
@Slf4j
public class test {
    public static void main(String[] args) {
        log.info("this is logback test " + LocalDateTime.now());
    }
}
