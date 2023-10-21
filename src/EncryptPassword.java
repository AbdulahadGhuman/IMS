import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;

public class EncryptPassword {
    public static byte [] EncryptPassword (String Password) {
        try {
            MessageDigest newDigest = MessageDigest.getInstance("SHA-256");
            return newDigest.digest(Password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
}
