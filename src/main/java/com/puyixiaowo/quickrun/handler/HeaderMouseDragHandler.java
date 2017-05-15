package com.puyixiaowo.quickrun.handler;/**
 * Created by ysdxz207 on 2017-03-10 0010.
 */

import com.puyixiaowo.quickrun.dialog.MainDialog;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author feihong
 * @date 2017-03-31
 */
public class HeaderMouseDragHandler extends MouseAdapter {

    @Override
    public void mouseDragged(MouseEvent e) {
        Point pWin = MainDialog.getInstance().getLocation();
        Point p = new Point();
        p.setLocation(pWin.getX() + e.getX() - MainDialog.pointHeader.getX(), pWin.getY() + e.getY() - MainDialog.pointHeader.getY());
        MainDialog.getInstance().setLocation(p);
    }
}
