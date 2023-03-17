import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    private URL[] sounds;
    private URL[] musicTracks;

    private AudioInputStream ais;
    private Clip clip;

    public Sound() {
        initSounds();
        initMusicTracks();
    }

    private void initSounds() {
        sounds = new URL[10];

        try {
            sounds[0] = getClass().getResource("sound_effects/cannon_pop.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initMusicTracks() {
        musicTracks = new URL[10];

        try {
            musicTracks[0] = getClass().getResource("music/game_loop.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSoundEffect(int index) {
        try {
            clip = null;
            ais = AudioSystem.getAudioInputStream(sounds[index]);
            clip = AudioSystem.getClip();

            clip.open(ais);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playMusic(int index) {
        try {
            clip = null;
            ais = AudioSystem.getAudioInputStream(musicTracks[index]);
            clip = AudioSystem.getClip();

            clip.open(ais);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pauseMusic() {

    }

    public void unPauseMusic() {

    }
}
