package com.yangqugame.comm.cache;

import java.util.List;

public class StoredListRef<T extends StoredObj>{

	private String oKeyStr;

	public StoredListRef(String oKeyStr) {
		this.oKeyStr = oKeyStr;
	}

	public String getOkey() {
		return oKeyStr;
	}

	public void add(T obj) {
		addTail(obj);
	}

	public void addHead(T obj) {
		StoreService.rpush(StoredObjManager.STORED_OBJ_DB,oKeyStr, obj);
	}

	public void addTail(T obj) {
		StoreService.lpush(StoredObjManager.STORED_OBJ_DB,oKeyStr, obj);
	}

	public void del(T obj, int count) {
		assert obj != null : "Object is null";
		StoreService.lrem(StoredObjManager.STORED_OBJ_DB,oKeyStr, count, obj);
	}

	@SuppressWarnings("unchecked")
	public T popTail(Class<? extends StoredObj> clazz) {
		return (T)StoreService.lpop(StoredObjManager.STORED_OBJ_DB,oKeyStr,clazz);
	}
	@SuppressWarnings("unchecked")
	public T popHead(Class<? extends StoredObj> clazz) {
		return (T)StoreService.rpop(StoredObjManager.STORED_OBJ_DB,oKeyStr,clazz);
	}

	@SuppressWarnings("unchecked")
	public T get(int index,Class<? extends StoredObj> clazz) {
		return (T) StoreService.lindex(StoredObjManager.STORED_OBJ_DB,oKeyStr, index,clazz);
	}

	@SuppressWarnings("unchecked")
	public List<T> get(int start, int end,Class<? extends StoredObj> clazz) {
		return (List<T>) StoreService.lrange(StoredObjManager.STORED_OBJ_DB,oKeyStr, start, end,clazz);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll(Class<? extends StoredObj> clazz) {
		return (List<T>) StoreService.lrange(StoredObjManager.STORED_OBJ_DB,oKeyStr, 0, len(),clazz);
	}

	public void set(int index, T obj) {
		StoreService.lset(StoredObjManager.STORED_OBJ_DB,oKeyStr, index, obj);
	}

	public int len() {
		return (int) StoreService.llen(StoredObjManager.STORED_OBJ_DB,oKeyStr);
	}

	@SuppressWarnings("unchecked")
	public T getHead(Class<? extends StoredObj> clazz) {
		return (T) StoreService.rpop(StoredObjManager.STORED_OBJ_DB,oKeyStr,clazz);
	}

	@SuppressWarnings("unchecked")
	public T getTail(Class<? extends StoredObj> clazz) {
		return (T) StoreService.lpop(StoredObjManager.STORED_OBJ_DB,oKeyStr,clazz);
	}

	public void remove() {
		StoreService.remove(StoredObjManager.STORED_OBJ_DB,oKeyStr);
	}
}
