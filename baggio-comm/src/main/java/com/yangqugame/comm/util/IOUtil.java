/**
 * 
 */
package com.yangqugame.comm.util;


import java.io.*;

/**
 * @author yama 26 Dec, 2014
 */
public class IOUtil {
	private static final int CACHE_SIZE = 512;

	/**
	 * copy inputstream data to output stream
	 */
	public static long copy(InputStream input, OutputStream output)
			throws IOException {
		byte[] buffer = new byte[4096];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
	/**
	 * copy input stream to writer
	 */
	public static void copy(InputStream input, Writer output)
			throws IOException {
		InputStreamReader in = new InputStreamReader(input); // NOSONAR
		copy(in, output);
	}
	/**
	 *copy input reader to output writer
	 */
	public static int copy(Reader input, Writer output) throws IOException {
		long count = copyLarge(input, output);
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return (int) count;
	}
	//
	public static long copyLarge(Reader input, Writer output)
			throws IOException {
		char[] buffer = new char[1024 * 4];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
}
