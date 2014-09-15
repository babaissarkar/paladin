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


import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class CWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container cnp = this.getContentPane();
	private GridLayout g = new GridLayout(5, 6);
	private CLabel[] labels = new CLabel[50];
	private Card noCard = new Card("No Card", "/images/NCRD.jpg");
	private int id;
	
	public CWindow(String title, int noc, CardListener c) {
		id = 0;
		this.setTitle(title);
		this.setSize(new Dimension(600, 600));
		g.setHgap(4);
		g.setVgap(4);
		cnp.setLayout(g);
		for (int j = 0; j < noc; j++) {
			labels[j+1] = new CLabel(noCard);
			labels[j+1].addMouseListener(c);
			cnp.add(labels[j+1]);
		}
	}
	
	public void addCard(Card card) {
		labels[id+1].setCard(card);
		id++;
	}
	
	public void showWin() {
		this.setVisible(true);
	}
}
