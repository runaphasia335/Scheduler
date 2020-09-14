//Carlos Perez
package View;

import Database.Queries;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static View.DashboardController.holidayDates;
public class EditAppointmentController implements Initializable {
    @FXML
    private Button saveEditButton;
    @FXML
    private TextField cField;
    @FXML
    private DatePicker calendar;
    @FXML
    private ComboBox descriptionBox;
    @FXML
    private ComboBox times;
    @FXML
    private TextArea notesArea;
    @FXML
    private TextField ptNameField;
    @FXML
    private TextField ptIdField;
    @FXML
    private TextField ptPhoneField;
    int aptId;
    Parent scene;
    Stage stage;
    private ObservableList<String> appointmentTypes = FXCollections.observableArrayList("Marriage", "Family", "Child",
            "Addiction");

    private ObservableList<String> appointmentTimes = FXCollections.observableArrayList("6:00am","7:00am","8:00am","9:00am","10:00am","11:00am",
            "12:00pm", "1:00pm", "2:00pm","3:00pm","4:00pm","5:00pm","6:00pm","7:00pm","8:00pm","9:00pm", "10:00pm","11:00pm","12:00pm");

    //-------------edit appointments. Uses methods validation() and holidayDates()-----------------------------
    @FXML
    public void actionEditAppointment(ActionEvent e) throws ParseException {
        int description = descriptionBox.getSelectionModel().getSelectedIndex() + 1;
        String time = times.getValue().toString();
        String notes = notesArea.getText();
        LocalDate date = calendar.getValue();
        try {
            if (Queries.overlappingAppointment(aptId, time, date)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Overlapping Appointments");
                alert.setContentText("You have an appointment scheduled in this time slot");
                alert.showAndWait();
            } else if (holidayDates(date) && validation()) {
                Queries.editAppointment(aptId, description, time, notes, date);
                stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
                stage.close();


            }
        } catch (Exception exception) {
            exception.fillInStackTrace();
        }

    }

    public void actionCancel(ActionEvent e) throws IOException {
        stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
        stage.setScene(new Scene(scene));
        stage.close();
    }


    //    populates fields from the table row chosen in either WeeklyTableview, biWeeklyTableview, and monthlyTableView-----
//    method is called in DashboardController after loading FXML file.
    public void populateAppointment(Appointment appointment) {
        aptId = appointment.getApt_id();
        ptIdField.setText(String.valueOf(appointment.getPt_id()));
        ptNameField.setText(appointment.getPtName());
        ptPhoneField.setText(appointment.getPhone());
        cField.setText(String.valueOf(appointment.getCounselor_id()));
        descriptionBox.setValue(appointment.getDescription());
        times.setValue(appointment.getStart_datetime());
        calendar.setValue(LocalDate.parse(appointment.getDate()));
        notesArea.setText(appointment.getNotes());

    }

    //-----------Method to validate all necessary fields are field to create an appointment. Used in actionEditAppointment();
    private Boolean validation() {

        if (descriptionBox.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Please choose a type");
            alert.show();

        } else if (times.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Please choose a time");
            alert.show();

        } else if (calendar.getValue() == null || calendar.getValue().isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Date is empty/In the past");
            alert.setContentText("Please choose a valid date");
            alert.show();

        } else if (notesArea.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Notes are empty");
            alert.setContentText("Please enter notes");
            alert.show();

        } else {
            return true;
        }
        return false;
    }

//when editAppointment screen is loaded, save button has been disabled until one of them fields have been activated.
    @FXML
    public void notesEnableSaveButton(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1) {
            saveEditButton.setDisable(false);
        }
    }

    @FXML
    public void calendarEnableSaveButton(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1) {
            saveEditButton.setDisable(false);
        }
    }

    @FXML
    public void descriptionEnableSaveButton(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1) {
            saveEditButton.setDisable(false);
        }
    }

    @FXML
    public void timesEnableSaveButton(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1) {
            saveEditButton.setDisable(false);
        }
    }



//    ------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        times.setItems(appointmentTimes);
        descriptionBox.setItems(appointmentTypes);
//        Oracle.(2015) Class DatePicker. Retrieved from https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/DatePicker.html
//        used to blocked past dates and weekends when business is closed. Lambda used to simplify the Callback function.
        calendar.setDayCellFactory(picker -> {
            return new DateCell() {
                public void updateItem(LocalDate calendar, boolean empty) {
                    super.updateItem(calendar, empty);
                    if (calendar.getDayOfWeek() == DayOfWeek.SATURDAY ||
                            calendar.getDayOfWeek() == DayOfWeek.SUNDAY) {
                        setDisable(true);
                        setStyle("-fx-background-color: #ffc0cb;");
                    }
                }
            };
        });

    }
}
