package utils;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import io.worldskills.project.PreferPopSong;

public class PreferPlugin extends JPanel {

	String albumName;
	int num;
	byte[] image;
	public PreferPlugin(byte[] image, String albumName, int num) {
		this.albumName = albumName;
		this.num = num;
		this.image = image;
		init();
	}
	
	public void init() {
		JPanel imagePanel = new JPanel();
		imagePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (PreferPopSong.count < 4) {
					if (PreferPopSong.count >= 1) {
						PreferPopSong.sb.append("," + num);
					} else {
						PreferPopSong.sb.append(num);
					}
					++PreferPopSong.count;
					PreferPopSong.textField.setText(String.valueOf(PreferPopSong.sb));
					setBorder(new LineBorder(Color.RED));
				} else {
					JOptionPane.showMessageDialog(null, "선호 음원 4개를 선택하세요.", "경고", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		ImageIcon image1 = new ImageIcon(image);
		JLabel label = new JLabel(MainPlugin.setImageSize(image1, 100, 100));
		label.setBounds(0, 0, 100, 100);
		imagePanel.add(label);

		JLabel title = new JLabel(albumName);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(imagePanel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
				.addComponent(title, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(imagePanel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE).addGap(2)
						.addComponent(title)));
		setLayout(groupLayout);
	}
}
