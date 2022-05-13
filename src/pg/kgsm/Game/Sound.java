package pg.kgsm.Game;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	private AudioClip clip;
	public static final Sound boardHit = new Sound("/boardHit.wav"); 
	public static final Sound playerHit = new Sound("/playerHit.wav"); 	
	public static final Sound point = new Sound("/point.wav"); 	
	
	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		}catch(Throwable e) {
			System.out.println("Erro");
		}	
	}
	
	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		}catch(Throwable e) {
			System.out.println("Erro");
		}
	}
	
}
