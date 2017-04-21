package com.puyixiaowo.quickrun.handler.menu.right;

import com.puyixiaowo.quickrun.dialog.EditShortCutDialog;
import com.puyixiaowo.quickrun.entity.Config;
import com.puyixiaowo.quickrun.entity.ShortCut;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 修改快捷键信息确定按钮
 * @author weishaoqiang
 * @date 2017-03-17 11:08
 */
public class EditSaveShortCutHandler implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {

		String name = EditShortCutDialog.self.getTextFieldName().getText();
		String link = EditShortCutDialog.self.getTextFieldLink().getText();
		String cmdArgs = EditShortCutDialog.self.getTextFieldCmdArgs().getText();
		String textIcon = EditShortCutDialog.self.getTextFieldTextIcon().getText();

		ShortCut shortCut = new ShortCut();

		shortCut.setIndex(EditShortCutDialog.shortCut.getIndex());
		shortCut.setName(name);
		shortCut.setLink(link);
		shortCut.setCmdArgs(cmdArgs);
		shortCut.setTextIcon(textIcon);

		try {
			Config.updateShorCut(shortCut);
			EditShortCutDialog.hideDialog();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(EditShortCutDialog.self,
					e1.getMessage(),
					"添加快捷方式错误", JOptionPane.ERROR_MESSAGE);
		}

	}
}
