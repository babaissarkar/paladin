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

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.util.Deque;

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
private JLabel[] jlb = new JLabel[11];
private JTextField[] jtf = new JTextField[11];
private Container cntpane, cntpane2, cntpane3;
private CLabel label;
private JTextArea jta1;
private JScrollPane jscroll, wscrl;
private JMenuBar jmb;
private JMenu jmFile, jmDeck;
private JMenuItem jmiExit, jmiOpen, jmiCreateDeck, jmiSaveDeck, jmiAddCard, jmiAppend, jmiExport;
private JFileChooser jfc;
private Card card;
private Deque<Card> deck = null;
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
	cntpane = new Container();
	cntpane.setLayout(new GridLayout(5, 4));
	cntpane2 = new Container();
	cntpane2.setLayout(new GridLayout(0, 1));
	cntpane3 = new Container();
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
	jmiExport = new JMenuItem("Export As Text Document...");
	jmiExport.addActionListener(this);
	jmFile = new JMenu("File");
	jmFile.add(jmiOpen);
	jmFile.add(jmiExport);
	jmFile.add(jmiExit);
	jmiCreateDeck = new JMenuItem("Create A New Deck");
	jmiCreateDeck.addActionListener(this);
	jmiSaveDeck = new JMenuItem("Save This Deck");
	jmiSaveDeck.addActionListener(this);
	jmiSaveDeck.setEnabled(false);
	jmiAddCard = new JMenuItem("Add Card To Deck");
	jmiAddCard.addActionListener(this);
	jmiAddCard.setEnabled(false);
	jmiAppend = new JMenuItem("Append To Existing Deck");
	jmiAppend.addActionListener(this);
	jmDeck = new JMenu("Deck");
	jmDeck.add(jmiCreateDeck);
	jmDeck.add(jmiAppend);
	jmDeck.add(jmiSaveDeck);
	jmDeck.add(jmiAddCard);
	jmb = new JMenuBar();
	jmb.add(jmFile);
	jmb.add(jmDeck);
	
	//Setting the properties of this JFrame
	this.setJMenuBar(jmb);
	this.setSize(900, 500);
	this.setLocation(80, 20);
	this.setTitle("Card Info");
	this.setIconImage(Toolkit.getDefaultToolkit().getImage(Infofield.class.getResource("/images/INF.png")));
	this.setAlwaysOnTop(true);
}

	public Infofield(Deque<Card> deck) {
		this();
		this.deck = deck;
		prepareDeck();
	}

	public void createInfofield() {
		//Creating the different components
		jlb[1] = new JLabel("Name");
		jtf[1] = new JTextField(20);
		jlb[2] = new JLabel("Civility");
		jtf[2] = new JTextField(20);
		jlb[3] = new JLabel("Type");
		jtf[3] = new JTextField(20);
		jlb[4] = new JLabel("Energy");
		jtf[4] = new JTextField(20);
		jtf[4].setText("↑0↓0~0(0)");
		jlb[5] = new JLabel("Subtype");
		jtf[5] = new JTextField(20);
		jlb[6] = new JLabel("Effects");
		jta1 = new JTextArea(10, 20);
		jscroll = new JScrollPane(jta1,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jlb[7] = new JLabel("Power");
		jtf[7] = new JTextField(20);
		jtf[7].setText("0");
		jlb[8] = new JLabel("Energy No.");
		jtf[8] = new JTextField(20);
		jtf[8].setText("↑0↓0~0");
		jlb[9] = new JLabel("Damage Points");
		jtf[9] = new JTextField(5);
		jtf[9].setText("0");
		jlb[10] = new JLabel("ID");
		jtf[10] = new JTextField(10);
		label = new CLabel(new Card("No Card2", "/images/NCRD.jpg"));
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
	for (int i = 7; i <= 10; i++) {
		cntpane.add(jlb[i]);
		cntpane.add(jtf[i]);
	}
		cntpane2.add(wscrl);
		tpane.addTab("Information", cntpane);
		tpane.addTab("Card Viewer", cntpane2);
		cntpane3.add(tpane);
	}
	
	public void setCard(Card card) {
		if (!(card == null)) {
			this.card = card;
			jtf[1].setText(card.name);
			jtf[2].setText(card.civility); //Changed
			jtf[3].setText(card.type);
			jtf[4].setText(Card.energyToString(card.energy));
			jtf[5].setText(card.subtype);
			jtf[7].setText(card.power.toString());
			jtf[8].setText(Card.energyToString(card.eno));
			jtf[9].setText(card.damage.toString());
			jtf[10].setText(card.id);
			jta1.setText("");
			for (String effect : card.effects) {
				jta1.append(effect);
				jta1.append("\n");
			}
			label.setCard(card);
			label.showFullImage();
		}
	}
	
	public Card getCard() {
		return this.card;
	}

	public final Deque<Card> getDeck() {
		return this.deck;
	}
	
	public final void setDeck(Deque<Card> deck) {
		this.deck = deck;
		prepareDeck();
	}

	public final void prepareDeck() {
		if (this.deck != null) {
			dv = new DeckViewer(this.deck);
			dv.btnShow.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (deck != null) {
						setCard(dv.getCard());
					}
				}
			});
			dv.btnRemove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (deck != null) {
						deck.remove(dv.getCard());
							dv.lblNoOfCards.setText("No of cards in this deck is :" + dv.noOfCards); //Check
							prepareDeck();
						}
				}
			});
			dv.btnSort.addActionListener(this);
			this.getContentPane().remove(tpane);
			if (tpane.getTabCount() == 3) {
				tpane.removeTabAt(2);
			}
			tpane.addTab("Deck Viewer", dv);
			this.getContentPane().add(tpane);
			try {
				this.setCard(deck.getLast());
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
			int stat = jfc.showOpenDialog(this);
			if (stat == JFileChooser.APPROVE_OPTION) {
				File f = jfc.getSelectedFile();
				if (f.getName().endsWith(".txt")) {
					deck = new CScan().scanAllAtributes(jfc.getSelectedFile()
							.getAbsolutePath());
				} else if (f.getName().endsWith(".zip")) {
					deck = new CScan().scanZipFile(jfc.getSelectedFile()
							.getAbsolutePath());
				}
				prepareDeck();
			}
		} else if (ae.getSource() == jmiCreateDeck) {
			this.jmiAddCard.setEnabled(true);
			this.jmiSaveDeck.setEnabled(true);
			this.jmiCreateDeck.setEnabled(false);
			this.jmiAppend.setEnabled(false);
			if (deck != null) {
				deck.removeAll(deck);
				prepareDeck();
			} else {
				deck = new ArrayDeque<Card>();
				prepareDeck();
			}
		} else if (ae.getSource() == jmiAppend) {
			if (deck != null) {
				this.jmiAddCard.setEnabled(true);
				this.jmiSaveDeck.setEnabled(true);
				this.jmiCreateDeck.setEnabled(false);
				this.jmiAppend.setEnabled(false);
			}
		} else if (ae.getSource() == jmiAddCard) {
			Card card = new Card(jtf[1].getText(), jtf[3].getText(), jtf[5].getText(),
					jtf[2].getText(), jtf[4].getText(), jtf[7].getText(),
					jtf[8].getText(), jtf[10].getText(), jta1.getText(), jtf[9].getText());
			deck.add(card);
			dv.lblNoOfCards.setText("No of cards in this deck is :" + this.dv.noOfCards); //Check
			prepareDeck();
		} else if (ae.getSource() == jmiSaveDeck) {
			int stat = jfc.showSaveDialog(this);
			if (stat == JFileChooser.APPROVE_OPTION) {
				File f = jfc.getSelectedFile();
				try {
					if (!f.exists()) {
						f.createNewFile();
					}
				PrintWriter write = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f)));
				for (Card c : deck) {
					write.println(c.writeInfo());
				}
				write.close();
				JOptionPane.showMessageDialog(this, "Deck Sucessfully Saved!", "",
						JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
				}
			}
			jmiSaveDeck.setEnabled(false);
			jmiAddCard.setEnabled(false);
			jmiCreateDeck.setEnabled(true);
			jmiAppend.setEnabled(true);
		} else if (ae.getSource() == jmiExport) {
			int stat = jfc.showSaveDialog(this);
			if (stat == JFileChooser.APPROVE_OPTION) {
				File f = jfc.getSelectedFile();
				try {
					if (!f.exists()) {
						f.createNewFile();
					}
				PrintWriter write = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f)));
				write.println("Card Database");
				write.println("-------------------------------------------");
				for (Card c : deck) {
					write.println(c.toString());
					write.println("-------------------------------------------");
				}
				write.println("-------------------------------------------");
				write.close();
				JOptionPane.showMessageDialog(this, "Deck Sucessfully Exported!", "",
						JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
				}
			}
		}
	}
}

