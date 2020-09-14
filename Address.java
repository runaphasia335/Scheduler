
//Carlos Perez
package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Address {

    private final SimpleIntegerProperty address_id = new SimpleIntegerProperty();
    private final SimpleStringProperty addressline_1 = new SimpleStringProperty();
    private final SimpleStringProperty addressline_2 = new SimpleStringProperty();;
    private final SimpleStringProperty city = new SimpleStringProperty();;
    private final SimpleStringProperty state = new SimpleStringProperty();;
    private final SimpleStringProperty postal_code = new SimpleStringProperty();;
    private final SimpleStringProperty phone = new SimpleStringProperty();
    private final SimpleStringProperty created_at = new SimpleStringProperty();;
    private final SimpleStringProperty updated_at = new SimpleStringProperty();;


    public Address(int address_id, String addressline_1, String addressline_2, String city, String state, String postal_code, String phone) {
        setAddress_id(address_id);
        setAddressline_1(addressline_1);
        setAddressline_2(addressline_2);
        setCity(city);
        setState(state);
        setPostal_code(postal_code);
        setPhone(phone);
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public int getAddress_id() {
        return address_id.get();
    }

    public void setAddress_id(int address_id) {
        this.address_id.set(address_id);
    }

    public String getAddressline_1() {
        return addressline_1.get();
    }

    public void setAddressline_1(String addressline_1) {
        this.addressline_1.set(addressline_1);
    }

    public String getAddressline_2() {
        return addressline_2.get();
    }

    public void setAddressline_2(String addressline_2) {
        this.addressline_2.set(addressline_2);
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getState() {
        return state.get();
    }

    public void setState(String state) {
        this.state.set(state);
    }

    public String getPostal_code() {
        return postal_code.get();
    }

    public void setPostal_code(String postal_code) {
        this.postal_code.set(postal_code);
    }


    public void setCreated_at(String created_at) {
        this.created_at.set(created_at);
    }



    public void setUpdated_at(String updated_at) {
        this.updated_at.set(updated_at);
    }

}
