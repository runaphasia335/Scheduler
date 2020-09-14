//Carlos Perez
package View;

import Database.Queries;
import Model.Address;
import Model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditPatientController implements Initializable{
    @FXML
    private ComboBox stateBox;
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField address2Field;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField postalField;
    @FXML
    private TextField insuranceField;
    @FXML
    private TextField cityField;
    @FXML
    private Button buttonSave;
    int patientId;
    int addressId;
    Parent scene;
    Stage stage;

    private ObservableList<String> states = FXCollections.observableArrayList("California","Colorado","Pennsylvania","New York");

    @FXML
    private void actionSaveEdit(ActionEvent e) throws IOException {
        String name = nameField.getText();
        String address = addressField.getText();
        String address2 = address2Field.getText();
        String phone = phoneField.getText();
        String state = stateBox.getSelectionModel().getSelectedItem().toString();
        String postalCode = postalField.getText();
        String insurance = insuranceField.getText();
        String city = cityField.getText();
        if(validation()){

            Address editedAddress = new Address(addressId,address,address2,city,state, postalCode,phone);
            Patient editedPatient = new Patient(patientId,name, insurance, editedAddress);
           Queries.editPatient(editedPatient);
            stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.close();
        }
    }

//    validates each field to ensure the requirements to edit a patient have been meet
    private Boolean validation(){
        if(nameField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Name field empty");
            alert.setContentText("Please type a patient name");
            alert.show();
            return false;
        }
        else if(addressField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Address is empty");
            alert.setContentText("Please type a valid address");
            alert.show();
        }
        else if(phoneField.getText().isEmpty() || phoneField.getText().length() != 12){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Please type a valid phone number");
            alert.show();

        }

        else if(postalField.getText().isEmpty() || postalField.getText().length() != 5){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Please type a valid postal code");
            alert.show();

        }
        else if(cityField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Please type a city");
            alert.show();

        }else if(stateBox.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Please choose a state");
            alert.show();

        }else if(insuranceField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Please insert an insurance provider");
            alert.show();
        }


        else {
            return true;
        }
        return false;
    }

//        after pressing tab or enter, add a hyphen to separate the area code and the office code.
    @FXML
    private void addHyphen(KeyEvent keyEvent) {
        if(phoneField.getText().length() == 10)
            if(keyEvent.getCode().equals(KeyCode.ENTER) || keyEvent.getCode().equals(KeyCode.TAB)){
                String newText = phoneField.getText().replaceFirst("(.{3})(.{3})","$1-$2-");
                phoneField.setText(newText);
            }
    }


    //all fields are disabled until the edit button has been pressed
    @FXML
    public void enableFields(ActionEvent e) {
        buttonSave.setDisable(false);
        nameField.setDisable(false);
        addressField.setDisable(false);
        address2Field.setDisable(false);
        phoneField.setDisable(false);
        stateBox.setDisable(false);
        postalField.setDisable(false);
        insuranceField.setDisable(false);
        cityField.setDisable(false);
    }


//    this method is called in DashboardController so they fields are populated before opening window
    public void fieldsToPopulate(Patient patient){

        patientId = patient.getPt_id();
        addressId = patient.getAddress().getAddress_id();
        nameField.setText(patient.getPt_name());
        addressField.setText(patient.getAddress().getAddressline_1());
        address2Field.setText(patient.getAddress().getAddressline_2());
        phoneField.setText(patient.getAddress().getPhone());
        stateBox.setValue(patient.getAddress().getState());
        insuranceField.setText(patient.getINS_PR());
        cityField.setText(patient.getAddress().getCity());
        postalField.setText(patient.getAddress().getPostal_code());

    }

    @FXML
    private void actionBack(ActionEvent e) throws IOException {
        stage = (Stage) ((Button)e.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/EditPatient.fxml"));
        stage.setScene(new Scene(scene));
        stage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stateBox.setItems(states);
    }
}