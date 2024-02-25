/*
 * Collec.java
 * 
 * Copyright 2014 Subhraman Sarkar <subhraman@subhraman-Inspiron-660s>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */

package clz;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


@SuppressWarnings("serial")
public class Collec extends JFrame {
	private JPanel panel, pnlBPoints;
	private JLabel lblBPoints;
	private JSpinner bPoints;
	private CLabel[] clbl = new CLabel[30];
	private int i;
	public int maxBPoints = 7;
	public CardListener a;
	private JMenuBar jmbar;
	private JMenu jpmAdd;
	private JMenuItem jmiAdd;
	
	public Collec(String title, CardListener al) {
		i = 0;
		a = al;
		bPoints = new JSpinner();
		bPoints.setValue(maxBPoints);
		panel = new JPanel();
		pnlBPoints = new JPanel();
		lblBPoints = new JLabel("Barrier Points :");
		setSize(new Dimension(510, 200));
		setTitle(title);
		setAlwaysOnTop(true);
		getContentPane().setLayout(new BorderLayout());
		panel.setLayout(new GridLayout(0, 7, 0, 0));
		pnlBPoints.setLayout(new FlowLayout());
		pnlBPoints.add(lblBPoints);
		pnlBPoints.add(bPoints);
		
		jmbar = new JMenuBar();
		jpmAdd = new JMenu("Add");
		jmiAdd = new JMenuItem("Add empty placeholder");
		jmiAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				//if (ae.getSource() == jmiAdd) {
					addBlank();
				//}
			}
		});
		jpmAdd.add(jmiAdd);
		jmbar.add(jpmAdd);
		setJMenuBar(jmbar);
		
		getContentPane().add(pnlBPoints, BorderLayout.NORTH);
		getContentPane().add(panel, BorderLayout.CENTER);
	}
	
	
	
	public void add(Card c) {
		clbl[i] = new CLabel(c);
		clbl[i].addMouseListener(a);
		panel.add(clbl[i]);
		SwingUtilities.updateComponentTreeUI(this);
		i++;
	}
	
	public void addBlank() {
		CLabel lbl = new CLabel();
		lbl.addMouseListener(a);
		panel.add(lbl);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void remove(CLabel clb) {
		panel.remove(clb);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public Card get(int index) {
		CLabel comp = (CLabel) getContentPane().getComponent(index);
		return comp.getCard();
	}
	
	public void setBPoints(int bp) {
		maxBPoints = bp;
		bPoints.setValue(bp);
	}
	
	public void showHand() {
		if (!this.isVisible()) {
			this.setVisible(true);
		}
	}
}
