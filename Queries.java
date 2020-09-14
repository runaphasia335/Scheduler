//Carlos Perez

package Database;
import Model.Address;
import Model.Appointment;
import Model.Counselor;
import Model.Patient;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.io.*;
import java.sql.*;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Queries {


    private static void logText(String username, Boolean success) throws IOException {
        if(success){
            FileWriter writer = new FileWriter("loginSuccess.txt", true);
            writer.write(ZonedDateTime.now(ZoneId.of("America/New_York")) + " / " + " Counselor ID " + username + " " + ZonedDateTime.now(ZoneId.systemDefault()));
            writer.append("\n");
            writer.flush();
            writer.close();

        }else {
            FileWriter writer = new FileWriter("loginFailure.txt", true);
            writer.write(ZonedDateTime.now(ZoneId.of("America/New_York")) + " / " + " Counselor ID " + username + " " + ZonedDateTime.now(ZoneId.systemDefault()));            writer.append("\n");
            writer.flush();
            writer.close();
        }
    }


    //    queries for log in------------------------------------------
    public static Boolean login(String username, String password, int pin) {
        Counselor user = new Counselor();
        try {
            Database.getConn();
            PreparedStatement statement = Database.conn.prepareStatement("SELECT * FROM counselor" +
                    " WHERE c_name=? AND c_password=? AND c_pin=?");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, pin);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                user.setC_id(result.getInt("c_id"));
                user.setC_name((result.getString("c_name")));
                user.setC_password(result.getString("c_password"));
                user.setC_pin(result.getInt("c_pin"));
                logText(username, true);
                return true;

            } else {
                logText(username, false);
                return false;
            }

        } catch (SQLException | IOException e) {
            System.out.println("SQLException: " + e.getMessage() + " " + e.getStackTrace());
        }
        return false;
    }

    //Queries about address ------------------------------------------
    public static int getAddressID() {
        int id = 0;
        try {
            Database.getConn();
            PreparedStatement statement = Database.conn.prepareStatement("SELECT MAX(address_id) AS address_id FROM address");
            ResultSet results = statement.executeQuery();
            results.next();
            id = results.getInt("address_id");
            return id;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage() + "----------getAddressID()");
            return -1;
        }
    }
