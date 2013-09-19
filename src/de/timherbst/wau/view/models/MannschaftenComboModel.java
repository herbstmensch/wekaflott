package de.timherbst.wau.view.models;

import javax.swing.DefaultComboBoxModel;

import de.timherbst.wau.domain.WettkampfTag;

public class MannschaftenComboModel extends DefaultComboBoxModel {

	private static final long serialVersionUID = 8853836402251053734L;
	private Object selectedItem;

	public MannschaftenComboModel() {

	}

	@Override
	public int getSize() {
		return WettkampfTag.get().getMannschaften().size();
	}

	@Override
	public Object getElementAt(int index) {
		return WettkampfTag.get().getMannschaften().get(index);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selectedItem = anItem;
	}

	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}

}
