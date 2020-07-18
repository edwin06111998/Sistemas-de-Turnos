/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXMLControllers;

import ComponentesSistema.ClassSerializer;
import ComponentesSistema.GeneradorTurnos;
import ComponentesSistema.Medico;
import ComponentesSistema.Paciente;
import ComponentesSistema.Puesto;
import ComponentesSistema.Sintoma;
import ComponentesSistema.Turno;
import FileReaders.*;
import FileReaders.SintomasFileReader;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class RegistrosController implements Initializable {
    
    private static RegistrosController singleInstance;
    
    //Instancia Pagina Principal
    SitemaPrincipalController principal;

    //FUNCIONALIDAD COMBOBOX
    private ObservableList<String> Lgenero;
    private ObservableList<String> LEspecialidad;
    private ObservableList<Sintoma> LSintomas;
    private ObservableList<Medico> LMedico;  //AGREGAR INFORMACION
    private ObservableList<Puesto> LPuesto; // AGREGAR INFORMACION
    private LectorArchivos reader;

    

    @FXML
    private ComboBox<String> cmbGenero;
    @FXML
    private ComboBox<String> cmbEspecialidad;
    @FXML
    private ComboBox<Sintoma> cmbSintomas;
    @FXML
    private ComboBox<Medico> cmbMedicoresponsable;
    @FXML
    private ComboBox<Puesto> cmbPuesto;
    
    //TEXT FIELDS
    @FXML
    private TextField txtNumeroPuesto;
    @FXML
    private TextField txtNombreDoctor;
    @FXML
    private TextField txtApellidoDoctor;
    @FXML
    private TextField txtNombrePaciente;
    @FXML
    private TextField txtApellidoPaciente;
    @FXML
    private TextField txtEdad;

    // GUARDAR INFORMACION
    @FXML
    private Button btnGuardarDoctor;
    @FXML
    private Button btnGuardarPaciente;
    @FXML
    private Button btnGuardarPuesto;

    
    public static RegistrosController getInstance(){
        if(singleInstance == null){
            singleInstance = new RegistrosController();
        }
        return singleInstance;
    }
    
    
    
    /**
     * Method for set Itemns to combobox.
     */
    private void loadData() {
        cmbGenero.getItems().setAll(new String[]{"MASCULINO", "FEMENINO"});
        cmbEspecialidad.getItems().setAll(new String[]{"Medicina General", "Alergología", "Cardiología", "Angiología", "Cirugía General", "Dermatología", "Endocrinología", "Ecografía", "Hematología"});
        cmbSintomas.getItems().setAll(LSintomas);
        cmbMedicoresponsable.getItems().setAll(LMedico);
//        cmbPuesto.getItems().setAll(LPuesto);
    }


    /**
     * Method for save Doctor data.
     */
    @FXML
    void guardarDoctor(ActionEvent event) {
        Medico medico = new Medico(txtNombreDoctor.getText(),
                        txtApellidoDoctor.getText(),
                        cmbEspecialidad.getValue());
        cmbMedicoresponsable.getItems().add(medico);
        txtNombreDoctor.setText("");
        txtApellidoDoctor.setText("");
        cmbEspecialidad.setValue(null);
    }

    /**
     * Method for save Paciente data.
     */
    @FXML
    void guardarPaciente(ActionEvent event) {
        Paciente p =new Paciente(txtNombrePaciente.getText(),
        txtApellidoPaciente.getText(),
                cmbGenero.getValue(),
                Integer.parseInt(txtEdad.getText()),
                cmbSintomas.getValue()
        );
        p.guardarPaciente();
        Turno t = GeneradorTurnos.generarTurnoConPaciente(p) ;
        
        txtNombrePaciente.setText("");
        txtApellidoPaciente.setText("");
        cmbEspecialidad.setValue("");
        cmbSintomas.setValue(null);
        txtEdad.setText("");
        principal.getTurnos().offer(t);
        principal.asignarPuestoATurno();
    }

    /**
     * Method for save Puesto data.
     */
    @FXML
    void guardarPuesto(ActionEvent event) {
        Puesto puesto = new Puesto(txtNumeroPuesto.getText(),cmbMedicoresponsable.getValue());
        puesto.guardarPuesto();
        principal.getPuestosLibres().add(puesto);
        cmbPuesto.getItems().add(puesto);
        txtNumeroPuesto.setText("");
        cmbMedicoresponsable.setValue(null);
    }

    //FUNCIONALIDAD MENU REGISTROS.
    @FXML
    private Button btnRegistroPaciente, btnRegistroDoctor, btnCrearPuesto, btnEliminarPuesto;
    @FXML
    private AnchorPane hRegistroPaciente, hEliminarPuesto, hCrearPuesto, hRegistroDoctores;

    /**
     * Method for set visibility to AnchorPane.
     */
    @FXML
    private void handleButtonAction(Event event) {

        if (event.getTarget() == btnRegistroPaciente) {
            hRegistroPaciente.setVisible(true);
            hRegistroDoctores.setVisible(false);
            hCrearPuesto.setVisible(false);
            hEliminarPuesto.setVisible(false);

        }
        if (event.getTarget() == btnRegistroDoctor) {
            hRegistroPaciente.setVisible(false);
            hRegistroDoctores.setVisible(true);
            hCrearPuesto.setVisible(false);
            hEliminarPuesto.setVisible(false);

        }
        if (event.getTarget() == btnCrearPuesto) {
            hRegistroPaciente.setVisible(false);
            hRegistroDoctores.setVisible(false);
            hCrearPuesto.setVisible(true);
            hEliminarPuesto.setVisible(false);

        }
        if (event.getTarget() == btnEliminarPuesto) {
            hRegistroPaciente.setVisible(false);
            hRegistroDoctores.setVisible(false);
            hCrearPuesto.setVisible(false);
            hEliminarPuesto.setVisible(true);

        }
    }
    
    /**
     * Constructor, Inicializa la instancia de la ventana principal
     */
    public RegistrosController() {
        principal = SitemaPrincipalController.getInstance();
        llenarListas();
        
    }
    
    /**
     * Llena las listas
     */
    public void llenarListas(){
        reader = new MedicoFileReader();
        System.out.println("Medicos ser");
        LMedico = reader.LeerArchivo("medicos.ser");
        System.out.println("Puestos Ser");
        reader = new PuestoFileReader();
        LPuesto = reader.LeerArchivo("puestos.ser");
        System.out.println("Sintomas.txt");
        reader = new SintomasFileReader();
        LSintomas = reader.LeerArchivo("sintomas.txt");
    }
    

    /**
     *
     * @param url
     * @param rb
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        loadData();
        cmbSintomas.setEditable(false);
    }
    
    
    public void serializarListas(){
        ClassSerializer.guardarObjeto("medicos.ser",LMedico);
        ClassSerializer.guardarObjeto("puestos.ser", LPuesto);
    }

}
