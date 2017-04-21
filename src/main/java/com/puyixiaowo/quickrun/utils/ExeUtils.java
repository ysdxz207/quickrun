package com.puyixiaowo.quickrun.utils;

import com.puyixiaowo.quickrun.entity.ShortCut;

import java.io.IOException;

/**
 *
 * @author weishaoqiang
 * @date 2017-03-10 16:42
 */
public class ExeUtils {
	public static boolean run(ShortCut shortCut) throws IOException {
		Runtime rt = Runtime.getRuntime();
		Process p = null;
		String command = "";

		if (StringUtils.isBlank(shortCut.getLink())) {
			return false;
		}
		command = "cmd /c start /B " +
				shortCut.getLink().replaceAll(" ","\" \"");

		if (StringUtils.isNotBlank(shortCut.getCmdArgs())) {
			command += " " + shortCut.getCmdArgs();
		}
		p = rt.exec(command);

		return p != null;
	}
}
