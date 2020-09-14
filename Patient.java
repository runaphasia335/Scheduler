//Carlos Perez

package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Patient {
    private final SimpleIntegerProperty pt_id = new SimpleIntegerProperty();
    private final SimpleStringProperty pt_name = new SimpleStringProperty();
    private final SimpleIntegerProperty address_id = new SimpleIntegerProperty();
    private final SimpleStringProperty INS_PR = new SimpleStringProperty();
    private Address address;
    private Appointment appointment;


    public Patient() {
    }


    public Patient(int id, String name, String insurance, Address address) {
        setPt_id(id);
        setPt_name(name);
        setINS_PR(insurance);
        this.address = address;
    }


    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
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

    public String getPt_name() {
        return pt_name.get();
    }

    public SimpleStringProperty pt_nameProperty() {
        return pt_name;
    }

    public void setPt_name(String pt_name) {
        this.pt_name.set(pt_name);
    }

    public int getAddress_id() {
        return address_id.get();
    }

    public SimpleIntegerProperty address_idProperty() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id.set(address_id);
    }

    public Address getAddress() {
        return address;
    }

    public String getINS_PR() {
        return INS_PR.get();
    }

    public SimpleStringProperty INS_PRProperty() {
        return INS_PR;
    }

    public void setINS_PR(String INS_PR) {
        this.INS_PR.set(INS_PR);
    }


}
