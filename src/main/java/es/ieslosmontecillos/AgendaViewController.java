package es.ieslosmontecillos;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AgendaViewController implements Initializable {
    private DataUtil dataUtil;
    private ObservableList<Provincia> olProvincias;
    private ObservableList<Persona> olPersonas;

    public void setDataUtil(DataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    public void setOlProvincias(ObservableList<Provincia> olProvincias) {
        this.olProvincias = olProvincias;
    }

    public void setOlPersonas(ObservableList<Persona> olPersonas) {
        this.olPersonas = olPersonas;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
