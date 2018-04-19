package com.puyixiaowo.quickrun.main;

import com.puyixiaowo.quickrun.dialog.MainDialog;
import com.puyixiaowo.quickrun.utils.AppUtils;
import org.pushingpixels.substance.api.skin.*;

import javax.swing.*;

/**
 * @author huangfeihong
 * @date 2016年12月25日 下午5:33:49
 */
public class Main {
    public static void main(String[] args) {
        AppUtils.checkIfRunning();
        MainDialog.getInstance();
        try {
            UIManager.setLookAndFeel( new SubstanceGraphiteLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

    }
}