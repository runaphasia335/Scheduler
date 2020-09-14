//Carlos Perez

package View;

import Database.Queries;
import Model.Appointment;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ResourceBundle;


public class DashboardController implements Initializable {
    @FXML
    public Button editButton;
    @FXML
    private Label DateLabel;
    @FXML
    private TextField searchPatient;
    @FXML
    private TableColumn<Appointment, Integer> weeklyAptIdCol;
    @FXML
    private TableColumn<Appointment, Integer> biWeeklyAptIdCol;
    @FXML
    private TableColumn<Appointment, Integer> monthlyAptIdCol;
    @FXML
    private TitledPane weeklyPane;
    @FXML
    private TitledPane monthlyPane;
    @FXML
    private TitledPane biWeeklyPane;
    @FXML
    private TableColumn<Appointment, Integer> biWeeklyIdCol;
    @FXML
    private TableColumn<Appointment, String> biWeeklyNameCol;
    @FXML
    private TableColumn<Appointment, String> biWeeklyPhoneCol;
    @FXML
    private TableColumn<Appointment, String> biWeeklyCounselorCol;
    @FXML
    private TableColumn<Appointment, String> biWeeklyDescriptionCol;
    @FXML
    private TableColumn<Appointment, String> biweeklyNotesCol;
    @FXML
    private TableColumn<Appointment, String> biWeeklyStartCol;
    @FXML
    private TableColumn<Appointment, String> biWeeklyEndCol;
    @FXML
    private TableColumn<Appointment, String> biWeeklyDateCol;
    @FXML
    private TableColumn<Appointment, Integer> monthlyIdCol;
    @FXML
    private TableColumn<Appointment, String> monthlyNameCol;
    @FXML
    private TableColumn<Appointment, String> monthlyPhoneCol;
    @FXML
    private TableColumn<Appointment, String> monthlyCounselorCol;
    @FXML
    private TableColumn<Appointment, String> monthlyDescriptionCol;
    @FXML
    private TableColumn<Appointment, String> monthlyNotesCol;
    @FXML
    private TableColumn<Appointment, String> monthlyStartCol;
    @FXML
    private TableColumn<Appointment, String> monthlyEndCol;
    @FXML
    private TableColumn<Appointment, String> monthlyDateCol;
    @FXML
    private TableColumn<Appointment, Integer> weeklyIdCol;
    @FXML
    private TableColumn<Appointment, String> weeklyNameCol;
    @FXML
    private TableColumn<Appointment, String> weeklyPhoneCol;
    @FXML
    private TableColumn<Appointment, String> weeklyCounselorCol;
    @FXML
    private TableColumn<Appointment, String> weeklyDescriptionCol;
    @FXML
    private TableColumn<Appointment, String> weeklyNotesCol;
    @FXML
    private TableColumn<Appointment, String> weeklyStartCol;
    @FXML
    private TableColumn<Appointment, String> weeklyEndCol;
    @FXML
    private TableColumn<Appointment, String> weeklyDateCol;
    @FXML
    private TableView<Appointment> weeklyTableView;
    @FXML
    private TableView<Appointment> biWeeklyTableView;
    @FXML
    private TableView<Appointment> monthlyTableView;
    @FXML
    private TableView<Patient> patientInfoView;
    @FXML
    private TableView<Appointment> patientAppointments;
    @FXML
    private TableColumn<Appointment, String> patientNameColumn;
    @FXML
    private TableColumn<Appointment, String> phoneColumn;
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    @FXML
    private TableColumn<Appointment, String> startColumn;
    @FXML
    private TableColumn<Appointment, String> endColumn;
    @FXML
    private TableColumn<Appointment, String> dateColumn;
    @FXML
    private TableColumn<Patient, Integer> patientIDCol;
    @FXML
    private TableColumn<Patient, String> patientNameCol;
    Appointment weekly;
    Appointment biWeekly;
    Appointment monthly;
    Appointment day;
    Patient patientToPopulate;
    Patient patient;
    Stage stage;
    Parent scene;


