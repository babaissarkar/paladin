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


import java.io.IOException;
//import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Card {
	//basic structure of a digital card
	public String name, race, type, civilization, effects;
	public String cost, power, manano, id;
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
			this.bkCard = CLabel.scale(new ImageIcon(
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
		this.race = race1;
		this.type = type1;
		this.civilization = civilization1;
		this.cost = cost1;
		this.power = power1;
		this.manano = manano1;
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
		this.race = race1;
		this.type = type1;
		this.civilization = civilization1;
		this.cost = cost1;
		this.power = power1;
		this.manano = manano1;
		this.id = id1;
		this.effects = effects;
		this.imCard = null;
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

