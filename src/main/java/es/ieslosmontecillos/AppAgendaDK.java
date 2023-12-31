package es.ieslosmontecillos;

import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AppAgendaDK extends Application {
    private DataUtil dataUtil;
    private InicioView inicioView = new InicioView();
    private InicioController inicioController;


    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane rootMain = new StackPane();
        View inicio = inicioView.getView();
        rootMain.getChildren().add(inicio);
        inicioController = inicioView.getInicioController();
        inicioController.setRootMain(rootMain);
        // Solicitamos los datos a los servicios de dataUtil
        dataUtil = new DataUtil();
        dataUtil.obtenerTodasProvincias();
        ObservableList<Provincia> olProv = dataUtil.getOlProvincias();
        dataUtil.obtenerTodasPersonas();
        ObservableList<Persona> olPers = dataUtil.getOlPersonas();
        // Pasamos los datos obtenidos a la clase controladora de inicio
        inicioController.setDataUtil(dataUtil);
        inicioController.setOlProv(olProv);
        inicioController.setOlPers(olPers);
        inicioController.setRootMain(rootMain);
        Scene scene = new Scene(rootMain,650,400);
        primaryStage.setTitle("App Agenda");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}