//Queries about Patient --------------------------- --------------------------- ---------------------------


    //    gets a single patient information
    public static Patient getPatient(Patient patient) {
        try {
            Database.getConn();
            PreparedStatement statement = Database.conn.prepareStatement("SELECT * FROM patient INNER JOIN address" +
                    " ON patient.address_id = address.address_id WHERE pt_id=?");
            statement.setInt(1, patient.getPt_id());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                patient = new Patient(
                        result.getInt("pt_id"),
                        result.getString("pt_name"), result.getString("INS_PR"),
                        new Address(result.getInt("address_id"), result.getString("addressline_1"),
                                result.getString("addressline_2"), result.getString("city"),
                                result.getString("state"),
                                result.getString("postal_code"), result.getString("phone")));
                return patient;
            }
            statement.close();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
        return null;
    }

    //   to autoincrement each patient ID, it finds the highest ID in patient table. Once found, increase ID in AddPatient method

    public static int getPatientID() {
        int id = 0;
        try {
            Database.getConn();
            PreparedStatement statement = Database.conn.prepareStatement("SELECT MAX(pt_id) AS pt_id FROM patient");
            ResultSet results = statement.executeQuery();
            results.next();
            id = results.getInt("pt_id");
            return id;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage() + "----------getPatientID()");
            return -1;
        }
    }

    //gathers all patient information
    public static ObservableList<Patient> getAllPatients() {
        ObservableList<Patient> allPatients = FXCollections.observableArrayList();
        try {
            Database.getConn();
            PreparedStatement statement = Database.conn.prepareStatement("SELECT patient.pt_id AS id, patient.pt_name AS 'name'," +
                    "address.addressline_1 AS address, address.addressline_2 AS address2, address.city, address.state," +
                    " address.postal_code AS zip, address.phone, patient.INS_PR AS insurance\n" +
                    "FROM patient INNER JOIN address ON patient.address_id = address.address_id ");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Patient patient = new Patient(
                        result.getInt("id"),
                        result.getString("name"), result.getString("insurance"),
                        new Address(-1, String.valueOf(new SimpleStringProperty(result.getString("address"))),
                                String.valueOf(new SimpleStringProperty(result.getString("address2"))),
                                String.valueOf(new SimpleStringProperty(result.getString("city"))),
                                String.valueOf(new SimpleStringProperty(result.getString("state"))),
                                String.valueOf(new SimpleStringProperty(result.getString("zip"))),
                                String.valueOf(new SimpleStringProperty(result.getString("phone")))));
                allPatients.addAll(patient);
            }
            statement.close();
            return allPatients;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage() + " " + e.getStackTrace() + "-------getAllPatients()");
            return null;
        }

    }

    //add a patient. To increase patient ID, getPatientID method
    public static boolean addPatient(String name, String insurance, Address address) {
        Counselor user = new Counselor();
        try {
            Database.getConn();
            PreparedStatement statement = Database.conn.prepareStatement("INSERT INTO address " +
                    "(address_id,addressline_1, addressline_2, city, state, postal_code, phone,created_at, created_by, updated_at, updated_by)" +
                    " VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            statement.setInt(1, getAddressID() + 1);
            statement.setString(2, address.getAddressline_1());
            statement.setString(3, address.getAddressline_2());
            statement.setString(4, address.getCity());
            statement.setString(5, address.getState());
            statement.setString(6, address.getPostal_code());
            statement.setString(7, address.getPhone());
            statement.setString(8, Counselor.getCreated_at());
            statement.setInt(9, user.getC_id());
            statement.setString(10, Counselor.getUpdated_at());
            statement.setInt(11, user.getC_id());
            statement.executeUpdate();


            statement = Database.conn.prepareStatement(("INSERT INTO patient " +
                    "(pt_id, pt_name, address_id, INS_PR,created_at,created_by,updated_at,updated_by, address_address_id)" +
                    " VALUES(?,?,?,?,?,?,?,?,?)"));
            statement.setInt(1, getPatientID() + 1);
            statement.setString(2, name);
            statement.setInt(3, getAddressID());
            statement.setString(4, insurance);
            statement.setString(5, Counselor.getCreated_at());
            statement.setInt(6, user.getC_id());
            statement.setString(7, Counselor.getCreated_at());
            statement.setInt(8, user.getC_id());
            statement.setInt(9, getAddressID());
            statement.executeUpdate();

            statement.close();

            System.out.println("Patient added");


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("bad here-----> Queries.AddPatient()");
            return false;
        }
        return false;

    }

    //    edits patient information
    public static boolean editPatient(Patient patient) {
        Counselor user = new Counselor();
        try {
            Database.getConn();
            PreparedStatement statement = Database.conn.prepareStatement("UPDATE address " +
                    "SET addressline_1=?,addressline_2=?,city=?,state=?, postal_code=?,phone=?, updated_at=?, updated_by=?\n" +
                    "WHERE address_id=?");
            statement.setString(1, patient.getAddress().getAddressline_1());
            statement.setString(2, patient.getAddress().getAddressline_2());
            statement.setString(3, patient.getAddress().getCity());
            statement.setString(4, patient.getAddress().getState());
            statement.setString(5, patient.getAddress().getPostal_code());
            statement.setString(6, patient.getAddress().getPhone());
            statement.setString(7, Counselor.getUpdated_at());
            statement.setInt(8, user.getC_id());
            statement.setInt(9, patient.getAddress().getAddress_id());
            statement.executeUpdate();


            statement = Database.conn.prepareStatement("UPDATE patient " +
                    "SET pt_name=?,INS_PR=?,updated_at=?,updated_by=? WHERE pt_id=?");
            statement.setString(1, patient.getPt_name());
            statement.setString(2, patient.getINS_PR());
            statement.setString(3, Counselor.getUpdated_at());
            statement.setInt(4, user.getC_id());
            statement.setInt(5, patient.getPt_id());
            statement.executeUpdate();

            statement.close();

            System.out.println("Patient updated");

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("bad here-----> Queries.EditPatient()");
            return false;
        }
        return false;

    }

    public static boolean deletePatient(Patient patient) {
        try {
            Database.getConn();
            PreparedStatement statement = Database.conn.prepareStatement("DELETE FROM patient " +
                    "WHERE pt_id=?");
            statement.setInt(1, patient.getPt_id());
            statement.executeUpdate();
            statement.close();
            System.out.println("patient deleted");

            statement = Database.conn.prepareStatement("DELETE FROM address WHERE address_id=?");
            statement.setInt(1, patient.getAddress().getAddress_id());
            statement.executeUpdate();
            statement.close();
            System.out.println("address deleted");
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage()+"-------deletePatient()");
            return false;
        }
    }


    //    in order to increment each appointment ID appropriately, it uses the same method as getPatientI
    public static int getAptID() {
        int id = 0;
        try {
            Database.getConn();
            PreparedStatement statement = Database.conn.prepareStatement("SELECT MAX(apt_id) AS aptID FROM appointment");
            ResultSet results = statement.executeQuery();
            results.next();
            id = results.getInt("aptID");
            return id;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage() + "----------getAptID()");
            return -1;
        }
    }


    //    When adding an appointment, to24HourConverson() method is used to convert the date and time to a TimeStamp. Making it
