import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Player{
	
	private JFrame frame;
	private JLabel label;
	private File file;
	
	private boolean playing;
	
	private Clip clip;
	private AudioInputStream audioInputStream;
	
	public Player(){
		
		playing = false;
		
		frame = new JFrame("Audio Player(Only supports WAV currently)");
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		label = new JLabel("                                      Audio Player");
		
		JButton button = new JButton("Open File");
		button.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0) {
				FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
			    dialog.setMode(FileDialog.LOAD);
			    dialog.setVisible(true);
			    file = new File(dialog.getDirectory() + dialog.getFile());
			    System.out.println(dialog.getDirectory() + dialog.getFile());
			    String[] fileExt = dialog.getFile().split(".");
			    
			    try{
				  	playSound(file);
				  	playing = true;
				   	label.setText("Playing " + file);
				}catch(Exception e){
			    	label.setText("Audio Player does not support playing " + fileExt[1] + " files");
				}
			    
			}
		});
		
		JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				stop();
			}
			
		});
		
		JButton replayButton = new JButton("Replay");
		replayButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				restart();
			}
		});
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(button);
		panel.add(stopButton);
		panel.add(replayButton);
		
		frame.add(BorderLayout.NORTH, label);
		frame.add(BorderLayout.SOUTH, panel);
		
		frame.setVisible(true);
	}
	
	private void playSound(File f) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		
		if(!playing){
			audioInputStream = AudioSystem.getAudioInputStream(f); 
	          
	        // create clip reference 
	        clip = AudioSystem.getClip(); 
	          
	        // open audioInputStream to the clip 
	        clip.open(audioInputStream); 
	          
	        clip.loop(0); 
		}else{
			playing = false;
			clip.stop(); 
	        clip.close(); 
	        
	        audioInputStream = AudioSystem.getAudioInputStream(f); 
	          
	        // create clip reference 
	        clip = AudioSystem.getClip(); 
	          
	        // open audioInputStream to the clip 
	        clip.open(audioInputStream); 
	          
	        clip.loop(0); 
		}
	}
	
	private void stop(){
		if(playing){
			playing = false;
			clip.stop();
			clip.close();
			label.setText("                                      Audio Player");
		}
	}
	
	private void restart(){
		
		stop();
		playing = true;
		
		if(playing){ 
			try {
				audioInputStream = AudioSystem.getAudioInputStream(file);
			
			          
				clip = AudioSystem.getClip(); 
				          
				clip.open(audioInputStream); 
				          
	            clip.loop(0); 
			}catch(Exception e){}
		}
	}
}