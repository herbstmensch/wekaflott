package de.timherbst.wau.view.wertungen;

import java.awt.Color;
import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.plaf.ComponentUI;
import javax.swing.table.TableCellEditor;

import sun.swing.DefaultLookup;
import de.axtres.logging.main.AxtresLogger;
import de.timherbst.wau.domain.wertungen.CdPWertung;
import de.timherbst.wau.domain.wertungen.PStufenWertung;
import de.timherbst.wau.events.Event;
import de.timherbst.wau.events.EventDispatcher;

public class WertungCellEditor extends AbstractCellEditor implements TableCellEditor {
	/** The look and feel delegate for this component. */
	protected transient ComponentUI ui;

	private static final long serialVersionUID = 3443590304981944396L;
	public static final int COMPONENT_HEIGHT = 45;
	private JComponent c;

	public Object getCellEditorValue() {
		if (c instanceof CdPWertungField)
			return ((CdPWertungField) c).getWertung();
		if (c instanceof PStufenWertungField)
			return ((PStufenWertungField) c).getWertung();

		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		Color alternateColor = null;
		if (value instanceof CdPWertung) {
			c = new CdPWertungField((CdPWertung) value);
		} else if (value instanceof PStufenWertung) {
			c = new PStufenWertungField((PStufenWertung) value);
		}
		alternateColor = DefaultLookup.getColor((JComponent) c, ui, "Table.alternateRowColor");

		if (alternateColor != null && row % 2 == 0) {
			c.setBackground(alternateColor);
		} else {
			c.setBackground(Color.white);
		}

		int labelHeight = (int) c.getMinimumSize().getHeight();
		int tableRowHeight = table.getRowHeight(row);

		if (labelHeight > tableRowHeight) {
			table.setRowHeight(row, labelHeight);
		}
		c.requestFocus();

		return c;
	}

	@Override
	public boolean stopCellEditing() {
		boolean result = false;
		try {
			result = super.stopCellEditing();
			((WertungField) c).setFieldBackground(Color.WHITE);
		} catch (Throwable e) {
			AxtresLogger.error("Zelle enthält Fehler", e);
			((WertungField) c).setFieldBackground(Color.RED);
			result = false;
		}
		EventDispatcher.dispatchEvent(Event.WERTUNG_CHANGED);
		return result;
	}

	public void focus() {
		if (c != null)
			c.requestFocus();
	}

	// The following methods override the defaults for performance reasons
	public void validate() {
	}

	public void revalidate() {
	}

}
