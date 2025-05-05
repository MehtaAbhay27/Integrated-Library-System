import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;
import java.lang.String;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
public class SUB_ADMIN_HOME_PAGE extends javax.swing.JFrame {

    Color mouseEnterColor = new Color(0, 0, 0);
    Color mouseExitColor = new Color(51, 51, 51);
    Color mouseEnterColor1 = new Color(102, 102, 255);
    DefaultTableModel model;
    String StudentName, Course, Branch;
    String BookName, Author, BookID, StudentID;
    int Quantity;
    String crudImageAbsolutePath = " ";
    private ImageIcon format = null;

    public SUB_ADMIN_HOME_PAGE() {
        initComponents();
        SetIcon();
        setDefaulterDetailsInTable();
        setIssueBookDetailsTable();
        setViewAllRecordsTable();
        setStudentDetailsTable();
        setBookDetailsTable();
        showPieChart();
        setDataToBooks();
        setDataToStudent();
        setDataToIssueBook();
        setDataToTotalBooks();
        setBookDetailsTable1();
        Auto_BookID();  //B-01 book id 
        Auto_StudentID();//S-01 student id
        Issued_ID();//I-01  IssueId
        
    }
private void SetIcon(){
setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("books.png")));
}
    public void showPieChart() {
        //create dataset
        DefaultPieDataset barDataset = new DefaultPieDataset();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms", "root", "Abhay@9899");
            String sql = "SELECT BOOK_NAME, sum(QUANTITY) as QUANTITY FROM book_details group by BOOK_NAME";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                barDataset.setValue(rs.getString("BOOK_NAME"), new Double(rs.getDouble("QUANTITY")));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error! in Pie Chart ");
        }
