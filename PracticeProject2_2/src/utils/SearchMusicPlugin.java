package utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import Data.DB;
import io.worldskills.project.Login;
import io.worldskills.project.MusicInformation;

public class SearchMusicPlugin extends JPanel {

	String name;
	String longName;
	public SearchMusicPlugin(byte[] getImage, String a_name, int getAge, String arti, JFrame frame) {
		name = a_name;
		longName = a_name;
		JLabel downloadLogo = new JLabel("");
		downloadLogo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String getUserNo = DB.getString("u_no", "user", "id", Login.id.getText());
				String getMusicNo = DB.getString("m_no", "music", "m_name", name);
				DB.insertPlayList(getUserNo, getMusicNo);
				JOptionPane.showMessageDialog(null, "음원이 저장되었습니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		ImageIcon icon3 = new ImageIcon("datafiles\\담기.png");
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				downloadLogo.setVisible(true);
				setToolTipText("<html>아티스트: " + arti +  "<br>제목: " + name + "<html/>");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				downloadLogo.setVisible(false);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				String singer = DB.getString("singer", "music", "m_name", longName);
				String lyricist = DB.getString("lyricist", "music", "m_name", longName);
				String composer = DB.getString("composer", "music", "m_name", longName);
				String album = DB.getString("album", "music", "m_name", longName);
				String playtime = DB.getString("playtime", "music", "m_name", longName);
				String soundquality = DB.getString("soundquality", "music", "m_name", longName);

				MusicInformation mc = new MusicInformation(longName, singer, lyricist, composer, album, playtime, soundquality, getImage);
				mc.getFrame().setVisible(true);
			}
		});
		JLabel lblNewLabel = new JLabel("");
		ImageIcon icon1 = new ImageIcon("datafiles\\19금.png");
		if (getAge < 20) {
			if (DB.getInt("agelimit", "music", "m_name", name) == 1) {
				lblNewLabel.setIcon(MainPlugin.setImageSize(icon1, 26, 26));
			}
		}
		
		if (name.length() > 9) {
			name = name.substring(0, 9) + "...";
		}
		
		JLabel namea = new JLabel(name);
		namea.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLayeredPane lPane = new JLayeredPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(namea, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
				.addComponent(lPane, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lPane, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(namea, GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE))
		);
		
		lblNewLabel.setBounds(52, 10, 26, 26);
		lPane.add(lblNewLabel);
		
		downloadLogo.setVisible(false);
		downloadLogo.setIcon(MainPlugin.setImageSize(icon3, 38, 36));
		downloadLogo.setBounds(52, 46, 38, 36);
		lPane.add(downloadLogo);
		
		ImageIcon icon2 = new ImageIcon(getImage);
		JLabel label = new JLabel(MainPlugin.setImageSize(icon2, 90, 76));
		label.setBounds(0, 0, 90, 82);
		lPane.add(label);
		setLayout(groupLayout);
		
	}
}
