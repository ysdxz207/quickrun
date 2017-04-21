package com.puyixiaowo.quickrun.handler;/**
 * Created by ysdxz207 on 2017-03-10 0010.
 */

import com.puyixiaowo.quickrun.constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author feihong
 * @date 2017-03-10 23:45
 */
public class MouseMovedHandler extends MouseAdapter {

    @Override
    public void mouseMoved(MouseEvent e) {
        JList jList = (JList) e.getSource();

        setHoverIndex(jList, e.getPoint());
    }

    private void setHoverIndex(JList jList, Point p) {
        int index = jList.locationToIndex(p);
        Rectangle rectangle = jList.getCellBounds(index, index);
        if (rectangle == null) {
            return;
        }
        index = rectangle.contains(p)
                ? index : -1;
        if (Constants.hoverIndex == index) return;
        Constants.hoverIndex = index;
        jList.repaint();
    }

}
