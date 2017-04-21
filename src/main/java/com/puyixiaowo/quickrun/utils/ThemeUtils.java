package com.puyixiaowo.quickrun.utils;

import com.puyixiaowo.quickrun.enums.Colors;

import java.awt.*;
import java.io.File;

/**
 * @author weishaoqiang
 * @date 2017-04-07 16:55
 */
public class ThemeUtils {
	public static String BG_HEAD = "images/head/head.gif";
	private static final double FACTOR = 0.5;

	/*
	 * 通过头部图片主色设置主题色
	 */
	public static Color getHeadBgColor() {
		try {
			String color = ImageUtils.
					getDominantColor(new File(ThemeUtils.BG_HEAD));
			return brightenColor(ColorUtil.string2Color(color), FACTOR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ColorUtil.string2Color(Colors.BG_MAIN.color);
	}

	public static Color brightenColor(Color color, double fraction) {

		int red = (int) Math.round(Math.min(255, color.getRed() + (255-color.getRed()) / 4 * 5 * fraction));
		int green = (int) Math.round(Math.min(255, color.getGreen() + (255-color.getGreen()) / 4 * 5 * fraction));
		int blue = (int) Math.round(Math.min(255, color.getBlue() + (255-color.getBlue()) / 4 * 5 * fraction));

		int alpha = color.getAlpha();

		return new Color(red, green, blue, alpha);

	}

	public static void main(String[] args) throws Exception {
		String color = ImageUtils.getDominantColor(new File("C:\\Users\\Administrator\\Desktop\\timg.gif"));
		Color c = brightenColor(ColorUtil.string2Color(color), FACTOR);
		System.out.println(ColorUtil.color2String(c));
	}

}
