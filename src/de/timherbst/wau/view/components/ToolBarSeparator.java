package de.timherbst.wau.view.components;

import java.awt.Dimension;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class ToolBarSeparator extends JSeparator {

	private static final long serialVersionUID = 476123867469865649L;

	public ToolBarSeparator() {
		super(SwingConstants.VERTICAL);
	}

	public Dimension getMaximumSize() {
		return new Dimension(getPreferredSize().width, Integer.MAX_VALUE);
	}
}
