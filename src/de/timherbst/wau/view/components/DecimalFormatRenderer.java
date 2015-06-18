package de.timherbst.wau.view.components;

import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DecimalFormatRenderer extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4910711818635206333L;

	private final DecimalFormat formatter;
	
	public DecimalFormatRenderer() {
		this("#0.00");
	}
	
	public DecimalFormatRenderer(String decimalFormat) {
		setHorizontalAlignment(JLabel.RIGHT);
		formatter = new DecimalFormat(decimalFormat);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// First format the cell value as required

		value = formatter.format((Number) value);

		// And pass it on to parent class

		return super.getTableCellRendererComponent(table, value, isSelected,
				hasFocus, row, column);
	}
}