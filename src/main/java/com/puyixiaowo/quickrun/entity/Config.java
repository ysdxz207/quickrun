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
		list.addMouseWheelListener(new MouseClickHandler());
		list.addKeyListener(new KeyHandler());
		list.addMouseListener(new MouseClickHandler());
		list.addMouseMotionListener(new MouseMovedHandler());
		list.setCellRenderer(new CellRenderer());
		list.setFixedCellHeight(18);
		list.setBackground(ColorUtil.string2Color(Colors.BG_MAIN.color));


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
			if (StringUtils.isBlank(shortCut.getTarget())) {
				shortCut.setStatus(ShortCutStatus.EMPTY_PATH.status);
			} else if (!new File(shortCut.getTarget()).exists()) {
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
		return AppUtils.getRunningAppPath() + "/";
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
			shortCut.setId(IdUtils.generateId()+"");
			config.add(JSONObject.toJSON(shortCut));

			//refreshConf(config);
			saveConf();
			MainDialog.refreshJList(shortCut.getIndex());//刷新列表
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
		if (Constants.CONFIG.size() > 0) {
			//重置索引
			JSONArray jsonArray = Constants.CONFIG;
			for (int i = 0; i < jsonArray.size(); i ++) {
				jsonArray.getJSONObject(i).put("index", i);
			}
		}

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
				&& !readShortCutConf(shortCut.getName()).getId().equalsIgnoreCase(shortCut.getId())) {
			//提示已存在
			throw new Exception("快捷方式[" + shortCut.getName() + "]已存在");
		}
		JSONArray config = Constants.CONFIG;

		JSONObject obj = config.getJSONObject(shortCut.getIndex());
		obj.put("id", shortCut.getId());
		obj.put("name", shortCut.getName());
		obj.put("link", shortCut.getLink());
		obj.put("target", shortCut.getTarget());
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

	public static void main(String[] args) throws Exception {
		String str = EncryptUtils.decryptByDES(Constants.DES_KEY, "3E3500A3612EF7854E47640323E48178573915E200F9E49BE589D7EBB72816622516665779AA5FA757D0B34EAD3AD9CCB0961A9C527E01AA8E2AD91B835BED76D4F92526ED6698B8AAA92A1F2E33E84340B102DA55458AE823ECA3732A43D02D3E7D9BAC90AB23CDCAF41739F7A4A2E27B7C260F46B617E62CB35E80D41CCE3177757572A4F17CFEFE7BBDA99AD27E33C3EDC1562ADCEEF3EA2BA18E6A47C7126CA4AD4E228F1609A798A390B177545DEFF9505F40C45E9D9BE7FC1C29D2871CF82AC5B9ACFF290BCC4E1123C64E997D00FC5EDD084C187662DE22360C143D80639B5BD56178983BE82DFE7A2531FE2331A8AD7AE0848316FAD11E13410289C0998D92647853AC2ED556052DC81AA8CFD4AE80B275FC03FB651CFD1CAFAE30E582D301A050D65C0A20EE175A837146C95CBC39C791865A3F058C2591D41A5487B041E9E00B2C35C944B3B3FAAFE00B3742FF06582123F8821C1CC0AF857881DE3106BC816D887AB0E1E54203F4BFC0E8");
		System.out.println(str);
	}
}