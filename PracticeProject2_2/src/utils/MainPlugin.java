package utils;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import Data.DB;
import io.worldskills.project.MusicInformation;

public class MainPlugin extends JPanel {

	String name;
	public MainPlugin(byte[] image, String name, int getAgeLimit, JFrame frame) {
		this.name = name;
		if (name.length() > 9) {
			name = name.substring(0, 9);
		}
		JLabel title = new JLabel(name);
		
		JLayeredPane layeredPane = new JLayeredPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(title, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
				.addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(layeredPane, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(title, GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE))
		);
		
		JLabel lblNewLabel = new JLabel("");
		if (getAgeLimit == 1) {
			ImageIcon icon1 = new ImageIcon("datafiles\\19금.png");
			lblNewLabel.setIcon(setImageSize(icon1, 24, 24));
			lblNewLabel.setBounds(64, 10, 24, 24);
			layeredPane.add(lblNewLabel);
		}
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 10, 24, 24);
		layeredPane.add(panel);
		
		event(image, frame, layeredPane);
		setLayout(groupLayout);
	}
	
	public void event(byte[] image, JFrame frame, JLayeredPane lp) {
		ImageIcon icon = new ImageIcon(image);
		JLabel label = new JLabel(setImageSize(icon, 100, 96));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				//음원상세정보로 넘어감
				String singer = DB.getString("singer", "music", "m_name", name);
				String lyricist = DB.getString("lyricist", "music", "m_name", name);
				String composer = DB.getString("composer", "music", "m_name", name);
				String album = DB.getString("album", "music", "m_name", name);
				String playtime = DB.getString("playtime", "music", "m_name", name);
				String soundquality = DB.getString("soundquality", "music", "m_name", name);
				byte[] image = DB.getImage("m_name", name);
				
				MusicInformation mc = new MusicInformation(name, singer, lyricist, composer, album, playtime, soundquality, image);
				mc.getFrame().setVisible(true);
				
			}
		});
		label.setBounds(0, 0, 100, 96);
		lp.add(label);
	}
	
	public static ImageIcon setImageSize(ImageIcon icon, int x, int y) {
		Image getImage = icon.getImage();
		Image returnImage = getImage.getScaledInstance(x, y, Image.SCALE_SMOOTH);
		ImageIcon set = new ImageIcon(returnImage);
		return set;
	}
}
