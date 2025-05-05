import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
public class DBConnection {
    static Connection con=null;
    public static Connection getConnection(){
    try{
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms","root","Abhay@9899");
    }catch(Exception e){
         JOptionPane.showMessageDialog(null,e);
    }
        return con;
 
    }
}
