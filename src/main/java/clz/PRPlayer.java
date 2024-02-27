/*
 * PRPlayer.java
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
 
package clz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;

import clz.xml.DeckEditor;


public class PRPlayer extends JFrame implements ActionListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JMenuItem jpiRot270;
	private JPanel jplMB, jplOB;
	private JLabel statusBar;
	public static PrefIFrame pif;
	public static JLabel jlbMG, jlbOG, jlbMD, jlbOD;
	private CLabel[] jlbMS = new CLabel[20];
	private CLabel[] jlbOS = new CLabel[20];
	private CLabel[] jlbMM = new CLabel[14];
	private CLabel[] jlbOM = new CLabel[14];
	private CLabel[] jlbMB = new CLabel[14];
	private CLabel[] jlbOB = new CLabel[14];
	private CWindow MG, OG, MRA[], ORA[];
	private JTextPane jtext;
	private JMenuBar jmb = new JMenuBar();
	private JMenu jmAct, jmGame, jmHelp;
	private JMenuItem jmiExit, jmiEndTurn, jmiShowMH, jmiShowOH, jmiShowR[],
						jmiViewLibrary, jmiShowTPL, jmiHelp, jmiAbout, jmiPref,
						jmiSave, jmiOpen, jmiViewRecentCard, jmiRestart, jmiShowEditor;
	
	private JButton jbLicence;
	private JFrame aboutFrame;
	private String message;
	public static String userhome;
	
	private GroupLayout gl, gl2;
	private Container bc = getContentPane();
	private ImageIcon imLibrary, imGrave;
	private JPopupMenu deckMenu, graveMenu, cardMenu;
	private JMenuItem jpiShuffle, jpiSearch, jpiViewExtra, jpiFlip, jpiDraw, jpiDrawMult, jpiLook, jpiFlipVert,
		jpiLink,
		jpiView, jpiViewCard, jpiToLibrary, jpiToBottom, jpiRemove, jpiToLibBottom, jpiToGrave, jpiSummon;
	public static boolean opturn;
	public static ImageIcon imNoCard;
	public Deck deck1, deck2; //Changed
	public Collec h1, h2;
	private EnergyBar bar1, bar2;
	public static CardListener cl;
	private Infofield inf, mi, oi;
	public Card bkCard;
	private JFrame viewer;
	private static PRPlayer bf = null;
	private CWindow exWin1, exWin2;

	private PRPlayer() {
		
		SwingUtilities.updateComponentTreeUI(this);
		
		//Setting Icon and Location
		this.setIconImage(
				Toolkit.getDefaultToolkit().getImage(
						Infofield.class.getResource("/images/Icon.png")));
		this.setLocation(40, 20);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		
		//Initializing Library Viewers and Librarys
		deck1 = new Deck();
		deck2 = new Deck();
		
		//Creating Images & Card templates
		try {
			imLibrary = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/Library.jpg")));
			imGrave = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/Grave.jpg")));
			imNoCard = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/images/NCRD.jpg")));
		} catch (Exception e) {
			e.printStackTrace();
			imLibrary = null;
			imGrave = null;
			imNoCard = null;
		}
		
		bkCard = new Card("FDCard", "/images/Back.jpg");
		
		//Creating JPopupMenus
		jpiSearch = new JMenuItem("Search");
		jpiSearch.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0));
		jpiSearch.addActionListener(this);
		jpiShuffle = new JMenuItem("Shuffle");
		jpiShuffle.addActionListener(this);
		jpiDraw = new JMenuItem("Draw");
		jpiDraw.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0));
		jpiDraw.addActionListener(this);
		jpiDrawMult = new JMenuItem("Draw Multiple...");
		jpiDrawMult.addActionListener(this);
		jpiView = new JMenuItem("View");
		jpiView.addActionListener(this);
		jpiViewExtra = new JMenuItem("View Extra Deck");
		jpiViewExtra.addActionListener(this);
		jpiFlip = new JMenuItem("Flip");
		jpiFlip.addActionListener(this);
		jpiViewCard = new JMenuItem("View Full Data");
		jpiViewCard.addActionListener(this);
		jpiFlipVert = new JMenuItem("Upside Down");
		jpiFlipVert.addActionListener(this);
		jpiRot270 = new JMenuItem("Rotate 270");
		jpiRot270.addActionListener(this);
		jpiLink = new JMenuItem("View Linked Card");
		jpiLink.addActionListener(this);
		jpiToLibrary = new JMenuItem("To Top of Library");
		jpiToLibrary.setToolTipText("Move this card to library's top.");
		jpiToLibrary.addActionListener(this);
		jpiToLibBottom = new JMenuItem("To Bottom of Library");
		jpiToLibBottom.setToolTipText("Move this card to library's bottom.");
		jpiToLibBottom.addActionListener(this);
		jpiToBottom = new JMenuItem("Top Card to Bottom");
		jpiToBottom.setToolTipText("Send the top card of this deck to its bottom.");
		jpiToBottom.addActionListener(this);
		jpiLook = new JMenuItem("Look at Top");
		jpiLook.addActionListener(this);
		jpiRemove = new JMenuItem("Remove Placeholder");
		jpiRemove.addActionListener(this);
		jpiToGrave = new JMenuItem("Destroy/Discard");
		jpiToGrave.addActionListener(this);
		jpiSummon = new JMenuItem("Summon/Cast");
		jpiSummon.addActionListener(this);

		deckMenu = new JPopupMenu("Deck");
		deckMenu.add(jpiLook);
		deckMenu.add(jpiSearch);
		deckMenu.add(jpiViewExtra);
		deckMenu.add(jpiShuffle);
		deckMenu.add(jpiDraw);
		deckMenu.add(jpiDrawMult);
		deckMenu.add(jpiToBottom);
		graveMenu = new JPopupMenu("Cemetry");
		graveMenu.add(jpiView);
		cardMenu = new JPopupMenu("Card");
		
		// Organize menus, 3/11/23
		JMenu jmActions = new JMenu("Actions");
		jmActions.add(jpiFlip);
		jmActions.add(jpiSummon);
		cardMenu.add(jmActions);
		
		JMenu jmRotate = new JMenu("Rotate");
		jmRotate.add(jpiFlipVert);
		jmRotate.add(jpiRot270);
		cardMenu.add(jmRotate);
		
		cardMenu.add(jpiViewCard);
		cardMenu.add(jpiLink);
		JMenu jmMove = new JMenu("Move");
		jmMove.add(jpiToLibrary);
		jmMove.add(jpiToLibBottom);
		jmMove.add(jpiToGrave);
		cardMenu.add(jmMove);
		
		cardMenu.add(jpiRemove);

		bar1 = new EnergyBar();
		bar2 = new EnergyBar();

		cl = new CardListener(cardMenu, bar1, bar2);
		
		//Initializing CWindows
		MG = new CWindow("Player 1's Cemetery", cl);
		OG = new CWindow("Player 2's Cemetery", cl);
		MRA = new CWindow[4];
		ORA = new CWindow[4];
		for (int i = 1; i < 5; i++) {
			MRA[i - 1] = new CWindow("Player 1's Area " + i, cl);
			ORA[i - 1] = new CWindow("Player 2's Area " + i, cl);
		}

		// Creating the Hands
		h1 = new Collec("Player 1's Collection", cl);
		h2 = new Collec("Player 2's Collection", cl);
		h1.setLocation(600, 50);
		h2.setLocation(600, 300);
		
		//Creating Menus and main JFrame
		this.setTitle("PRPlayer");
		this.setSize(new Dimension(1050, 700));
		
		jmAct = new JMenu("View");
		jmGame = new JMenu("Actions");
		jmHelp = new JMenu("Help");
		
		jmiExit = new JMenuItem("Exit");
		jmiExit.addActionListener(this);
		jmiExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0));
		jmiEndTurn = new JMenuItem("End Current Turn");
		jmiEndTurn.addActionListener(this);
		jmiEndTurn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0));
		jmiShowMH = new JMenuItem("Show Player 1's Collection");
		jmiShowMH.addActionListener(this);
		jmiShowMH.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0));
		jmiShowOH = new JMenuItem("Show Player 2's Collection");
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
		jmiViewLibrary = new JMenuItem("View Current Player's Library");
		jmiViewLibrary.addActionListener(this);
		jmiShowTPL = new JMenuItem("Turn Player");
		jmiShowTPL.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.SHIFT_MASK));
		jmiShowTPL.addActionListener(this);
		//jmiPref = new JMenuItem("Preferences..."); /* Not needed */
		//jmiPref.addActionListener(this);
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
		jmiRestart = new JMenuItem("Start/Restart Game");
		jmiRestart.addActionListener(this);
		jmiShowEditor = new JMenuItem("Deck Editor...");
		jmiShowEditor.addActionListener(
			evt -> { new DeckEditor(); }
		);
		
		JMenuItem jmiRot = new JMenuItem("Rotate selected");
		jmiRot.addActionListener(
			e -> {
				CLabel selLbl = cl.getSelectedLabel();
				cl.updateEnergyStatus(selLbl);
			}
		);
		jmiRot.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
		
		
		jmHelp.add(jmiHelp);
		jmHelp.add(jmiAbout);
		jmGame.add(jmiRestart);
		jmGame.add(jmiShowEditor);
		jmGame.add(jmiEndTurn);
		jmGame.add(jmiShowTPL);
		jmGame.add(jmiExit);
		jmAct.add(jmiShowMH);
		jmAct.add(jmiShowOH);
		jmAct.add(jmiViewLibrary);
		jmAct.add(jmiViewRecentCard);
		for (int i = 0; i < jmiShowR.length; i++) {
			jmAct.add(jmiShowR[i]);
		}
		
		JMenu jmCard = new JMenu("Card");
		jmCard.add(jmiRot);
		
		jmb.add(jmGame);
		jmb.add(jmAct);
		jmb.add(jmCard);
		jmb.add(jmHelp);
		this.setJMenuBar(jmb);

		//Creating the JLabels and JPanels
		jplMB = new JPanel();
		jplOB = new JPanel();
		jlbMD = new JLabel(imLibrary);
		jlbOD = new JLabel(imLibrary);
		jlbMG = new JLabel(imGrave);
		jlbOG = new JLabel(imGrave);
		jlbMD.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		jlbOD.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		jlbMG.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		jlbOG.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		for (int i = 0; i < 10; i++) {
			jlbMS[i+1] = new CLabel(imNoCard);
			jlbOS[i+1] = new CLabel(imNoCard);
			jlbOS[i+1].setOp(true);
		}

		//Adding MouseListeners
		MouseAdapter libAdapter = new MouseAdapter() {
			public void mousePressed(MouseEvent mev) {
				if (mev.isPopupTrigger()) {
					deckMenu.show(mev.getComponent(), mev.getX(), mev.getY());
				}
			}
		};
		
		MouseAdapter graveAdapter = new MouseAdapter() {
			public void mousePressed(MouseEvent mev) {
				if (mev.isPopupTrigger()) {
					graveMenu.show(mev.getComponent(), mev.getX(), mev.getY());
				}
			}
		};
		jlbOD.addMouseListener(libAdapter);
		jlbMD.addMouseListener(libAdapter);
		jlbMG.addMouseListener(graveAdapter);
		jlbOG.addMouseListener(graveAdapter);

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

		//Setting Tooltip texts from jlbMD, jlbOD, jlbMG and jlbOG
		jlbMD.setToolTipText("Player 1's Library");
		jlbOD.setToolTipText("Player 2's Library");
		jlbMG.setToolTipText("Player 1's Cemetery");
		jlbOG.setToolTipText("Player 2's Cemetery");
		
		//creating Energy Zone JLabels
		for(int i = 0; i <= 12; i++) {
			jlbMM[i+1] = new CLabel(true);
			jlbMM[i+1].addMouseListener(cl);
			jlbMM[i+1].addMouseWheelListener(cl);
			jlbOM[i+1] = new CLabel(true);
			jlbOM[i+1].setOp(true);
			jlbOM[i+1].addMouseListener(cl);
			jlbOM[i+1].addMouseWheelListener(cl);
			jlbMB[i+1] = new CLabel();
			jlbMB[i+1].addMouseListener(cl);
			jlbMB[i+1].addMouseWheelListener(cl);
			jlbOB[i+1] = new CLabel();
			jlbOB[i+1].setOp(true);
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
		for (int i = 1; i <= 10; i++) {
			mhGroup.addGroup(gl.createParallelGroup()
					.addComponent(jlbMB[i])
					.addComponent(jlbMS[11-i])
					.addComponent(jlbMM[i]));
		}
		mhGroup.addGroup(gl.createParallelGroup()
				.addComponent(jlbMB[11])
				.addComponent(jlbMD)
				.addComponent(jlbMM[11]));
		mhGroup.addGroup(gl.createParallelGroup()
				.addComponent(jlbMB[12])
				.addComponent(jlbMG)
				.addComponent(jlbMM[12]));
		gl.setHorizontalGroup(mhGroup);

		mvGroup.addGroup(gl.createParallelGroup()
				.addComponent(jlbMB[1])
				.addComponent(jlbMB[2])
				.addComponent(jlbMB[3])
				.addComponent(jlbMB[4])
				.addComponent(jlbMB[5])
				.addComponent(jlbMB[6])
				.addComponent(jlbMB[7])
				.addComponent(jlbMB[8])
				.addComponent(jlbMB[9])
				.addComponent(jlbMB[10])
				.addComponent(jlbMB[11])
				.addComponent(jlbMB[12])
				);
		
		ParallelGroup shGroup = gl.createParallelGroup();
		for (int j = 0; j < 10; j++) {
			// added in reverse order, so 1 is at the right hand side
			shGroup.addComponent(jlbMS[10-j]);
		}
		shGroup.addComponent(jlbMD);
		shGroup.addComponent(jlbMG);
		mvGroup.addGroup(shGroup);
		
		mvGroup.addGroup(gl.createParallelGroup()
				.addComponent(jlbMM[1])
				.addComponent(jlbMM[2])
				.addComponent(jlbMM[3])
				.addComponent(jlbMM[4])
				.addComponent(jlbMM[5])
				.addComponent(jlbMM[6])
				.addComponent(jlbMM[7])
				.addComponent(jlbMM[8])
				.addComponent(jlbMM[9])
				.addComponent(jlbMM[10])
				.addComponent(jlbMM[11])
				.addComponent(jlbMM[12])
				);
		//mvGroup.addComponent(bar1);

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
		//ohGroup.addComponent(bar2);
		gl2.setHorizontalGroup(ohGroup);

		//ovGroup.addComponent(bar2);
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
		
		//
		JPanel jplMB2, jplOB2;
		jplMB2 = new JPanel();
		jplOB2 = new JPanel();
		jplMB2.setLayout(new BoxLayout(jplMB2, BoxLayout.X_AXIS));
		jplOB2.setLayout(new BoxLayout(jplOB2, BoxLayout.X_AXIS));

		jplMB2.add(jplMB);
		jplMB2.add(Box.createRigidArea(new Dimension(10, 0)));
		jplMB2.add(bar1);

		jplOB2.add(jplOB);
		jplOB2.add(Box.createRigidArea(new Dimension(10, 0)));
		jplOB2.add(bar2);

		//Adding the JPanels
		JScrollPane scrMe, scrOp;
		scrMe = new JScrollPane(jplMB2,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrOp = new JScrollPane(jplOB2,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		statusBar = new JLabel();
		Border b1 = BorderFactory.createLineBorder(Color.DARK_GRAY, 3);
		Border b2 = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border mainBorder = BorderFactory.createCompoundBorder(
								BorderFactory.createCompoundBorder(b2, b1), b2);
		statusBar.setBorder(mainBorder);
		statusBar.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		bc.setLayout(new BorderLayout());
		bc.add(scrOp, BorderLayout.NORTH);
		bc.add(statusBar, BorderLayout.CENTER);
		bc.add(scrMe, BorderLayout.SOUTH);
		
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
		 
	}
	
	public static PRPlayer newInstance() {
		if (bf != null) {
			return bf;
		} else {
			bf = new PRPlayer();
			return bf;
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						// Load preference and set laf
						// Default
						UIManager.put("ProgressBar.horizontalSize", new Dimension(100, 20));
						FlatArcOrangeIJTheme.setup();
						
						restart();
						
						if (args.length > 0) {
							for (int i = 0; i < args.length; i++) {
								Infofield info = new Infofield(cl);
								info.setDeck(args[i]);
								info.setVisible(true);
							}
							if (args.length == 2) {
								PRPlayer.pif.setDeck1Path(args[0]);
								PRPlayer.pif.setDeck2Path(args[1]);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	
	public static void restart() {
		// Cleanup everything
		if (PRPlayer.bf != null) {
			PRPlayer.bf.cleanup();
		}
		
		PRPlayer.bf = PRPlayer.newInstance();
		//Initializing Preferences JInternalFrame
		pif = new PrefIFrame(PRPlayer.bf);
		pif.btnCancel.setEnabled(false);
		pif.setVisible(true);
	}
	
	private void cleanup() {
		MG.hide();
		OG.hide();
		for (CWindow win : MRA) {
			win.hide();
		}
		for (CWindow win : ORA) {
			win.hide();
		}
		PRPlayer.bf.setVisible(false);
		h1.setVisible(false);
		h2.setVisible(false);
		
		PRPlayer.bf = null;
	}

	public void proceed(int i, int j, int b) {
		//Initializing Infofield
        inf = new Infofield(cl);
        mi = new Infofield(cl);
        oi = new Infofield(cl);

        mi.setDeck(deck1);
        oi.setDeck(deck2);

		exWin1 = new CWindow("Extra Deck, Player 1", cl);
		for (Card exCard : deck1.getExtraDeck()) {
			exWin1.add(exCard);
		}

		exWin2 = new CWindow("Extra Deck, Player 2", cl);
		for (Card exCard : deck2.getExtraDeck()) {
			exWin2.add(exCard);
		}
		
		for (int i1 = 0; i1 < new Random().nextInt(60); i1++) {
			//Preparing
			deck1 = shuffle(deck1);
			deck2 = shuffle(deck2);
		}

		if (pif.isShields()) {
			//Adding Shields, if any
			for (int k = 0; k < i; k++) {
				addToShield(deck1.pop(), k + 1, false);
				addToShield(deck2.pop(), k + 1, true);
			}
		}
		
		if (pif.isDraw()) {
			//Drawing, if any
			if (!(deck1.isEmpty() && deck2.isEmpty())) {
				for (int k = 0; k < j; k++) {
					draw(true, true);
					draw(false, true);
				}
			}
		}
		
		// Setting life total, if needed.
		h1.setBPoints(b);
		h2.setBPoints(b);
		
		// Starting 1st player's turn.
		h1.showHand();
		opturn = false;
		statusBar.setText("Initial hands drawn. 1st players's turn");
	}
	
	public static Deck shuffle(Deck d) {
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
		Deck d2 = new Deck(s);
		if (d.hasExtras()) {
			for (Card c : d.getExtraDeck()) {
				d2.addAsExtra(c);
			}
		}
		return d2;
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
	
	public void draw(boolean opt, boolean initialDraw) {
		// Draw Phase
		
		String player;
		
		if (opt) {
			h2.add(deck2.pop());
			player = "2nd Player";
		} else {
			h1.add(deck1.pop());
			player = "1st Player";
		}
		
		if (!initialDraw) {
			statusBar.setText(player + " - summon phase");
		} else {
			statusBar.setText(player + " - drawing intitial hand");
		}
	}

	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == jmiExit) {
			System.exit(0);
			
		} else if(ae.getSource() == jmiEndTurn) {
			// Refreshing phase
			
			String player;
			if (opturn) {
				opturn = false;
				player = "1st Player";
				
				h2.setVisible(false);
				h1.setVisible(true);
				
				// Auto-refreshing cards to unused state
				for (CLabel lbl : jlbMB) {
					if ((lbl != null) && lbl.isUsed()) {
						lbl.setUsed(false);
						lbl.rot_0();
					}
				}
				
				for (CLabel lbl : jlbMM) {
					if ((lbl != null) && lbl.isUsed()) {
						lbl.setUsed(false);
						lbl.rot_0();
					}
				}
				
				cl.bar1.refresh();
				
			} else {
				opturn = true;
				player = "2nd Player";
				
				h2.setVisible(true);
				h1.setVisible(false);
				
				
				// Auto-refreshing cards to unused state
				for (CLabel lbl : jlbOB) {
					if ((lbl != null) && lbl.isUsed()) {
						lbl.setUsed(false);
						lbl.rot_0();
					}
				}
				
				for (CLabel lbl : jlbOM) {
					if ((lbl != null) && lbl.isUsed()) {
						lbl.setUsed(false);
						lbl.rot_0();
					}
				}
				
				cl.bar2.refresh();
				
				
			}
			// To draw phase
			statusBar.setText(player + " - draw phase");
			
			//JOptionPane.showMessageDialog(this, "The current player is : " + player,
			//		"Turn Ended", JOptionPane.INFORMATION_MESSAGE);
		} else if(ae.getSource() == jpiShuffle) {
			if (opturn) {
				shuffle(deck2);
			} else {
				shuffle(deck1);
			}
		} else if(ae.getSource() == jpiDraw) {
			draw(opturn, false);
			
		} else if(ae.getSource() == jpiDrawMult) {
			int count = Integer.parseInt(JOptionPane.showInputDialog("How many cards?"));
			for (int i = 0; i < count; i++) {
				draw(opturn, false);
			}
			
		} else if(ae.getSource() == jpiSearch) {
			if (inf != null) {
				if (deckMenu.getInvoker() == jlbOD) {
					inf.setDeck(deck2);
				} else if (deckMenu.getInvoker() == jlbMD) {
					inf.setDeck(deck1);
				}
				inf.setVisible(true);
				//Needs to be modified
			} else {
				System.err.println("No deck loaded!");
			}
		} else if(ae.getSource() == jpiViewExtra) {
			if (inf != null) {
				if (deckMenu.getInvoker() == jlbOD) {
					if (deck2.hasExtras()) {
						exWin2.show();
					} else {
						System.out.println("No extras found. Disabling option.");
						jpiViewExtra.setEnabled(false);
					}
				} else if (deckMenu.getInvoker() == jlbMD) {
					if (deck1.hasExtras()) {
						exWin1.show();
					} else {
						System.out.println("No extras found. Disabling option.");
						jpiViewExtra.setEnabled(false);
					}
				}
			} else {
				System.err.println("No deck loaded!");
			}
		} else if(ae.getSource() == jpiView) {
			if (graveMenu.getInvoker() == jlbOG) {
				OG.show();
			} else if(graveMenu.getInvoker() == jlbMG) {
				MG.show();
			}
		} else if(ae.getSource() == jpiFlip) {
			CLabel label = (CLabel) cardMenu.getInvoker();
			label.flip();
		} else if(ae.getSource() == jpiViewCard) {
			Component lbl = cardMenu.getInvoker();
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
			lblCard.setIcon(ImageUtils.scale(lblCard.getCard().getImCard(), 350));
			lblCard.setMinimumSize(
					new Dimension(350, lblCard.getIcon().getIconHeight()+5));
			lblCard.setToolTipText("");
			
			viewer = new JFrame("Card Viewer");
			
			JPanel pnlView = new JPanel();
			pnlView.setLayout(new GridLayout(1, 2, 5, 5));
			
			JTextPane txtView = new JTextPane();
			txtView.setContentType("text/html");
			txtView.setText(lblCard.getCard().createHtmlInfo());
			JScrollPane viewPane = new JScrollPane(
					txtView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			viewPane.setMaximumSize(new Dimension(300, 500));
			
			pnlView.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			pnlView.add(viewPane);
			pnlView.add(lblCard);
			
			viewer.setContentPane(pnlView);
			viewer.setLocation(200, 100);
			viewer.setSize(800, 500);
			viewer.setResizable(false);
			viewer.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			viewer.setVisible(true);
		
		} else if(ae.getSource() == jpiFlipVert) {
			CLabel lbl = (CLabel) cardMenu.getInvoker();
			lbl.rot_180();
		} else if(ae.getSource() == jpiRot270) {
			CLabel lbl = (CLabel) cardMenu.getInvoker();
			lbl.rot_270();
		} else if(ae.getSource() == jpiLink) {
			CLabel lbl = (CLabel) cardMenu.getInvoker();
			Card c = lbl.grCard();
			if ((c != null) && (c.getParts().size() > 0)) {
				// TODO multi (2+) sided support
				lbl.setCard(c.getParts().get(0));
			}
		} else if(ae.getSource() == jpiToLibrary) {
			CLabel lbl = (CLabel) cardMenu.getInvoker();
			Card card = lbl.grCard();
			oi.setVisible(false);
			mi.setVisible(false);
			inf.setVisible(false);
			if (card != null) {
				if (opturn) {
					deck2.addFirst(card);
				} else {
					deck1.addFirst(card);
				} 
			}
		} else if(ae.getSource() == jpiToLibBottom) {
			CLabel lbl = (CLabel) cardMenu.getInvoker();
			Card card = lbl.grCard();
			oi.setVisible(false);
			mi.setVisible(false);
			inf.setVisible(false);
			if (card != null) {
				if (opturn) {
					deck2.addLast(card);
				} else {
					deck1.addLast(card);
				} 
			}
			
		} else if(ae.getSource() == jpiToGrave) {
			CLabel lbl = (CLabel) cardMenu.getInvoker();
			Card card = lbl.grCard();
			oi.setVisible(false);
			mi.setVisible(false);
			inf.setVisible(false);
			if (card != null) {
				if (opturn) {
					OG.add(card);;
				} else {
					MG.add(card);
				} 
			}
			
		} else if(ae.getSource() == jpiSummon) {
			CLabel lbl = (CLabel) cardMenu.getInvoker();
			Card card = lbl.grCard();
			
			// Logic to be refined later.
			int available_energy;
			if (opturn) {
				available_energy = bar2.getAvailable();
			} else {
				available_energy = bar1.getAvailable();
			}
			
			Integer[] energy = card.energy;
			int required_energy = 0; 
			for(Integer en : energy) {
				required_energy += en;
			}
			
			if (available_energy < required_energy) {
				//Can't summon
				System.err.println("Not enough energy for summoning!");
				lbl.setCard(card);
			} else {
				//Summon
				int summon_id = 1;
				boolean summoned = false;
				while (!summoned) {
					if (opturn) {
						if (jlbOB[summon_id].getCard() == null) {
							bar2.use(required_energy);
							jlbOB[summon_id].setCard(card);
							summoned = true;
							System.out.println("Called " + card.name + " at pos : " + summon_id);
						} else {
							summon_id++;
						}
					} else {
						if (jlbMB[summon_id].getCard() == null) {
							
							bar1.use(required_energy);
							jlbMB[summon_id].setCard(card);
							summoned = true;
							System.out.println("Called " + card.name + " at pos : " + summon_id);
							
						} else {
							summon_id++;
						}
					}
				}
			}
			
		} else if(ae.getSource() == jpiToBottom) {
			Card card;
			if (opturn) {
				card = deck2.removeFirst();
				deck2.addLast(card);
				//inf.setCard(deck2.getFirst());
			} else {
				card = deck1.removeFirst();
				deck1.addLast(card);
				//inf.setCard(deck1.getFirst());
			}
		} else if(ae.getSource() == jpiRemove) {
			CLabel lbl = (CLabel) cardMenu.getInvoker();
			Container cont = lbl.getParent();
//			if (opturn) {
//				h2.remove(lbl);
//			} else {
//				h1.remove(lbl);
//			}
			cont.remove(lbl);
			SwingUtilities.updateComponentTreeUI(cont);
		} else if(ae.getSource() == jpiLook) {
			int count = Integer.parseInt(JOptionPane.showInputDialog("How many cards?"));
			CWindow cards = new CWindow("Looked cards", cl);
			for (int i = 0; i < count; i++) {
				if(opturn) {
					cards.add(deck2.pop());
				} else {
					cards.add(deck1.pop());
				}
			}
			cards.show();
			
		// Normal Menus
		} else if(ae.getSource() == jmiShowMH) {
			if (!h1.isVisible()) {
				h1.setVisible(true);
			}
		} else if(ae.getSource() == jmiShowOH) {
			if (!h2.isVisible()) {
				h2.setVisible(true);
			}
		} else if(ae.getSource() == jmiShowR[0]) {
			if (opturn) {
				ORA[0].show();
			} else {
				MRA[0].show();
			}
		} else if(ae.getSource() == jmiShowR[1]) {
			if (opturn) {
				ORA[1].show();
			} else {
				MRA[1].show();
			}
		} else if(ae.getSource() == jmiShowR[2]) {
			if (opturn) {
				ORA[2].show();
			} else {
				MRA[2].show();
			}
		} else if(ae.getSource() == jmiShowR[3]) {
			if (opturn) {
				ORA[3].show();
			} else {
				MRA[3].show();
			}
		
		} else if(ae.getSource() == jmiViewLibrary) {
			if (opturn) {
				if (oi.isVisible() == false) {
					oi.setVisible(true);
				}
			} else {
				if (mi.isVisible() == false) {
					mi.setVisible(true);
				}
			}
		
		} else if(ae.getSource() == jmiShowTPL) {
			String plname;
			if (opturn) {
				plname = "2nd Player - Opponent";
			} else {
				plname = "1st Player - Me";
			}
			JOptionPane.showMessageDialog(this, "The current player is : " + plname,
					"Current Player", JOptionPane.INFORMATION_MESSAGE);
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
			message = "PR Player"
					+ "\n"
					+ "Copyright 2014-2023 Subhraman Sarkar\n\n"
					+ "This program is free software; you can redistribute it and/or modify"
					+ "\n it under the terms of the GNU General Public License as published by"
					+ "\n  the Free Software Foundation; either version 2 of the License, or"
					+ "\n  (at your option) any later version."
					+ "\n  "
					+ "\n  This program is distributed in the hope that it will be useful,"
					+ "\n  but WITHOUT ANY WARRANTY; without even the implied warranty of"
					+ "\n  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the"
					+ "\n  GNU General Public License for more details."
					+ "\n "
					+ "\n  You should have received a copy of the GNU General Public License"
					+ "\n  along with this program; if not, write to the Free Software"
					+ "\n Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,"
					+ "\n MA 02110-1301, USA.";
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
							jtext.setPage(this.getClass().getResource("/doc/LICENCE"));
							jbLicence.setEnabled(false);
						} catch (IOException e) {
							e.printStackTrace();
						}
				}				
			});
			aboutFrame.getContentPane().add(jbLicence, BorderLayout.SOUTH);
			aboutFrame.setSize(new Dimension(500, 500));
			aboutFrame.setVisible(true);
		} else if(ae.getSource() == jmiRestart) {
			restart();
		}
	}
	
	public void setNimbusLF() {
		for (LookAndFeelInfo lafinf : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(lafinf.getName())) {
				try {
					UIManager.setLookAndFeel(lafinf.getClassName());
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
					setMetalLF();
				}
				SwingUtilities.updateComponentTreeUI(this);
			}
		}
	}
	
	public void setMetalLF() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void setSystemLF() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
			setMetalLF();
		}
		SwingUtilities.updateComponentTreeUI(this);
	}

	
	@Override
	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_R) {
//			System.out.println("Has focus!");
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

