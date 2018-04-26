package com.puyixiaowo.quickrun.utils;

import com.puyixiaowo.quickrun.dialog.MainDialog;
import com.puyixiaowo.quickrun.entity.ShortCut;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author huangfeihong
 * @date 2017-03-10
 */
public class ExecUtils {
    public static boolean run(ShortCut shortCut) {

        String ext = FileUtil.getFileExtName(shortCut.getLink());




        List<String> argList = new ArrayList<>();
        argList.add("cmd");
        argList.add("/c");
        if ("jar".equalsIgnoreCase(ext)) {

            argList.add("java");
            argList.add("-jar");
        }
        argList.add(shortCut.getTarget());

        if (StringUtils.isNotBlank(shortCut.getCmdArgs())) {
            argList.addAll(Arrays.asList(shortCut.getCmdArgs().split(" ")));
        }


        ProcessBuilder pb = new ProcessBuilder(argList.toArray(new String[argList.size()]));
        File dir = new File(shortCut.getTarget()).getParentFile();
        pb.directory(dir);
        try {
            Process p = pb.start();
            return true;
        } catch (IOException e) {
            return false;
        }


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
