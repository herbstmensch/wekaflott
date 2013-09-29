package de.timherbst.wau.view;

import javax.swing.JDialog;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import org.javabuilders.swing.SwingJavaBuilder;

import de.axtres.logging.main.AxtresLogger;
import de.axtres.util.AppProperties;
import de.timherbst.wau.application.Application;

@SuppressWarnings("serial")
public class SettingsDialog extends JDialog {

	private JTextField txtOutPath;
	private JTextField txtStdOutDir;
	private JTextField txtTemplatePath;
	private JTextField txtASPath;
	private JSpinner asSpin;

	public SettingsDialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		SwingJavaBuilder.build(this);
		loadSettings();
		pack();
		setLocationRelativeTo(Application.getMainFrame());

	}

	private void loadSettings() {
		txtOutPath.setText(AppProperties.getProperty("OUTPUT_PATH"));
		txtStdOutDir.setText(AppProperties.getProperty("STD_OUTPUT_SUBDIR"));
		txtTemplatePath.setText(AppProperties.getProperty("TEMPLATE_PATH"));
		txtASPath.setText(AppProperties.getProperty("STD_AUTOSAVE_PATH"));
		try {
			asSpin.setValue(new Integer(AppProperties.getProperty("AUTOSAVE_INTERVAL_SEC", "120")));
		} catch (Throwable t) {
			AxtresLogger.error(t.getMessage(), t);
			asSpin.setValue(120);
		}
	}

	private void saveSettings() {
		AppProperties.setProperty("OUTPUT_PATH", txtOutPath.getText());
		AppProperties.setProperty("STD_OUTPUT_SUBDIR", txtStdOutDir.getText());
		AppProperties.setProperty("TEMPLATE_PATH", txtTemplatePath.getText());
		AppProperties.setProperty("STD_AUTOSAVE_PATH", txtASPath.getText());
		AppProperties.setProperty("AUTOSAVE_INTERVAL_SEC", asSpin.getValue() + "");
	}

	public void cancel() {
		setVisible(false);
	}

	public void ok() {
		saveSettings();
		setVisible(false);
	}

}
