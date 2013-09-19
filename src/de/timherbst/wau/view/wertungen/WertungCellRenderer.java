package de.timherbst.wau.view.wertungen;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.plaf.ComponentUI;
import javax.swing.table.TableCellRenderer;

import sun.swing.DefaultLookup;
import de.timherbst.wau.domain.wertungen.CdPWertung;
import de.timherbst.wau.domain.wertungen.PStufenWertung;

public class WertungCellRenderer implements TableCellRenderer {
	/** The look and feel delegate for this component. */
	protected transient ComponentUI ui;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
		// 'value' is value contained in the cell located at
		// (rowIndex, vColIndex)

		if (hasFocus) {
			// this cell is the anchor and the table has the focus
		}

		JComponent c = null;
		Color alternateColor = null;
		if (value instanceof CdPWertung) {
			c = new CdPWertungLabel((CdPWertung) value);

		} else if (value instanceof PStufenWertung) {
			c = new PStufenWertungLabel((PStufenWertung) value);

		} else {
			c = new JLabel();
		}
		alternateColor = DefaultLookup.getColor((JComponent) c, ui, "Table.alternateRowColor");

		int labelHeight = (int) c.getMinimumSize().getHeight();
		int tableRowHeight = table.getRowHeight(rowIndex);

		if (labelHeight != tableRowHeight) {
			if (!table.isEditing() || table.getEditingRow() != rowIndex) {
				table.setRowHeight(rowIndex, labelHeight < 1 ? 1 : labelHeight);
			}
		}
		if (isSelected) {
			c.setForeground(table.getSelectionForeground());
			c.setBackground(table.getSelectionBackground());
		} else {
			if (alternateColor != null && rowIndex % 2 == 1) {
				c.setBackground(alternateColor);
			} else {
				c.setBackground(Color.white);
			}
		}

		return c;
	}

	// The following methods override the defaults for performance reasons
	public void validate() {
	}

	public void revalidate() {
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
	}

	public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
	}

}
