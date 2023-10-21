import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SendMessage {
    static JTextArea MessageInput = new JTextArea ();
    public static void SendMessage (JFrame NewJFrame) {

        JLabel MembersList = new JLabel ("Send Message:");
        MembersList.setBounds(395, 360, 300, 100);
        MembersList.setForeground(Color.WHITE);
        MembersList.setFont(new Font("Serif", Font.PLAIN, 30));
        
        MessageInput = new JTextArea ();
        MessageInput.setLineWrap(true);
        MessageInput.setWrapStyleWord(true);
        MessageInput.setMargin(new Insets(0,8, 0,0));
        MessageInput.setFont(new Font("Serif", Font.PLAIN, 20));
        
        JScrollPane MessageInputScroll = new JScrollPane (MessageInput, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        MessageInputScroll.setBounds(55, 450, 880, 150);
        
        JButton SendMessageButton = new JButton ("Send Message");
        SendMessageButton.setBackground(Color.WHITE);
        SendMessageButton.setFont(new Font("Serif", Font.PLAIN, 15));
        SendMessageButton.setBounds(410, 635, 150, 40);
        
        SendMessageButton.addActionListener((ActionEvent e) -> { 
            FileWriter newWriter = null;
            try { 
                if (IMS.Username == null) {
                    JOptionPane.showMessageDialog(null, "Select recipient");
                } else {
                    int count = 0;
                    while (new File ("D:\\IMS\\AllMessages\\" + IMS.Username + "\\UnreadMessages\\" + count + ".txt").exists()) {
                        count++;
                    }  
                    DateTimeFormatter NewFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
                    LocalDateTime CurrentTime = LocalDateTime.now();  
                    newWriter = new FileWriter ("D:\\IMS\\AllMessages\\" + IMS.Username + "\\UnreadMessages\\" + count + ".txt");
                    newWriter.write("Message: " + MessageInput.getText() + "\nDate: " + NewFormat.format(CurrentTime) + "\nFrom: " + RegisterOrLogin.LoginUsername);
                    newWriter.close();
                    JOptionPane.showMessageDialog(null, "Message sent");
                    MessageInput.setText("");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex);
            } 
        });
        
        NewJFrame.add(MessageInputScroll);
        NewJFrame.add(MembersList);
        NewJFrame.add(SendMessageButton);
    }
}
