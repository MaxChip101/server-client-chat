import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class NotificationManager {

    static Clip notification_sound;

    public static void setNotification_sound(String path) {
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(path));
            AudioFormat audioFormat = inputStream.getFormat();
            DataLine.Info clipInfo = new DataLine.Info(Clip.class, audioFormat);
            Clip newClip = (Clip) AudioSystem.getLine(clipInfo);
            newClip.open(inputStream);
            notification_sound = newClip;
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to set sound: " + e.getMessage());
        }
    }
    public static void notification() {
        if (SettingsManager.readRuleVal("notifications").equals("true")) {
            notification_sound.setMicrosecondPosition(0);
            notification_sound.start();
        }
    }

}