//    appropriate to store in database.
    public static boolean addAppointment(int ptId, String c_Id, int description, String time, LocalDate date, String notes) throws ParseException {
        String stamp = to24HourConversion(time, date).toString();
        Counselor user = new Counselor();
        try {
            Database.getConn();


            PreparedStatement statement = Database.conn.prepareStatement("INSERT INTO appointment(apt_id,pt_id,cr_id,apt_type_id,notes," +
                    "start_datetime, created_at, created_by, patient_pt_id,counselor_c_id, APTtype_APTtype_id)\n" +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            statement.setInt(1, getAptID() + 1);
            statement.setInt(2, ptId);
            statement.setInt(3, Integer.parseInt(c_Id));
            statement.setInt(4, description);
            statement.setString(5, notes);
            statement.setString(6, stamp);
            statement.setString(7, LocalDateTime.now().toString());
            statement.setInt(8, user.getC_id());
            statement.setInt(9, ptId);
            statement.setInt(10, user.getC_id());
            statement.setInt(11, description);
            statement.executeUpdate();
            System.out.println("appointment added");
            statement.close();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage() + " " + e.getStackTrace() + "-----------AddAppointment");
            return false;
        }
        return false;

    }

    // same method used as addAppointment
    public static boolean editAppointment(int aptId, int description, String time, String notes, LocalDate date) throws ParseException {
        String stamp = to24HourConversion(time, date).toString();
        Counselor user = new Counselor();
        try {
            Database.getConn();

            PreparedStatement statement = Database.conn.prepareStatement("UPDATE appointment " +
                    "SET apt_type_id=?,notes=?,start_datetime=?, updated_at=?, updated_by=?,APTtype_APTtype_id=?\n" +
                    "   WHERE apt_id=? ");
            statement.setInt(1, description);
            statement.setString(2, notes);
            statement.setString(3, stamp);
            statement.setString(4, LocalDateTime.now().toString());
            statement.setInt(5, user.getC_id());
            statement.setInt(6, description);
            statement.setInt(7, aptId);
            statement.executeUpdate();
            System.out.println("appointment updated");
            statement.close();
            return true;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage() + " " + e.getStackTrace() + "------EditAppointment");
            return false;
        }
    }

    public static void deleteAppointment(Appointment appointment) {
        try {
            Database.getConn();
            PreparedStatement statement = Database.conn.prepareStatement("DELETE FROM appointment\n" +
                    "WHERE apt_id=?");
            statement.setInt(1, appointment.getApt_id());
            statement.executeUpdate();
            statement.close();
            System.out.println("appointment deleted");

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage() + " " + e.getStackTrace() + "--------deleteAppointment");
        }
    }


    //    gathers all future daily appointments.
    public static ObservableList<Appointment> getDayPatientAppointment() {
        ObservableList<Appointment> itemsForPatient = FXCollections.observableArrayList();
        try {

            LocalDate day = LocalDate.now();


            Database.getConn();
            PreparedStatement statement = Database.conn.prepareStatement("SELECT" +
                    " appointment.apt_id AS 'aptId', patient.pt_name AS 'name', patient.pt_id AS 'ptId'," +
                    " appointment.start_datetime AS 'start', appointment.notes, APTtype.description AS 'description', patient.INS_PR as 'insurance'," +
                    " address.phone FROM appointment INNER JOIN APTtype ON" +
                    " appointment.apt_type_id = APTtype.APTtype_id INNER JOIN patient ON appointment.pt_id = patient.pt_id " +
                    "INNER JOIN address ON patient.address_id = address.address_id WHERE DATE(appointment.start_datetime)=?");
            statement.setString(1, day.toString());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String ptName = result.getString("name");
                int ptId = result.getInt("ptId");
                String start = result.getString("start");
                String notes = result.getString("notes");
                int aptId = result.getInt("aptId");
                String description = result.getString("description");
                String insurance = result.getString("insurance");
                String phone = result.getString("phone");

                LocalDate date = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:mm"));
                String startTimeToZone=  toUserTimeZone(start).format(DateTimeFormatter.ofPattern("hh:mma"));
                String endTimeToZone = toUserTimeZone(start).plusHours(1).format(DateTimeFormatter.ofPattern("hh:mma"));

                Appointment appointment = new Appointment(aptId, ptId, ptName, null, null,
                        phone,new Counselor().getC_id(),description,notes,startTimeToZone,endTimeToZone,date);

                itemsForPatient.addAll(appointment);

            }
            return itemsForPatient;

        } catch (SQLException e) {
            System.out.println("SQLException :" + e.getMessage() + "-------getDayappointment");
            return null;
        }
    }


    //    gets weekly appointments for weeklyTableView.
    public static ObservableList<Appointment> getWeeklyPatientAppointment() {
        ObservableList<Appointment> weeklyItems = FXCollections.observableArrayList();
        try {
            String ptName, phone, city, state, description,notes, start;
            int counselor, ptId, aptId;

//            finds appointments from current time to 21:00, or 9pm, one week later
            LocalDate week = LocalDate.now();
            LocalDate week2 = LocalDate.now().plusWeeks(1);
//------------------------------------------------------------------------------------------------------------------
            Database.getConn();
            PreparedStatement statement = Database.conn.prepareStatement("SELECT patient.pt_id, patient.pt_name," +
                    " address.phone, appointment.cr_id AS 'counselor', APTtype.description, appointment.notes," +
                    " appointment.start_datetime AS 'start', address.city, address.state, appointment.apt_id AS 'aptId' FROM APTtype" +
                    " INNER JOIN appointment ON appointment.apt_type_id = APTtype.APTtype_id " +
                    " INNER JOIN patient ON appointment.pt_id = patient.pt_id INNER JOIN address ON address.address_id = patient.address_id" +
                    " WHERE appointment.start_datetime BETWEEN ? AND ? ");
            statement.setString(1, week.toString());
            statement.setString(2, week2.toString());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                ptId = result.getInt("pt_id");
                ptName = result.getString("pt_name");
                phone = result.getString("phone");
                counselor = result.getInt("counselor");
                description = result.getString("description");
                notes = result.getString("notes");
                start = result.getString("start");
                city = result.getString("city");
                state = result.getString("state");
                aptId = result.getInt("aptId");

//   ------------------------  converts timestamp and converts it to AmPm in the time zone of the user. --------------------------
                LocalDate date = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:mm"));
                String startTimeToZone=  toUserTimeZone(start).format(DateTimeFormatter.ofPattern("hh:mma"));
                String endTimeToZone = toUserTimeZone(start).plusHours(1).format(DateTimeFormatter.ofPattern("hh:mma"));
//--------------------------------------------------------------------------------------------------------------------------------
                Appointment appointment = new Appointment(aptId, ptId, ptName, city, state, phone, counselor, description, notes,
                        startTimeToZone, endTimeToZone, date);

                weeklyItems.addAll(appointment);
            }
            return weeklyItems;


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage() + "---------WeeklAppointment");
        }
        return null;
    }

    public static ObservableList<Appointment> getBiWeeklyPatientAppointment() {
        ObservableList<Appointment> biWeeklyItems = FXCollections.observableArrayList();
        try {
            String ptName, phone, city, state, description,notes, start;
            int counselor, ptId, aptId;
//            finds appointments from the start of the day to 21:00,or 9pm, 2 weeks later
            LocalDateTime week = LocalDateTime.now().toLocalDate().atStartOfDay().plusHours(9);
            LocalDateTime week2 = LocalDateTime.now().toLocalDate().plusWeeks(2).atStartOfDay().plusHours(21);
//            ------------------------------------------------------------------------------------------------------------------
            System.out.println(week);
            System.out.println(week2);

            Database.getConn();
            PreparedStatement statement = Database.conn.prepareStatement("SELECT patient.pt_id, patient.pt_name," +
                    " address.phone, appointment.cr_id AS 'counselor', APTtype.description, appointment.notes, appointment.start_datetime AS 'start'," +
                    " address.city, address.state, appointment.apt_id AS 'aptId' FROM APTtype " +
                    "INNER JOIN appointment ON appointment.apt_type_id = APTtype.APTtype_id " +
                    "INNER JOIN patient ON appointment.pt_id = patient.pt_id INNER JOIN address ON address.address_id = patient.address_id" +
                    " WHERE appointment.start_datetime BETWEEN ? AND ? ");
            statement.setString(1, week.toString());
            statement.setString(2, week2.toString());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                ptId = result.getInt("pt_id");
                ptName = result.getString("pt_name");
                phone = result.getString("phone");
                counselor = result.getInt("counselor");
                description = result.getString("description");
                notes = result.getString("notes");
                start = result.getString("start");
                city = result.getString("city");
                state = result.getString("state");
                aptId = result.getInt("aptId");

//   ------------------------  get timestamp timestamp and converts it to AmPm in the time zone of the user. --------------------------
                LocalDate date = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:mm"));
                String startTimeToZone=  toUserTimeZone(start).format(DateTimeFormatter.ofPattern("hh:mma"));
                String endTimeToZone = toUserTimeZone(start).plusHours(1).format(DateTimeFormatter.ofPattern("hh:mma"));
//--------------------------------------------------------------------------------------------------------------------------------

                Appointment appointment = new Appointment(aptId, ptId, ptName, city, state, phone, counselor, description, notes,
                        startTimeToZone, endTimeToZone, date);

                biWeeklyItems.addAll(appointment);
            }
            return biWeeklyItems;


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage() + "---------BiWeeklyAppointment");
        }
        return null;
    }



    public static ObservableList<Appointment> getMonthlyPatientAppointment() {
        ObservableList<Appointment> monthlyItems = FXCollections.observableArrayList();
        try {
            String ptName, phone, city, state, description,notes, start;
            int counselor, ptId, aptId;
            LocalDateTime month = LocalDateTime.now();
//            up to the 21:00, or 9pm, one month later
            LocalDateTime month2 = LocalDateTime.now().toLocalDate().plusMonths(1).atStartOfDay().plusHours(21);


            Database.getConn();
            PreparedStatement statement = Database.conn.prepareStatement("SELECT patient.pt_id, patient.pt_name," +
                    " address.phone, appointment.cr_id AS 'counselor', APTtype.description, appointment.notes, appointment.start_datetime AS 'start'," +
                    " address.city, address.state, appointment.apt_id AS 'aptId' FROM APTtype" +
                    " INNER JOIN appointment ON appointment.apt_type_id = APTtype.APTtype_id " +
                    " INNER JOIN patient ON appointment.pt_id = patient.pt_id INNER JOIN address ON address.address_id = patient.address_id" +
                    " WHERE appointment.start_datetime BETWEEN ? AND ? ");
            statement.setString(1, month.toString());
            statement.setString(2, month2.toString());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                ptId = result.getInt("pt_id");
                ptName = result.getString("pt_name");
                phone = result.getString("phone");
                counselor = result.getInt("counselor");
                description = result.getString("description");
                notes = result.getString("notes");
                start = result.getString("start");
                city = result.getString("city");
                state = result.getString("state");
                aptId = result.getInt("aptId");


//   ------------------------  converts timestamp and converts it to AmPm in the time zone of the user. --------------------------
                LocalDate date = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:mm"));
                String startTimeToZone=  toUserTimeZone(start).format(DateTimeFormatter.ofPattern("hh:mma"));
                String endTimeToZone = toUserTimeZone(start).plusHours(1).format(DateTimeFormatter.ofPattern("hh:mma"));
//-----------------------------------------------------------------------------------------------------------------------
                Appointment appointment = new Appointment(aptId, ptId, ptName, city, state, phone, counselor, description, notes,
                        startTimeToZone, endTimeToZone, date);

                monthlyItems.addAll(appointment);
            }
            return monthlyItems;


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage() + "---------MonthlyAppointment");
        }
        return null;
    }


    //    finds all appointments that match the current one being made to ensure the user that the appointment being constructed
