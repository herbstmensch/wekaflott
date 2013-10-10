package de.timherbst.wau.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.axtres.logging.main.AxtresLogger;
import de.axtres.util.AppProperties;
import de.timherbst.wau.application.Application;
import de.timherbst.wau.domain.WettkampfTag;
import de.timherbst.wau.util.WeKaUtil;

public class StorageService {

	public static String filename = null;

	public static void saveWettkampftag(String filename, boolean isAutosave) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File(filename));
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(WettkampfTag.get());
		oos.close();
		if (!isAutosave)
			StorageService.filename = filename;
		Application.getMainFrame().setDirty(false);
		if (!isAutosave)
			Application.getMainFrame().setStatus(filename + " gespeichert (" + new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())) + ")");
		else
			Application.getMainFrame().setStatus("Autosave '" + filename + "' um " + new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));
	}

	public static void loadWettkampftag(String filename) throws IOException, ClassNotFoundException {

		WettkampfTag.set(getWettkampftagFromFile(filename));
		StorageService.filename = filename;
		if (Application.getMainFrame() != null)
			Application.getMainFrame().setDirty(false);
		Application.getMainFrame().setStatus(filename + " geladen");
	}

	public static WettkampfTag getWettkampftagFromFile(String filename) throws IOException, ClassNotFoundException {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(new File(filename));
			ois = new ObjectInputStream(fis);
			WettkampfTag wt = (WettkampfTag) ois.readObject();

			return wt;
		} finally {
			ois.close();
		}
	}

	public static void newWettkampftag() {
		WettkampfTag.set(WettkampfTag.newInstance());
		filename = null;
		if (Application.getMainFrame() != null)
			Application.getMainFrame().setDirty(false);
		Application.getMainFrame().setStatus("Neuer Wettkampftag");
	}

	public static void autoSave() {
		String autoSaveFilename;
		if (filename == null)
			autoSaveFilename = WeKaUtil.enshureEnding(AppProperties.getProperty("STD_AUTOSAVE_PATH", ""), "/") + "autosave.aswkt";
		else
			autoSaveFilename = filename.replaceAll(".wkt", "") + ".aswkt";
		try {
			saveWettkampftag(autoSaveFilename, true);
		} catch (IOException e) {
			AxtresLogger.error(e.getMessage(), e);
			Application.getMainFrame().setStatus("Autosave fehlgeschlagen: " + e.getMessage());
		}
	}

	public class AutoSaveThread extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(new Integer(AppProperties.getProperty("AUTOSAVE_INTERVAL_SEC")).intValue() * 1000);
				} catch (InterruptedException e) {
					AxtresLogger.error("AutoSaveThread", e);
				}
				StorageService.autoSave();
			}
		}
	}

}
