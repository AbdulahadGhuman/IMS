import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
public class ReadUsernames extends Thread {
    @Override
    public void run () {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get("D:\\IMS");
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey key;
            try {
                while ((key = watchService.take()) != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind().name().equals("ENTRY_MODIFY") && event.context().toString().equals("Accounts.txt")) {
                            int x = 0;
                            int y = 0;
                            if (Buttons.isEmpty()) {
                                x = 55;
                                y = 80;
                            } else if (Buttons.get(Buttons.size() - 1).getX() == 715) {
                                x = 55;
                                y = Buttons.get(Buttons.size() - 1).getY() + 30; 
                            } else {
                                x = Buttons.get(Buttons.size() - 1).getX() + 220;
                                y = Buttons.get(Buttons.size() - 1).getY();
                            }
                            File newFile = new File("Accounts.txt");
                            Scanner newScanner = new Scanner(newFile);
                            String Username = "";
                            while (newScanner.hasNextLine()) {
                                Username = newScanner.nextLine();
                                Username = Username.substring(0, Username.indexOf("#"));
                            }
                            JButton UsernameButton = new JButton (Username);
                            UsernameButton.setBackground(Color.WHITE);
                            UsernameButton.setFont(new Font("Serif", Font.PLAIN, 15));
                            UsernameButton.setBounds(x, y, 220, 30);
                            UsernameButton.addActionListener((ActionEvent e) -> { 
                                IMS.Username = UsernameButton.getText();
                                JOptionPane.showMessageDialog(null, "Recipient selected as " + IMS.Username);
                            });
                            IMS.IMSFrame.add(UsernameButton);
                            Buttons.add(UsernameButton);
                            IMS.IMSFrame.repaint();
                            IMS.IMSFrame.revalidate();
                        }
                    }
                    key.reset();
                }
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    static ArrayList <JButton> Buttons = new ArrayList <> ();
    public static void ReadAccountsFile (JFrame NewJFrame) {
        try {
            JLabel MembersList = new JLabel ("Username List:");
            MembersList.setBounds(400, -10, 300, 100);
            MembersList.setForeground(Color.WHITE);
            MembersList.setFont(new Font("Serif", Font.PLAIN, 30));
            NewJFrame.add(MembersList);
            File newFile = new File("Accounts.txt");
            Scanner newScanner = new Scanner(newFile);
            int x = 55;
            int y = 80;
            while (newScanner.hasNextLine()) {
                String Username = newScanner.nextLine();
                Username = Username.substring(0, Username.indexOf("#"));
                if (!Username.equals(RegisterOrLogin.LoginUsername)) {
                    JButton UsernameButton = new JButton (Username);
                    UsernameButton.setBackground(Color.WHITE);
                    UsernameButton.setFont(new Font("Serif", Font.PLAIN, 15));
                    UsernameButton.setBounds(x, y, 220, 30);
                    UsernameButton.addActionListener((ActionEvent e) -> { 
                        IMS.Username = UsernameButton.getText();
                        JOptionPane.showMessageDialog(null, "Recipient selected as " + IMS.Username);
                    });
                    x = x + 220;
                    NewJFrame.add(UsernameButton);
                    Buttons.add(UsernameButton);
                    if (x == 935) {
                        x = 55;
                        y = y + 30;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } 
    }
}