    @FXML
    private void actionAddPatient(ActionEvent e) throws IOException {
        stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddPatient.fxml"));
        stage.setTitle("Add Patient");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    private void actionEditPatient(ActionEvent e) throws IOException {
        patientToPopulate = patientInfoView.getSelectionModel().getSelectedItem();
        if (patientToPopulate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No item selected");
            alert.setContentText("Please choose a patient to edit");
            alert.show();
        } else {
            getPatientInfo();

        }
    }
//loads FXML file and uses controller to populates fields before showing screen
    private void getPatientInfo() throws IOException {
        patient = Queries.getPatient(patientToPopulate);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EditPatient.fxml"));
        Parent root = loader.load();
        EditPatientController controller = loader.getController();
        controller.fieldsToPopulate(patient);
        Scene scene = new Scene(root);
        Stage window = new Stage();
        window.setScene(scene);
        window.show();

    }


    @FXML
    private void actionDeletePatient(ActionEvent actionEvent) {
        patient = patientInfoView.getSelectionModel().getSelectedItem();
        if (patient != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Patient to be deleted");
            alert.setContentText("Are you sure you want to continue?");
            alert.showAndWait();
            Queries.deletePatient(patient);
            patientInfoView.setItems(Queries.getAllPatients());

        } else {
            return;
        }
    }

//--------------------------------------------------------------------------------------------------------


    @FXML
    private void addAppointment() throws IOException {
        patientToPopulate = patientInfoView.getSelectionModel().getSelectedItem();
        if (patientToPopulate != null) {
            populatePatientInfoforAppointment();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No patient chosen");
            alert.setContentText("Please choose a patient before continuing");
            alert.show();
        }


    }

//    populates the information of the patient when adding or editing an appointment
    private void populatePatientInfoforAppointment() throws IOException {
        patient = Queries.getPatient(patientToPopulate);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddAppointment.fxml"));
        try {
            Parent root = loader.load();
            AddAppointmentController controller = loader.getController();
            controller.populatedFields(patient);
            Scene scene = new Scene(root);
            Stage window = new Stage();
            window.setScene(scene);
            window.showAndWait();
            populateAllTables();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }


    @FXML
    private void editAppointment(ActionEvent e) throws IOException {
        day = patientAppointments.getSelectionModel().getSelectedItem();
        weekly = weeklyTableView.getSelectionModel().getSelectedItem();
        biWeekly = biWeeklyTableView.getSelectionModel().getSelectedItem();
        monthly = monthlyTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EditAppointment.fxml"));
        Parent root = loader.load();
        EditAppointmentController controller = loader.getController();
        if(day != null){
            controller.populateAppointment(day);
        }
        else if (weekly != null && weeklyPane.isExpanded()) {
            controller.populateAppointment(weekly);

        } else if (biWeekly != null && biWeeklyPane.isExpanded()) {
            controller.populateAppointment(biWeekly);

        } else if (monthly != null && monthlyPane.isExpanded()) {
            controller.populateAppointment(monthly);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No appointment chosen");
            alert.setContentText("Please choose an appointment before continuing");
            alert.show();
            return;
        }

        Scene scene = new Scene(root);
        Stage window = new Stage();
        window.setScene(scene);
        window.showAndWait();
        populateAllTables();
    }


    @FXML
    private void actionDeleteAppointment(ActionEvent actionEvent) {
        day = patientAppointments.getSelectionModel().getSelectedItem();
        weekly = weeklyTableView.getSelectionModel().getSelectedItem();
        biWeekly = biWeeklyTableView.getSelectionModel().getSelectedItem();
        monthly = monthlyTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Deleting appointment");
        alert.setContentText("Are you sure you want to delete appointment?");
        alert.setContentText("Are you sure you want to continue?");
        alert.showAndWait();
        if(day != null){
            Queries.deleteAppointment(day);
        }
       else if (weekly != null && weeklyPane.isExpanded()) {
            Queries.deleteAppointment(weekly);

        } else if (biWeekly != null && biWeeklyPane.isExpanded()) {
            Queries.deleteAppointment(biWeekly);

        } else if (monthly != null && monthlyPane.isExpanded()) {
            Queries.deleteAppointment(monthly);
        } else  {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setContentText("Error deleting appointment");

            }
        populateAllTables();

        }

//------------------------------------------------------------------------------------------------------------------------------
//double click a row from a tableview to edit an appointment


    @FXML
    private void weeklyDoubleClick(MouseEvent mouseEvent) throws IOException {
        weekly =weeklyTableView.getSelectionModel().getSelectedItem();
        if(mouseEvent.getClickCount() == 2 && weekly != null){
            loadFXMLBefore(weekly);
        }
    }


    @FXML
    private void biWeeklyDoubleClick(MouseEvent mouseEvent) throws IOException {
        biWeekly = biWeeklyTableView.getSelectionModel().getSelectedItem();
        if(mouseEvent.getClickCount() == 2 && biWeekly != null){
            loadFXMLBefore(biWeekly);
        }
    }

    @FXML
    private void monthlyDoubleClick(MouseEvent mouseEvent) throws IOException {
        monthly = monthlyTableView.getSelectionModel().getSelectedItem();
        if(mouseEvent.getClickCount() == 2 && monthly != null){
           loadFXMLBefore(monthly);
        }
    }

    @FXML
    private void dayDoubleclick(MouseEvent mouseEvent) throws IOException {
        day = patientAppointments.getSelectionModel().getSelectedItem();
        if(mouseEvent.getClickCount() == 2 && day != null){
            loadFXMLBefore(day);
        }

    }

//    loads the FXML file to populate the fields for a select table item
    private void loadFXMLBefore(Appointment tableView) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EditAppointment.fxml"));
        Parent root = loader.load();
        EditAppointmentController controller = loader.getController();
        controller.populateAppointment(tableView);
        Scene scene = new Scene(root);
        Stage window = new Stage();
        window.setScene(scene);
        window.showAndWait();
        populateAllTables();
    }
//  ----------------------------------------------------------------------------------------------------------------



//refreshes each titlePane when clicked on
    @FXML
    private void populateTitlePaneTables(MouseEvent mouseEvent) {
        weeklyTableView.setItems(Queries.getWeeklyPatientAppointment());
        biWeeklyTableView.setItems(Queries.getBiWeeklyPatientAppointment());
        monthlyTableView.setItems(Queries.getMonthlyPatientAppointment());
    }



//this method is called after edit or adding an appointment so all tables are refreshed with current data
    private void populateAllTables() {
        patientAppointments.setItems(Queries.getDayPatientAppointment());
        weeklyTableView.setItems(Queries.getWeeklyPatientAppointment());
        biWeeklyTableView.setItems(Queries.getBiWeeklyPatientAppointment());
        monthlyTableView.setItems(Queries.getMonthlyPatientAppointment());
    }


//    uses text field to search for a patient in the patientInfoView tableview. If the text matches an item, it will
//    populate the tableview with the found item. When backspace or delete keys are pressed on keyboard, it will call
//    onKeyPressed to populate the fields again with all patients.
    @FXML
    private void actionSearchPatient(){
      String search = searchPatient.getText();
      if(!search.isEmpty()){
          ObservableList<Patient> searchResult = FXCollections.observableArrayList();
          for(Patient patient : patientInfoView.getItems()) {
              System.out.println(patient.getPt_name());
              if(patient.getPt_name().toLowerCase().contains(search.toLowerCase())){
                  searchResult.add(patient);
                  patientInfoView.setItems(searchResult);
              }
          }
      }
    }

    //   L'ubomir Nerad(October, 2013).Handling JavaFx Events. Retrieved from  https://docs.oracle.com/javafx/2/events/handlers.htm
//    repopulates patientInfoView when deleting text in searchPatient textfield after a successful search---------
    @FXML
    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.BACK_SPACE) || keyEvent.getCode().equals(KeyCode.DELETE)) {
            patientInfoView.setItems(Queries.getAllPatients());
        }

    }
