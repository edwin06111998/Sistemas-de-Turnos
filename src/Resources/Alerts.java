/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author PC
 */
public class Alerts {
    public static Optional<ButtonType> confirmation(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION ALERT");
        alert.setHeaderText("CERRAR EL PROGRAMA");
        alert.setContentText("SI DA CLIC EN ACEPTAR, TERMINARÁ EL PROGRAMA \n ¿ESTÁ SEGURO QUE DESEA CONTINUAR?");
        return alert.showAndWait();    
    }
    
    public static Optional<ButtonType> confirmPuestoDelete(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION ALERT");
        alert.setHeaderText("Eliminar Puesto");
        alert.setContentText("SI DA CLIC EN ACEPTAR, "
                + "EL MEDICO DEL PUESTO QUEDARÁ LIBRE Y SIN PUESTO, EL PUESTO QUEDARÁ ELIMINADO.");
        return alert.showAndWait();    
    }
    
}
