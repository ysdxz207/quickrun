package com.puyixiaowo.quickrun.handler.menu.right;

import com.puyixiaowo.quickrun.dialog.MainDialog;
import com.puyixiaowo.quickrun.entity.Config;
import com.puyixiaowo.quickrun.entity.ShortCut;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author huangfeihong
 * @date 2017-03-17
 */
public class DeleteShortCutHandler implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem item = (JMenuItem) e.getSource();
		JPopupMenu menu = (JPopupMenu) item.getParent();
		JList jList = (JList) menu.getInvoker();
		ShortCut shortCut = (ShortCut) jList.getSelectedValue();

		if (shortCut != null) {
			Config.deleteConfig(shortCut.getName(), -1);
		}
	}
}
