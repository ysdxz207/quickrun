package com.puyixiaowo.quickrun.handler;/**
 * Created by ysdxz207 on 2017-03-10 0010.
 */

import com.puyixiaowo.quickrun.entity.ShortCut;
import com.puyixiaowo.quickrun.utils.ExecUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author feihong
 * @date 2017-03-10
 */
public class SelectShortCutHandler implements ListSelectionListener {
    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList jList = (JList)e.getSource();

        if ( !e.getValueIsAdjusting() && !jList.isSelectionEmpty()) {
            ShortCut shortCut= (ShortCut) jList.getSelectedValue();
            ExecUtils.run(shortCut);
        }
    }
}
