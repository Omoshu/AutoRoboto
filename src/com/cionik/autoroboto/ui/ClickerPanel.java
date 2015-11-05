package com.cionik.autoroboto.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.cionik.autoroboto.MouseButton;
import com.cionik.utils.swing.JKeyTextField;
import com.cionik.utils.swing.JNumericTextField;

import net.miginfocom.swing.MigLayout;

public class ClickerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected JButton setPointButton = new JButton("Set Point");
	
	protected JButton startButton = new JButton("Start");
	
	protected JButton stopButton = new JButton("Stop");
	
	protected JButton clearButton = new JButton("Clear");
	
	protected JCheckBox mouseLocationCheckBox = new JCheckBox("Click at current mouse location");
	
	protected JCheckBox onlyMovingCheckBox = new JCheckBox("Only when mouse is not moving");
	
	protected JCheckBox clickInfinitelyCheckBox = new JCheckBox("Click Infinitely");
	
	protected JNumericTextField xTextField = new JNumericTextField(0);
	
	protected JNumericTextField yTextField = new JNumericTextField(0);
	
	protected JNumericTextField delayTextField = new JNumericTextField(1000);
	
	protected JNumericTextField initialDelayTextField = new JNumericTextField(0);
	
	protected JNumericTextField numOfClicksTextField = new JNumericTextField(0);
	
	protected JKeyTextField stopKeyTextField = new JKeyTextField();
	
	protected JComboBox<MouseButton> mouseButtonComboBox = new JComboBox<MouseButton>(MouseButton.values());
	
	protected JComboBox<TimeUnit> delayComboBox = new JComboBox<TimeUnit>(TimeUnit.values());
	
	protected JComboBox<TimeUnit> initialDelayComboBox = new JComboBox<TimeUnit>(TimeUnit.values());
	
	public ClickerPanel() {
		initComponents();
		layoutComponents();
	}
	
	private void initComponents() {
		xTextField.setColumns(5);
		yTextField.setColumns(5);
		delayTextField.setColumns(5);
		initialDelayTextField.setColumns(5);
		numOfClicksTextField.setColumns(5);
		stopKeyTextField.setColumns(5);
		
		delayComboBox.setSelectedItem(TimeUnit.MILLISECONDS);
		initialDelayComboBox.setSelectedItem(TimeUnit.MILLISECONDS);
	}
	
	private void layoutComponents() {
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(new JLabel("Mouse Button: "));
		buttonPanel.add(mouseButtonComboBox);
		System.out.println(buttonPanel.getInsets());
		//buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JPanel pointPanel = new JPanel(new MigLayout());
		pointPanel.add(buttonPanel, "aligny top, span, wrap");
		/*pointPanel.add(new JLabel("Mouse Button: "));
		pointPanel.add(mouseButtonComboBox, "span 2, wrap");*/
		pointPanel.add(new JSeparator(), "span, growx, wrap");
		pointPanel.add(mouseLocationCheckBox, "span, growx, wrap");
		pointPanel.add(new JLabel("X Point: "));
		pointPanel.add(xTextField);
		pointPanel.add(setPointButton, "span 1 2, grow, wrap");
		pointPanel.add(new JLabel("Y Point: "));
		pointPanel.add(yTextField, "wrap");
		pointPanel.add(new JSeparator(), "span, growx, wrap");
		pointPanel.add(onlyMovingCheckBox, "span");
		
		JPanel delayPanel = new JPanel(new MigLayout());
		delayPanel.add(new JLabel("Initial Delay: "));
		delayPanel.add(initialDelayTextField);
		delayPanel.add(initialDelayComboBox, "wrap");
		delayPanel.add(new JLabel("Delay: "));
		delayPanel.add(delayTextField);
		delayPanel.add(delayComboBox, "wrap");
		delayPanel.add(new JSeparator(), "span, growx, wrap");
		delayPanel.add(new JLabel("Number of Clicks: "));
		delayPanel.add(numOfClicksTextField, "wrap");
		delayPanel.add(clickInfinitelyCheckBox, "wrap");
		delayPanel.add(new JSeparator(), "span, growx, wrap");
		delayPanel.add(new JLabel("Key to Stop: "));
		delayPanel.add(stopKeyTextField);
		delayPanel.add(clearButton);
		
		setLayout(new MigLayout());
		add(pointPanel, "aligny top");
		add(new JSeparator(SwingConstants.VERTICAL), "growy");
		add(delayPanel, "aligny top, wrap");
		add(new JSeparator(), "span, growx, wrap");
		add(startButton, "span, split, align right");
		add(stopButton, "span, align right");
	}
	
}