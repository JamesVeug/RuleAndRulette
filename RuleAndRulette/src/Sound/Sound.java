package Sound;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

import GameLogic.Time;

public class Sound {
	
	private static Mixer mixer;
	private static Clip clip;
	private static DataLine.Info dataInfo; 
	
	private static AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
	private static boolean playSounds = true;
	
	private static final float REPEAT_THRESHOLD = 0.1f; // the same sound can not be played closer than 0.1f seconds apart
	private static final HashMap<String, Float> soundPlayedAt = new HashMap<String, Float>();
	
	public static void SetEnabled(boolean status){
		playSounds = status;
	}
	
	public static void setupMixer(){
		Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
		/*for( Mixer.Info info : mixInfos){
			System.out.println(info.getName() + " ... " + info.getDescription());
		}*/
		mixer = AudioSystem.getMixer(mixInfos[0]);
		dataInfo = new DataLine.Info(Clip.class, null);
		/*try{
			clip = (Clip)mixer.getLine(dataInfo);
		}catch(LineUnavailableException e ){
			e.printStackTrace();
		}*/
		
		//AudioFormat f = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
		
	}
	

	public static synchronized void playSound(final URL url) {
		// Don't play sounds
		if( playSounds == false ){
			return;
		}
		
		// Set up for playing sounds
		if( mixer == null ){
			setupMixer();
		}
		
		// Play the sound
		new Thread(new Runnable() {
			// The wrapper thread is unnecessary, unless it blocks on the
		    // Clip finishing; see comments.
		    public void run() {
		    	try {
		    		
		    		/*Clip clip = null;
		    		clip = (Clip)getLine(format, dataInfo);
		    		if( clip == null ){
		    			System.err.println("clip is null!");
		    			return;
		    		}*/
					Clip clip = (Clip)mixer.getLine(dataInfo);
		    		
			        //Clip clip = AudioSystem.getClip();
			        AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
			        clip.open(inputStream);
			        clip.start(); 
			        
			        do{
			        	Thread.sleep(50);
			        }while( clip.isActive());
			        clip.stop();
			        clip.close();
			        
			        
		    	} catch (Exception e) {
			        System.err.println(e.getMessage());
			        e.printStackTrace();
		    	}
		    }
		}).start();
	}
	
	/**
	 * Return true if we should allow playing this sound.
	 * If it's been long enough since we last played the sound.
	 * @param path The path to the sound file.
	 * @return
	 */
	private static synchronized boolean canPlaySound(final String path) {
		if(soundPlayedAt.containsKey(path)) {
			float difference = Time.time - soundPlayedAt.get(path);
			if(difference < REPEAT_THRESHOLD) {
				return false;
			}
		}
		return true;
	}
	
	public static synchronized void playSound(final String path) {
		try {
			if(!canPlaySound(path)) { return; } //if we can't play the sound (played too recently)
			soundPlayedAt.put(path, Time.time);
			playSound(new File(path).toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static SourceDataLine getLine(AudioFormat audioFormat, Info info){
		SourceDataLine dataline = null;
		for (Mixer.Info mixerInfo : AudioSystem.getMixerInfo()) {
		    try {
		        Mixer mixer = AudioSystem.getMixer(mixerInfo);
		        dataline = (SourceDataLine)mixer.getLine(info);
		        if(dataline==null) {
		            continue; //Doesn't support this format
		        }
		        dataline.open(audioFormat);
		        dataline.start();
		    }
		    catch (Exception ex) {
		        //If we get here it's a buggered line, so loop round again
		        continue;
		    }
		    try {
		        dataline.close();
		    }
		    catch (Exception ex) {
		        ex.printStackTrace(); //Shouldn't get here
		    }
		}


		if(dataline==null) {
		    //No dataline capable of *really* playing the stream
			return null;
		}
		else {
		    //We have a non-lying dataline!
			return dataline;
		}
	}
}
