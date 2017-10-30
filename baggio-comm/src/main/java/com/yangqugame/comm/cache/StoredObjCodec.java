package com.yangqugame.comm.cache;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * redis 缓存对象编码类
 * 
 * @author jqh
 *
 */
public class StoredObjCodec {

	public final static String SUPPORTED_CHARSET_NAME = "UTF-8"; // this is for
																	// jdk 1.5
																	// compliance

	/**
	 * This helper method is mainly intended for use with a list of keys
	 * returned from Redis, given that it will use the UTF-8 {@link Charset} in
	 * decoding the byte array. Typical use would be to convert from the
	 * List<byte[]> output of {}
	 * 
	 * @param bytearray
	 * @return
	 */
	public static final List<String> toStr(List<byte[]> bytearray) {
		List<String> list = new ArrayList<String>(bytearray.size());
		for (byte[] b : bytearray)
			if (null != b)
				list.add(toStr(b));
			else
				list.add(null);
		return list;
	}

	/**
	 * @param bytes
	 * @return new {@link String#String(byte[])} or null if bytes is null.
	 */
	public static final String toStr(byte[] bytes) {
		String str = null;
		if (null != bytes) {
			try {
				str = new String(bytes, SUPPORTED_CHARSET_NAME);

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return str;
		// return new String(bytes, SUPPORTED_CHARSET); // Java 1.6 only
	}

	public static final byte[] encode(String value) {
		byte[] bytes = null;
		try {
			bytes = value.getBytes(SUPPORTED_CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return bytes;
		// return value.getBytes(SUPPORTED_CHARSET);
	}

	// /**
	// * @param bytes
	// * @return
	// */
	// @Deprecated
	// public static final Integer toInt (byte[] bytes) {
	// return new Integer(toStr (bytes));
	// }

	/**
	 * NOTE: Isn't this already in {@link Convert#toLong(byte[])}? This helper
	 * method will convert the byte[] to a {@link Long}.
	 * 
	 * @param bytes
	 * @return
	 */
	// @Deprecated
	public static final Long toLong(byte[] bytes) {
		// return new Long (toStr (bytes));
		return Convert.toLong(bytes);
	}

	public static final List<Long> toLong(List<byte[]> bytearray) {
		List<Long> list = new ArrayList<Long>(bytearray.size());
		for (byte[] b : bytearray)
			list.add(Convert.toLong(b));
		return list;
	}

	public static final List<Double> toDouble(List<byte[]> bytearray) {
		List<Double> list = new ArrayList<Double>(bytearray.size());
		for (byte[] b : bytearray)
			list.add(Convert.toDouble(b));
		return list;
	}

	/**
	 * @param bs
	 * @return
	 */
	public static double toDouble(byte[] bs) {
		return Convert.toDouble(bs);
	}

	@SuppressWarnings("unchecked")
	public static final <T extends StoredObj> Set<T> decode(Set<byte[]> set, Class<? extends StoredObj> c) {
		Set<T> objectList = new HashSet<T>(set.size());
		for (byte[] bytes : set) {
			if (null != bytes) {
				T object = (T) decode(bytes, c);
				objectList.add(object);
			} else {
				objectList.add(null);
			}
		}
		return objectList;
	}

	@SuppressWarnings("unchecked")
	public static final <T extends StoredObj> List<T> decode(List<byte[]> list, Class<? extends StoredObj> c) {
		List<T> objectList = new ArrayList<T>(list.size());
		for (byte[] bytes : list) {
			if (null != bytes) {
				T object = (T) decode(bytes, c);
				objectList.add(object);
			} else {
				objectList.add(null);
			}
		}
		return objectList;
	}

	@SuppressWarnings("unchecked")
	public static final <T extends StoredObj> T decode(byte[] bytes, Class<? extends StoredObj> c) {
		T t = null;
		Exception thrown = null;
		try {
			t = (T) JSON.parseObject(toStr(bytes), c);
		} catch (ClassCastException e) {
			e.printStackTrace();
			thrown = e;
		} finally {
			if (null != thrown)
				throw new RuntimeException("Error decoding byte[] data to instantiate java object - " + "data at key may not have been of this type or even an object", thrown);
		}
		return t;
	}

	/**
	 * This helper method will serialize the given StoredObj object of type T to
	 * a byte[], suitable for use as a value for a redis key, regardless of the
	 * key type.
	 * 
	 * @param <T>
	 * @param obj
	 * @return
	 */
	public static final <T extends StoredObj> byte[] encode(T obj) {
		byte[] bytes = null;
		try {
			bytes = JSON.toJSONString(obj).getBytes(SUPPORTED_CHARSET_NAME);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error serializing object" + obj + " => " + e);
		}
		return bytes;
	}
}
