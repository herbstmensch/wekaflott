package de.timherbst.wau.view;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.javabuilders.swing.SwingJavaBuilder;
import org.pushingpixels.substance.api.skin.SubstanceAutumnLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceBusinessBlueSteelLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceCeruleanLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceChallengerDeepLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceCremeCoffeeLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceCremeLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceDustCoffeeLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceDustLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceEmeraldDuskLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceGeminiLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteAquaLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceMagellanLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceMarinerLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceMistAquaLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceModerateLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceNebulaBrickWallLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceNebulaLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceOfficeBlack2007LookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceOfficeBlue2007LookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceOfficeSilver2007LookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceSaharaLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceTwilightLookAndFeel;

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
	private JComboBox cbLAF;

	public SettingsDialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		SwingJavaBuilder.build(this);
		loadLAFs();
		loadSettings();
		pack();
		setLocationRelativeTo(Application.getMainFrame());
		cbLAF.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) 
					lafSelect();
			}
		});
	}

	private void loadLAFs() {
		cbLAF.setModel(new DefaultComboBoxModel(
			new Object[]{
				new LAFSelect(SubstanceBusinessLookAndFeel.class.getName()),
				new LAFSelect(SubstanceAutumnLookAndFeel.class.getName()),
				new LAFSelect(SubstanceBusinessBlackSteelLookAndFeel.class.getName()),
				new LAFSelect(SubstanceBusinessBlueSteelLookAndFeel.class.getName()),
				new LAFSelect(SubstanceCeruleanLookAndFeel.class.getName()),
				new LAFSelect(SubstanceChallengerDeepLookAndFeel.class.getName()),
				new LAFSelect(SubstanceCremeCoffeeLookAndFeel.class.getName()),
				new LAFSelect(SubstanceCremeLookAndFeel.class.getName()),
				new LAFSelect(SubstanceDustCoffeeLookAndFeel.class.getName()),
				new LAFSelect(SubstanceDustLookAndFeel.class.getName()),
				new LAFSelect(SubstanceEmeraldDuskLookAndFeel.class.getName()),
				new LAFSelect(SubstanceGeminiLookAndFeel.class.getName()),
				new LAFSelect(SubstanceGraphiteAquaLookAndFeel.class.getName()),
				new LAFSelect(SubstanceGraphiteGlassLookAndFeel.class.getName()),
				new LAFSelect(SubstanceGraphiteLookAndFeel.class.getName()),
				new LAFSelect(SubstanceMagellanLookAndFeel.class.getName()),
				new LAFSelect(SubstanceMarinerLookAndFeel.class.getName()),
				new LAFSelect(SubstanceMistAquaLookAndFeel.class.getName()),
				new LAFSelect(SubstanceMistSilverLookAndFeel.class.getName()),
				new LAFSelect(SubstanceModerateLookAndFeel.class.getName()),
				new LAFSelect(SubstanceNebulaBrickWallLookAndFeel.class.getName()),
				new LAFSelect(SubstanceNebulaLookAndFeel.class.getName()),
				new LAFSelect(SubstanceOfficeBlack2007LookAndFeel.class.getName()),
				new LAFSelect(SubstanceOfficeBlue2007LookAndFeel.class.getName()),
				new LAFSelect(SubstanceOfficeSilver2007LookAndFeel.class.getName()),
				new LAFSelect(SubstanceRavenLookAndFeel.class.getName()),
				new LAFSelect(SubstanceSaharaLookAndFeel.class.getName()),
				new LAFSelect(SubstanceTwilightLookAndFeel.class.getName()),
			}
		));
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
		cbLAF.setSelectedItem(new LAFSelect(AppProperties.getProperty("LOOK_AND_FEEL")));
	}

	private void saveSettings() {
		AppProperties.setProperty("OUTPUT_PATH", txtOutPath.getText());
		AppProperties.setProperty("STD_OUTPUT_SUBDIR", txtStdOutDir.getText());
		AppProperties.setProperty("TEMPLATE_PATH", txtTemplatePath.getText());
		AppProperties.setProperty("STD_AUTOSAVE_PATH", txtASPath.getText());
		AppProperties.setProperty("AUTOSAVE_INTERVAL_SEC", asSpin.getValue() + "");
		AppProperties.setProperty("LOOK_AND_FEEL", ((LAFSelect) cbLAF.getSelectedItem()).getLaf());
	}

	public void cancel() {
		resetLaf();
		setVisible(false);
	}

	public void resetLaf() {
		cbLAF.setSelectedItem(new LAFSelect(AppProperties.getProperty("LOOK_AND_FEEL")));
	}

	public void ok() {
		saveSettings();
		setVisible(false);
	}

	public void lafSelect() {
		try {
			UIManager.setLookAndFeel(((LAFSelect) cbLAF.getSelectedItem()).getLaf());
			SwingUtilities.updateComponentTreeUI(Application.getMainFrame());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Throwable t) {
			AxtresLogger.error("Kann LAF nicht setzen", t);
		}
	}

	public class LAFSelect {

		String laf;

		public String getLaf() {
			return laf;
		}

		public void setLaf(String laf) {
			this.laf = laf;
		}

		public LAFSelect(String laf) {
			this.laf = laf;
		}

		@Override
		public String toString() {
			try {
				return ((LookAndFeel) Class.forName(laf).newInstance()).getName();
			} catch (Throwable t) {
				AxtresLogger.error(t.getMessage(), t);
				return "Error";
			}
		}

		@Override
		public int hashCode() {
			return laf.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof LAFSelect) {
				return ((LAFSelect) obj).getLaf().equals(laf);
			}
			return super.equals(obj);
		}
	}

}
