package clz;

/*
 * Battlefield2.java
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


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;



public class Battlefield extends JFrame
implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jplMB, jplOB;
	private PrefIFrame pif;
	public static CLabel jlbMG, jlbOG, jlbMD, jlbOD;
	private CLabel[] jlbMS = new CLabel[20];
	private CLabel[] jlbOS = new CLabel[20];
	private CLabel[] jlbMM = new CLabel[20];
	private CLabel[] jlbOM = new CLabel[20];
	private CLabel[] jlbMB = new CLabel[20];
	private CLabel[] jlbOB = new CLabel[20];
	private CWindow MG, OG, MRA[], ORA[];
	private JTextPane jtext;
	private JMenuBar jmb = new JMenuBar();
	private JMenu jmAct, jmGame, jmHelp, jmDeck;
	private JMenuItem jmiExit, jmiEndTurn, jmiShowMH, jmiShowOH, jmiShowR[],
						jmiViewDeck, jmiShowTPL, jmiHelp, jmiAbout, jmiPref,
						jmiSave, jmiOpen, jmiViewRecentCard;
	private JMenuItem jpiShuffle, jpiSearch, jpiFlip, jpiTap, jpiUntap, jpiDraw,
						jpiView, jpiViewCard, jpiViewName, jpiToDeck, jpiToBottom, jpiRemove;
	
	private JButton jbLicence;
	private JFrame aboutFrame;
	private String message;
	public static String userhome;
	
	private GroupLayout gl, gl2;
	private Container bc = getContentPane();
	private ImageIcon imDeck, imGrave;
	public static JPopupMenu s1, s2, s3;
	public static boolean opturn;
	public static ImageIcon imNoCard;
	public Deque<Card> deck1, deck2; //Changed
	private static Hand h1, h2;
	public static CardListener cl = new CardListener();
	private Infofield inf, mi, oi;
	public Card bkCard;
	private JFrame viewer;

	public Battlefield() {
		//Setting Icon and Location
		this.setIconImage(
				Toolkit.getDefaultToolkit().getImage(
						Infofield.class.getResource("/images/Icon.png")));
		this.setLocation(80, 20);		
		
		
		//Initializing Deck Viewers and Decks
		deck1 = new ArrayDeque<Card>();
		deck2 = new ArrayDeque<Card>();
        
		//Initializing CWindows
		MG = new CWindow("Player 1's Graveyard", 20, cl);
		OG = new CWindow("Player 2's Graveyard", 20, cl);
		MRA = new CWindow[4];
		ORA = new CWindow[4];
		for (int i = 1; i < 5; i++) {
			MRA[i - 1] = new CWindow("Player 1's Area " + i, 20, cl);
			ORA[i - 1] = new CWindow("Player 2's Area " + i, 20, cl);
		}
		//Creating JPopupMenus
		jpiSearch = new JMenuItem("Search");
		jpiSearch.addActionListener(this);
		jpiShuffle = new JMenuItem("Shuffle");
		jpiShuffle.addActionListener(this);
		jpiTap = new JMenuItem("Tap");
		jpiTap.addActionListener(this);
		jpiUntap = new JMenuItem("Untap");
		jpiUntap.addActionListener(this);
		jpiDraw = new JMenuItem("Draw");
		jpiDraw.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0));
		jpiDraw.addActionListener(this);
		jpiView = new JMenuItem("View");
		jpiView.addActionListener(this);
		jpiFlip = new JMenuItem("Flip");
		jpiFlip.addActionListener(this);
		jpiViewCard = new JMenuItem("View Full Image");
		jpiViewCard.addActionListener(this);
		jpiViewName = new JMenuItem("View Name");
		jpiViewName.addActionListener(this);
		jpiToDeck = new JMenuItem("To Deck");
		jpiToDeck.setToolTipText("Move this card to deck.");
		jpiToDeck.addActionListener(this);
		jpiToBottom = new JMenuItem("To Bottom of Deck");
		jpiToBottom.setToolTipText("Send the top card of this deck to its bottom.");
		jpiToBottom.addActionListener(this);
		jpiRemove = new JMenuItem("Remove Placeholder");
		jpiRemove.addActionListener(this);
		s1 = new JPopupMenu("Deck");
		s1.add(jpiSearch);
		s1.add(jpiShuffle);
		s1.add(jpiDraw);
		s1.add(jpiToBottom);
		s2 = new JPopupMenu();
		s2.add(jpiView);
		s3 = new JPopupMenu();
		s3.add(jpiFlip);
		s3.add(jpiViewCard);
		s3.add(jpiViewName);
		s3.add(jpiToDeck);
		s3.add(jpiRemove);
		
		//Creating Images & Card templates
		try {
			imDeck = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/Deck.jpg")));
			imGrave = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/Grave.jpg")));
			imNoCard = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/NCRD.jpg")));
		} catch (Exception e) {
			e.printStackTrace();
			imDeck = null;
			imGrave = null;
			imNoCard = null;
		}
		bkCard = new Card("FDCard", "/images/Back.jpg");

		//Creating the Hands
		h1 = new Hand("Player 1's Hand", cl);
		h2 = new Hand("Player 2's Hand", cl);
		h1.setLocation(600, 50);
		h2.setLocation(600, 300);
		
		//Creating Menus and main JFrame
		this.setTitle("Battlefield");
		this.setSize(new Dimension(900, 700));
		jmAct = new JMenu("Actions");
		jmGame = new JMenu("Game");
		jmHelp = new JMenu("Help");
		jmDeck = new JMenu("Deck");
		jmiExit = new JMenuItem("Exit");
		jmiExit.addActionListener(this);
		jmiExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0));
		jmiEndTurn = new JMenuItem("End Current Turn");
		jmiEndTurn.addActionListener(this);
		jmiEndTurn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0));
		jmiShowMH = new JMenuItem("Show Player 1's Hand");
		jmiShowMH.addActionListener(this);
		jmiShowMH.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0));
		jmiShowOH = new JMenuItem("Show Player 2's Hand");
		jmiShowOH.addActionListener(this);
		jmiShowOH.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.SHIFT_MASK));
		jmiShowR = new JMenuItem[4];
		jmiShowR[0] = new JMenuItem("Show Reserved Area 1");
		jmiShowR[0].addActionListener(this);
		jmiShowR[1] = new JMenuItem("Show Reserved Area 2");
		jmiShowR[1].addActionListener(this);
		jmiShowR[2] = new JMenuItem("Show Reserved Area 3");
		jmiShowR[2].addActionListener(this);
		jmiShowR[3] = new JMenuItem("Show Reserved Area 4");
		jmiShowR[3].addActionListener(this);
		jmiViewDeck = new JMenuItem("View Current Player's Deck");
		jmiViewDeck.addActionListener(this);
		jmiShowTPL = new JMenuItem("Turn Player");
		jmiShowTPL.addActionListener(this);
		jmiPref = new JMenuItem("Preferences...");
		jmiPref.addActionListener(this);
		jmiHelp = new JMenuItem("Rules");
		jmiHelp.setToolTipText("Rules of Paladins Returned");
		jmiHelp.addActionListener(this);
		jmiAbout = new JMenuItem("About");
		jmiAbout.addActionListener(this);
		jmiSave = new JMenuItem("Save Game");
		jmiSave.addActionListener(this);
		jmiOpen = new JMenuItem("Open Game");
		jmiOpen.addActionListener(this);
		jmiViewRecentCard = new JMenuItem("View Recently Viewed Card");
		jmiViewRecentCard.addActionListener(this);
		jmHelp.add(jmiHelp);
		jmHelp.add(jmiAbout);
		jmGame.add(jmiEndTurn);
		jmGame.add(jmiShowTPL);
		jmGame.add(jmiPref);
		jmGame.add(jmiExit);
		jmAct.add(jmiShowMH);
		jmAct.add(jmiShowOH);
		jmAct.add(jmiViewDeck);
		jmAct.add(jmiViewRecentCard);
		jmDeck.add(jpiSearch);
		jmDeck.add(jpiShuffle);
		jmDeck.add(jpiDraw);
		jmDeck.add(jpiToBottom);
		for (int i = 0; i < jmiShowR.length; i++) {
			jmAct.add(jmiShowR[i]);
		}
		jmb.add(jmGame);
		jmb.add(jmAct);
		jmb.add(jmDeck);
		jmb.add(jmHelp);
		this.setJMenuBar(jmb);

		//Creating the JLabels and JPanels
		jplMB = new JPanel();
		jplOB = new JPanel();
		jlbMD = new CLabel(imDeck);
		jlbOD = new CLabel(imDeck);
		for (int i = 0; i < 10; i++) {
		jlbMS[i+1] = new CLabel(imNoCard);
		jlbOS[i+1] = new CLabel(imNoCard);
		}

		jlbMG = new CLabel(imGrave);
		jlbOG = new CLabel(imGrave);

		//Adding MouseListeners
		jlbMD.addMouseListener(cl);
		jlbOD.addMouseListener(cl);
		jlbMG.addMouseListener(cl);
		jlbOG.addMouseListener(cl);
		for (int i = 0; i < 10; i++) {
		jlbMS[i+1].addMouseListener(cl);
		jlbOS[i+1].addMouseListener(cl);
		jlbMS[i+1].addMouseWheelListener(cl);
		jlbOS[i+1].addMouseWheelListener(cl);
		}
		jlbMD.addMouseWheelListener(cl);
		jlbOD.addMouseWheelListener(cl);
		jlbMG.addMouseWheelListener(cl);
		jlbOG.addMouseWheelListener(cl);

		//Clearing Tooltip texts from jlbMD, jlbOD, jlbMG and jlbOG
		jlbMD.setToolTipText("");
		jlbOD.setToolTipText("");
		jlbMG.setToolTipText("");
		jlbOG.setToolTipText("");
		
		//creating Mana JLabels
		for(int i = 0; i <= 12; i++) {
			jlbMM[i+1] = new CLabel(imNoCard);
			jlbMM[i+1].addMouseListener(cl);
			jlbMM[i+1].addMouseWheelListener(cl);
			jlbOM[i+1] = new CLabel(imNoCard);
			jlbOM[i+1].addMouseListener(cl);
			jlbOM[i+1].addMouseWheelListener(cl);
			jlbMB[i+1] = new CLabel(imNoCard);
			jlbMB[i+1].addMouseListener(cl);
			jlbMB[i+1].addMouseWheelListener(cl);
			jlbOB[i+1] = new CLabel(imNoCard);
			jlbOB[i+1].addMouseListener(cl);
			jlbOB[i+1].addMouseWheelListener(cl);
		}
		
		//Setting up Group Layout
		//GroupLayout 1
		gl = new GroupLayout(jplMB);
		jplMB.setLayout(gl);
		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);
		GroupLayout.SequentialGroup mhGroup = gl.createSequentialGroup();
		GroupLayout.SequentialGroup mvGroup = gl.createSequentialGroup();
		mhGroup.addGroup(gl.createParallelGroup().addComponent(jlbMB[12]).addComponent(jlbMS[1]).addComponent(jlbMM[12]));
		mhGroup.addGroup(gl.createParallelGroup().addComponent(jlbMB[11]).addComponent(jlbMS[2]).addComponent(jlbMM[11]));
		mhGroup.addGroup(gl.createParallelGroup().addComponent(jlbMB[10]).addComponent(jlbMS[3]).addComponent(jlbMM[10]));
		mhGroup.addGroup(gl.createParallelGroup().addComponent(jlbMB[9]).addComponent(jlbMS[4]).addComponent(jlbMM[9]));
		mhGroup.addGroup(gl.createParallelGroup().addComponent(jlbMB[8]).addComponent(jlbMS[5]).addComponent(jlbMM[8]));
		mhGroup.addGroup(gl.createParallelGroup().addComponent(jlbMB[7]).addComponent(jlbMS[6]).addComponent(jlbMM[7]));
		mhGroup.addGroup(gl.createParallelGroup().addComponent(jlbMB[6]).addComponent(jlbMS[7]).addComponent(jlbMM[6]));
		mhGroup.addGroup(gl.createParallelGroup().addComponent(jlbMB[5]).addComponent(jlbMS[8]).addComponent(jlbMM[5]));
		mhGroup.addGroup(gl.createParallelGroup().addComponent(jlbMB[4]).addComponent(jlbMS[9]).addComponent(jlbMM[4]));
		mhGroup.addGroup(gl.createParallelGroup().addComponent(jlbMB[3]).addComponent(jlbMS[10]).addComponent(jlbMM[3]));
		mhGroup.addGroup(gl.createParallelGroup().addComponent(jlbMB[2]).addComponent(jlbMD).addComponent(jlbMM[2]));
		mhGroup.addGroup(gl.createParallelGroup().addComponent(jlbMB[1]).addComponent(jlbMG).addComponent(jlbMM[1]));
		gl.setHorizontalGroup(mhGroup);
		mvGroup.addGroup(gl.createParallelGroup()
				.addComponent(jlbMB[12])
				.addComponent(jlbMB[11])
				.addComponent(jlbMB[10])
				.addComponent(jlbMB[9])
				.addComponent(jlbMB[8])
				.addComponent(jlbMB[7])
				.addComponent(jlbMB[6])
				.addComponent(jlbMB[5])
				.addComponent(jlbMB[4])
				.addComponent(jlbMB[3])
				.addComponent(jlbMB[2])
				.addComponent(jlbMB[1])
				);
		mvGroup.addGroup(gl.createParallelGroup()
				.addComponent(jlbMS[1])
				.addComponent(jlbMS[2])
				.addComponent(jlbMS[3])
				.addComponent(jlbMS[4])
				.addComponent(jlbMS[5])
				.addComponent(jlbMS[6])
				.addComponent(jlbMS[7])
				.addComponent(jlbMS[8])
				.addComponent(jlbMS[9])
				.addComponent(jlbMS[10])
				.addComponent(jlbMD)
				.addComponent(jlbMG)
				);
		mvGroup.addGroup(gl.createParallelGroup()
				.addComponent(jlbMM[12])
				.addComponent(jlbMM[11])
				.addComponent(jlbMM[10])
				.addComponent(jlbMM[9])
				.addComponent(jlbMM[8])
				.addComponent(jlbMM[7])
				.addComponent(jlbMM[6])
				.addComponent(jlbMM[5])
				.addComponent(jlbMM[4])
				.addComponent(jlbMM[3])
				.addComponent(jlbMM[2])
				.addComponent(jlbMM[1])
				);

		gl.setVerticalGroup(mvGroup);
		
		
		//GroupLayout 2
		gl2 = new GroupLayout(jplOB);
		jplOB.setLayout(gl2);
		gl2.setAutoCreateGaps(true);
		gl2.setAutoCreateContainerGaps(true);
		GroupLayout.SequentialGroup ohGroup = gl2.createSequentialGroup();
		GroupLayout.SequentialGroup ovGroup = gl2.createSequentialGroup();
		ohGroup.addGroup(gl2.createParallelGroup().addComponent(jlbOB[1]).addComponent(jlbOG).addComponent(jlbOM[1]));
		ohGroup.addGroup(gl2.createParallelGroup().addComponent(jlbOB[2]).addComponent(jlbOD).addComponent(jlbOM[2]));
		ohGroup.addGroup(gl2.createParallelGroup().addComponent(jlbOB[3]).addComponent(jlbOS[1]).addComponent(jlbOM[3]));
		ohGroup.addGroup(gl2.createParallelGroup().addComponent(jlbOB[4]).addComponent(jlbOS[2]).addComponent(jlbOM[4]));
		ohGroup.addGroup(gl2.createParallelGroup().addComponent(jlbOB[5]).addComponent(jlbOS[3]).addComponent(jlbOM[5]));
		ohGroup.addGroup(gl2.createParallelGroup().addComponent(jlbOB[6]).addComponent(jlbOS[4]).addComponent(jlbOM[6]));
		ohGroup.addGroup(gl2.createParallelGroup().addComponent(jlbOB[7]).addComponent(jlbOS[5]).addComponent(jlbOM[7]));
		ohGroup.addGroup(gl2.createParallelGroup().addComponent(jlbOB[8]).addComponent(jlbOS[6]).addComponent(jlbOM[8]));
		ohGroup.addGroup(gl2.createParallelGroup().addComponent(jlbOB[9]).addComponent(jlbOS[7]).addComponent(jlbOM[9]));
		ohGroup.addGroup(gl2.createParallelGroup().addComponent(jlbOB[10]).addComponent(jlbOS[8]).addComponent(jlbOM[10]));
		ohGroup.addGroup(gl2.createParallelGroup().addComponent(jlbOB[11]).addComponent(jlbOS[9]).addComponent(jlbOM[11]));
		ohGroup.addGroup(gl2.createParallelGroup().addComponent(jlbOB[12]).addComponent(jlbOS[10]).addComponent(jlbOM[12]));
		gl2.setHorizontalGroup(ohGroup);
		ovGroup.addGroup(gl2.createParallelGroup()
				.addComponent(jlbOM[1])
				.addComponent(jlbOM[2])
				.addComponent(jlbOM[3])
				.addComponent(jlbOM[4])
				.addComponent(jlbOM[5])
				.addComponent(jlbOM[6])
				.addComponent(jlbOM[7])
				.addComponent(jlbOM[8])
				.addComponent(jlbOM[9])
				.addComponent(jlbOM[10])
				.addComponent(jlbOM[11])
				.addComponent(jlbOM[12])
				);
		ovGroup.addGroup(gl2.createParallelGroup()
				.addComponent(jlbOG)
				.addComponent(jlbOD)
				.addComponent(jlbOS[1])
				.addComponent(jlbOS[2])
				.addComponent(jlbOS[3])
				.addComponent(jlbOS[4])
				.addComponent(jlbOS[5])
				.addComponent(jlbOS[6])
				.addComponent(jlbOS[7])
				.addComponent(jlbOS[8])
				.addComponent(jlbOS[9])
				.addComponent(jlbOS[10]));
		ovGroup.addGroup(gl2.createParallelGroup()
				.addComponent(jlbOB[1])
				.addComponent(jlbOB[2])
				.addComponent(jlbOB[3])
				.addComponent(jlbOB[4])
				.addComponent(jlbOB[5])
				.addComponent(jlbOB[6])
				.addComponent(jlbOB[7])
				.addComponent(jlbOB[8])
				.addComponent(jlbOB[9])
				.addComponent(jlbOB[10])
				.addComponent(jlbOB[11])
				.addComponent(jlbOB[12])
				);
		gl2.setVerticalGroup(ovGroup);
		
		//Adding the JPanels
		bc.setLayout(new BorderLayout());
		bc.add(jplOB, BorderLayout.NORTH);
		bc.add(jplMB, BorderLayout.SOUTH);
		
		//Finalizing
		this.setVisible(true);
		
		//Setting configuration path
		userhome = System.getProperty("user.home");
		File confdir = new File(userhome + "/.PRPlayer/"),
				deckdir = new File(confdir.getAbsolutePath() + "/decks/");
		if (!confdir.exists()) {
			confdir.mkdir();
		}
		if (!deckdir.exists()) {
			deckdir.mkdir();
		}
		
		//Initializing Preferences JInternalFrame
		  pif = new PrefIFrame(this);
		
		//Initializing Decks
		  pif.btnCancel.setEnabled(false);
		  pif.setVisible(true);
		 
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						for (LookAndFeelInfo lafinf : UIManager.getInstalledLookAndFeels()) {
							if ("Nimbus".equals(lafinf.getName())) {
								UIManager.setLookAndFeel(lafinf.getClassName());
							}
						}
						new Battlefield();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

	public static Deque<Card> shuffle(Deque<Card> d) {
		Random r = new Random();
		Random r2 = new Random();
		Vector<Card> s = new Vector<Card>(d);
		for (int j = 0; j < r2.nextInt(39); j++) {
			for (int i = 0; i < s.size(); i++) {
				int random = r.nextInt(s.size() - 1);
				Card rCard1, rCard2;
				rCard1 = s.elementAt(i);
				s.removeElementAt(i);
				rCard2 = s.elementAt(random);
				s.removeElementAt(random);
				s.insertElementAt(rCard1, random);
				s.insertElementAt(rCard2, i);
			}
		}
		Deque<Card> d2 = new ArrayDeque<Card>(s);
		return d2;
	}

	public void proceed(int i, int j) {
		//Initializing Infofield
        inf = new Infofield();
        mi = new Infofield(deck1);
        oi = new Infofield(deck2);
		
		for (int i1 = 0; i1 < new Random().nextInt(60); i1++) {
			//Preparing
			deck1 = shuffle(deck1);
			deck2 = shuffle(deck2);
		}
		if (pif.isShields()) {
			//Adding Shields, if any
			for (int k = 0; k < i; k++) {
				addToShield(deck1.pop(), k + 1, true);
				addToShield(deck2.pop(), k + 1, false);
			}
		}
		
		if (pif.isDraw()) {
			//Drawing
			if (!(deck1.isEmpty() && deck2.isEmpty())) {
				for (int k = 0; k < 5; k++) {
					draw(true);
					draw(false);
				}
			}
		}
		h1.showHand();
		opturn = false;
	}
	
	public void addToShield(Card card, int index, boolean opturn) {
		//Set cards and filp them
		if (opturn) {
			jlbOS[index].setCard(card);
			jlbOS[index].flip();
		} else {
			jlbMS[index].setCard(card);
			jlbMS[index].flip();
		}
	}

	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == jmiExit) {
			System.exit(0);
		} else if(ae.getSource() == jmiEndTurn) {
			String player;
			if (opturn) {
				opturn = false;
				player = "1st Player - Me";
			} else {
				opturn = true;
				player = "2nd Player - Opponent";
			}
			JOptionPane.showMessageDialog(this, "The current player is : " + player,
					"Turn Ended", JOptionPane.INFORMATION_MESSAGE);
			
		} else if(ae.getSource() == jpiShuffle) {
			if (opturn) {
				shuffle(deck2);
			} else {
				shuffle(deck1);
			}
		} else if(ae.getSource() == jpiDraw) {
			draw(opturn);
		} else if(ae.getSource() == jmiShowMH) {
			if (!h1.isVisible()) {
			h1.setVisible(true);
			}
		} else if(ae.getSource() == jmiShowOH) {
			if (!h2.isVisible()) {
			h2.setVisible(true);
			}
		} else if(ae.getSource() == jpiSearch) {
			if (s1.getInvoker() == jlbOD) {
				inf.setDeck(deck2);
			} else if(s1.getInvoker() == jlbMD) {
				inf.setDeck(deck1);
			}
			inf.setVisible(true);
		} else if(ae.getSource() == jmiShowR[0]) {
			if (opturn) {
				ORA[0].showWin();
			} else {
				MRA[0].showWin();
			}
		} else if(ae.getSource() == jmiShowR[1]) {
			if (opturn) {
				ORA[1].showWin();
			} else {
				MRA[1].showWin();
			}
		} else if(ae.getSource() == jmiShowR[2]) {
			if (opturn) {
				ORA[2].showWin();
			} else {
				MRA[2].showWin();
			}
		} else if(ae.getSource() == jmiShowR[3]) {
			if (opturn) {
				ORA[3].showWin();
			} else {
				MRA[3].showWin();
			}
		} else if(ae.getSource() == jpiView) {
			if (s2.getInvoker() == jlbOG) {
				OG.showWin();
			} else if(s2.getInvoker() == jlbMG) {
				MG.showWin();
			}
		} else if(ae.getSource() == jmiViewDeck) {
			if (opturn) {
				if (oi.isVisible() == false) {
					oi.setVisible(true);
				}
			} else {
				if (mi.isVisible() == false) {
					mi.setVisible(true);
				}
			}
		} else if(ae.getSource() == jpiFlip) {
			CLabel label = (CLabel) s3.getInvoker();
			label.flip();
		} else if(ae.getSource() == jpiViewCard) {
			Component lbl = s3.getInvoker();
			if (opturn) {
				h2.setVisible(false);
				h2.getContentPane().remove(lbl);
				h2.setVisible(true);
			} else {
				h1.setVisible(false);
				h1.getContentPane().remove(lbl);
				h1.setVisible(true);
			}
			CLabel lblCard = new CLabel(((CLabel) lbl).getCard());
			lblCard.addMouseListener(new ViewerListener(lblCard.getCard()));
			viewer = new JFrame("Card Viewer");
			viewer.setSize(400, 600);
			viewer.getContentPane().add(lblCard);
			viewer.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {
					
				}
			});
			lblCard.showFullImage();
			viewer.setVisible(true);
		} else if(ae.getSource() == jpiViewName) {
			CLabel label = (CLabel) s3.getInvoker();
			String cardname = label.getCard().name;
			cardname = cardname.substring(0, cardname.length() - 4);
			JOptionPane.showMessageDialog(this, "The selected card's name is : " + cardname,
					"Card Name", JOptionPane.INFORMATION_MESSAGE);
		} else if(ae.getSource() == jmiShowTPL) {
			String plname;
			if (opturn) {
				plname = "2nd Player - Opponent";
			} else {
				plname = "1st Player - Me";
			}
			JOptionPane.showMessageDialog(this, "The current player is : " + plname,
					"Current Player", JOptionPane.INFORMATION_MESSAGE);
		} else if(ae.getSource() == jpiToDeck) {
			CLabel lbl = (CLabel) s3.getInvoker();
			Card card = lbl.grCard();
			if (opturn) {
				deck2.addFirst(card);
				inf.setCard(deck2.getFirst());
			} else {
				deck1.addFirst(card);
				inf.setCard(deck1.getFirst());
			}
		} else if(ae.getSource() == jpiToBottom) {
			Card card;
			if (opturn) {
				card = deck2.removeFirst();
				deck2.addLast(card);
				inf.setCard(deck2.getFirst());
			} else {
				card = deck1.removeFirst();
				deck1.addLast(card);
				inf.setCard(deck1.getFirst());
			}
		} else if(ae.getSource() == jpiRemove) {
			Component lbl = s3.getInvoker();
			if (opturn) {
				h2.setVisible(false);
				h2.getContentPane().remove(lbl);
				h2.setVisible(true);
			} else {
				h1.setVisible(false);
				h1.getContentPane().remove(lbl);
				h1.setVisible(true);
			}
		} else if(ae.getSource() == jmiHelp) {
			HelpFrame hf = new HelpFrame();
			hf.setVisible(true);
		} else if(ae.getSource() == jmiPref) {		
			pif.setVisible(true);
		} else if(ae.getSource() == jmiViewRecentCard) {
			if ((viewer != null) && (!viewer.isVisible())) {
				viewer.setVisible(true);
			}
		} else if(ae.getSource() == jmiAbout) {
			message = "PR Player\nCreator : Subhraman Sarkar, 2014";
			aboutFrame = new JFrame("About");
			aboutFrame.getContentPane().setLayout(new BorderLayout());
			jtext = new JTextPane();
			jtext.setEditable(false);
			jtext.setText(message);
			JScrollPane js = new JScrollPane(jtext);
			aboutFrame.getContentPane().add(js, BorderLayout.CENTER);
			jbLicence = new JButton("Licence");
			jbLicence.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
						try {
							jtext.setPage(this.getClass().getResource("/LICENCE"));
							jbLicence.setEnabled(false);
						} catch (IOException e) {
							e.printStackTrace();
						}
				}				
			});
			aboutFrame.getContentPane().add(jbLicence, BorderLayout.SOUTH);
			aboutFrame.setSize(new Dimension(400, 400));
			aboutFrame.setVisible(true);
		}
	}
	
	public void draw(boolean opt) {
		if (opt) {
			h2.add(deck2.pop());
		} else {
			h1.add(deck1.pop());
		}
	}
	
	public class ViewerListener extends MouseAdapter implements ActionListener {
		JPopupMenu s4 = new JPopupMenu();
		JMenuItem jpiToHand = new JMenuItem("Move to Hand");
		Card c;
		
		public ViewerListener(Card card) {
			jpiToHand.addActionListener(this);
			s4.add(jpiToHand);
			c = card;
		}
		
		public void mouseClicked(MouseEvent me) {
			s4.show(me.getComponent(), me.getX(), me.getY());
		}

		@Override
		public void actionPerformed(ActionEvent aevt) {
			if (aevt.getSource() == jpiToHand) {
				if (c != null) {
					if (opturn) {
						h2.setVisible(false);
						h2.add(c);
						h2.setVisible(true);
					} else {
						h1.setVisible(false);
						h1.add(c);
						h1.setVisible(true);
					}
				}
				jpiToHand.setEnabled(false);
			}
		}
	}
	
}

