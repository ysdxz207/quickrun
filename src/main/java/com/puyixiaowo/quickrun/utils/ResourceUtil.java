package com.puyixiaowo.quickrun.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author weishaoqiang
 * @date 2016年12月25日 下午10:02:10
 */
public class ResourceUtil {
	/**
	 * 通过文件名获取图片图标资源
	 * @param fileName
	 * @return
	 */
	public static ImageIcon getImageIcon(String fileName){
		InputStream input = ResourceUtil.class.getResourceAsStream("/icon/" + fileName);
		try {
			ImageIcon icon = new ImageIcon();
			Image image =ImageIO.read(input);
			icon.setImage(image);
			return icon;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}