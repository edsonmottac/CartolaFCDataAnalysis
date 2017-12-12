/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartolafcdataanalysis;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author edson
 */
public class FXMLForm1Controller implements Initializable {

    @FXML
    private Label lblMensagem;
    private Button btAction;
    
    @FXML
    public void ClickButton2(ActionEvent rb) {
        lblMensagem.setText("testando!");
    }

    public void Click_btOpen(ActionEvent rb) {
        FXMLForm1Main.getStage().close();
                
        FXMLForm2Main f = new FXMLForm2Main();
        try {
            f.start(new Stage());
        } catch (IOException ex) {
            Logger.getLogger(FXMLForm1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
            
       
}
