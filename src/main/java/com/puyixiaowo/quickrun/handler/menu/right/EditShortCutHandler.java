package com.puyixiaowo.quickrun.handler.menu.right;

import com.puyixiaowo.quickrun.dialog.EditShortCutDialog;
import com.puyixiaowo.quickrun.dialog.MainDialog;
import com.puyixiaowo.quickrun.entity.ShortCut;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 编辑快捷键右键菜单选项
 * @author huangfeihong
 * @date 2017-03-16
 */
public class EditShortCutHandler implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem item = (JMenuItem) e.getSource();
		JPopupMenu menu = (JPopupMenu) item.getParent();
		JList jList = (JList) menu.getInvoker();
		ShortCut shortCut = (ShortCut) jList.getSelectedValue();

		if (shortCut != null) {
			shortCut.setIndex(jList.getSelectedIndex());

			//编辑快捷键对话框
			new EditShortCutDialog(MainDialog.self, "编辑快捷方式", true).showDialog(shortCut);
		}
	}
}
