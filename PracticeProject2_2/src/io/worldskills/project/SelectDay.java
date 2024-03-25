package io.worldskills.project;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import utils.DayCircle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SelectDay {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectDay window = new SelectDay();
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
	public SelectDay() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	int getYear = LocalDateTime.now().getYear();
	int getMonth = LocalDateTime.now().getMonthValue();

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("날짜 선택");
		frame.setIconImage(
				Toolkit.getDefaultToolkit().getImage("C:\\Users\\User\\Desktop\\과제2 결과물\\datafiles\\마이크.png"));
		frame.setBounds(100, 100, 569, 651);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBounds(22, 129, 507, 473);
		frame.getContentPane().add(panel);

		GridLayout gr = new GridLayout(0, 7);
		panel.setLayout(gr);

		JLabel lblNewLabel = new JLabel("날짜 선택");
		lblNewLabel.setFont(new Font("나눔고딕", Font.BOLD, 25));
		lblNewLabel.setBounds(215, 10, 116, 35);
		frame.getContentPane().add(lblNewLabel);

		JComboBox<Integer> comboBox = new JComboBox<Integer>();

		for (int i = 1950; i <= getYear; i++) {
			comboBox.addItem(i);
		}
		comboBox.setSelectedItem(getYear);
		comboBox.setBounds(215, 54, 116, 23);
		frame.getContentPane().add(comboBox);

		JLabel lblNewLabel_1 = new JLabel("년");
		lblNewLabel_1.setBounds(343, 56, 20, 19);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel setMonth = new JLabel(getMonth + "월");
		setMonth.setBounds(390, 56, 26, 19);
		frame.getContentPane().add(setMonth);

		JButton beforeBt = new JButton("<");
		beforeBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getYear = (int) comboBox.getSelectedItem();
				if (getYear == 1950 && getMonth == 1) {
					
				} else {
					if (getMonth == 1) {
						getYear -= 1;
						getMonth = 12;
					}
					getMonth -= 1;
				}
				comboBox.setSelectedItem(getYear);
				setMonth.setText(getMonth + "월");
				createPanel(panel);
			}
		});
		beforeBt.setBounds(420, 54, 44, 23);
		frame.getContentPane().add(beforeBt);

		JButton nextBt = new JButton(">");
		nextBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getYear = (int) comboBox.getSelectedItem();
				if (getYear == LocalDateTime.now().getYear() && getMonth == 12) {

				} else {
					if (getMonth == 12) {
						getYear += 1;
						getMonth = 1;
					}
					getMonth += 1;
				}
				comboBox.setSelectedItem(getYear);
				setMonth.setText(getMonth + "월");
				createPanel(panel);
			}
		});
		nextBt.setBounds(476, 54, 44, 23);
		frame.getContentPane().add(nextBt);

		JLabel lblNewLabel_2 = new JLabel("일");
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(12, 104, 64, 15);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("월");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1.setBounds(88, 104, 64, 15);
		frame.getContentPane().add(lblNewLabel_2_1);

		JLabel lblNewLabel_2_2 = new JLabel("화");
		lblNewLabel_2_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2.setBounds(164, 104, 64, 15);
		frame.getContentPane().add(lblNewLabel_2_2);

		JLabel lblNewLabel_2_3 = new JLabel("수");
		lblNewLabel_2_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_3.setBounds(240, 104, 64, 15);
		frame.getContentPane().add(lblNewLabel_2_3);

		JLabel lblNewLabel_2_4 = new JLabel("목");
		lblNewLabel_2_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_4.setBounds(316, 104, 64, 15);
		frame.getContentPane().add(lblNewLabel_2_4);

		JLabel lblNewLabel_2_5 = new JLabel("금");
		lblNewLabel_2_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_5.setBounds(390, 104, 64, 15);
		frame.getContentPane().add(lblNewLabel_2_5);

		JLabel lblNewLabel_2_6 = new JLabel("토");
		lblNewLabel_2_6.setForeground(Color.BLUE);
		lblNewLabel_2_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_6.setBounds(465, 104, 64, 15);
		frame.getContentPane().add(lblNewLabel_2_6);
		createPanel(panel);
	}

	public void createPanel(JPanel panel) {
		panel.removeAll();

		int ended = YearMonth.of(getYear, getMonth).lengthOfMonth();
		int first = LocalDate.of(getYear, getMonth, 1).getDayOfWeek().getValue();
		for (int i = 0; i < first; i++) {
			panel.add(new JLabel());
		}

		for (int i = 1; i <= ended; i++) {
			panel.add(new DayCircle(getYear, getMonth, i));
		}

		for (int i = 0; i < 42 - (ended + first); i++) {
			panel.add(new JLabel());
		}
		panel.revalidate();
		panel.repaint();
	}
}
