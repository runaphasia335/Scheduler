//Carlos Perez

package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;



public class Appointment {
    private final SimpleIntegerProperty apt_id = new SimpleIntegerProperty();
    private final SimpleIntegerProperty pt_id = new SimpleIntegerProperty();
    private final SimpleIntegerProperty cr_id = new SimpleIntegerProperty();
    private final SimpleIntegerProperty apt_type_id = new SimpleIntegerProperty();
    private final SimpleStringProperty notes = new SimpleStringProperty();
    private final SimpleStringProperty ptName = new SimpleStringProperty();
    private final SimpleStringProperty ptCity = new SimpleStringProperty();
    private final SimpleStringProperty ptState = new SimpleStringProperty();
    private final SimpleStringProperty description = new SimpleStringProperty();
    private final SimpleStringProperty start_datetime = new SimpleStringProperty();
    private final SimpleStringProperty endTime = new SimpleStringProperty();
    private final SimpleStringProperty type = new SimpleStringProperty();
    private final SimpleStringProperty date = new SimpleStringProperty();
    private final SimpleStringProperty phone = new SimpleStringProperty();
    private final SimpleStringProperty insurance = new SimpleStringProperty();
    private final SimpleIntegerProperty counselor_id = new SimpleIntegerProperty();

    public Appointment() {
    }

    public Appointment(int counselor, String description, String insurance, String startTimeToZone,
                       String endTimeToZone, LocalDate date) {
        setCounselor_id(counselor);
        setDescription(description);
        setInsurance(insurance);
        setStart_datetime(startTimeToZone);
        setEndTime(endTimeToZone);
        setDate(date.toString());
    }

    public Appointment(int aptId,int ptId, String ptName, String city, String state, String phone, int counselor,
                       String description, String notes, String startTimeToZone, String endTimeToZone, LocalDate date) {
        setApt_id(aptId);
        setPt_id(ptId);
        setPtName(ptName);
        setPtCity(city);
        setPtState(state);
        setPhone(phone);
        setCounselor_id(counselor);
        setDescription(description);
        setNotes(notes);
        setStart_datetime(startTimeToZone);
        setEndTime(endTimeToZone);
        setDate(date.toString());

    }

    public String getPtName() {
        return ptName.get();
    }

    public SimpleStringProperty ptNameProperty() {
        return ptName;
    }

    public void setPtName(String ptName) {
        this.ptName.set(ptName);
    }

    public String getPtCity() {
        return ptCity.get();
    }

    public SimpleStringProperty ptCityProperty() {
        return ptCity;
    }

    public void setPtCity(String ptCity) {
        this.ptCity.set(ptCity);
    }

    public String getPtState() {
        return ptState.get();
    }

    public SimpleStringProperty ptStateProperty() {
        return ptState;
    }

    public void setPtState(String ptState) {
        this.ptState.set(ptState);
    }

    public String getEndTime() {
        return endTime.get();
    }

    public SimpleStringProperty endTimeProperty() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime.set(endTime);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getInsurance() {
        return insurance.get();
    }

    public SimpleStringProperty insuranceProperty() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance.set(insurance);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public int getApt_type_id() {
        return apt_type_id.get();
    }

    public SimpleIntegerProperty apt_type_idProperty() {
        return apt_type_id;
    }

    public void setApt_type_id(int apt_type_id) {
        this.apt_type_id.set(apt_type_id);
    }

    public int getApt_id() {
        return apt_id.get();
    }

    public SimpleIntegerProperty apt_idProperty() {
        return apt_id;
    }

    public void setApt_id(int apt_id) {
        this.apt_id.set(apt_id);
    }

    public int getPt_id() {
        return pt_id.get();
    }

    public SimpleIntegerProperty pt_idProperty() {
        return pt_id;
    }

    public void setPt_id(int pt_id) {
        this.pt_id.set(pt_id);
    }

    public int getCr_id() {
        return cr_id.get();
    }

    public SimpleIntegerProperty cr_idProperty() {
        return cr_id;
    }

    public void setCr_id(int cr_id) {
        this.cr_id.set(cr_id);
    }

    public String getNotes() {
        return notes.get();
    }

    public SimpleStringProperty notesProperty() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    public String getStart_datetime() {
        return start_datetime.get();
    }

    public SimpleStringProperty start_datetimeProperty() {
        return start_datetime;
    }

    public void setStart_datetime(String start_datetime) {
        this.start_datetime.set(start_datetime);
    }



    public int getCounselor_id() {
        return counselor_id.get();
    }

    public SimpleIntegerProperty counselor_idProperty() {
        return counselor_id;
    }

    public void setCounselor_id(int counserlor_id) {
        this.counselor_id.set(counserlor_id);
    }


}
