package de.timherbst.wau.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.javabuilders.swing.SwingJavaBuilder;

import de.timherbst.wau.application.Application;

/**
 * 
 * @author __USER__
 */
public class InfoWindow extends JDialog {
	private static final long serialVersionUID = -1213030956560487480L;

	private JLabel lblShowAppName;
	private JLabel lblShowJavaVersion;
	private JLabel lblShowKontakt;
	private JLabel lblShowVersion;
	private JLabel lblShowCopyright;
	private JLabel lblShowAutor;
	private JLabel lblShowRuntime;

	public InfoWindow() {
		SwingJavaBuilder.build(this);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		
		lblShowAppName.setText("  " + Application.NAME);
		lblShowVersion.setText(Application.VERSION_VIEW);
		lblShowAutor.setText("Tim Herbst");
		lblShowKontakt.setText("mail@timherbst.de");
		lblShowCopyright.setText("© 2011" + (2011 == Calendar.getInstance().get(Calendar.YEAR) ? "" : " - " + Calendar.getInstance().get(Calendar.YEAR)));
		lblShowJavaVersion.setText(System.getProperty("java.version"));
		lblShowRuntime.setText(System.getProperty("java.runtime.name"));
		pack();
		setLocationRelativeTo(Application.getMainFrame());
	}

	@SuppressWarnings("unused")
	private void close() {
		this.dispose();
	}

}