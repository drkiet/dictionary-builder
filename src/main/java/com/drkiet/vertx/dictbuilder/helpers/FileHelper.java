package com.drkiet.vertx.dictbuilder.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class FileHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileHelper.class);

	public static String getDictionaryFolder() {
		return System.getProperty("dictionary.folder");
	}

	public static String getReferenceFolder() {
		return System.getProperty("reference.folder");
	}

	public static String getDictionaryName() {
		return System.getProperty("dictionary.name");
	}

	public static List<String> getFileNames(String folderName) {
		LOGGER.info("folder: {}", folderName);
		File folder = new File(folderName);

		if (!folder.isDirectory()) {
			return null;
		}

		return getFileNames(folder);

	}

	private static List<String> getFileNames(File file) {
		LOGGER.info("get file names: {}", file.getName());
		List<String> fileNames = new ArrayList<String>();
		if (!file.isDirectory()) {
			fileNames.add(file.getName());
			return fileNames;
		}

		for (File f : file.listFiles()) {
			fileNames.addAll(getFileNames(f));
		}

		return fileNames;
	}

}