//create chart
        JFreeChart piechart = ChartFactory.createPieChart("Book Details", barDataset, true, true, false);//explain

        PiePlot piePlot = (PiePlot) piechart.getPlot();
        piePlot.setBackgroundPaint(Color.white);

        //create chartPanel to display chart(graph)/2
        ChartPanel barChartPanel = new ChartPanel(piechart);
        panelBarChart.removeAll();
        panelBarChart.add(barChartPanel, BorderLayout.CENTER);
        panelBarChart.validate();
    }

    public void setDataToBooks() { //  types of Books in Home Page

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms", "root", "Abhay@9899");
            String sql = "SELECT count(Book_name) FROM book_details";
            PreparedStatement prepare = con.prepareStatement(sql);
            ResultSet rs = prepare.executeQuery();
            int countEnrolled = 0;
            if (rs.next()) {
                countEnrolled = rs.getInt("count(Book_name)");
            }
            book.setText(String.valueOf(countEnrolled));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error! in Book Count");
        }
    }

    public void setDataToTotalBooks() {//  total  Books in Home Page

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms", "root", "Abhay@9899");
            String sql = "SELECT SUM(QUANTITY) FROM book_details";
            PreparedStatement prepare = con.prepareStatement(sql);
            ResultSet rs = prepare.executeQuery();
            int countEnrolled = 0;
            if (rs.next()) {
                countEnrolled = rs.getInt("SUM(QUANTITY)");
            }
            Totalbook.setText(String.valueOf(countEnrolled));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error! in Total Book Count");
        }
    }

    public void setDataToStudent() {//  total Students in Home Page

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms", "root", "Abhay@9899");
            String sql = "SELECT COUNT(STUDENT_ID) FROM student_details";
            PreparedStatement prepare = con.prepareStatement(sql);
            ResultSet rs = prepare.executeQuery();
            int countEnrolled = 0;
            if (rs.next()) {
                countEnrolled = rs.getInt("COUNT(STUDENT_ID)");
            }
            student.setText(String.valueOf(countEnrolled));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error! in Total Stuent Count");
        }
    }

    public void setDataToIssueBook() { //  total issue Books in Home Page

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms", "root", "Abhay@9899");
            String sql = "SELECT COUNT(ISSUE_ID) FROM issueBook_details";
            PreparedStatement prepare = con.prepareStatement(sql);
            ResultSet rs = prepare.executeQuery();
            int countEnrolled = 0;
            if (rs.next()) {
                countEnrolled = rs.getInt("COUNT(ISSUE_ID)");
            }
            issuebook.setText(String.valueOf(countEnrolled));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error! in Issue Book Count");
        }
    }

    public void setBookDetailsTable1() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms", "root", "Abhay@9899");
            Statement st = con.createStatement();
            java.sql.ResultSet rs = st.executeQuery("Select * from book_details");
            while (rs.next()) {
                String BookName = rs.getString("BOOK_NAME");
                String author = rs.getString("AUTHOR");
                Object[] obj = {BookName, author};
                model = (DefaultTableModel) table_books.getModel();
                model.addRow(obj);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error! in Show Data Table");
        }
    }

    public void getStudentDetails() {
        String StudentID = txt_studentid.getText();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms", "root", "Abhay@9899");
            PreparedStatement pst = con.prepareStatement("Select * from STUDENT_DETAILS where STUDENT_ID=?");
            pst.setString(1, StudentID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                label_txt_studentid.setText(rs.getString("STUDENT_ID"));
                label_txt_studentname.setText(rs.getString("NAME"));
                label_txt_studentcourse.setText(rs.getString("COURSE"));
                label_txt_studentbranch.setText(rs.getString("BRANCH"));
                lbl_StudentError.setText(" ");
            } else {
                lbl_StudentError.setText("Invalid StudentID");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void getBookDetails() {
        String BookID = txt_bookid.getText();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms", "root", "Abhay@9899");
            PreparedStatement pst = con.prepareStatement("Select * from BOOK_DETAILS where BOOK_ID=?");
            pst.setString(1, BookID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                label_txt_bookid.setText(rs.getString("BOOK_ID"));
                label_txt_bookname.setText(rs.getString("BOOK_NAME"));
                label_txt_bookauthor.setText(rs.getString("AUTHOR"));
                label_txt_bookquantity.setText(rs.getString("QUANTITY"));
                lbl_BookError.setText("");
            } else {
                lbl_BookError.setText("Invalid BookID");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //  Manages Books 218-385

    public void Auto_BookID() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
            String sql = "Select max(BOOK_ID) from book_details";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            rs.getString("Max(BOOK_ID)");
            if (rs.getString("Max(BOOK_ID)") == null) {
                txt_bookid2.setText("B-01");
            } else {
                Long id = Long.parseLong(rs.getString("Max(BOOK_ID)").substring(2, rs.getString("Max(BOOK_ID)").length()));
                id++;
                txt_bookid2.setText("B-" + String.format("%02d", id));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
       

    public void setBookDetailsTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms", "root", "Abhay@9899");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from book_details");
            while (rs.next()) {
                String BookID = rs.getString("BOOK_ID");
                String BookName = rs.getString("BOOK_NAME");
                String author = rs.getString("AUTHOR");
                int Quantity = rs.getInt("QUANTITY");
                Object[] obj = {BookID, BookName, author, Quantity};
                model = (DefaultTableModel) table_ManageBooks.getModel();
                model.addRow(obj);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public boolean Bookvalidation() {
        if (txt_bookid2.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Enter Book ID");
            return false;
        }
        if (txt_bookname.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Enter Book name");
            return false;
        }
        if (txt_authorname.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Enter Author Name");
            return false;
        }
        if (txt_quantity.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Enter Book Quantity");
            return false;
        }

        return true;
    }

    public boolean addBook() {
        boolean isAdded = false;
        BookID = txt_bookid2.getText();
        BookName = txt_bookname.getText();
        Author = txt_authorname.getText();
        Quantity = Integer.parseInt(txt_quantity.getText());
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
            String sql = "insert into book_details values(?,?,?,?) ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, BookID);
            pst.setString(2, BookName);
            pst.setString(3, Author);
            pst.setInt(4, Quantity);
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isAdded = true;
            } else {
                isAdded = false;
            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }
        return isAdded;
    }

    public boolean checkupDuplicateBook() {
        String Bookid = txt_bookid2.getText();
        boolean isExist = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms", "root", "Abhay@9899");
            PreparedStatement p = con.prepareStatement("Select * from   book_details  where BOOK_ID=?");
            p.setString(1, Bookid);
            java.sql.ResultSet rs = p.executeQuery();
            if (rs.next()) {
                isExist = true;
            } else {
                isExist = false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return isExist;
    }

    public boolean updateBook() {
        boolean isUpdated = false;
//BookID=Integer.parseInt(txt_bookid2.getText());
        BookID = txt_bookid2.getText();
        BookName = txt_bookname.getText();
        Author = txt_authorname.getText();
        Quantity = Integer.parseInt(txt_quantity.getText());
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
            String sql = "update book_details set BOOK_NAME=?,AUTHOR=?,QUANTITY=? where BOOK_ID=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, BookName);
            pst.setString(2, Author);
            pst.setInt(3, Quantity);
            pst.setString(4, BookID);
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isUpdated = true;
            } else {
                isUpdated = false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return isUpdated;
    }
//method to clear table

    public void clearTabelBooks() {
        DefaultTableModel model = (DefaultTableModel) table_ManageBooks.getModel();
        model.setRowCount(0);
    }

    public boolean deleteBook() {
        boolean isDeleted = false;
//BookID=Integer.parseInt(txt_bookid2.getText());
        BookID = txt_bookid2.getText();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
            String sql = "delete from book_details where BOOK_ID=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, BookID);
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isDeleted = true;
            } else {
                isDeleted = false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return isDeleted;
    }
    // End of Manages 

    public void Auto_StudentID() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
            String sql = "Select max(STUDENT_ID) from STUDENT_DETAILS";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            rs.getString("Max(STUDENT_ID)");
            if (rs.getString("Max(STUDENT_ID)") == null) {
                txt_studentid2.setText("S-01");
            } else {
                Long id = Long.parseLong(rs.getString("Max(STUDENT_ID)").substring(2, rs.getString("Max(STUDENT_ID)").length()));
                id++;
                txt_studentid2.setText("S-" + String.format("%02d", id));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setStudentDetailsTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms", "root", "Abhay@9899");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from STUDENT_DETAILS");
            while (rs.next()) {
                String StudentID = rs.getString("STUDENT_ID");
                String StudentName = rs.getString("NAME");
                String Course = rs.getString("COURSE");
                String Branch = rs.getString("BRANCH");
                Object[] obj = {StudentID, StudentName, Course, Branch};
                model = (DefaultTableModel) table_student.getModel();
                model.addRow(obj);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public boolean Studentvalidation() {
        if (txt_studentid2.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Enter Student ID");
            return false;
        }
        if (txt_studentname.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Enter Student name");
            return false;
        }
        if (combo_course.getSelectedItem().equals("---SELECT---")) {
            JOptionPane.showMessageDialog(null, "Please Select Course");
            return false;
        }
        if (combo_branch.getSelectedItem().equals("---SELECT---")) {
            JOptionPane.showMessageDialog(null, "Please Select Branch");
            return false;
        }

        return true;
    }

    public boolean addStudent() {
        boolean isAdded = false;
        StudentID = txt_studentid2.getText();
        StudentName = txt_studentname.getText();
        Course = combo_course.getSelectedItem().toString();
        Branch = combo_branch.getSelectedItem().toString();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
            String sql = "insert into STUDENT_DETAILS values(?,?,?,?) ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, StudentID);
            pst.setString(2, StudentName);
            pst.setString(3, Course);
            pst.setString(4, Branch);
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isAdded = true;
                txt_studentid2.setText("");
                txt_studentname.setText("");
                combo_course.setSelectedItem("---SELECT---");
                combo_branch.setSelectedItem("---SELECT---");
            } else {
                isAdded = false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Student  already exists");
        }
        return isAdded;
    }

    public boolean deleteStudent() {
        boolean isDeleted = false;
        StudentID = txt_studentid2.getText();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
            String sql = "delete from STUDENT_DETAILS where STUDENT_ID=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, StudentID);
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isDeleted = true;
            } else {
                isDeleted = false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return isDeleted;
    }

    public boolean updateStudent() {
        boolean isUpdated = false;
        StudentID = txt_studentid2.getText();
        StudentName = txt_studentname.getText();
        Course = combo_course.getSelectedItem().toString();
        Branch = combo_branch.getSelectedItem().toString();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
            String sql = "update STUDENT_DETAILS set NAME=?,COURSE=?,BRANCH=? where STUDENT_ID=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, StudentName);
            pst.setString(2, Course);
            pst.setString(3, Branch);
            pst.setString(4, StudentID);
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isUpdated = true;
            } else {
                isUpdated = false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return isUpdated;
    }

    public boolean checkupDuplicateUser() {
        String StudentID = txt_studentid2.getText();
        boolean isExist = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms", "root", "Abhay@9899");
            PreparedStatement p = con.prepareStatement("Select * from   STUDENT_DETAILS  where STUDENT_ID=?");
            p.setString(1, StudentID);
            java.sql.ResultSet rs = p.executeQuery();
            if (rs.next()) {
                isExist = true;
            } else {
                isExist = false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return isExist;
    }

    public void clearStudentTabel() {
        DefaultTableModel model = (DefaultTableModel) table_student.getModel();
        model.setRowCount(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Gender_radioButton1 = new javax.swing.ButtonGroup();
        Gender_radioButton = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        manage_bookPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        manage_studentsPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        issue_bookPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        return_bookpanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        viewrecords_panel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        viewissuedbook_panel = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        defaulterlist_panel = new javax.swing.JPanel();
        jlabel12 = new javax.swing.JLabel();
        setting_panel = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        logout_panel = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel54 = new javax.swing.JPanel();
        jLabel107 = new javax.swing.JLabel();
        jPanel55 = new javax.swing.JPanel();
        NoOfBooksIcon = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        book = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jPanel56 = new javax.swing.JPanel();
        NoOfBooksIcon1 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        Totalbook = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jPanel57 = new javax.swing.JPanel();
        NoOfStudenticon = new javax.swing.JLabel();
        student = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jPanel58 = new javax.swing.JPanel();
        noOfIssueBookicons = new javax.swing.JLabel();
        issuebook = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        table_books = new rojeru_san.complementos.RSTableMetro();
        panelBarChart = new javax.swing.JPanel();
        jLabel128 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel43 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        txt_bookname = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        txt_authorname = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        txt_quantity = new javax.swing.JTextField();
        Add_button1 = new javax.swing.JButton();
        Update_button1 = new javax.swing.JButton();
        delete_button1 = new javax.swing.JButton();
        jLabel76 = new javax.swing.JLabel();
        txt_bookid2 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        table_ManageBooks = new rojeru_san.complementos.RSTableMetro();
        jLabel67 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        txt_studentname = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        Add_button = new javax.swing.JButton();
        Update_button = new javax.swing.JButton();
        delete_button = new javax.swing.JButton();
        combo_course = new javax.swing.JComboBox<>();
        combo_branch = new javax.swing.JComboBox<>();
        jLabel63 = new javax.swing.JLabel();
        txt_studentid2 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        table_student = new rojeru_san.complementos.RSTableMetro();
        jLabel22 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        label_txt_bookid = new javax.swing.JLabel();
        label_txt_bookname = new javax.swing.JLabel();
        label_txt_bookauthor = new javax.swing.JLabel();
        label_txt_bookquantity = new javax.swing.JLabel();
        lbl_BookError = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        label_txt_studentname = new javax.swing.JLabel();
        label_txt_studentid = new javax.swing.JLabel();
        label_txt_studentcourse = new javax.swing.JLabel();
        label_txt_studentbranch = new javax.swing.JLabel();
        lbl_StudentError = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        issued_button4 = new javax.swing.JButton();
        txt_bookid = new javax.swing.JTextField();
        txt_studentid = new javax.swing.JTextField();
        txt_issuedate = new com.toedter.calendar.JDateChooser();
        txt_duedate = new com.toedter.calendar.JDateChooser();
        jLabel123 = new javax.swing.JLabel();
        txt_issueid = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        lbl_issueId1 = new javax.swing.JLabel();
        lbl_bookname1 = new javax.swing.JLabel();
        lbl_issuedate1 = new javax.swing.JLabel();
        label_txt_bookquantity1 = new javax.swing.JLabel();
        label_bookerror = new javax.swing.JLabel();
        lbl_studentname1 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        txt_bookid1 = new javax.swing.JTextField();
        txt_studentid1 = new javax.swing.JTextField();
        issued_button2 = new javax.swing.JButton();
        issued_button3 = new javax.swing.JButton();
        jLabel124 = new javax.swing.JLabel();
        txt_returnDate = new com.toedter.calendar.JDateChooser();
        jLabel30 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        All_Dues_Record_indivdual_Student = new rojeru_san.complementos.RSTableMetro();
        lbl_studentname2 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        SearchUtton = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jPanel60 = new javax.swing.JPanel();
        issuedate = new com.toedter.calendar.JDateChooser();
        DueDate = new com.toedter.calendar.JDateChooser();
        jPanel27 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        viewallrecords = new rojeru_san.complementos.RSTableMetro();
        jLabel29 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ISSUE_BOOKDETAILS = new rojeru_san.complementos.RSTableMetro();
        jLabel125 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        jPanel62 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_DefaulterList = new rojeru_san.complementos.RSTableMetro();
        jLabel122 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel59 = new javax.swing.JPanel();
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jPanel63 = new javax.swing.JPanel();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        jLabel136 = new javax.swing.JLabel();
        txt_ADMIN_PROFILE_EMAILID = new javax.swing.JTextField();
        jLabel137 = new javax.swing.JLabel();
        txt_ADMIN_PROFILE_CONTACTNO = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel138 = new javax.swing.JLabel();
        txt_ADMIN_PROFILE_FULLNAME = new javax.swing.JTextField();
        txt_ADMIN_PROFILE_REGDATE = new javax.swing.JLabel();
        txt_ADMIN_PROFILE_USERNAME = new javax.swing.JLabel();
        label_Admin_profile_image = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        jPanel65 = new javax.swing.JPanel();
        Update_Img = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        profile_image = new javax.swing.JLabel();
        IMAGE_UPLOAD = new javax.swing.JButton();
        male = new javax.swing.JRadioButton();
        female = new javax.swing.JRadioButton();
        txt_dob = new com.toedter.calendar.JDateChooser();
        txt_username = new javax.swing.JTextField();
        txt_fullname = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        DATA_INSERTED = new javax.swing.JButton();
        DATA_CLEAR = new javax.swing.JButton();
        txt_id = new javax.swing.JLabel();
        ComboBox_SecurityQ = new javax.swing.JComboBox<>();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        txt_contactno = new javax.swing.JTextField();
        txt_answer = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        show_password = new javax.swing.JCheckBox();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jPanel48 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        txt_newpassword = new javax.swing.JPasswordField();
        txt_confirmpassword = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        txt_currentpassword = new javax.swing.JTextField();
        show_pass = new javax.swing.JCheckBox();
        jLabel116 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icons8_menu_48px_1.png"))); // NOI18N

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Library Management System");

        name.setBackground(new java.awt.Color(0, 0, 0));
        name.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        name.setForeground(new java.awt.Color(255, 255, 255));
        name.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nameMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 796, Short.MAX_VALUE)
                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1330, 70));

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 51, 51));

        jLabel3.setBackground(new java.awt.Color(255, 51, 51));
        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/home_24px.png"))); // NOI18N
        jLabel3.setText(" Home Page");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 240, 50));

        jLabel5.setBackground(new java.awt.Color(255, 51, 51));
        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Features");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 128, 169, 30));

        manage_bookPanel.setBackground(new java.awt.Color(51, 51, 51));
        manage_bookPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 1, 1, 1, new java.awt.Color(0, 0, 0)));

        jLabel6.setBackground(new java.awt.Color(255, 51, 51));
        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icons8_Book_26px.png"))); // NOI18N
        jLabel6.setText("Manage Books");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
        });

        javax.swing.GroupLayout manage_bookPanelLayout = new javax.swing.GroupLayout(manage_bookPanel);
        manage_bookPanel.setLayout(manage_bookPanelLayout);
        manage_bookPanelLayout.setHorizontalGroup(
            manage_bookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manage_bookPanelLayout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        manage_bookPanelLayout.setVerticalGroup(
            manage_bookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manage_bookPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(manage_bookPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 240, 50));

        manage_studentsPanel.setBackground(new java.awt.Color(51, 51, 51));
        manage_studentsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setBackground(new java.awt.Color(255, 51, 51));
        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icons8_Read_Online_26px.png"))); // NOI18N
        jLabel7.setText("Manage Students");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
        });

        javax.swing.GroupLayout manage_studentsPanelLayout = new javax.swing.GroupLayout(manage_studentsPanel);
        manage_studentsPanel.setLayout(manage_studentsPanelLayout);
        manage_studentsPanelLayout.setHorizontalGroup(
            manage_studentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manage_studentsPanelLayout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        manage_studentsPanelLayout.setVerticalGroup(
            manage_studentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manage_studentsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(manage_studentsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 240, 50));

        issue_bookPanel.setBackground(new java.awt.Color(51, 51, 51));
        issue_bookPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setBackground(new java.awt.Color(255, 51, 51));
        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icons8_Sell_26px.png"))); // NOI18N
        jLabel8.setText("Issue Book");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel8MouseExited(evt);
            }
        });

        javax.swing.GroupLayout issue_bookPanelLayout = new javax.swing.GroupLayout(issue_bookPanel);
        issue_bookPanel.setLayout(issue_bookPanelLayout);
        issue_bookPanelLayout.setHorizontalGroup(
            issue_bookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, issue_bookPanelLayout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        issue_bookPanelLayout.setVerticalGroup(
            issue_bookPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(issue_bookPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(issue_bookPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 240, 50));

        return_bookpanel.setBackground(new java.awt.Color(51, 51, 51));
        return_bookpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setBackground(new java.awt.Color(255, 51, 51));
        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 153, 153));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icons8_Return_Purchase_26px.png"))); // NOI18N
        jLabel9.setText("Return Book");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel9MouseExited(evt);
            }
        });

        javax.swing.GroupLayout return_bookpanelLayout = new javax.swing.GroupLayout(return_bookpanel);
        return_bookpanel.setLayout(return_bookpanelLayout);
        return_bookpanelLayout.setHorizontalGroup(
            return_bookpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, return_bookpanelLayout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        return_bookpanelLayout.setVerticalGroup(
            return_bookpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(return_bookpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(return_bookpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 240, 50));

        viewrecords_panel.setBackground(new java.awt.Color(51, 51, 51));
        viewrecords_panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setBackground(new java.awt.Color(255, 51, 51));
        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icons8_View_Details_26px.png"))); // NOI18N
        jLabel10.setText("View Records");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel10MouseExited(evt);
            }
        });

        javax.swing.GroupLayout viewrecords_panelLayout = new javax.swing.GroupLayout(viewrecords_panel);
        viewrecords_panel.setLayout(viewrecords_panelLayout);
        viewrecords_panelLayout.setHorizontalGroup(
            viewrecords_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewrecords_panelLayout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        viewrecords_panelLayout.setVerticalGroup(
            viewrecords_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewrecords_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(viewrecords_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 240, 50));

        viewissuedbook_panel.setBackground(new java.awt.Color(51, 51, 51));
        viewissuedbook_panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel11.setBackground(new java.awt.Color(255, 51, 51));
        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 153, 153));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icons8_Books_26px.png"))); // NOI18N
        jLabel11.setText("View Issued Books");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel11MouseExited(evt);
            }
        });

        javax.swing.GroupLayout viewissuedbook_panelLayout = new javax.swing.GroupLayout(viewissuedbook_panel);
        viewissuedbook_panel.setLayout(viewissuedbook_panelLayout);
        viewissuedbook_panelLayout.setHorizontalGroup(
            viewissuedbook_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewissuedbook_panelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        viewissuedbook_panelLayout.setVerticalGroup(
            viewissuedbook_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewissuedbook_panelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.add(viewissuedbook_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 240, 50));

        defaulterlist_panel.setBackground(new java.awt.Color(51, 51, 51));
        defaulterlist_panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        defaulterlist_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                defaulterlist_panelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                defaulterlist_panelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                defaulterlist_panelMouseExited(evt);
            }
        });
        defaulterlist_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlabel12.setBackground(new java.awt.Color(255, 51, 51));
        jlabel12.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jlabel12.setForeground(new java.awt.Color(153, 153, 153));
        jlabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icons8_Conference_26px.png"))); // NOI18N
        jlabel12.setText("Defaulter List");
        jlabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlabel12MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jlabel12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jlabel12MouseExited(evt);
            }
        });
        defaulterlist_panel.add(jlabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 7, 183, 34));

        jPanel3.add(defaulterlist_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 240, 50));

        setting_panel.setBackground(new java.awt.Color(51, 51, 51));
        setting_panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setting_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setting_panelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setting_panelMouseExited(evt);
            }
        });

        jLabel13.setBackground(new java.awt.Color(255, 51, 51));
        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 153, 153));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/settings.png"))); // NOI18N
        jLabel13.setText("Setting");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel13MouseExited(evt);
            }
        });

        javax.swing.GroupLayout setting_panelLayout = new javax.swing.GroupLayout(setting_panel);
        setting_panel.setLayout(setting_panelLayout);
        setting_panelLayout.setHorizontalGroup(
            setting_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, setting_panelLayout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        setting_panelLayout.setVerticalGroup(
            setting_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(setting_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(setting_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 520, 240, 50));

        logout_panel.setBackground(new java.awt.Color(51, 51, 51));
        logout_panel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        logout_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logout_panelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logout_panelMouseExited(evt);
            }
        });

        jLabel14.setBackground(new java.awt.Color(255, 51, 51));
        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(153, 153, 153));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icons8_Exit_26px.png"))); // NOI18N
        jLabel14.setText("Logout");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel14MouseExited(evt);
            }
        });

        javax.swing.GroupLayout logout_panelLayout = new javax.swing.GroupLayout(logout_panel);
        logout_panel.setLayout(logout_panelLayout);
        logout_panelLayout.setHorizontalGroup(
            logout_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logout_panelLayout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        logout_panelLayout.setVerticalGroup(
            logout_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logout_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(logout_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 570, 240, 60));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 240, 630));

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1100, 670));

        jLabel107.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel107.setText(" Books Cagetory");

        jPanel55.setBackground(new java.awt.Color(204, 204, 204));
        jPanel55.setBorder(javax.swing.BorderFactory.createMatteBorder(10, 0, 0, 0, new java.awt.Color(255, 51, 51)));

        NoOfBooksIcon.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        NoOfBooksIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icons8_Book_Shelf_50px.png"))); // NOI18N

        jLabel108.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N

        book.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel55Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NoOfBooksIcon)
                .addGap(40, 40, 40)
                .addComponent(book, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel108)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel55Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel108)
                .addGap(36, 36, 36))
            .addGroup(jPanel55Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NoOfBooksIcon)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel55Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(book, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel109.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel109.setText(" Total Book ");

        jPanel56.setBackground(new java.awt.Color(204, 204, 204));
        jPanel56.setBorder(javax.swing.BorderFactory.createMatteBorder(10, 0, 0, 0, new java.awt.Color(255, 51, 51)));

        NoOfBooksIcon1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        NoOfBooksIcon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icons8_Book_Shelf_50px.png"))); // NOI18N

        jLabel110.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N

        Totalbook.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel56Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NoOfBooksIcon1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Totalbook, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel110)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel56Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel110)
                .addGap(36, 36, 36))
            .addGroup(jPanel56Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NoOfBooksIcon1)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel56Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Totalbook, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel111.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel111.setText("No. of Students");

        jPanel57.setBackground(new java.awt.Color(204, 204, 204));
        jPanel57.setBorder(javax.swing.BorderFactory.createMatteBorder(10, 0, 0, 0, new java.awt.Color(255, 51, 51)));
        jPanel57.setPreferredSize(new java.awt.Dimension(150, 100));

        NoOfStudenticon.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        NoOfStudenticon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icons8_People_50px.png"))); // NOI18N

        student.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NoOfStudenticon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(student, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addComponent(NoOfStudenticon)
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel57Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(student, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel112.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel112.setText("No.of Issued Books");

        jPanel58.setBackground(new java.awt.Color(204, 204, 204));
        jPanel58.setBorder(javax.swing.BorderFactory.createMatteBorder(10, 0, 0, 0, new java.awt.Color(255, 51, 51)));

        noOfIssueBookicons.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        noOfIssueBookicons.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icons8_Sell_50px.png"))); // NOI18N

        issuebook.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel58Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(noOfIssueBookicons)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(issuebook, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel58Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(noOfIssueBookicons)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel58Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(issuebook, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel113.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel113.setText("    Books  Info");

        table_books.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Author"
            }
        ));
        table_books.setRowHeight(40);
        jScrollPane7.setViewportView(table_books);

        panelBarChart.setLayout(new java.awt.BorderLayout());

        jLabel128.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel128.setText("LMS / HOME");

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel54Layout.createSequentialGroup()
                        .addComponent(panelBarChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(45, 45, 45)
                        .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel54Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel54Layout.createSequentialGroup()
                        .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel54Layout.createSequentialGroup()
                                .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100)
                                .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100)
                                .addComponent(jLabel111, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100)
                                .addComponent(jLabel112, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel54Layout.createSequentialGroup()
                                .addComponent(jPanel55, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100)
                                .addComponent(jPanel56, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100)
                                .addComponent(jPanel57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)
                                .addComponent(jPanel58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 86, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel107)
                    .addComponent(jLabel109)
                    .addComponent(jLabel111)
                    .addComponent(jLabel112))
                .addGap(9, 9, 9)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel54Layout.createSequentialGroup()
                        .addComponent(jLabel113)
                        .addGap(1, 1, 1)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(panelBarChart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab1", jPanel6);

        jPanel44.setBackground(new java.awt.Color(102, 102, 255));

        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Contact_26px.png"))); // NOI18N

        jLabel69.setBackground(new java.awt.Color(0, 0, 0));
        jLabel69.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setText("Enter Book Id");

        jLabel70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Moleskine_26px.png"))); // NOI18N

        jLabel71.setBackground(new java.awt.Color(0, 0, 0));
        jLabel71.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(255, 255, 255));
        jLabel71.setText("Enter Book Name");

        txt_bookname.setBackground(new java.awt.Color(102, 102, 255));
        txt_bookname.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_bookname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_bookname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_booknameFocusLost(evt);
            }
        });

        jLabel72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Collaborator_Male_26px.png"))); // NOI18N

        jLabel73.setBackground(new java.awt.Color(0, 0, 0));
        jLabel73.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(255, 255, 255));
        jLabel73.setText("Author Name");

        txt_authorname.setBackground(new java.awt.Color(102, 102, 255));
        txt_authorname.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_authorname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_authorname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_authornameFocusLost(evt);
            }
        });

        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Unit_26px.png"))); // NOI18N

        jLabel75.setBackground(new java.awt.Color(0, 0, 0));
        jLabel75.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(255, 255, 255));
        jLabel75.setText("Quantity");

        txt_quantity.setBackground(new java.awt.Color(102, 102, 255));
        txt_quantity.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_quantity.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_quantity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_quantityFocusLost(evt);
            }
        });

        Add_button1.setBackground(new java.awt.Color(102, 102, 255));
        Add_button1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        Add_button1.setForeground(new java.awt.Color(255, 255, 255));
        Add_button1.setText("Add");
        Add_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add_button1ActionPerformed(evt);
            }
        });

        Update_button1.setBackground(new java.awt.Color(102, 102, 255));
        Update_button1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        Update_button1.setForeground(new java.awt.Color(255, 255, 255));
        Update_button1.setText("Update");
        Update_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Update_button1ActionPerformed(evt);
            }
        });

        delete_button1.setBackground(new java.awt.Color(102, 102, 255));
        delete_button1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        delete_button1.setForeground(new java.awt.Color(255, 255, 255));
        delete_button1.setText("Delete");
        delete_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_button1ActionPerformed(evt);
            }
        });

        jLabel76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/broom.png"))); // NOI18N
        jLabel76.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel76MouseClicked(evt);
            }
        });

        txt_bookid2.setBackground(new java.awt.Color(102, 102, 255));
        txt_bookid2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addComponent(jLabel74)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addComponent(jLabel68)
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel44Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel44Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(txt_bookid2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addComponent(jLabel72)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_authorname, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel44Layout.createSequentialGroup()
                            .addComponent(jLabel70)
                            .addGap(18, 18, 18)
                            .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_bookname, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jLabel76)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(Add_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(Update_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(delete_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 36, Short.MAX_VALUE))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel76)
                    .addComponent(jLabel69))
                .addGap(7, 7, 7)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_bookid2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(jLabel71)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_bookname, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(jLabel73)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_authorname, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addComponent(jLabel75)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Add_button1)
                    .addComponent(Update_button1)
                    .addComponent(delete_button1))
                .addGap(21, 21, 21))
        );

        jPanel45.setBackground(new java.awt.Color(255, 51, 51));
        jPanel45.setPreferredSize(new java.awt.Dimension(402, 4));

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 402, Short.MAX_VALUE)
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        jLabel77.setBackground(new java.awt.Color(255, 51, 51));
        jLabel77.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(255, 51, 51));
        jLabel77.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Books_52px_1.png"))); // NOI18N
        jLabel77.setText("Manage Books");

        jLabel78.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/clear.png"))); // NOI18N
        jLabel78.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel78MouseClicked(evt);
            }
        });

        jLabel79.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/refresh.png"))); // NOI18N
        jLabel79.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel79MouseClicked(evt);
            }
        });

        table_ManageBooks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book ID", "Name", "Author", "Quantity"
            }
        ));
        table_ManageBooks.setRowHeight(30);
        table_ManageBooks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_ManageBooksMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(table_ManageBooks);

        jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/rewind-button.png"))); // NOI18N
        jLabel67.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel67MouseClicked(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel21.setText("LMS / MANAGE BOOKS");

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel67)
                    .addComponent(jLabel21)
                    .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                        .addComponent(jLabel77)
                        .addGap(191, 191, 191))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                        .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(101, 101, 101))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel43Layout.createSequentialGroup()
                            .addComponent(jLabel79)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel78))
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel77, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel67)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel79)
                            .addComponent(jLabel78))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab2", jPanel7);

        jPanel41.setBackground(new java.awt.Color(102, 102, 255));

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Contact_26px.png"))); // NOI18N

        jLabel32.setBackground(new java.awt.Color(0, 0, 0));
        jLabel32.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Enter Student Id");

        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Moleskine_26px.png"))); // NOI18N

        jLabel58.setBackground(new java.awt.Color(0, 0, 0));
        jLabel58.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Enter Student Name");

        txt_studentname.setBackground(new java.awt.Color(102, 102, 255));
        txt_studentname.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_studentname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_studentname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_studentnameFocusLost(evt);
            }
        });

        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Collaborator_Male_26px.png"))); // NOI18N

        jLabel60.setBackground(new java.awt.Color(0, 0, 0));
        jLabel60.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setText("Select Course");

        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Unit_26px.png"))); // NOI18N

        jLabel62.setBackground(new java.awt.Color(0, 0, 0));
        jLabel62.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("Select Branch");

        Add_button.setBackground(new java.awt.Color(102, 102, 255));
        Add_button.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        Add_button.setForeground(new java.awt.Color(255, 255, 255));
        Add_button.setText("Add");
        Add_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add_buttonActionPerformed(evt);
            }
        });

        Update_button.setBackground(new java.awt.Color(102, 102, 255));
        Update_button.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        Update_button.setForeground(new java.awt.Color(255, 255, 255));
        Update_button.setText("Update");
        Update_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Update_buttonActionPerformed(evt);
            }
        });

        delete_button.setBackground(new java.awt.Color(102, 102, 255));
        delete_button.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        delete_button.setForeground(new java.awt.Color(255, 255, 255));
        delete_button.setText("Delete");
        delete_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_buttonActionPerformed(evt);
            }
        });

        combo_course.setBackground(new java.awt.Color(102, 102, 255));
        combo_course.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        combo_course.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---SELECT---", "B.Tech / B.E. (Bachelor of Technology / Bachelor of Engineering)", "B.Sc. (Bachelor of Science)", "B.A. (Bachelor of Arts)", "B.Com (Bachelor of Commerce)", "BBA / BMS (Bachelor of Business Administration / Bachelor of Management Studies)", "BDS (Bachelor of Dental Surgery)", "MBBS (Bachelor of Medicine, Bachelor of Surgery)", "B.Pharm (Bachelor of Pharmacy)", "BCA (Bachelor of Computer Applications)", "BHM (Bachelor of Hotel Management)", "BFA (Bachelor of Fine Arts)", "B.Design / B.Des (Bachelor of Design)", "M.Tech / M.S. (Master of Technology / Master of Science)", "M.Sc. (Master of Science)", "M.A. (Master of Arts)", "M.Com (Master of Commerce)", "MBA (Master of Business Administration)", "MCA (Master of Computer Applications)", "M.Pharm (Master of Pharmacy)", "MSW (Master of Social Work)", "LL.M (Master of Laws)", "MFA (Master of Fine Arts)", "M.Ed (Master of Education)", "MD (Doctor of Medicine)", "MDS (Master of Dental Surgery)", "MS (Master of Surgery)" }));
        combo_course.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_courseActionPerformed(evt);
            }
        });

        combo_branch.setBackground(new java.awt.Color(102, 102, 255));
        combo_branch.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        combo_branch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---SELECT---", "---B.Tech / B.E.---", "Computer Science Engineering (CSE)", "Mechanical Engineering (ME)", "Civil Engineering (CE)", "Electrical Engineering (EE)", "Electronics and Communication Engineering (ECE)", "Information Technology (IT)", "Biotechnology", "Chemical Engineering", "Aerospace Engineering", "Agricultural Engineering", "Robotics Engineering", "Environmental Engineering", "---B.Sc. ---", "Physics", "Chemistry", "Mathematics", "Biology", "Computer Science", "Environmental Science", "Forensic Science", "Data Science", "Agriculture Science", "Biotechnology", "Medical Lab Technology", "---B.A---", "English", "History", "Political Science", "Sociology", "Psychology", "Economics", "Philosophy", "Journalism", "Education", "Geography", "Linguistics", "---B.com---", "General", "Accounting & Finance", "Banking & Insurance", "Taxation", "Business Studies", "E-Commerce", "---BBA---", "General", "International Business", "Entrepreneurship", "Human Resource Management", "Marketing", "Finance", "Logistics & Supply Chain Management", "---B.Design / B.Des---", "Fashion Design", "Interior Design", "Product Design", "Graphic Design", "---M.tech---", "Computer Science", "Software Engineering", "VLSI Design", "Artificial Intelligence", "Data Science", "Electrical Engineering", "Mechanical Engineering", "Structural Engineering", "Civil Engineering", "Biotechnology", "Robotics", "Aerospace Engineering", "---M.Sc---", "Physics", "Chemistry", "Biology", "Mathematics", "Computer Science", "Environmental Science", "Bioinformatics", "Agriculture Science", "Statistics", "Data Science", "---M.A---", "English", "History", "Sociology", "Political Science", "Economics", "Psychology", "Philosophy", "Journalism", "Geography", "---M.com---", "Accounting", "Finance", "Business Management", "---MBA---", "General", "Marketing", "Finance", "HR Management", "Operations Management", "International Business", "Project Management", "Retail Management", "Supply Chain Management", "IT Management", "---M.Des---", "Fashion Design", "Interior Design", "Graphic Design", "Product Design", "---MD---", "General Medicine", "Pediatrics", "Dermatology", "Gynecology", "Surgery", "---MDS---", "Orthodontics", "Periodontics", "Prosthodontics", "Endodontics", "----MS---", "General Surgery", "Orthopedics", "Neurosurgery" }));

        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/broom.png"))); // NOI18N
        jLabel63.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel63MouseClicked(evt);
            }
        });

        txt_studentid2.setBackground(new java.awt.Color(102, 102, 255));
        txt_studentid2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(96, 96, 96)
                                .addComponent(jLabel63))
                            .addComponent(txt_studentid2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addComponent(jLabel57)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_studentname, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addComponent(jLabel61)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(combo_branch, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addComponent(jLabel59)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(combo_course, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addComponent(Add_button, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(Update_button, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(delete_button, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel63, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_studentid2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(jLabel58)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_studentname, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(jLabel60)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel41Layout.createSequentialGroup()
                                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jLabel62))
                            .addComponent(combo_course, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(combo_branch, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Add_button)
                    .addComponent(Update_button)
                    .addComponent(delete_button)))
        );

        jLabel64.setBackground(new java.awt.Color(255, 51, 51));
        jLabel64.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 51, 51));
        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Books_52px_1.png"))); // NOI18N
        jLabel64.setText("Manage Students");

        jPanel42.setBackground(new java.awt.Color(255, 51, 51));
        jPanel42.setPreferredSize(new java.awt.Dimension(402, 4));

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 402, Short.MAX_VALUE)
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/clear.png"))); // NOI18N
        jLabel65.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel65MouseClicked(evt);
            }
        });

        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/refresh.png"))); // NOI18N
        jLabel66.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel66MouseClicked(evt);
            }
        });

        table_student.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Name", "Course", "Branch"
            }
        ));
        table_student.setRowHeight(30);
        table_student.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_studentMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(table_student);

        jLabel22.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel22.setText("LMS / MANAGE STUDENTS");

        jLabel129.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/rewind-button.png"))); // NOI18N
        jLabel129.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel129MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel129)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(92, 92, 92)))
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel40Layout.createSequentialGroup()
                                .addComponent(jLabel66)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel65)))
                        .addContainerGap(75, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                                .addComponent(jLabel64)
                                .addGap(90, 90, 90))
                            .addComponent(jPanel42, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(178, 178, 178))))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel129)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel64)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
                        .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel66, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel65, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab3", jPanel8);

        jPanel33.setBackground(new java.awt.Color(255, 51, 51));
        jPanel33.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Book Name :");

        jLabel16.setBackground(new java.awt.Color(0, 0, 0));
        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Book Id :");

        jLabel17.setBackground(new java.awt.Color(0, 0, 0));
        jLabel17.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Author :");

        jLabel20.setBackground(new java.awt.Color(0, 0, 0));
        jLabel20.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Quantity :");

        jLabel46.setBackground(new java.awt.Color(0, 0, 0));
        jLabel46.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Literature_100px_1.png"))); // NOI18N
        jLabel46.setText("Book Details");

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));
        jPanel34.setPreferredSize(new java.awt.Dimension(402, 4));

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 258, Short.MAX_VALUE)
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        label_txt_bookid.setBackground(new java.awt.Color(255, 255, 255));
        label_txt_bookid.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        label_txt_bookname.setBackground(new java.awt.Color(255, 255, 255));
        label_txt_bookname.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        label_txt_bookauthor.setBackground(new java.awt.Color(255, 255, 255));
        label_txt_bookauthor.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        label_txt_bookquantity.setBackground(new java.awt.Color(255, 255, 255));
        label_txt_bookquantity.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        lbl_BookError.setBackground(new java.awt.Color(0, 0, 0));
        lbl_BookError.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel33Layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel33Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(label_txt_bookname, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel33Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_txt_bookid, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(label_txt_bookauthor, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_txt_bookquantity, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_BookError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(label_txt_bookid, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(label_txt_bookname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(label_txt_bookauthor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(label_txt_bookquantity, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(lbl_BookError, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        jPanel35.setBackground(new java.awt.Color(102, 102, 255));
        jPanel35.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel47.setBackground(new java.awt.Color(0, 0, 0));
        jLabel47.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Student Name :");

        jLabel48.setBackground(new java.awt.Color(0, 0, 0));
        jLabel48.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Student Id :");

        jLabel49.setBackground(new java.awt.Color(0, 0, 0));
        jLabel49.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Course Name :");

        jLabel50.setBackground(new java.awt.Color(0, 0, 0));
        jLabel50.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("Branch :");

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));
        jPanel36.setPreferredSize(new java.awt.Dimension(402, 4));

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 258, Short.MAX_VALUE)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        jLabel51.setBackground(new java.awt.Color(0, 0, 0));
        jLabel51.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Student_Registration_100px_2.png"))); // NOI18N
        jLabel51.setText("Student Details");

        label_txt_studentname.setBackground(new java.awt.Color(255, 255, 255));
        label_txt_studentname.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        label_txt_studentid.setBackground(new java.awt.Color(255, 255, 255));
        label_txt_studentid.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        label_txt_studentcourse.setBackground(new java.awt.Color(255, 255, 255));
        label_txt_studentcourse.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        label_txt_studentbranch.setBackground(new java.awt.Color(255, 255, 255));
        label_txt_studentbranch.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        lbl_StudentError.setBackground(new java.awt.Color(0, 0, 0));
        lbl_StudentError.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel35Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_txt_studentname, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_txt_studentid, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_txt_studentcourse, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_StudentError, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_txt_studentbranch, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel51)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(label_txt_studentid, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_txt_studentname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(label_txt_studentcourse, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(label_txt_studentbranch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_StudentError, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jPanel37.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel38.setBackground(new java.awt.Color(255, 51, 51));
        jPanel38.setPreferredSize(new java.awt.Dimension(402, 4));

        jPanel39.setBackground(new java.awt.Color(255, 51, 51));
        jPanel39.setPreferredSize(new java.awt.Dimension(402, 4));

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 324, Short.MAX_VALUE)
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel38Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel38Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jLabel52.setBackground(new java.awt.Color(255, 51, 51));
        jLabel52.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 51, 51));
        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Books_52px_1.png"))); // NOI18N
        jLabel52.setText("Issue Books");

        jLabel53.setBackground(new java.awt.Color(0, 0, 0));
        jLabel53.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 51, 51));
        jLabel53.setText("Book Id :");

        jLabel54.setBackground(new java.awt.Color(0, 0, 0));
        jLabel54.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 51, 51));
        jLabel54.setText("Issue Date :");

        jLabel55.setBackground(new java.awt.Color(0, 0, 0));
        jLabel55.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 51, 51));
        jLabel55.setText("Student Id :");

        jLabel56.setBackground(new java.awt.Color(0, 0, 0));
        jLabel56.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 51, 51));
        jLabel56.setText("Due Date :");

        issued_button4.setBackground(new java.awt.Color(255, 51, 51));
        issued_button4.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        issued_button4.setForeground(new java.awt.Color(255, 255, 255));
        issued_button4.setText("ISSUE BOOK");
        issued_button4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                issued_button4ActionPerformed(evt);
            }
        });

        txt_bookid.setBackground(new java.awt.Color(242, 242, 242));
        txt_bookid.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_bookid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 51, 51)));
        txt_bookid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_bookidFocusLost(evt);
            }
        });

        txt_studentid.setBackground(new java.awt.Color(242, 242, 242));
        txt_studentid.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_studentid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 51, 51)));
        txt_studentid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_studentidFocusLost(evt);
            }
        });

        txt_issuedate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2));
        txt_issuedate.setDateFormatString("yyyy-MM-dd");
        txt_issuedate.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        txt_duedate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2));
        txt_duedate.setDateFormatString("yyyy-MM-dd");
        txt_duedate.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel123.setBackground(new java.awt.Color(0, 0, 0));
        jLabel123.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel123.setForeground(new java.awt.Color(255, 51, 51));
        jLabel123.setText("Issue Id :");

        txt_issueid.setBackground(new java.awt.Color(0, 0, 0));
        txt_issueid.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(jLabel52))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_bookid, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(issued_button4, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel37Layout.createSequentialGroup()
                                .addComponent(jLabel123, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_issueid, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel37Layout.createSequentialGroup()
                                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_duedate, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel37Layout.createSequentialGroup()
                                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_issuedate, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel37Layout.createSequentialGroup()
                                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_studentid, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel52)
                .addGap(6, 6, 6)
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel123, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_issueid, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txt_bookid, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txt_studentid, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_issuedate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_duedate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(issued_button4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/rewind-button.png"))); // NOI18N
        jLabel45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel45MouseClicked(evt);
            }
        });

        jLabel130.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel130.setText("LMS / MANAGE BOOKS");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel130)
                    .addComponent(jLabel45)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel130, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab4", jPanel9);

        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/rewind-button.png"))); // NOI18N
        jLabel36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel36MouseClicked(evt);
            }
        });
        jPanel19.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 60, -1, -1));

        jPanel29.setBackground(new java.awt.Color(255, 51, 51));
        jPanel29.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 0, new java.awt.Color(0, 0, 0)));

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));
        jPanel30.setPreferredSize(new java.awt.Dimension(402, 4));

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 258, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        jLabel19.setBackground(new java.awt.Color(0, 0, 0));
        jLabel19.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Literature_100px_1.png"))); // NOI18N
        jLabel19.setText("Book Details");

        jLabel37.setBackground(new java.awt.Color(0, 0, 0));
        jLabel37.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Issued ID :");

        jLabel38.setBackground(new java.awt.Color(0, 0, 0));
        jLabel38.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Book Name :");

        jLabel39.setBackground(new java.awt.Color(0, 0, 0));
        jLabel39.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Student Name :");

        jLabel40.setBackground(new java.awt.Color(0, 0, 0));
        jLabel40.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Issue Date :");

        jLabel41.setBackground(new java.awt.Color(0, 0, 0));
        jLabel41.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Due Date :");

        lbl_issueId1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_issueId1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N

        lbl_bookname1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_bookname1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N

        lbl_issuedate1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_issuedate1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N

        label_txt_bookquantity1.setBackground(new java.awt.Color(255, 255, 255));
        label_txt_bookquantity1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N

        label_bookerror.setBackground(new java.awt.Color(255, 255, 255));
        label_bookerror.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N

        lbl_studentname1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_studentname1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_issueId1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_bookname1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_studentname1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_issuedate1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_txt_bookquantity1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_bookerror, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel19)
                .addGap(6, 6, 6)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(lbl_issueId1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(lbl_bookname1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(lbl_studentname1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(lbl_issuedate1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(label_txt_bookquantity1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(label_bookerror, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel19.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 390, 580));

        jPanel31.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel32.setBackground(new java.awt.Color(255, 51, 51));
        jPanel32.setPreferredSize(new java.awt.Dimension(402, 4));

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        jLabel42.setBackground(new java.awt.Color(255, 51, 51));
        jLabel42.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 51, 51));
        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Books_52px_1.png"))); // NOI18N
        jLabel42.setText("Return Books");

        jLabel43.setBackground(new java.awt.Color(0, 0, 0));
        jLabel43.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 51, 51));
        jLabel43.setText("Student Id :");

        jLabel44.setBackground(new java.awt.Color(0, 0, 0));
        jLabel44.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 51, 51));
        jLabel44.setText("Book Id :");

        txt_bookid1.setBackground(new java.awt.Color(242, 242, 242));
        txt_bookid1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_bookid1.setText("B-0");
        txt_bookid1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 51, 51)));
        txt_bookid1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_bookid1FocusLost(evt);
            }
        });

        txt_studentid1.setBackground(new java.awt.Color(242, 242, 242));
        txt_studentid1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_studentid1.setText("S-0");
        txt_studentid1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 51, 51)));
        txt_studentid1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_studentid1FocusLost(evt);
            }
        });
        txt_studentid1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txt_studentid1MouseReleased(evt);
            }
        });
        txt_studentid1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_studentid1ActionPerformed(evt);
            }
        });
        txt_studentid1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_studentid1KeyReleased(evt);
            }
        });

        issued_button2.setBackground(new java.awt.Color(102, 102, 255));
        issued_button2.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        issued_button2.setForeground(new java.awt.Color(255, 255, 255));
        issued_button2.setText("FIND BOOK");
        issued_button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                issued_button2ActionPerformed(evt);
            }
        });
        issued_button2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                issued_button2KeyReleased(evt);
            }
        });

        issued_button3.setBackground(new java.awt.Color(255, 51, 51));
        issued_button3.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        issued_button3.setForeground(new java.awt.Color(255, 255, 255));
        issued_button3.setText("RETURN BOOK");
        issued_button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                issued_button3ActionPerformed(evt);
            }
        });

        jLabel124.setBackground(new java.awt.Color(0, 0, 0));
        jLabel124.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel124.setForeground(new java.awt.Color(255, 51, 51));
        jLabel124.setText("Return Date :");

        txt_returnDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2));
        txt_returnDate.setDateFormatString("yyyy-MM-dd");
        txt_returnDate.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(issued_button2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(issued_button3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(65, 65, 65))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addGap(65, 65, 65))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel44)
                                    .addComponent(jLabel124, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_returnDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_studentid1)
                                    .addComponent(txt_bookid1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22))))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_studentid1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_bookid1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel124, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_returnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addComponent(issued_button2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(issued_button3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );

        jPanel19.add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 330, 580));

        jLabel30.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel30.setText("LMS / RETURN BOOKS");
        jPanel19.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 22, 205, 32));

        All_Dues_Record_indivdual_Student.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "BOOK_ID", "BOOK_NAME"
            }
        ));
        All_Dues_Record_indivdual_Student.setFuenteHead(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        All_Dues_Record_indivdual_Student.setRowHeight(30);
        All_Dues_Record_indivdual_Student.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                All_Dues_Record_indivdual_StudentMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(All_Dues_Record_indivdual_Student);

        jPanel19.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 340, 410));

        lbl_studentname2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jPanel19.add(lbl_studentname2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 166, 320, 40));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab5", jPanel10);

        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel25.setBackground(new java.awt.Color(102, 102, 255));

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setPreferredSize(new java.awt.Dimension(402, 4));

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 258, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel18.setBackground(new java.awt.Color(0, 0, 0));
        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Literature_100px_1.png"))); // NOI18N
        jLabel18.setText("View Book Records");

        jLabel24.setBackground(new java.awt.Color(0, 0, 0));
        jLabel24.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Issue Date :");

        jLabel25.setBackground(new java.awt.Color(0, 0, 0));
        jLabel25.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("To");

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/rewind-button.png"))); // NOI18N
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });

        SearchUtton.setBackground(new java.awt.Color(255, 51, 51));
        SearchUtton.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        SearchUtton.setForeground(new java.awt.Color(255, 255, 255));
        SearchUtton.setText("Search");
        SearchUtton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchUttonActionPerformed(evt);
            }
        });

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/broom.png"))); // NOI18N
        jLabel28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel28MouseClicked(evt);
            }
        });

        jPanel60.setBackground(new java.awt.Color(255, 51, 51));
        jPanel60.setPreferredSize(new java.awt.Dimension(402, 4));

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 332, Short.MAX_VALUE)
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        issuedate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2));
        issuedate.setDateFormatString("yyyy-MM-dd");
        issuedate.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        DueDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2));
        DueDate.setDateFormatString("yyyy-MM-dd");
        DueDate.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)))
                .addGap(109, 109, 109)
                .addComponent(jLabel28)
                .addGap(243, 243, 243))
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel24)
                .addGap(18, 18, 18)
                .addComponent(issuedate, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel25)
                .addGap(27, 27, 27)
                .addComponent(DueDate, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(SearchUtton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(190, Short.MAX_VALUE))
            .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel25Layout.createSequentialGroup()
                    .addGap(414, 414, 414)
                    .addComponent(jPanel60, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(344, Short.MAX_VALUE)))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel26)))
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(SearchUtton))
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(issuedate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DueDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(35, 35, 35))
            .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel25Layout.createSequentialGroup()
                    .addGap(73, 73, 73)
                    .addComponent(jPanel60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(107, Short.MAX_VALUE)))
        );

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));

        viewallrecords.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Issue ID", "Book Name", "Student Name", "Issue Date", "Due Date", "Status"
            }
        ));
        viewallrecords.setColumnSelectionAllowed(true);
        viewallrecords.setRowHeight(40);
        jScrollPane1.setViewportView(viewallrecords);
        viewallrecords.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/refresh.png"))); // NOI18N
        jLabel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel29MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 941, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jLabel131.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel131.setText("LMS / VIEW RECORDS");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel131, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel131, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel23.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 630));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab6", jPanel11);

        ISSUE_BOOKDETAILS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Issue ID", "Book Name", "Student Name", "Issue Date", "Due Date", "Status"
            }
        ));
        ISSUE_BOOKDETAILS.setRowHeight(40);
        jScrollPane2.setViewportView(ISSUE_BOOKDETAILS);

        jLabel125.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel125.setText("LMS / VIEW RECORDS ISSUED BOOKS ");

        jLabel126.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/rewind-button.png"))); // NOI18N
        jLabel126.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel126MouseClicked(evt);
            }
        });

        jLabel127.setBackground(new java.awt.Color(255, 51, 51));
        jLabel127.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel127.setForeground(new java.awt.Color(255, 51, 51));
        jLabel127.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Books_52px_1.png"))); // NOI18N
        jLabel127.setText("View Issued Books");

        jPanel62.setBackground(new java.awt.Color(255, 51, 51));
        jPanel62.setPreferredSize(new java.awt.Dimension(402, 4));

        javax.swing.GroupLayout jPanel62Layout = new javax.swing.GroupLayout(jPanel62);
        jPanel62.setLayout(jPanel62Layout);
        jPanel62Layout.setHorizontalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 402, Short.MAX_VALUE)
        );
        jPanel62Layout.setVerticalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel126)
                            .addComponent(jLabel125))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel127)
                        .addGap(410, 410, 410))))
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 941, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel127)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel126)))
                .addGap(18, 18, 18)
                .addComponent(jPanel62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab7", jPanel12);

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/rewind-button.png"))); // NOI18N
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
        });

        jLabel34.setBackground(new java.awt.Color(255, 51, 51));
        jLabel34.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 51, 51));
        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Books_52px_1.png"))); // NOI18N
        jLabel34.setText("Defaulter List");

        jPanel28.setBackground(new java.awt.Color(255, 51, 51));
        jPanel28.setPreferredSize(new java.awt.Dimension(402, 4));

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 402, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        table_DefaulterList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Issue ID", "Book Name", "Student Name", "Issue Date", "Due Date", "Status"
            }
        ));
        table_DefaulterList.setRowHeight(40);
        jScrollPane3.setViewportView(table_DefaulterList);

        jLabel122.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel122.setText("LMS / DEFAULTER LIST");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(342, 342, 342))
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel122, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33))
                        .addGap(221, 221, 221)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 941, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel122, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel33)))
                .addGap(18, 18, 18)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab8", jPanel13);

        jLabel120.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel120.setText("LMS / SETTING");

        jLabel121.setBackground(new java.awt.Color(255, 51, 51));
        jLabel121.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/rewind-button.png"))); // NOI18N
        jLabel121.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel121MouseClicked(evt);
            }
        });

        jPanel22.setBackground(new java.awt.Color(204, 204, 255));
        jPanel22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel63.setBackground(new java.awt.Color(0, 123, 255));
        jPanel63.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel132.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel132.setForeground(new java.awt.Color(255, 255, 255));
        jLabel132.setText("Update the Info");

        javax.swing.GroupLayout jPanel63Layout = new javax.swing.GroupLayout(jPanel63);
        jPanel63.setLayout(jPanel63Layout);
        jPanel63Layout.setHorizontalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel132)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel63Layout.setVerticalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel132, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel133.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel133.setText("Username");

        jLabel134.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel134.setText("Full Name");

        jLabel135.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel135.setText("Contact No");

        jLabel136.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel136.setText("Email ID");

        txt_ADMIN_PROFILE_EMAILID.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        txt_ADMIN_PROFILE_EMAILID.setForeground(new java.awt.Color(255, 51, 51));
        txt_ADMIN_PROFILE_EMAILID.setToolTipText("");
        txt_ADMIN_PROFILE_EMAILID.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_ADMIN_PROFILE_EMAILID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ADMIN_PROFILE_EMAILIDActionPerformed(evt);
            }
        });

        jLabel137.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel137.setText("Registration Date");

        txt_ADMIN_PROFILE_CONTACTNO.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        txt_ADMIN_PROFILE_CONTACTNO.setForeground(new java.awt.Color(255, 51, 51));
        txt_ADMIN_PROFILE_CONTACTNO.setToolTipText("");
        txt_ADMIN_PROFILE_CONTACTNO.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_ADMIN_PROFILE_CONTACTNO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ADMIN_PROFILE_CONTACTNOActionPerformed(evt);
            }
        });
        txt_ADMIN_PROFILE_CONTACTNO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_ADMIN_PROFILE_CONTACTNOKeyReleased(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 123, 255));
        jButton4.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Submit");
        jButton4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel138.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel138.setText("Change password ?");
        jLabel138.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel138MouseClicked(evt);
            }
        });

        txt_ADMIN_PROFILE_FULLNAME.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        txt_ADMIN_PROFILE_FULLNAME.setForeground(new java.awt.Color(255, 51, 51));
        txt_ADMIN_PROFILE_FULLNAME.setToolTipText("");
        txt_ADMIN_PROFILE_FULLNAME.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_ADMIN_PROFILE_FULLNAME.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ADMIN_PROFILE_FULLNAMEActionPerformed(evt);
            }
        });

        txt_ADMIN_PROFILE_REGDATE.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        txt_ADMIN_PROFILE_REGDATE.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_ADMIN_PROFILE_REGDATE.setPreferredSize(new java.awt.Dimension(64, 31));

        txt_ADMIN_PROFILE_USERNAME.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        txt_ADMIN_PROFILE_USERNAME.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_ADMIN_PROFILE_USERNAME.setPreferredSize(new java.awt.Dimension(64, 31));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel134, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel136, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel135, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                            .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txt_ADMIN_PROFILE_FULLNAME, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel22Layout.createSequentialGroup()
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                                    .addComponent(jLabel138, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txt_ADMIN_PROFILE_EMAILID, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_ADMIN_PROFILE_CONTACTNO, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel137, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(25, 25, 25)))
                    .addComponent(txt_ADMIN_PROFILE_REGDATE, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel133, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txt_ADMIN_PROFILE_USERNAME, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jPanel63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel133, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_ADMIN_PROFILE_USERNAME, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel134, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_ADMIN_PROFILE_FULLNAME, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel136, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_ADMIN_PROFILE_EMAILID, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel135, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_ADMIN_PROFILE_CONTACTNO, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel137, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_ADMIN_PROFILE_REGDATE, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jLabel138, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        label_Admin_profile_image.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        label_Admin_profile_image.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_Admin_profile_imageMouseClicked(evt);
            }
        });

        jLabel142.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel142.setText("SUB-ADMIN PROFILE");

        jPanel65.setBackground(new java.awt.Color(0, 0, 0));
        jPanel65.setPreferredSize(new java.awt.Dimension(402, 4));

        javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        Update_Img.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        Update_Img.setText("Update_Img");
        Update_Img.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Update_ImgActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 0, 0));
        jLabel4.setText("Note:- If you want to change the image then click on this image icon.");

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel59Layout.createSequentialGroup()
                        .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel65, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel121)
                                .addComponent(jLabel120, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel142, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Update_Img, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(label_Admin_profile_image, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)))
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel59Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel59Layout.createSequentialGroup()
                        .addComponent(jLabel120, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel121)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel142, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel65, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addGroup(jPanel59Layout.createSequentialGroup()
                        .addGap(0, 68, Short.MAX_VALUE)
                        .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel59Layout.createSequentialGroup()
                                .addComponent(label_Admin_profile_image, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Update_Img))
                            .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab9", jPanel14);

        jPanel47.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel80.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel80.setText("ID");

        jLabel81.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel81.setText("USERNAME");

        jLabel82.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel82.setText("FULLNAME");

        jLabel83.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel83.setText("EMAIL");

        jLabel84.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel84.setText("GENDER");

        jLabel85.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel85.setText("DOB");

        profile_image.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        profile_image.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profile_imageMouseClicked(evt);
            }
        });

        IMAGE_UPLOAD.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        IMAGE_UPLOAD.setText("Upload Image");
        IMAGE_UPLOAD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        IMAGE_UPLOAD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IMAGE_UPLOADActionPerformed(evt);
            }
        });

        Gender_radioButton.add(male);
        male.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        male.setText("Male");

        Gender_radioButton.add(female);
        female.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        female.setText("Female");
        female.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                femaleActionPerformed(evt);
            }
        });

        txt_dob.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_dob.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        txt_username.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_username.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_fullname.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_fullname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_email.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        DATA_INSERTED.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        DATA_INSERTED.setText("INSERT");
        DATA_INSERTED.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        DATA_INSERTED.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DATA_INSERTEDActionPerformed(evt);
            }
        });

        DATA_CLEAR.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        DATA_CLEAR.setText("CLEAR");
        DATA_CLEAR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        DATA_CLEAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DATA_CLEARActionPerformed(evt);
            }
        });

        txt_id.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        ComboBox_SecurityQ.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        ComboBox_SecurityQ.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Select Security Question---", "What is your First Elementary School ?", "What is your Favourite food ?", "What is your Child Nick name ?", "What is your Father's name ?", "What is your Favourite Fruit's ?" }));
        ComboBox_SecurityQ.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel86.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel86.setText("SECURITYQ");

        jLabel87.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel87.setText("CONTACTNO");

        jLabel88.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel88.setText("ANSWER");

        txt_contactno.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_contactno.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_answer.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_answer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel89.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel89.setText("PASSWORD");

        txt_password.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_password.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        show_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                show_passwordActionPerformed(evt);
            }
        });

        jLabel90.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel90MouseClicked(evt);
            }
        });

        jLabel91.setToolTipText("");
        jLabel91.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel91MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel47Layout.createSequentialGroup()
                        .addGap(512, 512, 512)
                        .addComponent(IMAGE_UPLOAD, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(profile_image, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel47Layout.createSequentialGroup()
                        .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel83, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel87, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                            .addComponent(jLabel84, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel47Layout.createSequentialGroup()
                                    .addGap(17, 17, 17)
                                    .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_email, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_fullname, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel47Layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel47Layout.createSequentialGroup()
                                            .addComponent(male)
                                            .addGap(18, 18, 18)
                                            .addComponent(female))
                                        .addComponent(txt_contactno, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel47Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel90))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel47Layout.createSequentialGroup()
                        .addComponent(jLabel91)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel47Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel47Layout.createSequentialGroup()
                                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel89, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_answer)
                                    .addGroup(jPanel47Layout.createSequentialGroup()
                                        .addComponent(DATA_INSERTED, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(DATA_CLEAR, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(show_password))
                            .addGroup(jPanel47Layout.createSequentialGroup()
                                .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ComboBox_SecurityQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_dob, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel47Layout.createSequentialGroup()
                        .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel47Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel91))
                                .addGap(6, 6, 6)
                                .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel47Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel90))
                                .addGap(7, 7, 7)
                                .addComponent(txt_fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(txt_contactno, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(6, 6, 6)
                        .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel47Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(IMAGE_UPLOAD, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(profile_image, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(male)
                            .addComponent(female))))
                .addGap(7, 7, 7)
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ComboBox_SecurityQ, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_dob, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_answer, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel47Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel47Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel47Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(show_password)))
                .addGap(26, 26, 26)
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DATA_INSERTED, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DATA_CLEAR, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel114.setBackground(new java.awt.Color(255, 51, 51));
        jLabel114.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/rewind-button.png"))); // NOI18N
        jLabel114.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel114MouseClicked(evt);
            }
        });

        jLabel117.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel117.setText("LMS / ADD-SUBADMIN");

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118))
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel114))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel114)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab10", jPanel15);

        jPanel49.setBackground(new java.awt.Color(255, 255, 255));
        jPanel49.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel50.setBackground(new java.awt.Color(0, 123, 255));
        jPanel50.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel92.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(255, 255, 255));
        jLabel92.setText("CHANGE YOUR PASSWORD");

        jLabel93.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel93MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addComponent(jLabel93)
                .addGap(58, 58, 58)
                .addComponent(jLabel92)
                .addContainerGap(129, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addComponent(jLabel93)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel94.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel94.setText("Current Password");

        jLabel95.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel95.setText("New Password");

        jLabel96.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel96.setText("Confirm Password");

        txt_newpassword.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        txt_newpassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_confirmpassword.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        txt_confirmpassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_confirmpassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_confirmpasswordKeyReleased(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 123, 255));
        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Change");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txt_currentpassword.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        txt_currentpassword.setToolTipText("");
        txt_currentpassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txt_currentpassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_currentpasswordActionPerformed(evt);
            }
        });

        show_pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                show_passActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel96, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_newpassword)
                    .addComponent(txt_confirmpassword, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                    .addComponent(txt_currentpassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(show_pass)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txt_currentpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txt_newpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel96, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_confirmpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(show_pass))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jLabel116.setBackground(new java.awt.Color(255, 51, 51));
        jLabel116.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/rewind-button.png"))); // NOI18N
        jLabel116.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel116MouseClicked(evt);
            }
        });

        jLabel119.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel119.setText("LMS / UPDATE PASSWORD");

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel119)
                    .addComponent(jLabel116))
                .addContainerGap(857, Short.MAX_VALUE))
            .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel48Layout.createSequentialGroup()
                    .addGap(297, 297, 297)
                    .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(297, Short.MAX_VALUE)))
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel116)
                .addContainerGap(543, Short.MAX_VALUE))
            .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel48Layout.createSequentialGroup()
                    .addGap(80, 80, 80)
                    .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(80, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab12", jPanel17);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 1090, 670));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1330, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void nameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nameMouseClicked

    }//GEN-LAST:event_nameMouseClicked
    public void clearDefaulterDetailsInTable() {
        DefaultTableModel model = (DefaultTableModel) table_DefaulterList.getModel();
        model.setRowCount(0);
    }

    public void setDefaulterDetailsInTable() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        String dd = sdf.format(d);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms", "root", "Abhay@9899");
            PreparedStatement pst = con.prepareStatement("Select * from ISSUEBOOK_DETAILS where DUE_DATE < ? and STATUS = ? ");
            pst.setString(1, dd);
            pst.setString(2, "Pending");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String ID = rs.getString("ISSUE_ID");
                String BookName = rs.getString("BOOK_NAME");
                String StudentName = rs.getString("STUDENT_NAME");
                String IssueDate = rs.getString("ISSUE_DATE");
                String DueDate = rs.getString("DUE_DATE");
                String Status = rs.getString("STATUS");
                Object[] obj = {ID, BookName, StudentName, IssueDate, DueDate, Status};
                model = (DefaultTableModel) table_DefaulterList.getModel();
                model.addRow(obj);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error setDefaulterDetailsInTable");
        }
    }

    public void clearIssueBookDetailsTable() {
        DefaultTableModel model = (DefaultTableModel) ISSUE_BOOKDETAILS.getModel();
        model.setRowCount(0);
    }
