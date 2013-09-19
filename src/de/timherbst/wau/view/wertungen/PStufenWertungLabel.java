package de.timherbst.wau.view.wertungen;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.javabuilders.swing.SwingJavaBuilder;

import de.timherbst.wau.domain.wertungen.PStufenWertung;
import de.timherbst.wau.util.Formatter;

public class PStufenWertungLabel extends JPanel {

	private static final long serialVersionUID = -6503591436431627963L;
	private JLabel ausgang;
	private JLabel abzug;
	private JLabel endwert;
	private PStufenWertung wertung;

	public PStufenWertungLabel(PStufenWertung wertung) {
		SwingJavaBuilder.build(this);
		setWertung(wertung);
		setOpaque(true);
		endwert.setFont(endwert.getFont().deriveFont(Font.BOLD));
	}

	private void setWertung(PStufenWertung wertung) {
		this.wertung = wertung;
		ausgang.setText(Formatter.format(wertung.getAusgang()));
		abzug.setText(Formatter.format(wertung.getAbzug()));
		endwert.setText(Formatter.format(wertung.getEndwert()));
	}

	public PStufenWertung getWertung() {
		return wertung;
	}

}
