package utils;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GraphBar extends JPanel {
	
	Color color;
	public GraphBar(Color color) {
		this.color = color;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		g.fillRect(0, 0, 57, 338);
	}
}
