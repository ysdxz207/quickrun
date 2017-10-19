package com.puyixiaowo.quickrun.handler.menu.right;

import com.puyixiaowo.quickrun.dialog.EditShortCutDialog;
import com.puyixiaowo.quickrun.entity.Config;
import com.puyixiaowo.quickrun.entity.ShortCut;
import com.puyixiaowo.quickrun.utils.Message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 修改快捷键信息确定按钮
 * @author huangfeihong
 * @date 2017-03-17
 */
public class EditSaveShortCutHandler implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {

		String name = EditShortCutDialog.getInstance().getTextFieldName().getText();
		String link = EditShortCutDialog.getInstance().getTextFieldLink().getText();
		String target = EditShortCutDialog.getInstance().getTextFieldTarget().getText();
		String cmdArgs = EditShortCutDialog.getInstance().getTextFieldCmdArgs().getText();
		String textIcon = EditShortCutDialog.getInstance().getTextFieldTextIcon().getText();

		ShortCut shortCut = new ShortCut();

		shortCut.setId(EditShortCutDialog.shortCut.getId());
		shortCut.setIndex(EditShortCutDialog.shortCut.getIndex());
		shortCut.setName(name);
		shortCut.setLink(link);
		shortCut.setTarget(target);
		shortCut.setCmdArgs(cmdArgs);
		shortCut.setTextIcon(textIcon);

		try {
			Config.updateShorCut(shortCut);
			EditShortCutDialog.hideDialog();
		} catch (Exception e1) {
			Message.error(null, e1.getMessage());
		}

	}
}
