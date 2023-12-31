package es.ieslosmontecillos;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
public class InicioController {
    @FXML
    private View inicio;
    private DataUtil dataUtil;
    private ObservableList olProv;
    private ObservableList olPers;
    private Pane rootMain = new Pane();
    @FXML
    private ImageView imagenMain;
    @FXML
    private Label label;

    public Pane getRootMain(){
        return rootMain;
    }


    @Deprecated
    public void iniciaApp(InputEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/AgendaView.fxml"));
            Pane rootAgendaView = fxmlLoader.load();
            rootMain.getChildren().add(rootAgendaView);
            AgendaViewController agendaViewController = fxmlLoader.getController();
            agendaViewController.setDataUtil(dataUtil);
            agendaViewController.setOlProvincias(olProv);
            agendaViewController.setOlPersonas(olPers);
            agendaViewController.cargarTodasPersonas();

            imagenMain.setVisible(false);
            label.setVisible(false);

        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }
    public void setRootMain(Pane rootMain) {
        this.rootMain = rootMain;
    }
    public void setDataUtil(DataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }
    public void setOlProv(ObservableList olProv) {
        this.olProv = olProv;
    }
    public void setOlPers(ObservableList olPers) {
        this.olPers = olPers;
    }

//    @FXML
//    public void iniciaApp(InputEvent event) {
//    }
}
