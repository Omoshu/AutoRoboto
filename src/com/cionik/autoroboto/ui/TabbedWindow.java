package com.cionik.autoroboto.ui;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class TabbedWindow {
	
	private JFrame frame = new JFrame();
	
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
	
	public TabbedWindow(String title) {
		initComponents(title);
	}
	
	public void setVisible(boolean b) {
		if (b) {
			frame.setLocationByPlatform(true);
		}
		frame.setVisible(b);
	}
	
	public void addTab(String title, Component c) {
		tabbedPane.addTab(title, c);
	}
	
	public void pack() {
		frame.pack();
	}
	
	private void initComponents(String title) {
		frame.setTitle(title);
		frame.add(tabbedPane);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}