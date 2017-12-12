/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartolafcdataanalysis;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class FXMLForm2Controller implements Initializable {

    @FXML
    private Button btAction;
    
    @FXML
    public void Click_btAction(ActionEvent rb) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("testando");
        a.setTitle("Meu Titulo");
        a.setContentText("Contúdo da mensagem!");
        a.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
