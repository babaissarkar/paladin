package clz.xml;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import clz.CScan;
import clz.Card;
import clz.Deck;
import clz.ImageManipulator;

public class DeckEditor extends MouseAdapter implements ActionListener {
	Deck deck = new Deck();
	Deck deckLeft = new Deck();
	Deck deckRight = new Deck();
	private CScan scan;
	private JFrame frmInfo = null;
	private JList<String> jlsDeck;
	private JPanel cpane3;
	private JLabel[] jlb = new JLabel[10];
	private JTextField[] jtf = new JTextField[10];
	private JTextArea jtaEffects;
	private JComboBox<String> jcbCivility;
	private JLabel card_image;
	
	private JMenu mnuDeck;
	private JMenuItem jmiNewDeck, jmiAddCard, jmiDelCard, jmiUpdateCard, jmiSave, jmiOpen;
	
	private final String[] attr_names = {"Name", "Element", "Type", "Energy", "Subtype", "Effects", "Power", "Energy No.", "Damage Points", "ID"};
	private String[] civilities;
	private JCheckBox jcbIsExtra;

	private boolean isExtra;
	private JMenuItem jmiAddMult;

	public DeckEditor() {
		isExtra = false;

		this.scan = new CScan();
		this.frmInfo = createGUI();
		this.frmInfo.setVisible(true);
	}
	
