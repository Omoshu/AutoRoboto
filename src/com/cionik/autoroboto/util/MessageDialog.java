package com.cionik.autoroboto.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class MessageDialog {
	
	public static void show(Frame owner, boolean modal, String title, String message, String dontShowId) {
		final Preferences prefs = Preferences.userNodeForPackage(MessageDialog.class);
		if (dontShowId != null) {
			if (prefs.get(dontShowId, "false").equals("true")) {
				return;
			}
		}
		
		JDialog dialog = new JDialog(owner, title, modal);
		dialog.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		JLabel label = new JLabel(message);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(label);
		
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(e -> dialog.dispose());
		okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(okButton);
		
		panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		dialog.add(panel, BorderLayout.CENTER);
		
		if (dontShowId != null) {
			JPanel checkPanel = new JPanel(new BorderLayout());
			checkPanel.add(new JSeparator(), BorderLayout.NORTH);
			JCheckBox dontShowCheckBox = new JCheckBox("Don't show again");
			dontShowCheckBox.addActionListener(e -> prefs.put(dontShowId, Boolean.toString(dontShowCheckBox.isSelected())));
			checkPanel.add(dontShowCheckBox, BorderLayout.SOUTH);
			checkPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
			dialog.add(checkPanel, BorderLayout.SOUTH);
		}
		
		dialog.pack();
		dialog.setLocationByPlatform(true);
		dialog.setVisible(true);
	}
	
	public static void show(Frame owner, boolean modal, String title, String message) {
		show(owner, modal, title, message, null);
	}
	
}