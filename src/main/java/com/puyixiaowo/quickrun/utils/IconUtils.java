package com.puyixiaowo.quickrun.utils;

import com.puyixiaowo.quickrun.entity.Config;
import sun.awt.shell.ShellFolder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author huangfeihong
 * @date 2017-03-13
 */
public class IconUtils {

	private final static String ICON_DIR = "icon";

	/**
	 * 通过文件名获取图片图标资源
	 * @param fileName
	 * @return
	 */
	public static ImageIcon getImageIcon(String iconName){
		InputStream input = ResourceUtil.getInpuStream("/" + ICON_DIR  + "/" + iconName);
		try {
			ImageIcon icon = new ImageIcon();
			Image image = ImageIO.read(input);
			icon.setImage(image);
			return icon;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Icon getIcon(File file, int size) {
		ShellFolder shellFolder = null;
		Icon icon = null;
		try {
			shellFolder = ShellFolder.getShellFolder(file);
		} catch (FileNotFoundException e) {
			//Message.error(null, "获取图标异常：" + e.getMessage());
		}
		if (shellFolder != null) {
			icon = new ImageIcon(shellFolder.getIcon(true).getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH));
		}
		return icon;
	}

	public static File getIconFile(String iconName) {
		String iconPath = Config.getInstance().getRootconfigPath();
		return new File(iconPath + ICON_DIR + "/" + iconName);
	}
}