//    overlaps another.
    public static Boolean overlappingAppointment(int aptId, String time, LocalDate date) throws ParseException {
        String stamp = to24HourConversion(time, date).toString();
        try {
            Database.getConn();
            PreparedStatement statement = Database.conn.prepareStatement("SELECT * FROM appointment WHERE " +
                    "appointment.start_datetime=?");
            statement.setString(1, stamp);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
               String datetime = result.getString("start_datetime");
              int apt_id = result.getInt("apt_id");


                if(apt_id ==aptId && stamp.contains(datetime)) {
                    return false;
                }else{
                    return true;
                }

            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage() + "  " + e.getStackTrace() + "---------OverlappingAppointments");
        }
        return false;
    }


    // finds all appointments that fall in a 4 hour gap. Method is called in DashboardController------------------------
    public static ObservableList<Appointment> fourHourWarning() {
        ObservableList<Appointment> fourHourList = FXCollections.observableArrayList();
        try {

            String fourHourBegin = LocalDateTime.now().toString();
            String fourHourEnd = LocalDateTime.now().plusHours(4).toString();

            String ptName, phone, city, state, description,notes, start;
            int counselor, ptId, aptId;

            Database.getConn();
            PreparedStatement statement = Database.conn.prepareStatement("SELECT patient.pt_id, patient.pt_name," +
                    " address.phone, appointment.cr_id AS 'counselor', APTtype.description, appointment.notes, appointment.start_datetime AS 'start'," +
                    " address.city, address.state, appointment.apt_id AS 'aptId' FROM APTtype" +
                    " INNER JOIN appointment ON appointment.apt_type_id = APTtype.APTtype_id " +
                    " INNER JOIN patient ON appointment.pt_id = patient.pt_id INNER JOIN address ON address.address_id = patient.address_id" +
                    " WHERE appointment.start_datetime BETWEEN ? AND ? ");
            statement.setString(1, fourHourBegin);
            statement.setString(2, fourHourEnd);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                ptId = result.getInt("pt_id");
                ptName = result.getString("pt_name");
                phone = result.getString("phone");
                counselor = result.getInt("counselor");
                description = result.getString("description");
                notes = result.getString("notes");
                start = result.getString("start");
                city = result.getString("city");
                state = result.getString("state");
                aptId = result.getInt("aptId");


                //   ------------------------  converts timestamp and converts it to AmPm in the time zone of the user. --------------------------
                LocalDate date = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:mm"));
                String startTimeToZone=  toUserTimeZone(start).format(DateTimeFormatter.ofPattern("hh:mma"));
                String endTimeToZone = toUserTimeZone(start).plusHours(1).format(DateTimeFormatter.ofPattern("hh:mma"));
//------------------------------------------------------------------------------------------------------------------------------
                Appointment appointment = new Appointment(aptId, ptId, ptName, city, state, phone, counselor, description, notes,
                        startTimeToZone, endTimeToZone, date);
                fourHourList.addAll(appointment);


            }
            System.out.println("found all");
            return fourHourList;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage() + "  " + e.getStackTrace() + "---------fourHourAppointments");
        }
        return null;
    }





    //    method used to convert AmPm string to TimeStamp to store in database in Eastern timezone. When adding appointment,
    //    checks for outside business hours and for past hours of the day
    private static Timestamp to24HourConversion(String time, LocalDate date) throws ParseException {
        Timestamp timestamp = null;
        ZoneId easternZone = ZoneId.of("America/New_York");


//        parse 12 format to 24 hour ----------------------------------------------
        SimpleDateFormat from12Hour = new SimpleDateFormat("hh:mma");

        Date timeConvert = from12Hour.parse(time);

        SimpleDateFormat to24Hour = new SimpleDateFormat("HH:mm");

        LocalTime lt = LocalTime.parse(to24Hour.format(timeConvert));
//-----------------------------------------------------------------------

//        Combine LocalTime and LocalDate to LocalDateTime to be converted to ZoneDateTime. ZoneDateTime will allow for the
//        appointments times to be stored in Eastern time. ----------------------------------------------------------
        LocalDateTime ldt = LocalDateTime.of(LocalDate.parse(date.toString()), lt);

        ZonedDateTime localZoneId = ldt.atZone(ZoneId.systemDefault());

        ZonedDateTime toEasternTime = localZoneId.withZoneSameInstant(easternZone);


        if(toEasternTime.getHour() < 9 ||
                toEasternTime.getHour() > 21){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Outside of business hours");
            alert.setContentText("Please choose another time");
            alert.show();
        }else if(toEasternTime.toLocalDateTime().isBefore(LocalDateTime.now(easternZone)) ||
          toEasternTime.toLocalDateTime().isEqual(LocalDateTime.now(easternZone))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Hour chosen is in the past");
            alert.setContentText("Please choose another time");
            alert.show();
        }
        else{
            timestamp = Timestamp.valueOf(LocalDateTime.from(toEasternTime));
        }
        return timestamp;
    }

