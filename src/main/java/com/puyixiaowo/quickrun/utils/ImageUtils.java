package com.puyixiaowo.quickrun.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author weishaoqiang
 * @date 2017-03-17 16:04
 */
public class ImageUtils {
	public static BufferedImage convertToBufferedImage(Image image) {
		BufferedImage newImage = new BufferedImage(
				image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newImage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return newImage;
	}

	public static boolean isImage(String fullpath) {
		boolean valid = true;
		try {
			Image image = ImageIO.read(new File(fullpath));
			if (image == null) {
				valid = false;
			}
		} catch (IOException ex) {
			valid = false;
		}
		return valid;
	}
}
