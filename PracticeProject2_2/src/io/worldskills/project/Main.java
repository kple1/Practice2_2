package io.worldskills.project;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Data.DB;
import utils.MainPlugin;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		initialize();
	}
	
	public JFrame getFrame() {
		return frame;
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 676, 478);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("SKILL MUSIC(OLD POP SONG)");
		lblNewLabel.setBounds(104, 10, 461, 56);
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 30));
		frame.getContentPane().add(lblNewLabel);
		
		JButton loginB = new JButton("로그인");
		loginB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Login login = new Login();
				login.getFrame().setVisible(true);
			}
		});
		loginB.setBounds(12, 65, 117, 50);
		frame.getContentPane().add(loginB);
		
		JButton registerB = new JButton("회원가입");
		registerB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Register rg = new Register();
				rg.getFrame().setVisible(true);
			}
		});
		registerB.setBounds(141, 65, 117, 50);
		frame.getContentPane().add(registerB);
		
		JButton searchMusicB = new JButton("음원검색");
		searchMusicB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				SearchMusic sm = new SearchMusic();
				sm.getFrame().setVisible(true);
			}
		});
		searchMusicB.setBounds(270, 65, 117, 50);
		frame.getContentPane().add(searchMusicB);
		
		JButton myPageB = new JButton("마이페이지");
		myPageB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MyPage myPage = new MyPage();
				myPage.getFrame().setVisible(true);
			}
		});
		myPageB.setBounds(399, 65, 117, 50);
		frame.getContentPane().add(myPageB);
		
		JButton graphB = new JButton("분석");
		graphB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Graph g = new Graph();
				g.getFrame().setVisible(true);
				frame.dispose();
			}
		});
		graphB.setBounds(528, 65, 117, 50);
		frame.getContentPane().add(graphB);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 125, 633, 304);
		frame.getContentPane().add(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 5, 0, 0));
		
		List<Integer> array = DB.getAllInt("m_no", "music");
		for (int i = 0; i< array.size(); i++) {
			String getName = DB.getString("m_name", "music", "m_no", String.valueOf(array.get(i)));
			byte[] getImage = DB.getImage("m_no", String.valueOf(array.get(i)));
			int getAgeLimit = DB.getInt("agelimit", "music", "m_no", String.valueOf(array.get(i)));
			panel.add(new MainPlugin(getImage, getName, getAgeLimit, frame));
		}
	}
}