//    converts timezone to be displayed on tableviews based on the users timezone

    private static ZonedDateTime toUserTimeZone(String start){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:mm");

        LocalDate date = LocalDate.parse(start, formatter);
        LocalTime time = LocalTime.parse(start, formatter);

        LocalDateTime dateTime = LocalDateTime.of(date, time);
        ZoneId easternZoneId = ZoneId.of("America/New_York");
        ZoneId zoneId = ZoneId.systemDefault();

        return dateTime.atZone(easternZoneId).withZoneSameInstant(zoneId);
    }




    //    gathers appointments sort by counselor. Appends the results into a string and its each string to the observableArray
    public static ObservableList<String> getAllAppointmentsByCounselor(){

        ObservableList<String> allCounselor = FXCollections.observableArrayList();
        int year = LocalDate.now().getYear();
        int num = 1;
        try{
            Database.getConn();
            PreparedStatement statement = Database.conn.prepareStatement("SELECT patient.pt_name, " +
                    " appointment.cr_id AS 'counselor', APTtype.description, appointment.start_datetime AS 'start', " +
                    "address.city, address.state, appointment.apt_id AS 'aptId' FROM APTtype" +
                    " INNER JOIN appointment ON appointment.apt_type_id = APTtype.APTtype_id "+
                    " INNER JOIN patient ON appointment.pt_id = patient.pt_id " +
                    " INNER JOIN address ON address.address_id = patient.address_id WHERE YEAR(appointment.start_datetime)=? " +
                    " ORDER BY appointment.cr_id");
            statement.setInt(1,year);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int counselor = result.getInt("counselor");
                String ptName = result.getString("pt_name");
                String description = result.getString("description");
                String start = result.getString("start");
                String city = result.getString("city");
                String state = result.getString("state");




                String text= num++ + ")COUNSELOR: " + counselor + "-- PATIENT NAME: " + ptName + "-- DESCRIPTION: " +
                        description +  "-- DATE/TIME: " + toUserTimeZone(start).toLocalDateTime()
                        + "-- CITY: " + city + "-- STATE: " + state + "\n\n";

              allCounselor.add(text);

            }
            statement.close();
            return allCounselor;

            } catch (SQLException e) {
            System.out.println("SQLException: "+ e.getMessage());
        }
        return null;
    }


    //    gathers appointments sort by state. Appends the results into a string and its each string to the observableArray
    public static ObservableList<String> getAllAppointmentsByState(){

        ObservableList<String> allByState = FXCollections.observableArrayList();
        int year = LocalDate.now().getYear();
        int num = 1;
        System.out.println(LocalDate.now().getYear() + "------getAllAppointmentState");
        try{
            Database.getConn();
            PreparedStatement statement = Database.conn.prepareStatement("SELECT patient.pt_name, " +
                    " appointment.cr_id AS 'counselor', APTtype.description, appointment.start_datetime AS 'start', " +
                    "address.city, address.state, appointment.apt_id AS 'aptId' FROM APTtype" +
                    " INNER JOIN appointment ON appointment.apt_type_id = APTtype.APTtype_id "+
                    " INNER JOIN patient ON appointment.pt_id = patient.pt_id INNER JOIN address" +
                    " ON address.address_id = patient.address_id WHERE YEAR(appointment.start_datetime)=? ORDER BY address.state");
            statement.setInt(1,year);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int counselor = result.getInt("counselor");
                String ptName = result.getString("pt_name");
                String description = result.getString("description");
                String start = result.getString("start");
                String city = result.getString("city");
                String state = result.getString("state");

                    String text2 = num++ + ")STATE: " + state + "--COUNSELOR: " + counselor + "--DESCRIPTION: " + description +   "--PATIENT NAME: " + ptName + "--DATE/TIME: " +
                            toUserTimeZone(start).toLocalDateTime() + "--CITY: " + city + "--STATE: " + state + "\n\n";
                    allByState.add(text2);

            }
            statement.close();
            return allByState;

        } catch (SQLException e) {
            System.out.println("SQLException: "+ e.getMessage());
        }
        return null;
    }


