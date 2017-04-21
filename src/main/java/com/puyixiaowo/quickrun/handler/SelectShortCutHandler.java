package com.puyixiaowo.quickrun.handler;/**
 * Created by ysdxz207 on 2017-03-10 0010.
 */

import com.puyixiaowo.quickrun.entity.ShortCut;
import com.puyixiaowo.quickrun.utils.ExeUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.IOException;

/**
 * @author feihong
 * @date 2017-03-10 23:10
 */
public class SelectShortCutHandler implements ListSelectionListener {
    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList jList = (JList)e.getSource();

        if ( !e.getValueIsAdjusting() && !jList.isSelectionEmpty()) {
            ShortCut shortCut= (ShortCut) jList.getSelectedValue();
            try {
                ExeUtils.run(shortCut);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
