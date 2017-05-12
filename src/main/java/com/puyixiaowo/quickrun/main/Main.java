package com.puyixiaowo.quickrun.main;

import com.puyixiaowo.quickrun.dialog.MainDialog;
import com.puyixiaowo.quickrun.utils.AppUtils;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;

/**
 * @author huangfeihong
 * @date 2016年12月25日 下午5:33:49
 */
public class Main {
	public static void main(String[] args) {
		AppUtils.checkIfRunning();

		try {
			// 设置此开关量为false即表示关闭之，BeautyEye LNF中默认是true
			BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
			// 设置本属性将改变窗口边框样式定义
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("RootPane.setupButtonVisible", false);// 关闭设置按钮
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),
					"错误", JOptionPane.ERROR_MESSAGE);
		}

		new MainDialog();
	}
}