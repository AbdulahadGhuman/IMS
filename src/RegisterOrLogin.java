import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
public class RegisterOrLogin {
    static String LoginUsername;
    public RegisterOrLogin () {
        JFrame LoginPage = new JFrame ();
        LoginPage.setSize(600, 350);
        LoginPage.setLayout(null);
        LoginPage.getContentPane().setBackground(Color.LIGHT_GRAY);
        LoginPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JLabel RegisterOrLoginLabel = new JLabel ("Register Or Login");
        RegisterOrLoginLabel.setFont(new Font("Serif", Font.BOLD, 30));
        RegisterOrLoginLabel.setForeground(Color.WHITE);
        RegisterOrLoginLabel.setBounds(170, 40, 300, 50);
        
        JLabel UsernameLabel = new JLabel ("Username: ");
        UsernameLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        UsernameLabel.setForeground(Color.WHITE);
        UsernameLabel.setBounds(160,100, 100, 50);
        
        JLabel PasswordLabel = new JLabel ("Password: ");
        PasswordLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        PasswordLabel.setForeground(Color.WHITE);
        PasswordLabel.setBounds(160,150, 100, 50);
        
        JTextField UsernameInput = new JTextField ();
        UsernameInput.setFont(new Font("Serif", Font.PLAIN, 15));
        UsernameInput.setBounds(260,112, 150, 30);
        
        JPasswordField PasswordInput = new JPasswordField ();
        PasswordInput.setFont(new Font("Serif", Font.PLAIN, 15));
        PasswordInput.setBounds(260,162, 150, 30);
        
        JButton RegisterOrLoginButton = new JButton ("Register Or Login");
        RegisterOrLoginButton.setBackground(Color.WHITE);
        RegisterOrLoginButton.setFont(new Font("Serif", Font.PLAIN, 15));
        RegisterOrLoginButton.setBounds(210, 230, 150, 40);
        
        RegisterOrLoginButton.addActionListener((ActionEvent e) -> { 
            boolean accountFound = false;
            try {
                File newFile = new File("Accounts.txt");
                if (!newFile.exists()) {
                    newFile.createNewFile();
                }
                Scanner newScanner = new Scanner(newFile);
                while (newScanner.hasNextLine()) {
                    String Account = newScanner.nextLine();
                    String [] AccountCredentials = Account.split("#");
                    if (AccountCredentials[0].equals(UsernameInput.getText()) && AccountCredentials[1].equals(Arrays.toString(EncryptPassword.EncryptPassword(PasswordInput.getText())))) {
                        JOptionPane.showMessageDialog(null, "Account Found");
                        accountFound = true;
                        LoginUsername = UsernameInput.getText();
                        IMS.createIMS();
                        LoginPage.dispatchEvent(new WindowEvent(LoginPage, WindowEvent.WINDOW_CLOSING));

                    } else if (AccountCredentials[0].equals(UsernameInput.getText()) && !AccountCredentials[1].equals(Arrays.toString(EncryptPassword.EncryptPassword(PasswordInput.getText())))) {
                        JOptionPane.showMessageDialog(null, "Incorrect Password");
                        accountFound = true;
                    } 
                }
                if (accountFound == false) {
                    FileWriter newWriter = new FileWriter ("Accounts.txt", true);
                    newWriter.write(UsernameInput.getText() + "#" + Arrays.toString(EncryptPassword.EncryptPassword(PasswordInput.getText())) + "\n");
                    newWriter.close();
                    JOptionPane.showMessageDialog(null, "Account Created");
                    newScanner.close();
                    new File ("AllMessages\\" + UsernameInput.getText()).mkdir();
                    new File ("AllMessages\\" + UsernameInput.getText() + "\\UnreadMessages").mkdir();
                    new File ("AllMessages\\" + UsernameInput.getText() + "\\ArchiveMessages").mkdir();
                    LoginUsername = UsernameInput.getText();
                    IMS.createIMS();
                    LoginPage.dispatchEvent(new WindowEvent(LoginPage, WindowEvent.WINDOW_CLOSING));
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        });  
        
        LoginPage.add(RegisterOrLoginLabel);
        LoginPage.add(UsernameLabel);
        LoginPage.add(PasswordLabel);
        LoginPage.add(UsernameInput);
        LoginPage.add(PasswordInput);
        LoginPage.add(RegisterOrLoginButton);
        
        LoginPage.setVisible(true);
    }
    public static void main (String [] args) {
        RegisterOrLogin newLoginPage = new RegisterOrLogin ();
    }
}
