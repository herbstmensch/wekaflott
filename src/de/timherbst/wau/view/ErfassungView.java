package de.timherbst.wau.view;

import java.util.List;

import javax.swing.JPanel;

import org.javabuilders.swing.SwingJavaBuilder;

import de.axtres.logging.main.AxtresLogger;
import de.timherbst.wau.domain.Mannschaft;
import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.domain.WettkampfTag;
import de.timherbst.wau.domain.riege.EinzelRiege;
import de.timherbst.wau.domain.riege.MannschaftsRiege;
import de.timherbst.wau.domain.riege.Riege;
import de.timherbst.wau.domain.wettkampf.EinzelWettkampf;
import de.timherbst.wau.domain.wettkampf.MannschaftsWettkampf;
import de.timherbst.wau.domain.wettkampf.Wettkampf;
import de.timherbst.wau.view.components.ErfassungTable;
import de.timherbst.wau.view.models.ErfassungTableModel;

public class ErfassungView extends JPanel {

	private static final long serialVersionUID = 6735149897545351921L;
	ErfassungTable table;
	Object item;

	public ErfassungView() {
		SwingJavaBuilder.build(this);

	}

	public void fillModel(Object item) {
		this.item = item;
		if (item instanceof Mannschaft) {
			if (((Mannschaft) item).getWettkampf() == null)
				return;
			setModel(((Mannschaft) item).getTurner(), ErfassungTableModel.CONTEXT_MANNSCHAFT);
		}
		if (item instanceof EinzelRiege) {
			setModel(((EinzelRiege) item).getTurner(), ErfassungTableModel.CONTEXT_EINZEL_RIEGE);
		}
		if (item instanceof MannschaftsRiege) {
			setModel(((MannschaftsRiege) item).getTurner(), ErfassungTableModel.CONTEXT_MANNSCHAFTS_RIEGE);
		}
		if (item instanceof EinzelWettkampf) {
			setModel(((EinzelWettkampf) item).getTurner(), ErfassungTableModel.CONTEXT_EINZEL_WETTKAMPF);
		}
		if (item instanceof MannschaftsWettkampf) {
			setModel(((MannschaftsWettkampf) item).getTurner(), ErfassungTableModel.CONTEXT_MANNSCHAFTS_WETTKAMPF);
		}
	}

	private void setModel(List<Turner> turner, String context) {
		table.setModel(new ErfassungTableModel(turner, context));
	}

	public String getNameForWindow() {
		if (item instanceof Mannschaft) {
			if (((Mannschaft) item).getWettkampf() == null)
				return null;
			return "MS: " + ((Mannschaft) item).getName() + " (" + ((Mannschaft) item).getWettkampf().getName() + ")";
		}
		if (item instanceof EinzelRiege) {
			return "RG: " + ((EinzelRiege) item).getName();
		}
		if (item instanceof MannschaftsRiege) {
			return "RG: " + ((MannschaftsRiege) item).getName();
		}
		if (item instanceof EinzelWettkampf) {
			return "WK: " + ((EinzelWettkampf) item).getName();
		}
		if (item instanceof MannschaftsWettkampf) {
			return "WK: " + ((MannschaftsWettkampf) item).getName();
		}
		return null;
	}

	public boolean doesExist() {
		AxtresLogger.info("Checking existence of " + item);
		if (item instanceof Mannschaft) {
			AxtresLogger.info("" + item + (WettkampfTag.get().getMannschaften().contains(item) ? " exists" : " was removed"));
			return WettkampfTag.get().getMannschaften().contains(item);
		}
		if (item instanceof Riege) {
			AxtresLogger.info("" + item + (WettkampfTag.get().getRiegen().contains(item) ? " exists" : " was removed"));
			return WettkampfTag.get().getRiegen().contains(item);
		}
		if (item instanceof Wettkampf) {
			AxtresLogger.info("" + item + (WettkampfTag.get().getWettkaempfe().contains(item) ? " exists" : " was removed"));
			return WettkampfTag.get().getWettkaempfe().contains(item);
		}
		return false;
	}

	public void refresh() {
		((ErfassungTableModel) table.getModel()).fireTableDataChanged();
	}

}
