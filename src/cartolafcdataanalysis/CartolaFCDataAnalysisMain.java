/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartolafcdataanalysis;

import java.io.IOException;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author edson
 */
public class CartolaFCDataAnalysisMain extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("CartolaFCDataAnalysisFXML.fxml"));
       Scene scene = new Scene(root);
       primaryStage.setScene(scene);
       primaryStage.show();
    }
   
    public static void main(String[] args) {
        launch(args);
    }
    
}
