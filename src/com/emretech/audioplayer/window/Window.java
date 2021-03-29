package com.emretech.audioplayer.window;

import javax.sound.sampled.*;
import javax.swing.*;

import com.emretech.audioplayer.framework.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Color;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Window {
	private JTextField fileName;
	private JButton btnPlay;
	private JButton btnStop;
	private JButton btnLoop;
	private JLabel label;
	private Music music;
	private JButton btnSend;
	private JLabel lblFileName;
	private JTextField volume;
	private JSlider slider;

	private boolean running = true;
	private static boolean looping = false;
	private String tick = null;
	private JButton btnGetMyEmail;

	public Window() {
		int r = 255;
		int g = 255;
		int b = 255;
		

		JFrame frame = new JFrame("Audio Player Prototype");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setBackground(new Color(r,g,b));
		frame.getContentPane().setBackground(new Color(r,g,b));

		frame.setResizable(false);
		frame.getContentPane().setLayout(null);

		btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				if (btn.getText().equalsIgnoreCase("Pause")) {
					music.pause();
					btn.setText("Play");
				}
				else if (btn.getText().equalsIgnoreCase("Play")) {
					music.play();
					btn.setText("Pause");
					if (music.clip.getMicrosecondPosition() >= music.clip.getMicrosecondLength())
						music.clip.setMicrosecondPosition(0L);
				}	
			}
		});
		btnPlay.setBounds(83, 121, 117, 29);
		frame.getContentPane().add(btnPlay);

		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				music.stop();
				btnPlay.setText("Play");
			}
		});
		btnStop.setBounds(341, 121, 117, 29);
		frame.getContentPane().add(btnStop);

		btnLoop = new JButton("Loop");
		btnLoop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				if (btn.getText().equalsIgnoreCase("Loop")) {
					music.loop();
					btn.setText("Unloop");
					setLooping(true);
					if (btnPlay.getText() != "Pause")
						btnPlay.setText("Pause");

				}
				else if (btn.getText().equalsIgnoreCase("Unloop")) {
					music.unloop();
					btn.setText("Loop");
					setLooping(false);
					if (btnPlay.getText() != "Pause")
						btnPlay.setText("Pause");
				}
			}
		});
		btnLoop.setBounds(212, 121, 117, 29);
		frame.getContentPane().add(btnLoop);

		fileName = new JTextField();
		fileName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filename = fileName.getText();
				String name = filename;
				filename = filename + ".wav";
				lblFileName.setText(name);
				try {
					music = new Music(filename);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					System.out.println(e1.getMessage());
				}
				if (btnPlay.getText().equals("Play"))
					btnPlay.setText("Play");
			}
		});
		fileName.setBounds(205, 50, 304, 26);
		frame.getContentPane().add(fileName);
		fileName.setColumns(10);

		label = new JLabel("Enter the file name here:");
		label.setBounds(34, 55, 159, 16);
		frame.getContentPane().add(label);

		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filename = fileName.getText();
				String name = filename;
				filename = filename + ".wav";
				lblFileName.setText(name);
				try {
					music = new Music(filename);
					
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					System.out.println(e1.getMessage());
				}
				if (btnPlay.getText() != "play") 
					tick = "ticked";
				
				System.out.println(tick);
			}
		});
		btnSend.setBounds(314, 70, 117, 29);
		frame.getContentPane().add(btnSend);

		JLabel lblVolume = new JLabel("Volume:");
		lblVolume.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVolume.setBounds(159, 167, 61, 16);
		frame.getContentPane().add(lblVolume);

		lblFileName = new JLabel("");
		lblFileName.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblFileName.setHorizontalAlignment(SwingConstants.CENTER);
		lblFileName.setBounds(6, 6, 503, 32);
		frame.getContentPane().add(lblFileName);

		volume = new JTextField();
		volume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double newVolume = 0;
				try {
					newVolume = Double.parseDouble(volume.getText());
				} catch (Exception e2) {
					String message = e2.getMessage();
					System.out.println("Error: Volume could not be set because of this error: " + message);
					newVolume = 1;
					volume.setText("1");
				}
				if (newVolume > 2) {
					newVolume = 2;
					volume.setText("2");
				}
				else if (newVolume < 0) {
					newVolume = 0;
					volume.setText("0");
				}
				else if (newVolume < 0.0001) {
					newVolume = 0.0001;
					volume.setText("0.00001");
				}
				try {
					float floatNewVolume = (float) newVolume;
					music.setVolume(floatNewVolume);
					System.out.println(floatNewVolume * 100);
					System.out.println((int) floatNewVolume * 100);
					slider.setValue((int) floatNewVolume * 100);
				} catch (Exception e1) {
					System.out.println("Error: Volume could not be set because of this error: " + e1.getMessage());
				}

			}
		});
		volume.setBounds(222, 162, 130, 26);
		frame.getContentPane().add(volume);
		volume.setColumns(10);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				running = false;
				System.exit(1);
			}
		});
		btnExit.setBounds(392, 319, 117, 29);
		frame.getContentPane().add(btnExit);

		JLabel lblSendFeedback = new JLabel("Send Feedback:");
		lblSendFeedback.setBounds(222, 242, 102, 16);
		frame.getContentPane().add(lblSendFeedback);

		JButton discordFeedback = new JButton("Join my Discord Server");
		discordFeedback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop desktop = java.awt.Desktop.getDesktop();
					URI oURL = new URI("https://discordapp.com");
					desktop.browse(oURL);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		discordFeedback.setBounds(181, 266, 179, 29);
		frame.getContentPane().add(discordFeedback);

		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				double newVolume = 0;
				try {
					newVolume = ((double) slider.getValue() / 100.0);
					//System.out.println(newVolume);
				} catch (Exception e2) {
					System.out.println("Error: Volume could not be set because of this error: " + e2.getMessage());
					newVolume = 1;
					volume.setText("1");
				}

				try {
					float floatNewVolume = (float) newVolume;
					music.setVolume(floatNewVolume);
					volume.setText("" + newVolume);
				} catch (Exception e1) {
					System.out.println("Error: Volume could not be set because of this error: " + e1.getMessage());
				} 
			}
		});
		slider.setValue(0);
		slider.setPaintTicks(true);
		slider.setMaximum(205);
		slider.setBounds(192, 201, 190, 29);
		frame.getContentPane().add(slider);

		frame.setLocationRelativeTo(null);
		frame.setSize(515, 376);
		frame.setVisible(true);

		while (running) {
			if (music.clip.getMicrosecondPosition() == music.clip.getMicrosecondLength()) {

			}
			if (tick.equalsIgnoreCase("ticked")) {
				String playString = btnPlay.getText();
				if (playString.equalsIgnoreCase("Pause")) {
					btnPlay.setText("Play");
				}
			}
		}
	}

	public static boolean isLooping() {
		return looping;
	}

	public static void setLooping(boolean looping) {
		Window.looping = looping;
	}

	public static void main(String[] args) {
		new Window();
	}
}
