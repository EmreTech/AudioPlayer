package com.emretech.audioplayer.window;

import javax.sound.sampled.*;
import javax.swing.*;

import com.emretech.audioplayer.framework.Music;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.awt.event.ActionEvent;
import java.awt.Desktop;
import java.awt.Font;

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
	public Window() {
		JFrame frame = new JFrame("Audio Player Prototype");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				if (btn.getText() == "Pause") {
					music.pause();
					btn.setText("Play");
				}
				else if (btn.getText() == "Play") {
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
				if (btn.getText() == "Loop") {
					music.loop();
					btn.setText("Unloop");
					if (btnPlay.getText() != "Pause")
						btnPlay.setText("Pause");
					
				}
				else if (btn.getText() == "Unloop") {
					music.unloop();
					btn.setText("Loop");
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
					System.out.println("Error: Volume could not be set because of this error: " + e2.getMessage());
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
				System.exit(1);
			}
		});
		btnExit.setBounds(392, 299, 117, 29);
		frame.getContentPane().add(btnExit);
		
		JLabel lblSendFeedback = new JLabel("Send Feedback:");
		lblSendFeedback.setBounds(232, 200, 102, 16);
		frame.getContentPane().add(lblSendFeedback);
		
		JButton emailFeedback = new JButton("Send Email");
		emailFeedback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblFileName.setText("This function does not work yet");
			}
		});
		emailFeedback.setBounds(217, 228, 117, 29);
		frame.getContentPane().add(emailFeedback);
		
		JButton discordFeedback = new JButton("Join my Discord Server");
		discordFeedback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop desktop = java.awt.Desktop.getDesktop();
					URI oURL = new URI("https://discord.gg/H5NpyHW");
					desktop.browse(oURL);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		discordFeedback.setBounds(186, 257, 179, 29);
		frame.getContentPane().add(discordFeedback);
		
		frame.setLocationRelativeTo(null);
		frame.setSize(515, 356);
		frame.setVisible(true);
		
	}
	
	
	
	public static void main(String[] args) {
		new Window();
	}
}
