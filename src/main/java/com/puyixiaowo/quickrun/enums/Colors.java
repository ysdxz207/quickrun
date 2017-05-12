package com.puyixiaowo.quickrun.enums;

/**
 *
 * @author huangfeihong
 * @date 2017-03-31
 */
public enum Colors {
	BG_MAIN("#caffca", "主窗口背景色"),
	BG_HEADER("#FF8888", "头部背景色"),
	BG_LIST_SELECT("#85d0fe", "列表选中背景色"),
	FG_LIST_SELECT("#FFFFFF", "列表选中前景色"),
	BG_LIST_HOVER("#ffc86a", "列表鼠标hover背景色"),
	FG_LIST_HOVER("#000000", "列表鼠标hover前景色"),
	COLOR_SHORTCUT_USEABLE("#000000", "快捷键正常可用"),
	COLOR_SHORTCUT_EMPTY_PATH("#c0c0c0", "无法获取目标路径"),
	GETCOLOR_SHORTCUT_NOT_EXISTS("#ea2000", "目标路径不存在");

	Colors(String color, String description) {
		this.color = color;
		this.description = description;
	}

	public String color;
	public String description;

}
