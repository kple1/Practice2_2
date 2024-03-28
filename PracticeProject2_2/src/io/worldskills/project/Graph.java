package io.worldskills.project;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import Data.DB;
import utils.GraphBar;

public class Graph {

	private JFrame frame;
	JLabel[] textLabel = new JLabel[5];
	JLabel[] label = new JLabel[5];
	JLayeredPane[] lpList = new JLayeredPane[5];
	int panelGap = 45;
	int textGap = 30;

	public JFrame getFrame() {
		return frame;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Graph window = new Graph();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Graph() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("datafiles\\마이크.png"));
		frame.setTitle("통계");
		frame.setBounds(100, 100, 583, 587);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		JLabel lblNewLabel = new JLabel("연령별 TOP 5");
		lblNewLabel.setFont(new Font("나눔고딕", Font.BOLD, 20));
		lblNewLabel.setBounds(227, 10, 127, 33);
		frame.getContentPane().add(lblNewLabel);

		JPanel designPanel = new JPanel();
		designPanel.setBackground(Color.BLACK);
		designPanel.setBounds(0, 423, 567, 2);
		frame.getContentPane().add(designPanel);

		// 초기화
		for (int i = 0; i < 5; i++) {
			lpList[i] = new JLayeredPane();
			label[i] = new JLabel();
			textLabel[i] = new JLabel();
		}

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// reset
				panelGap = 45;
				textGap = 30;
				for (int i = 0; i < 5; i++) {
					lpList[i].removeAll();
					label[i].removeAll();
					textLabel[i].removeAll();
				}
				design(comboBox);
			}
		});

		for (int i = 10; i <= 70; i += 10) {
			if (i != 70) {
				comboBox.addItem(i + "대");
			} else {
				comboBox.addItem(i + "대 이상");
			}
		}
		comboBox.setBounds(449, 48, 106, 23);
		frame.getContentPane().add(comboBox);
	}

	public void design(JComboBox<String> comboBox) {
		List<String[]> getS = DB.summary((comboBox.getSelectedIndex() + 1) * 10);

		for (int i = 0; i < 5; i++) {
			// 패널 컬러설정
			Color[] cList = new Color[] { Color.BLUE, Color.ORANGE, Color.GRAY, Color.YELLOW, Color.CYAN };

			// LayeredPanel 위치설정
			lpList[i].setBounds(panelGap, 118, 57, 307);

			// 들은 횟수 설정
			label[i].setBounds(0, 0, 57, 15);
			label[i].setText(getS.get(i)[1]);
			lpList[i].add(label[i], JLayeredPane.PALETTE_LAYER);
			panelGap += 100;

			// 그래프 설정
			JPanel panel = new GraphBar(cList[i]);
			panel.setBounds(0, 0, 57, 307);
			lpList[i].add(panel, JLayeredPane.DEFAULT_LAYER);

			// 음악 이름
			if (getS.get(i)[0].length() > 9) {
				textLabel[i].setText(getS.get(i)[0].substring(0, 9) + "...");
			} else {
				textLabel[i].setText(getS.get(i)[0]);
			}
			textLabel[i].setBounds(textGap, 440, 90, 15);
			textGap += 100;

			frame.getContentPane().add(lpList[i]);
			frame.getContentPane().add(textLabel[i]);
		}
	}
}