	private JFrame createGUI() {
		JFrame frmMain = new JFrame("Deck Editor");
		frmMain.setSize(900, 750);
		frmMain.setLocation(40, 20);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Layouting
		JPanel mpane = new JPanel();
		mpane.setLayout(new FlowLayout());
		
		JPanel cpane = new JPanel();
		cpane.setLayout(new BoxLayout(cpane, BoxLayout.PAGE_AXIS));
		Border b1 = BorderFactory.createLineBorder(Color.DARK_GRAY, 3);
		Border b2 = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border mainBorder = BorderFactory.createCompoundBorder(
								BorderFactory.createCompoundBorder(b2, b1), b2);
		cpane.setPreferredSize(new Dimension(500,500));
		cpane.setBorder(mainBorder);
		
		JPanel cpane2 = new JPanel();
		cpane2.setBorder(mainBorder);
		card_image = new JLabel("Click here to add card image", SwingConstants.CENTER);
		card_image.addMouseListener(this);
		//card_image.setPreferredSize(new Dimension(300,400));
		JScrollPane jslImg = new JScrollPane(card_image,
				  ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jslImg.setPreferredSize(new Dimension(340,400));
		
		
		cpane3 = new JPanel();
		jlsDeck = new JList<String>();
		jlsDeck.setForeground(Color.BLACK);
		JScrollPane jsl = new JScrollPane(jlsDeck,
				  ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsl.setPreferredSize(new Dimension(300,340));
		
		jlsDeck.addListSelectionListener(
			e -> {
				if (deck.size() > 0) {
					int index = jlsDeck.getSelectedIndex();
					if (index != -1) {
						Card c;
						if (index < deck.size()) {
							Card[] cards = new Card[deck.size()];
							deck.toArray(cards);
							c = cards[index];
//							System.out.println("Main deck card # : " + index);
							setCard(c, false);
						} else {
							int extraIndex = index - deck.size();
							List<Card> extras = deck.getExtraDeck();
							if (extras != null) {
								Card[] cards = new Card[extras.size()];
								extras.toArray(cards);
								c = cards[extraIndex];
//							System.out.println("Extra deck card # : " + extraIndex);
								setCard(c, true);
							}
						}
					}
				}
			}
		);
		
		cpane3.add(jsl);
		cpane3.setPreferredSize(new Dimension(380,400));
		cpane3.setBorder(mainBorder);
		
		// Attributes
		JPanel[] panels = new JPanel[10];
		
		jcbCivility = new JComboBox<String>();
		if (civilities != null) {
			for (String c : civilities) {
				jcbCivility.addItem(c);
			} 
		}
		jcbCivility.setEditable(true);
		
		jtaEffects = new JTextArea(5, 35);
		JScrollPane jscroll = new JScrollPane(jtaEffects,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//jscroll.setPreferredSize(new Dimension(400, 100));

		jcbIsExtra = new JCheckBox("Extra Card?");
		jcbIsExtra.addActionListener(
				evt -> {
					if (this.isExtra == false) {
						JOptionPane.showMessageDialog(frmInfo, "Marked as Extra!");
						this.isExtra = true;
					} else {
						JOptionPane.showMessageDialog(frmInfo, "Marked as Normal.");
						this.isExtra = false;
					}
				}
		);

		// Adding to content pane
		for (int i = 0; i < jlb.length; i++) {
			jlb[i] = new JLabel(attr_names[i] + " : ");
		}
		
		for (int i = 0; i < jtf.length; i++) {
			if (i < 6) {
				jtf[i] = new JTextField(30);
			} else {
				jtf[i] = new JTextField(20);
			}
		}
		
		for (int i = 0; i < panels.length; i++) {
			panels[i] = new JPanel();
			FlowLayout layout = new FlowLayout();
			layout.setAlignment(FlowLayout.LEFT);
			panels[i].setLayout(layout);
			panels[i].add(jlb[i]);
			if ((i != 1) && (i != 5)) {
				panels[i].add(jtf[i]);
			} else if (i == 5) {
				panels[i].add(jscroll);
			} else {
				panels[i].add(jcbCivility);
			}
			
			cpane.add(panels[i]);
		}

		cpane.add(jcbIsExtra);
		
		jtf[3].setText("↑0↓0~0(0)");
		jtf[6].setText("0");
		jtf[7].setText("↑0↓0~0");
		jtf[8].setText("0");
		
		cpane2.add(jslImg);
		
		mnuDeck = new JMenu("Deck");
		jmiNewDeck = new JMenuItem("New Deck");
		jmiOpen = new JMenuItem("Open Deck");
		jmiAddCard = new JMenuItem("Add Card");
		jmiAddMult = new JMenuItem("Add Multiple Copies");
		jmiUpdateCard = new JMenuItem("Update Card");
		jmiDelCard = new JMenuItem("Remove Card");
		jmiSave = new JMenuItem("Save Deck");
		
		JMenuItem jmiQuit = new JMenuItem("Exit");
		jmiQuit.addActionListener(
			evt -> { System.exit(0); }
		);
		
		jmiNewDeck.addActionListener(this);
		jmiOpen.addActionListener(this);
		jmiAddCard.addActionListener(this);
		jmiAddMult.addActionListener(this);
		jmiDelCard.addActionListener(this);
		jmiUpdateCard.addActionListener(this);
		jmiSave.addActionListener(this);
		
		//jmiSave.setEnabled(false);

		// Tab 2
		JPanel pnlMove = new JPanel();
		pnlMove.setLayout(new BoxLayout(pnlMove, BoxLayout.Y_AXIS));

		JList<String> deckList1 = new JList<String>();
		//deckList1.setPreferredSize(new Dimension(300, 600));
		JList<String> deckList2 = new JList<String>();
		//deckList2.setPreferredSize(new Dimension(300, 600));
		JButton btnMoveLeft = new JButton("<");
		JButton btnMoveRight = new JButton(">");
		JButton btnCopyLeft = new JButton("<<");
		JButton btnCopyRight = new JButton(">>");
		JButton btnLoadDeck1 = new JButton("Load Deck");
		JButton btnLoadDeck2 = new JButton("Load Deck");
		JButton btnSaveDeck2 = new JButton("Save Deck");

		JPanel pnlMoveBtns = new JPanel();
		pnlMoveBtns.setLayout(new BoxLayout(pnlMoveBtns, BoxLayout.Y_AXIS));
		pnlMoveBtns.add(btnCopyLeft);
		pnlMoveBtns.add(btnCopyRight);
		pnlMoveBtns.add(btnMoveLeft);
		pnlMoveBtns.add(btnMoveRight);

		JPanel pnlDeck1 = new JPanel();
		pnlDeck1.setLayout(new BoxLayout(pnlDeck1, BoxLayout.Y_AXIS));
		JPanel pnlList1 = new JPanel();
		JPanel pnlLoadDeck1 = new JPanel();
		pnlLoadDeck1.setLayout(new FlowLayout(FlowLayout.LEFT));
		JScrollPane scrollPane1 = new JScrollPane(deckList1, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane1.setPreferredSize(new Dimension(300, 400));
		pnlList1.add(scrollPane1);
		pnlLoadDeck1.add(btnLoadDeck1);
		pnlDeck1.add(pnlList1);
		pnlDeck1.add(pnlLoadDeck1);

		JPanel pnlDeck2 = new JPanel();
		pnlDeck2.setLayout(new BoxLayout(pnlDeck2, BoxLayout.Y_AXIS));
		JPanel pnlList2 = new JPanel();
		JPanel pnlLoadDeck2 = new JPanel();
		pnlLoadDeck2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JScrollPane scrollPane2 = new JScrollPane(deckList2, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane2.setPreferredSize(new Dimension(300, 400));
		pnlList2.add(scrollPane2);
		pnlLoadDeck2.add(btnSaveDeck2);
		pnlLoadDeck2.add(btnLoadDeck2);
		pnlDeck2.add(pnlList2);
		pnlDeck2.add(pnlLoadDeck2);

		JPanel pnlLists = new JPanel();
		//pnlLists.setLayout(new BoxLayout(pnlLists, BoxLayout.X_AXIS));
		pnlLists.add(pnlDeck1);
		pnlLists.add(pnlMoveBtns);
		pnlLists.add(pnlDeck2);
		pnlLists.setBorder(b1);

		pnlMove.add(pnlLists);
		//pnlMove.add(pnlLoadBtns);

		// Action Listeners
		btnLoadDeck1.addActionListener(
				evt -> {
					this.deckLeft = openDeck();
					if (deckLeft != null) {
						update(deckList1, deckLeft);
					}
				}
		);

		btnLoadDeck2.addActionListener(
				evt -> {
					this.deckRight = openDeck();
					if (deckRight != null) {
						update(deckList2, deckRight);
					}
				}
		);

		btnSaveDeck2.addActionListener(
				evt -> {
					scan.writeToZip(deckRight);
				}
		);

		btnMoveRight.addActionListener(
				evt -> {
					if (!deckLeft.isEmpty()) {
						int index = deckList1.getSelectedIndex();
						List<Card> ls = new ArrayList<Card>(deckLeft);
						Card c = ls.get(index);
						ls.remove(index);
						deckLeft = new Deck(ls);
						deckRight.add(c);

						update(deckList1, deckLeft);
						update(deckList2, deckRight);
					}
				}
		);

		btnMoveLeft.addActionListener(
				evt -> {
					if (!deckRight.isEmpty()) {
						int index = deckList2.getSelectedIndex();
						List<Card> ls = new ArrayList<Card>(deckRight);
						Card c = ls.get(index);
						ls.remove(index);
						deckRight = new Deck(ls);
						deckLeft.add(c);

						update(deckList1, deckLeft);
						update(deckList2, deckRight);
					}
				}
		);

		btnCopyLeft.addActionListener(
				evt -> {
					if (!deckRight.isEmpty()) {
						int index = deckList2.getSelectedIndex();
						List<Card> ls = new ArrayList<Card>(deckRight);
						Card c = ls.get(index);
						deckRight = new Deck(ls);
						deckLeft.add(c);

						update(deckList1, deckLeft);
						update(deckList2, deckRight);
					}
				}
		);

		btnCopyRight.addActionListener(
				evt -> {
					if (!deckLeft.isEmpty()) {
						int index = deckList1.getSelectedIndex();
						List<Card> ls = new ArrayList<Card>(deckLeft);
						Card c = ls.get(index);
						deckLeft = new Deck(ls);
						deckRight.add(c);

						update(deckList1, deckLeft);
						update(deckList2, deckRight);
					}
				}
		);

		// Settting up menu
		//mnuDeck.add(jmiNewDeck);
		mnuDeck.add(jmiOpen);
		mnuDeck.add(jmiAddCard);
		mnuDeck.add(jmiAddMult);
		mnuDeck.add(jmiUpdateCard);
		mnuDeck.add(jmiDelCard);
		mnuDeck.add(jmiSave);
		mnuDeck.add(jmiQuit);
		
		JMenuBar menubar = new JMenuBar();
		menubar.add(mnuDeck);
		frmMain.setJMenuBar(menubar);

		// Adding components to pane
		mpane.add(cpane3);
		mpane.add(cpane);
		mpane.add(cpane2);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Editor", mpane);
		tabbedPane.addTab("Move Cards", pnlMove);
		frmMain.setContentPane(tabbedPane);
		
		frmMain.pack();
		frmMain.setResizable(false);
		
		return frmMain;
	}
	
//	private void update() {
//		if (deck != null) {
//			//cpane3.setDeck(deck);
//			prepareDeck();
//
//			DefaultListModel<String> lm = new DefaultListModel<String>();
//			for (Card c : deck) {
//				lm.addElement(
//						String.format("<html><body><font color='blue'>%s</font></body></html>", c.name));
//			}
//			if (deck.hasExtras()) {
//				for (Card c2 : deck.getExtraDeck()) {
//					lm.addElement(
//							String.format("<html><body><i><font color='red'>%s</font></i></body></html>", c2.name));
//				}
//			}
//			jlsDeck.setModel(lm);
//
//			//setCard(deck.getLast());
//
//			SwingUtilities.updateComponentTreeUI(this.frmInfo);
//		}
//	}

	private void update() {
		update(jlsDeck, this.deck);
	}

	private void update(JList<String> list, Deck deck) {
		if (deck != null) {
			//cpane3.setDeck(deck);
			prepareDeck();

			DefaultListModel<String> lm = new DefaultListModel<String>();
			for (Card c : deck) {
				lm.addElement(
						String.format("<html><body><font color='blue'>%s</font></body></html>", c.name));
			}
			if (deck.hasExtras()) {
				for (Card c2 : deck.getExtraDeck()) {
					lm.addElement(
							String.format("<html><body><i><font color='red'>%s</font></i></body></html>", c2.name));
				}
			}
			list.setModel(lm);

			//setCard(deck.getLast());

			SwingUtilities.updateComponentTreeUI(this.frmInfo);
		}
	}

	public void setCard(Card card) {
		setCard(card, false);
	}

	public void setCard(Card card, boolean isExtra) {
		if (!(card == null)) {
			//this.card = card;
			jtf[0].setText(card.name);
			jcbCivility.setSelectedItem(card.civility);
			//jcbType.setSelectedItem(card.type);
			jtf[2].setText(card.type);
			jtf[3].setText(Card.energyToString(card.energy));
			jtf[4].setText(card.subtype);

			jtf[6].setText(card.power.toString());
			jtf[7].setText(Card.energyToString(card.eno));
			jtf[8].setText(card.damage.toString());
			jtf[9].setText(card.id);
			jtaEffects.setText("");
			for (String effect : card.effects) {
				jtaEffects.append(effect);
				jtaEffects.append("\n");
			}

			if (card.getImCard() == null) {
				card.createImage();
			}

			if (isExtra) {
				jcbIsExtra.setSelected(true);
			} else {
				jcbIsExtra.setSelected(false);
			}

			card_image.setText("");
			card_image.setIcon(ImageManipulator.scale(card.getImCard(), 300, 400));
		}
	}

	public void setDeck(Deck deck2) {
		this.deck = deck2;
		update();
	}
	
	public void setDeck(String path) {
		if (path.endsWith(".txt")) {
			this.deck = new Deck(scan.scanAllAtributes(path));
			//deckPath = path;
		} else if (path.endsWith(".zip")) {
			this.deck = scan.scanZipFile(path);
			//deckPath = path;
		}
		update();
	}
	
	public void setDeck(InputStream path) {
		deck = new Deck(scan.scanAllAtributes(path));
		update();
	}
	
	public Deque<Card> getDeck() {
		return this.deck;
	}
	
	public void setVisible(boolean visibility) {
		if (frmInfo != null) {
			frmInfo.setVisible(visibility);
		}
	}
	
	public boolean isVisible() {
		return frmInfo.isVisible();
	}
	
	public final void prepareDeck()  {
		// Not Needed Yet.
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if ((ae.getSource() == jmiAddCard) || (ae.getSource() == jmiAddMult) || (ae.getSource() == jmiUpdateCard))  {
			int copies = 1;
			if (ae.getSource() == jmiAddMult) {
				try {
					copies = Integer.parseInt(JOptionPane.showInputDialog("How many copies?"));
				} catch (NumberFormatException ne) {
					ne.printStackTrace();
				}
			}
			
			Card c = new Card();
			c.name = jtf[0].getText();
            //c.civility = (String) jcbCivility.getSelectedItem();
			c.civility = (String) jcbCivility.getEditor().getItem();

			c.type = jtf[2].getText();
			c.energy = Card.stringToEnergy(jtf[3].getText());
			c.subtype = jtf[4].getText();     
			c.power = Integer.parseInt(jtf[6].getText());
			c.eno = Card.stringToEnergy(jtf[7].getText());
			c.damage = Integer.parseInt(jtf[8].getText());
			c.id = jtf[9].getText();
			c.effects = jtaEffects.getText().split("\n");
			c.setImCard((ImageIcon) card_image.getIcon());
			
			if (ae.getSource() == jmiUpdateCard) {
				// Remove the previous card if the user is update/editing the card.
				int index = jlsDeck.getSelectedIndex();
				List<Card> ls = new ArrayList<Card>(deck);
				ls.remove(index);
				deck = new Deck(ls);
			}

			for (int i = 0; i < copies; i++) {
				if (!isExtra) {
					deck.add(c);
					//System.out.println("Adding as main card.");
				} else {
					deck.addAsExtra(c);
					//System.out.println("Adding as extra card.");
				}
			}

			update();
			
			//jmiSave.setEnabled(true);
		} else if (ae.getSource() == jmiDelCard) {
			int index = jlsDeck.getSelectedIndex();
			List<Card> ls = new ArrayList<Card>(deck);
			List<Card> lsEx = deck.getExtraDeck();
			if (index < deck.size()) {
				ls.remove(index);
			} else {
				int indexEx = index - deck.size();
				lsEx.remove(indexEx);
			}
			
			deck = new Deck(ls);
			deck.setExtraDeck(lsEx);

			update();
		} else if (ae.getSource() == jmiSave) {
			//new XMLCardWriter(this.deck);
			scan.writeToZip(deck);
		} else if (ae.getSource() == jmiOpen) {
			this.deck = openDeck();
			if (deck != null) {
				update();
			}
		}
	}

	public Deck openDeck() {
		//deck = scan.scanXMLFile();
		Deck newDeck = null;
		JFileChooser files = new JFileChooser();
		int status = files.showOpenDialog(this.frmInfo);
		if (status == JFileChooser.APPROVE_OPTION) {
			File f = files.getSelectedFile();
			//System.out.println(f.getName()); // Test
			if (f != null) {
				if (f.getName().endsWith("zip")) {
					newDeck = scan.scanZipFileWithXML(f);
				} else if (f.getName().endsWith("txt")) {
					newDeck = scan.scanAllAtributes(f);
				} else {
					JOptionPane.showMessageDialog(new JFrame("Error."), "Deck Error.",
							"Sorry!", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		return newDeck;
	}
	
	public static void main(String... args) {
		new DeckEditor();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		JFileChooser files = new JFileChooser();
		files.showOpenDialog(this.frmInfo);
		if (files.getSelectedFile() != null) {
			try {
				card_image.setIcon(
					new ImageIcon(
						ImageIO.read(files.getSelectedFile())));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
