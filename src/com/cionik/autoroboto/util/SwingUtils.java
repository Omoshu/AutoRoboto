package com.cionik.autoroboto.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.SwingUtilities;

public class SwingUtils {
	
	private SwingUtils() {
	}
	
	public static void checkKeyEventId(int id) {
		if (id != KeyEvent.KEY_TYPED &&
			id != KeyEvent.KEY_PRESSED &&
			id != KeyEvent.KEY_RELEASED) {
			throw new IllegalArgumentException("Invalid key event id");
		}
	}
	
	public static void checkMouseEventId(int id) {
		if (id != MouseEvent.MOUSE_CLICKED &&
			id != MouseEvent.MOUSE_PRESSED &&
			id != MouseEvent.MOUSE_RELEASED) {
			throw new IllegalArgumentException("Invalid mouse event id");
		}
	}
	
	public static <T> void addDoubleClickListenerr(JList<T> list, Listener<DoubleClickEvent<JList<T>, T>> listener) {
		list.addMouseListener(new MouseAdapter() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
					JList<T> list = (JList<T>) e.getSource();
					int index = list.locationToIndex(e.getPoint());
					Rectangle bounds = list.getCellBounds(index, index);
					if (bounds != null && bounds.contains(e.getPoint())) {
						listener.handleEvent(new DoubleClickEvent<JList<T>, T>(list, (T) list.getModel().getElementAt(index)));
					}
				}
			}
			
		});
	}
	
	public static void equatePreferredSize(Component... components) {
		int maxWidth = -1;
		int maxHeight = -1;
		for (Component c : components) {
			Dimension size = c.getPreferredSize();
			if (size.width > maxWidth) {
				maxWidth = size.width;
			}
			if (size.height > maxHeight) {
				maxHeight = size.height;
			}
		}
		
		if (maxWidth != -1 && maxHeight != -1) {
			Dimension newSize = new Dimension(maxWidth, maxHeight);
			for (Component c : components) {
				c.setPreferredSize(newSize);
			}
		}
	}
	
}