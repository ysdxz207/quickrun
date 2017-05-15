package com.puyixiaowo.quickrun.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.puyixiaowo.quickrun.constants.Constants;
import com.puyixiaowo.quickrun.dialog.MainDialog;
import com.puyixiaowo.quickrun.enums.Colors;
import com.puyixiaowo.quickrun.enums.ShortCutStatus;
import com.puyixiaowo.quickrun.handler.KeyHandler;
import com.puyixiaowo.quickrun.handler.MouseClickHandler;
import com.puyixiaowo.quickrun.handler.MouseMovedHandler;
import com.puyixiaowo.quickrun.handler.menu.right.DeleteShortCutHandler;
import com.puyixiaowo.quickrun.handler.menu.right.EditShortCutHandler;
import com.puyixiaowo.quickrun.renderer.CellRenderer;
import com.puyixiaowo.quickrun.utils.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Iterator;

/**
 * @author huangfeihong
 * @date 2016年12月24日 下午3:33:17
 */
public class Config {

	// 使用volatile关键字保其可见性
	private static String rootPath;
	private static String rootconfigPath;
	private static String configFilePath;

	public static String ICON_DIR = "icon/";


	public static Config getInstance() {

		return ConfigEnum.INSTANCE.singleton;
	}

	private enum ConfigEnum {
		INSTANCE;

		ConfigEnum() {
			singleton = new Config();
		}
		private Config singleton;
	}

	public static JList getConfigJList() {

		JList list = new JList(toShortCutList()) {
			@Override
			public int locationToIndex(Point location) {
				int index = super.locationToIndex(location);
				if (index != -1 && !getCellBounds(index, index).contains(location)) {
					return -1;
				} else {
					return index;
				}
			}
		};
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		//list.addListSelectionListener(new SelectShortCutHandler());
		list.addMouseWheelListener(new MouseClickHandler());
		list.addKeyListener(new KeyHandler());
		list.addMouseListener(new MouseClickHandler());
		list.addMouseMotionListener(new MouseMovedHandler());
		list.setCellRenderer(new CellRenderer());
		list.setFixedCellHeight(18);
		list.setBackground(ColorUtil.string2Color(Colors.BG_MAIN.color));


		//右键菜单start
		JPopupMenu rightClickMenu = new JPopupMenu();
		JMenuItem jMenuItemEdit = new JMenuItem();
		jMenuItemEdit.setText("编辑");
		jMenuItemEdit.setComponentZOrder(list, 0);
		jMenuItemEdit.setIcon(ResourceUtil.getImageIcon("edit.png"));
		jMenuItemEdit.addActionListener(new EditShortCutHandler());
		rightClickMenu.add(jMenuItemEdit);

		JMenuItem jMenuItemDelete = new JMenuItem();
		jMenuItemDelete.setText("删除");
		jMenuItemDelete.setComponentZOrder(list, 0);
		jMenuItemDelete.setIcon(ResourceUtil.getImageIcon("delete.png"));
		jMenuItemDelete.addActionListener(new DeleteShortCutHandler());
		rightClickMenu.add(jMenuItemDelete);
		list.setComponentPopupMenu(rightClickMenu);
		//右键菜单end

		return list;
	}

	/**
	 * 配置转快捷方式列表
	 *
	 * @return
	 */
	public static DefaultListModel<ShortCut> toShortCutList() {
		JSONArray config = Constants.CONFIG;
		DefaultListModel<ShortCut> listModel = new DefaultListModel<>();
		for (int i = 0; i < config.size(); i++) {
			JSONObject obj = config.getJSONObject(i);
			ShortCut shortCut = obj.toJavaObject(ShortCut.class);
			//刷新快捷方式状态
			if (StringUtils.isBlank(shortCut.getLink())) {
				shortCut.setStatus(ShortCutStatus.EMPTY_PATH.status);
			} else if (!new File(shortCut.getLink()).exists()) {
				shortCut.setStatus(ShortCutStatus.NOT_EXISTS.status);
			} else {
				shortCut.setStatus(ShortCutStatus.USEABLE.status);
			}
			listModel.addElement(shortCut);
		}
		return listModel;
	}

	public String getConfigFilePath() {
		return configFilePath;
	}



	//////////////////////////////

	/**
	 *
	 */
	public Config() {
		rootPath = getConfigDir();
		rootconfigPath = getRootConfigPath();
		configFilePath = rootconfigPath + "conf/";
	}

	/**
	 * 获取配置文件根路径
	 *
	 * @return
	 */
	private String getRootConfigPath() {

		return System.getProperty("user.dir") + "/";
	}

	/**
	 * 获取图片资源目录
	 *
	 * @return
	 */
	public static String getImagesResourcePath() {
		return Config.rootPath + "images/";
	}

	/**
	 * @return
	 */
	private static String getConfigDir() {
		return Config.class.getResource("").getPath();
	}

