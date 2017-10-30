package com.yangqugame.comm.cache;


import com.yangqugame.comm.annotation.CachaAnnotationManager;

/**
 * 该类是游戏里 对象的核心类，所有要缓存的对象均要继承自该类 并指定一个 Serialize ID
 * 
 * @author jiangqh
 * 
 */
public abstract class StoredObj {

	StoredObjRef<?> ref;

	/**
	 * 绑定一个StoredObjRef 到自身
	 * 
	 * @param ref
	 */
	void bindRef(StoredObjRef<?> ref) {
		this.ref = ref;
	}

	/**
	 * 获取自身的key in redis
	 * 
	 * @return key
	 */
	String getKey() {
		return this.ref.getObjkey();
	}

	/**
	 * 删除 该StoredObj
	 * 
	 */
	public void remove() {
		if (ref != null) {
			ref.remove();
			ref = null;
		}
	}

	/**
	 * 保存对象到缓存
	 */
	public void save() {
		if (ref != null) {
			StoreService.saveObject(StoredObjManager.STORED_OBJ_DB,ref.getObjkey(), this);
		}else{
			ref = StoredObjManager.makeObjRef(this,makeKey(keyName(),keyID()));
		}	
	}
	
	
	/**
	 * 保存对象到缓存
	 */
	public void save(String key) {
		StoredObjManager.makeObjRef(this,key);
	}
	
	/**
	 * 保存对象到缓存
	 */
	public void saveInMap(String key) {
		StoredObjManager.makeObjMap(this, key);
	}
	
	/****
	 * 适用于单kv数
	 * @param name
	 * @param uuid
	 * @return
	 */
	public static String makeKey(String name,String uuid){		
		return StoreService.STOREDOBJPROFIX + name + StoreService.SPLIT + uuid;
	}

	/***
	 * 适用于分组数据
	 * @param name
	 * @param uuid
	 * @param id
	 * @return
	 */
	public static String makeKey(String name,String uuid,long id){		
		return StoreService.STOREDOBJPROFIX + name + StoreService.SPLIT + uuid + StoreService.SPLIT+id;
	}

    public String keyName() {
	    return CachaAnnotationManager.getCacheKeyName(this.getClass());
    }

    public String keyID() {
	    return CachaAnnotationManager.getCacheKeyIdR(this);
    }
}
