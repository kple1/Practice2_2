package io.worldskills.project;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Data.DB;
import utils.MainPlugin;
import utils.PreferPlugin;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PreferPopSong {

	private JFrame frame;
	public static JTextField textField;
	public static StringBuilder sb = new StringBuilder();
	public static int count = 0;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PreferPopSong window = new PreferPopSong();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PreferPopSong() {
		initialize();
	}
	
	public JFrame getFrame() {
		return frame;
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 601, 677);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("선호 POP SONG");
		lblNewLabel.setFont(new Font("나눔고딕", Font.BOLD, 25));
		lblNewLabel.setBounds(196, 10, 190, 45);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("선택");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setFont(new Font("나눔고딕", Font.BOLD, 20));
		lblNewLabel_1.setBounds(12, 60, 44, 33);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setBounds(68, 65, 270, 30);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel reset = new JLabel("");
		reset.setBounds(350, 65, 33, 33);
		frame.getContentPane().add(reset);
		
		JButton btnNewButton = new JButton("선택");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Login login = new Login(sb.toString());
				login.getFrame().setVisible(true);
			}
		});
		btnNewButton.setBounds(392, 65, 106, 31);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("");
		ImageIcon image = new ImageIcon("C:\\Users\\User\\Desktop\\과제2 결과물\\datafiles\\메인.png");
		lblNewLabel_2.setIcon(MainPlugin.setImageSize(image, 151, 45));
		lblNewLabel_2.setBounds(422, 10, 151, 45);
		frame.getContentPane().add(lblNewLabel_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 106, 561, 522);
		frame.getContentPane().add(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 5, 0, 0));
		
		List<Integer> array = DB.getAllInt("m_no", "music");
		for (int i = 0; i< array.size(); i++) {
			String getName = DB.getString("m_name", "music", "m_no", String.valueOf(array.get(i)));
			byte[] getImage = DB.getImage("m_no", String.valueOf(array.get(i)));
			//int getAgeLimit = DB.getInt("agelimit", "music", "m_no", String.valueOf(array.get(i)));
			panel.add(new PreferPlugin(getImage, getName, i + 1));
		}
	}
}
