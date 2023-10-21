import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ReadPhrases {
    public static void ReadPhrasesFile (JFrame NewJFrame) {
        JLabel CommonPhrases = new JLabel ("Common Phrases:");
        CommonPhrases.setBounds(380, 95, 300, 100);
        CommonPhrases.setForeground(Color.WHITE);
        CommonPhrases.setFont(new Font("Serif", Font.PLAIN, 30));
        NewJFrame.add(CommonPhrases);
        try {
            File newFile = new File("Phrases.txt");
            Scanner newScanner = new Scanner(newFile);
            int x = 55;
            int y = 180;
            while (newScanner.hasNextLine()) {
                String Phrases = newScanner.nextLine();
                JButton PhrasesButton = new JButton (Phrases);
                PhrasesButton.setBackground(Color.WHITE);
                PhrasesButton.setFont(new Font("Serif", Font.PLAIN, 15));
                PhrasesButton.setBounds(x, y, 220, 30);
                PhrasesButton.addActionListener((ActionEvent e) -> { 
                    SendMessage.MessageInput.setText(PhrasesButton.getText());
                });
                x = x + 220;
                NewJFrame.add(PhrasesButton);
                if (x == 935) {
                    x = 55;
                    y = y + 30;
                }
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
