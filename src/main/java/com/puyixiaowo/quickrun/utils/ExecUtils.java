package com.puyixiaowo.quickrun.utils;

import com.puyixiaowo.quickrun.dialog.MainDialog;
import com.puyixiaowo.quickrun.entity.ShortCut;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author huangfeihong
 * @date 2017-03-10
 */
public class ExecUtils {
	public static boolean run(ShortCut shortCut) {
		String command = "";

		if (StringUtils.isBlank(shortCut.getLink()) ||
				!new File(shortCut.getLink()).exists()) {
			Message.error(MainDialog.getInstance(), "运行“" + shortCut.getName() + "”失败，请确保目标路径可用。");
			return false;
		}
		command = "cmd /c start /B " +
				shortCut.getLink().replaceAll(" ","\" \"");

		if (StringUtils.isNotBlank(shortCut.getCmdArgs())) {
			command += " " + shortCut.getCmdArgs();
		}

		try {
			return run(command);
		} catch (IOException e) {
		}
		return false;
	}

	public static boolean run(String command) throws IOException {
		Runtime rt = Runtime.getRuntime();
		Process p = rt.exec(command);
		return p != null;
	}

}
