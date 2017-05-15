package com.puyixiaowo.quickrun.handler.menu.right;

import com.puyixiaowo.quickrun.dialog.EditShortCutDialog;
import com.puyixiaowo.quickrun.entity.Config;
import com.puyixiaowo.quickrun.entity.ShortCut;
import com.puyixiaowo.quickrun.utils.IconUtils;
import com.puyixiaowo.quickrun.utils.ImageUtils;
import com.puyixiaowo.quickrun.utils.Message;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * 提取图标按钮
 * @author huangfeihong
 * @date 2017-03-17
 */
public class ExtractIconCutHandler implements ActionListener {
	final JFileChooser fc = new JFileChooser();

	@Override
	public void actionPerformed(ActionEvent e) {
		ShortCut shortCut = EditShortCutDialog.shortCut;
		String rootAbsolutePath = Config.getInstance().getRootconfigPath() + Config.ICON_DIR;
		String iconPath = shortCut.getName() + ".png";
		fc.setCurrentDirectory(new File(rootAbsolutePath));
		int returnVal = fc.showOpenDialog(EditShortCutDialog.getInstance());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();//选中文件



			String fullpath = rootAbsolutePath + iconPath;
			Icon icon = null;

			if (ImageUtils.isImage(file.getAbsolutePath())) {
				icon = new ImageIcon(file.getAbsolutePath());
			} else {
				icon = IconUtils.getIcon(file, 64);//获取程序图标
			}
			if (icon == null) {
				Message.error(null, "无法获取图标");
				return;
			}

			//生成图片
			File iconFile = new File(fullpath);

			if (!iconFile.getParentFile().exists()) {
				iconFile.mkdirs();
			}
			ImageIcon imageIcon = (ImageIcon)icon;
			try {
				ImageIO.write(ImageUtils.convertToBufferedImage(imageIcon.getImage()), "png", iconFile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			shortCut.setTextIcon(iconPath);
			EditShortCutDialog.fillData(shortCut);
		} else {
//			System.out.println("取消选择");
		}
	}
}
