package com.letv.ads.util;

import java.util.Date;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * Memcached工具类
 * 
 * @author haoxiaosha
 */
public class MemCachedUtil {

	private volatile static MemCachedUtil memCachedUtil;
	private static SockIOPool pool;
	private static MemCachedClient memCachedClient;
	private static String server = Constant.TEST_SERVER; // 从config.properties中读取
	private static int port = Constant.MEMCACHED_PORT;

	private MemCachedUtil() {
	}

	/**
	 * 类初始化
	 * 
	 * @return
	 */
	public static MemCachedUtil getInstance() {
		if (null == memCachedUtil) {
			synchronized (MemCachedUtil.class) {
				if (null == memCachedUtil) {
					memCachedUtil = new MemCachedUtil();
					pool = getPool(server, port);
				}
			}
		}
		return memCachedUtil;
	}

	/**
	 * 获取sock连接池
	 * 
	 * @return
	 */
	public synchronized static SockIOPool getPool(String server, int port) {
		if (null == pool) {
			/************************************ 配置Memcached **************************************/
			pool = SockIOPool.getInstance();
			pool.setServers(new String[] { server + ":" + port });// 设置memcached服务器地址
			pool.setWeights(new Integer[] { 3 }); // 设置每个MemCached服务器权重
			pool.setFailover(true); // 当一个memcached服务器失效的时候是否去连接另一个memcached服务器.
			pool.setInitConn(10); // 初始化时对每个服务器建立的连接数目
			pool.setMinConn(10); // 每个服务器建立最小的连接数
			pool.setMaxConn(100); // 每个服务器建立最大的连接数
			pool.setMaintSleep(30); // 自查线程周期进行工作，其每次休眠时间
			pool.setNagle(false); // Socket的参数，如果是true在写数据时不缓冲，立即发送出去。Tcp的规则是在发送一个包之前，包的发送方会等待远程接收方确认已收到上一次发送过来的包；这个方法就可以关闭套接字的缓存——包准备立即发出。
			pool.setSocketTO(3000); // Socket阻塞读取数据的超时时间
			pool.setAliveCheck(true); // 设置是否检查memcached服务器是否失效
			pool.setMaxIdle(1000 * 30 * 30); // 设置最大处理时间
			pool.setSocketConnectTO(0); // 连接建立时对超时的控制
			pool.initialize(); // 初始化连接池
			if (memCachedClient == null) {
				memCachedClient = new MemCachedClient();
				memCachedClient.setPrimitiveAsString(true); // 是否将基本类型转换为String方法
			}
		}
		return pool;
	}

	/**
	 * 向缓存添加键值对。注意：如果键已经存在，则之前的键对应的值将被替换。
	 * 
	 * @author GaoHuanjie
	 */
	public boolean set(String key, Object value) {
		try {
			return memCachedClient.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向缓存添加键值对并为该键值对设定逾期时间（即多长时间后该键值对从Memcached内存缓存中删除，比如： new
	 * Date(1000*10)，则表示十秒之后从Memcached内存缓存中删除）。注意：如果键已经存在，则之前的键对应的值将被替换。
	 * 
	 * @author GaoHuanjie
	 */
	public boolean set(String key, Object value, Date expire) {
		try {
			return memCachedClient.set(key, value, expire);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向缓存添加键值对。注意：仅当缓存中不存在键时，才会添加成功。
	 * 
	 */
	public boolean add(String key, Object value) {
		try {
			if (get(key) != null) {
				System.out.println("key=[" + key + "] Memcached内存缓存中已经存在该键值对！");
				return false;
			} else {
				return memCachedClient.add(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向缓存添加键值对并为该键值对设定逾期时间（即多长时间后该键值对从Memcached内存缓存中删除，比如： new
	 * 
	 */
	public boolean add(String key, Object value, Date expire) {
		try {
			if (get(key) != null) {
				System.out.println("key=[" + key + "] Memcached内存缓存中已经存在该键值对！");
				return false;
			} else {
				return memCachedClient.add(key, value, expire);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据键来替换Memcached内存缓存中已有的对应的值。注意：只有该键存在时，才会替换键相应的值。
	 * 
	 */
	public boolean replace(String key, Object newValue) {
		try {
			return memCachedClient.replace(key, newValue);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据键来替换Memcached内存缓存中已有的对应的值并设置逾期时间（即多长时间后该键值对从Memcached内存缓存中删除，比如： new
	 * Date(1000*10)，则表示十秒之后从Memcached内存缓存中删除）。注意：只有该键存在时，才会替换键相应的值。
	 * 
	 */
	public boolean replace(String key, Object newValue, Date expireDate) {
		try {
			return memCachedClient.replace(key, newValue, expireDate);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据键获取Memcached内存缓存管理系统中相应的值
	 * 
	 */
	public Object get(String key) {
		try {
			return memCachedClient.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据键删除memcached中的键/值对
	 * 
	 */
	public boolean delete(String key) {
		try {
			return memCachedClient.delete(key);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据键和逾期时间（例如：new Date(1000*10)：十秒后过期）删除 memcached中的键/值对
	 * 
	 */
	public boolean delete(String key, Date expireDate) {
		try {
			return memCachedClient.delete(key, expireDate);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 清理缓存中的所有键/值对
	 * 
	 */
	public boolean flashAll() {
		try {
			return memCachedClient.flushAll();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		MemCachedUtil m = MemCachedUtil.getInstance();
		System.out.println(m.set("5c3767f8b8ce4040bac9b23dff63c6e7.label", "[1]"));
		System.out.println(m.get("5c3767f8b8ce4040bac9b23dff63c6e7.label"));
		
	}
}