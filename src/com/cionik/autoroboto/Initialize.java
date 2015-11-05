package com.cionik.autoroboto;

import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.cionik.autoroboto.ui.ClickerPanelController;
import com.cionik.autoroboto.ui.TabbedWindow;
import com.cionik.autoroboto.ui.TyperPanelController;
import com.cionik.utils.swing.MessageDialog;

public class Initialize {
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.OFF);
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			MessageDialog.show(null, true, "Error", "Some features are not available for your operating system.", "autoRoboto.nativeHook");
		}
		
		EventQueue.invokeLater(() -> {
			TabbedWindow window = new TabbedWindow();
			window.addTab("Clicker", new ClickerPanelController());
			window.addTab("Typer", new TyperPanelController());
			window.pack();
			window.setVisible(true);
		});
	}
	
}