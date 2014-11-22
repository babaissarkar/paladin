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
	private static Card ncrd = new Card("No Card", "/images/NCRD.jpg");
	private boolean fliped;
	private boolean tapped;
	private String tooltip = "Click to move and click again on another card to paste." +
			" Move the mouse wheel to tap/untap.";

	public CLabel() {
		super(scale(ncrd.getImCard()));
		cards.add(ncrd);
		fliped = false;
		tapped = false;
		this.setToolTipText(tooltip);
	}
	
	public CLabel(Card card1) {
		super(scale(card1.getImCard()));
		cards.add(card1);
		fliped = false;
		tapped = false;
		this.setToolTipText(tooltip);
	}

	public CLabel(String text) {
		super(text);
		cards.add(ncrd);
		fliped = false;
		tapped = false;
		this.setToolTipText(tooltip);
	}

	public CLabel(Icon image) {
		super(scale(image));
		cards.add(ncrd);
		fliped = false;
		tapped = false;
		this.setToolTipText(tooltip);
	}

	public CLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		cards.add(ncrd);
		fliped = false;
		tapped = false;
		this.setToolTipText(tooltip);
	}

	public CLabel(Icon image, int horizontalAlignment) {
		super(scale(image), horizontalAlignment);
		cards.add(ncrd);
		fliped = false;
		tapped = false;
		this.setToolTipText(tooltip);
	}

	public CLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, scale(icon), horizontalAlignment);
		cards.add(ncrd);
		fliped = false;
		tapped = false;
		this.setToolTipText(tooltip);
	}
	
	public final boolean isTapped() {
		return this.tapped;
	}

	public final void setTapped(boolean tapped) {
		this.tapped = tapped;
	}

	public Card getCard() {
		Card card2 = cards.lastElement();
		super.setIcon(scale(cards.lastElement().getImCard()));
		return card2;
	}
	
	public Card grCard() {
		Card card2 = cards.pop();
		if (cards.size() >= 1) {
			super.setIcon(scale(cards.lastElement().getImCard()));
		} else {
			this.setCard(ncrd);
		}
		return card2;
	}
	public void setCard(Card card0) {
		cards.add(card0);
		super.setIcon(scale(cards.lastElement().getImCard()));
	}
	
	public void showFullImage() {
		this.setIcon(this.getCard().getImCard());
	}
	
	public void showNormalImage() {
		this.setIcon(scale(this.getCard().getImCard()));
	}
	
	public void flip() {
		//imCard2 = imCard;
		Card card = this.getCard();
		if (card.bkCard != null) {
			if (fliped) {
				this.setIcon(scale(card.getImCard()));
				fliped = false;
			} else {
				this.setIcon(scale(card.bkCard));
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
		Graphics g = bi.createGraphics();
		g.drawImage(((ImageIcon) i).getImage(), 0, 0, 64, 87, 0, 0, i.getIconWidth(), i.getIconHeight(), null);
		g.dispose();
		return new ImageIcon(bi);
	}

}
