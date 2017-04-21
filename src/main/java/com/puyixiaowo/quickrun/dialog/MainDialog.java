package com.puyixiaowo.quickrun.dialog;

import com.melloware.jintellitype.JIntellitype;
import com.puyixiaowo.quickrun.entity.Config;
import com.puyixiaowo.quickrun.entity.ShortCut;
import com.puyixiaowo.quickrun.handler.HeaderMouseClickHandler;
import com.puyixiaowo.quickrun.handler.HeaderMouseDragHandler;
import com.puyixiaowo.quickrun.panel.RootPanel;
import com.puyixiaowo.quickrun.tray.MainTray;
import com.puyixiaowo.quickrun.utils.ResourceUtil;
import com.puyixiaowo.quickrun.utils.ShortCutUtils;
import com.puyixiaowo.quickrun.utils.ThemeUtils;
import com.sun.awt.AWTUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

/**
 * @author weishaoqiang
 * @date 2017-03-10 13:37
 */
public class MainDialog extends JFrame {
	private static final String TITLE = "";
	public static final int WIDTH = 220;
	public static final int HEIGHT = 800;
	//private static final int HEIGHT_HEAD = 30;
	//public static final int POSITION_HEAD = 40;
	public static final int HEIGHT_MAIN_LIST = 530;

	//定义热键标识，用于在设置多个热键时，在事件处理中区分用户按下的热键
	public static final int SHOW_KEY_MARK = 1;

	private DefaultListModel<ShortCut> shortCutList;//快捷方式列表

	public static MainDialog mainDialog;

	private static JScrollPane panel;

	public static Point pointHeader = new Point();

	public MainDialog() {
		mainDialog = this;
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		//setLocationRelativeTo(null);
		setAlwaysOnTop(true);//置顶
		center();

		setUndecorated(true);
		AWTUtilities.setWindowOpaque(this, false);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		//getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);//去掉最小化按钮
		com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.92f);


		//显示桌面则最小化到托盘
		addWindowListener(new WindowAdapter() {
			public void windowIconified(WindowEvent e) {
				dispose();//窗口最小化时dispose该窗口
			}
		});

		Config.initConf();//初始化配置

		initUI();
		setVisible(true);
	}

	private void center() {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screensize.getWidth();//屏幕宽度
		int height = (int) screensize.getHeight();//屏幕高度
		int x = (width - WIDTH) / 2;
		int y = (height - HEIGHT_MAIN_LIST + RootPanel.height) / 2 - HEIGHT + HEIGHT_MAIN_LIST - RootPanel.height;
		System.out.println(y);
		setLocation(x, y);
	}

	private void initUI() {
		new MainTray(this);//托盘
		setIconImage(ResourceUtil.getImageIcon("icon.png").getImage());
		initPanel();
	}

	private void initPanel() {
		JPanel panelRoot = new RootPanel();
		panelRoot.setToolTipText("右键隐藏窗口");
		panelRoot.addMouseListener(new HeaderMouseClickHandler());
		panelRoot.addMouseMotionListener(new HeaderMouseDragHandler());
		panelRoot.setCursor(new Cursor(Cursor.MOVE_CURSOR));
		setContentPane(panelRoot);


		panel = new JScrollPane();
		panel.setHorizontalScrollBar(null);
		panel.setBackground(ThemeUtils.getHeadBgColor());
		panel.setBounds(0, HEIGHT - HEIGHT_MAIN_LIST, WIDTH, HEIGHT_MAIN_LIST);
		panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		initHotKey();//初始化快捷键

		initData(panel);

		initDrag(panel);

		//panelRoot.add(panelHead);
		panelRoot.add(panel);

	}

	private void initData(JScrollPane pane) {
		pane.setViewportView(Config.getConfigJList());
		//设置边框透明，右移列表项4个像素
		pane.setBorder(BorderFactory.createEmptyBorder(4, 4, 2, 2));
	}

	private void initDrag(JScrollPane panel)//定义的拖拽方法
	{
		//panel表示要接受拖拽的控件
		new DropTarget(panel, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() {
			@Override
			public void drop(DropTargetDropEvent dtde)//重写适配器的drop方法
			{
				try {
					if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor))//如果拖入的文件格式受支持
					{
						dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);//接收拖拽来的数据
						List<File> list = (List<File>) (dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));

						shortCutList = ShortCutUtils.convertToShortCutList(list);

						//更新配置
						Config.addShortCutConfs(shortCutList);
						//刷新列表显示
						refreshJList(-1);


//						String temp = "";
//						for (File file : list)
//							temp += ShortCutUtils.parseRealPath(file) + ";\n";
//						JOptionPane.showMessageDialog(null, temp);
						dtde.dropComplete(true);//指示拖拽操作已完成
					} else {
						dtde.rejectDrop();//否则拒绝拖拽来的数据
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initHotKey() {
		//第一步：注册热键，第一个参数表示该热键的标识，第二个参数表示组合键，如果没有则为0，第三个参数为定义的主要热键
		JIntellitype.getInstance().registerHotKey(SHOW_KEY_MARK, JIntellitype.MOD_CONTROL | JIntellitype.MOD_SHIFT, KeyEvent.VK_BACK_QUOTE);

		//第二步：添加热键监听器
		JIntellitype.getInstance().addHotKeyListener((int markCode) -> {
			switch (markCode) {
				case SHOW_KEY_MARK:
					showOrHide();
					break;

			}
		});
	}

	public static void showDialog() {
		mainDialog.setVisible(true);
	}

	public static void hideDialog() {
		mainDialog.setVisible(false);
	}


	public void showOrHide() {
		if (isShowing()) {
			setVisible(false);
		} else {
			setVisible(true);
			setExtendedState(Frame.NORMAL);
		}
	}

	/**
	 * 刷新列表并选中
	 */
	public static void refreshJList(int index) {
		panel.setViewportView(Config.getConfigJList());
		JList jList = (JList) panel.getViewport().getComponent(0);
		jList.requestFocus();//设置jlist焦点，否则刷新后无法使用键盘
		if (index >= -1) {
			jList.setSelectedIndex(index);
		}
	}

}
