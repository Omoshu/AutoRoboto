package com.cionik.autoroboto.ui;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class TabbedWindow {
	
	private JFrame frame = new JFrame("AutoRoboto");
	
	private JTabbedPane tabbedPane = new JTabbedPane();
	
	public TabbedWindow() {
		initComponents();
	}
	
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
	
	public void addTab(String title, Component c) {
		tabbedPane.addTab(title, c);
	}
	
	public void pack() {
		frame.pack();
		frame.setLocationByPlatform(true);
	}
	
	private void initComponents() {
		frame.add(tabbedPane);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}