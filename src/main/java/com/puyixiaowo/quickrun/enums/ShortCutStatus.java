package com.puyixiaowo.quickrun.enums;

/**
 * @author huangfeihong
 * @date 2017-03-17
 */
public enum  ShortCutStatus {
	USEABLE(1, "正常可用"),
	EMPTY_PATH(0, "无法获取目标路径"),
	NOT_EXISTS(-1, "目标路径不存在");

	ShortCutStatus(int status, String description) {
		this.status = status;
		this.description = description;
	}

	public int status;
	public String description;


	public static ShortCutStatus getEnumByStatus(int status) {
		for (ShortCutStatus shortCutStatus : ShortCutStatus.values()) {
			if (shortCutStatus.status == status) {
				return shortCutStatus;
			}
		}
		return null;
	}
}
