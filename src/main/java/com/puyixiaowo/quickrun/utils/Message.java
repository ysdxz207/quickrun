package com.puyixiaowo.quickrun.utils;

import javax.swing.*;
import java.awt.*;

/**
 * @author huangfeihong
 * @date 2017-05-12
 */
public class Message {
	public static void alert(Window parent, String msg){
		JOptionPane.showMessageDialog(parent,
				msg,
				"提示", JOptionPane.WARNING_MESSAGE);
	}

	public static void error(Window parent, String msg){
		JOptionPane.showMessageDialog(parent,
				msg,
				"错误", JOptionPane.ERROR_MESSAGE);
	}
}
