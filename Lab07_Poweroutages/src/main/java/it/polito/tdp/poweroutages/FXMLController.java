/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML // fx:id="btnWorst"
	private Button btnWorst; // Value injected by FXMLLoader

	@FXML // fx:id="txtMaxH"
	private TextField txtMaxH; // Value injected by FXMLLoader

	@FXML // fx:id="txtMaxY"
	private TextField txtMaxY; // Value injected by FXMLLoader

	@FXML // fx:id="cbNERC"
	private ComboBox<Nerc> cbNERC; // Value injected by FXMLLoader

	@FXML
	void doWorst(ActionEvent event) {
		txtResult.clear();

		int maxH = 0;
		int maxY = 0;

		try {
			maxH = Integer.parseInt(txtMaxH.getText());
			maxY = Integer.parseInt(txtMaxY.getText());
		} catch (Exception e) {
			txtResult.appendText("Inserire solo numeri nei campi liberi");
			return;
		}

		if (maxH <= 0 || maxY <= 0) {
			txtResult.appendText("Inserire numeri superiori a 0");
			return;
		}

		List<PowerOutage> result = model.ricerca(cbNERC.getValue(), maxY, maxH);
		if (result.size() == 0 || result == null) {
			txtResult.appendText("Nessuna corrispondenza trovata");
		}
		result.forEach(x -> txtResult.appendText(x + "\n"));

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnWorst != null : "fx:id=\"btnWorst\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtMaxH != null : "fx:id=\"txtMaxH\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtMaxY != null : "fx:id=\"txtMaxY\" was not injected: check your FXML file 'Scene.fxml'.";
		assert cbNERC != null : "fx:id=\"cbNERC\" was not injected: check your FXML file 'Scene.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;

		// First Start
		cbNERC.getItems().setAll(model.getNercList());
		cbNERC.getSelectionModel().selectFirst();

		txtMaxY.setText("1");
		txtMaxH.setText("24");
	}
}
