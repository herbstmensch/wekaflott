package de.timherbst.wau.view.components;

import java.awt.Component;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class TabulatorTextField extends JTextField {
	private static final long serialVersionUID = 9114072729059799924L;

	public TabulatorTextField() {
		super();
	}

	public void setNextComponent(Component c) {
		myNextComponent = c;
	}

	public void setPreviousComponent(Component c) {
		myPreviousComponent = c;
	}

	protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
		boolean retVal = false;

		if (e.getKeyCode() != KeyEvent.VK_TAB) {
			retVal = super.processKeyBinding(ks, e, condition, pressed);
		} else if ((e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) == InputEvent.SHIFT_DOWN_MASK) {
			if (myPreviousComponent != null && pressed) {
				myPreviousComponent.requestFocusInWindow();
				retVal = true;
			} else {
				retVal = super.processKeyBinding(ks, e, condition, pressed);
			}
		} else {
			if (myNextComponent != null && pressed) {
				myNextComponent.requestFocusInWindow();
				retVal = true;
			} else {
				retVal = super.processKeyBinding(ks, e, condition, pressed);
			}
		}

		return retVal;
	}

	private Component myNextComponent;
	private Component myPreviousComponent;
}
