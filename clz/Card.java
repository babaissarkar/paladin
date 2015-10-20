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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Card {
	
	//basic structure of a digital card
	public String name = " ", subtype = " ", type = " ", civility = " ", id = " ";
	public String[] effects = new String[20];
	public Integer[] cost = {0, 0, 0} , eno = {1, 0 , 0};
	public Integer power = 0, dpbonus = 0;
	public final static String[] SYMBOLS = {"\u2191", "\u2193", "~"};
	public char armourPowType = ' ', enoType = 'F';
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
			this.bkCard = ImageManipulator.scale(new ImageIcon(
					ImageIO.read(getClass().getResourceAsStream("/images/Back.jpg"))), 64, 87);
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
	
	public Card(String name1, String subtype1, String type1, String civility1,
			String eng1, String power1, String eno1, String id1, String effects) {
		try {
			this.bkCard = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/Back.jpg")));
		} catch (IOException e) {
			this.bkCard = null;
		}
		this.name = name1;
		this.subtype = subtype1;
		this.type = type1;
		this.civility = civility1;
		this.cost[0] = Integer.parseInt(eng1.substring(1, 2));
		this.cost[1] = Integer.parseInt(eng1.substring(3, 4));
		this.cost[2] = Integer.parseInt(eng1.substring(5, 6));
		this.power = Integer.parseInt(power1);
		this.eno[0] = Integer.parseInt(eno1.substring(1, 2));
		this.eno[1] = Integer.parseInt(eno1.substring(3, 4));
		this.eno[2] = Integer.parseInt(eno1.substring(5, 6));
		this.id = id1;
		this.effects = effects.split("\n");
		this.imCard = new ImageIcon(ImageManipulator.createImage(this));
	}
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
		sb.append(this.civility + "\n");
		sb.append(this.id + "\n");
		sb.append(this.type + "\n");
		sb.append(this.subtype + "\n");
		sb.append(SYMBOLS[0] + this.cost[0]);
		sb.append(SYMBOLS[1] + this.cost[1]);
		sb.append(SYMBOLS[2] + this.cost[2] + "\n");
		if (this.type.equalsIgnoreCase("Armour")) {
			sb.append(armourPowType);
		}
		sb.append(this.power + "\n");
		sb.append(SYMBOLS[0] + this.eno[0]);
		sb.append(SYMBOLS[1] + this.eno[1]);
		sb.append(SYMBOLS[2] + this.eno[2] + "\n");
		for (String effect : effects) {
			if (effect != null) {
				sb.append(effect + "\n");
			}
		}
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

