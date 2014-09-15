package clz;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JList;

/**
 * @author babaissarkar
 *
 */
public class DeckViewer extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JList jlsDeck;
	private Stack<Card> deck;
	private Container cdv;
	private Card[] cards;
	private String[] cardsnm;

 public DeckViewer(Stack<Card> stack) {
	this.setSize(new Dimension(200, 200));
	this.setTitle("Deck Viewer");
	deck = stack;
	cdv = this.getContentPane();
	cdv.setLayout(new GridLayout(0, 1));
	cards = new Card[deck.size()+1];
	deck.toArray(cards);
	cardsnm = new String[deck.size()+1];
	for (int i = 0; i < deck.size(); i++) {
		cardsnm[i] = cards[i].name;
	}
	jlsDeck = new JList(cardsnm);
	cdv.add(jlsDeck);
}

@Override
public void actionPerformed(ActionEvent arg0) {
}

public void showDeck() {
	this.setVisible(true);
}
}