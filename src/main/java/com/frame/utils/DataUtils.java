package com.frame.utils;

import java.io.File;

public class DataUtils {
	/**
	 * @param srcFile
	 * @param destPath
	 * @return
	 */
	public static boolean move(String srcFile, String destPath,String fileName) {
		// File (or directory) to be moved
		File file = new File(srcFile);

		// Destination directory
		File dir = new File(destPath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		// Move file to new directory
		boolean success = file.renameTo(new File(dir, fileName));

		return success;
	}
}
