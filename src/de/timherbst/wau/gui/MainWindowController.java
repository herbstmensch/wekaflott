package de.timherbst.wau.gui;

/**
 * Sample Skeleton for "MainWindow.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import de.axtres.logging.main.AxtresLogger;
import de.timherbst.wau.domain.Mannschaft;
import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.domain.WettkampfTag;
import de.timherbst.wau.events.Event;
import de.timherbst.wau.events.EventDispatcher;
import de.timherbst.wau.events.EventListener;

public class MainWindowController implements Initializable, EventListener {

	ObservableList<Turner> observableList;

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
	private TableView<Mannschaft> mannschaftenTable; // Value injected by
														// FXMLLoader

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
	// fx:id="ttColAK"
	private TableColumn<Turner, String> ttColAK; // Value injected by FXMLLoader

	@FXML
	// fx:id="ttColIcon"
	private TableColumn<Turner, String> ttColIcon; // Value injected by
													// FXMLLoader

	@FXML
	// fx:id="ttColJahrgang"
	private TableColumn<Turner, String> ttColJahrgang; // Value injected by
														// FXMLLoader

	@FXML
	// fx:id="ttColMannschaft"
	private TableColumn<Turner, String> ttColMannschaft; // Value injected by
															// FXMLLoader

	@FXML
	// fx:id="ttColName"
	private TableColumn<Turner, String> ttColName; // Value injected by
													// FXMLLoader

	@FXML
	// fx:id="ttColRiege"
	private TableColumn<Turner, String> ttColRiege; // Value injected by
													// FXMLLoader

	@FXML
	// fx:id="ttColVerein"
	private TableColumn<Turner, String> ttColVerein; // Value injected by
														// FXMLLoader

	@FXML
	// fx:id="ttColVorname"
	private TableColumn<Turner, String> ttColVorname; // Value injected by
														// FXMLLoader

	@FXML
	// fx:id="ttColWettkampf"
	private TableColumn<Turner, String> ttColWettkampf; // Value injected by
														// FXMLLoader

	@FXML
	// fx:id="turnerTable"
	private TableView<Turner> turnerTable; // Value injected by FXMLLoader

	// Handler for MenuItem[javafx.scene.control.MenuItem@67cd3e41] onAction
	public void openRenameWettkampftag(ActionEvent event) {
		// handle the event here
	}

	@Override // This method is called by the FXMLLoader when initialization is complete
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
	        assert ttColAK != null : "fx:id=\"ttColAK\" was not injected: check your FXML file 'MainWindow.fxml'.";
	        assert ttColIcon != null : "fx:id=\"ttColIcon\" was not injected: check your FXML file 'MainWindow.fxml'.";
	        assert ttColJahrgang != null : "fx:id=\"ttColJahrgang\" was not injected: check your FXML file 'MainWindow.fxml'.";
	        assert ttColMannschaft != null : "fx:id=\"ttColMannschaft\" was not injected: check your FXML file 'MainWindow.fxml'.";
	        assert ttColName != null : "fx:id=\"ttColName\" was not injected: check your FXML file 'MainWindow.fxml'.";
	        assert ttColRiege != null : "fx:id=\"ttColRiege\" was not injected: check your FXML file 'MainWindow.fxml'.";
	        assert ttColVerein != null : "fx:id=\"ttColVerein\" was not injected: check your FXML file 'MainWindow.fxml'.";
	        assert ttColVorname != null : "fx:id=\"ttColVorname\" was not injected: check your FXML file 'MainWindow.fxml'.";
	        assert ttColWettkampf != null : "fx:id=\"ttColWettkampf\" was not injected: check your FXML file 'MainWindow.fxml'.";
	        assert turnerTable != null : "fx:id=\"turnerTable\" was not injected: check your FXML file 'MainWindow.fxml'.";

	        // initialize your logic here: all @FXML variables will have been injected
	        
	        ttColName.setCellValueFactory(new PropertyValueFactory<Turner, String>("name"));
	        ttColVorname.setCellValueFactory(new PropertyValueFactory<Turner, String>("vorname"));
	        ttColVerein.setCellValueFactory(new PropertyValueFactory<Turner, String>("verein"));
	        ttColJahrgang.setCellValueFactory(new PropertyValueFactory<Turner, String>("jahrgang"));
	        ttColName.setEditable(true);
	        turnerTable.setEditable(true);
	        
	        
	      //--- Add for Editable Cell of Value field, in Double
	        ttColName.setCellFactory(new Callback<TableColumn<Turner,String>, TableCell<Turner,String>>() {
	        	@Override
	        	public TableCell<Turner, String> call(
	        			TableColumn<Turner, String> arg0) {
	        		 TextFieldTableCell<Turner, String> t = new TextFieldTableCell<Turner, String>(new StringConverter<String>() {
                        @Override
                        public String toString(String t) {
                            return t.toString();
                        }
                        @Override
                        public String fromString(String string) {
                            return string;
                        }                                    
                    });
	        		return t
	        	}
			});
	        ttColName.setOnEditCommit(
	                new EventHandler<TableColumn.CellEditEvent<Turner, String>>() {
	                    @Override public void handle(TableColumn.CellEditEvent<Turner, String> t) {
	                        ((Turner)t.getTableView().getItems().get(
	                                t.getTablePosition().getRow())).setName(t.getNewValue());
	                    }
	                });
	        //---
	        

	        EventDispatcher.addListener(Event.WETTKAMPFTAG_CHANGED, this);
	    }

	// Handler for Button[fx:id="newTurner"] onAction
	public void addTurner(ActionEvent event) {
		observableList.add(new Turner());
	}

	@Override
	public void inform(Event e) {
		if (e.equals(Event.WETTKAMPFTAG_CHANGED)) {
			System.out.println("Changed");
			observableList = FXCollections.observableList(WettkampfTag.get()
					.getTurner());
			turnerTable.setItems(observableList);
		}
	}

	public void openRenameWettkampftag() {
		try {
			// +++++++++++++++++++++++++++++++++++++++++++++
			// Layout
			// +++++++++++++++++++++++++++++++++++++++++++++

			URL location = getClass().getResource(
					"../gui/WKTEigenschaften.fxml");

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
		} catch (Throwable t) {
			AxtresLogger.error(
					"Konnte 'Wettkampftag Eigenschaften' Fenster nciht öffnen",
					t);
		}
	}

}
