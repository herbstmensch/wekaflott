package de.timherbst.wau.application;

import java.awt.Toolkit;
import java.io.IOException;
import java.util.Locale;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.gpl.JSplitButton.JSplitButton;
import org.javabuilders.swing.SwingJavaBuilder;

import de.axtres.logging.main.AxtresLogger;
import de.axtres.util.AppProperties;
import de.timherbst.wau.service.StorageService;
import de.timherbst.wau.service.StorageService.AutoSaveThread;
import de.timherbst.wau.util.WeKaUtil;
import de.timherbst.wau.view.ErfassungHostView;
import de.timherbst.wau.view.ErfassungView;
import de.timherbst.wau.view.MainFrame;
import de.timherbst.wau.view.MannschaftenView;
import de.timherbst.wau.view.RiegenView;
import de.timherbst.wau.view.TurnerView;
import de.timherbst.wau.view.WettkaempfeView;
import de.timherbst.wau.view.WettkampfTagPropertiesWindow;
import de.timherbst.wau.view.components.ErfassungTable;
import de.timherbst.wau.view.components.TabulatorTextField;
import de.timherbst.wau.view.components.ToolBarSeparator;

public class Application {

	public static final String NAME = "WeKaFlott";
	public static final String VERSION_VIEW = "1.0 RC1";

	private static MainFrame mainFrame = null;

	private AutoSaveThread ast = new StorageService().new AutoSaveThread();

	public Application(final String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					if ("TRUE".equalsIgnoreCase(AppProperties.getProperty("USE_PLATFORM_LOOK_AND_FEEL", "FALSE"))) {

						AxtresLogger.info("Setting LaF: " + UIManager.getSystemLookAndFeelClassName());
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

					} else {

						UIManager.put("Synthetica.window.decoration", Boolean.FALSE);

						AxtresLogger.info("Setting LaF: " + AppProperties.getProperty("LOOK_AND_FEEL"));
						UIManager.setLookAndFeel(AppProperties.getProperty("LOOK_AND_FEEL"));
					}

					AxtresLogger.info("Setted Look And Feel to: " + UIManager.getLookAndFeel().getName());
					AxtresLogger.info("Setting Locale: " + AppProperties.getProperty("LOCALE_LANGUAGE") + "," + AppProperties.getProperty("LOCALE_COUNTRY"));
					// Set the default locale to custom locale
					Locale.setDefault(new Locale(AppProperties.getProperty("LOCALE_LANGUAGE"), AppProperties.getProperty("LOCALE_COUNTRY")));

					SwingJavaBuilder.getConfig().addType(ErfassungTable.class);
					SwingJavaBuilder.getConfig().addType(TabulatorTextField.class);
					SwingJavaBuilder.getConfig().addType(ToolBarSeparator.class);
					SwingJavaBuilder.getConfig().addType(JSplitButton.class);
					SwingJavaBuilder.getConfig().addType(TurnerView.class, MannschaftenView.class, WettkaempfeView.class, RiegenView.class, ErfassungView.class, ErfassungHostView.class);

					AxtresLogger.info("User Dir: " + System.getProperty("user.dir"));

					mainFrame = new MainFrame();
					mainFrame.setVisible(true);

					if (args.length > 0) {
						for (String s : args) {
							if (!"".equals(s)) {
								AxtresLogger.info("Application arg: " + s);
								StorageService.loadWettkampftag(args[0]);
							}
						}
					} else {
						StorageService.newWettkampftag();
						renameWettkampftag();
					}

					ast.start();

				} catch (Throwable e) {
					AxtresLogger.error(e.getMessage(), e);
				}
			}
		});
	}

	// TODO Auto-generated method stub

	public static void main(final String[] args) {
		try {
			// Setzen dieser Eigenschaft ist f�r den Gnome-Desktop wichtig
			Toolkit xToolkit = Toolkit.getDefaultToolkit();
			java.lang.reflect.Field awtAppClassNameField = xToolkit.getClass().getDeclaredField("awtAppClassName");
			awtAppClassNameField.setAccessible(true);
			awtAppClassNameField.set(xToolkit, NAME);
		} catch (Throwable t) {
			AxtresLogger.warn("awtAppClassName konnte nicht gesetzt werden. Vermutlich keine Linux JVM");
		}

		if (WeKaUtil.isLinuxSystem()) {
			try {
				AxtresLogger.info("Trying to set wmname to metacity");
				Runtime.getRuntime().exec("wmname");

				AxtresLogger.info("Setting wmname to metacity succesful");
			} catch (IOException e) {
				AxtresLogger.warn("Changing wmname to metacity not succesful. When running under Gnome-Shell 3.x install 'wmname' to prevent display errors in maximized mode.");
			}
		}

		new Application(args);
	}

	public static void renameWettkampftag() {
		new WettkampfTagPropertiesWindow().setVisible(true);
	}

	public static MainFrame getMainFrame() {
		return mainFrame;
	}

	public static void selectAuswertung() {
		getMainFrame().selectAuswertung();
	}

	public static void openErfassung(Object o) {
		getMainFrame().openErfassung(o);

	}

}
