package clz;

/*
 *      Card.java
 *
 *      Copyright 2014 babai sarkar <bsarkar@ssarkar-Inspiron>
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


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
//import java.util.Vector;

import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Card implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7028456289629115594L;
	
	//basic structure of a digital card
	public String name = " ", subtype = " ", type = " ", civilization = " ", id = " ";
	public StringBuffer effects = new StringBuffer(" ");
	public Integer cost = 0 , power = 0, manano = 1;
	public ImageIcon imCard;
	public ImageIcon bkCard;
	
	/*
	 * The state variable denotes the current
	 * state of the Battlefield. 0 means normal.
	 * Players can continue playing. 1 means conditon
	 * is abnormal for the oppponent. 2 means the
	 * condition is abnormal for the current
	 * player. 3 means abnormal for both players.
	 */
	//public static int state;
	//protected boolean attacks, attacked, blocks, blocked, tapped;
	//public static Vector<Card> vInterruptor = new Vector<Card>();
	
	public Card() {
		try {
			this.bkCard = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/Back.jpg")));
		} catch (IOException e) {
			this.bkCard = null;
		}
	}
	
	public Card(String name0, String path0) {
		try {
			this.bkCard = scale(new ImageIcon(
					ImageIO.read(getClass().getResourceAsStream("/images/Back.jpg"))));
		} catch (IOException e) {
			this.bkCard = null;
		}
		this.name = name0;
		try {
			this.imCard = new ImageIcon(ImageIO.read(getClass().getResourceAsStream(path0)));
		} catch (IOException e) {
			this.imCard = null;
		}
	}
	
	public Card(String name0, ImageIcon im) {
		try {
			this.bkCard = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/Back.jpg")));
		} catch (IOException e) {
			this.bkCard = null;
		}
		this.name = name0;
		this.imCard = im;
	}

	
	public Card(String name1, String race1, String type1, String civilization1,
			String cost1, String power1, String manano1, String id1, String effects, String path1) {
		try {
			this.bkCard = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/Back.jpg")));
		} catch (IOException e) {
			this.bkCard = null;
		}
		this.name = name1;
		this.subtype = race1;
		this.type = type1;
		this.civilization = civilization1;
		this.cost = Integer.parseInt(cost1);
		this.power = Integer.parseInt(power1);
		this.manano = Integer.parseInt(manano1);
		this.id = id1;
		try {
			this.imCard = new ImageIcon(ImageIO.read(getClass().getResourceAsStream(path1)));
		} catch (IOException e) {
			this.imCard = null;
		}
	}
	
	public Card(String name1, String race1, String type1, String civilization1,
			String cost1, String power1, String manano1, String id1, String effects) {
		try {
			this.bkCard = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/Back.jpg")));
		} catch (IOException e) {
			this.bkCard = null;
		}
		this.name = name1;
		this.subtype = race1;
		this.type = type1;
		this.civilization = civilization1;
		this.cost = Integer.parseInt(cost1);
		this.power = Integer.parseInt(power1);
		this.manano = Integer.parseInt(manano1);
		this.id = id1;
		this.effects = new StringBuffer(effects);
		this.imCard = null;
	}
	
	public static ImageIcon scale(Icon i) {
		BufferedImage bi = new BufferedImage(64, 87, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.createGraphics();
		g.drawImage(((ImageIcon) i).getImage(), 0, 0, 64, 87, 0, 0, i.getIconWidth(), i.getIconHeight(), null);
		g.dispose();
		return new ImageIcon(bi);
	}

	/*public static Card getInt(int idx) {
		return vInterruptor.elementAt(idx);
	}
	
	public void stateChange(int st) {
		state = st;
		vInterruptor.addElement(this);
	}*/

	public ImageIcon getImCard() {
		return this.imCard;
	}

	public void setImCard(ImageIcon imCard) {
		this.imCard = imCard;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.name + "\n");
		sb.append(this.civilization + "\n");
		sb.append(this.id + "\n");
		sb.append(this.type + "\n");
		sb.append(this.subtype + "\n");
		sb.append(this.cost + "\n");
		sb.append(this.power + "\n");
		sb.append(this.manano + "\n");
		return sb.toString();
	}
	
	
	

	/*
	 * For automatic resolving of abilities.
	 * 
	//Is called when the creature is put into the battle zone.
	public void init() {
	}
	
	//Is called when a card attacks.
	public abstract void attack();

	//Occurs when a card blocks.
	public abstract void block();

	//Occurs when a card taps (i. e. due to a tap ability).
	public abstract void tap();

	public boolean isTapped() {
		return tapped;
	}

	public boolean isAttacking() {
		return attacks;
	}

	public boolean isBlocking() {
		return blocks;
	}*/

}

