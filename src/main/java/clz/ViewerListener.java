/*
 *      ViewerListener.java
 *      
 *      Copyright 2016 Subhraman Sarkar <subhraman@subhraman-Inspiron>
 *      
 *      This program is free software; you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation; either version 2 of the License, or
 *      (at your option) any later version.
 *      
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *      
 *      You should have received a copy of the GNU General Public License
 *      along with this program; if not, write to the Free Software
 *      Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *      MA 02110-1301, USA.
 *      
 *      
 */


package clz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

public class ViewerListener extends MouseAdapter implements ActionListener {
	JPopupMenu s4 = new JPopupMenu();
	JMenuItem jpiToHand = new JMenuItem("Move to Collec");
	Collec h1, h2;
	Card c;
	
	public ViewerListener(Card card, Collec h1, Collec h2) {
		jpiToHand.addActionListener(this);
		s4.add(jpiToHand);
		c = card;
		this.h1 =  h1; this.h2 = h2;
	}
	
	public void mouseClicked(MouseEvent me) {
		s4.show(me.getComponent(), me.getX(), me.getY());
	}

	@Override
	public void actionPerformed(ActionEvent aevt) {
		if (aevt.getSource() == jpiToHand) {
			if (c != null) {
				if (PRPlayer.opturn) {
					h2.add(c);
					SwingUtilities.updateComponentTreeUI(h2);
				} else {
					h1.add(c);
					SwingUtilities.updateComponentTreeUI(h1);
				}
			}
			jpiToHand.setEnabled(false); // ?
		}
	}
}
