package io.worldskills.project;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Data.DB;
import utils.MainPlugin;

public class Login {

	private JFrame frame;
	public static JTextField id;
	public static JTextField pw;
	private JLabel idLabel;
	private JLabel pwLabel;

	public JFrame getFrame() {
		return frame;
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		initialize();
	}
	
	String getPw;
	public Login(String getPw) {
		this.getPw = getPw;
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("로그인");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JLabel loginLabel = new JLabel("로그인");
		loginLabel.setFont(new Font("나눔고딕", Font.BOLD, 25));
		loginLabel.setBounds(185, 10, 80, 36);
		frame.getContentPane().add(loginLabel);
		
		JLabel mainLogo = new JLabel("");
		ImageIcon icon = new ImageIcon("datafiles\\메인.png");
		mainLogo.setIcon(MainPlugin.setImageSize(icon, 88, 36));
		mainLogo.setBounds(334, 10, 88, 36);
		frame.getContentPane().add(mainLogo);
		
		id = new JTextField();
		id.setBounds(100, 91, 236, 36);
		frame.getContentPane().add(id);
		id.setColumns(10);
		
		pw = new JTextField(getPw);
		pw.setEnabled(false);
		pw.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				PreferPopSong pfp = new PreferPopSong();
				PreferPopSong.count = 0;
				pfp.getFrame().setVisible(true);
			}
		});
		pw.setColumns(10);
		pw.setBounds(100, 137, 236, 36);
		frame.getContentPane().add(pw);
		
		JButton login = new JButton("로그인");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (id.getText().isEmpty() || pw.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "빈칸이 있습니다.", "경고", JOptionPane.ERROR_MESSAGE);
				} else if (!DB.loginEquals(pw.getText(), id.getText())) {
					JOptionPane.showMessageDialog(null, "로그인 정보가 일치하지 않습니다.", "경고", JOptionPane.ERROR_MESSAGE);
				} else {
					frame.dispose();
					Main main = new Main();
					main.getFrame().setVisible(true);
				}
			}
		});
		login.setBounds(100, 183, 236, 36);
		frame.getContentPane().add(login);
		
		idLabel = new JLabel("ID");
		idLabel.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		idLabel.setBounds(55, 93, 33, 24);
		frame.getContentPane().add(idLabel);
		
		pwLabel = new JLabel("PW");
		pwLabel.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		pwLabel.setBounds(55, 139, 33, 24);
		frame.getContentPane().add(pwLabel);
	}
}
