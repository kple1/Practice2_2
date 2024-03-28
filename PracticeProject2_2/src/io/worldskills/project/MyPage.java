package io.worldskills.project;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Data.DB;
import utils.ImageRender;
import utils.MainPlugin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyPage {

	private JFrame frame;
	private JTable playList;
	private JTable listenList;
	JPopupMenu pm = new JPopupMenu();
	JMenuItem remove = new JMenuItem("삭제");

	public JFrame getFrame() {
		return frame;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyPage window = new MyPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MyPage() {
		initialize();
	}

	int playListCount = 0;
	int listenListCount = 0;
	int resultCount = 0;

	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(
				Toolkit.getDefaultToolkit().getImage("C:\\Users\\User\\Desktop\\과제2 결과물\\datafiles\\마이크.png"));
		frame.setTitle("마이페이지");
		frame.setBounds(100, 100, 656, 441);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		JLabel lblNewLabel = new JLabel("마이페이지");
		lblNewLabel.setFont(new Font("나눔고딕", Font.BOLD, 25));
		lblNewLabel.setBounds(263, 10, 125, 43);
		frame.getContentPane().add(lblNewLabel);

		JLabel mainLogo = new JLabel("");
		ImageIcon icon = new ImageIcon("C:\\Users\\User\\Desktop\\과제2 결과물\\datafiles\\메인.png");
		mainLogo.setIcon(MainPlugin.setImageSize(icon, 106, 36));
		mainLogo.setBounds(522, 10, 106, 36);
		frame.getContentPane().add(mainLogo);

		JButton userInfoEdit = new JButton("회원 정보수정");
		userInfoEdit.setBounds(504, 56, 125, 36);
		frame.getContentPane().add(userInfoEdit);

		String name = DB.getString("u_name", "user", "id", Login.id.getText());
		JLabel userName = new JLabel(name + " 님");
		userName.setFont(new Font("굴림", Font.PLAIN, 20));
		userName.setBounds(12, 54, 99, 36);
		frame.getContentPane().add(userName);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 97, 616, 270);
		frame.getContentPane().add(tabbedPane);

		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("PlayList", null, scrollPane, null);

		playList = new JTable();
		playList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int getCol = playList.getSelectedColumn();
				if (getCol == 2 || getCol == 3) {
					if (e.getButton() == e.BUTTON1) {
						playList.setSelectionBackground(Color.yellow);
					}

					if (e.getButton() == e.BUTTON3) {
						pm.add(remove);
						pm.show(playList, e.getX(), e.getY());
					}
				}
			}
		});
		DefaultTableModel model1 = new DefaultTableModel(new Object[][] {}, new String[] { "No",
				"\uC74C\uC6D0 \uC0AC\uC9C4", "\uC544\uD2F0\uC2A4\uD2B8", "\uC74C\uC6D0 \uC81C\uBAA9" });

		List<String> list = DB.getPlayList("singer", Login.id.getText());
		List<String> m_name = DB.getPlayList("m_name", Login.id.getText());
		for (int i = 0; i < list.size(); i++) {
			ImageIcon playListAl = new ImageIcon(DB.getImage("singer", list.get(i)));
			model1.addRow(new Object[] { ++playListCount, MainPlugin.setImageSize(playListAl, 50, 50), list.get(i),
					m_name.get(i) });
		}

		playList.setModel(model1);
		playList.setRowHeight(50);
		playList.getColumnModel().getColumn(1).setCellRenderer(new ImageRender());
		playList.getColumnModel().getColumn(0).setPreferredWidth(31);
		playList.getColumnModel().getColumn(1).setPreferredWidth(79);
		playList.getColumnModel().getColumn(2).setPreferredWidth(245);
		playList.getColumnModel().getColumn(3).setPreferredWidth(270);
		scrollPane.setViewportView(playList);

		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane.addTab("ListenList", null, scrollPane_1, null);

		listenList = new JTable();
		listenList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int getCol = listenList.getSelectedColumn();
				if (getCol == 2 || getCol == 3) {
					if (e.getButton() == e.BUTTON1) {
						listenList.setSelectionBackground(Color.yellow);
					}

					if (e.getButton() == e.BUTTON3) {
						pm.add(remove);
						pm.show(listenList, e.getX(), e.getY());
					}
				}
			}
		});
		DefaultTableModel model2 = new DefaultTableModel(new Object[][] {},
				new String[] { "No", "\uC74C\uC6D0 \uC0AC\uC9C4", "\uC544\uD2F0\uC2A4\uD2B8",
						"\uC74C\uC6D0 \uC81C\uBAA9", "\uB4E4\uC740 \uD69F\uC218" });

		List<String[]> getListenList = DB.getListenList(Login.id.getText());
		for (int i = 0; i < getListenList.size(); i++) {
			resultCount += Integer.parseInt(getListenList.get(i)[2]);
			ImageIcon listenListAl = new ImageIcon(DB.getImage("singer", getListenList.get(i)[0]));
			model2.addRow(new Object[] { ++listenListCount, MainPlugin.setImageSize(listenListAl, 50, 50),
					getListenList.get(i)[0], getListenList.get(i)[1], getListenList.get(i)[2] });
		}

		listenList.setModel(model2);
		listenList.setRowHeight(50);
		listenList.getColumnModel().getColumn(0).setPreferredWidth(37);
		listenList.getColumnModel().getColumn(1).setPreferredWidth(68);
		listenList.getColumnModel().getColumn(1).setCellRenderer(new ImageRender());
		listenList.getColumnModel().getColumn(2).setPreferredWidth(167);
		listenList.getColumnModel().getColumn(3).setPreferredWidth(178);
		listenList.getColumnModel().getColumn(4).setPreferredWidth(169);
		scrollPane_1.setViewportView(listenList);

		JLabel myMusicCount = new JLabel("나의 음원 수 : " + playListCount + "곡");
		myMusicCount.setFont(new Font("굴림", Font.PLAIN, 20));
		myMusicCount.setBounds(438, 367, 190, 34);
		frame.getContentPane().add(myMusicCount);

		tabbedPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tabbedPane.getSelectedIndex() == 0) {
					myMusicCount.setText("나의 음원 수 : " + playListCount + "곡");
				} else {
					myMusicCount.setText("나의 음원 수 : " + resultCount + "곡");
				}
			}
		});

		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null, "음원을 삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION);
				if (confirm == 0) {
					if (tabbedPane.getSelectedIndex() == 0) {
						int getRow = playList.getSelectedRow();
						model1.removeRow(getRow);
						myMusicCount.setText("나의 음원 수 : " + --playListCount + "곡");
					} else {
						int getRow = listenList.getSelectedRow();
						resultCount -= Integer.parseInt((String) listenList.getValueAt(getRow, 4));
						model2.removeRow(getRow);
						myMusicCount.setText("나의 음원 수 : " + resultCount + "곡");
					}
				}
			}
		});
	}
}
