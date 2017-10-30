package com.yangqugame.comm.cache;

import com.yangqugame.comm.annotation.CachaAnnotationManager;
import com.yangqugame.comm.log.Logger;
import com.yangqugame.comm.log.LoggerFactory;
import redis.clients.jedis.*;

import java.util.*;
import java.util.Map.Entry;

/**
 * 该类提供Redis 缓存服务
 * 
 * @author jiangqh
 * 
 */
public class StoreService extends StoredObjCodec {

	final transient static Logger log = LoggerFactory.getLogger(StoreService.class);

	public static final String SPLIT = ":";

	/**
	 * 对象缓存key profix
	 */
	public static final String STOREDOBJPROFIX = "Stored:Obj:";
	/**
	 * 对象list缓存key profix
	 */
	public static final String STOREDLISTPROFIX = "Stored:list:";
	/**
	 * 对象set缓存key profix
	 */
	public static final String STOREDSETPROFIX = "Stored:set:";

	private static boolean running = false;

	private static JedisPool pool;

	/**
	 * 停止服务
	 */
	public static void shutdown() {
		log.info(" stored  service  stoped");
		running = false;
		if (pool != null)
			pool.destroy();
		System.gc();
	}

	/**
	 * 初始化redis服务
	 * @param host
	 * @param port
	 * @param database
	 * @param database2
	 * @param passwd
	 */
	public static void start(String host, int port,int database, int database2, String passwd) {

		log.info(" * connect cache " + host + " db " + database + " db2 " + database2);
		if (!running) {

			JedisPoolConfig config = new JedisPoolConfig();// Jedis池配置
			config.setMaxIdle(1000 * 60);// 对象最大空闲时间
			config.setTestOnBorrow(true);

			if (passwd.equals("")) {
				pool = new JedisPool(config, host, port);
			} else {
				pool = new JedisPool(config, host, port, 100000, passwd);
			}

			StoredObjManager.initDB(database);
			StoredDataManager.initDB(database2);

			running = true;
		}
		log.info(" * connect cache successful");
	}

