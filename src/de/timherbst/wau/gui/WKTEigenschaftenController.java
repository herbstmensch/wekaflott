/**
 * Sample Skeleton for "WKTEigenschaften.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package de.timherbst.wau.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class WKTEigenschaftenController

    implements Initializable {

    @FXML //  fx:id="cancel"
    private Button cancel; // Value injected by FXMLLoader

    @FXML //  fx:id="datum"
    private TextField datum; // Value injected by FXMLLoader

    @FXML //  fx:id="name"
    private TextField name; // Value injected by FXMLLoader

    @FXML //  fx:id="ok"
    private Button ok; // Value injected by FXMLLoader

    @FXML //  fx:id="ort"
    private TextField ort; // Value injected by FXMLLoader


    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file 'WKTEigenschaften.fxml'.";
        assert datum != null : "fx:id=\"datum\" was not injected: check your FXML file 'WKTEigenschaften.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'WKTEigenschaften.fxml'.";
        assert ok != null : "fx:id=\"ok\" was not injected: check your FXML file 'WKTEigenschaften.fxml'.";
        assert ort != null : "fx:id=\"ort\" was not injected: check your FXML file 'WKTEigenschaften.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected

    }
    
    public void ok(){
    	
    }

    public void cancel(){
    	
    }
}
