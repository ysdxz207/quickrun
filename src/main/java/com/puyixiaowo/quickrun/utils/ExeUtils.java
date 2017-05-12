package com.puyixiaowo.quickrun.utils;

import com.puyixiaowo.quickrun.dialog.MainDialog;
import com.puyixiaowo.quickrun.entity.ShortCut;

import java.io.IOException;

/**
 *
 * @author huangfeihong
 * @date 2017-03-10
 */
public class ExeUtils {
	public static boolean run(ShortCut shortCut) throws IOException {
		String command = "";

		if (StringUtils.isBlank(shortCut.getLink())) {
			return false;
		}
		command = "cmd /c start /B " +
				shortCut.getLink().replaceAll(" ","\" \"");

		if (StringUtils.isNotBlank(shortCut.getCmdArgs())) {
			command += " " + shortCut.getCmdArgs();
		}

		return run(command);
	}

	public static boolean run(String command) throws IOException {
		Runtime rt = Runtime.getRuntime();
		Process p = rt.exec(command);
		return p != null;
	}

}
