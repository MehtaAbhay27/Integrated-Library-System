import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class RESET_PASSWORD_PAGE extends javax.swing.JFrame {

  
    public RESET_PASSWORD_PAGE() {
        initComponents();
        SetIcon();
    }
    private void SetIcon(){
setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("books.png")));
}
    public void insertSingupPageDetails(){
    String Username=txt_username.getText();
    String Password=txt_password.getText();
    String Email=txt_securityQuestion.getText();
    String Contact=txt_answer.getText();
    try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms","root","Abhay@9899");
                PreparedStatement p = con.prepareStatement("Insert into USERS (NAME,PASSWORD,EMAIL,CONTACT)  values(?,?,?,?)");
                p.setString(1,Username);
                p.setString(2,Password);
                p.setString(3,Email);
                p.setString(4,Contact);
                int UpdateRowCount=p.executeUpdate();
                if(UpdateRowCount>0){
                    txt_username.setText("");
                    txt_password.setText("");
                    txt_securityQuestion.setText("");
                    txt_answer.setText("");
                    LOGIN_PAGE page=new LOGIN_PAGE();
                    page.setVisible(true);
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null,"Registration UnsucessFully");
                }
                   
    }catch(Exception e){
        JOptionPane.showMessageDialog(null,e);
    }
    }
    public boolean ValidateSignup(){
    String Username=txt_username.getText();
    String Password=txt_password.getText();
    String Email=txt_securityQuestion.getText();
    String Contact=txt_answer.getText();
    if(Username.equals("")){
    JOptionPane.showMessageDialog(null,"Please Enter Username");
    return false;
    }
    if(Password.equals("")){
    JOptionPane.showMessageDialog(null,"Please Enter Password");
    return false;
    }
    if(Email.equals("") ){//|| !Email.matches("^.+@.\\..+$") 
    JOptionPane.showMessageDialog(null,"Please valid Email");
    return false;
    }
    if(Contact.equals("")){
    JOptionPane.showMessageDialog(null,"Please Enter Contact No");
    return false;
    }
    return true;
    }
public boolean checkupDuplicateUser(){
String Username=txt_username.getText();
boolean isExist=false;
try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_ms","root","Abhay@9899");
                PreparedStatement p = con.prepareStatement("Select * from   admin_table  where USERNAME=?");
                p.setString(1,Username);
                java.sql.ResultSet rs= p.executeQuery();
                if(rs.next()){
                isExist=true;
                }else{
               isExist=false; 
               }
}catch(Exception e){
    JOptionPane.showMessageDialog(null,e);
}
return isExist;
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_answer = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        txt_password = new javax.swing.JPasswordField();
        show_pass = new javax.swing.JCheckBox();
        txt_securityQuestion = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Library cartoon.jpg"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 750, 500));

        jLabel1.setBackground(new java.awt.Color(255, 51, 51));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("        Advance Library");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 240, -1));

        jLabel4.setBackground(new java.awt.Color(255, 51, 51));
        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel4.setText("Welcome To");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 134, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/home2-32.png"))); // NOI18N
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 580));

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("           Reset Password");
        jLabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));

        txt_username.setBackground(new java.awt.Color(102, 102, 255));
        txt_username.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_username.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_usernameFocusLost(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Username");

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Password");

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Security Question");

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Answer");

        txt_answer.setBackground(new java.awt.Color(102, 102, 255));
        txt_answer.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_answer.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));

        jButton1.setBackground(new java.awt.Color(102, 102, 255));
        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Update Password");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txt_password.setBackground(new java.awt.Color(102, 102, 255));
        txt_password.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_password.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passwordActionPerformed(evt);
            }
        });

        show_pass.setBackground(new java.awt.Color(102, 102, 255));
        show_pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                show_passActionPerformed(evt);
            }
        });

        txt_securityQuestion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_securityQuestion.setText("Automatic Fetch Security Question ");
        txt_securityQuestion.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(255, 255, 255)));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel5MouseEntered(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel7.setText("Login");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5))
                    .addComponent(txt_securityQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_password)
                            .addComponent(txt_answer, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(show_pass)))
                .addGap(26, 26, 26))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(10, 10, 10)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_securityQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_answer, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(show_pass))
                .addGap(35, 35, 35)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 0, 390, 580));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
      String username=txt_username.getText();
        String securityQ=txt_securityQuestion.getText();
        String Answer=txt_answer.getText();
        String NewPassword=txt_password.getText();

        try{

            if(txt_username.getText().equals("")  && txt_answer.getText().equals("") &&txt_password.getText().equals("") )
            {
                JOptionPane.showMessageDialog(null,"Please Input a All the Details");

            }else if(txt_username.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"Please Input a Username");

            }
            else if(txt_securityQuestion.getText().equals("Automatic Fetch Security Question "))
            {
                JOptionPane.showMessageDialog(null,"Please Search the UserName to get Security Question");

            }

            else if(txt_answer.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"Please Input a Answer");

            }
            else if(txt_password.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"Please Input a NewPassword");

            }
            else{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms","root","Abhay@9899");
                Statement st=con.createStatement();
                java.sql.ResultSet rs=st.executeQuery("select SECURITYQ from admin_table  where USERNAME='"+username+"' and ANSWER='"+Answer+"'");
                if(rs.next()){
                    st.executeUpdate("update admin_table set PASSWORD='"+NewPassword+"' where USERNAME='"+username+"' and ANSWER='"+Answer+"'");
                    JOptionPane.showMessageDialog(null,"Your Password is SuccessFully Updated");
                    setVisible(false);
                    new LOGIN_PAGE().setVisible(true);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Please Write correct Answer");
                    txt_answer.setText("");
                    con.close();
                    rs.close();
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_usernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_usernameFocusLost

    }//GEN-LAST:event_txt_usernameFocusLost

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
        INDEX_PAGE home=new INDEX_PAGE();
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel14MouseClicked

    private void txt_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_passwordActionPerformed

    private void show_passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_show_passActionPerformed
        // TODO add your handling code here:
        if(show_pass.isSelected()){
            txt_password.setEchoChar((char)0);
        }
        else{
            txt_password.setEchoChar('*');
        }
    }//GEN-LAST:event_show_passActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
      // txt_securityQuestion.setText("");
        String Username=txt_username.getText();
        if(txt_username.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null,"Please Input a Username");
            txt_securityQuestion.setText("Automatic Fetch Security Question ");
        }
        else{
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms","root","Abhay@9899");
                Statement st=con.createStatement();
                java.sql.ResultSet rs=st.executeQuery("select SECURITYQ from ADMIN_TABLE  where USERNAME='"+txt_username.getText()+"' ");
                if(rs.next()){
                    txt_securityQuestion.setText(rs.getString(1));
                }
                else{
                    JOptionPane.showMessageDialog(null,"Please Enter correct Username");
                    txt_securityQuestion.setText("Automatic Fetch Security Question ");
                       txt_username.setText("");
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"reset error");
            }
        }
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel5MouseEntered

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        setVisible(false);
        LOGIN_PAGE ob = new LOGIN_PAGE();
        ob.setVisible(true);
    }//GEN-LAST:event_jLabel7MouseClicked

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RESET_PASSWORD_PAGE().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JCheckBox show_pass;
    private javax.swing.JTextField txt_answer;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JLabel txt_securityQuestion;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