//    gathers appointments sort by APTtype. Appends the results into a string and its each string to the observableArray
    public static ObservableList<String> getAllAppointmentsByType(){

        ObservableList<String> allByState = FXCollections.observableArrayList();
        int year = LocalDate.now().getYear();
        int num = 1;
        try{
            Database.getConn();
            PreparedStatement statement = Database.conn.prepareStatement("SELECT patient.pt_name, " +
                    " appointment.cr_id AS 'counselor', APTtype.description, appointment.start_datetime AS 'start', " +
                    "address.city, address.state, appointment.apt_id AS 'aptId' FROM APTtype" +
                    " INNER JOIN appointment ON appointment.apt_type_id = APTtype.APTtype_id "+
                    " INNER JOIN patient ON appointment.pt_id = patient.pt_id INNER JOIN address" +
                    " ON address.address_id = patient.address_id WHERE YEAR(appointment.start_datetime)=? ORDER BY APTtype.description");
            statement.setInt(1,year);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int counselor = result.getInt("counselor");
                String ptName = result.getString("pt_name");
                String description = result.getString("description");
                String start = result.getString("start");
                String city = result.getString("city");
                String state = result.getString("state");

                String text3 = num++ + ") DESCRIPTION: " + description + "--COUNSELOR: " + counselor + "--PATIENT NAME: " + ptName +
                        "--DATE/TIME: " + toUserTimeZone(start).toLocalDateTime() + "--CITY: " + city + "--STATE: " + state + "\n\n";
                allByState.add(text3);

            }
            statement.close();
            return allByState;

        } catch (SQLException e) {
            System.out.println("SQLException: "+ e.getMessage());
        }
        return null;
    }
}

