package Sound;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

import GameLogic.Time;

public class Sound {
	
	private static Mixer mixer;
	private static List<Clip> clips;
	private static List<Clip> music;
	private static DataLine.Info dataInfo; 
	
	private static AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
	private static boolean playClips = true;
	private static boolean playMusic = true;
	
	private static final float REPEAT_THRESHOLD = 0.1f; // the same sound can not be played closer than 0.1f seconds apart
	private static final HashMap<String, Float> soundPlayedAt = new HashMap<String, Float>();
	
	// Volume of current soudn and music
	private static float volume_clip = 0.8f; // 1 = 100%, 0 == 0%
	private static float volume_music = 0.9f; // 1 = 100%, 0 == 0%
	
	// When we mute, we want to assign the volume to be this value
	// So when we unmute, we can change back to what we originally was.
	private static float unmuted_volume_clip = 1; // 1 = 100%, 0 == 0%
	private static float unmuted_volume_music = 1; // 1 = 100%, 0 == 0%
	
	public static void muteClips(){
		playClips = false;
		unmuted_volume_clip = volume_clip;
		volume_clip = 0;
		assignClipVolume();
		//System.out.println("Muted clips");
	}
	
	public static void unmuteClips(){
		playClips = true;
		volume_clip = unmuted_volume_clip;
		assignClipVolume();
		//System.out.println("Unmuted clips");
	}
	
	public static void muteMusic(){
		playMusic = false;
		unmuted_volume_music = volume_music;
		volume_music = 0;
		assignMusicVolume();
		//System.out.println("Muted Music");
	}
	
	public static void unmuteMusic(){
		playMusic = true;
		volume_music = unmuted_volume_music;
		assignMusicVolume();
		//System.out.println("Unmuted Music");
	}
	
	public static boolean isMusicMuted(){
		return !playMusic;
	}
	
	public static boolean isClipsMuted(){
		return !playClips;
	}
	
	public static void setupMixer(){
		Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
		mixer = AudioSystem.getMixer(mixInfos[0]);
		dataInfo = new DataLine.Info(Clip.class, null);
		clips = new ArrayList<>();
		music = new ArrayList<>();
	}

	public static synchronized void playSound(final URL url, final boolean loops, final boolean isMusic) {
		// Don't play sounds
		if( (isMusic && !playMusic) || (!isMusic && !playClips) ){
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
		    		
					Clip clip = (Clip)mixer.getLine(dataInfo);
					
					if( isMusic ){
						music.add(clip);
					}
					else{
						clips.add(clip);
					}
		    		
			        //Clip clip = AudioSystem.getClip();
			        AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
			        clip.open(inputStream);
			        clip.start(); 
			        
			        // Change volume
			        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			        if( isMusic ){
			        	gainControl.setValue(getMusicValue(clip));
			        }
			        else{
			        	gainControl.setValue(getClipValue(clip));
			        }
		        	
			        // Loop or no loop
			        // That is the question
		        	if( loops ){
		        		clip.loop(Clip.LOOP_CONTINUOUSLY);
		        	}
		        	else{
		        		clip.start();
		        	}
			        
			        do{
			        	Thread.sleep(50);
			        }while( clip.isActive());
			        clip.stop();
			        clip.close();
			        clips.remove(clip);
			        
			        
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
	
	public static synchronized void playSound(final String path, boolean loops, boolean music) {
		try {
			if(!canPlaySound(path)) { return; } //if we can't play the sound (played too recently)
			soundPlayedAt.put(path, Time.time);
			playSound(new File(path).toURL(), loops, music);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static synchronized void playSound(final String path) {
		playSound(path, false, false);
	}
	
	public static synchronized void playMusic(final String path) {
		playSound(path, true, true);
	}

	public static void increaseMusicVolume() {
		if( !isMusicMuted() ){
			volume_music = Math.min(1,volume_music+0.1f);
			assignMusicVolume();
		}
	}

	public static void decreaseMusicVolume() {
		if( !isMusicMuted() ){
			volume_music = Math.max(0,volume_music-0.1f);
			assignMusicVolume();
		}
	}
	
	public static void increaseClipVolume() {
		if( !isClipsMuted() ){
			volume_clip = Math.min(1,volume_clip+0.1f);
			assignClipVolume();
		}
	}

	public static void decreaseClipVolume() {
		if( !isClipsMuted() ){
			volume_clip = Math.max(0,volume_clip-0.1f);
			assignClipVolume();
		}
	}
	
	/**
	 * Returns a float from 0 to 1 that determines the percentage of the volume.
	 * Clips are any sound that is not music
	 * @return 0.1.....1.0
	 */
	public static float getClipVolume(){
		return volume_clip;
	}
	
	/**
	 * Returns a float from 0 to 1 that determines the percentage of the volume.
	 * @return 0.1.....1.0
	 */
	public static float getMusicVolume(){
		return volume_music;
	}
	
	private static void assignClipVolume(){
        //System.out.println("setting clip volume to "+volume_clip);
        for(Line line : clips){
            try{
        		FloatControl control = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
                float value = getClipValue(line);
                control.setValue(value);
                //System.out.println("	"+value);
            }
            catch(java.lang.IllegalArgumentException e) { continue; }
         }
    }
	
	private static float getClipValue(Line line){
		FloatControl control = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
        float diff = control.getMaximum()-control.getMinimum();
        float change = diff*volume_clip;
        float value = control.getMinimum()+change;
        return limit(control,value);
	}
	
	private static float getMusicValue(Line line){
		FloatControl control = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
        float diff = control.getMaximum()-control.getMinimum();
        float change = diff*volume_music;
        float value = control.getMinimum()+change;
        return limit(control,value);
	}
	
	private static void assignMusicVolume(){
        //System.out.println("setting music volume to "+volume_music);
        for(Line line : music){
            try{
                FloatControl control = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
                float value = getMusicValue(line);
                control.setValue(limit(control,value));
                //System.out.println("	"+value);
            }
            catch(java.lang.IllegalArgumentException e) { continue; }
         }
    }
	
	public static void stopAllSounds(){
		while(!clips.isEmpty()){
			Clip clip = clips.get(clips.size()-1);
			
			clip.stop();
			clip.close();
			
			// Remove the clip
			clips.remove(clips.size()-1);
        }
	}
	
	public static void stopAllMusic(){
		while(!music.isEmpty()){
			Clip clip = music.get(music.size()-1);
			
			clip.stop();
			clip.close();
			
			// Remove the clip
			music.remove(music.size()-1);
        }
	}

	private static float limit(FloatControl control,float level){ 
		return Math.min(control.getMaximum(), Math.max(control.getMinimum(), level)); 
	}
}
