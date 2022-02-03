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

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import java.awt.Font;

import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Component;
import java.io.File;
import java.nio.file.FileSystems;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class PrefIFrame extends JFrame implements ActionListener {
	private boolean shields;
	private boolean draw;
	
	// GUI Components
	public JButton btnCancel, btnOk, btnBrowse, btnBrowse2;
	private final JCheckBox chckbxShields;
	private final JCheckBox chckbxDraw;
	private final JTextField txtDk1;
	private final JTextField txtDk2;
	private final JTextField textField_2;
	private final JTextField textField_1;
	private final JFileChooser files;
	private final JSpinner spinBPoints;
	
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
		
		JLabel lblDeck = new JLabel("Deck 1 :");
		lblDeck.setFont(new Font("DejaVu Serif", Font.PLAIN, 13));

		String sep = FileSystems.getDefault().getSeparator();

		txtDk1 = new JTextField();
		txtDk1.setText(PRPlayer.userhome + sep + ".PRPlayer" + sep + "decks" + sep + "deck1.txt");
		txtDk1.setColumns(10);
		
		btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(this);
		
		JLabel lblDeck_1 = new JLabel("Deck 2 :");
		lblDeck_1.setFont(new Font("DejaVu Serif", Font.PLAIN, 13));
		
		txtDk2 = new JTextField();
		txtDk2.setText(PRPlayer.userhome + sep + ".PRPlayer" + sep + "decks" + sep + "deck2.txt");
		txtDk2.setColumns(10);
		
		btnBrowse2 = new JButton("Browse...");
		btnBrowse2.addActionListener(this);
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(this);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		
		Box hboxFCards = Box.createHorizontalBox();
		
		Box hboxDraw = Box.createHorizontalBox();
		
		chckbxDraw = new JCheckBox("Draw");
		chckbxDraw.addActionListener(this);
		chckbxDraw.setSelected(true);
		hboxDraw.add(chckbxDraw);
		
		textField_2 = new JTextField("6");
		hboxDraw.add(textField_2);
		
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBorder(new TitledBorder(
				new MatteBorder(2, 2, 2, 2, new Color(0, 255, 255)), "Look and Feel", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		
		spinBPoints = new JSpinner();
		spinBPoints.setModel(
				new SpinnerNumberModel(7, null, null, 1));
		
		JLabel lblBarrierPoints = new JLabel("Barrier Points");
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblDeck, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtDk1, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(btnBrowse))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblDeck_1, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(txtDk2, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(btnBrowse2, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(109, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(24)
					.addComponent(btnOk)
					.addPreferredGap(ComponentPlacement.RELATED, 400, Short.MAX_VALUE)
					.addComponent(btnCancel)
					.addGap(22))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(hboxFCards, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(hboxDraw, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblBarrierPoints)
					.addGap(4)
					.addComponent(spinBPoints, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(71, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(16, Short.MAX_VALUE)
					.addComponent(horizontalBox, GroupLayout.PREFERRED_SIZE, 553, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDeck, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtDk1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBrowse))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDeck_1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(5)
							.addComponent(txtDk2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(btnBrowse2)))
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(hboxFCards, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(hboxDraw, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblBarrierPoints)
								.addComponent(spinBPoints, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))))
					.addGap(18)
					.addComponent(horizontalBox, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addGap(90)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnOk)
						.addComponent(btnCancel))
					.addGap(25))
		);
		
		JButton tglbtnMetal = new JButton("Metal");
		tglbtnMetal.addActionListener(arg0 -> btf.setMetalLF());
		horizontalBox.add(tglbtnMetal);

		Component horizontalStrut = Box.createHorizontalStrut(120);
		horizontalBox.add(horizontalStrut);

		JButton tglbtnSystem = new JButton("System");
		tglbtnSystem.addActionListener(arg0 -> btf.setSystemLF());
		
		horizontalBox.add(tglbtnSystem);

		Component horizontalStrut_1 = Box.createHorizontalStrut(120);
		horizontalBox.add(horizontalStrut_1);

		JButton tglbtnNimbus = new JButton("Nimbus");
		tglbtnNimbus.addActionListener(arg0 -> btf.setNimbusLF());
		horizontalBox.add(tglbtnNimbus);
		
		chckbxShields = new JCheckBox("Flipped Cards");
		chckbxShields.addActionListener(this);
		hboxFCards.add(chckbxShields);
		
		textField_1 = new JTextField();
		textField_1.setEnabled(false);
		hboxFCards.add(textField_1);
		
		getContentPane().setLayout(groupLayout);
		setTitle("Preferences");
		setBounds(100, 100, 587, 375);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnCancel) {
			this.setVisible(false);
		} else if (arg0.getSource() == btnOk) {
			//PRPlayer.restart();
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
		String deck1 = txtDk1.getText();
		String deck2 = txtDk2.getText();
		CScan cs = new CScan();

		String[] options = {"Yes", "No"};
		if (deck1.endsWith(".zip")) {
			int ans = JOptionPane.showOptionDialog(null,
					"Does the Zip file has metadata?",
					"Select Type",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[0]);
			if (ans == JOptionPane.YES_OPTION) {
				btf.deck1 = new Deck(cs.scanZipFileWithXML(new File(deck1)));
			} else {
				btf.deck1 = new Deck(cs.scanZipFileWithoutXML(new File(deck1)));
			}
		} else if (deck1.endsWith(".txt")) {
			btf.deck1 = new Deck(cs.scanAllAtributes(deck1));
		}
		
		if (deck2.endsWith(".zip")) {
			int ans = JOptionPane.showOptionDialog(null,
					"Does the Zip file has metadata?",
					"Select Type",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[0]);
			if (ans == JOptionPane.YES_OPTION) {
				btf.deck2 = new Deck(cs.scanZipFileWithXML(new File(deck2)));
			} else {
				btf.deck2 = new Deck(cs.scanZipFileWithoutXML(new File(deck2)));
			}
		} else if (deck1.endsWith(".txt")) {
			btf.deck2 = new Deck(cs.scanAllAtributes(deck2));
		}
	}
	
	public void setDeck1Path(String path) {
		txtDk1.setText(path);
	}
	public void setDeck2Path(String path) {
		txtDk2.setText(path);
	}
}

