/*
 * CardListener.java
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class CardListener implements MouseListener, ActionListener, MouseWheelListener {

	private boolean moved;
	private ImageIcon imBuf;
	public Card bCard, noCard;
	public CLabel lbuf;

	public CardListener() {
		try {
				new ImageIcon(ImageIO.read(new File("/images/NCRD.jpg")));
			} catch (IOException ioe) {
				//imNoCard = null;
			}
		noCard = new Card("No Card", "/images/NCRD.jpg");
	}

	private boolean isMoved() {
		return this.moved;
	}
	
	private void setMoved(boolean moved) {
		this.moved = moved;
	}
	public ImageIcon getImBuf() {
		return this.imBuf;
	}
	
	public void setImBuf(ImageIcon imBuf) {
		this.imBuf = imBuf;
	}
	
	public Card getCard() {
		return bCard;
	}

	public void setCard(Card bCard) {
		this.bCard = bCard;
	}
	
	public final CLabel getLbuf() {
		return this.lbuf;
	}

	public final void setLbuf(CLabel lbuf) {
		this.lbuf = lbuf;
	}

	public void move(MouseEvent mev) {	
		if (this.isMoved() == false) {
			CLabel label;
			label = (CLabel) mev.getComponent();
			this.setLbuf(label);
			Card dcard = (Card) label.grCard();
			this.setCard(dcard);
			this.setMoved(true);
		}
	}
	
	public void mouseReleased(MouseEvent me2) {
	}
	
	public void mouseExited(MouseEvent me3) {
	}
	
	public void mouseClicked(MouseEvent me4) {
	}
	
	public void mousePressed(MouseEvent me) {
		if (me.getSource() == Battlefield.jlbMD||me.getSource() == Battlefield.jlbOD) {
			if (me.isPopupTrigger()) {
					Battlefield.s1.show(me.getComponent(),
					 me.getX(), me.getY());
				 }
		} else if(me.getSource() instanceof JLabel) {
			if (me.isPopupTrigger() && (me.getSource() == Battlefield.jlbMG||me.getSource() == Battlefield.jlbOG)) {
					Battlefield.s2.show(me.getComponent(),
					 me.getX(), me.getY());
			} else if (me.isPopupTrigger() == true &&
					!(me.getSource() == Battlefield.jlbMG||me.getSource() == Battlefield.jlbOG)) {
				Battlefield.s3.show(me.getComponent(),
						 me.getX(), me.getY());
			} else if (this.isMoved() == true) {
				CLabel label2;
				label2 = (CLabel) me.getComponent();
				label2.setCard(this.getCard());
				this.setMoved(false);
			} else {
				move(me);
			}
		}
	}

	public void mouseWheelMoved(MouseWheelEvent mwe) {
			CLabel lbl;
			lbl = (CLabel) mwe.getComponent();
		if (!lbl.isTapped()) {
			lbl.tap();
		} else {
			lbl.untap();
		} 
	}

	public void actionPerformed(ActionEvent ae) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

