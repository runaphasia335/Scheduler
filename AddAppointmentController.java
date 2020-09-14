//Carlos Perez
package View;

import Database.Queries;;
import Model.Counselor;
import Model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.*;
import java.util.ResourceBundle;

import static View.DashboardController.holidayDates;

public class AddAppointmentController implements Initializable {
    @FXML
    private TextField counselorField;
    @FXML
    private DatePicker calendar;
    @FXML
    private ComboBox descriptionBox;
    @FXML
    private ComboBox times;
    @FXML
    private TextArea notesArea;
    @FXML
    private TextField patientNameField;
    @FXML
    private TextField patientIdField;
    @FXML
    private TextField patientPhoneField;
    Stage stage;
    Parent scene;


    private ObservableList<String> appointmentTypes = FXCollections.observableArrayList("Family", "Child", "Marriage",
            "Addiction");
    private ObservableList<String> appointmentTimes = FXCollections.observableArrayList("6:00am","7:00am","8:00am","9:00am","10:00am","11:00am",
            "12:00pm", "1:00pm", "2:00pm","3:00pm","4:00pm","5:00pm","6:00pm","7:00pm","8:00pm","9:00pm", "10:00pm","11:00pm","12:00pm");


    public void actionCancel(ActionEvent e) throws IOException {
        stage = (Stage) ((Button)e.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
        stage.setScene(new Scene(scene));
        stage.close();
    }
///    populates fields from the table row chosen in either patientInfoTableView. Method is called in DashboardController
//    after FXML file has been loaded. Ensures the field data has been assigned properly before opening FXML file.
    public void populatedFields(Patient patient){
        Counselor user = new Counselor();
        patientNameField.setText(patient.getPt_name());
        patientIdField.setText(String.valueOf(patient.getPt_id()));
        patientPhoneField.setText(patient.getAddress().getPhone());
        counselorField.setText(String.valueOf(user.getC_id()));
    }

//    Creates appointments. Uses methods validation() and holidayDates() ----------
    @FXML
    public void actionSaveAppointment(ActionEvent e) throws ParseException {
        int id = Integer.parseInt(patientIdField.getText());
        String c_Id = counselorField.getText();
        int description = descriptionBox.getSelectionModel().getSelectedIndex() + 1;
        String time = times.getValue().toString();
        String notes = notesArea.getText();
        LocalDate date = calendar.getValue();

        try {
            if (Queries.overlappingAppointment(-1,time, date)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Overlapping Appointments");
                alert.setContentText("You have an appointment scheduled in this time slot");
                alert.showAndWait();
            } else if (holidayDates(date) && validation()) {
                Queries.addAppointment(id, c_Id, description, time, date, notes);

                stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
                stage.close();


            }
        } catch(Exception exception) {
            exception.fillInStackTrace();
        }
    }

    //-----------Method to validate all necessary fields are field to create an appointment. -----
    private Boolean validation(){

        if(descriptionBox.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Please choose a type");
            alert.show();

        }

        else if(times.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Please choose a time");
            alert.show();

        } else if(calendar.getValue() == null || calendar.getValue().isBefore(LocalDate.now())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Date is empty/In the past");
            alert.setContentText("Please choose a valid date");
            alert.show();

        }else if(notesArea.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Notes are empty");
            alert.setContentText("Please enter notes");
            alert.show();
        } else {
            return true;
        }
        return false;
    }

//---Method used to validate no appointments are scheduled for certain holidays. Used in line 70, actionSaveAppointment()


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        times.setItems(appointmentTimes);
        descriptionBox.setItems(appointmentTypes);

//        Oracle.(2015) Class DatePicker. Retrieved from https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/DatePicker.html
//        used to blocked past dates and weekends when business is closed. Lambda used to simplify the Callback function.
        calendar.setDayCellFactory(picker -> {
            return new DateCell() {
                public void updateItem(LocalDate calendar, boolean empty){
                    super.updateItem(calendar, empty);
                      if(calendar.getDayOfWeek() == DayOfWeek.SATURDAY ||
                               calendar.getDayOfWeek() == DayOfWeek.SUNDAY){
                          setDisable(true);
                          setStyle("-fx-background-color: #ffc0cb;");
                      }
                }
            };
        });

    }

}