// get called in initializable() and prompts an alert whenever there appointments in the 4 hour time frame
    private static void fourHourWarning(){
//lambda .forEach() implemented here to cycle through all the appointments gathered by Queries.fourHourWarning() and
//        assigns the necessary values extracted from each result.
        ObservableList<Appointment> upcomingAppointments = Queries.fourHourWarning();
       if(!upcomingAppointments.isEmpty()) {
           upcomingAppointments.forEach(appointment -> {
               String name = appointment.getPtName();
               String phone = appointment.getPhone();
               String time = appointment.getStart_datetime();
               String description = appointment.getDescription();

               String text = "Name: " + name + " Phone: " + phone + " Time: " + time + " Type: " + description + "\n";

               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Upcoming Appointments");
               alert.setHeaderText("Appointments the next 4 hours");
               alert.setContentText(text);
               alert.show();
           });
       }else{
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Congratulations");
           alert.setHeaderText("Empty Schedule");
           alert.setContentText("You have no appointments");
           alert.show();
        }

    }

//method to prompt the user when an appointment is scheduled on these holidays. Uses TemporalAdjusters to find the next occurrence
//    of the DayOfWeek and adds the appropriate number of weeks from that day or adjust to the last occurrence of that week day:
//    for example, memorial day
    public static boolean holidayDates(LocalDate date){
        int year = date.getYear();
        LocalDate martinLuther = LocalDate.of(year, 1, 1).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).plusWeeks(2);
        LocalDate memorialDay = LocalDate.of(year, 5, 1).with(TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY));
        LocalDate veteransDay = LocalDate.of(year, 11, 11);
        LocalDate thanksGiving = LocalDate.of(year, 11, 1).with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY)).plusWeeks(3);
        LocalDate thanks2 = thanksGiving.plusDays(1);
        if(date.equals(martinLuther)|| date.equals(memorialDay) || date.equals(veteransDay)
                || date.equals(thanksGiving) || date.equals(thanks2)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Holiday");
            alert.setContentText("Please choose another day");
            alert.show();
            return false;
        }
        else{
            return true;
        }

    }


    @FXML
    private void viewReports(ActionEvent e) throws IOException {
        stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Report.fxml"));
        stage.setTitle("View Reports");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fourHourWarning();

//        sets the date on the right corner of the dashboard
        DateLabel.setText(LocalDate.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));


        patientAppointments.setItems(Queries.getDayPatientAppointment());

        patientInfoView.setItems(Queries.getAllPatients());
        patientIDCol.setCellValueFactory(new PropertyValueFactory<>("pt_id"));
        patientNameCol.setCellValueFactory(new PropertyValueFactory<>("pt_name"));