	/**
	 * 移除单个缓存对象
	 * 
	 * @param key
	 */
	static void remove(int dbid, String key) {
		// log.info("redis  remove  "+key);
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			redis.del(encode(key));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	/**
	 * 保存单个对象
	 * 
	 * @param <T>
	 *            对象类型
	 * @param key
	 *            保存key
	 * @param object
	 *            保存的对象
	 */
	public static <T extends StoredObj> void saveObject(int dbid, String key, T object) {
		set(dbid, key, object);
	}

	/**
	 * 保存单条String
	 * 
	 * @param key
	 *            保存key
	 * @param str
	 *            保存的String对象
	 */
	static void setString(int dbid, String key, String str) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			redis.set(encode(key), encode(str));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	/**
	 * 保存单条String
	 * 
	 * @param key
	 *            保存key
	 * @param str
	 *            保存的String对象
	 */
	static void setExString(int dbid, String key, String str, int ex) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			redis.setex(encode(key), ex, encode(str));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	/**
	 * 根据key 模糊查询对象list
	 * 
	 * @param fuzzyKey
	 *            查询key
	 * @return 包含对象List 的set
	 */
	// static Set<StoredListRef<? extends StoredObj>> keysOfList(String
	// fuzzyKey, Class<? extends StoredObj> clazz) {
	// BinaryJedis redis = null;
	// try {
	// redis = pool.getResource();
	// Set<byte[]> keys = redis.keys(encode(fuzzyKey + "*"));
	// if (keys != null && keys.size() > 0) {
	// Set<StoredListRef<? extends StoredObj>> objs = new
	// HashSet<StoredListRef<? extends StoredObj>>();
	//
	// for (byte[] key : keys) {
	// objs.add(StoredObjManager.makeObjListRef(toStr(key),
	// decode(redis.lrange(key, 0, redis.llen(key).intValue()), clazz)));
	// }
	// return objs;
	// } else {
	// return null;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// return null;
	// } finally {
	// pool.returnResource((Jedis) redis);
	// }
	// }

	/**
	 * 根据key 模糊查询
	 * 
	 * @param key
	 *            查询key
	 * @return 包含String 的List
	 */
	// static List<String> keys(int dbid, String key) {
	// BinaryJedis redis = null;
	// try {
	// redis = pool.getResource();
	// redis.select(dbid);
	// Set<byte[]> keys = redis.keys(encode(key + "*"));
	// if (keys != null && keys.size() > 0) {
	// List<String> result = new ArrayList<String>();
	// for (byte[] bs : keys) {
	// result.add(toStr(bs));
	// }
	// return result;
	// } else {
	// return null;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// return null;
	// } finally {
	// pool.returnResource((Jedis) redis);
	// }
	// }

	static long incrNumber(int dbid, String key) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return redis.incr(encode(key));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static long decr(int dbid, String key) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return redis.decr(encode(key));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	/**
	 * 根据key 模糊查询keys
	 * 
	 * @param dbid
	 * @return key
	 */
	static List<String> keys(int dbid, String key) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			Set<byte[]> keys = redis.keys(encode(key + "*"));
			if (keys != null && keys.size() > 0) {
				List<String> objs = new ArrayList<String>(keys.size());
				for (byte[] bs : keys) {
					objs.add(toStr(bs));
				}
				return objs;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	/**
	 * 根据key 模糊查询对象
	 * 
	 * @param dbid
	 * @return key
	 */
	static <T extends StoredObj> Set<T> keys(int dbid, String key, Class<T> c) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			Set<byte[]> keys = redis.keys(encode(key + "*"));
			if (keys != null && keys.size() > 0) {
				Set<T> objs = new HashSet<T>();
				for (byte[] bs : keys) {
					objs.add(decode(redis.get(bs), c));
				}
				return objs;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	/**
	 * 是否存在某个key的对象
	 * 
	 * @param key
	 *            查询key
	 * @return 是否存在
	 */
	static boolean exists(int dbid, String key) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return redis.exists(encode(key));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static void setNumber(int dbid, String key, long value) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			redis.set(encode(key), encode(Long.toString(value)));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	/**
	 * 将单个对象绑定到指定key
	 * 
	 * @param <T>
	 *            对象类型
	 * @param key
	 *            指定key
	 * @param object
	 *            绑定的对象
	 */
	static <T extends StoredObj> void set(int dbid, String key, T object) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			redis.set(encode(key), encode(object));
			int expire = CachaAnnotationManager.getCacheExpire(object.getClass());
			if (0 < expire) {
			    redis.expire(encode(key), expire);
            }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	/**
	 * 根据指定key 查询对象
	 * 
	 * @param key
	 *            查询key
	 * @return 保存的对象 (如果key 不存在，返回null)
	 */
	static Object get(int dbid, String key, Class<? extends StoredObj> c) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			byte[] data = redis.get(encode(key));
			return decode(data, c);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	/**
	 * 查询一组对象
	 * 
	 * @param key
	 *            该组对象的key
	 * @param start
	 *            起始index
	 * @param end
	 *            结束index
	 * @return 对象list
	 */
	static List<? extends StoredObj> lrange(int dbid, String key, int start, int end, Class<? extends StoredObj> clazz) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return decode(redis.lrange(encode(key), start, end), clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static String lpopString(int dbid, String key) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);

			byte[] keyBytes = encode(key);
			byte[] result = redis.lpop(keyBytes);
			if (result != null)
				return toStr(result);
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static List<String> lrangeString(int dbid, String key, int len) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);

			byte[] keyBytes = encode(key);
			List<String> list = new LinkedList<String>();
			int size = Math.min(len, (int) llen(dbid, key));
			for (int i = 0; i < size; i++) {
				list.add(toStr(redis.lpop(keyBytes)));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	/**
	 * 添加对象到list尾部
	 * 
	 * @param <T>
	 *            类型
	 * @param key
	 *            list 的key
	 * @param element
	 *            追加的对象
	 */
	static <T extends StoredObj> void rpush(int dbid, String key, T element) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			redis.rpush(encode(key), encode(element));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static void lpushString(int dbid, String key, String element) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			redis.lpush(encode(key), encode(element));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	/**
	 * 添加String 到list 尾部
	 * 
	 * @param key
	 *            list 的key
	 * @param element
	 *            追加的String
	 */
	static void rpushString(int dbid, String key, String element) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			redis.rpush(encode(key), encode(element));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	/**
	 * 添加对象到list头部
	 * 
	 * @param <T>
	 *            对象类型
	 * @param key
	 *            list 的key
	 * @param element
	 *            追加的对象
	 */
	static <T extends StoredObj> void lpush(int dbid, String key, T element) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			redis.lpush(encode(key), encode(element));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	/**
	 * 获取list 的长度
	 * 
	 * @param key
	 *            list 的key
	 * @return list的长度
	 */
	static long llen(int dbid, String key) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return redis.llen(encode(key));
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static Object lpop(int dbid, String key, Class<? extends StoredObj> clazz) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return decode(redis.lpop(encode(key)), clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static Object rpop(int dbid, String key, Class<? extends StoredObj> clazz) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return decode(redis.rpop(encode(key)), clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static <T extends StoredObj> void lset(int dbid, String key, int index, T object) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			redis.lset(encode(key), index, encode(object));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static Object lindex(int dbid, String key, int index, Class<? extends StoredObj> clazz) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return decode(redis.lindex(encode(key), index), clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static <T extends StoredObj> void lrem(int dbid, String key, int count, T object) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			redis.lrem(encode(key), count, encode(object));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	/** the save method for stored set **/

	static <T extends StoredObj> void sadd(int dbid, String key, T object) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			redis.sadd(encode(key), encode(object));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static <T extends StoredObj> void srem(int dbid, String key, T object) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			redis.srem(encode(key), encode(object));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static <T extends StoredObj> void smove(int dbid, String srcKey, String destKey, T object) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			redis.smove(encode(srcKey), encode(destKey), encode(object));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static <T extends StoredObj> long scard(int dbid, String key) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return redis.scard(encode(key));
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static <T extends StoredObj> boolean sismember(int dbid, String key, T object) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return redis.sismember(encode(key), encode(object));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static <T extends StoredObj> Set<T> smembers(int dbid, String key, Class<? extends StoredObj> clazz) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return decode(redis.smembers(encode(key)), clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static <T extends StoredObj> T spop(int dbid, String key, Class<? extends StoredObj> clazz) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return decode(redis.spop(encode(key)), clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static void cleanDB(int dbid) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			redis.flushDB();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static boolean hexists(int dbid, String key, String field) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return redis.hexists(encode(key), encode(field));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static long hdel(int dbid, String key, String field) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return redis.hdel(encode(key), encode(field));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static long hset(int dbid, String key, String field, String value) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return redis.hset(encode(key), encode(field), encode(value));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static <T extends StoredObj> long hset(int dbid, String key, String field, T object) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return redis.hset(encode(key), encode(field), encode(object));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static String hget(int dbid, String key, String field) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return toStr(redis.hget(encode(key), encode(field)));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static <T extends StoredObj> T hget(int dbid, String key, String field, Class<? extends StoredObj> c) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return decode(redis.hget(encode(key), encode(field)),c);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	
	
	static Set<String> hkeys(int dbid, String key) {
		BinaryJedis redis = null;
		Set<String> result = new HashSet<String>();
		try {
			redis = pool.getResource();
			redis.select(dbid);
			Set<byte[]> tmp = redis.hkeys(encode(key));
			for (byte[] bs : tmp) {
				result.add(toStr(bs));
			}
			if(result.size() == 0)return null;
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static List<String> hvalues(int dbid, String key) {
		BinaryJedis redis = null;
		List<String> result = new ArrayList<String>();
		try {
			redis = pool.getResource();
			redis.select(dbid);
			List<byte[]> tmp = redis.hvals(encode(key));
			for (byte[] bs : tmp) {
				result.add(toStr(bs));
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static long hlen(int dbid, String key) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return redis.hlen(encode(key));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static long hincrBy(int dbid, String key, String field, long num) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return redis.hincrBy(encode(key), encode(field), num);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static Map<String, String> hgetAll(int dbid, String key) {
		BinaryJedis redis = null;
		Map<String, String> result = new HashMap<String, String>();
		try {
			redis = pool.getResource();
			redis.select(dbid);
			Map<byte[], byte[]> tmp = redis.hgetAll(encode(key));
			for (Entry<byte[], byte[]> e : tmp.entrySet()) {
				result.put(toStr(e.getKey()), toStr(e.getValue()));
			}
			if(result.size() == 0)return null;
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	static <T extends StoredObj> Map<String, T> hgetAll(int dbid, String key, Class<? extends StoredObj> c) {
		BinaryJedis redis = null;
		Map<String, T> result = new HashMap<String, T>();
		try {
			redis = pool.getResource();
			redis.select(dbid);
			Map<byte[], byte[]> tmp = redis.hgetAll(encode(key));
			for (Entry<byte[], byte[]> e : tmp.entrySet()) {
				result.put(toStr(e.getKey()), decode(e.getValue(), c));
			}
			if(result.size() == 0)return null;
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	public static long getNumber(int dbid, String key) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			byte[] keyBytes = encode(key);
			if (redis.exists(keyBytes)) {
				return Long.parseLong(toStr(redis.get(keyBytes)));
			} else {
				redis.set(keyBytes, encode("" + 0));
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}

	public static String getString(int dbid, String key) {
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			byte [] d = redis.get(encode(key));
			return toStr(d);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}
	
	/**
	 * 取得有序数组中 某个成员 分数值
	 * @param dbid
	 * @param key
	 * @param member
	 * @return
	 */
	public static long zscore(int dbid, String key , String member)
	{
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			double res = redis.zscore(encode(key), encode(member));
			return (long) res;
		} catch (Exception e) {
			//e.printStackTrace();
			return 0;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}
	
	/**
	 * 取得有序数组中 某个成员  排名
	 * @param dbid
	 * @param key
	 * @param member
	 * @return
	 */
	public static long zrank(int dbid, String key , String member)
	{
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			long v = redis.zrank(encode(key), encode(member));
			//double res = redis.zscore(encode(key), encode(member));
			return v;
		} catch (Exception e) {
			//e.printStackTrace();
			return 0;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}
	
	/**
	 * 取得数量
	 * @param dbid
	 * @param key
	 * @return
	 */
	public static long zcard(int dbid, String key )
	{
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			long res = redis.zcard(encode(key));;
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}
	
	/**
	 * 删除 member 
	 * @param dbid
	 * @param key
	 * @param member
	 * @return
	 */
	public static long zrem(int dbid, String key , String member)
	{
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			double res = redis.zrem(encode(key), encode(member));
			return (long) res;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}
	
	public static long zadd(int dbid, String key,long score,String value) {
		
		System.out.println("zadd : key=" + key + " , score=" + score + " , member=" + value);
		BinaryJedis redis = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			return redis.zadd(encode(key), score, encode(value));
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}
	
	//取排名列表
	public static List<String> zrange(int dbid, String key,int start,int end) {
		BinaryJedis redis = null;
		List<String>  tmp = null;
		try {
			redis = pool.getResource();
			redis.select(dbid);
			Set<byte[]> eles = redis.zrange(encode(key), start, end);
			
			tmp = new ArrayList<String>(eles.size());
			for (byte[] bs : eles) {
				tmp.add(toStr(bs));
			}
			return tmp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource((Jedis) redis);
		}
	}
	
	//取排名列表
		public static List<String> zrangeWithScores(int dbid, String key,int start,int end) {
			BinaryJedis redis = null;
			List<String>  tmp = null;
			try {
				redis = pool.getResource();
				redis.select(dbid);
				Set<Tuple> eles = redis.zrangeWithScores(encode(key), start, end);
				
				tmp = new ArrayList<String>(eles.size());
				for (Tuple bs : eles) {
					tmp.add( bs.getElement() +":" + (long)bs.getScore() );
				}
				return tmp;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				pool.returnResource((Jedis) redis);
			}
		}
}
