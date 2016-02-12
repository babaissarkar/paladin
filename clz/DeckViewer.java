package clz;

/*
 * DeckViewer.java
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


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Deque;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.ListSelectionModel;

import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author babaissarkar
 *
 */
public class DeckViewer extends JPanel {
	private static final long serialVersionUID = 1L;
	public int noOfCards = 0;
	public JList<String> jlsDeck;
	public Deque<Card> deck;
	private Card[] cards;
	private String[] cardsnm;
	private Card selCard;
	private Infofield inf;
	public JButton btnShow, btnRemove, btnSort;
	public JLabel lblNoOfCards;

 public DeckViewer(Deque<Card> stack) {
 	setLocation(new Point(700, 60));
 	setForeground(Color.BLACK);
	this.setSize(new Dimension(330, 226));
	deck = stack;
	GridBagConstraints gbc = new GridBagConstraints();
	this.setLayout(new GridBagLayout());
	noOfCards = deck.size();
	cards = new Card[noOfCards];
	deck.toArray(cards);
	cardsnm = new String[noOfCards];
	initializeDeck();
	jlsDeck.setForeground(Color.BLACK);
	jlsDeck.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	JScrollPane jsl = new JScrollPane(jlsDeck,
			  ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	jsl.setBorder(new LineBorder(Color.BLUE, 2, true));
	
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridheight = 5;
	gbc.gridwidth = 4;
	jsl.setPreferredSize(new Dimension(500, 250));
	this.add(jsl, gbc);
	
	JPanel panel = new JPanel();
	panel.setPreferredSize(new Dimension(5, 5));
	gbc.gridx = 0;
	gbc.gridy = 6;
	gbc.gridheight = 2;
	panel.setPreferredSize(new Dimension(500, 50));
	panel.setLayout(new FlowLayout());
	
	btnShow = new JButton("Show");
	btnRemove = new JButton("Remove");
	btnSort = new JButton("Sort");
	panel.add(btnShow);
	panel.add(btnSort);
	panel.add(btnRemove);
	this.add(panel, gbc);
	
	lblNoOfCards = new JLabel("No of cards in this deck is :" + noOfCards);
	gbc.gridx = 0;
	gbc.gridy = 8;
	gbc.gridheight = 2;
	this.add(lblNoOfCards, gbc);
}

public void initializeDeck() {
	for (int i = 0; i < deck.size(); i++) {
		cardsnm[i] = cards[i].name;
	}
	jlsDeck = new JList<String>();
	DefaultListModel<String> lm;
	lm = new DefaultListModel<String>();
	for (int i = 0; i < cardsnm.length; i++) {
		lm.addElement(cardsnm[i]);
	}
	jlsDeck.setModel(lm);
}

public void showDeck() {
	this.setVisible(true);
}

public Card getSelCard() {
	return (Card) selCard;
}

public void setSelCard(Card selCard) {
	this.selCard = selCard;
	inf.setCard(selCard);
}

public Card getCard() {
	int index = jlsDeck.getSelectedIndex();
	return cards[index];
}

}
