/**
 * 
 */
package com.yangqugame.comm.db;

import com.alibaba.fastjson.JSON;
import com.yangqugame.comm.util.IOUtil;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author yama
 *
 */
public class PUSmartBeanDAO<T> {
    public final static short DB_TYPE_DATA = 1;
    public final static short DB_TYPE_CONFIG = 2;
    public final static short DB_TYPE_OTHER = 2;

	private String tableNamePrefix="t";
	private String tableName;
	private short dbType = 1;
	private Connection conn = null;

    private PUSmartBeanDAO() {
    }

    public PUSmartBeanDAO(String tableNamePrefix, String tableName, short dbType) {
        this.tableNamePrefix = tableNamePrefix;
        this.tableName = tableName;
        this.dbType = dbType;
    }

    public PUSmartBeanDAO(String tableNamePrefix, String tableName, short dbType, Connection conn) {
        this.tableNamePrefix = tableNamePrefix;
        this.tableName = tableName;
        this.dbType = dbType;
        this.conn = conn;
    }

    /**
	 * @return the tableName
	 */
	public String getTableName() {
		if(tableName!=null){
			return tableName;
		}
		Class<?>type=getTypeClass();
		String tableName=tableNamePrefix+convertFieldName(type.getSimpleName());
		return tableName;
	}
	/**
	 * @return the tablePrefix
	 */
	public String getTableNamePrefix() {
		return tableNamePrefix;
	}
	/**
	 * @param tablePrefix the tablePrefix to set
	 */
	public void setTableNamePrefix(String tablePrefix) {
		this.tableNamePrefix = tablePrefix;
	}
	protected Class<?>getTypeClass(){
		ParameterizedType pt=(ParameterizedType) getClass().getGenericSuperclass();
		Class<?>type=(Class<?>) pt.getActualTypeArguments()[0];
		return type;
	}
	//
	protected int update(T bean, PUQueryTerms qt, String... excludeProperties){
		return update(bean,false,qt,excludeProperties);
	}
	//
	private void checkExcludeProperties(String []excludeProperties,Class<?>type){
		for(String p:excludeProperties){
			try {
				if(type.getField(p)==null){	
					return;
				}
			} catch (Exception e) {
				throw new IllegalArgumentException("can not find property:"+
						p+" in type:"+type.getName());
			} 
		}
	}
	//
	private static final HashSet<Class<?>> WRAP_TYPES=new HashSet<>();
	static{
		WRAP_TYPES.add(Boolean.class);
		WRAP_TYPES.add(Character.class);
		WRAP_TYPES.add(Byte.class);
		WRAP_TYPES.add(Short.class);
		WRAP_TYPES.add(Integer.class);
		WRAP_TYPES.add(Long.class);
		WRAP_TYPES.add(BigDecimal.class);
		WRAP_TYPES.add(BigInteger.class);
		WRAP_TYPES.add(Double.class);
		WRAP_TYPES.add(Float.class);
		WRAP_TYPES.add(String.class);
		WRAP_TYPES.add(Date.class);
		WRAP_TYPES.add(Timestamp.class);
		WRAP_TYPES.add(java.sql.Date.class);
		WRAP_TYPES.add(Byte[].class);
		WRAP_TYPES.add(byte[].class);
		WRAP_TYPES.add(int.class);
		WRAP_TYPES.add(boolean.class);
		WRAP_TYPES.add(char.class);
		WRAP_TYPES.add(byte.class);
		WRAP_TYPES.add(short.class);
		WRAP_TYPES.add(int.class);
		WRAP_TYPES.add(long.class);
		WRAP_TYPES.add(float.class);
		WRAP_TYPES.add(double.class);
	}
	//
	protected int update(T bean, boolean excludeNull, PUQueryTerms qt, String... excludeProperties){
		
		StringBuilder sql=new StringBuilder();
		Class<?>type=getTypeClass();
		checkExcludeProperties(excludeProperties,type);
		String tableName=getTableName();
		sql.append("update ").append(tableName).append(" ");
		Set<String> excludesNames = new TreeSet<String>();
		for (String e : excludeProperties) {
			excludesNames.add(e);
		}
		List<Object>fieldList=new ArrayList<Object>();
		sql.append("set ");
		for (Field f : type.getFields()) {
			if (excludesNames.contains(f.getName())) {
				continue;
			}
			String fieldName = convertFieldName(f.getName());
			if (Modifier.isStatic(f.getModifiers())) {
				continue;
			}
			try {
				Object fieldValue=f.get(bean);
				if(excludeNull&&fieldValue==null){
					continue;
				}
				if(fieldValue!=null&&!WRAP_TYPES.contains(fieldValue.getClass())){
					fieldList.add(JSON.toJSONString(fieldValue));
				}else{
					fieldList.add(fieldValue);
				}
			} catch (Exception e) {
				throw new ConnectionException(e);
			}
			sql.append(" `").append(fieldName).append("`=?,");
		}
		sql.deleteCharAt(sql.length()-1);
		sql.append(" where 1=1");
		sql.append(qt.whereStatement());
		for(Object o:qt.whereValues()){
			fieldList.add(o);
		}
		if (DB_TYPE_DATA == dbType) {
            return PUBaseDaoThreadPool.executeUpdate(PUGlobalPool.getDataThreadPool(), PUGlobalPool.getDbPool(), sql.toString(), fieldList.toArray(new Object[fieldList.size()]));
        } else if (DB_TYPE_CONFIG == dbType) {
            return PUBaseDaoThreadPool.executeUpdate(PUGlobalPool.getConfigThreadPool(), PUGlobalPool.getConfigPool(), sql.toString(), fieldList.toArray(new Object[fieldList.size()]))	;
        }else {
	        return PUBaseDaoLocalThread.executeUpdate(conn, sql.toString(), fieldList.toArray(new Object[fieldList.size()]))	;
        }
	}
	//
	protected int delete(PUQueryTerms qt){
		StringBuilder sql=new StringBuilder();
		String tableName=getTableName();
		sql.append("delete from ").append(tableName);
		sql.append(" where 1=1");
		sql.append(qt.whereStatement());
		if (DB_TYPE_DATA == dbType) {
            return PUBaseDaoThreadPool.executeUpdate(PUGlobalPool.getDataThreadPool(), PUGlobalPool.getDbPool(), sql.toString(),qt.whereValues());
        } else if (DB_TYPE_CONFIG == dbType) {
            return PUBaseDaoThreadPool.executeUpdate(PUGlobalPool.getConfigThreadPool(), PUGlobalPool.getConfigPool(), sql.toString(),qt.whereValues());
        } else {
		    return PUBaseDaoLocalThread.executeUpdate(conn, sql.toString(), qt.whereValues());
        }
	}
	//
	private String querySql(PUQueryTerms qt, String... excludeProperties){
		StringBuilder sql=new StringBuilder();
		Class<?>type=getTypeClass();
		checkExcludeProperties(excludeProperties,type);
		String tableName=getTableName();
		sql.append("select ");
		if(excludeProperties==null||excludeProperties.length==0){
			sql.append(" * ");
		}else{
			Set<String> excludesNames = new TreeSet<String>();
			for (String e : excludeProperties) {
				excludesNames.add(e);
			}
			for (Field f : type.getFields()) {
				if (excludesNames.contains(f.getName())) {
					continue;
				}
				String fieldName = convertFieldName(f.getName());
				if (Modifier.isStatic(f.getModifiers())) {
					continue;
				}
				sql.append("`").append(fieldName).append("`").append(",");
			}
			sql.deleteCharAt(sql.length()-1);
		}
		sql.append(" from ").append(tableName);
		sql.append(" where 1=1");
		sql.append(qt.whereStatement());
		if(!qt.orderBys.isEmpty()){
			sql.append(" order by ");
			for(String k:qt.orderBys){
				sql.append(k).append(",");
			}
			sql.deleteCharAt(sql.length()-1);
		}
		if(qt.limitEnd!=-1){
			sql.append(" limit ").
			append(qt.limitStart).
			append(",").
			append(qt.limitEnd);
		}
		return sql.toString();
	}
	//
	private String queryCountSql(PUQueryTerms qt){
		StringBuilder sql=new StringBuilder();
		String tableName=getTableName();
		sql.append("select count(1) ");
		sql.append(" from ").append(tableName);
		sql.append(" where 1=1");
		sql.append(qt.whereStatement());
		if(!qt.orderBys.isEmpty()){
			sql.append(" order by ");
			for(String k:qt.orderBys){
				sql.append(k).append(",");
			}
			sql.deleteCharAt(sql.length()-1);
		}
		if(qt.limitEnd!=-1){
			sql.append(" limit ").
			append(qt.limitStart).
			append(",").
			append(qt.limitEnd);
		}
		return sql.toString();
	}
	
