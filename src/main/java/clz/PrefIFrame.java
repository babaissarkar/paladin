/*
 *      PrefIFrame.java
 *      
 *      Copyright 2016 Subhraman Sarkar <subhraman@subhraman-Inspiron>
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


package clz;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.FileSystems;
import java.util.prefs.Preferences;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class PrefIFrame extends JFrame implements ActionListener {
	private boolean shields;
	private boolean draw;
	
	// GUI Components
	public JButton btnCancel;
	private JButton btnOk, btnBrowse, btnBrowse2;
	private final JCheckBox chckbxShields;
	private final JCheckBox chckbxDraw;
	private final JTextField txtDk1;
	private final JTextField txtDk2;
	private final JTextField textField_2;
	private final JTextField textField_1;
	private final JFileChooser files;
	private final JSpinner spinBPoints;
	private JRadioButton tglbtnSystem, tglbtnNimbus, tglbtnMetal;
	private int themeNo;
	
	public PRPlayer btf;

	/**
	 * Create the frame.
	 */
	public PrefIFrame(PRPlayer b) {
		btf = b;
		shields = false;
		draw = true;
		files = new JFileChooser();
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		getContentPane().setBackground(Color.WHITE);
		
		// Load Preferences
		Preferences prefs = Preferences.userNodeForPackage(PrefIFrame.class);
		themeNo = prefs.getInt("theme", 1);
		
		// Deck 1 selection
		JLabel lblDeck = new JLabel("Deck 1 :");
		lblDeck.setFont(new Font("DejaVu Serif", Font.PLAIN, 13));

		String sep = FileSystems.getDefault().getSeparator();

		txtDk1 = new JTextField();
		String deck1Path = PRPlayer.userhome + ".PRPlayer" + sep + "decks" + sep + "deck1.txt";
		String path = prefs.get("deck1", deck1Path);
		txtDk1.setText(path);
		txtDk1.setColumns(30);
		
		btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(this);
		
		JPanel pnlDeck1 = new JPanel();
		pnlDeck1.setLayout(new FlowLayout());
		pnlDeck1.add(lblDeck);
		pnlDeck1.add(txtDk1);
		pnlDeck1.add(btnBrowse);
		
		// Deck2 selection
		JLabel lblDeck_1 = new JLabel("Deck 2 :");
		lblDeck_1.setFont(new Font("DejaVu Serif", Font.PLAIN, 13));
		
		txtDk2 = new JTextField();
		String deck2Path = PRPlayer.userhome + ".PRPlayer" + sep + "decks" + sep + "deck2.txt";
		String path2 = prefs.get("deck2", deck1Path);
		txtDk2.setText(path2);
		txtDk2.setColumns(30);
		
		btnBrowse2 = new JButton("Browse...");
		btnBrowse2.addActionListener(this);
		
		JPanel pnlDeck2 = new JPanel();
		pnlDeck2.setLayout(new FlowLayout());
		pnlDeck2.add(lblDeck_1);
		pnlDeck2.add(txtDk2);
		pnlDeck2.add(btnBrowse2);
		
		// Draw area
		Box hboxDraw = Box.createHorizontalBox();
		
		chckbxDraw = new JCheckBox("Draw");
		chckbxDraw.addActionListener(this);
		chckbxDraw.setSelected(true);
		hboxDraw.add(chckbxDraw);
		
		textField_2 = new JTextField();
		textField_2.setText(""+prefs.getInt("draw", 6));
		if(textField_2.getText().equals("")) {
			textField_2.setEnabled(false);
			chckbxDraw.setSelected(false);
			draw = false;
		} else {
			textField_2.setEnabled(true);
			chckbxDraw.setSelected(true);
			draw = true;
		}
		
		textField_2.setColumns(2);
		hboxDraw.add(textField_2);
		
		// Barrier points selection
		JLabel lblBarrierPoints = new JLabel("Barrier Points");
		spinBPoints = new JSpinner();
		spinBPoints.setModel(
				new SpinnerNumberModel(7, null, null, 1));
		((JSpinner.DefaultEditor) spinBPoints.getEditor()).getTextField().setColumns(4);
		spinBPoints.setValue(prefs.getInt("bp", 7));
		
		Box hboxBarrier = Box.createHorizontalBox();
		hboxBarrier.add(lblBarrierPoints);
		hboxBarrier.add(spinBPoints);
		
		// Shields selection
		Box hboxFCards = Box.createHorizontalBox();
		chckbxShields = new JCheckBox("Flipped Cards");
		chckbxShields.addActionListener(this);
		hboxFCards.add(chckbxShields);
		
		textField_1 = new JTextField();
		int flipped = prefs.getInt("flipped", 0);
		if(flipped == 0) {
			textField_1.setText("");
			textField_1.setEnabled(false);
			chckbxShields.setSelected(false);
			shields = false;
		} else {
			textField_1.setText("" + flipped);
			textField_1.setEnabled(true);
			chckbxShields.setSelected(true);
			shields = true;
		}
		
		textField_1.setColumns(2);
		//textField_1.setEnabled(false);
		hboxFCards.add(textField_1);
		
		// Parameters selection panel
		JPanel pnlParams = new JPanel();
		pnlParams.setLayout(new FlowLayout());
		pnlParams.add(hboxDraw);
		pnlParams.add(hboxFCards);
		pnlParams.add(hboxBarrier);
		
		// Ok and Cancel Buttons
		btnOk = new JButton("OK");
		btnOk.addActionListener(this);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		
		JPanel pnlOkCancel = new JPanel();
		pnlOkCancel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pnlOkCancel.add(btnOk);
		pnlOkCancel.add(btnCancel);
		
		// Look and Feel selection
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBorder(new TitledBorder(
				new MatteBorder(2, 2, 2, 2, new Color(0, 255, 255)), "Look and Feel", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));


		tglbtnMetal = new JRadioButton("Metal");
		tglbtnMetal.addActionListener(arg0 -> {
			themeNo = 3; // Metal laf : number 3
			btf.setMetalLF();
			SwingUtilities.updateComponentTreeUI(this);
		});
		horizontalBox.add(tglbtnMetal);

		Component horizontalStrut = Box.createHorizontalStrut(120);
		horizontalBox.add(horizontalStrut);

		tglbtnSystem = new JRadioButton("System");
		tglbtnSystem.addActionListener(arg0 -> {
			themeNo = 1; // System laf : number 3
			btf.setSystemLF();
			SwingUtilities.updateComponentTreeUI(this);
		});

		horizontalBox.add(tglbtnSystem);

		Component horizontalStrut_1 = Box.createHorizontalStrut(120);
		horizontalBox.add(horizontalStrut_1);

		tglbtnNimbus = new JRadioButton("Nimbus");
		tglbtnNimbus.addActionListener(arg0 -> {
			themeNo = 2; // Nimbus laf : number 3
			btf.setNimbusLF();
			SwingUtilities.updateComponentTreeUI(this);
		});
		horizontalBox.add(tglbtnNimbus);

		ButtonGroup lafButtons = new ButtonGroup();
		lafButtons.add(tglbtnMetal);
		lafButtons.add(tglbtnSystem);
		lafButtons.add(tglbtnNimbus);
		
		switch(themeNo) {
		case 2 :
			tglbtnNimbus.setSelected(true);
			break;
		case 3 :
			tglbtnMetal.setSelected(true);
			break;
		default :
			tglbtnSystem.setSelected(true);
			break;
		}
		
		// Set overall window layout manager
		getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		// Final Layouting
		JPanel pnlDeckSelection = new JPanel();
		pnlDeckSelection.setLayout(new BoxLayout(pnlDeckSelection, BoxLayout.PAGE_AXIS));
		pnlDeckSelection.add(pnlDeck1);
		pnlDeckSelection.add(pnlDeck2);
		
		getContentPane().add(pnlDeckSelection);
		getContentPane().add(pnlParams);
		getContentPane().add(horizontalBox);
		getContentPane().add(pnlOkCancel);
		
		pack();
		setTitle("Preferences");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLocation(200, 200);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnCancel) {
			this.setVisible(false);
		} else if (arg0.getSource() == btnOk) {
			//PRPlayer.restart();
			//Save Preferences
			Preferences prPref = Preferences.userNodeForPackage(PrefIFrame.class);
			prPref.put("deck1", txtDk1.getText());
			prPref.put("deck2", txtDk2.getText());
			prPref.putInt("flipped", Integer.parseInt(textField_1.getText()));
			prPref.putInt("draw", Integer.parseInt(textField_2.getText()));
			prPref.putInt("bp", Integer.parseInt(spinBPoints.getValue().toString()));	
			prPref.putInt("theme", themeNo);
			
			//Setup game
			setDeck();
			proceed();
		} else if (arg0.getSource() == btnBrowse) {
			files.showOpenDialog(this);
			if (files.getSelectedFile() != null) {
				txtDk1.setText(files.getSelectedFile().getAbsolutePath());
			}
		} else if (arg0.getSource() == btnBrowse2) {
			files.showOpenDialog(this);
			if (files.getSelectedFile() != null) {
				txtDk2.setText(files.getSelectedFile().getAbsolutePath());
			}
		} else if(arg0.getSource() == chckbxShields) {
			textField_1.setEnabled(chckbxShields.isSelected());
			shields = chckbxShields.isSelected();
		} else if(arg0.getSource() == chckbxDraw) {
			textField_2.setEnabled(chckbxDraw.isSelected());
			draw = chckbxDraw.isSelected();
		}
		
	}

	public void proceed() {
		this.setVisible(false);
		this.btnCancel.setEnabled(true);
		int i = 0, j = 0, k;
		if (!textField_1.getText().equals("")) {
			i = Integer.parseInt(textField_1.getText());
		}
		if (!textField_2.getText().equals("")) {
			j = Integer.parseInt(textField_2.getText());
		}
		k = Integer.parseInt(spinBPoints.getValue().toString());
		if (k > 0) {
			btf.proceed(i, j, k);
		} else {
			btf.proceed(i, j, 7);
		}
	}

	public final boolean isDraw() {
		return this.draw;
	}

	public final boolean isShields() {
		return this.shields;
	}

	public void setDeck() {
		String deck1path = txtDk1.getText();
		String deck2path = txtDk2.getText();
		CScan cs = new CScan();

		String[] options = {"Yes", "No"};
		if (deck1path.endsWith(".zip")) {
			int ans = JOptionPane.showOptionDialog(null,
					"Does the Zip file has metadata?",
					"Select Type",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[0]);
			if (ans == JOptionPane.YES_OPTION) {
				btf.deck1 = cs.scanZipFileWithXML(new File(deck1path));
			} else {
				btf.deck1 = cs.scanZipFileWithoutXML(new File(deck1path));
			}
		} else if (deck1path.endsWith(".txt")) {
			btf.deck1 = new Deck(cs.scanAllAtributes(deck1path));
		}
		
		if (deck2path.endsWith(".zip")) {
			int ans = JOptionPane.showOptionDialog(null,
					"Does the Zip file has metadata?",
					"Select Type",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[0]);
			if (ans == JOptionPane.YES_OPTION) {
				btf.deck2 = cs.scanZipFileWithXML(new File(deck2path));
			} else {
				btf.deck2 = cs.scanZipFileWithoutXML(new File(deck2path));
			}
		} else if (deck1path.endsWith(".txt")) {
			btf.deck2 = new Deck(cs.scanAllAtributes(deck2path));
		}
	}
	
	public void setDeck1Path(String path) {
		txtDk1.setText(path);
	}
	public void setDeck2Path(String path) {
		txtDk2.setText(path);
	}
}

