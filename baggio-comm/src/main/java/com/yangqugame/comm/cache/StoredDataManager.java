package com.yangqugame.comm.cache;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

/**
 * 
 * StoredDataManager 对象的管理器，负责创建或获取cache 中非对象类数据
 * 
 * @author jiangqh
 *
 */
public class StoredDataManager {

	public static int STORED_DATA_DB = 0;

	public static void initDB(int dbid) {
		STORED_DATA_DB = dbid;
	}

	/**
	 * 删除已存在对象
	 * 
	 * @param key
	 *            对象的key
	 */
	public static void deleteExistsData(String key) {
		StoreService.remove(STORED_DATA_DB, key);
	}

	/**
	 * 是否存在该key
	 * 
	 * @param key
	 * @return if exists
	 */
	public static boolean existsKey(String key) {
		return StoreService.exists(STORED_DATA_DB, key);
	}

	public static void makeStoredString(String key, String str) {
		if (StoreService.exists(STORED_DATA_DB, key)) {
		} else {
			StoreService.setString(STORED_DATA_DB, key, str);
		}
	}
	
	
	public static void makeStoredExString(String key, String str,int ex) {
		if (StoreService.exists(STORED_DATA_DB, key)) {
		} else {
			StoreService.setExString(STORED_DATA_DB, key, str,ex);
		}
	}

	
	/****
	 * set map 中元素
	 * @param key
	 * @return
	 */
	public static Set<String> keysInMap(String key) {
		return StoreService.hkeys(STORED_DATA_DB,key);
	}
	
	/***
	 * 模糊取得一组对象key
	 * 
	 * @param key
	 * @return
	 */
	public static List<String> getStoredKeysWith(String key) {
		return StoreService.keys(STORED_DATA_DB,key);
	}

	/**
	 * 加入redis list 元素
	 * 
	 * @param key
	 * @param value
	 */
	public static void makeStoredStringInList(String key, String value) {
		StoreService.rpushString(STORED_DATA_DB, key, value);
	}

	/**
	 * 取出len长度的 记录 并删除
	 * 
	 * @param key
	 * @return
	 */
	public static List<String> getStoredStringListAndRemove(String key, int len) {
		return StoreService.lrangeString(STORED_DATA_DB, key, len);
	}

	/**
	 * 取出len长度的 记录 并删除
	 * 
	 * @param key
	 * @return
	 */
	public static String popString(String key) {
		return StoreService.lpopString(STORED_DATA_DB, key);
	}

	/**
	 * list 长度
	 * 
	 * @param key
	 * @return
	 */
	public static int getStoredStringListSize(String key) {
		return (int) StoreService.llen(STORED_DATA_DB, key);
	}

	public static void setStoredNumber(String key,long value) {
		StoreService.setNumber(STORED_DATA_DB, key, value);
	}
	
	public static long getStoredNumber(String key) {
		return StoreService.getNumber(STORED_DATA_DB, key);
	}
	
	/**
	 * 取得自增big integer
	 * 
	 * @param key2
	 * @return
	 */
	public static String getStoredBigNumberInc(String key1, String key2) {
		System.out.println(" getStoredBigNumberInc ---------  "+STORED_DATA_DB);
		long v1 = StoreService.getNumber(STORED_DATA_DB, key1);

		long v2 = StoreService.getNumber(STORED_DATA_DB, key2);

		if (v2 >= Long.MAX_VALUE) {

			v1 = StoreService.incrNumber(STORED_DATA_DB, key1);
			
			StoreService.setNumber(STORED_DATA_DB, key2, 1);
			
			v2 = 1;
			
		}else{
			v2 =  StoreService.incrNumber(STORED_DATA_DB, key2);
		}
		
		if (v1 > 0) {
			BigInteger value = new BigInteger(v1 + "");
			value = value.multiply(new BigInteger(Long.MAX_VALUE + ""));
			value = value.add(new BigInteger(v2 + ""));
			return value.toString();
		} else {
			return v2 + "";
		}
	}

	public static String getStoredString(String key) {
		return StoreService.getString(STORED_DATA_DB, key);
	}

}
