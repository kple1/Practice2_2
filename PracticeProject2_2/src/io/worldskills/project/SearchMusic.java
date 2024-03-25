package io.worldskills.project;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Data.DB;
import utils.MainPlugin;
import utils.SearchMusicPlugin;

public class SearchMusic {

	private JFrame frame;
	private JTextField search;

	public JFrame getFrame() {
		return frame;
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchMusic window = new SearchMusic();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SearchMusic() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("datafiles\\마이크.png"));
		frame.setTitle("음원검색");
		frame.setBounds(100, 100, 650, 588);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JLabel searchLabel = new JLabel("음원검색");
		searchLabel.setFont(new Font("나눔고딕", Font.BOLD, 25));
		searchLabel.setBounds(267, 10, 105, 29);
		frame.getContentPane().add(searchLabel);
		
		JLabel mainLogo = new JLabel("");
		mainLogo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				Main main = new Main();
				main.getFrame().setVisible(true);
			}
		});
		ImageIcon icon2 = new ImageIcon("datafiles\\메인.png");
		mainLogo.setIcon(MainPlugin.setImageSize(icon2, 131, 39));
		mainLogo.setBounds(491, 10, 131, 39);
		frame.getContentPane().add(mainLogo);
		
		JLabel comboLabel = new JLabel("검색기준");
		comboLabel.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		comboLabel.setBounds(12, 71, 82, 24);
		frame.getContentPane().add(comboLabel);
		
		JComboBox<String> com1 = new JComboBox<String>();
		com1.addItem("제목");
		com1.addItem("아티스트");
		com1.setBounds(91, 71, 105, 27);
		frame.getContentPane().add(com1);
		
		search = new JTextField();
		search.setBounds(208, 71, 164, 26);
		frame.getContentPane().add(search);
		search.setColumns(10);
		
		JComboBox<String> com2 = new JComboBox<String>();
		com2.addItem("오름차순");
		com2.addItem("내림차순");
		com2.setBounds(540, 127, 82, 22);
		frame.getContentPane().add(com2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 159, 610, 380);
		frame.getContentPane().add(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 6, 0, 0));
		createPanel(panel, com1, com2);
		
		JLabel searchBt = new JLabel("");
		searchBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				createPanel(panel, com1, com2);
			}
		});
		ImageIcon icon1 = new ImageIcon("C:\\Users\\User\\Desktop\\과제2 결과물\\datafiles\\검색.png");
		searchBt.setIcon(MainPlugin.setImageSize(icon1, 24, 24));
		searchBt.setBounds(382, 71, 24, 24);
		frame.getContentPane().add(searchBt);
		
		JLabel sortLabel = new JLabel("정렬기준");
		sortLabel.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		sortLabel.setBounds(475, 125, 56, 24);
		frame.getContentPane().add(sortLabel);
	}
	
	int result;
	List<String> getAlbumName = DB.getAllString("m_name", "music");
	List<String> getSinger = DB.getAllString("singer", "music");
	List<byte[]> getImage = DB.getAllImage();
	public void createPanel(JPanel panel, JComboBox<String> com1, JComboBox<String> com2) {
		panel.removeAll();
		for (int i = 0; i < getAlbumName.size(); i++) {
			String getBirth = DB.getString("birth", "user", "id", Login.id.getText());
			int getYear = Integer.parseInt(getBirth.substring(0, 4));
			int getMonth = Integer.parseInt(getBirth.substring(5, 7));
			int getDay = Integer.parseInt(getBirth.substring(8, 10));
			
			int year = LocalDateTime.now().getYear();
			int month = LocalDateTime.now().getMonthValue();
			int day = LocalDateTime.now().getDayOfMonth();
			
			if (getYear >= year && getMonth >= month && getDay > day) {
				result = year - getYear;
			}
			
			//다 upper해서 일치하게 만들기
			if (getAlbumName.get(i).toUpperCase().contains(search.getText().toUpperCase()) || search.getText().isEmpty()) {
				if (com1.getSelectedItem().equals("제목") && com2.getSelectedItem().equals("오름차순")) {
					Collections.sort(getSinger);
					Collections.sort(getAlbumName);
				} else if (com1.getSelectedItem().equals("제목") && com2.getSelectedItem().equals("내림차순")) {
					Collections.sort(getSinger, Collections.reverseOrder());
					Collections.sort(getAlbumName, Collections.reverseOrder());
				}
				
				byte[] getImage = DB.getImage("m_name", getAlbumName.get(i));
				panel.add(new SearchMusicPlugin(getImage, getAlbumName.get(i), result, getSinger.get(i), frame));
				
			} else if (getSinger.get(i).toUpperCase().contains(search.getText().toUpperCase()) || search.getText().isEmpty()) {
				if (com1.getSelectedItem().equals("아티스트") && com2.getSelectedItem().equals("오름차순")) {
					Collections.sort(getSinger);
					Collections.sort(getAlbumName);
				} else if (com1.getSelectedItem().equals("아티스트") && com2.getSelectedItem().equals("내림차순")) {
					Collections.sort(getSinger, Collections.reverseOrder());
					Collections.sort(getAlbumName, Collections.reverseOrder());
				}
				
				byte[] getImage = DB.getImage("singer", getSinger.get(i));
				panel.add(new SearchMusicPlugin(getImage,getAlbumName.get(i), result, getSinger.get(i), frame));
      		}
		}
		panel.revalidate();
		panel.repaint();
	}
}
