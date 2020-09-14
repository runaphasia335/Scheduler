//Carlos Perez

package View;

import Database.Queries;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    @FXML
    private TextArea typeTextArea;
    @FXML
    private TextArea stateAreaText;
    @FXML
    private TextArea counselorTextArea;
    Stage stage;
    Parent scene;


    //lambda .forEach() implemented here to cycle through all the appointments gathered by Queries.getAllAppointmentsByCounselor()
//        and appends the results to the area text.
    private void counselorReport() {
     ObservableList<String> counselorAppointments = Queries.getAllAppointmentsByCounselor();
     if(!counselorAppointments.isEmpty()){
         counselorAppointments.forEach(couselor -> {
             counselorTextArea.appendText(couselor);
         });
     }else{
         counselorTextArea.setText("NO REPORT FOUND");
         System.out.println("problem over here ----counselorReport");
     }


    }
    //lambda .forEach() implemented here to cycle through all the appointments gathered by Queries.getAllAppointmentsByState()
//        and appends the results to the area text.
    private void stateReport(){
        ObservableList<String>  byStateAppointments = Queries.getAllAppointmentsByState();
        if(!byStateAppointments.isEmpty()){
            byStateAppointments.forEach(state ->{
                stateAreaText.appendText(state);
            });
        }else{
            stateAreaText.setText("NO REPORT FOUNDS");
            System.out.println("problem over here ----stateReport");
        }
    }
    //lambda .forEach() implemented here to cycle through all the appointments gathered by Queries.getAllAppointmentsByType()
//        and appends the results to the area text.
    private void typeReport(){
        ObservableList<String> byDescriptionAppointments = Queries.getAllAppointmentsByType();
        if(!byDescriptionAppointments.isEmpty()){
            byDescriptionAppointments.forEach(type ->{
                typeTextArea.appendText(type);
            });
        }else{
            typeTextArea.setText("NO REPORT FOUND");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        counselorReport();
        stateReport();
        typeReport();
    }

    public void actionBack(ActionEvent e) throws IOException {
        stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
