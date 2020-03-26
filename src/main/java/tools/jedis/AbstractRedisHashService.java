package tools.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis Hash结构 基础服务类
 *
 */
@Service
public abstract class AbstractRedisHashService<T> {

	/**
	 * 注入RedisTemplate对象
	 */
	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;

	/**
	 * 注入Hash结构操作存储对象
	 */
	@Resource
	protected HashOperations<String, String, T> hashOperations;

	/**
	 * 定义Hash结构的redis key名称 留给子类实现
	 *
	 * @return
	 */
	protected abstract String getRedisKey();

	/**
	 * 在hash结构中添加键值对
	 * 
	 * @param key
	 * @param doamin
	 * @param expire
	 *            过期时间(单位:秒),传入 -1 时表示不设置过期时间
	 */
	public void put(String key, T doamin, long expire) {
		hashOperations.put(getRedisKey(), key, doamin);
		if (expire != -1) {
			redisTemplate.expire(getRedisKey(), expire, TimeUnit.SECONDS);
		}
	}

	/**
	 * 删除hash结构中 key名称的值
	 * 
	 * @param key
	 */
	public void remove(String key) {
		hashOperations.delete(getRedisKey(), key);
	}

	/**
	 * 在相应Hash表中查询key名称的值
	 * 
	 * @param key
	 * @return
	 */
	public T get(String key) {
		return hashOperations.get(getRedisKey(), key);
	}

	/**
	 * 获取hash结构所有值
	 *
	 * @return
	 */
	public List<T> getAll() {
		return hashOperations.values(getRedisKey());
	}

	/**
	 * 查询在相应Hash表下的所有key名称
	 *
	 * @return
	 */
	public Set<String> getKeys() {
		return hashOperations.keys(getRedisKey());
	}

	/**
	 * 判断在相应Hash表下key是否存在
	 *
	 * @param key
	 * @return
	 */
	public boolean isKeyExists(String key) {
		return hashOperations.hasKey(getRedisKey(), key);
	}

	/**
	 * 查询相应Hash表的缓存数量
	 *
	 * @return
	 */
	public long count() {
		return hashOperations.size(getRedisKey());
	}

	/**
	 * 清空相应Hash表的所有数据
	 */
	public void empty() {
		Set<String> set = hashOperations.keys(getRedisKey());
		set.stream().forEach(key -> hashOperations.delete(getRedisKey(), key));
	}
}