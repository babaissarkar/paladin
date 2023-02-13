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
import java.awt.Dimension;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;


public class CLabel extends JLabel {
	
	private static final long serialVersionUID = 1L;
	private Stack<Card> cards = new Stack<Card>();
	//public static Card ncrd = new Card("No Card", "/images/NCRD.jpg");
	private boolean fliped;
	private boolean used;
	private boolean upside_down;
	private boolean isEnergy;

	public boolean isOp() {
		return isOp;
	}

	public void setOp(boolean op) {
		isOp = op;
	}

	private boolean isOp; /* Whether this is opponent's area or not */
	private String default_tooltip =
			"<html><body>Click to move and click again on another card to paste.<br/>" +
			"Move the mouse wheel to use/unuse.</body></html>";

	public CLabel() throws IOException {
		//this(ncrd);
		//System.out.println("Generated");
		super(ImageManipulator.scale(
				new ImageIcon(
						ImageIO.read(CLabel.class.getResourceAsStream("/images/NCRD.jpg"))), 64, 87));
		setPreferredSize(new Dimension(64, 87));
	}
	
	public CLabel(Card card1) {
		super(ImageManipulator.scale(card1.getImCard(), 64, 87));
		cards.add(card1);
		setDefaultState();
		if (card1.name.equalsIgnoreCase("No Card")) {
			setDefaultTooltip();
		} else {
			this.setToolTipText(createCardTooltip(card1));
		}
	}

	public CLabel(String text) {
		super(text);
		//cards.add(ncrd);
		init();
	}

	public CLabel(Icon image) {
		super(ImageManipulator.scale(image, 64, 87));
		//cards.add(ncrd);
		init();
	}

	public CLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		//cards.add(ncrd);
		init();
	}

	public CLabel(Icon image, int horizontalAlignment) {
		super(ImageManipulator.scale(image, 64, 87), horizontalAlignment);
//		cards.add(ncrd);
		init();
	}

	public CLabel(String text, Icon image, int horizontalAlignment) {
		super(text, ImageManipulator.scale(image, 64, 87), horizontalAlignment);
//		cards.add(ncrd);
		init();
	}

	private void init() {
		setDefaultState();
		setDefaultTooltip();
	}

	private void setDefaultState() {
		fliped = false;
		used = false;
		isEnergy = false;
		setUpsideDown(false);
		setPreferredSize(new Dimension(64, 87));
	}

	private void setDefaultTooltip() {
		setToolTipText(default_tooltip);
	}

	private String createCardTooltip(Card card1) {
		String htmlInfo = card1.createHtmlInfo();
		return htmlInfo;
	}
	
	public final boolean isUsed() {
		return this.used;
	}

	public final void setUsed(boolean used) {
		this.used = used;
	}

	public Card getCard() {
		if (cards.size() > 0) {
			Card card2 = cards.lastElement();
			//super.setIcon(ImageManipulator.scale(cards.lastElement().getImCard(), 64, 87));
			return card2;
		} else {
			return null;
		}
	}
	
	public Card grCard() {
		setDefaultTooltip();

		if (cards.size() > 0) {
			Card card2 = cards.pop();
			if (cards.size() > 0) {
				super.setIcon(ImageManipulator.scale(cards.lastElement().getImCard(), 64, 87));
			} else {
				//			this.setCard(ncrd);
				try {
					super.setIcon(
						ImageManipulator.scale(
							new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/NCRD.jpg"))), 64, 87
							));
				} catch(Exception e) {
					// What to do if the image is missing?
					System.err.println("Missing Resource!");
				}
			}
			return card2;
		} else {
			return null; // NULL
		}
	}
	public void setCard(Card card0) {
		if (card0 != null) {
			cards.add(card0);
			super.setIcon(ImageManipulator.scale(card0.getImCard(), 64, 87));
			if (card0.name.equalsIgnoreCase("No Card")) {
				this.setToolTipText(default_tooltip);
			} else {
				this.setToolTipText(createCardTooltip(card0));
						// Problem here ↓
						//card0.name + "," + Card.energyToString(card0.energy) + "," + Card.energyToString(card0.eno));
			} 
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
				this.setToolTipText(createCardTooltip(card));
				fliped = false;
			} else {
				this.setIcon(ImageManipulator.scale(card.bkCard, 64, 87));
				setDefaultTooltip();
				fliped = true;
			}
		}
	}
	
	public void use2() {
		setUsed(true);
		Card c = this.getCard();

		// Needs to be rewritten using Constants class ↓
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
			this.setBorder(new LineBorder(Color.DARK_GRAY, 2));
		}
		repaint();
	}
	
	public void unuse2() {
		setUsed(false);
		this.setBorder(null);
		repaint();
	}
	
	public void rot_90() {
		setUsed(true);
		
		ImageIcon img = ImageManipulator.scale(this.getCard().getImCard(), 64, 87);
		ImageIcon rot_img = ImageManipulator.rotate90(img);
		this.setIcon(rot_img);
	}
	
	public void rot_180() {
		ImageIcon img = ImageManipulator.scale(this.getCard().getImCard(), 64, 87);
		
		if (isUpsideDown()) {
			setUpsideDown(false);
		} else {
			setUpsideDown(true);
			img = ImageManipulator.flipVert(img);
		}
	
		this.setIcon(img);
	}

	public void rot_270() {
		setUsed(true);
		
		ImageIcon img = ImageManipulator.scale(this.getCard().getImCard(), 64, 87);
		img = ImageManipulator.rotate270(img);
		this.setIcon(img);
	}
	
	public void rot_0() {
		setUsed(false);
		ImageIcon img = ImageManipulator.scale(this.getCard().getImCard(), 64, 87);
		
		// Flipping the image if it was flipped before tapping.
		if (isUpsideDown()) {
			img = ImageManipulator.flipVert(img);
		}
		
		this.setIcon(img);
	}
	
//	public void flipBack() {
//		setUpsideDown(false);
//		setOrig();
//	}
//	
//	public void unuse() {
//		setUsed(false);
//		if (!isUpsideDown()) {
//			setOrig();
//		} else {
//			ImageIcon img = ImageManipulator.scale(this.getIcon(), 64, 87);
//			ImageIcon rot_img = ImageManipulator.flipVert(img);
//			this.setIcon(rot_img);
//		}
//	}
//	
	public void setOrig() {
		this.setIcon(ImageManipulator.scale(this.getCard().getImCard(), 64, 87));
	}

	public boolean isUpsideDown() {
		return upside_down;
	}

	public void setUpsideDown(boolean upside_down) {
		this.upside_down = upside_down;
	}

	public boolean isEnergy() {
		return isEnergy;
	}

	public void setEnergy(boolean isEnergy) {
		this.isEnergy = isEnergy;
	}
}
