package vista;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class ReproducirSonido {
    public static void reproducir() {
        try {
            File archivo = new File("sounds/disparo.wav");
            AudioInputStream audio = AudioSystem.getAudioInputStream(archivo);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
