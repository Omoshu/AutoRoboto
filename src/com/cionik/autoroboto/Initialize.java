package com.cionik.autoroboto;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.cionik.autoroboto.task.KeyPressTaskType;
import com.cionik.autoroboto.task.KeyReleaseTaskType;
import com.cionik.autoroboto.task.KeyTypeTaskType;
import com.cionik.autoroboto.task.MouseClickTaskType;
import com.cionik.autoroboto.task.MousePressTaskType;
import com.cionik.autoroboto.task.MouseReleaseTaskType;
import com.cionik.autoroboto.task.SleepTaskType;
import com.cionik.autoroboto.task.TaskType;
import com.cionik.autoroboto.task.TextTypeTaskType;
import com.cionik.autoroboto.ui.MouseClickTaskPanel;
import com.cionik.autoroboto.ui.RobotPanel;
import com.cionik.autoroboto.ui.TabbedWindow;
import com.cionik.autoroboto.ui.TaskOptionsPanel;
import com.cionik.autoroboto.ui.TextTypeTaskPanel;
import com.cionik.autoroboto.util.MessageDialog;

public class Initialize {
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			//Ignore
		}
		
		Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.OFF);
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			MessageDialog.show(null, true, "Error", "Some features are not available for your operating system.", "autoRoboto.nativeHook");
		}
		
		TaskType[] taskTypes = {
				new KeyTypeTaskType(),
				new KeyPressTaskType(),
				new KeyReleaseTaskType(),
				new MouseClickTaskType(),
				new MousePressTaskType(),
				new MouseReleaseTaskType(),
				new TextTypeTaskType(),
				new SleepTaskType()
		};
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				TabbedWindow window = new TabbedWindow("AutoRoboto");
				window.addTab("Clicker", new TaskOptionsPanel(new MouseClickTaskPanel()), KeyEvent.VK_C);
				window.addTab("Typer", new TaskOptionsPanel(new TextTypeTaskPanel()), KeyEvent.VK_T);
				window.addTab("Robot", new TaskOptionsPanel(new RobotPanel(taskTypes)), KeyEvent.VK_R);
				window.pack();
				window.setVisible(true);
			}
			
		});
	}
	
}