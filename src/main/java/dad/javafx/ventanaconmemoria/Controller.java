package dad.javafx.ventanaconmemoria;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Controller implements Initializable{

	// Vista
	@FXML
	GridPane vistaPane;

	@FXML
	Slider rojoSlider;

	@FXML
	Slider verdeSlider;

	@FXML
	Slider azulSlider;

	// Model
	private Model modelo = new Model();
	
	private Stage ventana;

	public Controller(Properties prop) throws IOException {
		modelo.setRed(Integer.valueOf((String) prop.get("background.red")));
		modelo.setGreen(Integer.valueOf((String) prop.get("background.green")));
		modelo.setBlue(Integer.valueOf((String) prop.get("background.blue")));
		modelo.setWidth(Double.valueOf((String) prop.get("size.width")));
		modelo.setHeight(Double.valueOf((String) prop.get("size.height")));
		modelo.setLocationX(Double.valueOf((String) prop.get("location.x")));
		modelo.setLocationY(Double.valueOf((String) prop.get("location.y")));

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VistaVentana.fxml"));
		loader.setController(this);
		loader.load();
	}

	public void initialize(URL location, ResourceBundle resources) {
		Bindings.bindBidirectional(rojoSlider.valueProperty(), modelo.redProperty());
		Bindings.bindBidirectional(verdeSlider.valueProperty(), modelo.greenProperty());
		Bindings.bindBidirectional(azulSlider.valueProperty(), modelo.blueProperty());

		rojoSlider.valueProperty().addListener(e -> onSlideValueChange(e));
		verdeSlider.valueProperty().addListener(e -> onSlideValueChange(e));
		azulSlider.valueProperty().addListener(e -> onSlideValueChange(e));

		vistaPane.setStyle(
				"-fx-background-color: rgb(" + modelo.getRed() + "," + modelo.getGreen() + "," + modelo.getBlue() + ");");
	}

	private void onSlideValueChange(Observable e) {
		vistaPane.setStyle(
				"-fx-background-color: rgb(" + modelo.getRed() + "," + modelo.getGreen() + "," + modelo.getBlue() + ");");
	}
	
	public void crearListenersStage(Stage primaryStage) {
		ventana = primaryStage;
		
		ventana.widthProperty().addListener(e -> onStageSizeChange(e));
		ventana.heightProperty().addListener(e -> onStageSizeChange(e));
		
		ventana.xProperty().addListener(e -> onStagePositionChange(e));
		ventana.yProperty().addListener(e -> onStagePositionChange(e));
	}

	private void onStageSizeChange(Observable e) {
		modelo.setWidth(ventana.getWidth());
		modelo.setHeight(ventana.getHeight());
	}
	
	private void onStagePositionChange(Observable e) {
		modelo.setLocationX(ventana.getX());
		modelo.setLocationY(ventana.getY());
	}
	
	public GridPane getVista() {
		return vistaPane;
	}

	public void setVista(GridPane view) {
		this.vistaPane = view;
	}

	public Model getModelo() {
		return modelo;
	}

	public void setModelo(Model model) {
		this.modelo = model;
	}

}
