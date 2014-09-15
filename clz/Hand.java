package clz;
/*
 * Hand.java
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


import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


	@SuppressWarnings("serial")
	public class Hand extends JFrame {
		public int NoOfCards, cardNo;
		public CLabel[] jlbCard = new CLabel[99];
		public JPanel jplM;
		//public JLabel jlbCard;
		public Vector<String> Cards = new Vector<String>();
		public Container cntHand = getContentPane();
		public GridBagConstraints gb = new GridBagConstraints();
		private GroupLayout gx;
		public ImageIcon imNoCard, cardImage;
		private int j;
		
		public Hand(String title, CardListener c) {
			j = 0;
			cardNo = -1;
			NoOfCards = -1;
			//Creating Group Layouts
			//Creating ImageIcon
			try {
				imNoCard = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/NCRD.jpg")));
				cardImage = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/Deck.jpg")));
			} catch (IOException e) {
				imNoCard = null;
				e.printStackTrace();
			}
			//Creating JLabel
			for (int i = 0; i < 10; i++) {
			jlbCard[i+1] = new CLabel(imNoCard);
			jlbCard[i+1].addMouseListener(c);
			}
			
			//GroupLayout
			jplM = new JPanel();
			gx = new GroupLayout(jplM);
			jplM.setLayout(gx);
			gx.setAutoCreateGaps(true);
			gx.setAutoCreateContainerGaps(true);
			GroupLayout.SequentialGroup phGroup = gx.createSequentialGroup();
			GroupLayout.SequentialGroup pvGroup = gx.createSequentialGroup();
			
			pvGroup.addGroup(gx.createParallelGroup()
					.addComponent(jlbCard[1])
					.addComponent(jlbCard[2])
					.addComponent(jlbCard[3])
					.addComponent(jlbCard[4])
					.addComponent(jlbCard[5])
					);
			pvGroup.addGroup(gx.createParallelGroup()
					.addComponent(jlbCard[6])
					.addComponent(jlbCard[7])
					.addComponent(jlbCard[8])
					.addComponent(jlbCard[9])
					.addComponent(jlbCard[10])
					);
			gx.setVerticalGroup(pvGroup);
			
			
			phGroup.addGroup(gx.createParallelGroup().addComponent(jlbCard[1]).addComponent(jlbCard[6]));
			phGroup.addGroup(gx.createParallelGroup().addComponent(jlbCard[2]).addComponent(jlbCard[7]));
			phGroup.addGroup(gx.createParallelGroup().addComponent(jlbCard[3]).addComponent(jlbCard[8]));
			phGroup.addGroup(gx.createParallelGroup().addComponent(jlbCard[4]).addComponent(jlbCard[9]));
			phGroup.addGroup(gx.createParallelGroup().addComponent(jlbCard[5]).addComponent(jlbCard[10]));
			gx.setHorizontalGroup(phGroup);
			
			//Finalizing
			this.setTitle(title);
			this.setSize(new Dimension(320, 180));
			this.add(jplM);
			//showHand();
		}
		
		public void showHand() {
			this.setVisible(true);
		}
		
		public void addCard(Card cardname) {
			jlbCard[j+1].setCard(cardname);
			if (j < 5) {
				j++;
			}
		}

	
	//public static void main(String[] args) {
		//// TODO Auto-generated method stub
		//new Hand("My Hand", cl2);
	//}
	

}
