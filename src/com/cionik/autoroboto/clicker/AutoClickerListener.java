package com.cionik.autoroboto.clicker;

import com.cionik.utils.model.Listener;

public interface AutoClickerListener extends Listener {
	
	void started();
	
	void stopped();
	
}