package de.timherbst.wau.gui;

/**
 * Sample Skeleton for "MainWindow.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import de.axtres.logging.main.AxtresLogger;

public class MainWindowController implements Initializable {

	@FXML
	// fx:id="addMannschaftToRiege"
	private SplitMenuButton addMannschaftToRiege; // Value injected by
													// FXMLLoader

	@FXML
	// fx:id="addMannschftToWettkampf"
	private SplitMenuButton addMannschftToWettkampf; // Value injected by
														// FXMLLoader

	@FXML
	// fx:id="addTurnerToMannschaft"
	private SplitMenuButton addTurnerToMannschaft; // Value injected by
													// FXMLLoader

	@FXML
	// fx:id="addTurnerToRiege"
	private SplitMenuButton addTurnerToRiege; // Value injected by FXMLLoader

	@FXML
	// fx:id="addTurnerToWettkampf"
	private SplitMenuButton addTurnerToWettkampf; // Value injected by
													// FXMLLoader

	@FXML
	// fx:id="deleteMannschaft"
	private Button deleteMannschaft; // Value injected by FXMLLoader

	@FXML
	// fx:id="deleteTurner"
	private Button deleteTurner; // Value injected by FXMLLoader

	@FXML
	// fx:id="mannschaftenTable"
	private TableView<?> mannschaftenTable; // Value injected by FXMLLoader

	@FXML
	// fx:id="newMannschaft"
	private Button newMannschaft; // Value injected by FXMLLoader

	@FXML
	// fx:id="newTurner"
	private Button newTurner; // Value injected by FXMLLoader

	@FXML
	// fx:id="openErfassungForMannschaft"
	private Button openErfassungForMannschaft; // Value injected by FXMLLoader

	@FXML
	// fx:id="removeMannschaftFromRiege"
	private MenuItem removeMannschaftFromRiege; // Value injected by FXMLLoader

	@FXML
	// fx:id="removeMannschaftFromWettkampf"
	private MenuItem removeMannschaftFromWettkampf; // Value injected by
													// FXMLLoader

	@FXML
	// fx:id="removeTurnerFromMannschaft"
	private MenuItem removeTurnerFromMannschaft; // Value injected by FXMLLoader

	@FXML
	// fx:id="removeTurnerFromRiege"
	private MenuItem removeTurnerFromRiege; // Value injected by FXMLLoader

	@FXML
	// fx:id="removeTurnerFromWettkampf"
	private MenuItem removeTurnerFromWettkampf; // Value injected by FXMLLoader

	@FXML
	// fx:id="statusLabel"
	private Label statusLabel; // Value injected by FXMLLoader

	@FXML
	// fx:id="turnerTable"
	private TableView<?> turnerTable; // Value injected by FXMLLoader

	 // Handler for Button[fx:id="newTurner"] onAction
    public void addTurner(ActionEvent event) {
        // handle the event here
    	System.out.println(234234);
    }
	
	@Override
	// This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert addMannschaftToRiege != null : "fx:id=\"addMannschaftToRiege\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert addMannschftToWettkampf != null : "fx:id=\"addMannschftToWettkampf\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert addTurnerToMannschaft != null : "fx:id=\"addTurnerToMannschaft\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert addTurnerToRiege != null : "fx:id=\"addTurnerToRiege\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert addTurnerToWettkampf != null : "fx:id=\"addTurnerToWettkampf\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert deleteMannschaft != null : "fx:id=\"deleteMannschaft\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert deleteTurner != null : "fx:id=\"deleteTurner\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert mannschaftenTable != null : "fx:id=\"mannschaftenTable\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert newMannschaft != null : "fx:id=\"newMannschaft\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert newTurner != null : "fx:id=\"newTurner\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert openErfassungForMannschaft != null : "fx:id=\"openErfassungForMannschaft\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert removeMannschaftFromRiege != null : "fx:id=\"removeMannschaftFromRiege\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert removeMannschaftFromWettkampf != null : "fx:id=\"removeMannschaftFromWettkampf\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert removeTurnerFromMannschaft != null : "fx:id=\"removeTurnerFromMannschaft\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert removeTurnerFromRiege != null : "fx:id=\"removeTurnerFromRiege\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert removeTurnerFromWettkampf != null : "fx:id=\"removeTurnerFromWettkampf\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert statusLabel != null : "fx:id=\"statusLabel\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert turnerTable != null : "fx:id=\"turnerTable\" was not injected: check your FXML file 'MainWindow.fxml'.";

		// initialize your logic here: all @FXML variables will have been
		// injected

	}

	public void openRenameWettkampftag() {
		try{
		// +++++++++++++++++++++++++++++++++++++++++++++
				// Layout
				// +++++++++++++++++++++++++++++++++++++++++++++


				URL location = getClass().getResource("../gui/WKTEigenschaften.fxml");

				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(location);
				fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

				Parent root = (Parent) fxmlLoader.load(location.openStream());
				
				// Szene
				Stage s = new Stage();
				s.setScene(new Scene(root));
				// +++++++++++++++++++++++++++++++++++++++++++++
				// Stage konfigurieren
				// +++++++++++++++++++++++++++++++++++++++++++++

				// Titel setzen
				s.setTitle("Wettkampftag Eigenschaften");
				s.sizeToScene();

				// Stage anzeigen
				s.show();
		}catch (Throwable t) {
			AxtresLogger.error("Konnte 'Wettkampftag Eigenschaften' Fenster nciht öffnen", t);
		}
	}


}
