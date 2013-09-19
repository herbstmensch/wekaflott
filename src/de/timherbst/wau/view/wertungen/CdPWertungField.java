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
import de.timherbst.wau.domain.wertungen.CdPWertung;
import de.timherbst.wau.util.Formatter;
import de.timherbst.wau.view.components.TabulatorTextField;

public class CdPWertungField extends JPanel implements WertungField {

	private static final long serialVersionUID = 5369793277968388540L;
	private TabulatorTextField difficulty;
	private TabulatorTextField execution;
	private TabulatorTextField missing;
	private JLabel endwert;
	private CdPWertung wertung;

	public CdPWertungField(CdPWertung wertung) {
		SwingJavaBuilder.build(this);

		setWertung(wertung);
		setOpaque(true);
		setFocusable(true);
		endwert.setFont(endwert.getFont().deriveFont(Font.BOLD));
		difficulty.setNextComponent(execution);
		execution.setPreviousComponent(difficulty);
		execution.setNextComponent(missing);
		missing.setPreviousComponent(execution);
		difficulty.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				try {
					setWertung(getWertung());
				} catch (Throwable pe) {
					AxtresLogger.error("Fehler beim setzen einer CdPWertung", pe);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				difficulty.selectAll();
			}

		});
		execution.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				try {
					setWertung(getWertung());
				} catch (Throwable pe) {
					AxtresLogger.error("Fehler beim setzen einer CdPWertung", pe);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				execution.selectAll();
			}
		});
		missing.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				try {
					setWertung(getWertung());
				} catch (Throwable pe) {
					AxtresLogger.error("Fehler beim setzen einer CdPWertung", pe);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				missing.selectAll();
			}
		});
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				difficulty.requestFocus();
				super.focusGained(e);
			}
		});
	}

	private void setWertung(CdPWertung wertung) {
		this.wertung = wertung;
		difficulty.setText(Formatter.format(wertung.getDifficulty()));
		execution.setText(Formatter.format(wertung.getExecution()));
		missing.setText(Formatter.format(wertung.getMissingElements()));
		endwert.setText(Formatter.format(wertung.getEndwert()));
	}

	public CdPWertung getWertung() {

		try {
			wertung.setDifficulty(Formatter.parse(difficulty.getText()).doubleValue());
			wertung.setExecution(Formatter.parse(execution.getText()).doubleValue());
			wertung.setMissingElements(Formatter.parse(missing.getText()).doubleValue());
		} catch (Exception e) {
			AxtresLogger.error(e.getMessage(), e);
			throw new RuntimeErrorException(new Error(e));
		}
		return wertung;
	}

	public void setFieldBackground(Color bg) {
		if (difficulty != null)
			difficulty.setBackground(bg);
		if (execution != null)
			execution.setBackground(bg);
		if (missing != null)
			missing.setBackground(bg);
	}

}
