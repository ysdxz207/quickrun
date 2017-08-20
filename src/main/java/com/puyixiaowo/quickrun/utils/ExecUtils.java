package com.puyixiaowo.quickrun.utils;

import com.puyixiaowo.quickrun.dialog.MainDialog;
import com.puyixiaowo.quickrun.entity.ShortCut;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author huangfeihong
 * @date 2017-03-10
 */
public class ExecUtils {
    public static boolean run(ShortCut shortCut) {
        String command = "";

        if (StringUtils.isBlank(shortCut.getTarget()) ||
                !new File(shortCut.getTarget()).exists()) {
            Message.error(MainDialog.getInstance(), "运行“" + shortCut.getName() + "”失败，请确保目标路径可用。");
            return false;
        }
        command = "cmd /c start /B " +
                shortCut.getTarget().replaceAll(" ", "\" \"");

        if (StringUtils.isNotBlank(shortCut.getCmdArgs())) {
            command += " " + shortCut.getCmdArgs();
        }

//        try {
//
//            return run(command);
//        } catch (IOException e) {
//        }

        try {
            return startProgram(shortCut.getLink());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean run(String command) throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec(command);
        return p != null;
    }

    /**
     * 启动应用程序
     *
     * @param programPath
     * @return
     * @throws IOException
     */
    public static boolean startProgram(String programPath) throws IOException {
        if (StringUtils.isNotBlank(programPath)) {
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(new File(programPath));
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }


}
