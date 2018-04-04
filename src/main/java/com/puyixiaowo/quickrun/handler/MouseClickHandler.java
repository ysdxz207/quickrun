package com.puyixiaowo.quickrun.handler;/**
 * Created by ysdxz207 on 2017-03-10 0010.
 */

import com.puyixiaowo.quickrun.dialog.EditShortCutDialog;
import com.puyixiaowo.quickrun.dialog.MainDialog;
import com.puyixiaowo.quickrun.entity.ShortCut;
import com.puyixiaowo.quickrun.renderer.CellRenderer;
import com.puyixiaowo.quickrun.utils.ExecUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * @author feihong
 * @date 2017-03-10
 */
public class MouseClickHandler extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        JList jList = (JList) e.getSource();
        int index = jList.locationToIndex(e.getPoint());

        if (jList.locationToIndex(e.getPoint()) == -1 && !e.isShiftDown()
                && !isMenuShortcutKeyDown(e)) {
            jList.clearSelection();
        }

        //左键打开程序
        if (e.getButton() == MouseEvent.BUTTON1) {

            ShortCut shortCut = (ShortCut) jList.getSelectedValue();
            if (shortCut != null) {
                MainDialog.hideDialog();
                ExecUtils.run(shortCut);
            }
        }

        if (e.getButton() == MouseEvent.BUTTON2) {
            //中键选中

            jList.setSelectedIndex(index);
        }

        if (e.getButton() == MouseEvent.BUTTON3) {
            //右键编辑
            jList.setSelectedIndex(index);
            ShortCut shortCut = (ShortCut) jList.getSelectedValue();

            if (shortCut != null) {
                shortCut.setIndex(jList.getSelectedIndex());

                //编辑快捷键对话框
                EditShortCutDialog.showDialog(shortCut);
            }

        }

        //hover样式
        Component component = jList.getComponentAt(e.getPoint());
        if (component instanceof CellRenderer) {
            CellRenderer cellRender = (CellRenderer) component;
            cellRender.setBackground(Color.RED);
        }
    }

    private boolean isMenuShortcutKeyDown(InputEvent event){
        return (event.getModifiers() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0;
    }


    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        JList jList = (JList) e.getSource();
        int index = jList.getSelectedIndex();

        JScrollPane pane = (JScrollPane) jList.getParent().getParent();
        JScrollBar bar = pane.getVerticalScrollBar();


        if (e.getWheelRotation() == 1) {
            index++;
            jList.setSelectedIndex(index);

            if (index > 0) {
                bar.setValue(bar.getValue() + 18);
            }
        }
        if (e.getWheelRotation() == -1) {
            index--;
            if (index < -1) {
                index = jList.getModel().getSize() - 1;
            }
            jList.setSelectedIndex(index);
            if (index < jList.getModel().getSize()) {
                bar.setValue(bar.getValue() - 18);
            }
        }
    }

}
