package de.timherbst.wau.view;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.javabuilders.swing.SwingJavaBuilder;

import de.axtres.logging.main.AxtresLogger;
import de.timherbst.wau.application.Application;
import de.timherbst.wau.domain.WettkampfTag;
import de.timherbst.wau.util.Formatter;

public class WettkampfTagPropertiesWindow extends JDialog {

	private static final long serialVersionUID = -2541536572046492142L;
	private JTextField txtVeranstaltung;
	private JTextField txtOrt;
	private JTextField txtDatum;

	public WettkampfTagPropertiesWindow() {
		super((JFrame) null, true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		SwingJavaBuilder.build(this);
		txtVeranstaltung.setText(WettkampfTag.get().getName());
		txtOrt.setText(WettkampfTag.get().getOrt());
		txtDatum.setText(Formatter.format(WettkampfTag.get().getDatum()));
		pack();
		setLocationRelativeTo(Application.getMainFrame());
	}

	@SuppressWarnings("unused")
	private void ok() {
		try {
			WettkampfTag.get().setDatum(Formatter.parseDate(txtDatum.getText()));
		} catch (Throwable t) {
			AxtresLogger.error(txtDatum.getText() + " ist kein Gültiges Datum. Bitte im Format TT.MM.JJJJ angeben.", t);
			JOptionPane.showMessageDialog(this, txtDatum.getText() + " ist kein Gültiges Datum. Bitte im Format TT.MM.JJJJ angeben.", "Fehler", JOptionPane.ERROR_MESSAGE);
			return;
		}

		WettkampfTag.get().setName(txtVeranstaltung.getText());
		WettkampfTag.get().setOrt(txtOrt.getText());

		cancel();
	}

	private void cancel() {
		setVisible(false);
	}

}
