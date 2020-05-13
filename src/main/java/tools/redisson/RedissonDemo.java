package tools.redisson;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

import java.io.File;
import java.io.IOException;

/**
 * @author lmc
 * @date 2020/5/10 13:12
 */
public class RedissonDemo {
    public static void main(String[] args) throws IOException {
        // 1. Create config object
        Config config = new Config();
        config.useClusterServers()
                // use "rediss://" for SSL connection
                .addNodeAddress("redis://127.0.0.1:7181");

        // or read config from file
        config = Config.fromYAML(new File("config-file.yaml"));

        // 2. Create Redisson instance

        // Sync and Async API
        RedissonClient redisson = Redisson.create(config);

        // Reactive API
        RedissonReactiveClient redissonReactive = Redisson.createReactive(config);

        // RxJava2 API
        RedissonRxClient redissonRx = Redisson.createRx(config);

        // 3. Get Redis based Map
        RMap<String, Object> map = redisson.getMap("myMap");

        RMapReactive<String, Object> mapReactive = redissonReactive.getMap("myMap");

        RMapRx<String, Object> mapRx = redissonRx.getMap("myMap");

        // 4. Get Redis based Lock
        RLock lock = redisson.getLock("myLock");

        RLockReactive lockReactive = redissonReactive.getLock("myLock");

        RLockRx lockRx = redissonRx.getLock("myLock");

        // 4. Get Redis based ExecutorService
        RExecutorService executor = redisson.getExecutorService("myExecutorService");

        // over 50 Redis based Java objects and services ...
    }

}
