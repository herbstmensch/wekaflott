package de.timherbst.wau.application;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.javabuilders.swing.SwingJavaBuilder;

import de.axtres.logging.main.AxtresLogger;
import de.axtres.util.AppProperties;
import de.timherbst.wau.gui.MainWindowController;
import de.timherbst.wau.service.StorageService;
import de.timherbst.wau.service.StorageService.AutoSaveThread;
import de.timherbst.wau.view.ErfassungHostView;
import de.timherbst.wau.view.ErfassungView;
import de.timherbst.wau.view.MainFrame;
import de.timherbst.wau.view.MannschaftenView;
import de.timherbst.wau.view.RiegenView;
import de.timherbst.wau.view.TurnerView;
import de.timherbst.wau.view.WettkaempfeView;
import de.timherbst.wau.view.components.ErfassungTable;
import de.timherbst.wau.view.components.TabulatorTextField;
import de.timherbst.wau.view.components.ToolBarSeparator;

public class CopyOfApplication extends Application {

	public static final String NAME = "WeKaFlott";
	public static final String VERSION_VIEW = "1.0 beta 6";

	private static MainFrame mainFrame = null;
	private static MainWindowController mainWindowController = null;

	private AutoSaveThread ast = new StorageService().new AutoSaveThread();

	

	public static MainFrame getMainFrame() {
		return mainFrame;
	}

	public static void selectAuswertung() {
		getMainFrame().selectAuswertung();
	}

	public static void openErfassung(Object o) {
		getMainFrame().openErfassung(o);

	}

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		
		SwingJavaBuilder.getConfig().addType(ErfassungTable.class);
		SwingJavaBuilder.getConfig().addType(TabulatorTextField.class);
		SwingJavaBuilder.getConfig().addType(ToolBarSeparator.class);
		SwingJavaBuilder.getConfig().addType(TurnerView.class, MannschaftenView.class, WettkaempfeView.class, RiegenView.class, ErfassungView.class, ErfassungHostView.class);

		
		AxtresLogger.info("Setting Locale: "
				+ AppProperties.getProperty("LOCALE_LANGUAGE") + ","
				+ AppProperties.getProperty("LOCALE_COUNTRY"));
		// Set the default locale to custom locale
		Locale.setDefault(new Locale(AppProperties
				.getProperty("LOCALE_LANGUAGE"), AppProperties
				.getProperty("LOCALE_COUNTRY")));

		AxtresLogger.info("User Dir: " + System.getProperty("user.dir"));

		
		
		// +++++++++++++++++++++++++++++++++++++++++++++
		// Layout
		// +++++++++++++++++++++++++++++++++++++++++++++


		System.out.println(getClass().getResource(
				"../gui/MainWindow.fxml"));
		
		URL location = getClass().getResource("../gui/MainWindow.fxml");

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

		Parent root = (Parent) fxmlLoader.load(location.openStream());
		
		mainWindowController = fxmlLoader.getController();
		
		// Szene
		Scene scene = new Scene(root);

		// +++++++++++++++++++++++++++++++++++++++++++++
		// Stage konfigurieren
		// +++++++++++++++++++++++++++++++++++++++++++++

		// Titel setzen
		primaryStage.setTitle(NAME + " " + VERSION_VIEW);

		// Szene setzen
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();

		// Stage anzeigen
		primaryStage.show();
		
		scene.getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent arg0) {
				System.exit(0);
			}
		});
	

		if (getParameters().getRaw().size() > 0) {
			for (String s : getParameters().getRaw()) {
				try {
					if (!"".equals(s)) {
						AxtresLogger.info("Application arg: " + s);
						StorageService.loadWettkampftag(s);
					}
				} catch (Throwable e) {
					AxtresLogger.error("Konnte File: " + s
							+ " nicht laden. Neuer Wettkampftag angelegt.");
					StorageService.newWettkampftag();
					 mainWindowController.openRenameWettkampftag();
				}
			}
		} else {
			StorageService.newWettkampftag();
			mainWindowController.openRenameWettkampftag();
		}

		ast.start();

	}

}