//clearIssueBookDetailsTable();
//setIssueBookDetailsTable();

    public void setIssueBookDetailsTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms", "root", "Abhay@9899");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from ISSUEBOOK_DETAILS where STATUS='Pending' ");
            while (rs.next()) {
                String ID = rs.getString("ISSUE_ID");
                String BookName = rs.getString("BOOK_NAME");
                String StudentName = rs.getString("STUDENT_NAME");
                String IssueDate = rs.getString("ISSUE_DATE");
                String DueDate = rs.getString("DUE_DATE");
                String Status = rs.getString("STATUS");
                Object[] obj = {ID, BookName, StudentName, IssueDate, DueDate, Status};
                model = (DefaultTableModel) ISSUE_BOOKDETAILS.getModel();
                model.addRow(obj);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error setIssueBookDetailsTable");
        }
    }

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered
        // TODO add your handling code here:
        manage_bookPanel.setBackground(mouseEnterColor);
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        // TODO add your handling code here:
        manage_bookPanel.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel6MouseExited

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered
        // TODO add your handling code here:
        manage_studentsPanel.setBackground(mouseEnterColor);
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        // TODO add your handling code here:
        manage_studentsPanel.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel7MouseExited

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseEntered
        // TODO add your handling code here:\
        issue_bookPanel.setBackground(mouseEnterColor);
    }//GEN-LAST:event_jLabel8MouseEntered

    private void jLabel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseExited
        // TODO add your handling code here:
        issue_bookPanel.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel8MouseExited

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(4);
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseEntered
        // TODO add your handling code here:
        return_bookpanel.setBackground(mouseEnterColor);
    }//GEN-LAST:event_jLabel9MouseEntered

    private void jLabel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseExited
        // TODO add your handling code here:
        return_bookpanel.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel9MouseExited

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseEntered
        // TODO add your handling code here:
        viewrecords_panel.setBackground(mouseEnterColor);
    }//GEN-LAST:event_jLabel10MouseEntered

    private void jLabel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseExited
        // TODO add your handling code here:
        viewrecords_panel.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel10MouseExited

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(6);
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseEntered
        // TODO add your handling code here:
        viewissuedbook_panel.setBackground(mouseEnterColor);
    }//GEN-LAST:event_jLabel11MouseEntered

    private void jLabel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseExited
        // TODO add your handling code here:
        viewissuedbook_panel.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel11MouseExited

    private void jlabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlabel12MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(7);
    }//GEN-LAST:event_jlabel12MouseClicked

    private void jlabel12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlabel12MouseEntered
        // TODO add your handling code here:
        defaulterlist_panel.setBackground(mouseEnterColor);

    }//GEN-LAST:event_jlabel12MouseEntered

    private void jlabel12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlabel12MouseExited
        // TODO add your handling code here:
        defaulterlist_panel.setBackground(mouseExitColor);
    }//GEN-LAST:event_jlabel12MouseExited

    private void defaulterlist_panelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_defaulterlist_panelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_defaulterlist_panelMouseClicked

    private void defaulterlist_panelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_defaulterlist_panelMouseEntered
        // TODO add your handling code here:
        //     defaulterlist_panel.setBackground(mouseEnterColor);
    }//GEN-LAST:event_defaulterlist_panelMouseEntered

    private void defaulterlist_panelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_defaulterlist_panelMouseExited
        // TODO add your handling code here:
        //   defaulterlist_panel.setBackground(mouseExitColor);
    }//GEN-LAST:event_defaulterlist_panelMouseExited

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(8);

    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseEntered
        // TODO add your handling code here:
        setting_panel.setBackground(mouseEnterColor);
    }//GEN-LAST:event_jLabel13MouseEntered

    private void jLabel13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseExited
        // TODO add your handling code here:
        setting_panel.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel13MouseExited

    private void setting_panelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_setting_panelMouseEntered
        // TODO add your handling code here:
        setting_panel.setBackground(mouseEnterColor);

    }//GEN-LAST:event_setting_panelMouseEntered

    private void setting_panelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_setting_panelMouseExited
        // TODO add your handling code here:
        setting_panel.setBackground(mouseExitColor);
    }//GEN-LAST:event_setting_panelMouseExited

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        JFrame frame = new JFrame("EXIT");
        if (JOptionPane.showConfirmDialog(frame, "Confirm you want to Logout", "LMS Admin Dashboard",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            LOGIN_PAGE home = new LOGIN_PAGE();
            home.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel14MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseEntered
        // TODO add your handling code here:
        logout_panel.setBackground(mouseEnterColor1);
    }//GEN-LAST:event_jLabel14MouseEntered

    private void jLabel14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseExited
        // TODO add your handling code here:
        logout_panel.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel14MouseExited

    private void logout_panelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logout_panelMouseEntered
        // TODO add your handling code here:
        logout_panel.setBackground(mouseEnterColor1);
    }//GEN-LAST:event_logout_panelMouseEntered

    private void logout_panelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logout_panelMouseExited
        // TODO add your handling code here:
        logout_panel.setBackground(mouseExitColor);
    }//GEN-LAST:event_logout_panelMouseExited

