package com.yangqugame.comm.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * StoredObj 对象的管理器，负责创建或获取StoredObj 的Ref 存储key 采用表名-id 存储value 采用json 格式
 * 
 * @author jiangqh
 *
 */
public class StoredObjManager {

	public static int STORED_OBJ_DB = 0;
	
	public static void initDB(int dbid) {
		STORED_OBJ_DB = dbid;
	}
	
	
	/**
	 * 删除已存在对象
	 * 
	 * @param key
	 *            对象的key
	 */
	public static void deleteExistsObj(String key) {
		StoreService.remove(STORED_OBJ_DB,key);
	}

	/**
	 * 是否存在该key的StoredObj
	 * 
	 * @param key
	 * @return if exists
	 */
	public static boolean existsKey(String key) {
		return StoreService.exists(STORED_OBJ_DB,key);
	}

	/**
	 * 生成一个StoredObj 的Ref uid 格式：前缀+keyName+id
	 * 
	 * @param <T>
	 * @param obj
	 *            必须是继承自StoredObj
	 * @return StoredObjRef<T>
	 */
	public static <T extends StoredObj> StoredObjRef<T> makeObjRef(T obj, String key) {
		if (obj == null) {
//			log.info("makeObjRef Null");
			return null;
		}
		
		if (obj.ref != null) {
			return (StoredObjRef<T>) obj.ref;
		}

		StoreService.set(STORED_OBJ_DB,key, obj);

		return new StoredObjRef<T>(key, obj);
	}

	/**
	 * 根据一个特定的key取得一个StoredObj的Ref
	 * 
	 * @param <T>对象类型
	 * @param key
	 *            key
	 * @return the StoredObjRef
	 */
	public static <T extends StoredObj> StoredObjRef<T> getObjRef(Class<? extends StoredObj> c, String key) {
		if (!StoreService.exists(STORED_OBJ_DB,key)) {
			return null;
		} else {
			return new StoredObjRef<T>(c, key);
		}
	}

	/**
	 * 生成一个StoredObj 的RefList
	 * 
	 * @param <T>
	 * @param elements
	 *            初始化的item 列表
	 * @return the StoredListRef
	 */
	public static <T extends StoredObj> StoredListRef<T> makeObjListRef(T... elements) {
		String key = StoreService.STOREDLISTPROFIX + elements[0].keyName() + StoreService.SPLIT + elements[0].keyID();

		if (StoreService.exists(STORED_OBJ_DB,key)) {
			return null;
		} else {
			for (T e : elements)
				StoreService.lpush(STORED_OBJ_DB,key, e);
		}

		return new StoredListRef<T>(key);
	}

	/**
	 * 生成一个StoredObj 的RefList
	 * 
	 * @param <T>
	 * @param elements
	 *            初始化的item 列表
	 * @return the StoredListRef
	 */
	public static <T extends StoredObj> StoredListRef<T> makeObjListRef(List<T> elements) {
		String key = StoreService.STOREDLISTPROFIX + elements.get(0).keyName() + StoreService.SPLIT + elements.get(0).keyID();

		if (StoreService.exists(STORED_OBJ_DB,key)) {
			return null;
		} else {
			for (T e : elements)
				StoreService.lpush(STORED_OBJ_DB,key, e);
		}

		return new StoredListRef<T>(key);
	}

	/**
	 * 生成一个StoredObj 的RefList
	 * 
	 * @param <T>
	 * @param elements
	 *            初始化的item 列表
	 * @return the StoredListRef
	 */
	public static <T extends StoredObj> StoredListRef<T> makeObjListRef(List<T> elements,String key) {
		if (StoreService.exists(STORED_OBJ_DB,key)) {
			return null;
		} else {
			for (T e : elements)
				StoreService.lpush(STORED_OBJ_DB,key, e);
		}

		return new StoredListRef<T>(key);
	}

	/**
	 * 生成一个StoredObj 的Ref Set
	 * 
	 * @param <T>
	 *            初始化的item 列表
	 * @param elements
	 * @return the StoredSetRef
	 */
	public static <T extends StoredObj> StoredSetRef<T> makeObjSetRef(T... elements) {

		String key = StoreService.STOREDSETPROFIX + elements[0].keyName() + StoreService.SPLIT + elements[0].keyID();

		if (StoreService.exists(STORED_OBJ_DB,key)) {
			return null;
		} else {
			for (T e : elements)
				StoreService.sadd(STORED_OBJ_DB,key, e);
		}

		return new StoredSetRef<T>(key);
	}

	public static void makeStoredString(String key, String str) {
		if (StoreService.exists(STORED_OBJ_DB,key)) {
		} else {
			StoreService.setString(STORED_OBJ_DB,key, str);
		}

	}

	/***
	 * 模糊取得一组对象
	 * 
	 * @param key
	 * @return
	 */
	public static <T extends StoredObj> Set<T> getStoredObjsWithOneKey(Class<T> c, String key) {
		return StoreService.keys(STORED_OBJ_DB,key, c);
	}

	/****
	 * set map 中元素
	 * @param c
	 * @param key
	 * @return
	 */
	public static <T extends StoredObj> long makeObjMap(T obj, String key) {
		return StoreService.hset(STORED_OBJ_DB,key,obj.keyID(),obj);
	}
	
	/****
	 * remove map 中元素
	 * @param c
	 * @param key
	 * @return
	 */
	public static <T extends StoredObj> long removeObjMap(String key,String field) {
		return StoreService.hdel(STORED_OBJ_DB,key, field);
	}
	
	
	/****
	 * set map 中元素
	 * @param c
	 * @param key
	 * @return
	 */
	public static <T extends StoredObj> void makeObjMap(String key,Collection<T> list) {
		for (T t : list) {
			StoreService.hset(STORED_OBJ_DB,key,t.keyID(),t);
		}
	}
	
	/****
	 * 取得map 中所有元素
	 * @param c
	 * @param key
	 * @return
	 */
	public static <T extends StoredObj> Map<String,T> getStoredObjsInMap(Class<T> c, String key) {
		return StoreService.hgetAll(STORED_OBJ_DB,key, c);
	}
	
	/****
	 * 取得map 中元素
	 * @param c
	 * @param key
	 * @return
	 */
	public static <T extends StoredObj> T getStoredObjInMap(Class<T> c, String key,String field) {
		return StoreService.hget(STORED_OBJ_DB,key,field, c);
	}
	
	
	/****
	 * set map 中元素
	 * @param c
	 * @param key
	 * @return
	 */
	public static Set<String> keysInMap(String key) {
		return StoreService.hkeys(STORED_OBJ_DB,key);
	}
	
	/****
	 * 清空缓存
	 */
	public static void cleanAll() {
		StoreService.cleanDB(STORED_OBJ_DB);
	}

}
