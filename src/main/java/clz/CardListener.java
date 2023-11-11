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

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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


public class CardListener 
	implements MouseListener, MouseWheelListener {

	private boolean moved;
	private ImageIcon imBuf;
	private Card bCard;
	private CLabel lbuf;
	private CLabel selLbl = null;
	private JPopupMenu cardMenu;
	public EnergyBar bar1, bar2;

//	public CardListener() {
////		try {
////				new ImageIcon(ImageIO.read(new File("/images/NCRD.jpg")));
////			} catch (IOException ioe) {
////				//imNoCard = null;
////			}
//		//noCard = new Card("No Card", "/images/NCRD.jpg");
//	}
	
	public CLabel getSelLbl() {
		return this.selLbl;
	}
	
	public CardListener(JPopupMenu cardMenu, EnergyBar bar1, EnergyBar bar2) {
		this.cardMenu = cardMenu;
		this.bar1 = bar1;
		this.bar2 = bar2;
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
			if (lbl.isUpsideDown()) {
				lbl.rot_270();
			} else {
				lbl.rot_90();
			}
			if (lbl.isEnergy()) {
				if (lbl.isOp()) {
					// Increase opponent's mana
//					String civ = lbl.getCard().civility;
//					int num = Constants.numCiv.get(civ);
//					bar2.addEnergy(1, num);

					// Add energy no. to the pool
					Integer[] eno = lbl.getCard().eno;
					for (int i = 0; i < eno.length; i++) {
						//bar2.addEnergy(eno[i], bar2.getCivNo() + i);
						bar2.addEnergy(eno[i], i);
					}
				} else {
					// Increase 1st player's mana
//					String civ = lbl.getCard().civility;
//					int num = Constants.numCiv.get(civ);
//					//System.out.println(civ +  " " + num);
//					bar1.addEnergy(1, num);

					// Add energy no. to the pool
					Integer[] eno = lbl.getCard().eno;
					for (int i = 0; i < eno.length; i++) {
						//bar1.addEnergy(eno[i], bar1.getCivNo() + i);
						bar1.addEnergy(eno[i], i);
					}
				}
			}
		} else {
			lbl.rot_0();
			if (lbl.isEnergy()) {
				if (lbl.isOp()) {
					// Decrease opponent's mana
//					String civ = lbl.getCard().civility;
//					int num = Constants.numCiv.get(civ);
//					bar2.removeEnergy(1, num);

					// remove energy no. from the pool
					Integer[] eno = lbl.getCard().eno;
					for (int i = 0; i < eno.length; i++) {
						//bar2.removeEnergy(eno[i], bar2.getCivNo() + i);
						bar2.removeEnergy(eno[i], i);
					}
				} else {
					// Decrease 1st player's mana
//					String civ = lbl.getCard().civility;
//					int num = Constants.numCiv.get(civ);
//					bar1.removeEnergy(1, num);

					// remove energy no. from the pool
					Integer[] eno = lbl.getCard().eno;
					for (int i = 0; i < eno.length; i++) {
						//bar1.removeEnergy(eno[i], bar1.getCivNo() + i);
						bar1.removeEnergy(eno[i], i);
					}
				}
			}
		} 
	}

	@Override
	public void mouseEntered(MouseEvent me) {
		CLabel label2 = (CLabel) me.getComponent();
		this.selLbl = label2;
		label2.use2(Color.YELLOW);
	}
	@Override
	public void mouseExited(MouseEvent me3) {
		CLabel label2 = (CLabel) me3.getComponent();
		this.selLbl = null;
		label2.unuse2();
	}
	
}

