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

import de.timherbst.wau.application.Application;

/**
 * 
 * @author __USER__
 */
public class InfoWindow extends JDialog {
	private static final long serialVersionUID = -1213030956560487480L;

	public InfoWindow() {
		super(Application.getMainFrame(), "Info...", true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		init();
		setLocationRelativeTo(Application.getMainFrame());
	}

	private void init() {
		initComponents();
	}

	// GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jPanel1 = new JPanel();
		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		jLabel3 = new JLabel();
		jLabel7 = new JLabel();
		jLabel5 = new JLabel();
		jLabel8 = new JLabel();
		jLabel4 = new JLabel();
		jLabel11 = new JLabel();
		jLabel12 = new JLabel();
		jLabel13 = new JLabel();
		jLabel10 = new JLabel();
		jLabel9 = new JLabel();
		jLabel6 = new JLabel();
		jButton1 = new JButton();

		jPanel1.setBackground(new java.awt.Color(255, 255, 255));
		jPanel1.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		jLabel1.setFont(new Font("Tahoma", 1, 24));
		jLabel1.setIcon(new ImageIcon(getClass().getResource("/icons/r32/app.png")));
		jLabel1.setText("  " + Application.NAME);

		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addContainerGap(49, Short.MAX_VALUE)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jLabel2.setFont(new Font("Tahoma", 1, 14));
		jLabel2.setText("Version:");

		jLabel3.setFont(new Font("Tahoma", 1, 14));
		jLabel3.setText(Application.VERSION_VIEW);

		jLabel7.setFont(new Font("Tahoma", 0, 14));
		jLabel7.setText("Tim Herbst");

		jLabel13.setFont(new Font("Tahoma", 0, 14));
		jLabel13.setText("mail@timherbst.de");

		jLabel5.setFont(new Font("Tahoma", 0, 14));
		jLabel5.setText("(c) 2011" + (2011 == Calendar.getInstance().get(Calendar.YEAR) ? "" : " - " + Calendar.getInstance().get(Calendar.YEAR)));

		jLabel8.setFont(new Font("Tahoma", 0, 14));
		jLabel8.setText("Runtime:");

		jLabel4.setFont(new Font("Tahoma", 0, 14));
		jLabel4.setText("Copyright:");

		jLabel11.setFont(new Font("Tahoma", 0, 14));
		jLabel11.setText(System.getProperty("java.version"));

		jLabel10.setFont(new Font("Tahoma", 0, 14));
		jLabel10.setText("Java Version:");

		jLabel9.setFont(new Font("Tahoma", 0, 14));
		jLabel9.setText(System.getProperty("java.runtime.name"));

		jLabel6.setFont(new Font("Tahoma", 0, 14));
		jLabel6.setText("Autor:");

		jLabel12.setFont(new Font("Tahoma", 0, 14));
		jLabel12.setText("Kontakt:");

		jButton1.setText("Schließen");
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGap(36, 36, 36).addGroup(layout.createParallelGroup(Alignment.LEADING, false).addComponent(jLabel10, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel12, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE).addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE).addComponent(jLabel7, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE).addComponent(jLabel13, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE).addComponent(jLabel9, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE).addComponent(jLabel11, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)).addContainerGap()).addGroup(Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(292, Short.MAX_VALUE).addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, layout.createSequentialGroup().addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel13, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel12, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGap(13, 13, 13).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel11, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel10, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addContainerGap()));

		pack();
	}// </editor-fold>

	// GEN-END:initComponents

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		this.dispose();
	}

	// GEN-BEGIN:variables
	// Variables declaration - do not modify
	private JButton jButton1;
	private JLabel jLabel1;
	private JLabel jLabel10;
	private JLabel jLabel11;
	private JLabel jLabel12;
	private JLabel jLabel13;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JPanel jPanel1;
	// End of variables declaration//GEN-END:variables

}