// issue books details 4742-4864
    public boolean Issuevalidation() {
        if (txt_bookid.getText().equals("") && txt_studentid.getText().equals("") && txt_issuedate.getDate() == null && txt_duedate.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Please Enter All Details");
            return false;
        }

        if (txt_bookid.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Enter Book ID");
            return false;
        }
        if (txt_studentid.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Enter Student ID");
            return false;
        }
        if (txt_issuedate.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Please Enter a Book issue Date");
            return false;
        }
        if (txt_issuedate.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Please Enter a Book issue Date");
            return false;
        }
        if (txt_duedate.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Please Enter a Book Due Date");
            return false;
        }
        return true;
    }

    public void updateBookCount1() {
        String BookID = txt_bookid.getText();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
            String sql = "update BOOK_DETAILS set QUANTITY=QUANTITY-1 where BOOK_ID=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, BookID);
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                JOptionPane.showMessageDialog(null, "Book count updated");
                int initialCount = Integer.parseInt(label_txt_bookquantity.getText());
                label_txt_bookquantity.setText(Integer.toString(initialCount - 1));

            } else {
                JOptionPane.showMessageDialog(null, "can't update book count");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Update book count error");
        }
    }

     public void Issued_ID(){
    try{
           Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
            String sql = "Select max(ISSUE_ID) from issuebook_details";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            rs.getString("Max(ISSUE_ID)");
            if (rs.getString("Max(ISSUE_ID)") == null) {
              txt_issueid.setText("I-01");
            } else {
                Long id = Long.parseLong(rs.getString("Max(ISSUE_ID)").substring(2, rs.getString("Max(ISSUE_ID)").length()));
                id++;
               txt_issueid.setText("I-" + String.format("%02d", id));
            }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
    }
    public boolean issueBook() {
        boolean issued = false;
        String IssueId=txt_issueid.getText();
        String BookID = txt_bookid.getText();
        String StudentID = txt_studentid.getText();
        String BookName = label_txt_bookname.getText();
        String StudentName = label_txt_studentname.getText();
        Date date = txt_issuedate.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String uIssueDate = sdf.format(date);
        Date date1 = txt_duedate.getDate();
        String uDueDate = sdf.format(date1);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
            String sql = "insert into ISSUEBOOK_DETAILS (ISSUE_ID,BOOK_ID,BOOK_NAME, STUDENT_ID,STUDENT_NAME,ISSUE_DATE,DUE_DATE,STATUS)"
                    + "values(?,?,?,?,?,?,?,?) ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, IssueId);
            pst.setString(2, BookID);
            pst.setString(3, BookName);
            pst.setString(4, StudentID);
            pst.setString(5, StudentName);
            pst.setString(6, uIssueDate);
            pst.setString(7, uDueDate);
            pst.setString(8, "Pending");
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                issued = true;
            } else {
                issued = false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return issued;
    }

    public boolean isAlreadyIssued() {
        boolean isAlreadyIssued = false;
        String BookID = txt_bookid.getText();
        String StudentID = txt_studentid.getText();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
            String sql = "select * from ISSUEBOOK_DETAILS where BOOK_ID=? and STUDENT_ID=? and STATUS=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, BookID);
            pst.setString(2, StudentID);
            pst.setString(3, "PENDING");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                isAlreadyIssued = true;
            } else {
                isAlreadyIssued = false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "isAlreadyIssued error");
        }
        return isAlreadyIssued;
    }

    public void getStudentDetails1() {
        String StudentID = txt_studentid.getText();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms", "root", "Abhay@9899");
            PreparedStatement pst = con.prepareStatement("Select * from STUDENT_DETAILS where STUDENT_ID=?");
            pst.setString(1, StudentID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                label_txt_studentid.setText(rs.getString("STUDENT_ID"));
                label_txt_studentname.setText(rs.getString("NAME"));
                label_txt_studentcourse.setText(rs.getString("COURSE"));
                label_txt_studentbranch.setText(rs.getString("BRANCH"));
                lbl_StudentError.setText(" ");
            } else {
                lbl_StudentError.setText("Invalid StudentID");
              
        label_txt_studentid.setText("");
        label_txt_studentname.setText("");
        label_txt_studentcourse.setText("");
        label_txt_studentbranch.setText("");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "getStudentDetails1");
        }
    }

    public void getBookDetails1() {
        String BookID = txt_bookid.getText();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms", "root", "Abhay@9899");
            PreparedStatement pst = con.prepareStatement("Select * from BOOK_DETAILS where BOOK_ID=?");
            pst.setString(1, BookID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                label_txt_bookid.setText(rs.getString("BOOK_ID"));
                label_txt_bookname.setText(rs.getString("BOOK_NAME"));
                label_txt_bookauthor.setText(rs.getString("AUTHOR"));
                label_txt_bookquantity.setText(rs.getString("QUANTITY"));
                lbl_BookError.setText("");
            } else {
         lbl_BookError.setText("Invalid BookID");
        label_txt_bookid.setText("");
        label_txt_bookname.setText("");
        label_txt_bookauthor.setText("");
        label_txt_bookquantity.setText("");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "getBookDetails1");
        }
    }

    public boolean Returnvalidation() {
        if (txt_bookid1.getText().equals("") && txt_studentid1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Enter All Details");
            return false;
        }
        if (txt_bookid1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Enter Book ID");
            return false;
        }
        if (txt_studentid1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Enter Student ID");
            return false;
        }
        return true;
    }

    public void updateBookCount() {
        String BookID = txt_bookid1.getText();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
            String sql = "update BOOK_DETAILS set QUANTITY=QUANTITY+1 where BOOK_ID=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, BookID);
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                JOptionPane.showMessageDialog(null, "Book count updated");

            } else {
                JOptionPane.showMessageDialog(null, "can't update book count");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
   public void date(){
    Date d=new Date();      //     import java.util.Date;
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    txt_returnDate.setDate(d);
     }
    public boolean returnBook() {
        boolean isReturned = false;
        String BookID = txt_bookid1.getText();
        String StudentID = txt_studentid1.getText();
        Date date=new Date();
        SimpleDateFormat temp=new SimpleDateFormat("yyyy-MM-dd");   // a stands for Am
        String R_Date = temp.format(date);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
            String sql = "update ISSUEBOOK_DETAILS set STATUS=? , RETURN_DATE=? where STUDENT_ID=? and BOOK_ID=? and STATUS=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "Returned");
            pst.setString(2,R_Date);
            pst.setString(3, StudentID);
            pst.setString(4, BookID);
            pst.setString(5, "Pending");
            int rowCount = pst.executeUpdate();
            if (rowCount > 0){   
                clearIndividual_Due_Many_books();
                Individual_Due_Many_books();
                isReturned = true;
          
            } else {
                isReturned = false;
                label_bookerror.setText("Incorrect Details");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return isReturned;
    }
 public void getBookIssueBookDetails() {
        //boolean success=false;
        String BookID = txt_bookid1.getText();
        String StudentID = txt_studentid1.getText();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
            String sql = "select * from ISSUEBOOK_DETAILS where BOOK_ID=? and STUDENT_ID =? and STATUS=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, BookID);
            pst.setString(2, StudentID);
            pst.setString(3, "Pending");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                // success=true;
                lbl_issueId1.setText(rs.getString("ISSUE_ID"));
                lbl_bookname1.setText(rs.getString("BOOK_NAME"));
                lbl_studentname1.setText(rs.getString("STUDENT_NAME"));
                lbl_studentname2.setText(rs.getString("STUDENT_NAME"));
                lbl_issuedate1.setText(rs.getString("ISSUE_DATE"));
                label_txt_bookquantity1.setText(rs.getString("DUE_DATE"));
                label_bookerror.setText("");
                date();
            } else {
                label_bookerror.setText("No Record Found");
                //clearIndividual_Due_Many_books();
                lbl_issueId1.setText("");
                lbl_bookname1.setText("");
                lbl_studentname1.setText("");
                // lbl_studentname2.setText("");
                lbl_issuedate1.setText("");
                label_txt_bookquantity1.setText("");
                txt_studentid.setText("");
                txt_bookid.setText("");
              
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
//return success;
    }
      public void Individual_Due_Many_books() {
        String StudentID = txt_studentid1.getText();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
            String sql = "select * from ISSUEBOOK_DETAILS where  STUDENT_ID =? and STATUS= ? ";
             NameId(); 
            PreparedStatement pst = con.prepareStatement(sql);           
            pst.setString(1, StudentID);
            pst.setString(2, "Pending");
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                // if (lbl_studentname2.getText().isEmpty()) {
                 //   lbl_studentname2.setText(rs.getString("STUDENT_NAME"));
                //}
                String BookID = rs.getString("BOOK_ID");
                String BookName = rs.getString("BOOK_NAME");
                Object[] obj = {BookID, BookName};
                model = (DefaultTableModel) All_Dues_Record_indivdual_Student.getModel();
                model.addRow(obj);
            } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void NameId(){
 try {
            String StudentID = txt_studentid1.getText();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
            String sql = "select  NAME from student_details where  STUDENT_ID =?";
            PreparedStatement pst = con.prepareStatement(sql);           
            pst.setString(1, StudentID);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                      lbl_studentname2.setText(rs.getString("NAME"));
             }else{
             lbl_studentname2.setText("Invalid Student ID");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
//        public void BookId(){
// try {
//            String BookID = txt_bookid1.getText();
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
//            String sql = "select  BOOK_ID from book_details where  BOOK_ID =?";
//            PreparedStatement pst = con.prepareStatement(sql);           
//            pst.setString(1, BookID);
//            ResultSet rs = pst.executeQuery();
//            if(rs.next()) {
//                      lbl_studentname2.setText(rs.getString("NAME"));
//             }else{
//             JOptionPane.showMessageDialog(null, "Student ID Does't Exists");
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }
      public void clearIndividual_Due_Many_books() {
        DefaultTableModel model = (DefaultTableModel) All_Dues_Record_indivdual_Student.getModel();
        model.setRowCount(0);
        lbl_studentname2.setText("");
    }
    public void clearISSUE_BOOKDETAILS() {
        DefaultTableModel model = (DefaultTableModel) ISSUE_BOOKDETAILS.getModel();
        model.setRowCount(0);
    }
    public void setViewAllRecordsTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms", "root", "Abhay@9899");
            Statement st = con.createStatement();
            java.sql.ResultSet rs = st.executeQuery("Select * from ISSUEBOOK_DETAILS");
            while (rs.next()) {
                String ID = rs.getString("ISSUE_ID");
                String BookName = rs.getString("BOOK_NAME");
                String StudentName = rs.getString("STUDENT_NAME");
                String IssueDate = rs.getString("ISSUE_DATE");
                String DueDate = rs.getString("DUE_DATE");
                String Status = rs.getString("STATUS");
                Object[] obj = {ID, BookName, StudentName, IssueDate, DueDate, Status};
                model = (DefaultTableModel) viewallrecords.getModel();
                model.addRow(obj);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

 
public void Search(){
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
Date date=issuedate.getDate();
String FromDate=sdf.format(date);
Date date1=DueDate.getDate();
String ToDate=sdf.format(date1);
try{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms","root","Abhay@9899");
String sql="select * from issuebook_details where ISSUE_DATE BETWEEN ? and ?";
PreparedStatement pst=con.prepareStatement(sql);
pst.setString(1,FromDate);
pst.setString(2,ToDate);
ResultSet rs=pst.executeQuery();
while(rs.next() ){
                String ID=rs.getString("ISSUE_ID");
                String BookName=rs.getString("BOOK_NAME");
                String StudentName=rs.getString("STUDENT_NAME");
                String IssueDate=rs.getString("ISSUE_DATE");
                String DueDate=rs.getString("DUE_DATE");
                String Status=rs.getString("STATUS");
                Object[] obj={ID, BookName, StudentName, IssueDate, DueDate, Status};
                 model = (DefaultTableModel)viewallrecords.getModel();
                model.addRow(obj);
}
}catch(Exception e){
JOptionPane.showMessageDialog(null,e);
}
}
    public void clearViewAllRecordsTabel() {
        DefaultTableModel model = (DefaultTableModel) viewallrecords.getModel();
        model.setRowCount(0);
    }   
    int id = 0;

    public int getId() {
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
            String sql = "Select max(ID) from admin_table";
            Statement st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {

                id = rs.getInt(1);
                id++;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return id;
    }    

   String Staff_Username, Saff_Fullname, Staff_EmailId, Staff_ContactNo, Staff_Password;


    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel116MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel116MouseClicked
        jTabbedPane1.setSelectedIndex(8);
        txt_currentpassword.setText("");
        txt_newpassword.setText("");
        txt_confirmpassword.setText("");
    }//GEN-LAST:event_jLabel116MouseClicked

    private void show_passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_show_passActionPerformed
        // TODO add your handling code here:
        if (show_pass.isSelected()) {
            txt_newpassword.setEchoChar((char) 0);
            txt_confirmpassword.setEchoChar((char) 0);
        } else {
            txt_newpassword.setEchoChar('*');
            txt_confirmpassword.setEchoChar('*');
        }
    }//GEN-LAST:event_show_passActionPerformed

    private void txt_currentpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_currentpasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_currentpasswordActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String Username = name.getText();
        String CurrentPassword = txt_currentpassword.getText();
        String NewPassword = txt_newpassword.getText();
        String ConfirmPassword = txt_confirmpassword.getText();
        if (txt_currentpassword.getText().equals("") && txt_newpassword.getText().equals("") && txt_confirmpassword.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Input a All the Details");

        } else if (txt_currentpassword.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Input a Current Password");

        } else if (txt_newpassword.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Input New Password");

        } else if (txt_confirmpassword.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Input a Confirm Password");

        } else {
            if (!(new String(txt_newpassword.getPassword()).equals(new String(txt_confirmpassword.getPassword())))) {
                JOptionPane.showMessageDialog(this, "Your Confirm Password Does Not Match!");
            } else {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("select USERNAME from ADMIN_TABLE  where  PASSWORD='" + CurrentPassword + "'");
                    if (rs.next()) {
                        st.executeUpdate("update ADMIN_TABLE set PASSWORD='" + ConfirmPassword + "' where USERNAME='" + Username + "' ");
                        JOptionPane.showMessageDialog(null, "Sub-Admin Password Updated Successfully");
                        txt_currentpassword.setText("");
                        txt_newpassword.setText("");
                        txt_confirmpassword.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Please Write correct Current Password");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_confirmpasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_confirmpasswordKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_confirmpasswordKeyReleased

    private void jLabel93MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel93MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel93MouseClicked

    private void txt_ADMIN_PROFILE_FULLNAMEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ADMIN_PROFILE_FULLNAMEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ADMIN_PROFILE_FULLNAMEActionPerformed

    private void jLabel138MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel138MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(10);
    }//GEN-LAST:event_jLabel138MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        String Admin = txt_ADMIN_PROFILE_USERNAME.getText();
        String Full = txt_ADMIN_PROFILE_FULLNAME.getText();
        String Email = txt_ADMIN_PROFILE_EMAILID.getText();
        String Contact = txt_ADMIN_PROFILE_CONTACTNO.getText();
        String RegDATE = txt_ADMIN_PROFILE_REGDATE.getText();
        if (txt_ADMIN_PROFILE_FULLNAME.getText().equals("") && txt_ADMIN_PROFILE_EMAILID.getText().equals("") && txt_ADMIN_PROFILE_CONTACTNO.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Input a All the Details");

        } else if (txt_ADMIN_PROFILE_FULLNAME.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Input a FullName");

        } else if (txt_ADMIN_PROFILE_EMAILID.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Input a Email Id");

        } else if (txt_ADMIN_PROFILE_CONTACTNO.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Input a Contact No");

        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginauth", "root", "Abhay@9899");
                String sql = "update table_admin set Admin_Name=?,EMAILID=? ,ContactNo=?,Admin_RegDate=? where Admin_Username=?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, Full);
                pst.setString(2, Email);
                pst.setString(3, Contact);
                pst.setString(4, RegDATE);
                pst.setString(5, Admin);
                int rowCount = pst.executeUpdate();
                if (rowCount > 0) {
                    JOptionPane.showMessageDialog(null, "Admin Profile  Updated");
                } else {
                    JOptionPane.showMessageDialog(null, "Admin Profile Updated Failed");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txt_ADMIN_PROFILE_CONTACTNOKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ADMIN_PROFILE_CONTACTNOKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ADMIN_PROFILE_CONTACTNOKeyReleased

    private void txt_ADMIN_PROFILE_CONTACTNOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ADMIN_PROFILE_CONTACTNOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ADMIN_PROFILE_CONTACTNOActionPerformed

    private void txt_ADMIN_PROFILE_EMAILIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ADMIN_PROFILE_EMAILIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ADMIN_PROFILE_EMAILIDActionPerformed

    private void jLabel121MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel121MouseClicked
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jLabel121MouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jLabel33MouseClicked

    private void jLabel126MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel126MouseClicked
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jLabel126MouseClicked

    private void jLabel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseClicked
        // TODO add your handling code here:
        clearViewAllRecordsTabel();
        setViewAllRecordsTable();
    }//GEN-LAST:event_jLabel29MouseClicked

    private void jLabel28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel28MouseClicked
        // TODO add your handling code here:
        issuedate.setDate(null);
        DueDate.setDate(null);
        setViewAllRecordsTable();
    }//GEN-LAST:event_jLabel28MouseClicked

    private void SearchUttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchUttonActionPerformed
        // TODO add your handling code here:
        if (issuedate.getDate() != null && DueDate.getDate() != null) {
            clearViewAllRecordsTabel();
            Search();
        } else {
            JOptionPane.showMessageDialog(null, "Please Select  a Date");
        }
    }//GEN-LAST:event_SearchUttonActionPerformed

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        jTabbedPane1.setSelectedIndex(0);
        issuedate.setDate(null);
        DueDate.setDate(null);
    }//GEN-LAST:event_jLabel26MouseClicked

    private void All_Dues_Record_indivdual_StudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_All_Dues_Record_indivdual_StudentMouseClicked
        // TODO add your handling code here:
        int selectedRow = All_Dues_Record_indivdual_Student.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) All_Dues_Record_indivdual_Student.getModel();
        txt_studentid2.setText(model.getValueAt(selectedRow, 0).toString());
        txt_studentname.setText(model.getValueAt(selectedRow, 1).toString());
        combo_course.setSelectedItem(model.getValueAt(selectedRow, 2).toString());
        combo_branch.setSelectedItem(model.getValueAt(selectedRow, 3).toString());
    }//GEN-LAST:event_All_Dues_Record_indivdual_StudentMouseClicked

    private void issued_button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_issued_button3ActionPerformed
        int opt = JOptionPane.showConfirmDialog(null, "Are You want to Return this Book", "Delete", JOptionPane.YES_NO_OPTION);
        if (opt == 0) {
            if (Returnvalidation() == true) {
                if (returnBook() == true) {
                    JOptionPane.showMessageDialog(null, "Book Return Sucessfully");
                    updateBookCount();
                    clearViewAllRecordsTabel();
                    setViewAllRecordsTable();
                    clearIssueBookDetailsTable();
                    setIssueBookDetailsTable();
                    clearDefaulterDetailsInTable();
                    setDefaulterDetailsInTable();
                    lbl_issueId1.setText("");
                    lbl_bookname1.setText("");
                    lbl_studentname1.setText("");
                    lbl_issuedate1.setText("");
                    label_txt_bookquantity1.setText("");
                    txt_studentid1.setText("S-0");
                    txt_bookid1.setText("B-0");
                    txt_returnDate.setDate(null);

                } else {
                    JOptionPane.showMessageDialog(null, "Book Return Failed");
                }
            } else {
                // JOptionPane.showMessageDialog(null,"Incorrect Details");
            }
        }
    }//GEN-LAST:event_issued_button3ActionPerformed

    private void issued_button2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_issued_button2KeyReleased

    }//GEN-LAST:event_issued_button2KeyReleased

    private void issued_button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_issued_button2ActionPerformed
        if (Returnvalidation() == true) {
            clearIndividual_Due_Many_books();
            Individual_Due_Many_books();
            getBookIssueBookDetails();

        } else {
        }
    }//GEN-LAST:event_issued_button2ActionPerformed

    private void txt_studentid1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_studentid1KeyReleased
        clearIndividual_Due_Many_books();
        date();
        Individual_Due_Many_books();
    }//GEN-LAST:event_txt_studentid1KeyReleased

    private void txt_studentid1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_studentid1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_studentid1ActionPerformed

    private void txt_studentid1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_studentid1MouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_studentid1MouseReleased

    private void txt_studentid1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_studentid1FocusLost
        //
    }//GEN-LAST:event_txt_studentid1FocusLost

    private void txt_bookid1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_bookid1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bookid1FocusLost

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked
        jTabbedPane1.setSelectedIndex(0);
        lbl_issueId1.setText("");
        lbl_bookname1.setText("");
        lbl_studentname1.setText("");
        lbl_issuedate1.setText("");
        label_txt_bookquantity1.setText("");
        txt_studentid1.setText("S-0");
        txt_bookid1.setText("B-0");
        label_bookerror.setText("");
        txt_returnDate.setDate(null);
        lbl_studentname2.setText("");
        clearIndividual_Due_Many_books();

    }//GEN-LAST:event_jLabel36MouseClicked

    private void jLabel45MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseClicked
        jTabbedPane1.setSelectedIndex(0);
        label_txt_bookid.setText("");
        label_txt_bookname.setText("");
        label_txt_bookauthor.setText("");
        label_txt_bookquantity.setText("");
        lbl_BookError.setText("");
        label_txt_studentid.setText("");
        label_txt_studentname.setText("");
        label_txt_studentcourse.setText("");
        label_txt_studentbranch.setText("");
        lbl_StudentError.setText("");
        txt_bookid.setText("");
        txt_studentid.setText("");
        txt_issuedate.setDate(null);
        txt_duedate.setDate(null);
    }//GEN-LAST:event_jLabel45MouseClicked

    private void txt_studentidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_studentidFocusLost
        // TODO add your handling code here:
        if (!txt_studentid.getText().equals("")) {
            getStudentDetails1();
            //  lbl_StudentError.setText("");
        }
    }//GEN-LAST:event_txt_studentidFocusLost

    private void txt_bookidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_bookidFocusLost
        // TODO add your handling code here:
        if (!txt_bookid.getText().equals("")) {
            getBookDetails1();
            // lbl_BookError.setText("");

        }
    }//GEN-LAST:event_txt_bookidFocusLost

    private void issued_button4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_issued_button4ActionPerformed
        int opt = JOptionPane.showConfirmDialog(null, "Please Confirm for issue this Book", "Delete", JOptionPane.YES_NO_OPTION);
        if (opt == 0) {
            if (Issuevalidation() == true) {
                if (label_txt_bookquantity.getText().equals("0")) {
                    JOptionPane.showMessageDialog(null, "Book is not available");
                } else {
                    if (isAlreadyIssued() == false) {
                        if (issueBook() == true) {
                            JOptionPane.showMessageDialog(null, "Book Issued Succesfully");
                            updateBookCount1();
                            clearViewAllRecordsTabel();
                            setViewAllRecordsTable();
                            clearIssueBookDetailsTable();
                            setIssueBookDetailsTable();
                            clearDefaulterDetailsInTable();
                            setDefaulterDetailsInTable();
                            setDataToIssueBook();
                            label_txt_bookid.setText("");
                            label_txt_bookname.setText("");
                            label_txt_bookauthor.setText("");
                            label_txt_bookquantity.setText("");
                            lbl_BookError.setText("");
                            label_txt_studentid.setText("");
                            label_txt_studentname.setText("");
                            label_txt_studentcourse.setText("");
                            label_txt_studentbranch.setText("");
                            lbl_StudentError.setText("");
                            txt_bookid.setText("");
                            txt_studentid.setText("");
                            txt_issuedate.setDate(null);
                            txt_duedate.setDate(null);
                            Issued_ID();
                        } else {
                            JOptionPane.showMessageDialog(null, "can't isued the Book");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "This Student already has this book");

                    }

                }
            }
        }
    }//GEN-LAST:event_issued_button4ActionPerformed

    private void jLabel129MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel129MouseClicked
        jTabbedPane1.setSelectedIndex(0);
        txt_studentid2.setText("");
        txt_studentname.setText("");
        combo_course.setSelectedItem("---SELECT---");
        combo_branch.setSelectedItem("---SELECT---");
        Auto_StudentID();
    }//GEN-LAST:event_jLabel129MouseClicked

    private void table_studentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_studentMouseClicked
        // TODO add your handling code here:
        int selectedRow = table_student.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) table_student.getModel();
        txt_studentid2.setText(model.getValueAt(selectedRow, 0).toString());
        txt_studentname.setText(model.getValueAt(selectedRow, 1).toString());
        combo_course.setSelectedItem(model.getValueAt(selectedRow, 2).toString());
        combo_branch.setSelectedItem(model.getValueAt(selectedRow, 3).toString());
    }//GEN-LAST:event_table_studentMouseClicked

    private void jLabel66MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel66MouseClicked
        // TODO add your handling code here:
        clearStudentTabel();
        setStudentDetailsTable();
    }//GEN-LAST:event_jLabel66MouseClicked

    private void jLabel65MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel65MouseClicked
        // TODO add your handling code here:
        clearStudentTabel();
        txt_studentid2.setText("");
        txt_studentname.setText("");
        combo_course.setSelectedItem("---SELECT---");
        combo_branch.setSelectedItem("---SELECT---");
        Auto_StudentID();
    }//GEN-LAST:event_jLabel65MouseClicked

    private void jLabel63MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel63MouseClicked
        // TODO add your handling code here:
        txt_studentid2.setText("");
        txt_studentname.setText("");
        combo_course.setSelectedItem("---SELECT---");
        combo_branch.setSelectedItem("---SELECT---");
        Auto_StudentID();
    }//GEN-LAST:event_jLabel63MouseClicked

    private void combo_courseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_courseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_courseActionPerformed

    private void delete_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_buttonActionPerformed
        // TODO add your handling code here:
        int opt = JOptionPane.showConfirmDialog(null, "Are You want Delete", "Delete", JOptionPane.YES_NO_OPTION);
        if (opt == 0) {
            DefaultTableModel tblmodel = (DefaultTableModel) table_student.getModel();
            if (table_student.getSelectedRowCount() == 1) {
                tblmodel.removeRow(table_student.getSelectedRow());
                if (deleteStudent() == true) {
                    JOptionPane.showMessageDialog(null, "Student Deletion");
                    txt_studentid2.setText("");
                    txt_studentname.setText("");
                    combo_course.setSelectedItem("---SELECT---");
                    combo_branch.setSelectedItem("---SELECT---");
                    clearStudentTabel();
                    setStudentDetailsTable();
                    Auto_StudentID();
                    showPieChart();
                    setDataToTotalBooks() ;

                } else {
                    JOptionPane.showMessageDialog(null, "Student Deletion Failed");
                }
            } else {
                if (table_student.getSelectedRowCount() == 1) {
                    JOptionPane.showMessageDialog(null, "Table is Empty");
                } else {
                    JOptionPane.showMessageDialog(null, "Please Select a single Row in table for Delete");
                }
            }
        }
    }//GEN-LAST:event_delete_buttonActionPerformed

    private void Update_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Update_buttonActionPerformed
        // TODO add your handling code here:
        DefaultTableModel tblmodel = (DefaultTableModel) table_student.getModel();
        if (table_student.getSelectedRowCount() == 1) {
            tblmodel.removeRow(table_student.getSelectedRow());
            if (updateStudent() == true) {
                JOptionPane.showMessageDialog(null, "Student Updated");
                txt_studentid2.setText("");
                txt_studentname.setText("");
                combo_course.setSelectedItem("---SELECT---");
                combo_branch.setSelectedItem("---SELECT---");
                clearStudentTabel();
                setStudentDetailsTable();
                Auto_StudentID();
                showPieChart();
                setDataToTotalBooks() ;
            } else {
                JOptionPane.showMessageDialog(null, "Student Updated Failed");
            }
        } else {
            if (table_student.getSelectedRowCount() == 1) {
                JOptionPane.showMessageDialog(null, "Table is Empty");
            } else {
                JOptionPane.showMessageDialog(null, "Please Select a single Row in a table for Updation");
            }
        }
    }//GEN-LAST:event_Update_buttonActionPerformed

    private void Add_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Add_buttonActionPerformed
        // TODO add your handling code here:
        if (Studentvalidation() == true) {
            if (checkupDuplicateUser() == false) {
                int opt = JOptionPane.showConfirmDialog(null, "Are You want to Added?", "Added", JOptionPane.YES_NO_OPTION);
                if (opt == 0) {
                    if (addStudent() == true) {
                        JOptionPane.showMessageDialog(null, "Student Added");
                        clearStudentTabel();
                        setStudentDetailsTable();
                        Auto_StudentID();
                        setDataToStudent();
                        showPieChart();
                        setDataToTotalBooks() ;
                    } else {

                    }
                } else {
                }
            } else {
                JOptionPane.showMessageDialog(null, "Student ID Already Exists ");
                JOptionPane.showMessageDialog(null, "StudentAdded Failed");
            }

        }
    }//GEN-LAST:event_Add_buttonActionPerformed

    private void txt_studentnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_studentnameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_studentnameFocusLost

    private void jLabel67MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel67MouseClicked
        jTabbedPane1.setSelectedIndex(0);
        txt_bookid2.setText("");
        txt_bookname.setText("");
        txt_authorname.setText("");
        txt_quantity.setText("");
        Auto_BookID();
    }//GEN-LAST:event_jLabel67MouseClicked

    private void table_ManageBooksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_ManageBooksMouseClicked
        // TODO add your handling code here:
        int selectedRow = table_ManageBooks.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) table_ManageBooks.getModel();
        txt_bookid2.setText(model.getValueAt(selectedRow, 0).toString());
        txt_bookname.setText(model.getValueAt(selectedRow, 1).toString());
        txt_authorname.setText(model.getValueAt(selectedRow, 2).toString());
        txt_quantity.setText(model.getValueAt(selectedRow, 3).toString());
    }//GEN-LAST:event_table_ManageBooksMouseClicked

    private void jLabel79MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel79MouseClicked

        clearTabelBooks();
        setBookDetailsTable();
    }//GEN-LAST:event_jLabel79MouseClicked

    private void jLabel78MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel78MouseClicked
        // TODO add your handling code here:
        clearTabelBooks();
        txt_bookid2.setText("");
        txt_bookname.setText("");
        txt_authorname.setText("");
        txt_quantity.setText("");
        Auto_BookID();
    }//GEN-LAST:event_jLabel78MouseClicked

    private void jLabel76MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel76MouseClicked
        // TODO add your handling code here:
        txt_bookid2.setText("");
        txt_bookname.setText("");
        txt_authorname.setText("");
        txt_quantity.setText("");
        Auto_BookID();
    }//GEN-LAST:event_jLabel76MouseClicked

    private void delete_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_button1ActionPerformed
        // TODO add your handling code here:
        int opt = JOptionPane.showConfirmDialog(null, "Are You want to Delete?", "Delete", JOptionPane.YES_NO_OPTION);
        if (opt == 0) {
            DefaultTableModel tblmodel = (DefaultTableModel) table_ManageBooks.getModel();
            if (table_ManageBooks.getSelectedRowCount() == 1) {
                tblmodel.removeRow(table_ManageBooks.getSelectedRow());
                if (deleteBook() == true) {
                    JOptionPane.showMessageDialog(null, "Book Deletion");
                    txt_bookid2.setText("");
                    txt_bookname.setText("");
                    txt_authorname.setText("");
                    txt_quantity.setText("");
                    clearTabelBooks();
                    setBookDetailsTable();
                    Auto_BookID();
                    setDataToTotalBooks();
                    setDataToBooks();
                    showPieChart();
                    setDataToBooks();
                } else {
                    JOptionPane.showMessageDialog(null, "Book Deletion Failed");
                }

            } else {
                if (table_ManageBooks.getSelectedRowCount() == 1) {
                    JOptionPane.showMessageDialog(null, "Table is Empty");
                } else {
                    JOptionPane.showMessageDialog(null, "Please Select a single Row in table for Delete");
                }
            }
        } else {
        }
    }//GEN-LAST:event_delete_button1ActionPerformed

    private void Update_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Update_button1ActionPerformed
        int opt = JOptionPane.showConfirmDialog(null, "Are You want to Update?", "Update", JOptionPane.YES_NO_OPTION);
        if (opt == 0) {
            DefaultTableModel tblmodel = (DefaultTableModel) table_ManageBooks.getModel();
            if (table_ManageBooks.getSelectedRowCount() == 1) {
                tblmodel.removeRow(table_ManageBooks.getSelectedRow());
                if (updateBook() == true) {
                    JOptionPane.showMessageDialog(null, "Book Updated");
                    txt_bookid2.setText("");
                    txt_bookname.setText("");
                    txt_authorname.setText("");
                    txt_quantity.setText("");
                    clearTabelBooks();
                    setBookDetailsTable();
                    Auto_BookID();
                    setDataToTotalBooks();
                    setDataToBooks();
                    showPieChart();
                    setDataToBooks();

                } else {
                    JOptionPane.showMessageDialog(null, "Book Updated Failed");
                    txt_bookid2.setText("");
                    txt_bookname.setText("");
                    txt_authorname.setText("");
                    txt_quantity.setText("");
                    Auto_BookID();
                }
            } else {
                if (table_ManageBooks.getSelectedRowCount() == 1) {
                    JOptionPane.showMessageDialog(null, "Details is Empty");
                } else {
                    JOptionPane.showMessageDialog(null, "Please Select a single Row in table for Updation");
                }
            }
        } else {
        }

    }//GEN-LAST:event_Update_button1ActionPerformed

    private void Add_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Add_button1ActionPerformed
        if (Bookvalidation() == true) {
            if (checkupDuplicateBook() == false) {
                int opt = JOptionPane.showConfirmDialog(null, "Are You want to Added?", "Added", JOptionPane.YES_NO_OPTION);
                if (opt == 0) {
                    if (addBook() == true) {
                        JOptionPane.showMessageDialog(null, "Book Added");
                        txt_bookid2.setText("");
                        txt_bookname.setText("");
                        txt_authorname.setText("");
                        txt_quantity.setText("");
                        clearTabelBooks();
                        setBookDetailsTable();
                        Auto_BookID();
                        setDataToTotalBooks();
                        setDataToBooks();
                        showPieChart();
                        setDataToBooks();
                    } else {

                    }
                } else {
                }
            } else {
                JOptionPane.showMessageDialog(null, "Book ID Already Exists ");
                JOptionPane.showMessageDialog(null, "Book Added Failed");
            }

        }
    }//GEN-LAST:event_Add_button1ActionPerformed

    private void txt_quantityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_quantityFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_quantityFocusLost

    private void txt_authornameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_authornameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_authornameFocusLost

    private void txt_booknameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_booknameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_booknameFocusLost

    private void jLabel114MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel114MouseClicked
        jTabbedPane1.setSelectedIndex(8);
        txt_id.setText("");
        txt_username.setText("");
        txt_fullname.setText("");
        txt_email.setText("");
        txt_contactno.setText("");
        Gender_radioButton1.clearSelection();
        profile_image.setIcon(null);
        ComboBox_SecurityQ.setSelectedItem("---Select Security Question---");
        txt_dob.setDate(null);
        txt_answer.setText("");
        txt_password.setText("");
        setVisible(true);
        int ID_no = getId();
        txt_id.setText(Integer.toString(ID_no));
    }//GEN-LAST:event_jLabel114MouseClicked

    private void jLabel91MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel91MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel91MouseClicked

    private void jLabel90MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel90MouseClicked
        // TODO add your handling code here:
        String username = txt_username.getText();
        if (txt_username.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Input a Username");
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LoginAuth", "root", "Abhay@9899");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select SECURITYQ from user  where USERNAME='" + username + "'");
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Username already Exist,Choosen new ");
                    txt_username.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "It's Unique");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jLabel90MouseClicked

    private void show_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_show_passwordActionPerformed
        // TODO add your handling code here:
        if (show_password.isSelected()) {
            txt_password.setEchoChar((char) 0);
        } else {
            txt_password.setEchoChar('*');
        }
    }//GEN-LAST:event_show_passwordActionPerformed

    private void DATA_CLEARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DATA_CLEARActionPerformed
        // TODO add your handling code here:
        txt_id.setText("");
        txt_username.setText("");
        txt_fullname.setText("");
        txt_email.setText("");
        txt_contactno.setText("");
        Gender_radioButton1.clearSelection();
        profile_image.setIcon(null);
        ComboBox_SecurityQ.setSelectedItem("---Select Security Question---");
        txt_dob.setDate(null);
        txt_answer.setText("");
        txt_password.setText("");
        setVisible(true);
        int ID_no = getId();
        txt_id.setText(Integer.toString(ID_no));
    }//GEN-LAST:event_DATA_CLEARActionPerformed

    private void DATA_INSERTEDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DATA_INSERTEDActionPerformed
        // TODO add your handling code here:
