import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

public class LoadMessages extends Thread {
    static int x = 55;
    static int y = 350;
    static ArrayList <JButton> Buttons = new ArrayList <> ();
    @Override
    public void run () {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get("D:\\IMS\\AllMessages\\" + RegisterOrLogin.LoginUsername + "\\UnreadMessages\\");
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey key;
            try {
                while ((key = watchService.take()) != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind().name().equals("ENTRY_CREATE")) {
                            int x = 0;
                            int y = 0;
                            if (Buttons.isEmpty()) {
                                x = 55;
                                y = 350;
                            }
                            else if (Buttons.get(Buttons.size() - 1).getX() == 715) {
                                x = 55;
                                y = Buttons.get(Buttons.size() - 1).getY() + 30; 
                            } else {
                                x = Buttons.get(Buttons.size() - 1).getX() + 220;
                                y = Buttons.get(Buttons.size() - 1).getY();
                            }
                            JButton NewMessage = new JButton (event.context().toString());
                            NewMessage.setBackground(Color.WHITE);
                            NewMessage.setFont(new Font("Serif", Font.PLAIN, 15));
                            NewMessage.setBounds(x, y, 220, 30);
                            NewMessage.addActionListener((ActionEvent e) -> { 
                                String Data = "";
                                Scanner newScanner;
                                try {
                                    newScanner = new Scanner (new File ("D:\\IMS\\AllMessages\\" + RegisterOrLogin.LoginUsername + "\\UnreadMessages\\" + event.context().toString()));
                                    while (newScanner.hasNextLine()) {
                                        Data = Data + newScanner.nextLine() + "\n";
                                    }
                                    newScanner.close();
                                } catch (FileNotFoundException ex) {
                                    JOptionPane.showMessageDialog(null, ex);
                                }
                                JOptionPane.showMessageDialog(null, Data);
                                try {
                                    Files.move(Paths.get("D:\\IMS\\AllMessages\\" + RegisterOrLogin.LoginUsername + "\\UnreadMessages\\" + event.context().toString()), Paths.get("D:\\IMS\\AllMessages\\" + RegisterOrLogin.LoginUsername + "\\ArchiveMessages\\" + event.context().toString()), StandardCopyOption.REPLACE_EXISTING);
                                    IMS.IMSFrame.remove(NewMessage);
                                    Buttons.remove(NewMessage);
                                    IMS.IMSFrame.repaint();
                                    IMS.IMSFrame.revalidate();
                                } catch (IOException ex) {
                                    JOptionPane.showMessageDialog(null, ex);
                                }
                            });
                            IMS.IMSFrame.add(NewMessage);
                            Buttons.add(NewMessage);
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
    public static void LoadAllMessages (JFrame newJFrame, File MessageDirectory) {
        JLabel MembersList = new JLabel ("All Messages:");
        MembersList.setBounds(395, 260, 300, 100);
        MembersList.setForeground(Color.WHITE);
        MembersList.setFont(new Font("Serif", Font.PLAIN, 30));
        newJFrame.add(MembersList);
        for (File MessageEntry : MessageDirectory.listFiles()) {
            if (MessageEntry.isDirectory()) {
                LoadAllMessages(newJFrame, MessageEntry);
            } else {
                JButton NewMessage = new JButton (MessageEntry.getName());
                NewMessage.setBackground(Color.WHITE);
                NewMessage.setFont(new Font("Serif", Font.PLAIN, 15));
                NewMessage.setBounds(x, y, 220, 30);
                NewMessage.addActionListener((ActionEvent e) -> { 
                    try {
                        String Data = "";
                        Scanner newScanner = new Scanner (MessageEntry);
                        while (newScanner.hasNextLine()) {
                            Data = Data + newScanner.nextLine() + "\n";
                        }
                        JOptionPane.showMessageDialog(null, Data);
                        try {
                            newScanner.close();
                            Files.move(Paths.get(MessageEntry.getAbsolutePath()), Paths.get("D:\\IMS\\AllMessages\\" + RegisterOrLogin.LoginUsername + "\\ArchiveMessages\\" + MessageEntry.getName()), StandardCopyOption.REPLACE_EXISTING);
                            newJFrame.remove(NewMessage);
                            Buttons.remove(NewMessage);
                            newJFrame.repaint();
                            newJFrame.revalidate();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                        }

                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                    
                });
                x = x + 220;
                newJFrame.add(NewMessage);
                Buttons.add(NewMessage);
                if (x == 935) {
                    x = 55;
                    y = y + 30;
                }
            }
        }
    }
}
