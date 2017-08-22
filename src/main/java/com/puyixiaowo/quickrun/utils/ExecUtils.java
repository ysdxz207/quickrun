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

        if (StringUtils.isBlank(shortCut.getLink()) ||
                !new File(shortCut.getLink()).exists()) {
            Message.error(MainDialog.getInstance(),
                    "运行“" + shortCut.getName()
                            + "”失败，请确保快捷方式可用。");
            return false;
        }

        return startProgram(shortCut.getLink());
    }

    public static boolean runCmd(String command) throws IOException {
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
    public static boolean startProgram(String programPath) {
        if (StringUtils.isNotBlank(programPath)) {
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(new File(programPath));
                return true;
            } catch (Exception e) {
                Message.alert(null, e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }


}
