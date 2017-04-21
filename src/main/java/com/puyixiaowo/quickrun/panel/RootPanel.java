package com.puyixiaowo.quickrun.panel;

import com.puyixiaowo.quickrun.dialog.MainDialog;
import com.puyixiaowo.quickrun.utils.ThemeUtils;
import com.sun.imageio.plugins.gif.GIFImageMetadata;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RootPanel extends JPanel {

	private ImageReader reader;
	private int count = 0;
	private GifFrame[] frames;
	private Map<Integer, Integer[]> frameMap = new HashMap<Integer, Integer[]>();
	private int index = 0;
	private int delayFactor = 20;//动画延迟

	public static int height;

	public RootPanel() {


//            setBorder(new CompoundBorder(
//					new LineBorder(Color.RED),
//                            new EmptyBorder(0, 0, 0, 0)));

		setOpaque(false);
		setLayout(null);
		initGif(new File(ThemeUtils.BG_HEAD));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
			if (index > 0) {
				Integer[] array = frameMap.get(index);

				for (Integer i : array) {
					g.drawImage(frames[i].image, calculateX(frames[i].width), calculateY(frames[i].height), this);
				}
			} else {
				g.drawImage(frames[0].image, calculateX(frames[0].width), calculateY(frames[0].height), this);
			}
	}

	private int calculateX(int width) {
		return (MainDialog.WIDTH - width) / 2;
	}

	private int calculateY(int height) {
		return MainDialog.HEIGHT-MainDialog.HEIGHT_MAIN_LIST - height;
	}

	private class Delay implements Runnable {

		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(frames[index].delayTime * delayFactor);
				} catch (InterruptedException e) {
				}

				index++;
				if (index >= count) {
					index = 0;
				}
			}
		}
	}

	public void initGif(File gifFile) {
		try {

			ImageInputStream imageIn = ImageIO.createImageInputStream(gifFile);

			Iterator<ImageReader> iter = ImageIO.getImageReadersByFormatName("gif");
			if (iter.hasNext()) {
				reader = iter.next();
			}
			reader.setInput(imageIn, false);
			count = reader.getNumImages(true);
			frames = new GifFrame[count];
			for (int i = 0; i < count; i++) {
				frames[i] = new GifFrame();
				frames[i].image = reader.read(i);
				frames[i].x = ((GIFImageMetadata) reader.getImageMetadata(i)).imageLeftPosition;
				frames[i].y = ((GIFImageMetadata) reader.getImageMetadata(i)).imageTopPosition;
				frames[i].width = ((GIFImageMetadata) reader.getImageMetadata(i)).imageWidth;
				frames[i].height = ((GIFImageMetadata) reader.getImageMetadata(i)).imageHeight;
				frames[i].disposalMethod = ((GIFImageMetadata) reader.getImageMetadata(i)).disposalMethod;
				frames[i].delayTime = ((GIFImageMetadata) reader.getImageMetadata(i)).delayTime;
				if (frames[i].delayTime == 0) {
					frames[i].delayTime = 1;
				}
			}
			for (int i = 1; i < count; i++) {
				if (frames[i].disposalMethod == 2) {
					// restoreToBackgroundColor
					frameMap.put(new Integer(i), new Integer[]{i});
					continue;
				}
				// doNotDispose
				int firstIndex = getFirstIndex(i);
				java.util.List<Integer> l = new ArrayList<Integer>();
				for (int j = firstIndex; j <= i; j++) {
					l.add(j);
				}
				frameMap.put(new Integer(i), l.toArray(new Integer[]{}));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread t = new Thread(new Delay());
		t.start();

		height = frames[0].height;
	}

	private int getFirstIndex(int index) {
		int tempIndex = index;
		while (tempIndex > 1) {
			if (tempIndex - 1 > 0 && frames[tempIndex - 1].disposalMethod == 2) {
				return index;
			}
			tempIndex--;
		}
		return tempIndex;
	}
	class GifFrame {

		public BufferedImage image;
		public int x;
		public int y;
		public int width;
		public int height;
		public int disposalMethod;
		public int delayTime;
	}
}