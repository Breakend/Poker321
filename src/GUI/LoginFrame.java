package GUI;

import java.awt.Dimension;
import java.rmi.RemoteException;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 * Implements a login portal to the game server.
 * 
 * @author mattvertescher
 */
public class LoginFrame extends javax.swing.JFrame {

    public static GUIClient clientRequest; 
    public static ServerListener serverListener; 
    
    public static String presetIP; 
    
    /**
     * Creates new form LoginScreen.
     */
    public LoginFrame() {
        initComponents();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width/2)-(this.getWidth()/2), (screenSize.height/2)-(this.getHeight()/2));  
        this.setIconImage(new ImageIcon(GUIClient.class.getResource("images/login_icon.png")).getImage());
        
        clientRequest = new GUIClient();
        serverListener = new ServerListener();
        
        
        presetIP = GUIClient.getIp();
        ipTextField.setText(presetIP);
    }
    
    /**
     * This method checks the fields given and tries to contact the server.
     * @return boolean confirming or denying server login success. 
     */
    private boolean loginAction() throws RemoteException, SQLException {
        String emailString = emailTextField.getText();
        String passwordString = new String(passwordField.getPassword());
        
        if (emailString.equals("")) {
            JOptionPane.showMessageDialog(this, "Email Field Empty", "Error Message", JOptionPane.ERROR_MESSAGE);
            return false; 
        }
 
        else if (passwordString.equals("")) {
            JOptionPane.showMessageDialog(this, "Password Field Empty", "Error Message", JOptionPane.ERROR_MESSAGE);
            return false; 
        }
       
        else if (ipTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Server IP Field Empty", "Error Message", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        boolean loginAccepted = clientRequest.testEmailPassword(emailString, passwordString);
        
        if (!loginAccepted) {
            JOptionPane.showMessageDialog(this, "The server hates you.", "Error Message", JOptionPane.ERROR_MESSAGE);
            return false;
        }    
        
        return true;
    }
    
    /**
     * Opens the main menu screen and destroys the LoginFrame.
     */
    private void openMainMenu() throws RemoteException, SQLException {
        MainMenuFrame mm = new MainMenuFrame(clientRequest, serverListener);
        mm.setVisible(true);   
        this.setVisible(false); 
        this.dispose();
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        topPanel = new javax.swing.JPanel();
        emailLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        emailTextField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();
        ipTextField = new javax.swing.JTextField();
        ipLabel = new javax.swing.JLabel();
        bottomPanel = new javax.swing.JPanel();
        passwordRecoveryButton = new javax.swing.JButton();
        newAccountButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setResizable(false);

        emailLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        emailLabel.setText("Email:");

        passwordLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        passwordLabel.setText("Password:");

        emailTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                emailTextFieldKeyPressed(evt);
            }
        });

        passwordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordFieldKeyPressed(evt);
            }
        });

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        ipLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        ipLabel.setText("Server IP:");

        org.jdesktop.layout.GroupLayout topPanelLayout = new org.jdesktop.layout.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(topPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(ipLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(passwordLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                    .add(emailLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(18, 18, 18)
                .add(topPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, emailTextField)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, passwordField)
                    .add(loginButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .add(ipTextField))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(topPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(emailLabel)
                    .add(emailTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(topPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(passwordLabel)
                    .add(passwordField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(topPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(ipTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ipLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 12, Short.MAX_VALUE)
                .add(loginButton)
                .addContainerGap())
        );

        passwordRecoveryButton.setText("Forget Password?");
        passwordRecoveryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordRecoveryButtonActionPerformed(evt);
            }
        });

        newAccountButton.setText("Need Account?");
        newAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newAccountButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout bottomPanelLayout = new org.jdesktop.layout.GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(bottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(passwordRecoveryButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(newAccountButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 128, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(bottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(bottomPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(passwordRecoveryButton)
                    .add(newAccountButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jSeparator1)
            .add(layout.createSequentialGroup()
                .add(bottomPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, topPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(topPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(4, 4, 4)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(2, 2, 2)
                .add(bottomPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    /**
     * When enter is pressed in the email text field, the password field is 
     * focused.
     * @param evt 
     */
    private void emailTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emailTextFieldKeyPressed
         if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            passwordField.grabFocus();
         }
    }//GEN-LAST:event_emailTextFieldKeyPressed

    /**
     * When enter is pressed in the password field, loginAction is called.
     * @param evt 
     */
    private void passwordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordFieldKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            try {
                if (loginAction() == true) 
                    openMainMenu();
            } catch (RemoteException ex) {} catch (SQLException ex) {}
        }
    }//GEN-LAST:event_passwordFieldKeyPressed

    /**
     * When the login button is pressed, loginAction is called.
     * @param evt 
     */
    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        try {
            if (loginAction() == true) 
                openMainMenu();
        } catch (RemoteException ex) {} catch (SQLException ex) {}
    }//GEN-LAST:event_loginButtonActionPerformed

    /**
     * When the password recovery button is pressed, the user is prompted. 
     * @param evt 
     */
    private void passwordRecoveryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordRecoveryButtonActionPerformed
        String username = JOptionPane.showInputDialog(this, "Please enter your username: ", "Password Recovery", JOptionPane.PLAIN_MESSAGE);
        //boolean emailSent = GUIClient.sendPasswordRecoveryEmail(username);
        boolean emailSent = false;
        try {
            emailSent = clientRequest.sendPasswordRecoveryEmail(username);
        } catch (RemoteException ex) {} catch (SQLException ex) {}
        
        if (emailSent == false)
            JOptionPane.showMessageDialog(this, "Username does not exist", "Error", JOptionPane.ERROR_MESSAGE);
        else if (emailSent == true)
            JOptionPane.showMessageDialog(this, "Passoword sent to " + username + "'s email", "Password Recovery", JOptionPane.INFORMATION_MESSAGE);
            
    }//GEN-LAST:event_passwordRecoveryButtonActionPerformed

    /**
     * When the new account button is pressed, a new registration frame is 
     * created.
     * @param evt 
     */
    private void newAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newAccountButtonActionPerformed
        RegistrationFrame rs = new RegistrationFrame(clientRequest);
        rs.setVisible(true);   
        this.setVisible(false); 
        this.dispose();      
    }//GEN-LAST:event_newAccountButtonActionPerformed
    

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Sets the JTattoo look and feel
         */
        try {
            com.jtattoo.plaf.noire.NoireLookAndFeel.setTheme("Small-Font");
            javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JLabel ipLabel;
    private javax.swing.JTextField ipTextField;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton loginButton;
    private javax.swing.JButton newAccountButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JButton passwordRecoveryButton;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables
}
