package com.puyixiaowo.quickrun.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author huangfeihong
 * @date 2016年12月25日 下午10:02:10
 */
public class ResourceUtil {

	public static InputStream getInpuStream(String fileName) {
		return ResourceUtil.class.getResourceAsStream(fileName);
	}
}