package dad.javafx.ventanaconmemoria;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VentanaMemoria extends Application {
	private Controller controller;

	private void crearConfiguracionInicial(File fichero) throws IOException {
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(fichero));
		String configuracionInicial = "";
		configuracionInicial += "background.red=255\n";
		configuracionInicial += "background.green=255\n";
		configuracionInicial += "background.blue=255\n";
		configuracionInicial += "size.width=320\n";
		configuracionInicial += "size.height=200\n";
		configuracionInicial += "location.x=440\n";
		configuracionInicial += "location.y=244";

		bWriter.write(configuracionInicial);
		bWriter.close();
	}

	@Override
	public void init() throws Exception {
		String rutadelPerfil = System.getProperty("user.home");
		Properties propiedades = new Properties();
		File ficheroConfiguracion = new File(rutadelPerfil + "\\.VentanaConMemoria\\ventana.config");

		if (!ficheroConfiguracion.getParentFile().exists() && !ficheroConfiguracion.getParentFile().mkdirs())
			throw new IllegalStateException("No se pudo crear la carpeta .VentanaConMemoria");

		if (ficheroConfiguracion.createNewFile()) {
			crearConfiguracionInicial(ficheroConfiguracion);
		}

		propiedades.load(new FileInputStream(ficheroConfiguracion));
		controller = new Controller(propiedades);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(controller.getVista(), controller.getModelo().getWidth(),
				controller.getModelo().getHeight());
		primaryStage.setTitle("Ventana con memoria");
		primaryStage.setScene(scene);
		primaryStage.setX(controller.getModelo().getLocationX());
		primaryStage.setY(controller.getModelo().getLocationY());
		primaryStage.show();

		controller.crearListenersStage(primaryStage);
	}

	@Override
	public void stop() throws Exception {
		String rutaPerfil = System.getProperty("user.home");
		Properties propiedades = new Properties();
		File ficheroConfiguracion = new File(rutaPerfil + "\\.VentanaConMemoria\\ventana.config");
		Model model = controller.getModelo();

		if (!ficheroConfiguracion.getParentFile().exists() && !ficheroConfiguracion.getParentFile().mkdirs())
			throw new IllegalStateException("No se pudo crear la carpeta .VentanaConMemoria");

		// Creamos el fichero en caso de no existir
		ficheroConfiguracion.createNewFile();

		propiedades.setProperty("background.red", String.valueOf(model.getRed()));
		propiedades.setProperty("background.green", String.valueOf(model.getGreen()));
		propiedades.setProperty("background.blue", String.valueOf(model.getBlue()));
		propiedades.setProperty("size.width", String.valueOf(model.getWidth()));
		propiedades.setProperty("size.height", String.valueOf(model.getHeight()));
		propiedades.setProperty("location.x", String.valueOf(model.getLocationX()));
		propiedades.setProperty("location.y", String.valueOf(model.getLocationY()));

		propiedades.store(new FileWriter(ficheroConfiguracion), null);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
