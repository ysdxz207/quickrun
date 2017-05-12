package com.puyixiaowo.quickrun.utils;

/**
 * @author huangfeihong
 * @date 2017-05-12
 */
public class ArrayUtils {
	public static String arrayToString(String[] var0) {
		if(var0 == null) {
			return null;
		} else {
			StringBuffer var1 = new StringBuffer();

			for(int var2 = 0; var2 < var0.length; ++var2) {
				var1.append((var0[var2] != null?var0[var2].replaceAll("\\s", ""):"") + " ");
			}

			return var1.toString();
		}
	}

	public static boolean isEmpty(String[] arr) {
		return StringUtils.isBlank(ArrayUtils.arrayToString(arr));
	}
}
