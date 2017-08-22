package com.puyixiaowo.quickrun.utils;

import com.puyixiaowo.quickrun.dialog.MainDialog;
import com.puyixiaowo.quickrun.entity.Config;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Properties;

/**
 * @author huangfeihong
 * @date 2017-05-12
 */
public class AppUtils {
	private static final int PORT = 19900;
	private static ServerSocket socket;


	private static final String APP_CONF_FILE = "app.properties";
	private static final String APP_NAME_KEY = "app.name";
	private static final String APP_VERSION_KEY = "app.version";

	private static final String START_UP_REG_LOCATION = "HKEY_LOCAL_MACHINE\\Software\\Microsoft\\Windows\\CurrentVersion\\Run";


	public static String getPropty(String key) {
		String value = null;
		Properties properties = new Properties();
		try {
			properties.load(AppUtils.class.getClassLoader().getResourceAsStream(APP_CONF_FILE));
			if (!properties.isEmpty()) {
				value = properties.getProperty(key);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String getAppVersion() {
		return getPropty(APP_VERSION_KEY);
	}

	public static String getAppName() {
		return getPropty(APP_NAME_KEY);
	}

	public static String getAppFullName() {
		return getAppName() + "-" + getAppVersion();
	}

	private static File getRunningAppFile() {
		return new File(ExecUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	}

	/**
	 * get running jar file name if dev env returns app name + version
	 * @return
	 */
	public static String getRunningAppName(){
		File appFile = getRunningAppFile();
		return !appFile.isDirectory() ? appFile.getName() : AppUtils.getAppFullName();
	}

	/**
	 * get running jar file name if dev env returns app name + version
	 * @return
	 */
	public static String getRunningAppFullPath(){
		try {
			return java.net.URLDecoder.decode(getRunningAppFile().getAbsolutePath(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get running jar file name if dev env returns app name
	 * @return
	 */
	public static String getRunningAppPath(){
		try {
			return java.net.URLDecoder.decode(getRunningAppFile().getParent(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 更改开机启动状态
	 * @param isStartUp
	 * 			是否开机启动
	 */
	public static void startUp(boolean isStartUp) {
//		String winName = System.getProperty("os.name");
//		if ("Windows 10".equals(winName)) {
//			return;
//		}

		startUpWin10();
/*
		String command = "reg " + (isStartUp ? "add " : "delete ") +
				START_UP_REG_LOCATION + " /v " + getAppName() +
				(isStartUp ? " /t reg_sz /d \"" + getRunningAppFullPath() : "\" /f");
		try {
			ExecUtils.runCmd(command);
		} catch (IOException e) {
			Message.error(MainDialog.getInstance(), e.getMessage());
		}*/
	}

	private static void startUpWin10(){
		String startupBat = "/startup.bat";
		String userDir = Config.getInstance().getRootconfigPath();
		String runFilePath = userDir + startupBat;
		File startup = new File(runFilePath);
		InputStream in = ResourceUtil.getInpuStream(startupBat);
		try {
			FileUtils.copyToFile(in, startup);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ExecUtils.run(runFilePath);
	}

	public static boolean isStartUp() {
		return RegeditUtils.isRegExists(START_UP_REG_LOCATION, getAppName());
	}



	public static void checkIfRunning() {
		try {
			//Bind to localhost adapter with a zero connection queue
			socket = new ServerSocket(PORT,0, InetAddress.getByAddress(new byte[] {127,0,0,1}));
		}
		catch (BindException e) {
			System.err.println("Already running.");
			System.exit(1);
		}
		catch (IOException e) {
			System.err.println("Unexpected error.");
			e.printStackTrace();
			System.exit(2);
		}
	}

	public static void main(String[] args) {
		startUp(true);
	}
}
