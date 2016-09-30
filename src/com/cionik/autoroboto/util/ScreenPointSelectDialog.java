package com.cionik.autoroboto.util;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;

public class ScreenPointSelectDialog {
	
	public static Point getPoint() {
		Point[] point = new Point[1];
		JDialog dialog = new JDialog();
		dialog.setModal(true);
		dialog.setUndecorated(true);
		dialog.setOpacity(0.5f);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setBounds(new Rectangle(0, 0, size.width, size.height)); 
		dialog.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				point[0] = e.getLocationOnScreen();
				dialog.dispose();
			}
			
		});
		dialog.setVisible(true);
		return point[0];
	}
	
}