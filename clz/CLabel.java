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

package clz;

import java.awt.Color;
import java.util.Stack;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;


public class CLabel extends JLabel {
	
	private static final long serialVersionUID = 1L;
	private Stack<Card> cards = new Stack<Card>();
	public static Card ncrd = new Card("No Card", "/images/NCRD.jpg");
	private boolean fliped;
	private boolean used;
	private String tooltip = "Click to move and click again on another card to paste.\n" +
			" Move the mouse wheel to use/unuse.";

	public CLabel() {
		this(ncrd);
	}
	
	public CLabel(Card card1) {
		super(ImageManipulator.scale(card1.getImCard(), 64, 87));
		cards.add(card1);
		fliped = false;
		used = false;
		if (card1.name.equalsIgnoreCase("No Card")) {
			this.setToolTipText(tooltip);
		} else {
			this.setToolTipText(card1.name);
		}
	}

	public CLabel(String text) {
		super(text);
		cards.add(ncrd);
		fliped = false;
		used = false;
		this.setToolTipText(tooltip);
	}

	public CLabel(Icon image) {
		super(ImageManipulator.scale(image, 64, 87));
		cards.add(ncrd);
		fliped = false;
		used = false;
		this.setToolTipText(tooltip);
	}

	public CLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		cards.add(ncrd);
		fliped = false;
		used = false;
		this.setToolTipText(tooltip);
	}

	public CLabel(Icon image, int horizontalAlignment) {
		super(ImageManipulator.scale(image, 64, 87), horizontalAlignment);
		cards.add(ncrd);
		fliped = false;
		used = false;
		this.setToolTipText(tooltip);
	}

	public CLabel(String text, Icon image, int horizontalAlignment) {
		super(text, ImageManipulator.scale(image, 64, 87), horizontalAlignment);
		cards.add(ncrd);
		fliped = false;
		used = false;
		this.setToolTipText(tooltip);
	}
	
	public final boolean isUsed() {
		return this.used;
	}

	public final void setUsed(boolean used) {
		this.used = used;
	}

	public Card getCard() {
		Card card2 = cards.lastElement();
		super.setIcon(ImageManipulator.scale(cards.lastElement().getImCard(), 64, 87));
		return card2;
	}
	
	public Card grCard() {
		Card card2 = cards.pop();
		if (cards.size() >= 1) {
			super.setIcon(ImageManipulator.scale(cards.lastElement().getImCard(), 64, 87));
		} else {
			this.setCard(ncrd);
		}
		return card2;
	}
	public void setCard(Card card0) {
		cards.add(card0);
		super.setIcon(ImageManipulator.scale(cards.lastElement().getImCard(), 64, 87));
		if (card0.name.equalsIgnoreCase("No Card")) {
			this.setToolTipText(tooltip);
		} else {
			this.setToolTipText(card0.name);
		}
	}
	
	public void showFullImage() {
		this.setIcon(this.getCard().getImCard());
	}
	
	public void showNormalImage() {
		this.setIcon(ImageManipulator.scale(this.getCard().getImCard(), 64, 87));
	}
	
	public void flip() {
		//imCard2 = imCard;
		Card card = this.getCard();
		if (card.bkCard != null) {
			if (fliped) {
				this.setIcon(ImageManipulator.scale(card.getImCard(), 64, 87));
				fliped = false;
			} else {
				this.setIcon(ImageManipulator.scale(card.bkCard, 64, 87));
				fliped = true;
			}
		}
	}
	
	public void use() {
		setUsed(true);
		Card c = this.getCard();
		if (c.civility.equalsIgnoreCase("Raenid")) {
			this.setBorder(new LineBorder(Color.YELLOW, 2));
		} else if (c.civility.equalsIgnoreCase("Asarn")) {
			this.setBorder(new LineBorder(Color.RED, 2));
		} else if (c.civility.equalsIgnoreCase("Mayarth")) {
			this.setBorder(new LineBorder(Color.ORANGE, 2));
		} else if (c.civility.equalsIgnoreCase("Zivar")) {
			this.setBorder(new LineBorder(Color.BLACK, 2));
		} else if (c.civility.equalsIgnoreCase("Niaz")) {
			this.setBorder(new LineBorder(Color.BLUE, 2));
		} else if (c.civility.equalsIgnoreCase("Kshiti")) {
			this.setBorder(new LineBorder(Color.GREEN, 2));
		} else {
			this.setBorder(new LineBorder(Color.PINK, 2));
		}
		repaint();
	}

	public void unuse() {
		setUsed(false);
		this.setBorder(null);
		repaint();
	}

}
