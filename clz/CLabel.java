package clz;
/*
 * CLabel.java
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


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Stack;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class CLabel extends JLabel {
	
	
	private Image im;
	private static final long serialVersionUID = 1L;
	private Stack<Card> cards = new Stack<Card>();
	private boolean fliped;
	private boolean tapped;

	public CLabel() {
		super(scale(new Card().bkCard));
		cards.add(new Card("No Card", Battlefield.imNoCard));
		fliped = false;
		tapped = false;
	}
	
	public CLabel(Card card1) {
		super(scale(card1.getImCard()));
		cards.add(card1);
		fliped = false;
		tapped = false;
	}

	public CLabel(String text) {
		super(text);
		cards.add(new Card("No Card", Battlefield.imNoCard));
		fliped = false;
		tapped = false;
	}

	public CLabel(Icon image) {
		super(scale(image));
		cards.add(new Card("No Card", Battlefield.imNoCard));
		fliped = false;
		tapped = false;
	}

	public CLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		cards.add(new Card("No Card", Battlefield.imNoCard));
		fliped = false;
		tapped = false;
	}

	public CLabel(Icon image, int horizontalAlignment) {
		super(scale(image), horizontalAlignment);
		cards.add(new Card("No Card", Battlefield.imNoCard));
		fliped = false;
		tapped = false;
	}

	public CLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, scale(icon), horizontalAlignment);
		cards.add(new Card("No Card", Battlefield.imNoCard));
		fliped = false;
		tapped = false;
	}
	
	public final boolean isTapped() {
		return this.tapped;
	}

	public final void setTapped(boolean tapped) {
		this.tapped = tapped;
	}

	public Card getCard() {
		Card card2 = cards.pop();
		super.setIcon(scale(cards.lastElement().getImCard()));
		return card2;
	}

	public void setCard(Card card0) {
		cards.add(card0);
		super.setIcon(scale(cards.lastElement().getImCard()));
	}
	
	public void flip() {
		//imCard2 = imCard;
		Card card = this.getCard();
		if (card.bkCard != null) {
			if (fliped) {
				this.setIcon(card.getImCard());
				fliped = false;
			} else {
				this.setIcon(card.bkCard);
				fliped = true;
			}
		}
	}
	
	public void tap() {
		setTapped(true);
		im = ((ImageIcon) getIcon()).getImage();
		
		Graphics gd = im.getGraphics();
		gd.fillRect(im.getWidth(null)/2 - 20, im.getHeight(null)/2 - 18, 68, 16);
		gd.setColor(Color.BLACK);
		gd.drawString("Tapped", im.getWidth(null)/2 - 13, im.getHeight(null)/2 - 5);
		gd.dispose();
		repaint();
	}

	public void untap() {
		setTapped(false);
		im = ((ImageIcon) getIcon()).getImage();
		
		Graphics gd2 = im.getGraphics();
		gd2.fillRect(im.getWidth(null)/2 - 20, im.getHeight(null)/2 - 18, 68, 16);
		gd2.setColor(Color.BLACK);
		gd2.drawString("Untapped", im.getWidth(null)/2 - 13, im.getHeight(null)/2 - 5);
		gd2.dispose();
		repaint();
	}
	
	public static ImageIcon scale(Icon i) {
		BufferedImage bi = new BufferedImage(64, 87, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		g.drawImage(((ImageIcon) i).getImage(), 0, 0, 64, 87,
				0, 0, i.getIconWidth(), i.getIconHeight(), null);
		g.dispose();
		return new ImageIcon(bi);
	}

}
