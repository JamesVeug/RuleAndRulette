package Sound;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

public class Sound {
	
	private static Mixer mixer;
	private static Clip clip;
	private static DataLine.Info dataInfo; 
	
	private static AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
	private static boolean playSounds = true;
	
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
	
	public static synchronized void playSound(final String path) {
		try {
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
