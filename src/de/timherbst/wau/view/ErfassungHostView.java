package de.timherbst.wau.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.javabuilders.swing.SwingJavaBuilder;

import de.axtres.logging.main.AxtresLogger;
import de.timherbst.wau.application.Application;
import de.timherbst.wau.domain.Mannschaft;
import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.domain.riege.EinzelRiege;
import de.timherbst.wau.domain.riege.MannschaftsRiege;
import de.timherbst.wau.domain.wettkampf.EinzelWettkampf;
import de.timherbst.wau.domain.wettkampf.MannschaftsWettkampf;
import de.timherbst.wau.events.Event;
import de.timherbst.wau.events.EventDispatcher;
import de.timherbst.wau.events.EventListener;
import de.timherbst.wau.view.lookup.SelectAuswertungDialog;

public class ErfassungHostView extends JPanel implements EventListener {

	private static final long serialVersionUID = 6735149897545351921L;

	JTabbedPane tabs;
	JButton btnAddTab;
	HashMap<String, Component> openTabs = new HashMap<String, Component>();

	public ErfassungHostView() {
		SwingJavaBuilder.build(this);
		EventDispatcher.addListener(Event.WETTKAMPFTAG_CHANGED, this);
		EventDispatcher.addListener(Event.RIEGE_CHANGED, this);
		EventDispatcher.addListener(Event.MANNSCHAFT_CHANGED, this);
		EventDispatcher.addListener(Event.TURNER_CHANGED, this);
		EventDispatcher.addListener(Event.NEW_WETTKAMPFTAG, this);
		tabs.add("", new JPanel()).setEnabled(false);
		tabs.setTabComponentAt(tabs.getTabCount() - 1, btnAddTab);
		tabs.setEnabledAt(0, false);
		selectLast();

	}

	@SuppressWarnings({ "unused", "serial" })
	private void _addAuswertungTab() {
		new SelectAuswertungDialog() {
			@Override
			public void onSelect(Object o) {
				addAuswertungTab(o);
			}
		}.show(tabs.getTabComponentAt(tabs.getTabCount() - 1));

	}

	public void addAuswertungTab(Object item) {
		if (item == null) {
			JOptionPane.showMessageDialog(this, "Bitte eine Auswahl treffen...", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		AxtresLogger.info("Open Erfassung for: " + item);
		if (!canBeOpened(item)) {
			AxtresLogger.info("Kann kein Fenster oeffnen fuer: " + item);
			JOptionPane.showMessageDialog(this, "Erfassungsfenster kann nicht geöffnet werden.\nMindestens ein Turner von " + item + " ist keinem Wettkampf zugeordnet.", "Fehler", JOptionPane.ERROR_MESSAGE);
			return;
		}
		ErfassungView v = openTable(item);

		if (openTabs.containsKey(v.getNameForWindow())) {
			Application.selectAuswertung();
			tabs.setSelectedComponent(openTabs.get(v.getNameForWindow()));
			AxtresLogger.info("Restored existing tab");
		} else {

			tabs.insertTab(v.getNameForWindow(), null, v, "Erfassungsfenster für " + v.getNameForWindow(), tabs.getTabCount() - 1);
			addCloseButton(v.getNameForWindow(), tabs.getTabCount() - 2);

			Application.selectAuswertung();
			tabs.setSelectedIndex(tabs.getTabCount() - 2);
			openTabs.put(v.getNameForWindow(), tabs.getComponentAt(tabs.getTabCount() - 2));

			AxtresLogger.info("Created new tab");
		}

	}

	private void addCloseButton(final String label, int i) {
		JPanel pnl = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		pnl.setOpaque(false);
		JLabel lbl = new JLabel(label + "   ");
		lbl.setOpaque(false);
		pnl.add(lbl);
		JButton b = new JButton("", new ImageIcon(getClass().getResource("/icons/r16/cancel.png")));
		b.setBorder(null);
		b.setBorderPainted(false);
		b.setFocusPainted(false);
		b.setContentAreaFilled(false);
		b.setOpaque(false);
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int c = tabs.indexOfComponent(openTabs.get(label));
				tabs.remove(openTabs.get(label));
				openTabs.remove(label);
				if (c < tabs.getTabCount() - 1) {
					tabs.setSelectedIndex(c);
				} else
					selectLast();
				AxtresLogger.info("Tab " + label + " closed");
			}
		});
		pnl.add(b);
		tabs.setTabComponentAt(i, pnl);
	}

	private boolean canBeOpened(Object item) {
		if (item instanceof Mannschaft) {
			if (((Mannschaft) item).getWettkampf() != null)
				return true;
		}
		if (item instanceof EinzelRiege) {
			if (((EinzelRiege) item).getTurner() != null)
				for (Turner t : ((EinzelRiege) item).getTurner())
					if (t.getWettkampf() == null)
						return false;
			return true;
		}
		if (item instanceof MannschaftsRiege) {
			if (((MannschaftsRiege) item).getTurner() != null)
				for (Turner t : ((MannschaftsRiege) item).getTurner())
					if (t.getWettkampf() == null)
						return false;
			return true;
		}
		if (item instanceof EinzelWettkampf) {
			if (((EinzelWettkampf) item).getTurner() != null)
				for (Turner t : ((EinzelWettkampf) item).getTurner())
					if (t.getWettkampf() == null)
						return false;
			return true;
		}
		if (item instanceof MannschaftsWettkampf) {
			if (((MannschaftsWettkampf) item).getTurner() != null)
				for (Turner t : ((MannschaftsWettkampf) item).getTurner())
					if (t.getWettkampf() == null)
						return false;
			return true;
		}
		return false;
	}

	private ErfassungView openTable(Object item) {
		ErfassungView table = new ErfassungView();
		table.fillModel(item);
		return table;
	}

	@Override
	public void inform(Event e) {
		if (e.equals(Event.NEW_WETTKAMPFTAG)) {
			closeAllTabs();
		}
		if (e.equals(Event.WETTKAMPFTAG_CHANGED)) {
			closeAllDirtyTabs();
		}
		if (e.equals(Event.MANNSCHAFT_CHANGED) || e.equals(Event.RIEGE_CHANGED) || e.equals(Event.TURNER_CHANGED)) {
			closeAllDirtyTabs();
			updateModels();
		}
	}

	private void updateModels() {
		for (String s : openTabs.keySet())
			((ErfassungView) openTabs.get(s)).refresh();
	}

	private void closeAllDirtyTabs() {
		Vector<String> toRemove = new Vector<String>();
		for (String s : openTabs.keySet())
			if (!((ErfassungView) openTabs.get(s)).doesExist()) {
				tabs.remove(openTabs.get(s));
				toRemove.add(s);
			} else {
				Component c = openTabs.get(s);
				if (!s.equals(((ErfassungView) c).getNameForWindow())) {
					toRemove.add(s);
					openTabs.put(((ErfassungView) c).getNameForWindow(), c);
					addCloseButton(((ErfassungView) c).getNameForWindow(), tabs.indexOfComponent(c));
				}
			}
		if (toRemove.size() > 0) {
			for (String s : toRemove)
				openTabs.remove(s);

			selectLast();
		}

	}

	private void selectLast() {
		if (tabs.getTabCount() > 1)
			tabs.setSelectedIndex(0);
		else {
			tabs.setSelectedIndex(-1);
		}
	}

	private void closeAllTabs() {
		AxtresLogger.info("Closing all tabs");
		for (Component c : openTabs.values())
			tabs.remove(c);
		openTabs.clear();
		selectLast();
	}

}
