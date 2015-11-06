package clz;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class PrefIFrame extends JFrame implements ActionListener {
	private boolean shields;
	private boolean draw;
	
	// GUI Components
	public JButton btnCancel, btnOk, btnBrowse, btnBrowse2;
	private JCheckBox chckbxShields, chckbxDraw;
	private JTextField txtDk1, txtDk2, textField_2;
	private JFileChooser files;
	private JTextField textField_4;
	
	public Battlefield btf;

	/**
	 * Create the frame.
	 */
	public PrefIFrame(Battlefield b) {
		btf = b;
		shields = false;
		draw = true;
		files = new JFileChooser();
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		getContentPane().setBackground(Color.WHITE);
		
		JLabel lblDeck = new JLabel("Deck 1 :");
		lblDeck.setFont(new Font("DejaVu Serif", Font.PLAIN, 13));
		
		txtDk1 = new JTextField();
		txtDk1.setText(Battlefield.userhome + "/.PRPlayer/decks/deck1.zip");
		txtDk1.setColumns(10);
		
		btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(this);
		
		JLabel lblDeck_1 = new JLabel("Deck 2 :");
		lblDeck_1.setFont(new Font("DejaVu Serif", Font.PLAIN, 13));
		
		txtDk2 = new JTextField();
		txtDk2.setText(Battlefield.userhome + "/.PRPlayer/decks/deck2.zip");
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
		
		textField_2 = new JTextField();
		textField_2.setText("5");
		hboxDraw.add(textField_2);
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
					.addContainerGap(275, Short.MAX_VALUE))
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
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(hboxFCards, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 179, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnOk)
								.addComponent(btnCancel))
							.addGap(25))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(hboxDraw, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		
		chckbxShields = new JCheckBox("Flipped Cards");
		chckbxShields.addActionListener(this);
		hboxFCards.add(chckbxShields);
		
		textField_4 = new JTextField();
		textField_4.setEnabled(false);
		hboxFCards.add(textField_4);
		
		getContentPane().setLayout(groupLayout);
		setTitle("Preferences");
		setBounds(100, 100, 587, 375);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnCancel) {
			this.setVisible(false);
		} else if (arg0.getSource() == btnOk) {
			setDeck();
			this.setVisible(false);
			this.btnCancel.setEnabled(true);
			int i = 0, j = 0;
			if (!textField_4.getText().equals("")) {
				i = new Integer(textField_4.getText());
			}
			if (!textField_2.getText().equals("")) {
				j = new Integer(textField_2.getText());
			}
			btf.proceed(i, j);
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
			if (false == shields) {
				textField_4.setEnabled(true);
				shields = true;
			} else {
				textField_4.setEnabled(false);
				shields = false;
			}
		} else if(arg0.getSource() == chckbxDraw) {
			if (draw) {
				textField_2.setEditable(false);
				draw = false;
			} else {
				textField_2.setEditable(true);
				draw = true;
			}
		}
		
	}

	public final boolean isDraw() {
		return this.draw;
	}

	public final boolean isShields() {
		return this.shields;
	}

	private void setDeck() {
		String deck1 = txtDk1.getText();
		String deck2 = txtDk2.getText();
		CScan cs = new CScan();
		
		if (deck1.endsWith(".zip")) {
			btf.deck1 = cs.scanZipFile(deck1);
		} else if (deck1.endsWith(".txt")) {
			btf.deck1 = cs.scanAllAtributes(deck1);
		}
		
		if (deck2.endsWith(".zip")) {
			btf.deck2 = cs.scanZipFile(deck2);
		} else if (deck1.endsWith(".txt")) {
			btf.deck2 = cs.scanAllAtributes(deck2);
		}
	}
}

