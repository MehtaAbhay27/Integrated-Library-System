
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.swing.ImageIcon;

/**
 *
 * @author Abhay mehta
 */
public class LOGIN_PAGE extends javax.swing.JFrame {
public ImageIcon format=null;
    public LOGIN_PAGE() {
        initComponents();
        SetIcon();
    }
private void SetIcon(){
setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("books.png")));
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        name = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        show_pass = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(730, 550));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Library cartoon.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 730, 480));

        jLabel4.setBackground(new java.awt.Color(255, 51, 51));
        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel4.setText("Welcome To");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 134, -1));

        jLabel2.setBackground(new java.awt.Color(255, 51, 51));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setText("Advance Library");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 190, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/home2-32.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        name.setBackground(new java.awt.Color(0, 0, 0));
        name.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        name.setForeground(new java.awt.Color(255, 255, 255));
        name.setText("Login Page");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Collaborator_Male_26px.png"))); // NOI18N

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Username");

        txt_username.setBackground(new java.awt.Color(102, 102, 255));
        txt_username.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_username.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_usernameFocusLost(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/password.png"))); // NOI18N

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Password");

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

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel11.setText("   forgot password ?   ");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(102, 102, 255));
        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Login");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_password, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(140, 140, 140)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(show_pass))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(name)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(name)
                .addGap(79, 79, 79)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(show_pass))
                .addGap(65, 65, 65)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addContainerGap(98, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        INDEX_PAGE home=new INDEX_PAGE();
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void txt_usernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_usernameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_usernameFocusLost

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

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
        setVisible(false);
        RESET_PASSWORD_PAGE ob = new RESET_PASSWORD_PAGE();
        ob.setVisible(true);
    }//GEN-LAST:event_jLabel11MouseClicked
/*
              if(txt_username.getText().equals("") && txt_password.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null,"Please enter Username & Passwod.");
         
            
        }
        else if(txt_username.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null,"Username is required.");
            
            
        }
        else if(txt_password.getText().equals(""))
        {
            
            JOptionPane.showMessageDialog(null,"Password is required.");
            
        }
        else{
 String sql = "SELECT * FROM admin_table WHERE USERNAME=? AND PASSWORD=?";
  try ( 
Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms","root","Abhay@9899");    
PreparedStatement pst = con.prepareStatement(sql)) {
pst.setString(1, txt_username.getText());
pst.setString(2, new String(txt_password.getPassword()));
    try (ResultSet rs = pst.executeQuery()) {
        if (rs.next()) {
            String userType = rs.getString("User_Type");
            if ("1".equals(userType)) {
                 dispose();
                 HOME_PAGE HPAGE=new HOME_PAGE();
                 HPAGE.name.setText(txt_username.getText());
                 HPAGE.txt_ADMIN_PROFILE_USERNAME.setText(rs.getString(2));
                 HPAGE. txt_ADMIN_PROFILE_FULLNAME.setText(rs.getString(3));
                 HPAGE. txt_ADMIN_PROFILE_EMAILID.setText(rs.getString(4));
                 HPAGE. txt_ADMIN_PROFILE_CONTACTNO.setText(rs.getString(5));
                 HPAGE. txt_ADMIN_PROFILE_REGDATE.setText(rs.getString(12));
                 byte[] imagedata=rs.getBytes ("IMAGE");
                 format=new ImageIcon(imagedata);
                 Image mm=format.getImage();
                 Image img2=mm.getScaledInstance(246,291,Image.SCALE_SMOOTH);
                 ImageIcon image=new ImageIcon(img2); 
                 HPAGE.label_Admin_profile_image.setIcon(image);
                 HPAGE.show();
            } else {
                //dispose();
                  SUB_ADMIN_HOME_PAGE S_HPAGE=new SUB_ADMIN_HOME_PAGE();
                 S_HPAGE.name.setText(txt_username.getText());
                 S_HPAGE.txt_ADMIN_PROFILE_USERNAME.setText(rs.getString(2));
                 S_HPAGE. txt_ADMIN_PROFILE_FULLNAME.setText(rs.getString(3));
                 S_HPAGE. txt_ADMIN_PROFILE_EMAILID.setText(rs.getString(4));
                 S_HPAGE. txt_ADMIN_PROFILE_CONTACTNO.setText(rs.getString(5));
                 S_HPAGE. txt_ADMIN_PROFILE_REGDATE.setText(rs.getString(12));
                 byte[] imagedata=rs.getBytes ("IMAGE");
                 format=new ImageIcon(imagedata);
                 Image mm=format.getImage();
                 Image img2=mm.getScaledInstance(246,291,Image.SCALE_SMOOTH);
                 ImageIcon image=new ImageIcon(img2); 
                 S_HPAGE.label_Admin_profile_image.setIcon(image);
                 S_HPAGE.show();
                
            }
            this.dispose(); // Close the login form
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }
} catch (Exception e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
}
        }
    */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
