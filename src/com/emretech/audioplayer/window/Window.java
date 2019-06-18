package com.emretech.audioplayer.window;

import javax.sound.sampled.*;
import javax.swing.*;

import com.emretech.audioplayer.framework.Music;
import java.awt.event.*;
import java.io.IOException;
import java.awt.event.ActionEvent;
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
				}	
			}
		});
		btnPlay.setBounds(34, 121, 117, 29);
		frame.getContentPane().add(btnPlay);
		
		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				music.stop();
				btnPlay.setText("Play");
			}
		});
		btnStop.setBounds(285, 121, 117, 29);
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
		btnLoop.setBounds(167, 121, 117, 29);
		frame.getContentPane().add(btnLoop);
		
		fileName = new JTextField();
		fileName.setBounds(167, 50, 277, 26);
		frame.getContentPane().add(fileName);
		fileName.setColumns(10);
		
		label = new JLabel("Enter the file name here:");
		label.setBounds(6, 55, 159, 16);
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
		btnSend.setBounds(250, 80, 117, 29);
		frame.getContentPane().add(btnSend);
		
		JLabel lblVolume = new JLabel("Volume:");
		lblVolume.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVolume.setBounds(90, 182, 61, 16);
		frame.getContentPane().add(lblVolume);
		
		lblFileName = new JLabel("");
		lblFileName.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblFileName.setHorizontalAlignment(SwingConstants.CENTER);
		lblFileName.setBounds(6, 6, 438, 32);
		frame.getContentPane().add(lblFileName);
		
		volume = new JTextField();
		volume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double newVolume = Double.parseDouble(volume.getText());
				if (newVolume > 2) {
					newVolume = 2;
					volume.setText("2");
				}
				else if (newVolume < 0) {
					newVolume = 0;
					volume.setText("0");
				}
				float floatNewVolume = (float) newVolume;
				
				music.setVolume(floatNewVolume);
			}
		});
		volume.setBounds(154, 177, 130, 26);
		frame.getContentPane().add(volume);
		volume.setColumns(10);
		
		frame.setLocationRelativeTo(null);
		frame.setSize(450, 250);
		frame.setVisible(true);
		
	}
	
	
	
	public static void main(String[] args) {
		new Window();
	}
}
