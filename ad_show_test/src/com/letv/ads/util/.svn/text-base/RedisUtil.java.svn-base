package com.letv.ads.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 操作Redis数据库
 * @author haoxiaosha
 *
 */
public class RedisUtil {
		
	private volatile static RedisUtil redisUntil;
	private static JedisPool pool;
	private static String redisIp = Constant.TEST_SERVER;  //从config.properties中读取
	private static int redisPort = Constant.REDIS_PORT;
	private static Jedis jedis;

	/**
	 * 类初始化
	 * 
	 * @return
	 */
	public static RedisUtil getInstance() {
		if (null == redisUntil) {
			synchronized (RedisUtil.class) {
				if (null == redisUntil) {
					redisUntil = new RedisUtil();
					pool = getPool(redisIp,redisPort);
					jedis = pool.getResource();
				}
			}
		}
		return redisUntil;
	}	
		
	/**
	 * 获取redis连接池
	 * 
	 * @return
	 */
	public synchronized static JedisPool getPool(String redisIp, int redisPort) {
		if (null == pool || pool.isClosed()) {
			JedisPoolConfig config = new JedisPoolConfig();
			// 控制一个pool可分配多少个j edis实例，通过pool.getResource()来获取；
			// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
			config.setMaxTotal(5000);
			// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
			config.setMaxIdle(500);
			// 初始化连接数
			config.setMinIdle(20);
			// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
			config.setMaxWaitMillis(1000 * 10);
			// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
			config.setTestOnBorrow(false);
			pool = new JedisPool(config, redisIp, redisPort, 1000 * 10);
		}
		return pool;
	}
	

	/**
	 * 释放资源 createBy
	 * 
	 * @param pool
	 * @param redis
	 */
	public void returnResource() {
		if (null != jedis) {
			pool.returnResourceObject(jedis);
		}
	}

	/**
	 * 设置redis值（rk ，rv）
	 * 
	 * @param redis
	 * @param rk
	 * @param rv
	 * @return
	 */
	public String setRedisValue(String rk, String rv) {
		String msg = "ok";
		try {
			jedis.set(rk, rv);
		} catch (Exception e) {
			msg = e.getMessage();
		}
		return msg;
	}
	
	/**
	 * 批量设置redis值（rk ，rv）
	 * 
	 * @param redis
	 * @param rk
	 * @param rv
	 * @return
	 */
	public String multiSetRedisValue(String...keysvalues) {
		String msg = "ok";
		try {
			jedis.mset(keysvalues);
		} catch (Exception e) {
			msg = e.getMessage();
		}
		return msg;
	}

	
	/**
	 * 查询redis值（rk ，rv）
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public String getRedisValue(String rk) {
		String value = null;
		try {
			value = jedis.get(rk);
		} catch (Exception e) {

		}
		return value;
	}
	
	/**
	 * 批量查询redis值（rk ，rv）
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public List<String> muliGetRedisValue(String...rks) {
		List<String> values = null;
		try {
			 values = jedis.mget(rks);
		} catch (Exception e) {

		}
		return values;
	}

	/**
	 * 删除redis值（rk ，rv）
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public String deleteRedisValue(String... rks) {
		String msg = "ok";
		try {
			jedis.del(rks);
			jedis.flushDB();
		} catch (Exception e) {
			msg = e.getMessage();
		}
		return msg;
	}
	
	/**
	 * 删除redis所有key
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public String deleteAllKeys() {
		String msg = "ok";
		try {
			jedis.flushDB();
		} catch (Exception e) {
			msg = e.getMessage();
		}
		return msg;
	}

	/**
	 * 检查redis的key值是否存在（rk ，rv）
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public boolean isExistsKey(String rk){
		return jedis.exists(rk);
	}

	/**
	 * hset设置redis值（rk ，hk，rv）
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public String setRedisValueByHash(String rk, String hk,
			String hv) {
		String msg = "ok";
		try {
			jedis.hset(rk, hk, hv);
		} catch (Exception e) {
			msg = e.getMessage();
		}
		return msg;
	}
	
	/**
	 * 批量设置redis值（rk ，hk，rv
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public String multiSetRedisValueByHash(String rk, Map<String,String> hKValues) {
		String msg = "ok";
		try {
			jedis.hmset(rk, hKValues);
		} catch (Exception e) {
			msg = e.getMessage();
		}
		return msg;
	}

	/**
	 * 获取redis值（rk ，hk，rv）
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public String getRedisValueByHash(String rk, String hk) {
		String value = null;
		try {
			value = jedis.hget(rk, hk);
		} catch (Exception e) {

		}
		return value;
	}

	/**
	 * 批量获取redis的hk，hv（rk ，hk，rv）
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public Map<String, String> mutiGetRedisKVsByHash(String rk) {
		Map<String, String> values = null;
		try {
			values = jedis.hgetAll(rk);
		} catch (Exception e) {

		}
		return values;
	}

	/**
	 * 批量获取redis的hv（rk ，hk，rv）
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public List<String> mutiGetRedisValuesByHash(String rk) {
		List<String> values = null;
		try {
			values = jedis.hvals(rk);
		} catch (Exception e) {

		}
		return values;
	}

	/**
	 * 批量获取redis的hk（rk ，hk，rv）
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public Set<String> mutiGetRedisKeysByHash( String rk) {
		Set<String> keys = null;
		try {
			keys = jedis.hkeys(rk);
		} catch (Exception e) {

		}
		return keys;
	}

	/**
	 * 删除redis的hv（rk ，hk，rv）
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public String delRedisValueByHash(String rk, String hk) {
		String msg = "ok";
		try {
			jedis.hdel(rk, hk);
		} catch (Exception e) {
			msg = e.getMessage();
		}
		return msg;
	}

	/**
	 * 检测redis的hk是否存在（rk ，hk，rv）
	 * 
	 * @param redis
	 * @param rk
	 * @return
	 */
	public boolean isHexists(String rk, String hk) {
		return jedis.hexists(rk, hk);
	}
}
