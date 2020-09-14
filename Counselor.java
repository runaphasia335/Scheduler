//Carlos Perez

package Model;

import java.time.LocalDateTime;

public class Counselor {
    private static int c_id;
    private static String c_name;
    private static String c_password;
    private int c_pin;
    private static String created_at;
    private static String created_by;
    private static String updated_at;
    private static String update_by;

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public void setC_password(String c_password) {
        this.c_password = c_password;
    }

    public void setC_pin(int c_pin) {
        this.c_pin = c_pin;
    }

    public static String getCreated_at() {
        return LocalDateTime.now().toString();
    }

    public void setCreated_at(String created_at) {
        this.created_at = LocalDateTime.now().toString();
    }

    public static String getUpdated_at() {
        return LocalDateTime.now().toString();
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }


    public void User() {

    }

}
