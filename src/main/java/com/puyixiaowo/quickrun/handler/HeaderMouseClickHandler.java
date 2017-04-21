package com.puyixiaowo.quickrun.handler;

import com.puyixiaowo.quickrun.dialog.MainDialog;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author weishaoqiang
 * @date 2017-04-01 9:58
 */
public class HeaderMouseClickHandler extends MouseAdapter {

	@Override
	public void mousePressed(MouseEvent e) {
		MainDialog.pointHeader.setLocation(e.getPoint());
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			//右键点击关闭窗口
			MainDialog.hideDialog();
		}
	}
}
