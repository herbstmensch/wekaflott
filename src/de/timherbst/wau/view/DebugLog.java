package de.timherbst.wau.view;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.javabuilders.swing.SwingJavaBuilder;

import de.timherbst.wau.application.Application;
import de.timherbst.wau.domain.WettkampfTag;

public class DebugLog extends JDialog {

	private static final long serialVersionUID = -2541536572046492142L;
	private JTextField txtVeranstaltung;

	public DebugLog() {
		super((JFrame) null, true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		SwingJavaBuilder.build(this);
		txtVeranstaltung.setText(WettkampfTag.get().getName());
		pack();
		setLocationRelativeTo(Application.getMainFrame());
	}

	@SuppressWarnings("unused")
	private void ok() {

		WettkampfTag.get().setName(txtVeranstaltung.getText());

		cancel();
	}

	private void cancel() {
		setVisible(false);
	}

}
