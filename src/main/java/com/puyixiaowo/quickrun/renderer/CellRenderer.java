package com.puyixiaowo.quickrun.renderer;

import com.puyixiaowo.quickrun.constants.Constants;
import com.puyixiaowo.quickrun.entity.Config;
import com.puyixiaowo.quickrun.entity.ShortCut;
import com.puyixiaowo.quickrun.enums.Colors;
import com.puyixiaowo.quickrun.enums.ShortCutStatus;
import com.puyixiaowo.quickrun.utils.ColorUtil;
import com.puyixiaowo.quickrun.utils.IconUtils;
import com.puyixiaowo.quickrun.utils.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author huangfeihong
 * @date 2017-03-13
 */
public class CellRenderer extends JLabel implements ListCellRenderer, Serializable {

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		ListModel model = list.getModel();
		ShortCut shortCut = (ShortCut) model.getElementAt(index);

		if (StringUtils.isNotBlank(shortCut.getTextIcon()) &&
				IconUtils.getIconFile(shortCut.getTextIcon()).exists()) {
			Icon textIcon = null;
			try {
				textIcon = new ImageIcon(ImageIO.read(IconUtils.getIconFile(shortCut.getTextIcon())).getScaledInstance(Constants.ICON_SIZE, Constants.ICON_SIZE, Image.SCALE_SMOOTH));
			} catch (IOException e) {
				e.printStackTrace();
			}
			setIcon(textIcon);
		} else {
			Icon icon = IconUtils.getIcon(new File(shortCut.getTarget()), Constants.ICON_SIZE);
			setIcon(icon);
		}
		setText(value.toString());
		setFont(new Font("宋体", 0, 18));
		setOpaque(true);//设置不透明，背景色才会生效

		if (isSelected) {
			setBackground(ColorUtil.string2Color(Colors.BG_LIST_SELECT.color));
			setForeground(ColorUtil.string2Color(Colors.FG_LIST_SELECT.color));
		} else {
			// 设置选取与取消选取的前景与背景颜色.
			setBackground(Color.WHITE);
			switch (ShortCutStatus.getEnumByStatus(shortCut.getStatus())) {

				case EMPTY_PATH:
					setForeground(ColorUtil.string2Color(Colors.COLOR_SHORTCUT_EMPTY_PATH.color));
					setFont(new Font("仿宋", 1, 18));
					break;
				case NOT_EXISTS:
					setForeground(ColorUtil.string2Color(Colors.GETCOLOR_SHORTCUT_NOT_EXISTS.color));
					setFont(new Font("仿宋", 2, 18));
					break;
				default:
					setForeground(ColorUtil.string2Color(Colors.COLOR_SHORTCUT_USEABLE.color));
					break;
			}
			setBackground(index == Constants.hoverIndex
					? ColorUtil.string2Color(Colors.BG_LIST_HOVER.color) : list.getBackground());

		}

		return this;
	}
}
