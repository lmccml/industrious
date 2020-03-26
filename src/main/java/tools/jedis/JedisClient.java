package tools.jedis;

import java.util.Map;

public interface JedisClient {
	
	//字符串取值和设置
	String get(String key);
	String set(String key, String value);
	
	//hash取值和设置
	String hget(String hkey, String key);
	long hset(String hkey, String key, String value);
	
	//自增key的值
	long incr(String key);
	//为key设置过期时间，秒
	long expire(String key, int second);
	//获取key的过期时间，秒
	long ttl(String key);
	//删除字符串对应的key
	long del(String key);
	//删除hash对应的key
	long hdel(String hkey, String key);
	long hset(String hkey, Map<String,String> map,int second);
	long hset(String hkey, String key, String value,int second);
	Map<String,String> hget(String hkey);
	boolean exists(String key);
	String set(String key, String value, int second);
}
