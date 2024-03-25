package io.worldskills.project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import utils.MainPlugin;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import Data.DB;

public class MusicInformation {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MusicInformation window = new MusicInformation();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JFrame getFrame() {
		return frame;
	}
	
	String name;
	String singer;
	String lyricist;
	String composer;
	String album;
	String playtime;
	String soundquality;
	byte[] image;
	public MusicInformation(String name, String singer, String lyricist, String composer, String album, String playtime, String soundquality, byte[] image) {
		this.name = name;
		this.singer = singer;
		this.lyricist = lyricist;
		this.composer = composer;
		this.album = album;
		this.playtime = playtime;
		this.soundquality = soundquality;
		this.image = image;
		initialize();
	}
	
	public MusicInformation() {
		initialize();
	}

	Timer timer;
	JProgressBar progressBar = new JProgressBar();
	JLabel time;
	JLabel sound;

	int min;
	int sec;	
	int count = 0;
	int result;
	public void timer() {
		if (count == 0) {
			min = Integer.parseInt(playtime.substring(0, 2));
			sec = Integer.parseInt(playtime.substring(3, 5));
			result = (min * 60) + sec;
		}
		
		int musicNum = DB.getInt("m_no", "music", "m_name", name);
		String getStopTime = DB.lastlisten(Login.id.getText(), musicNum);
		if (!getStopTime.isEmpty()) {
			min = Integer.parseInt(getStopTime.substring(0, 2));
			sec = Integer.parseInt(getStopTime.substring(3, 5));
			result = (min * 60) + sec;
		}
		
		timer = new Timer(1000, e1 -> {
			progressBar.setMinimum(0);
			progressBar.setMaximum(result);
			progressBar.setValue(++count);
			if (sec == 0) {
				min -= 1;
				sec = 59;
			} else {
				--sec;
			}
			
			time.setText(min + ":" + sec);
			if (count == result) {
				timer.stop();
				JOptionPane.showMessageDialog(null, "음원 재생이 완료되었습니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		timer.start();
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 659, 462);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		ImageIcon image1 = new ImageIcon("datafiles\\재생중지.png");
		ImageIcon image2 = new ImageIcon("datafiles\\재생중.png");
		
		JButton listen = new JButton("듣기");
		listen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listen.getText().equals("듣기")) {
					listen.setText("멈춤");
					sound.setIcon(MainPlugin.setImageSize(image2, 25, 25));
					timer();
				} else {
					listen.setText("듣기");
					JOptionPane.showMessageDialog(null, "음원 재생이 중지되었습니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
					sound.setIcon(MainPlugin.setImageSize(image1, 25, 25));
					timer.stop();
				}
			}
		});
		listen.setBounds(329, 338, 109, 37);
		frame.getContentPane().add(listen);
		
		progressBar.setBounds(0, 399, 612, 24);
		frame.getContentPane().add(progressBar);
		
		time = new JLabel(playtime);
		time.setHorizontalAlignment(SwingConstants.RIGHT);
		time.setBounds(555, 383, 57, 15);
		frame.getContentPane().add(time);
		
		sound = new JLabel(MainPlugin.setImageSize(image1, 25, 25));
		sound.setBounds(613, 399, 30, 24);
		frame.getContentPane().add(sound);
		
		JLabel title = new JLabel("음원상세정보");
		title.setFont(new Font("나눔고딕", Font.BOLD, 27));
		title.setBounds(244, 10, 156, 32);
		frame.getContentPane().add(title);
		
		JLabel name = new JLabel(this.name);
		name.setFont(new Font("나눔고딕", Font.BOLD, 20));
		name.setBounds(12, 69, 156, 24);
		frame.getContentPane().add(name);
		
		JLabel mainLogo = new JLabel("");
		mainLogo.setIcon(new ImageIcon("datafiles\\메인.png"));
		mainLogo.setBounds(519, 69, 112, 32);
		frame.getContentPane().add(mainLogo);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 111, 643, 1);
		frame.getContentPane().add(panel);
		
		JPanel imagePanel = new JPanel();
		ImageIcon image = new ImageIcon(this.image);
		JLabel label = new JLabel(MainPlugin.setImageSize(image, 203, 187));
		label.setBounds(0, 0, 203, 187);
		imagePanel.add(label);
		imagePanel.setBounds(10, 120, 203, 187);
		frame.getContentPane().add(imagePanel);
		imagePanel.setLayout(null);
		
		JLabel ar = new JLabel("아티스트");
		ar.setFont(new Font("나눔고딕", Font.PLAIN, 18));
		ar.setBounds(225, 122, 68, 24);
		frame.getContentPane().add(ar);
		
		JLabel ly = new JLabel("작사");
		ly.setFont(new Font("나눔고딕", Font.PLAIN, 18));
		ly.setBounds(225, 154, 68, 24);
		frame.getContentPane().add(ly);
		
		JLabel co = new JLabel("작곡");
		co.setFont(new Font("나눔고딕", Font.PLAIN, 18));
		co.setBounds(225, 188, 68, 24);
		frame.getContentPane().add(co);
		
		JLabel al = new JLabel("앨범");
		al.setFont(new Font("나눔고딕", Font.PLAIN, 18));
		al.setBounds(225, 215, 68, 24);
		frame.getContentPane().add(al);
		
		JLabel pt = new JLabel("재생시간");
		pt.setFont(new Font("나눔고딕", Font.PLAIN, 18));
		pt.setBounds(225, 249, 68, 24);
		frame.getContentPane().add(pt);
		
		JLabel sq = new JLabel("음질");
		sq.setFont(new Font("나눔고딕", Font.PLAIN, 18));
		sq.setBounds(225, 283, 68, 24);
		frame.getContentPane().add(sq);
		
		JLabel singer = new JLabel(this.singer);
		singer.setBounds(329, 122, 302, 24);
		frame.getContentPane().add(singer);
		
		JLabel lyricist = new JLabel(this.lyricist);
		lyricist.setBounds(329, 154, 302, 24);
		frame.getContentPane().add(lyricist);
		
		JLabel composer = new JLabel(this.composer);
		composer.setBounds(329, 188, 302, 24);
		frame.getContentPane().add(composer);
		
		JLabel album = new JLabel(this.album);
		album.setBounds(329, 215, 302, 24);
		frame.getContentPane().add(album);
		
		JLabel playtime = new JLabel(this.playtime);
		playtime.setBounds(329, 249, 302, 24);
		frame.getContentPane().add(playtime);
		
		JLabel soundquality = new JLabel(this.soundquality);
		soundquality.setBounds(329, 283, 302, 24);
		frame.getContentPane().add(soundquality);
		
		JButton btnNewButton = new JButton("담기");
		btnNewButton.setBounds(206, 338, 111, 37);
		frame.getContentPane().add(btnNewButton);
	}
}
