package com.puyixiaowo.quickrun.utils;

import com.puyixiaowo.quickrun.dialog.MainDialog;
import com.puyixiaowo.quickrun.entity.ShortCut;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author huangfeihong
 * @date 2017-03-10
 */
public class ExecUtils {
    public static boolean run(ShortCut shortCut) {

        //批处理文件
        if ("bat".equalsIgnoreCase(FileUtil.getFileExtName(shortCut.getLink()))) {
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", shortCut.getTarget());
            File dir = new File(shortCut.getTarget()).getParentFile();
            pb.directory(dir);
            try {
                Process p = pb.start();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        //其他
        if (StringUtils.isBlank(shortCut.getLink()) ||
                !new File(shortCut.getLink()).exists()) {
            return startProgram(shortCut.getTarget());
        }

        boolean flag = startProgram(shortCut.getLink());
        if (!flag) {
            Message.error(MainDialog.getInstance(),
                    "运行“" + shortCut.getName()
                            + "”失败，请确保快捷方式可用。");
            return false;
        }

        return true;
    }

    public static boolean run(String path) {
        if (StringUtils.isBlank(path)) {
            Message.error(MainDialog.getInstance(),
                    "运行“" + path
                            + "”失败，请确保执行路径可用。");
            return false;
        }

        return startProgram(path);
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
    private static boolean startProgram(String programPath) {
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
