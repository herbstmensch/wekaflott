package de.timherbst.wau.view.wertungen;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.management.RuntimeErrorException;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.javabuilders.swing.SwingJavaBuilder;

import de.axtres.logging.main.AxtresLogger;
import de.timherbst.wau.domain.wertungen.PStufenWertung;
import de.timherbst.wau.util.Formatter;
import de.timherbst.wau.view.components.TabulatorTextField;

public class PStufenWertungField extends JPanel implements WertungField {

	private static final long serialVersionUID = -6503591436431627963L;
	private TabulatorTextField ausgang;
	private TabulatorTextField abzug;
	private JLabel endwert;
	private PStufenWertung wertung;

	public PStufenWertungField(PStufenWertung wertung) {
		SwingJavaBuilder.build(this);
		setWertung(wertung);
		setOpaque(true);
		setFocusable(true);
		endwert.setFont(endwert.getFont().deriveFont(Font.BOLD));
		abzug.setPreviousComponent(ausgang);
		ausgang.setNextComponent(abzug);
		ausgang.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				try {
					setWertung(getWertung());
				} catch (Throwable pe) {
					AxtresLogger.error("focusLost", pe);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				ausgang.selectAll();
			}
		});
		abzug.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				try {
					setWertung(getWertung());
				} catch (Throwable pe) {
					AxtresLogger.error("focusLost", pe);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				abzug.selectAll();
			}
		});
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				ausgang.requestFocus();
				// super.focusGained(e);
			}
		});

	}

	private void setWertung(PStufenWertung wertung) {
		this.wertung = wertung;
		ausgang.setText(Formatter.format(wertung.getAusgang()));
		abzug.setText(Formatter.format(wertung.getAbzug()));
		endwert.setText(Formatter.format(wertung.getEndwert()));
	}

	public PStufenWertung getWertung() {

		try {
			wertung.setAbzug(Formatter.parse(abzug.getText()).doubleValue());
			wertung.setAusgang(Formatter.parse(ausgang.getText()).doubleValue());
		} catch (Exception e) {
			AxtresLogger.error("getWertung", e);
			throw new RuntimeErrorException(new Error(e));
		}
		return wertung;
	}

	public void setFieldBackground(Color bg) {
		if (abzug != null)
			abzug.setBackground(bg);
		if (ausgang != null)
			ausgang.setBackground(bg);
	}

}
