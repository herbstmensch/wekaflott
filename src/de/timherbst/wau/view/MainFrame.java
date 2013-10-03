package de.timherbst.wau.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

import de.axtres.logging.main.AxtresLogger;
import de.timherbst.wau.application.Application;
import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.domain.WettkampfTag;
import de.timherbst.wau.events.Event;
import de.timherbst.wau.events.EventDispatcher;
import de.timherbst.wau.events.EventListener;
import de.timherbst.wau.service.StorageService;

public class MainFrame extends JFrame implements EventListener {

	private static final long serialVersionUID = -1883573216655861511L;
	private static final int AUSWERTUNG_TAB_INDEX = 4;

	private boolean dirty = false;
	@SuppressWarnings("unused")
	private BuildResult result;
	private JTabbedPane tabs;
	private JLabel status;
	private ErfassungHostView pimpedErfassung;
	
	private JButton turnerBtn;
	private JButton mannschaftBtn;
	private JButton wettkampfBtn;
	private JButton riegeBtn;
	private JButton erfassungBtn;

	/**
	 * Launch the application.
	 */

	@Override
	public void inform(Event e) {
		dirty = true;
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {

		EventDispatcher.addListener(Event.WETTKAMPFTAG_CHANGED, this);
		EventDispatcher.addListener(Event.WERTUNG_CHANGED, this);
		result = SwingJavaBuilder.build(this);
		setLocationByPlatform(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle(Application.NAME + " " + Application.VERSION_VIEW);
		addWindowListener(new MainFrameWindowListener());
		addSaveListener(this);
	}
	
	public void addSaveListener(final JFrame frame) {
		ActionListener saveListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveWettkampftag();
			}
		};

		frame.getRootPane().registerKeyboardAction(saveListener, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW);

	}

	public void openWettkampftag() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Wettkampftag Dateien", "wkt"));
		fc.setAcceptAllFileFilterUsed(false);

		int state = fc.showOpenDialog(this);

		if (state == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				StorageService.filename = file.getAbsolutePath();
				StorageService.loadWettkampftag(StorageService.filename);
			} catch (Throwable t) {
				AxtresLogger.error("Fehler beim öffnen der Datei", t);
				JOptionPane.showMessageDialog(this, "Beim öffnen der Datei " + StorageService.filename + " ist ein Fehler aufgetreten:\n\n" + t.getLocalizedMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public boolean saveWettkampftag() {
		if (StorageService.filename == null) {
			return saveAsWettkampftag();
		} else {
			try {
				StorageService.saveWettkampftag(StorageService.filename, false);
				dirty = false;
			} catch (Throwable t) {
				AxtresLogger.error("Fehler beim speichern der Datei", t);
				JOptionPane.showMessageDialog(this, "Beim Speichern der Datei " + StorageService.filename + " ist ein Fehler aufgetreten:\n\n" + t.getLocalizedMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

		return true;
	}

	public boolean saveAsWettkampftag() {

		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Wettkampftag Dateien", "wkt"));
		fc.setAcceptAllFileFilterUsed(false);

		int state = fc.showSaveDialog(this);

		if (state == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();

			if (file.exists())
				if (JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(this, "Soll die vorhandene Datei überschrieben werden?", "Frage", JOptionPane.YES_NO_OPTION))
					return false;

			StorageService.filename = file.getAbsolutePath();
			if (!StorageService.filename.toLowerCase().endsWith(".wkt"))
				StorageService.filename += ".wkt";

			try {
				StorageService.saveWettkampftag(StorageService.filename, false);
				dirty = false;
			} catch (Throwable t) {
				AxtresLogger.error("Fehler beim speichern der Datei", t);
				JOptionPane.showMessageDialog(this, "Beim Speichern der Datei " + StorageService.filename + " ist ein Fehler aufgetreten:\n\n" + t.getLocalizedMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);

				return false;
			}
		} else {
			return false;
		}

		return true;

	}

	public void newWettkampftag() {
		StorageService.newWettkampftag();
		renameWettkampftag();
	}

	public void renameWettkampftag() {
		new WettkampfTagPropertiesWindow().setVisible(true);
	}

	public void openSettings() {
		System.out.println("Open settings");
		new SettingsDialog().setVisible(true);
	}

	public void openRundenAuswertung() {
		new RundenwettkampfDialog().setVisible(true);
	}

	public void initWertungen() {
		if (JOptionPane.showConfirmDialog(null, "Sind sie sicher, dass Sie alle Wertungen zurücksetzen wollen? Das kann nicht rückgängig gemacht werden!", "Frage", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
			return;

		for (Turner t : WettkampfTag.get().getTurner()) {
			if (t.getWettkampf() != null)
				t.initWertungen(t.getWettkampf().getWertungsmodus());
		}
	}

	public void openInfo() {
		InfoWindow i = new InfoWindow();
		i.setVisible(true);
	}

	@SuppressWarnings("unused")
	private void beenden() {
		dispose();
	}

	public class MainFrameWindowListener extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			if (dirty) {
				int i = JOptionPane.showConfirmDialog(MainFrame.this, "Wettkampftag vor dem Beenden speichern?");
				switch (i) {
				case JOptionPane.CANCEL_OPTION:
					return;
				case JOptionPane.YES_OPTION:
					if (!saveWettkampftag())
						return;
				default:
					MainFrame.this.setVisible(false);
					System.exit(0);
					break;
				}
			} else {
				MainFrame.this.setVisible(false);
				System.exit(0);
			}
		}

		@Override
		public void windowClosed(WindowEvent e) {
			System.exit(0);
		}

	}

	public void setDirty(boolean b) {
		this.dirty = b;
	}

	public void selectAuswertung() {
		tabs.setSelectedIndex(AUSWERTUNG_TAB_INDEX);
	}

	public void openErfassung(Object o) {
		pimpedErfassung.addAuswertungTab(o);
	}

	public void setStatus(String text) {
		status.setText(text);
	}
	
	public void showTurner(){
		disableSelectButtons();
		enableSelectButton(turnerBtn);
	}
	
	public void showMannschaften(){
		disableSelectButtons();
		enableSelectButton(mannschaftBtn);
	}
	
	public void showWettkaempfe(){
		disableSelectButtons();
		enableSelectButton(wettkampfBtn);
	}
	
	public void showRiegen(){
		disableSelectButtons();
		enableSelectButton(riegeBtn);
	}
	
	public void showErfassung(){
		disableSelectButtons();
		enableSelectButton(erfassungBtn);
	}

	private void enableSelectButton(JButton btn) {
		btn.setSelected(true);
	}

	private void disableSelectButtons() {
		turnerBtn.setSelected(false);
		mannschaftBtn.setSelected(false);
		wettkampfBtn.setSelected(false);
		riegeBtn.setSelected(false);
		erfassungBtn.setSelected(false);
	}
	
	

}
