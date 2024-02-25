/*
 * CWindow.java
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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import clz.xml.DeckEditor;

public class CWindow {
	/**
	 * 
	 */
	private JFrame win;
	private JPanel cnp;
//	private GridLayout g = new GridLayout(5, 6);
	private JMenuBar jmbar;
	private JMenu jpmAdd;
	private JMenuItem jmiAdd;
	private JScrollPane scroll;
	private CardListener cl;
	private Card selCard;
	private SelectorParent de;
	
	private boolean selector = false;
	
	public CWindow(String title, CardListener c) {
		this(title, c, false, null);
	}
	
	public CWindow(String title, CardListener c, boolean selector, SelectorParent de) {
		this.selector = selector;
		this.de = de;
		
		cnp = new JPanel();
		cnp.setLayout(new GridLayout(0, 6, 0, 0));
		win = new JFrame();
		win.setBackground(Color.WHITE);
		win.setTitle(title);
		win.setSize(new Dimension(500, 200));
		win.setAlwaysOnTop(true);
		cl = c;
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
		win.setJMenuBar(jmbar);
//		g.setHgap(4);
//		g.setVgap(4);
//		cnp.setLayout(g);
		scroll = new JScrollPane(cnp,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		win.setContentPane(scroll);
	}

	public void add(Card c) {
		CLabel lbl = new CLabel(c);
		if (!selector) {
			lbl.addMouseListener(cl);
		} else {
			//.removeMouseListener(PRPlayer.cl);
			lbl.addMouseListener(
				new MouseAdapter() {
					public void mousePressed(MouseEvent me) {
						selCard = lbl.getCard();
						//System.out.println(selCard.name);
						if ((de != null) && (selCard != null)) {
							de.indicateSelection(selCard);
							hide();
						}
					}
				}
			);
		}

		cnp.add(lbl);
		SwingUtilities.updateComponentTreeUI(win);
	}
	
	public void addBlank() {
		CLabel lbl = new CLabel();
		lbl.addMouseListener(cl);
		cnp.add(lbl);
		SwingUtilities.updateComponentTreeUI(win);
	}
	
//	private Vector<CLabel> getControls() {
//		Vector<CLabel> cardLabels = new Vector<CLabel>();
//		for (Component c : win.getContentPane().getComponents()) {
//			if (c instanceof CLabel) {
//				if (((CLabel) c).getCard() != null) {
//					cardLabels.add((CLabel) c);
//				}
//			}
//		}
//		return cardLabels;
//	}
	
	public void show() {
		win.setVisible(true);
	}
	
	public void hide() {
		win.setVisible(false);
	}
	
}
