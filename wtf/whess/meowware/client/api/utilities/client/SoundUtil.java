package wtf.whess.meowware.client.api.utilities.client;

import wtf.whess.meowware.client.api.utilities.Utility;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;
import java.io.InputStream;

public final class SoundUtil extends Utility {

    public static synchronized void playSound(String name, float volume, boolean stop) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                InputStream audioSrc = SoundUtil.class.getResourceAsStream("assets/minecraft/helse/sounds/" + name);

                if(audioSrc == null)
                    return;

                BufferedInputStream bufferedIn = new BufferedInputStream(audioSrc);
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(bufferedIn);
                clip.open(inputStream);
                FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume);
                clip.start();
                if (stop) {
                    clip.stop();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