//        TableView for the appointments for the day
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("ptName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start_datetime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));


//        TitlePane for weekly
        weeklyTableView.setItems(Queries.getWeeklyPatientAppointment());

        weeklyIdCol.setCellValueFactory(new PropertyValueFactory<>("pt_id"));
        weeklyNameCol.setCellValueFactory(new PropertyValueFactory<>("ptName"));
        weeklyPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        weeklyCounselorCol.setCellValueFactory(new PropertyValueFactory<>("counselor_id"));
        weeklyDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        weeklyNotesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
        weeklyStartCol.setCellValueFactory(new PropertyValueFactory<>("start_datetime"));
        weeklyEndCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        weeklyDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        weeklyAptIdCol.setCellValueFactory(new PropertyValueFactory<>("apt_id"));


//        TitlePane for Bi-Weekly
        biWeeklyTableView.setItems(Queries.getBiWeeklyPatientAppointment());

        biWeeklyIdCol.setCellValueFactory(new PropertyValueFactory<>("pt_id"));
        biWeeklyNameCol.setCellValueFactory(new PropertyValueFactory<>("ptName"));
        biWeeklyPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        biWeeklyCounselorCol.setCellValueFactory(new PropertyValueFactory<>("counselor_id"));
        biWeeklyDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        biweeklyNotesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
        biWeeklyStartCol.setCellValueFactory(new PropertyValueFactory<>("start_datetime"));
        biWeeklyEndCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        biWeeklyDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        biWeeklyAptIdCol.setCellValueFactory(new PropertyValueFactory<>("apt_id"));


//        TitlePane for the month
        monthlyTableView.setItems(Queries.getMonthlyPatientAppointment());

        monthlyIdCol.setCellValueFactory(new PropertyValueFactory<>("pt_id"));
        monthlyNameCol.setCellValueFactory(new PropertyValueFactory<>("ptName"));
        monthlyPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        monthlyCounselorCol.setCellValueFactory(new PropertyValueFactory<>("counselor_id"));
        monthlyDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        monthlyNotesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
        monthlyStartCol.setCellValueFactory(new PropertyValueFactory<>("start_datetime"));
        monthlyEndCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        monthlyDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        monthlyAptIdCol.setCellValueFactory(new PropertyValueFactory<>("apt_id"));




    }


}






