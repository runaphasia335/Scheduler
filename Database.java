//Carlos Perez

package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL ="jdbc:mysql://3.227.166.251/U05Yd1";
    private static final String USER = "U05Yd1";
    private static final String PASSWORD = "53688640577";
    public static Connection conn;

    public static boolean connect(){
        try{
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Connected to database");
        }catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;

        }
        return false;
    }

    public static void disconnect(){
        try{
            conn.close();
            System.out.println("Disconnected from database");
        }catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        }
    }
    public static Connection getConn(){
        return conn;
    }





}
