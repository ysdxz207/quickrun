package com.puyixiaowo.quickrun.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * @author huangfeihong
 * @date 2017-05-12
 */
public class RegeditUtils {


	/**
	 * @param location path in the registry
	 * @param key      registry key
	 * @return registry value or null if not found
	 */
	public static final String readRegistry(String location, String key) {
		try {
			// Run reg query, then read output with StreamReader (internal class)
			Process process = Runtime.getRuntime().exec("reg query " +
					'"' + location + "\" /v " + key);

			StreamReader reader = new StreamReader(process.getInputStream());
			reader.start();
			process.waitFor();
			reader.join();

			// Parse out the value
			String[] parsed = reader.getResult().replaceAll("\r\n", "").split("\\s+");

			for (int i = 0; i < parsed.length; i++) {
				String str = parsed[i].trim();
				if (str.indexOf(location) != -1 ||
						str.equals(key) ||
						str.toUpperCase().indexOf("REG_SZ") != -1 ||
						str.toUpperCase().indexOf("REG.EXE") != -1 ||
						str.toUpperCase().indexOf("VERSION") != -1 ||
						str.equals("!")) {
					parsed[i] = "";
				}
			}

			if (ArrayUtils.isEmpty(parsed)) {
				return null;
			}

			return ArrayUtils.arrayToString(parsed).trim();
		} catch (Exception e) {
		}

		return null;
	}

	static class StreamReader extends Thread {
		private InputStream is;
		private StringWriter sw = new StringWriter();

		public StreamReader(InputStream is) {
			this.is = is;
		}

		public void run() {
			try {
				int c;
				while ((c = is.read()) != -1)
					sw.write(c);
			} catch (IOException e) {
			}
		}

		public String getResult() {
			return sw.toString();
		}
	}


	public static boolean isRegExists(String location, String key){
		return readRegistry(location, key) != null;
	}

	public static void main(String[] args) {

		// Sample usage
		String value = RegeditUtils.readRegistry("HKEY_LOCAL_MACHINE\\Software\\Microsoft\\Windows\\CurrentVersion\\Run", "VhallService");
		System.out.println(value);
	}
}
