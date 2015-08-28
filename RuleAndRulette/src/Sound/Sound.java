package Sound;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	public static synchronized void playSound(final URL url) {
		
		new Thread(new Runnable() {
			// The wrapper thread is unnecessary, unless it blocks on the
		    // Clip finishing; see comments.
		    public void run() {
		    	try {
			        Clip clip = AudioSystem.getClip();
			        AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
			        clip.open(inputStream);
			        clip.start(); 
			        System.out.println("Started......");
		    	} catch (Exception e) {
			        System.err.println(e.getMessage());
			        e.printStackTrace();
		    	}
		    }
		}).start();
	  System.out.println("Finished");
	}	
}
