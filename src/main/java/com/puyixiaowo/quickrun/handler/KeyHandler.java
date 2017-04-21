package com.puyixiaowo.quickrun.handler;/**
 * Created by ysdxz207 on 2017-03-11 0011.
 */

import com.puyixiaowo.quickrun.dialog.MainDialog;
import com.puyixiaowo.quickrun.entity.Config;
import com.puyixiaowo.quickrun.entity.ShortCut;
import com.puyixiaowo.quickrun.utils.ExeUtils;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * @author feihong
 * @date 2017-03-11 0:07
 */
public class KeyHandler extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        JList jList = (JList) e.getSource();
        ShortCut shortCut = (ShortCut) jList.getSelectedValue();
        //List<ShortCut> selectList = jList.getSelectedValuesList();
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //打开程序
            if (shortCut != null) {
                MainDialog.hideDialog();
                try {
                    ExeUtils.run(shortCut);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            //删除快捷方式
//            if (selectList != null && selectList.size() > 0) {
//
//                for (ShortCut shortCut1:
//                     selectList) {
//                    Config.deleteConfig(shortCut1.getName());
//                }
//            }
            if (shortCut != null) {

                int nextIndex = jList.getSelectedIndex();
                nextIndex = (nextIndex >= jList.getModel().getSize() - 1) ? -1 : nextIndex;
                System.out.println(nextIndex);
                Config.deleteConfig(shortCut.getName(), nextIndex);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            //移除选中
            jList.clearSelection();
            jList.setSelectedIndex(-1);
        }
    }
}