//
//        if (Staffvalidation() == true) {
//
//            try {
//                String Username = txt_username.getText();
//                String Fullname = txt_fullname.getText();
//                String Email = txt_email.getText();
//                String Contactno = txt_contactno.getText();
//                String gender = " ";
//                InputStream imageIS = null;
//                String SecurityQ = (String) ComboBox_SecurityQ.getSelectedItem();
//                Date date = txt_dob.getDate();
//                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
//                String DOB = sdf.format(date);
//                Date thisdate = new Date();
//                SimpleDateFormat RegDate = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss ");
//                String RDate = RegDate.format(new Date());
//                String ANSWER = txt_answer.getText();
//                String PASSWORD = txt_password.getText();
//
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "Abhay@9899");
//                if (male.isSelected()) {
//                    gender = "Male";
//                } else if (female.isSelected()) {
//                    gender = "Female";
//                }
//                imageIS = new FileInputStream(new File(crudImageAbsolutePath));
//                PreparedStatement p = con.prepareStatement("Insert into admin_table   values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
//                p.setInt(1, getId());
//                p.setString(2, Username);
//                p.setString(3, Fullname);
//                p.setString(4, Email);
//                p.setString(5, Contactno);
//                p.setString(6, gender);
//                p.setBlob(7, imageIS);
//                p.setString(8, SecurityQ);
//                p.setString(9, DOB);
//                p.setString(10, ANSWER);
//                p.setString(11, PASSWORD);
//                p.setString(12, RDate);
//                p.setInt(13, 0);
//                Username = txt_username.getText();
//                Statement st = con.createStatement();
//                ResultSet rs = st.executeQuery("select SECURITYQ from admin_table  where USERNAME='" + txt_username + "'");
//                if (rs.next()) {
//                    JOptionPane.showMessageDialog(null, "It's already Exist,Choosen new ");
//                } else {
//                    p.executeUpdate();
//                    JOptionPane.showMessageDialog(null, "Registration is Sucessfully");
//                    txt_id.setText("");
//                    txt_username.setText("");
//                    txt_fullname.setText("");
//                    txt_email.setText("");
//                    txt_contactno.setText("");
//                    Gender_radioButton1.clearSelection();
//                    profile_image.setIcon(null);
//                    ComboBox_SecurityQ.setSelectedItem("---Select Security Question---");
//                    txt_dob.setDate(null);
//                    txt_answer.setText("");
//                    txt_password.setText("");
//                    setVisible(true);
//                    int ID_no = getId();
//                    txt_id.setText(Integer.toString(ID_no));
//                }
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, e);
//            }
//
//        } else {
//        }
    }//GEN-LAST:event_DATA_INSERTEDActionPerformed

    private void femaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_femaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_femaleActionPerformed

    private void IMAGE_UPLOADActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IMAGE_UPLOADActionPerformed
        String currentDirectoryPath = "";//C:\\Users\\Abhay mehta\\Documents\\NetBeansProjects\\CRUD_INSERT_IMAGE_WITH_FORM\\src\\USER_PROFILE_IMAGE
        JFileChooser imageFileChooser = new JFileChooser(currentDirectoryPath);
        imageFileChooser.setDialogTitle("CHOOSE IMAGE");
        javax.swing.filechooser.FileNameExtensionFilter imageFNEF = new javax.swing.filechooser.FileNameExtensionFilter("IMAGES", "png", "jpeg", "jpg");
        imageFileChooser.setFileFilter(imageFNEF);
        int imageChooser = imageFileChooser.showOpenDialog(null);
        File imageFile = imageFileChooser.getSelectedFile();
        crudImageAbsolutePath = imageFile.getAbsolutePath();
        ImageIcon imageicon = new ImageIcon(crudImageAbsolutePath);
        Image imageResize = imageicon.getImage().getScaledInstance(profile_image.getWidth(), profile_image.getHeight(), Image.SCALE_SMOOTH);//import java.awt.image;
        profile_image.setIcon(new ImageIcon(imageResize));
    }//GEN-LAST:event_IMAGE_UPLOADActionPerformed

    private void profile_imageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profile_imageMouseClicked
        // TODO add your handling code here:
        //        IMAGE_DISPLAY_JFRAME IPAGE=new IMAGE_DISPLAY_JFRAME();
        //         IPAGE.show();
        //         ImageGetInTable();
    }//GEN-LAST:event_profile_imageMouseClicked
