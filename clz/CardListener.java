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
package clz;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
//import java.io.File;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;


public class CardListener implements MouseListener, MouseWheelListener {

	private boolean moved;
	private ImageIcon imBuf;
	private Card bCard;
	private CLabel lbuf;
	private JPopupMenu cardMenu;

//	public CardListener() {
////		try {
////				new ImageIcon(ImageIO.read(new File("/images/NCRD.jpg")));
////			} catch (IOException ioe) {
////				//imNoCard = null;
////			}
//		//noCard = new Card("No Card", "/images/NCRD.jpg");
//	}
	
	public CardListener(JPopupMenu cardMenu) {
		this.cardMenu = cardMenu;
	}

	private boolean isMoved() {
		return this.moved;
	}
	
	public void setMoved(boolean moved) {
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
		//System.out.println("Grabbed card!");
	}
	
	public final CLabel getLbuf() {
		return this.lbuf;
	}

	public final void setLbuf(CLabel lbuf) {
		this.lbuf = lbuf;
	}

	public void move(MouseEvent mev) {	
		CLabel label;
		label = (CLabel) mev.getComponent();
		this.setLbuf(label);
		Card dcard = (Card) label.grCard();
		if (dcard != null) {
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
		if (!me.isPopupTrigger()) {
			if (this.isMoved()) {
				CLabel label2 = (CLabel) me.getComponent();
//				Card card = getCard();
				//if (card != null) {
					label2.setCard(getCard());
					this.setMoved(false);
				//}
			} else {
				move(me);
			} 
		} else {
			showPopup(me);
		}
	}
	
	private void showPopup(MouseEvent me) {
		cardMenu.show(me.getComponent(), me.getX(), me.getY());
	}

	public void mouseWheelMoved(MouseWheelEvent mwe) {
		CLabel lbl;
		lbl = (CLabel) mwe.getComponent();
		if (!lbl.isUsed()) {
			lbl.rot_90();
		} else {
			lbl.rot_0();
		} 
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

