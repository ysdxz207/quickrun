package com.puyixiaowo.quickrun.utils;

import sun.awt.shell.ShellFolder;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author huangfeihong
 * @date 2017-03-13
 */
public class IconUtils {

	public static Icon getIcon(File file, int size) {
		ShellFolder shellFolder = null;
		Icon icon = null;
		try {
			shellFolder = ShellFolder.getShellFolder(file);
		} catch (FileNotFoundException e) {
			//JOptionPane.showMessageDialog(null, "获取图标异常：" + e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
		}
		if (shellFolder != null) {
			icon = new ImageIcon(shellFolder.getIcon(true).getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH));
		}
		return icon;
	}
}
