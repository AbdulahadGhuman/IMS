import java.awt.Color;
import java.io.File;
import javax.swing.JFrame;
public class IMS {
    static String Username;
    static JFrame IMSFrame;
    public static void createIMS () {
        IMSFrame = new JFrame ("Username: " + RegisterOrLogin.LoginUsername);
        IMSFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        IMSFrame.setSize(1000, 900);
        IMSFrame.setLayout(null);
        IMSFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
        ReadUsernames.ReadAccountsFile(IMSFrame);
        ReadUsernames newUsernames = new ReadUsernames();
        ReadPhrases.ReadPhrasesFile(IMSFrame);
        LoadMessages.LoadAllMessages(IMSFrame, new File ("AllMessages\\" + RegisterOrLogin.LoginUsername + "\\UnreadMessages\\"));
        SendMessage.SendMessage(IMSFrame);
        newUsernames.start();
        LoadMessages newMessages = new LoadMessages();
        newMessages.start();
        IMSFrame.setVisible(true);
    }
}
