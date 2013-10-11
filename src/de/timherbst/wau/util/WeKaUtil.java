package de.timherbst.wau.util;

import java.io.File;

import de.axtres.util.AppProperties;
import de.timherbst.wau.service.StorageService;

public class WeKaUtil {

	public static String enshureEnding(String text, String wantedEnding) {
		return text.endsWith(wantedEnding) ? text : text + wantedEnding;

	}

	public static String getOutputFoldername() {
		if (StorageService.filename == null)
			return AppProperties.getProperty("STD_OUTPUT_SUBDIR", "out");
		if (new File(StorageService.filename).getName().contains("."))
			return new File(StorageService.filename).getName().split("\\.")[0];
		return new File(StorageService.filename).getName();

	}

	public static boolean isLinuxSystem() {
		String osName = System.getProperty("os.name").toLowerCase();
		return osName.indexOf("linux") >= 0;
	}
}
