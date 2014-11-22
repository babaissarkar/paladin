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


import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Deque;

import javax.swing.JDesktopPane;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;

/**
 * @author babaissarkar
 *
 */
public class DeckViewer extends JDesktopPane {
	private static final long serialVersionUID = 1L;
	public JList jlsDeck;
	public Deque<Card> deck;
	private Container cdv;
	private Card[] cards;
	private String[] cardsnm;
	private Card selCard;
	private Infofield inf;
	public JButton btnShow;

 public DeckViewer(Deque<Card> stack) {
 	setLocation(new Point(700, 60));
 	setForeground(Color.BLACK);
	this.setSize(new Dimension(330, 226));
	deck = stack;
	cdv = this;
	cdv.setLayout(new BorderLayout());
	cards = new Card[deck.size()+1];
	deck.toArray(cards);
	cardsnm = new String[deck.size()+1];
	for (int i = 0; i < deck.size(); i++) {
		cardsnm[i] = cards[i].name;
	}
	jlsDeck = new JList(cardsnm);
	jlsDeck.setForeground(Color.BLACK);
	jlsDeck.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	JScrollPane jsl = new JScrollPane(jlsDeck,
			  ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	jsl.setBorder(new LineBorder(Color.BLUE, 2, true));
	cdv.add(jsl, BorderLayout.NORTH);
	
	JPanel panel = new JPanel();
	panel.setPreferredSize(new Dimension(5, 5));
	this.add(panel, BorderLayout.CENTER);
	panel.setLayout(new FlowLayout());
	
	btnShow = new JButton("Show");
	btnShow.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
	btnShow.setAlignmentX(Component.CENTER_ALIGNMENT);
	panel.add(btnShow, BorderLayout.SOUTH);
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
