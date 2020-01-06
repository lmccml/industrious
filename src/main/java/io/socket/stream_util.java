package io.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;

@Slf4j
public class stream_util {
    public static void close(Closeable p){
        if(null == p){
            return;
        }
        try {
            p.close();
        } catch (IOException e) {
            log.error("Error on close {}", p.getClass().getName() , e);
        }
    }
}
