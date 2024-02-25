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
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;


public class CLabel extends JLabel {
	
	private static final long serialVersionUID = 1L;
	private Stack<Card> cards = new Stack<Card>();
	private boolean fliped;
	private boolean used = false;
	private boolean upside_down;
	private boolean isEnergy;
	private String ncrdnePath = "/images/NCRD.jpg";
	private String ncrdePath = "/images/NCRD_E.jpg";
	private String ncrdPath = ncrdnePath;

	/** Getter : Owned by opponent? */
	public boolean isOp() {
		return isOp;
	}

	/** Setter : Owned by opponent? */
	public void setOp(boolean op) {
		isOp = op;
	}

	/** Whether this is opponent's area or not */
	private boolean isOp;
	
	private String default_tooltip =
			"<html><body>Click to move and click again on another card to paste.<br/>" +
			"Move the mouse wheel to use/unuse.</body></html>";

	public CLabel() {
		this(false);
	}
	
	public CLabel(boolean isEnergy) {
		super();
		this.setEnergy(isEnergy);
		try {
			this.setIcon(
					ImageUtils.scale(
							new ImageIcon(
									ImageIO.read(
											CLabel.class.getResourceAsStream( ncrdPath ) )), Constants.CARD_WIDTH));
		} catch (IOException e) {
			System.err.println(String.format("Missing resource : %s", ncrdPath));
		}
		this.setPreferredSize(new Dimension(Constants.CARD_WIDTH, 87));
		this.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
	}
	
	public CLabel(Card card1) {
		super(ImageUtils.scale(card1.getImCard(), Constants.CARD_WIDTH));
		cards.add(card1);
		init();
		
		if (card1.name.equalsIgnoreCase("No Card")) {
			this.setDefaultTooltip();
		} else {
//			this.setToolTipText(createCardTooltip(card1));
		}
		this.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
	}
	

	public CLabel(String text) {
		super(text);

		init();
	}

	public CLabel(Icon image) {
		super(ImageUtils.scale(image, Constants.CARD_WIDTH));

		init();
	}

	public CLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);

		init();
	}

	public CLabel(Icon image, int horizontalAlignment) {
		super(ImageUtils.scale(image, Constants.CARD_WIDTH), horizontalAlignment);

		init();
	}

	public CLabel(String text, Icon image, int horizontalAlignment) {
		super(text, ImageUtils.scale(image, Constants.CARD_WIDTH), horizontalAlignment);

		init();
	}

	private void init() {
		setDefaultState();
		setDefaultTooltip();
		this.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
	}

	private void setDefaultState() {
		fliped = false;
		used = false;
		isEnergy = false;
		setUpsideDown(false);
		setPreferredSize(new Dimension(Constants.CARD_WIDTH, 87));
	}

	private void setDefaultTooltip() {
		setToolTipText(default_tooltip);
	}

