package com.cionik.autoroboto.ui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.event.DocumentListener;

import com.cionik.autoroboto.model.MouseButton;
import com.cionik.autoroboto.util.JNumericTextField;
import com.cionik.autoroboto.util.Listener;
import com.cionik.autoroboto.util.ScreenPointSelectDialog;

import net.miginfocom.swing.MigLayout;

public abstract class MouseTaskPanel extends JPanel implements TaskPanel {

	private static final long serialVersionUID = 1L;
	
	private JComboBox<MouseButton> mouseButtonComboBox = new JComboBox<MouseButton>(MouseButton.values());
	private JCheckBox currentMouseLocationCheckBox = new JCheckBox("Click at Current Mouse Location");
	private JNumericTextField xTextField = new JNumericTextField(0);
	private JNumericTextField yTextField = new JNumericTextField(0);
	private JButton setPointButton = new JButton("Set Point");
	
	public MouseTaskPanel(MouseButton button, Point point) {
		super();
		
		addListeners();
		initComponents(button, point);
		layoutComponents();
	}
	
	public MouseTaskPanel() {
		this(null, null);
	}
	
	public MouseButton getMouseButton() {
		return (MouseButton) mouseButtonComboBox.getSelectedItem();
	}
	
	public boolean isCurrentMouseLocation() {
		return currentMouseLocationCheckBox.isSelected();
	}
	
	public Point getPoint() {
		Integer x = xTextField.getValue();
		Integer y = yTextField.getValue();
		return x == null || y == null ? null : new Point(x, y);
	}
	
	@Override
	public boolean hasValidInput() {
		return isCurrentMouseLocation() || getPoint() != null;
	}
	
	@Override
	public JPanel getPanel() {
		return this;
	}

	@Override
	public void addInputChangeListener(Listener<Void> listener) {
		currentMouseLocationCheckBox.addActionListener(new ActionListenerAdapter(listener));
		
		DocumentListener documentListener = new DocumentListenerAdapter(listener);
		xTextField.getDocument().addDocumentListener(documentListener);
		yTextField.getDocument().addDocumentListener(documentListener);
	}
	
	private void initComponents(MouseButton button, Point point) {
		if (button != null) {
			mouseButtonComboBox.setSelectedItem(button);
			if (point != null) {
				xTextField.setValue(point.x);
				yTextField.setValue(point.y);
			} else {
				currentMouseLocationCheckBox.setSelected(true);
			}
		}
		
		xTextField.setColumns(5);
		yTextField.setColumns(5);
	}
	
	private void addListeners() {
		currentMouseLocationCheckBox.addActionListener(new CurrentMouseLocationActionListener());
		setPointButton.addActionListener(new SetPointActionListener());
	}
	
	private void layoutComponents() {
		setLayout(new MigLayout());
		add(new JLabel("Mouse Button: "), "span, split 2");
		add(mouseButtonComboBox, "wrap");
		add(new JSeparator(), "span, pushx, growx");
		add(currentMouseLocationCheckBox, "span");
		
		JPanel pointPanel = new JPanel(new MigLayout("insets 0"));
		pointPanel.add(new JLabel("X Point: "));
		pointPanel.add(xTextField, "wrap");
		pointPanel.add(new JLabel("Y Point: "));
		pointPanel.add(yTextField);
		
		add(pointPanel, "split 2");
		add(setPointButton, "span 1 2, growy, wrap");
	}
	
	private class CurrentMouseLocationActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean selected = !currentMouseLocationCheckBox.isSelected();
			xTextField.setEnabled(selected);
			yTextField.setEnabled(selected);
			setPointButton.setEnabled(selected);
		}
		
	}
	
	private class SetPointActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Point p = ScreenPointSelectDialog.getPoint();
			xTextField.setValue(p.x);
			yTextField.setValue(p.y);
		}
		
	}
	
}