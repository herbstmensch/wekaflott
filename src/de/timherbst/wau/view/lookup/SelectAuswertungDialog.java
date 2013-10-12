package de.timherbst.wau.view.lookup;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import org.javabuilders.swing.SwingJavaBuilder;

import de.axtres.logging.main.AxtresLogger;
import de.timherbst.wau.application.Application;
import de.timherbst.wau.domain.WettkampfTag;

@SuppressWarnings("serial")
public class SelectAuswertungDialog extends JDialog {

	private JComboBox<String> typ;
	private JComboBox<?> auswahl;

	public SelectAuswertungDialog() {
		SwingJavaBuilder.build(this, loadYaml(), new ResourceBundle[0]);
		setTitle("Bitte auswählen");
		setModal(true);
		typ.setModel(new DefaultComboBoxModel(new Vector<String>(Arrays.asList("Riegen", "Wettkämpfe", "Mannschaften"))));
		fillAuswahl();
		typ.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					fillAuswahl();
					pack();
				}
			}
		});
		pack();
		setLocationRelativeTo(Application.getMainFrame());
	}

	private String loadYaml() {
		try {
			InputStream is = getClass().getResourceAsStream("/de/timherbst/wau/view/lookup/SelectAuswertungDialog.yml");
			if (is != null) {
				Writer writer = new StringWriter();

				char[] buffer = new char[1024];
				try {
					Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
					int n;
					while ((n = reader.read(buffer)) != -1) {
						writer.write(buffer, 0, n);
					}
				} finally {
					is.close();
				}
				return writer.toString();
			} else {
				return "";
			}
		} catch (Exception e) {
			AxtresLogger.error("SelectAuswertungDialog", e);
			return "";
		}
	}

	private void fillAuswahl() {
		if (typ.getSelectedItem() != null) {
			if ("Mannschaften".equals(typ.getSelectedItem()))
				auswahl.setModel(new DefaultComboBoxModel(WettkampfTag.get().getMannschaften()));
			if ("Wettkämpfe".equals(typ.getSelectedItem()))
				auswahl.setModel(new DefaultComboBoxModel(WettkampfTag.get().getWettkaempfe()));
			if ("Riegen".equals(typ.getSelectedItem()))
				auswahl.setModel(new DefaultComboBoxModel(WettkampfTag.get().getRiegen()));

		} else {
			auswahl.setModel(new DefaultComboBoxModel());
		}
	}

	public void show(Component relativeTo) {
		setLocationRelativeTo(relativeTo);
		setVisible(true);
	}

	public void show(Point at) {
		setLocation(at);
		setVisible(true);
	}

	public void show(int x, int y) {
		setLocation(x, y);
		setVisible(true);
	}

	public void onSelect(Object o) {
	}

	public void onCancel() {

	}

	public void cancel() {
		onCancel();
		setVisible(false);
	}

	public void ok() {
		onSelect(auswahl.getSelectedItem());
		setVisible(false);
	}

}
