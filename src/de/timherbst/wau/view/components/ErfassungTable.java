package de.timherbst.wau.view.components;

import java.util.EventObject;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import de.timherbst.wau.domain.wertungen.Wertung;
import de.timherbst.wau.util.AutofitTableColumns;
import de.timherbst.wau.view.wertungen.WertungCellEditor;
import de.timherbst.wau.view.wertungen.WertungCellRenderer;

public class ErfassungTable extends JTable {

	private static final long serialVersionUID = 1L;

	public ErfassungTable() {
		super();
		setDefaultRenderer(Wertung.class, new WertungCellRenderer());
		setDefaultEditor(Wertung.class, new WertungCellEditor());
		setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		setAutoCreateRowSorter(true);
		setCellSelectionEnabled(true);
		setColumnSelectionAllowed(true);
		setRowSelectionAllowed(true);
		getSelectionModel().addListSelectionListener(new SelectionListener());
		getColumnModel().getSelectionModel().addListSelectionListener(new SelectionListener());

		AutofitTableColumns.autoResizeTable(this, true);
	}

	@Override
	public boolean editCellAt(int row, int column, EventObject e) {
		boolean b;
		if (b = super.editCellAt(row, column, e)) {
			if (getCellEditor() != null) {
				((WertungCellEditor) getCellEditor()).focus();
			}
		}
		return b;
	}

	@Override
	public void setModel(TableModel dataModel) {
		if (isEditing()) {
			if (!getCellEditor().stopCellEditing()) {
				throw new RuntimeException("Table is in Edit Mode. Can't switch Model.");
			}
		}
		super.setModel(dataModel);
		return;
	}

	private class SelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if (isEditing() && getEditingColumn() == getSelectedColumn() && getEditingRow() == getSelectedRow())
				return;
			ErfassungTable.this.editCellAt(getSelectedRow(), getSelectedColumn());
		}
	}

}
