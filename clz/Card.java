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

package clz;

import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Card {
	
	//basic structure of a digital card
	public String name = " ", subtype = " ", type = " ", civility = " ", id = " ";
	public String[] effects = new String[20];
	public Integer[] energy = {0, 0, 0, 0} , eno = {1, 0 , 0};
	public Integer power = 0, damage = 0;
	public final static String[] SYMBOLS = {"\u2191", "\u2193", "~"};
	public char armourPowType = ' ';
	public ImageIcon imCard;
	public ImageIcon bkCard;
	public boolean generic = false;
	
	/*
	 * The state variable denotes the current
	 * state of the PRPlayer. 0 means normal.
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
	
	public Card(String name1, String type1, String subtype1, String civility1,
			String eng1, String power1, String eno1, String id1, String effects, String damage) {
		try {
			this.bkCard = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/Back.jpg")));
		} catch (IOException e) {
			this.bkCard = null;
		}
		this.name = name1;
		this.subtype = subtype1;
		this.type = type1;
		this.civility = civility1;
		this.energy[0] = Integer.parseInt(eng1.substring(1, 2));
		this.energy[1] = Integer.parseInt(eng1.substring(3, 4));
		this.energy[2] = Integer.parseInt(eng1.substring(5, 6));
		if (eng1.endsWith(")")) {
			this.generic = true;
			this.energy[3] = Integer.parseInt(eng1.substring(eng1.length() - 2, eng1.length() - 1));
		}
		this.power = Integer.parseInt(power1);
		this.eno[0] = Integer.parseInt(eno1.substring(1, 2));
		this.eno[1] = Integer.parseInt(eno1.substring(3, 4));
		this.eno[2] = Integer.parseInt(eno1.substring(5, 6));
		this.id = id1;
		this.effects = effects.split("\n");
		this.damage = Integer.parseInt(damage);
		this.imCard = new ImageIcon(ImageManipulator.createImage(this));
	}
	public ImageIcon getImCard() {
		return this.imCard;
	}

	public void setImCard(ImageIcon imCard) {
		this.imCard = imCard;
	}
	
	public static String energyToString(Integer[] engs) {
		String convEngString;
		StringBuffer sbEngString = new StringBuffer();
		sbEngString.append(SYMBOLS[0] + engs[0]);
		sbEngString.append(SYMBOLS[1] + engs[1]);
		sbEngString.append(SYMBOLS[2] + engs[2]);
		if (engs.length > 3) {
			sbEngString.append("(" + engs[3] + ")");
		}
		convEngString = sbEngString.toString();
		return convEngString;
	}
	
	public static Integer[] stringToEnergy(String engs) {
		Integer[] arEngs = new Integer[3];
		char[] chrEngs = engs.toUpperCase().toCharArray();
		for (int i = 0; i < chrEngs.length; i++) {
			if (chrEngs[i] == 'F') {
				arEngs[0] = Integer.parseInt(new Character(chrEngs[i++]).toString());
			} else if (chrEngs[i] == 'R') {
				arEngs[1] = Integer.parseInt(new Character(chrEngs[i++]).toString());
			} else if (chrEngs[i] == 'Q') {
				arEngs[2] = Integer.parseInt(new Character(chrEngs[i++]).toString());
			} else if (chrEngs[i] == '(') {
				arEngs[3] = Integer.parseInt(new Character(chrEngs[i++]).toString());
			}
		}
		return arEngs;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Name : " + this.name + "\n");
		sb.append("Civility : " + this.civility + "\n");
		sb.append("Id : " + this.id + "\n");
		sb.append("Type : " + this.type + "\n");
		sb.append("Subtype : " + this.subtype + "\n");
		sb.append("Energy required :" + energyToString(this.energy) + "\n");
		if (this.type.equalsIgnoreCase("Armour")) {
			sb.append("Power : ");
			sb.append(armourPowType);
		} else {
			sb.append("Power bonus : ");
		}
		sb.append(this.power + "\n");
		sb.append("Energy number : " + energyToString(this.eno) + "\n");
		sb.append("Effects :\n");
		for (String effect : effects) {
			if (effect != null) {
				sb.append(effect + "\n");
			}
		}
		sb.append("Damage points : " + this.damage + "\n");
		return sb.toString();
	}
	
	public Vector<String> getData() {
		Vector<String> data = new Vector<String>();
		data.add(id);
		data.add(name);
		data.add(civility);
		data.add(type);
		data.add(subtype);
		data.add(Card.energyToString(energy));
		data.add(power.toString());
		data.add(Card.energyToString(eno));
		for (String effect : effects) {
			if (effect != null) {
				data.add(effect);
			}
		}
		return data;
	}
	
	public String writeInfo() {
		StringBuilder sbCardInfo = new StringBuilder();
		sbCardInfo.append(this.name + ";\n");
		sbCardInfo.append(this.type + ";\n");
		sbCardInfo.append(this.subtype + ";\n");
		sbCardInfo.append(this.civility + ";\n");
		sbCardInfo.append(energyToString(this.energy) + ";\n");
		sbCardInfo.append(power.toString() + ";\n");
		sbCardInfo.append(energyToString(this.eno) + ";\n");
		sbCardInfo.append(this.id + ";\n");
		for (String effect : effects) {
			if (effect != null) {
				sbCardInfo.append(effect + "\n");
			}
		}
		sbCardInfo.append(";\n");
		sbCardInfo.append(this.damage + ";");
		return sbCardInfo.toString();
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