	protected int queryCount(PUQueryTerms qt){
        if (DB_TYPE_DATA == dbType) {
            return PUBaseDaoThreadPool.queryForInteger(PUGlobalPool.getDataThreadPool(), PUGlobalPool.getDbPool(), queryCountSql(qt), qt.whereValues());
        } else if (DB_TYPE_CONFIG == dbType) {
            return PUBaseDaoThreadPool.queryForInteger(PUGlobalPool.getConfigThreadPool(), PUGlobalPool.getConfigPool(), queryCountSql(qt), qt.whereValues());
        } else {
            return PUBaseDaoLocalThread.queryForInteger(conn, queryCountSql(qt), qt.whereValues());
        }
	}
	//
	protected T query(PUQueryTerms qt, String... excludeProperties){
		Class<?>type=getTypeClass();
		String sql = querySql(qt,excludeProperties);
        ResultSetHandler<T> resultSetHandler = new ResultSetHandler<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T handleRow(ResultSet row) throws Exception {
                T o=(T) type.newInstance();
                convertBean(o, row, excludeProperties);
                return o;
            }
        };
        if (DB_TYPE_DATA == dbType) {
            return PUBaseDaoThreadPool.queryForObject(PUGlobalPool.getDataThreadPool(), PUGlobalPool.getDbPool(), sql, resultSetHandler, qt.whereValues());
        } else if (DB_TYPE_CONFIG == dbType) {
            return PUBaseDaoThreadPool.queryForObject(PUGlobalPool.getConfigThreadPool(), PUGlobalPool.getConfigPool(), sql, resultSetHandler, qt.whereValues());
        } else {
            return PUBaseDaoLocalThread.queryForObject(conn, sql, resultSetHandler, qt.whereValues());
        }
	}
	//
	protected List<T>queryList(PUQueryTerms qt, String... excludeProperties){
		Class<?>type=getTypeClass();
        String sql = querySql(qt,excludeProperties);
        ResultSetHandler<T> resultSetHandler = new ResultSetHandler<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T handleRow(ResultSet row) throws Exception {
                T o=(T) type.newInstance();
                convertBean(o, row, excludeProperties);
                return o;
            }
        };
        if (DB_TYPE_DATA == dbType) {
            return PUBaseDaoThreadPool.queryForList(PUGlobalPool.getDataThreadPool(), PUGlobalPool.getDbPool(), sql, resultSetHandler, -1, qt.whereValues());
        } else if (DB_TYPE_CONFIG == dbType) {
            return PUBaseDaoThreadPool.queryForList(PUGlobalPool.getConfigThreadPool(), PUGlobalPool.getConfigPool(), sql, resultSetHandler, -1, qt.whereValues());
        } else {
            return PUBaseDaoLocalThread.queryForList(conn, sql, resultSetHandler, -1, qt.whereValues());
        }
	}
	//
	protected int insert(T o,boolean withGenerateKey,String... excludeProperties){
		StringBuilder sql=new StringBuilder();
		Class<?>type=getTypeClass();
		checkExcludeProperties(excludeProperties,type);
		String tableName=getTableName();
		sql.append("insert into ").append(tableName).append("(");
		Set<String> excludesNames = new TreeSet<String>();
		for (String e : excludeProperties) {
			excludesNames.add(e);
		}
		List<Object>fieldList=new ArrayList<Object>();
		for (Field f : type.getFields()) {
			if (excludesNames.contains(f.getName())) {
				continue;
			}
			String fieldName = convertFieldName(f.getName());
			if (Modifier.isStatic(f.getModifiers())) {
				continue;
			}
			try {
				Object fieldValue=f.get(o);
				if(fieldValue!=null&&!WRAP_TYPES.contains(fieldValue.getClass())){
					fieldList.add(JSON.toJSONString(fieldValue));
				}else{
					fieldList.add(fieldValue);
				}
			} catch (Exception e) {
				throw new ConnectionException(e);
			}
			sql.append("`").append(fieldName).append("`,");
		}
		sql.deleteCharAt(sql.length()-1);
		sql.append(")");
		sql.append("values(");
		for(int i=0;i<fieldList.size();i++){
			sql.append("?,");
		}
		sql.deleteCharAt(sql.length()-1);
		sql.append(")");
		if (DB_TYPE_DATA == dbType) {
            if(withGenerateKey){
                return PUBaseDaoThreadPool.executeWithGenKey(PUGlobalPool.getDataThreadPool(), PUGlobalPool.getDbPool(), sql.toString(), fieldList.toArray(new Object[fieldList.size()]));
            }else{
                PUBaseDaoThreadPool.execute(PUGlobalPool.getDataThreadPool(), PUGlobalPool.getDbPool(), sql.toString(), fieldList.toArray(new Object[fieldList.size()]));
                return 0;
            }
        } else if(DB_TYPE_CONFIG == dbType) {
            if(withGenerateKey){
                return PUBaseDaoThreadPool.executeWithGenKey(PUGlobalPool.getConfigThreadPool(), PUGlobalPool.getConfigPool(), sql.toString(), fieldList.toArray(new Object[fieldList.size()]));
            }else{
                PUBaseDaoThreadPool.execute(PUGlobalPool.getConfigThreadPool(), PUGlobalPool.getConfigPool(), sql.toString(), fieldList.toArray(new Object[fieldList.size()]));
                return 0;
            }
        }else {
            if(withGenerateKey){
                return PUBaseDaoLocalThread.executeWithGenKey(conn, sql.toString(), fieldList.toArray(new Object[fieldList.size()]));
            }else{
                PUBaseDaoLocalThread.execute(conn, sql.toString(), fieldList.toArray(new Object[fieldList.size()]));
                return 0;
            }
        }
	}
	//
	@SuppressWarnings("unchecked")
	protected T convertBean(ResultSet rs){
		Class<?>type=getTypeClass();
		try{
			Object instance=type.newInstance();
			convertBean(instance,rs);
			return (T) instance;
		}catch(Exception e){
			throw new ConnectionException(e);
		}
	} 
	//
	/**
	 * get data from result set and convert to bean 
	 */
	protected void convertBean(Object o, ResultSet rs, String... excludeProperties)
			throws Exception {
		Set<String> excludesNames = new TreeSet<String>();
		for (String e : excludeProperties) {
			excludesNames.add(e);
		}
		Class<?> type = o.getClass();
		checkExcludeProperties(excludeProperties,type);
		for (Field f : type.getFields()) {
			if (excludesNames.contains(f.getName())) {
				continue;
			}
			String fieldName = convertFieldName(f.getName());
			Class<?> fieldType = f.getType();
			if (Modifier.isStatic(f.getModifiers())) {
				continue;
			}
			Object value = null;
			if (fieldType.equals(String.class)) {
				value = rs.getString(fieldName);
			} else if (fieldType.equals(Integer.class)
					|| fieldType.equals(int.class)) {
				value = rs.getInt(fieldName);
			} else if (fieldType.equals(Short.class)
					|| fieldType.equals(short.class)) {
				value = rs.getShort(fieldName);
			} else if (fieldType.equals(Long.class)
					|| fieldType.equals(long.class)) {
				value = rs.getLong(fieldName);
			} else if (fieldType.equals(Double.class)
					|| fieldType.equals(double.class)) {
				value = rs.getDouble(fieldName);
			} else if (fieldType.equals(Float.class)
					|| fieldType.equals(float.class)) {
				value = rs.getFloat(fieldName);
			} else if (fieldType.equals(Date.class)) {
				value = rs.getTimestamp(fieldName);
			} else if (fieldType.equals(Boolean.class)
					|| fieldType.equals(boolean.class)) {
				value = rs.getBoolean(fieldName);
			} else if (fieldType.equals(BigDecimal.class)) {
				value = rs.getBigDecimal(fieldName);
			}  else if (fieldType.equals(byte[].class)) {
				Blob bb = rs.getBlob(fieldName);
				if (bb != null) {
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					IOUtil.copy(bb.getBinaryStream(), bos);
					value = bos.toByteArray();
				}
			} else {
				String strValue=rs.getString(fieldName);
				if(strValue!=null){
					value= JSON.parseObject(strValue,fieldType);
				}
			}
			f.setAccessible(true);
			if (value != null) {
				f.set(o, value);
			}
		}
	}
	//
	protected  String convertFieldName(String name) {
		StringBuffer result = new StringBuffer();
		for (char c : name.toCharArray()) {
			if (Character.isUpperCase(c)) {
				result.append("_");
			}
			result.append(Character.toLowerCase(c));
		}
		return result.toString();
	}
}
