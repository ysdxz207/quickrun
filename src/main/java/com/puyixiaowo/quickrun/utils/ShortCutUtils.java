package com.puyixiaowo.quickrun.utils;

import com.puyixiaowo.quickrun.constants.Constants;
import com.puyixiaowo.quickrun.entity.ShortCut;
import com.puyixiaowo.quickrun.enums.ShortCutStatus;
import mslinks.LinkInfo;
import mslinks.ShellLink;
import mslinks.ShellLinkException;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author weishaoqiang
 * @date 2017-03-10 16:21
 */
public class ShortCutUtils {
	public static DefaultListModel<ShortCut> convertToShortCutList(List<File> list) throws IOException, ShellLinkException {
		DefaultListModel resultList = new DefaultListModel();
		int index = Constants.CONFIG.size();
		for (File file :
				list) {

			ShortCut shortCut = new ShortCut();
			String cmdArgs = null;
			shortCut.setTextIcon("");
			shortCut.setIndex(index);
			shortCut.setName(FileUtil.getFileNameWithoutExt(file));

			String path = "";
			if (file.getName().toLowerCase().endsWith(".lnk")) {

				try {
					ShellLink shellLink = new ShellLink(file);
					cmdArgs = shellLink.getCMDArgs();


					LinkInfo linkInfo = shellLink.getLinkInfo();
					if (linkInfo == null) {
                        path = shellLink.resolveTarget();
                    } else {
                        path = linkInfo.getLocalBasePath();
                        if (linkInfo.getCommonPathSuffix() != null) {
                            path = path += linkInfo.getCommonPathSuffix();
                        }
                    }
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "获取目标异常：" + e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				}
			} else {
				path = file.getAbsolutePath();
			}

			if (StringUtils.isBlank(path)) {
				path = "";
				shortCut.setStatus(ShortCutStatus.EMPTY_PATH.status);
			} else if (!new File(path).exists()) {
				shortCut.setStatus(ShortCutStatus.NOT_EXISTS.status);
			} else {
				shortCut.setStatus(ShortCutStatus.USEABLE.status);
			}
			shortCut.setLink(path);
			shortCut.setCmdArgs(cmdArgs == null ? cmdArgs : cmdArgs.trim());
			resultList.addElement(shortCut);
			index++;

		}
		return resultList;
	}


	public static void main(String[] args) throws ShellLinkException, IOException {
		File file = new File("C:\\Users\\Administrator\\Desktop\\Postman.lnk");

		ShellLink shellLink = new ShellLink(file);

		String str = shellLink.getLinkInfo().getLocalBasePath();
		System.out.println(str);
	}

}
