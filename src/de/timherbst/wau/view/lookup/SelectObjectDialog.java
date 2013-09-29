package de.timherbst.wau.view.lookup;

import java.awt.Component;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import org.javabuilders.swing.SwingJavaBuilder;

import de.axtres.logging.main.AxtresLogger;

@SuppressWarnings("serial")
public class SelectObjectDialog<O extends Object> extends JDialog {

	private JComboBox combo;
	List<O> list;

	public SelectObjectDialog(List<O> l, boolean showEmpty, String title) {
		SwingJavaBuilder.build(this, loadYaml(), new ResourceBundle[0]);
		setTitle(title);
		setModal(true);
		this.list = new Vector<O>(l == null ? new Vector<O>() : l);
		if (showEmpty) {
			list.add(0, null);
		}

		combo.setModel(new DefaultComboBoxModel(new Vector<O>(list)));
		pack();
	}

	private String loadYaml() {
		try {
			InputStream is = getClass().getResourceAsStream("/de/timherbst/wau/view/lookup/SelectObjectDialog.yml");
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

	// public void show() {
	// setVisible(true);
	// }

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

	public void onSelect(O o) {
	}

	public void onCancel() {

	}

	// public O getSelected() {
	// return (O) selected;
	// }
	//
	// public void setSelected(O selected) {
	// this.selected = selected;
	// }

	// public static <O extends Object> O selectFrom(List<O> l, boolean
	// showEmpty,
	// Component component, String title) {
	// SelectObjectDialog<O> d = new SelectObjectDialog<O>(l, showEmpty, title);
	// d.setLocationRelativeTo(component);
	// d.setVisible(true);
	// return d.getSelected();
	// }

	public void cancel() {
		onCancel();
		setVisible(false);
	}

	@SuppressWarnings("unchecked")
	public void ok() {
		onSelect((O) combo.getSelectedItem());
		setVisible(false);
	}

	// @SuppressWarnings({ "unchecked", "unused" })
	// private void ok() {
	// if (combo.getSelectedItem() == null) {
	// if (JOptionPane
	// .showConfirmDialog(
	// null,
	// "Sind sie sicher, dass Sie die Zuordnung entfernen wollen?",
	// "Frage", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
	// return;
	// }
	// setSelected((O) combo.getSelectedItem());
	// setVisible(false);
	// }

}
