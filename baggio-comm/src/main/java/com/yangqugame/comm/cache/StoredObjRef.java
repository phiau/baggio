package com.yangqugame.comm.cache;


/**
 * 该类是 对StoredObj （或其子类对象）实例的 打包，一般作为该对象的引用 加以保持
 * 取得包内的StoredObj  使用get() 即可
 * 
 * @author jiangqh
 *
 * @param <T>
 */

public class StoredObjRef<T extends StoredObj>{

	private Class<? extends StoredObj> clazz;
	
	private String oKeyStr;

	private transient T o;
	/**
	 * 构建一个StoredObjRef 绑定到指定的key
	 * @param oKeyStr  key
	 */
	@SuppressWarnings("unchecked")
	public StoredObjRef(Class<? extends StoredObj> c,String oKeyStr) {
		this.oKeyStr = oKeyStr;
		this.clazz   = c;
	}

	/**
	 * 构建一个StoredObjRef 绑定对象到指定的key
	 * @param oKeyStr
	 * @param obj
	 */
	StoredObjRef(String oKeyStr, T obj) {
		assert obj != null : "Object is null";
		assert obj instanceof StoredObj : "Object subclass is not subclass of StoredObj";
		
		this.oKeyStr = oKeyStr;
		this.o = obj;
		this.o.bindRef(this);
	}
	/**
	 * 取得内部缓存的key
	 * 
	 * @return theKey
	 */
	public String getObjkey() {
		return oKeyStr;
	}
	
	/**
	 * 
	 * 取得该Ref 绑定的Obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T get() {
		if(o==null){
			o = (T) StoreService.get(StoredObjManager.STORED_OBJ_DB,oKeyStr,clazz);
			o.bindRef(this);
		}
		return o;
	}
		
	/**
	 * 删除该Ref 绑定的Obj 
	 * 注意如果绑定的是User**Bean，不但要 调用StoredObj 的remove (或者Ref的remove) 
	 * 同时也要使用BaseBean 的delete，以便操作数据库删除 
	 */
	public void remove(){
		StoreService.remove(StoredObjManager.STORED_OBJ_DB,oKeyStr);		
	}
}
