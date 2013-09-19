package de.timherbst.wau.view.wertungen;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.javabuilders.swing.SwingJavaBuilder;

import de.timherbst.wau.domain.wertungen.CdPWertung;
import de.timherbst.wau.util.Formatter;

public class CdPWertungLabel extends JPanel {

	private static final long serialVersionUID = 5369793277968388540L;
	private JLabel difficulty;
	private JLabel execution;
	private JLabel missing;
	private JLabel endwert;
	private CdPWertung wertung;

	public CdPWertungLabel(CdPWertung wertung) {
		SwingJavaBuilder.build(this);
		setWertung(wertung);
		setOpaque(true);
		endwert.setFont(endwert.getFont().deriveFont(Font.BOLD));
	}

	private void setWertung(CdPWertung wertung) {
		this.wertung = wertung;
		difficulty.setText(Formatter.format(wertung.getDifficulty()));
		execution.setText(Formatter.format(wertung.getExecution()));
		missing.setText(Formatter.format(wertung.getMissingElements()));
		endwert.setText(Formatter.format(wertung.getEndwert()));
	}

	public CdPWertung getWertung() {
		return wertung;
	}

}
