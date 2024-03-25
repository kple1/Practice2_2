package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.time.LocalDateTime;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DayCircle extends JPanel {

	int getYear;
	int getMonth;
	int getDay;
	JLabel dayLabel;
	Color color = Color.yellow;
	public DayCircle(int getYear, int getMonth, int getDay) {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				color = Color.red;
				repaint();
			}
		});
		this.getYear = getYear;
		this.getMonth = getMonth;
		this.getDay = getDay;
		setLayout(null);
		
		//토,일 색구분
		int getDayOfWeek = LocalDateTime.of(getYear, getMonth, getDay, 0, 0, 0).getDayOfWeek().getValue();
		if (getDayOfWeek == 7) {
			dayLabel = new JLabel(String.valueOf("<html><font color = 'red'>" + getDay + "</font><html/>"));
		} else if (getDayOfWeek == 6) {
			dayLabel = new JLabel(String.valueOf("<html><font color = 'blue'>" + getDay + "</font><html/>"));
		} else {
			dayLabel = new JLabel(String.valueOf(getDay));
		}
		
		dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dayLabel.setBounds(0, 10, 50, 30);
		add(dayLabel);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int getYear = LocalDateTime.now().getYear();
		int getMonth = LocalDateTime.now().getMonthValue();
		int getDay = LocalDateTime.now().getDayOfMonth();
		//이전페이지 제어
		if (this.getYear == getYear && this.getMonth >= getMonth) {

		} else {
			g.setColor(Color.YELLOW);
			g.fillOval(0, 0, 60, 60);
			g.setColor(color);
			g.drawOval(0, 0, 61, 61);
		}
		
		//해당페이지 제어
		if (this.getYear == getYear && this.getMonth == getMonth && this.getDay < getDay) {
			g.setColor(Color.YELLOW);
			g.fillOval(0, 0, 60, 60);
			g.setColor(color);
			g.drawOval(0, 0, 61, 61);
		}
	}
}
