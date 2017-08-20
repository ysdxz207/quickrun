package com.puyixiaowo.quickrun.entity;

/**
 * 快捷方式
 *
 * @author huangfeihong
 * @date 2017-03-10
 */
public class ShortCut {
	private String name;
	private String link;
	private String target;
	private String cmdArgs;//命令行参数
	private String textIcon;//自定义图标文件路径
	private int index = -1;
	private int status;//状态，1正常，0无法获取目标路径，-1目标不存在

	public ShortCut() {
	}

	public ShortCut(String name, String link) {
		this.name = name;
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getCmdArgs() {
		return cmdArgs;
	}

	public void setCmdArgs(String cmdArgs) {
		this.cmdArgs = cmdArgs;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getTextIcon() {
		return textIcon;
	}

	public void setTextIcon(String textIcon) {
		this.textIcon = textIcon;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return this.getName();
	}

}
