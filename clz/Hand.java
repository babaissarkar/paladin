package clz;

/*
 * Hand.java
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


import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.GridLayout;


@SuppressWarnings("serial")
public class Hand extends JFrame {
	private CLabel[] clbl = new CLabel[30];
	private int i;
	public CardListener a;
	
	public Hand(String title, CardListener al) {
		i = 0;
		a = al;
		setSize(new Dimension(508, 146));
		setTitle(title);
		getContentPane().setLayout(new GridLayout(0, 5, 0, 0));
	}
	
	public void add(Card c) {
		clbl[i] = new CLabel(c);
		clbl[i].addMouseListener(a);
		getContentPane().add(clbl[i]);
		i++;
	}
	
	public Card get(int index) {
		CLabel comp = (CLabel) getContentPane().getComponent(index);
		return comp.getCard();
	}
	
	public void showHand() {
		if (!this.isVisible()) {
			this.setVisible(true);
		}
	}
}
