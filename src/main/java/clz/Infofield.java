package clz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

public class Infofield implements ActionListener {
	Deck deck;
	private CScan scan;
	private JFrame frmInfo = null;
	private JList<String> jlsDeck;
	private JPanel cpane3;
	private JLabel[] jlb = new JLabel[10];
	private JTextField[] jtf = new JTextField[10];
	private JTextArea jtaEffects;
	private JComboBox<String> jcbCivility;
	private JLabel card_image;
	private JButton grab;
	private JCheckBox jcbIsExtra;
	private final String[] attr_names = {"Name", "Element", "Type", "Energy", "Subtype", "Effects", "Power", "ENO", "DP", "ID"};
	
	private CardListener cl;
	
	public Infofield(CardListener cl) {
		this.deck = new Deck();
		this.scan = new CScan();
		this.cl = cl;
		this.frmInfo = createGUI();
	}
	
	private JFrame createGUI() {
		JFrame frmMain = new JFrame("Deck Viewer");
		frmMain.setSize(900, 500);
		frmMain.setLocation(80, 20);
		
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
		card_image = new JLabel();
		card_image.setPreferredSize(new Dimension(300,400));
		
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
						Card[] cards = new Card[deck.size()];
						deck.toArray(cards);
						Card c = cards[index];
						setCard(c);
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
		for (String c : Constants.civilities) {
			jcbCivility.addItem(c);
		}
		jcbCivility.setEditable(true);
		
		jtaEffects = new JTextArea(3, 20);
		JScrollPane jscroll = new JScrollPane(jtaEffects,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		
		for (int i = 0; i < jlb.length; i++) {
			jlb[i] = new JLabel(Constants.mainProp.getProperty(attr_names[i]) + " : ");
		}

		grab = new JButton("Grab Card");
		grab.addActionListener(this);
		
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
		cpane.add(grab);
		
		cpane2.add(card_image);
		
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
				lm.addElement(c.name);
			}
			jlsDeck.setModel(lm);

			if (deck.size() >= 1) {
				setCard(deck.getLast());
			}
		}
	}
	
	public void setCard(Card card) {
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
			card_image.setIcon(ImageManipulator.scale(card.getImCard(), 300, 400));
//			if (PRPlayer.bf != null) {
//				card_image.addMouseListener(new ViewerListener(card_image.getCard(),
//						PRPlayer.bf.h1, PRPlayer.bf.h2));
//			}
			//card_image.showFullImage();
		}
	}

	public void setDeck(Deck deck2) {
		this.deck = deck2;
		update();
	}
	
	public void setDeck(String path) {
		if (path.endsWith(".txt")) {
			this.deck = scan.scanAllAtributes(path);
			//deckPath = path;
		} else if (path.endsWith(".zip")) {
			this.deck = scan.scanZipFile(path);
			//deckPath = path;
		}
		update();
	}
	
	public void setDeck(InputStream path) {
		deck = scan.scanAllAtributes(path);
		update();
	}
	
	public Deck getDeck() {
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
		if (ae.getSource() == grab)  {
			int serialNo = jlsDeck.getSelectedIndex();
			List<Card> lsCards = new ArrayList<Card>();
			lsCards.addAll(deck);
			if (lsCards.size() > 0) {
				Card curCard = lsCards.get(serialNo);
				//lsCards.remove(serialNo); // DOES NOT Work
				this.deck.removeFirstOccurrence(curCard);
				update();
				cl.setCard(curCard);
				cl.setMoved(true);
				//setDeck(new Deck(lsCards)); // DOES NOT Work
			}
		}
	// ToDo
	}
		
	
	
//	public static void main(String... args) {
//		Infofield3 inf = new Infofield3();
//		inf.setDeck(Infofield3.class.getResourceAsStream("/doc/Niaz.txt"));
//		inf.setVisible(true);
//	}

}
