package clz;

/*
 *      Infofield.java
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

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;


public class Infofield extends JFrame implements ActionListener {

private static final long serialVersionUID = 6555470512771760138L;

public JFrame win = new JFrame("Card Image");
private JLabel[] jlb = new JLabel[10];
private JTextField[] jtf = new JTextField[10];
private JDesktopPane cntpane, cntpane2, cntpane3;
private CLabel label;
private JTextArea jta1;
private JButton b, b2;
private JScrollPane jscroll, wscrl;
private JMenuBar jmb;
private JMenu jmFile;
private JMenuItem jmiExit, jmiOpen;
private JFileChooser jfc;
private Card card;
private int deckid = 0;
private Stack<Card> deck;
private DeckViewer dv;
private JTabbedPane tpane;

/**
 * @author babaissarkar
 *
 */
public Infofield() {
	//Initializing
	jfc = new JFileChooser();
	tpane = new JTabbedPane();
	cntpane = new JDesktopPane();
	cntpane.setLayout(new GridLayout(5, 4));
	cntpane2 = new JDesktopPane();
	cntpane2.setLayout(new GridLayout(0, 1));
	cntpane3 = new JDesktopPane();
	cntpane3.setLayout(new GridLayout(0, 1));
	this.setContentPane(cntpane3);
	createInfofield();

	//Setting the menus
	jmiExit = new JMenuItem("Exit");
	jmiExit.addActionListener(this);
	jmiExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
	jmiOpen = new JMenuItem("Open");
	jmiOpen.addActionListener(this);
	jmiOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
	jmFile = new JMenu("File");
	jmFile.add(jmiOpen);
	jmFile.add(jmiExit);
	jmb = new JMenuBar();
	jmb.add(jmFile);
	
	//Setting the properties of this JFrame
	this.setJMenuBar(jmb);
	this.setSize(600, 400);
	this.setLocation(80, 20);
	this.setTitle("Card Info");
	this.setIconImage(Toolkit.getDefaultToolkit().getImage(Infofield.class.getResource("/images/INF.png")));
	this.setAlwaysOnTop(true);
}

	public Infofield(Stack<Card> deck) {
		this();
		this.deck = deck;
//		dv = new DeckViewer(this.deck);
//		dv.btnShow.addActionListener(this);
//		this.getContentPane().remove(tpane);
//		tpane.addTab("Deck Viewer", dv);
//		this.getContentPane().add(tpane);
		prepareDeck();
	}

	public void createInfofield() {
		//Creating the different components
		b = new JButton("Previous Card");
		b.addActionListener(this);
		b2 = new JButton("Next Card");
		b2.addActionListener(this);
		jlb[1] = new JLabel("Name");
		jtf[1]= new JTextField(20);
		jlb[2] = new JLabel("Civilization");
		jtf[2] = new JTextField(20);
		jlb[3] = new JLabel("Type");
		jtf[3] = new JTextField(20);
		jlb[4] = new JLabel("Cost");
		jtf[4] = new JTextField(20);
		jlb[5] = new JLabel("Race");
		jtf[5] = new JTextField(20);
		jlb[6] = new JLabel("Effects");
		jta1 = new JTextArea(10, 20);
		jscroll = new JScrollPane(jta1,
	  ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
	  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jlb[7] = new JLabel("Power");
		jtf[7] = new JTextField(20);
		jlb[8] = new JLabel("Mana No.");
		jtf[8] = new JTextField(20);
		label = new CLabel(new Card("No Card", "/images/NCRD.jpg"));
		label.addMouseListener(Battlefield.cl);
		wscrl = new JScrollPane(label,
				  ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//Adding the components to corresponding JDesktopPanes
	for (int i = 1; i <= 5; i++) {
		cntpane.add(jlb[i]);
		cntpane.add(jtf[i]);
	}
		cntpane.add(jlb[6]);
		cntpane.add(jscroll);
		cntpane.add(jlb[7]);
		cntpane.add(jtf[7]);
		cntpane.add(jlb[8]);
		cntpane.add(jtf[8]);
		cntpane.add(b);
		cntpane.add(b2);
		cntpane2.add(wscrl);
		tpane.addTab("Infofield", cntpane);
		tpane.addTab("Card Viewer", cntpane2);
		cntpane3.add(tpane);
	}
	
	public void setCard(Card card) {
		if (!(card == null)) {
			this.card = card;
			jtf[1].setText(card.name);
			jtf[2].setText(card.civilization);
			jtf[3].setText(card.type);
			jtf[4].setText(card.cost);
			jtf[5].setText(card.race);
			jtf[7].setText(card.power);
			jtf[8].setText(card.manano);
			jta1.setText(card.effects);
			label.setCard(card);
			label.showFullImage();
		}
	}
	
	public Card getCard() {
		return this.card;
	}
	
	public void addCard() {
		deck.add(new Card(
			jtf[1].getText(),
			jtf[5].getText(),
			jtf[3].getText(),
			jtf[2].getText(),
			jtf[4].getText(),
			jtf[7].getText(),
			jtf[8].getText(),
			"0",
			jta1.getText()));
	}

	public final Stack<Card> getDeck() {
		return this.deck;
	}
	
	public final void setDeck(Stack<Card> deck) {
		this.deck = deck;
		prepareDeck();
	}

	public final void prepareDeck() {
		if (this.deck != null) {
			dv = new DeckViewer(this.deck);
			dv.btnShow.addActionListener(this);
			this.getContentPane().remove(tpane);
			tpane.addTab("Deck Viewer", dv);
			this.getContentPane().add(tpane);
			try {
				this.setCard(deck.elementAt(deck.size() - 1));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Deck Error.", "Sorry!",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public static void main(String[] args) {
		Infofield i = new Infofield();
		i.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == jmiExit) {
			this.setVisible(false);
		} else if(ae.getSource() == jmiOpen) {
			jfc.showOpenDialog(this);
			deck = new CScan().scan(jfc.getSelectedFile());
			prepareDeck();
		} else if (ae.getSource() == b) {
			deckid--;
			try {
			this.setCard(deck.elementAt(deckid));
			} catch (ArrayIndexOutOfBoundsException e) {
			}
		} else if (ae.getSource() == b2) {
			deckid++;
			try {
				this.setCard(deck.elementAt(deckid));
			} catch (ArrayIndexOutOfBoundsException e) {
			}
		} else if (ae.getSource() == dv.btnShow) {
				this.setCard(dv.getCard());
		}
	}
}

