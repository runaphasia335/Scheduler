//Carlos Perez
package View;

import Database.Queries;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML
    private Label mainOfficeLabel;
    @FXML
    private Label localTimeLabel;
    @FXML
    private Label userDate;
    @FXML
    private Label mainOfficeDate;
    @FXML
    private Label userTime;
    @FXML
    private Label mainOfficeTime;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField userNameField;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label pinLabel;
    @FXML
    private Label loginLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField pinField;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;
    private String alertTitle;
    private String alertHeader;
    private String alertContent;


    Parent scene;
    Stage stage;
    @FXML
    public void actionLogin(ActionEvent e) throws IOException {
        String username = userNameField.getText();
        String password = passwordField.getText();
        int pin = Integer.parseInt(pinField.getText());
        if(Queries.login(username,password,pin)){
            stage = (Stage) ((Button)e.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(scene));
            stage.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(alertTitle);
            alert.setHeaderText(alertHeader);
            alert.setContentText(alertContent);
            alert.showAndWait();
        }
    }
    @FXML
    public void actionCancel(ActionEvent e) throws IOException {
        stage = (Stage) ((Button)e.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainOfficeDate.setText(LocalDate.now(ZoneId.of("America/New_York")).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        mainOfficeTime.setText(LocalTime.now(ZoneId.of("America/New_York")).format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
        userTime.setText(LocalTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
        userDate.setText(LocalDate.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));


        Locale Spanish = new Locale("es");
        resources = ResourceBundle.getBundle("Languages/login", Spanish);

        if(resources.getLocale().getLanguage().equals("en") || resources.getLocale().getLanguage().equals("de")||
            resources.getLocale().equals(Spanish)){
            loginLabel.setText(resources.getString("login"));
            usernameLabel.setText(resources.getString("username"));
            passwordLabel.setText(resources.getString("password"));
            pinLabel.setText(resources.getString("pin"));
            alertTitle = resources.getString("loginFail");
            alertHeader = resources.getString("alertHeader");
            alertContent = resources.getString("alertContent");
            loginButton.setText(resources.getString("login"));
            cancelButton.setText(resources.getString("cancel"));
            mainOfficeLabel.setText(resources.getString("mainOffice"));
            localTimeLabel.setText(resources.getString("localTime"));
        }

    }
}
