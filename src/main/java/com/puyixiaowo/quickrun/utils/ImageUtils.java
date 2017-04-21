package com.puyixiaowo.quickrun.utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

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

	///////////////
	public static String getDominantColor(File file) throws Exception {
		ImageInputStream is = ImageIO.createImageInputStream(file);
		Iterator iter = ImageIO.getImageReaders(is);

		if (!iter.hasNext()) {
			System.out.println("Cannot load the specified file " + file);
			System.exit(1);
		}
		ImageReader imageReader = (ImageReader) iter.next();
		imageReader.setInput(is);

		BufferedImage image = imageReader.read(0);

		int height = image.getHeight();
		int width = image.getWidth();

		Map m = new HashMap();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = image.getRGB(i, j);
				int[] rgbArr = getRGBArr(rgb);
				// Filter out grays....
				if (!isGray(rgbArr)) {
					Integer counter = (Integer) m.get(rgb);
					if (counter == null)
						counter = 0;
					counter++;
					m.put(rgb, counter);
				}
			}
		}
		return getMostCommonColour(m);
	}


	private static String getMostCommonColour(Map map) {
		java.util.List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});
		Map.Entry me = (Map.Entry) list.get(list.size() - 1);
		int[] rgb = getRGBArr((Integer) me.getKey());
		String r = Integer.toHexString(rgb[0]);
		String g = Integer.toHexString(rgb[1]);
		String b = Integer.toHexString(rgb[2]);
		r = r.length() == 2 ? r : r + "0";
		g = g.length() == 2 ? g : g + "0";
		b = b.length() == 2 ? b : b + "0";
		return "#" + r + g + b;
	}

	private static int[] getRGBArr(int pixel) {
		int alpha = (pixel >> 24) & 0xff;
		int red = (pixel >> 16) & 0xff;
		int green = (pixel >> 8) & 0xff;
		int blue = (pixel) & 0xff;
		return new int[]{red, green, blue};

	}

	private static boolean isGray(int[] rgbArr) {
		int rgDiff = rgbArr[0] - rgbArr[1];
		int rbDiff = rgbArr[0] - rgbArr[2];
		// Filter out black, white and grays...... (tolerance within 10 pixels)
		int tolerance = 10;
		if (rgDiff > tolerance || rgDiff < -tolerance)
			if (rbDiff > tolerance || rbDiff < -tolerance) {
				return false;
			}
		return true;
	}

}
