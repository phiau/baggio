package com.yangqugame.comm.cache;

import java.util.List;

/**
 * 该类是 对一组StoredObj（或其子类对象）实例的 打包，一般作为该对象的引用 加以保持
 * 
 * @author jiangqh
 *
 * @param <T>
 */
public class StoredSetRef<T extends StoredObj> {

	private String oKeyStr;

	public StoredSetRef(String oKeyStr) {
		this.oKeyStr = oKeyStr;
	}

	String getOkey() {
		return oKeyStr;
	}

	public void add(T obj) {
		StoreService.sadd(StoredObjManager.STORED_OBJ_DB,oKeyStr, obj);
	}

	@SuppressWarnings("hiding")
	public <T extends StoredObj> void del(T obj, int count) {
		assert obj != null : "Object is null";
		StoreService.srem(StoredObjManager.STORED_OBJ_DB,oKeyStr, obj);
	}

	public boolean exists(T obj) {
		return StoreService.sismember(StoredObjManager.STORED_OBJ_DB,oKeyStr, obj);
	}

	@SuppressWarnings("unchecked")
	public List<T> allElememts(Class<? extends StoredObj> clazz) {
		return (List<T>) StoreService.smembers(StoredObjManager.STORED_OBJ_DB,oKeyStr,clazz);
	}

	public long len() {
		return StoreService.scard(StoredObjManager.STORED_OBJ_DB,oKeyStr);
	}

	public void move(StoredSetRef<T> desSet, T obj) {
		StoreService.smove(StoredObjManager.STORED_OBJ_DB,oKeyStr, desSet.getOkey(), obj);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends StoredObj> T getRandomElment(Class<? extends StoredObj> clazz){
		return (T)StoreService.spop(StoredObjManager.STORED_OBJ_DB,oKeyStr,clazz);
	}
}