	/**
	 * 初始化配置文件
	 */
	public static void initConf() {

		Config.getInstance();

		if (isConfExists()) {
			readConfToConstants();
			return;
		}

		saveConf();
	}

	/**
	 * 读取配置文件到全局变量
	 */
	public static JSONArray readConfToConstants() {

		StringBuffer buffer = FileUtil.readToBuffer(configFilePath + Constants.CONFIG_FILE_NAME, Constants.ENCODE);
		if (buffer == null) {
			return null;
		}
		String str = buffer.toString();
		String configsStr = null;
		try {
			configsStr = EncryptUtils.decryptByDES(Constants.DES_KEY, str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Constants.CONFIG = JSONArray.parseArray(configsStr);
		return Constants.CONFIG;
	}


	/**
	 * 刷新全局变量配置
	 */
	private static void refreshConf(JSONArray config) {

		Constants.CONFIG = config;
	}

	/**
	 * 添加配置到constants
	 *
	 * @param shortCut
	 */
	public static void addShortCutConf(ShortCut shortCut) {
		JSONArray config = Constants.CONFIG;

		if (isShortCutConfExists(shortCut)) {
			//提示是否覆盖
			Message.error(MainDialog.getInstance(),
					"快捷方式[" + shortCut.getName() + "]已存在");
		} else {
			// 创建新的配置
			config.add(JSONObject.toJSON(shortCut));

			//refreshConf(config);
			saveConf();
		}
	}

	private static boolean isShortCutConfExists(ShortCut shortCut) {

		return readShortCutConf(shortCut.getName()) != null;
	}


	/**
	 * 根据名称读取快捷方式配置
	 *
	 * @param name
	 * @return
	 */
	private static ShortCut readShortCutConf(String name) {
		JSONArray config = Constants.CONFIG;

		Iterator<Object> it = config.iterator();
		while (it.hasNext()) {
			JSONObject obj = (JSONObject) it.next();
			String name2 = obj.getString("name");
			if (name2.equals(name)) {
				ShortCut shortCut = new ShortCut();
				shortCut = obj.toJavaObject(ShortCut.class);
				return shortCut;
			}
		}
		return null;
	}


	/**
	 * 保存配置到文件
	 */
	public static void saveConf() {
		String str = "";

		try {
			str = EncryptUtils.encryptToDES(Constants.DES_KEY, Constants.CONFIG.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (new File(configFilePath + Constants.CONFIG_FILE_NAME).exists()) {

			FileUtil.saveFile(configFilePath + Constants.CONFIG_FILE_NAME, str);
		} else {
			FileUtil.writeFile(str, configFilePath, Constants.CONFIG_FILE_NAME, Constants.ENCODE);
		}
	}


	/**
	 * 配置文件是否存在
	 *
	 * @return
	 */
	private static boolean isConfExists() {
		return new File(configFilePath + Constants.CONFIG_FILE_NAME).exists();
	}

	/**
	 * 添加配置到constants
	 *
	 * @param shortCutList
	 */
	public static void addShortCutConfs(DefaultListModel<ShortCut> shortCutList) {
		if (shortCutList == null) {
			return;
		}
		for (int i = 0; i < shortCutList.getSize(); i++) {
			ShortCut shortCut = shortCutList.get(i);
			addShortCutConf(shortCut);
		}
	}

	/**
	 * 删除并选中下一个index
	 * @param name
	 * @param nextIndex
	 */
	public static void deleteConfig(String name, int nextIndex) {
		JSONArray config = Constants.CONFIG;
		Iterator<Object> it = config.iterator();
		while (it.hasNext()) {
			JSONObject obj = (JSONObject) it.next();
			if (obj.getString("name").equals(name)) {
				it.remove();
			}
		}

		saveConf();
		MainDialog.refreshJList(nextIndex);//刷新列表
	}

	/**
	 * 修改快捷方式
	 *
	 * @param shortCut
	 */
	public static void updateShorCut(ShortCut shortCut) throws Exception {
		if (shortCut.getIndex() == -1) {
			throw new Exception("未选中快捷方式");
		}
		if (isShortCutConfExists(shortCut)
				&& readShortCutConf(shortCut.getName()).getIndex() != shortCut.getIndex()) {
			//提示已存在
			throw new Exception("快捷方式[" + shortCut.getName() + "]已存在");
		}
		JSONArray config = Constants.CONFIG;

		JSONObject obj = config.getJSONObject(shortCut.getIndex());
		obj.put("name", shortCut.getName());
		obj.put("link", shortCut.getLink());
		obj.put("cmdArgs", shortCut.getCmdArgs());
		obj.put("textIcon", shortCut.getTextIcon());

		saveConf();
		MainDialog.refreshJList(shortCut.getIndex());//刷新列表
//		System.out.println(Constants.CONFIG.getJSONObject(shortCut.getIndex()));
	}


	//////////////////////

	public String getRootconfigPath() {
		return rootconfigPath;
	}

}