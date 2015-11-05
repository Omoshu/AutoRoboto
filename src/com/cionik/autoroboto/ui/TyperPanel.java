package com.cionik.autoroboto.ui;

import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.cionik.utils.swing.JKeyTextField;
import com.cionik.utils.swing.JNumericTextField;

import net.miginfocom.swing.MigLayout;

public class TyperPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField textField = new JTextField();
	
	private JNumericTextField initialDelayTextField = new JNumericTextField(0);
	
	private JNumericTextField characterDelayTextField = new JNumericTextField(0);
	
	private JNumericTextField delayTextField = new JNumericTextField(1000);
	
	private JNumericTextField numOfTypesTextField = new JNumericTextField(0);
	
	private JComboBox<TimeUnit> initialDelayComboBox = new JComboBox<TimeUnit>(TimeUnit.values());
	
	private JComboBox<TimeUnit> characterDelayComboBox = new JComboBox<TimeUnit>(TimeUnit.values());
	
	private JComboBox<TimeUnit> delayComboBox = new JComboBox<TimeUnit>(TimeUnit.values());
	
	private JCheckBox typeInfinitelyCheckBox = new JCheckBox("Type Infinitely");
	
	private JKeyTextField stopKeyTextField = new JKeyTextField();
	
	private JButton startButton = new JButton("Start");
	
	private JButton stopButton = new JButton("Stop");
	
	public TyperPanel() {
		initComponents();
		layoutComponents();
	}
	
	private void initComponents() {
		initialDelayTextField.setColumns(5);
		characterDelayTextField.setColumns(5);
		delayTextField.setColumns(5);
		numOfTypesTextField.setColumns(5);
		stopKeyTextField.setColumns(5);
		
		delayComboBox.setSelectedItem(TimeUnit.MILLISECONDS);
		characterDelayComboBox.setSelectedItem(TimeUnit.MILLISECONDS);
		initialDelayComboBox.setSelectedItem(TimeUnit.MILLISECONDS);
	}
	
	private void layoutComponents() {
		setLayout(new MigLayout());
		
		JPanel textPanel = new JPanel(new MigLayout());
		textPanel.add(new JLabel("Text: "));
		textPanel.add(textField, "pushx, growx");
		
		JPanel delayPanel = new JPanel(new MigLayout());
		delayPanel.add(new JLabel("Initial Delay: "));
		delayPanel.add(initialDelayTextField);
		delayPanel.add(initialDelayComboBox, "wrap");
		delayPanel.add(new JLabel("Character Delay: "));
		delayPanel.add(characterDelayTextField);
		delayPanel.add(characterDelayComboBox, "wrap");
		delayPanel.add(new JLabel("Delay: "));
		delayPanel.add(delayTextField);
		delayPanel.add(delayComboBox, "wrap");
		
		JPanel stopPanel = new JPanel(new MigLayout());
		stopPanel.add(typeInfinitelyCheckBox, "span, wrap");
		stopPanel.add(new JLabel("Number of Types: "));
		stopPanel.add(numOfTypesTextField, "wrap");
		stopPanel.add(new JSeparator(), "span, growx, wrap");
		stopPanel.add(new JLabel("Key to Stop: "));
		stopPanel.add(stopKeyTextField);
		
		add(textPanel, "span, pushx, growx, wrap");
		add(new JSeparator(), "span, growx, wrap");
		add(delayPanel, "split");
		add(new JSeparator(SwingConstants.VERTICAL), "growy");
		add(stopPanel, "wrap");
		add(new JSeparator(), "span, growx, wrap");
		add(startButton, "span, split, align right");
		add(stopButton, "span, align right");
	}
	
}