String imgPath=null;
    public ImageIcon ResizeImage1(String imgPath1){
        ImageIcon MyImage = new ImageIcon(imgPath1);
        Image img = MyImage.getImage();
        Image newImage = img.getScaledInstance(label_Admin_profile_image.getWidth(), label_Admin_profile_image.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        return image;
    }
    private void label_Admin_profile_imageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_Admin_profile_imageMouseClicked
              JFileChooser imageFileChooser=new JFileChooser();
        imageFileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        imageFileChooser.setDialogTitle("CHOOSE IMAGE");
        FileNameExtensionFilter imageFNEF=new FileNameExtensionFilter("IMAGES","png","jpeg","jpg");
        imageFileChooser.setFileFilter(imageFNEF);
        int result=imageFileChooser.showSaveDialog(null);
         if(result == JFileChooser.APPROVE_OPTION){
             File selectedFile = imageFileChooser.getSelectedFile();
             String path = selectedFile.getAbsolutePath();
             label_Admin_profile_image.setIcon(ResizeImage1(path));
             imgPath = path;
              }
         else if(result == JFileChooser.CANCEL_OPTION){
             System.out.println("No Data");
         }
    }//GEN-LAST:event_label_Admin_profile_imageMouseClicked

    private void Update_ImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Update_ImgActionPerformed
        if(imgPath!=null ){
            Staff_Username = txt_ADMIN_PROFILE_USERNAME.getText();
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms","root","Abhay@9899");
                String sql="Update  admin_table set IMAGE=? where USERNAME=?";
                InputStream img=new FileInputStream(new File(imgPath));
                PreparedStatement pst=con.prepareStatement(sql);
                pst.setBlob(1,img);
                pst.setString(2,Staff_Username);
                if(pst.executeUpdate()==1){
                    JOptionPane.showMessageDialog(null,"Image Updated");
                    //label_Admin_profile_image.setIcon(null);
                }else{
                    JOptionPane.showMessageDialog(null,"No Image Exist With this ID");
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
        }else{
            JOptionPane.showMessageDialog(null,"No Image Selected");
        }
        imgPath=null;
    }//GEN-LAST:event_Update_ImgActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HOME_PAGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HOME_PAGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HOME_PAGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HOME_PAGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HOME_PAGE().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add_button;
    private javax.swing.JButton Add_button1;
    private rojeru_san.complementos.RSTableMetro All_Dues_Record_indivdual_Student;
    private javax.swing.JComboBox<String> ComboBox_SecurityQ;
    private javax.swing.JButton DATA_CLEAR;
    private javax.swing.JButton DATA_INSERTED;
    private com.toedter.calendar.JDateChooser DueDate;
    private javax.swing.ButtonGroup Gender_radioButton;
    private javax.swing.ButtonGroup Gender_radioButton1;
    private javax.swing.JButton IMAGE_UPLOAD;
    private rojeru_san.complementos.RSTableMetro ISSUE_BOOKDETAILS;
    private javax.swing.JLabel NoOfBooksIcon;
    private javax.swing.JLabel NoOfBooksIcon1;
    private javax.swing.JLabel NoOfStudenticon;
    private javax.swing.JButton SearchUtton;
    private javax.swing.JLabel Totalbook;
    private javax.swing.JButton Update_Img;
    private javax.swing.JButton Update_button;
    private javax.swing.JButton Update_button1;
    private javax.swing.JLabel book;
    private javax.swing.JComboBox<String> combo_branch;
    private javax.swing.JComboBox<String> combo_course;
    private javax.swing.JPanel defaulterlist_panel;
    private javax.swing.JButton delete_button;
    private javax.swing.JButton delete_button1;
    private javax.swing.JRadioButton female;
    private javax.swing.JPanel issue_bookPanel;
    private javax.swing.JLabel issuebook;
    private javax.swing.JButton issued_button2;
    private javax.swing.JButton issued_button3;
    private javax.swing.JButton issued_button4;
    private com.toedter.calendar.JDateChooser issuedate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel jlabel12;
    public javax.swing.JLabel label_Admin_profile_image;
    private javax.swing.JLabel label_bookerror;
    private javax.swing.JLabel label_txt_bookauthor;
    private javax.swing.JLabel label_txt_bookid;
    private javax.swing.JLabel label_txt_bookname;
    private javax.swing.JLabel label_txt_bookquantity;
    private javax.swing.JLabel label_txt_bookquantity1;
    private javax.swing.JLabel label_txt_studentbranch;
    private javax.swing.JLabel label_txt_studentcourse;
    private javax.swing.JLabel label_txt_studentid;
    private javax.swing.JLabel label_txt_studentname;
    private javax.swing.JLabel lbl_BookError;
    private javax.swing.JLabel lbl_StudentError;
    private javax.swing.JLabel lbl_bookname1;
    private javax.swing.JLabel lbl_issueId1;
    private javax.swing.JLabel lbl_issuedate1;
    private javax.swing.JLabel lbl_studentname1;
    private javax.swing.JLabel lbl_studentname2;
    private javax.swing.JPanel logout_panel;
    private javax.swing.JRadioButton male;
    private javax.swing.JPanel manage_bookPanel;
    private javax.swing.JPanel manage_studentsPanel;
    public static final javax.swing.JLabel name = new javax.swing.JLabel();
    private javax.swing.JLabel noOfIssueBookicons;
    private javax.swing.JPanel panelBarChart;
    private javax.swing.JLabel profile_image;
    private javax.swing.JPanel return_bookpanel;
    private javax.swing.JPanel setting_panel;
    private javax.swing.JCheckBox show_pass;
    private javax.swing.JCheckBox show_password;
    private javax.swing.JLabel student;
    private rojeru_san.complementos.RSTableMetro table_DefaulterList;
    private rojeru_san.complementos.RSTableMetro table_ManageBooks;
    private rojeru_san.complementos.RSTableMetro table_books;
    private rojeru_san.complementos.RSTableMetro table_student;
    public javax.swing.JTextField txt_ADMIN_PROFILE_CONTACTNO;
    public javax.swing.JTextField txt_ADMIN_PROFILE_EMAILID;
    public javax.swing.JTextField txt_ADMIN_PROFILE_FULLNAME;
    public javax.swing.JLabel txt_ADMIN_PROFILE_REGDATE;
    public javax.swing.JLabel txt_ADMIN_PROFILE_USERNAME;
    private javax.swing.JTextField txt_answer;
    private javax.swing.JTextField txt_authorname;
    private javax.swing.JTextField txt_bookid;
    private javax.swing.JTextField txt_bookid1;
    private javax.swing.JLabel txt_bookid2;
    private javax.swing.JTextField txt_bookname;
    private javax.swing.JPasswordField txt_confirmpassword;
    private javax.swing.JTextField txt_contactno;
    private javax.swing.JTextField txt_currentpassword;
    private com.toedter.calendar.JDateChooser txt_dob;
    private com.toedter.calendar.JDateChooser txt_duedate;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_fullname;
    private javax.swing.JLabel txt_id;
    private com.toedter.calendar.JDateChooser txt_issuedate;
    private javax.swing.JLabel txt_issueid;
    private javax.swing.JPasswordField txt_newpassword;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_quantity;
    private com.toedter.calendar.JDateChooser txt_returnDate;
    private javax.swing.JTextField txt_studentid;
    private javax.swing.JTextField txt_studentid1;
    private javax.swing.JLabel txt_studentid2;
    private javax.swing.JTextField txt_studentname;
    private javax.swing.JTextField txt_username;
    private rojeru_san.complementos.RSTableMetro viewallrecords;
    private javax.swing.JPanel viewissuedbook_panel;
    private javax.swing.JPanel viewrecords_panel;
    // End of variables declaration//GEN-END:variables
}
