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
	private JMenuItem jmiNewDeck, jmiAddCard, jmiDelCard, jmiSave, jmiOpen;
	
	private final String[] attr_names = {"Name", "Element", "Type", "Energy", "Subtype", "Effects", "Power", "Energy No.", "Damage Points", "ID"};
	private String[] civilities; // = {"Raenid", "Asarn", "Niaz", "Zivar", "Mayarth", "Kshiti"};
	private JCheckBox jcbIsExtra;

	private boolean isExtra;
//	private String[] sorSubtypes = {"Aid", "Assail", "Attacker", "Convey", "Locate", "Protect"};
//	private String[] trpSubtypes = {"Aid", "Assail", "Convey", "Defender", "Locate", "Protect"};
	
	public DeckEditor() {
		isExtra = false;

		this.scan = new CScan();
		this.frmInfo = createGUI();
		this.frmInfo.setVisible(true);
	}
	
	private JFrame createGUI() {
		JFrame frmMain = new JFrame("Deck Editor");
		frmMain.setSize(900, 700);
		frmMain.setLocation(80, 20);
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
		cpane.setPreferredSize(new Dimension(380,400));
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
							Card[] cards = new Card[deck.getExtraDeck().size()];
							deck.getExtraDeck().toArray(cards);
							c = cards[extraIndex];
//							System.out.println("Extra deck card # : " + extraIndex);
							setCard(c, true);
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
		
		jtaEffects = new JTextArea(3, 20);
		JScrollPane jscroll = new JScrollPane(jtaEffects,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

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
				jtf[i] = new JTextField(20);
			} else {
				jtf[i] = new JTextField(10);
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
		jmiDelCard = new JMenuItem("Remove Card");
		jmiSave = new JMenuItem("Save Deck");
		
		JMenuItem jmiQuit = new JMenuItem("Exit");
		jmiQuit.addActionListener(
			evt -> { System.exit(0); }
		);
		
		jmiNewDeck.addActionListener(this);
		jmiOpen.addActionListener(this);
		jmiAddCard.addActionListener(this);
		jmiDelCard.addActionListener(this);
		jmiSave.addActionListener(this);
		
		jmiSave.setEnabled(false);
		
		mnuDeck.add(jmiNewDeck);
		mnuDeck.add(jmiOpen);
		mnuDeck.add(jmiAddCard);
		mnuDeck.add(jmiDelCard);
		mnuDeck.add(jmiSave);
		mnuDeck.add(jmiQuit);
		
		JMenuBar menubar = new JMenuBar();
		menubar.add(mnuDeck);
		frmMain.setJMenuBar(menubar);
		
		mpane.add(cpane3);
		mpane.add(cpane);
		mpane.add(cpane2);
		
		frmMain.setContentPane(mpane);
		
		frmMain.pack();
		frmMain.setResizable(false);
		
		return frmMain;
	}
	
	private void update() {
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
			jlsDeck.setModel(lm);
			
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
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == jmiAddCard) {
			
			Card c = new Card();
			c.name = jtf[0].getText();
			c.type = jtf[2].getText();
			c.energy = Card.stringToEnergy(jtf[3].getText());
			c.subtype = jtf[4].getText();     
			c.power = Integer.parseInt(jtf[6].getText());
			c.eno = Card.stringToEnergy(jtf[7].getText());
			c.damage = Integer.parseInt(jtf[8].getText());
			c.id = jtf[9].getText();
			c.effects = jtaEffects.getText().split("\n");
			c.setImCard((ImageIcon) card_image.getIcon());

			if (!isExtra) {
				deck.add(c);
				System.out.println("Adding as main card.");
			} else {
				deck.addAsExtra(c);
				System.out.println("Adding as extra card.");
			}

			update();
			
			jmiSave.setEnabled(true);
		} else if (ae.getSource() == jmiDelCard) {
			int index = jlsDeck.getSelectedIndex();
			List<Card> ls = new ArrayList<Card>(deck);
			ls.remove(index);
			deck = new Deck(ls);

			update();
		} else if (ae.getSource() == jmiSave) {
			//new XMLCardWriter(this.deck);
			scan.writeToZip(deck);
		} else if (ae.getSource() == jmiOpen) {
			//deck = scan.scanXMLFile();
			JFileChooser files = new JFileChooser();
			int status = files.showOpenDialog(this.frmInfo);
			if (status == JFileChooser.APPROVE_OPTION) {
				File f = files.getSelectedFile();
				if (f != null) {
					deck = scan.scanZipFileWithXML(f);
					if (deck != null) {
						update();
					}
				}
			}
		}
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
