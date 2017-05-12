package com.puyixiaowo.quickrun.tray;

import com.puyixiaowo.quickrun.dialog.MainDialog;
import com.puyixiaowo.quickrun.utils.AppUtils;
import com.puyixiaowo.quickrun.utils.ResourceUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author huangfeihong
 * @date 2017-03-10
 */
public class MainTray {
	SystemTray tray; // 本操作系统托盘的实例
	TrayIcon trayIcon; // 托盘图标
	MainDialog mainDialog;

	public MainTray(MainDialog mainDialog) {
		this.mainDialog = mainDialog;
		init();
	}

	private void init() {

		tray = SystemTray.getSystemTray(); // 获得本操作系统托盘的实例
		ImageIcon icon = ResourceUtil.getImageIcon("icon.png"); // 将要显示到托盘中的图标
		PopupMenu pop = new PopupMenu(); // 构造一个右键弹出式菜单

		final MenuItem show = new MenuItem("打开程序");
		final CheckboxMenuItem startUp = new CheckboxMenuItem("开机启动");
		if (AppUtils.isStartUp()) {
			startUp.setState(true);
		}
		final MenuItem exit = new MenuItem("退出程序");

		pop.add(show);
		pop.add(startUp);
		pop.add(exit);
		trayIcon = new TrayIcon(icon.getImage(), AppUtils.getAppName(), pop);//实例化托盘图标
		trayIcon.setImageAutoSize(true);
		//为托盘图标监听点击事件
		trayIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)//鼠标双击图标
				{
					// trayIcon.displayMessage("警告", "这是一个警告提示!", TrayIcon.MessageType.WARNING);
					// trayIcon.displayMessage("错误", "这是一个错误提示!", TrayIcon.MessageType.ERROR);
					//trayIcon.displayMessage("信息", "这是一个信息提示!", TrayIcon.MessageType.INFO);

					mainDialog.showDialog();
				}
			}
		});

		//选项注册事件
		ActionListener al2 = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//退出程序
				if (e.getSource() == exit) {
					tray.remove(trayIcon); // 从系统的托盘实例中移除托盘图标
					System.exit(0);//退出程序
				}

				//打开程序
				if (e.getSource() == show) {
					mainDialog.showDialog();
				}

			}
		};
		ItemListener itemListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				//开机启动
				if (e.getSource() == startUp) {
					if (AppUtils.isStartUp()) {
						startUp.setState(false);
						AppUtils.startUp(false);
					} else {
						startUp.setState(true);
						AppUtils.startUp(true);
					}
				}
			}
		};
		exit.addActionListener(al2);
		startUp.addItemListener(itemListener);
		show.addActionListener(al2);

		try {
			tray.add(trayIcon); // 将托盘图标添加到系统的托盘实例中
		} catch (AWTException ex) {
			ex.printStackTrace();
		}
	}
}
