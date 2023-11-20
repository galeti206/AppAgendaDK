package es.ieslosmontecillos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AgendaViewController implements Initializable {
    private DataUtil dataUtil;
    private ObservableList<Provincia> olProvincias;
    private ObservableList<Persona> olPersonas;
    @FXML
    private TableView<Persona> tableViewAgenda;
    @FXML
    private TableColumn<Persona, String> columnNombre;
    @FXML
    private TableColumn<Persona, String> columnApellidos;
    @FXML
    private TableColumn<Persona, String> columnEmail;
    @FXML
    private TableColumn<Persona, String> columnProvincia;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldApellidos;
    private Persona personaSeleccionada;
    @FXML
    private AnchorPane rootAgendaView;


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
    public void initialize(URL url, ResourceBundle rb) {
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnProvincia.setCellValueFactory(
                cellData -> {
                    SimpleStringProperty property = new SimpleStringProperty();
                    if (cellData.getValue().getProvincia() != null) {
                        System.out.println("Provincia: " + cellData.getValue().getProvincia().getNombre());
                        property.setValue(cellData.getValue().getProvincia().getNombre());
                    }
                    return property;
                });
        /*columnProvincia.setCellValueFactory(
                cellData->{
                    SimpleStringProperty property=new SimpleStringProperty();
                    if (cellData.getValue().getProvincia()!= 0){
                        int idProv = cellData.getValue().getProvincia();
                        Provincia laProvincia;
                        FilteredList<Provincia> flProvincias = olProvincias.filtered(issue
                                -> issue.getId() == idProv);
                        laProvincia = flProvincias.get(0);
                        property.setValue(laProvincia.getNombre());
                    }
                    return property;
                });*/
        tableViewAgenda.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    personaSeleccionada = newValue; // Obtiene la persona seleccionada en la tabla
                    if (personaSeleccionada != null) {
                        // Muestra los datos del nombre y apellido de esa persona en los textfield
                        textFieldNombre.setText(personaSeleccionada.getNombre());
                        textFieldApellidos.setText(personaSeleccionada.getApellidos());
                    } else {
                        textFieldNombre.setText("");
                        textFieldApellidos.setText("");
                    }
                });
    }

    public void cargarTodasPersonas() {
        tableViewAgenda.setItems(FXCollections.observableArrayList(olPersonas));
    }

    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
        if (personaSeleccionada != null) {
            // Actualiza los datos de la persona seleccionada con los nuevos introducidos en el textfield
            personaSeleccionada.setNombre(textFieldNombre.getText());
            personaSeleccionada.setApellidos(textFieldApellidos.getText());

            // Actualiza los datos de la persona en la base de datos
            dataUtil.actualizarPersona(personaSeleccionada);

            // Actualiza los datos del tableview con los actualizados del textField
            int numFilaSeleccionada = tableViewAgenda.getSelectionModel().getSelectedIndex();
            tableViewAgenda.getItems().set(numFilaSeleccionada,personaSeleccionada);

            // Mueve el foco a la persona actualizada del tableView una vez pulsado el botón
            TablePosition pos = new TablePosition(tableViewAgenda,numFilaSeleccionada,null);
            tableViewAgenda.getFocusModel().focus(pos);
            tableViewAgenda.requestFocus();
        }
    }

    @FXML
    private void onActionButtonNuevo(ActionEvent event){
        try{
            // Cargar la vista de detalle
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/PersonaDetalleView.fxml"));
            Parent rootDetalleView=fxmlLoader.load();
            // Ocultar la vista de la lista
            rootAgendaView.setVisible(false);

            PersonaDetalleViewController personaDetalleViewController =
                    (PersonaDetalleViewController) fxmlLoader.getController();
            personaDetalleViewController.setRootAgendaView(rootAgendaView);

            //Añadir la vista detalle al StackPane principal para que se muestre
            StackPane rootMain = (StackPane) rootAgendaView.getScene().getRoot();
            rootMain.getChildren().add(rootDetalleView);

            //Intercambio de datos funcionales con el detalle
            personaDetalleViewController.setTableViewPrevio(tableViewAgenda);
            personaDetalleViewController.setDataUtil(dataUtil);

            // Al ser nueva envía true
            personaSeleccionada = new Persona();
            personaDetalleViewController.setPersona(personaSeleccionada,true);

            // Muestra en la vista los datos de la persona seleccionada
            personaDetalleViewController.mostrarDatos();
        } catch (IOException | ParseException ex){
            System.out.println("Error volcado"+ex);}
    }
    @FXML
    private void onActionButtonEditar(ActionEvent event){
        try{
            // Cargar la vista de detalle
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/PersonaDetalleView.fxml"));
            Parent rootDetalleView=fxmlLoader.load();
            // Ocultar la vista de la lista
            rootAgendaView.setVisible(false);

            PersonaDetalleViewController personaDetalleViewController =
                    (PersonaDetalleViewController) fxmlLoader.getController();
            personaDetalleViewController.setRootAgendaView(rootAgendaView);

            //Añadir la vista detalle al StackPane principal para que se muestre
            StackPane rootMain = (StackPane) rootAgendaView.getScene().getRoot();
            rootMain.getChildren().add(rootDetalleView);

            //Intercambio de datos funcionales con el detalle
            personaDetalleViewController.setTableViewPrevio(tableViewAgenda);
            personaDetalleViewController.setDataUtil(dataUtil);

            // Al ser nueva envía false
            personaDetalleViewController.setPersona(personaSeleccionada,false);

            // Muestra en la vista los datos de la persona seleccionada
            personaDetalleViewController.mostrarDatos();
        } catch (IOException | ParseException ex){
            System.out.println("Error volcado"+ex);}
    }

    @FXML
    private void onActionButtonSuprimir(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText("¿Desea suprimir el siguiente registro?");
        alert.setContentText(personaSeleccionada.getNombre() + " "
                + personaSeleccionada.getApellidos());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            // Acciones a realizar si el usuario acepta
            dataUtil.eliminarPersona(personaSeleccionada);
            tableViewAgenda.getItems().remove(personaSeleccionada);
            tableViewAgenda.getFocusModel().focus(null);
            tableViewAgenda.requestFocus();
        }
        else
        {
            // Acciones a realizar si el usuario cancela
            // Acciones a realizar si el usuario cancela
            int numFilaSeleccionada = tableViewAgenda.getSelectionModel().getSelectedIndex();
            tableViewAgenda.getItems().set(numFilaSeleccionada,personaSeleccionada);
            TablePosition pos = new TablePosition(tableViewAgenda, numFilaSeleccionada,null);
            tableViewAgenda.getFocusModel().focus(pos);
            tableViewAgenda.requestFocus();
        }
    }



}
