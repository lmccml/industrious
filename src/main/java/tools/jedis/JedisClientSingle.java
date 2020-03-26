package tools.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

public class JedisClientSingle implements JedisClient {

	@Autowired
	private JedisPool jedisPool;

	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.get(key);
		jedis.close();
		return string;
	}

	@Override
	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.set(key, value);
		jedis.close();
		return string;
	}
	@Override
	public String set(String key, String value,int second) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.set(key, value);
		if(second>0){
			jedis.expire(key, second);
		}
		jedis.close();
		return string;
	}

	@Override
	public String hget(String hkey, String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.hget(hkey, key);
		jedis.close();
		return string;
	}

	@Override
	public long hset(String hkey, String key, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hset(hkey, key, value);
		jedis.close();
		return result;
	}
	@Override
	public long hset(String hkey, String key, String value,int second) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hset(hkey, key, value);
		if(second>0){
			jedis.expire(hkey, second);
		}
		jedis.close();
		return result;
	}
	@Override
	public long hset(String hkey, Map<String,String> map,int second) {
		if(map==null||map.isEmpty()) {
			return 0L;
		}
		Jedis jedis = jedisPool.getResource();
		for(Map.Entry<String,String> key:map.entrySet()){
			jedis.hset(hkey,key.getKey(),key.getValue());
		}
		if(second>0){
			jedis.expire(hkey, second);
		}
		jedis.close();
		return 1;
	}

	@Override
	public Map<String,String> hget(String hkey) {
		Jedis jedis = jedisPool.getResource();
		Map<String, String> res = jedis.hgetAll(hkey);
		jedis.close();
		return res;
	}

	@Override
	public long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	@Override
	public long expire(String key, int second) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.expire(key, second);
		jedis.close();
		return result;
	}

	@Override
	public long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

	@Override
	public long del(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.del(key);
		jedis.close();
		return result;
	}

	@Override
	public long hdel(String hkey, String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hdel(hkey, key);
		jedis.close();
		return result;
	}
	@Override
	public boolean exists(String key) {
		Jedis jedis = jedisPool.getResource();
		boolean result = jedis.exists(key);
		jedis.close();
		return result;
	}
}