//          if(txt_username.getText().equals("") && txt_password.getText().equals(""))
//        {
//            JOptionPane.showMessageDialog(null,"Please enter Username & Passwod.");
//         
//            
//        }
//        else if(txt_username.getText().equals(""))
//        {
//            JOptionPane.showMessageDialog(null,"Username is required.");
//            
//            
//        }
//        else if(txt_password.getText().equals(""))
//        {
//            
//            JOptionPane.showMessageDialog(null,"Password is required.");
//            
//        }
//        else{
//        try{
//           Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms","root","Abhay@9899");
//            String Username=txt_username.getText();
//            String Password=txt_password.getText();
//            Statement stm=con.createStatement();
//            String sql="Select * from admin_table where USERNAME='"+Username+"' and PASSWORD='"+Password+"' ";
//            java.sql.ResultSet rs=stm.executeQuery(sql);
//            if(rs.next()){
//                dispose();
//                  HOME_PAGE HPAGE=new HOME_PAGE();
//                  HPAGE.name.setText(txt_username.getText());
//                  HPAGE.txt_ADMIN_PROFILE_USERNAME.setText(rs.getString(2));
//                  HPAGE. txt_ADMIN_PROFILE_FULLNAME.setText(rs.getString(3));
//                   HPAGE. txt_ADMIN_PROFILE_EMAILID.setText(rs.getString(4));
//                  HPAGE. txt_ADMIN_PROFILE_CONTACTNO.setText(rs.getString(5));
//                  HPAGE. txt_ADMIN_PROFILE_REGDATE.setText(rs.getString(12));
//                byte[] imagedata=rs.getBytes ("IMAGE");
//                format=new ImageIcon(imagedata);
//                Image mm=format.getImage();
//                Image img2=mm.getScaledInstance(246,291,Image.SCALE_SMOOTH);
//                ImageIcon image=new ImageIcon(img2); 
//                HPAGE.label_Admin_profile_image.setIcon(image);
//                  
//                  HPAGE.show();
//            }else{
//                JOptionPane.showMessageDialog(null,"Incorrect Username or Password");
//            }
//        }catch(Exception e){
//        }
//        }
             if(txt_username.getText().equals("") && txt_password.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null,"Please enter Username & Passwod.");
         
            
        }
        else if(txt_username.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null,"Username is required.");
            
            
        }
        else if(txt_password.getText().equals(""))
        {
            
            JOptionPane.showMessageDialog(null,"Password is required.");
            
        }
        else{
 String sql = "SELECT * FROM admin_table WHERE USERNAME=? AND PASSWORD=?";
  try ( 
Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms","root","Abhay@9899");    
PreparedStatement pst = con.prepareStatement(sql)) {
pst.setString(1, txt_username.getText());
pst.setString(2, new String(txt_password.getPassword()));
    try (ResultSet rs = pst.executeQuery()) {
        if (rs.next()) {
            String userType = rs.getString("User_Type");
            if ("1".equals(userType)) {
                 dispose();
                 HOME_PAGE HPAGE=new HOME_PAGE();
                 HPAGE.name.setText(txt_username.getText());
                 HPAGE.txt_ADMIN_PROFILE_USERNAME.setText(rs.getString(2));
                 HPAGE. txt_ADMIN_PROFILE_FULLNAME.setText(rs.getString(3));
                 HPAGE. txt_ADMIN_PROFILE_EMAILID.setText(rs.getString(4));
                 HPAGE. txt_ADMIN_PROFILE_CONTACTNO.setText(rs.getString(5));
                 HPAGE. txt_ADMIN_PROFILE_REGDATE.setText(rs.getString(12));
                 byte[] imagedata=rs.getBytes ("IMAGE");
                 format=new ImageIcon(imagedata);
                 Image mm=format.getImage();
                 Image img2=mm.getScaledInstance(246,291,Image.SCALE_SMOOTH);
                 ImageIcon image=new ImageIcon(img2); 
                 HPAGE.label_Admin_profile_image.setIcon(image);
                 HPAGE.show();
            } else {
                //dispose();
                  SUB_ADMIN_HOME_PAGE S_HPAGE=new SUB_ADMIN_HOME_PAGE();
                 S_HPAGE.name.setText(txt_username.getText());
                 S_HPAGE.txt_ADMIN_PROFILE_USERNAME.setText(rs.getString(2));
                 S_HPAGE. txt_ADMIN_PROFILE_FULLNAME.setText(rs.getString(3));
                 S_HPAGE. txt_ADMIN_PROFILE_EMAILID.setText(rs.getString(4));
                 S_HPAGE. txt_ADMIN_PROFILE_CONTACTNO.setText(rs.getString(5));
                 S_HPAGE. txt_ADMIN_PROFILE_REGDATE.setText(rs.getString(12));
                 byte[] imagedata=rs.getBytes ("IMAGE");
                 format=new ImageIcon(imagedata);
                 Image mm=format.getImage();
                 Image img2=mm.getScaledInstance(246,291,Image.SCALE_SMOOTH);
                 ImageIcon image=new ImageIcon(img2); 
                 S_HPAGE.label_Admin_profile_image.setIcon(image);
                 S_HPAGE.show();
                
            }
            this.dispose(); // Close the login form
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }
} catch (Exception e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this,  e);//"Database error: " +
}
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(LOGIN_PAGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LOGIN_PAGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LOGIN_PAGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LOGIN_PAGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LOGIN_PAGE().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel name;
    private javax.swing.JCheckBox show_pass;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
