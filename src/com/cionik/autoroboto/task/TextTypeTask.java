package com.cionik.autoroboto.task;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import com.cionik.autoroboto.model.Time;
import com.cionik.autoroboto.ui.TaskPanel;
import com.cionik.autoroboto.ui.TextTypeTaskPanel;

public class TextTypeTask implements Task {
	
	private Robot robot;
	private String text;
	private Time characterDelay;
	private long characterDelayMillis;
	
	public TextTypeTask(String text, Time characterDelay) throws AWTException {
		if (text == null) {
			throw new IllegalArgumentException("text cannot be null");
		} else if (characterDelay == null) {
			throw new IllegalArgumentException("characterDelay cannot be null");
		}
		
		this.text = text;
		this.characterDelay = characterDelay;
		characterDelayMillis = characterDelay.convert(TimeUnit.MILLISECONDS);
		
		robot = new Robot();
	}

	@Override
	public void run() {
		char[] charArray = text.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
			boolean upperCase = Character.isUpperCase(c);
			if (upperCase) {
				robot.keyPress(KeyEvent.VK_SHIFT);
			}
			robot.keyPress(keyCode);
			robot.keyRelease(keyCode);
			if (upperCase) {
				robot.keyRelease(KeyEvent.VK_SHIFT);
			}
			
			if (characterDelayMillis > 0 && i != charArray.length - 1) {
				try {
					Thread.sleep(characterDelayMillis);
				} catch (InterruptedException e) {
					//Ignore
				}
			}
		}
	}
	
	@Override
	public TaskPanel createPanel() {
		return new TextTypeTaskPanel(text, characterDelay);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Text Type: Text[");
		sb.append(text);
		sb.append("], Character Delay[");
		sb.append(characterDelay.getDuration());
		sb.append(" ");
		sb.append(characterDelay.getUnit());
		sb.append("]");
		return sb.toString();
	}
	
}