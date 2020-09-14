//Carlos Perez


package View;
import Database.Queries;
import Model.Address;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddPatientController implements Initializable {
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
    private TextField cityField;
    @FXML
    private TextField postalField;
    @FXML
    private TextField insuranceField;
    Parent scene;
    Stage stage;

    private ObservableList<String> states = FXCollections.observableArrayList("California", "Colorado", "Pennsylvania", "New York");

    @FXML
    public void actionSavePatient(ActionEvent e) throws IOException {
        String name = nameField.getText();
        String address = addressField.getText();
        String address2 = address2Field.getText();
        String phone = phoneField.getText();
        String state = stateBox.getSelectionModel().getSelectedItem().toString();
        String postalCode = postalField.getText();
        String insurance = insuranceField.getText();
        String city = cityField.getText();
        if (validation()) {

            Address newAddress = new Address(-1, address, address2, city, state, postalCode, phone);
            Queries.addPatient(name, insurance, newAddress);
        }
        stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
        stage.setTitle("Dashboard");
        stage.setScene(new Scene(scene));
        stage.show();
    }


    //-----------Method to validate all necessary fields are field to create a patient---------
    private Boolean validation() {
        if (nameField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Name field empty");
            alert.setContentText("Please type a patient name");
            alert.show();
        } else if (addressField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Address is empty");
            alert.setContentText("Please type a valid address");
            alert.show();

        } else if (phoneField.getText().isEmpty() || phoneField.getText().length() != 12) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Please type a valid phone number");
            alert.show();


        } else if (postalField.getText().isEmpty() || postalField.getText().length() != 5) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Please type a valid postal code");
            alert.show();


        } else if (cityField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Please choose a city");
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


//    after pressing tab or enter, add a hyphen to separate the area code and the office code.
    @FXML
    public void addHyphen(KeyEvent keyEvent) {
        if(phoneField.getText().length() == 10)
            if(keyEvent.getCode().equals(KeyCode.ENTER) || keyEvent.getCode().equals(KeyCode.TAB)){
                String newText = phoneField.getText().replaceFirst("(.{3})(.{3})","$1-$2-");
                phoneField.setText(newText);
            }
    }




    @FXML
    public void actionBack(ActionEvent e) throws IOException {
        stage = (Stage) ((javafx.scene.control.Button) e.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
        stage.setTitle("Dashboard");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stateBox.setItems(states);
    }

}