//	private String createCardTooltip(Card card1) {
//		String htmlInfo = card1.createHtmlInfo();
//		return htmlInfo;
//	}
	
	public final boolean isUsed() {
		return this.used;
	}

	public final void setUsed(boolean used) {
		this.used = used;
	}

	// Copy card
	public Card getCard() {
		if (cards.size() > 0) {
			Card card2 = cards.lastElement();
			return card2;
		} else {
			return null;
		}
	}
	
	public boolean hasCard() {
		return cards.size() > 0;
	}
	// Cut/Move card
	public Card grCard() {

		if (cards.size() > 0) {
			Card card2 = cards.pop();
			if (cards.size() > 0) {
				// Stack has more than one card, set the top card of stack as stack's image.
				if (!cards.lastElement().fliped) {
					super.setIcon(ImageUtils.scale(cards.lastElement().getImCard(), Constants.CARD_WIDTH));
				} else {
					super.setIcon(ImageUtils.scale(cards.lastElement().bkCard, Constants.CARD_WIDTH));
				}
			} else {
				// No cards in stack
				//			this.setCard(ncrd);
				try {
					super.setIcon(
						ImageUtils.scale(
							new ImageIcon(ImageIO.read(getClass().getResourceAsStream(ncrdPath))), Constants.CARD_WIDTH
							));
				} catch(Exception e) {
					// What to do if the image is missing?
					System.err.println("Missing Resource!");
				}
				setUsed(false);
			}
			
			return card2;
		} else {
			return null; // NULL
		}
	}
	
	public void setCard(Card card0) {
		if (card0 != null) {
			cards.add(card0);
			setUsed(false);
			
			if (!card0.fliped) {
				super.setIcon(ImageUtils.scale(card0.getImCard(), Constants.CARD_WIDTH));
				//			if (card0.fliped) {
				//				this.flip();
				//			}
			} else {
				super.setIcon(ImageUtils.scale(card0.bkCard, Constants.CARD_WIDTH));
			}
			
			if (card0.name.equalsIgnoreCase("No Card")) {
				this.setToolTipText(default_tooltip);
			} else {
//				this.setToolTipText(createCardTooltip(card0));
						// FIXME Problem here ↓
						//card0.name + "," + Card.energyToString(card0.energy) + "," + Card.energyToString(card0.eno));
			} 
		}
	}
	
	public void showFullImage() {
		this.setIcon(this.getCard().getImCard());
	}
	
	public void showNormalImage() {
		this.setIcon(ImageUtils.scale(this.getCard().getImCard(), Constants.CARD_WIDTH));
	}
	
	public void flip() {
		Card card = this.getCard();
		if (card.bkCard != null) {
			if (card.fliped) {
				
				//Unflip
				this.setIcon(ImageUtils.scale(card.getImCard(), Constants.CARD_WIDTH));
//				this.setToolTipText(createCardTooltip(card));
				card.fliped = false;
			} else {
				
				//Flip
				this.setIcon(ImageUtils.scale(card.bkCard, Constants.CARD_WIDTH));
				setDefaultTooltip();
				card.fliped = true;
			}
		}
		
	}
	
	/** Highlight this label by creating a border around it in the given color */
	public void highlight(Color borderCol) {
		this.setBorder(BorderFactory.createLineBorder(borderCol, 3));
		repaint();
	}
	
	public void removeHighlight() {
		this.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		repaint();
	}
	
	public void use2() {
		setUsed(true);
		Card c = this.getCard();

		// TODO Needs to be rewritten using Constants class ↓
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
		this.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		repaint();
	}
	
	public void rot_90() {		
		ImageIcon img = ImageUtils.scale(this.getCard().getImCard(), Constants.CARD_WIDTH);
		ImageIcon rot_img = ImageUtils.rotate90(img);
		this.setIcon(rot_img);
		setUsed(true);
	}
	
	public void rot_180() {
		ImageIcon img = ImageUtils.scale(this.getCard().getImCard(), Constants.CARD_WIDTH);
		
		if (isUpsideDown()) {
			setUpsideDown(false);
		} else {
			setUpsideDown(true);
			img = ImageUtils.flipVert(img);
		}
	
		this.setIcon(img);
	}

	public void rot_270() {
		ImageIcon img = ImageUtils.scale(this.getCard().getImCard(), Constants.CARD_WIDTH);
		img = ImageUtils.rotate270(img);
		this.setIcon(img);
		setUsed(true);
	}
	
	public void rot_0() {
		setUsed(false);
		ImageIcon img = ImageUtils.scale(this.getCard().getImCard(), Constants.CARD_WIDTH);
		
		// Flipping the image if it was flipped before tapping.
		if (isUpsideDown()) {
			img = ImageUtils.flipVert(img);
		}
		
		this.setIcon(img);
	}
	
	public void setOrig() {
		this.setIcon(ImageUtils.scale(this.getCard().getImCard(), Constants.CARD_WIDTH));
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
		ncrdPath = isEnergy ? ncrdePath : ncrdnePath;
	}
}
