package com.puyixiaowo.quickrun.utils;

import java.awt.Color;

/**
 * @author huangfeihong
 * @date 2016年12月12日 下午10:43:03
 */
public class ColorUtil {
	public static String color2String(Color color) {
		String R = Integer.toHexString(color.getRed());
		R = R.length() < 2 ? ('0' + R) : R;
		String B = Integer.toHexString(color.getBlue());
		B = B.length() < 2 ? ('0' + B) : B;
		String G = Integer.toHexString(color.getGreen());
		G = G.length() < 2 ? ('0' + G) : G;
		return '#' + R + B + G;
	}

	public static Color string2Color(String str) {
		int i = Integer.parseInt(str.substring(1), 16);
		return new Color(i);
	}
}