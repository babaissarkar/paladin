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
import java.io.File;

import javax.swing.Box;
import javax.swing.JCheckBox;
//import javax.swing.JSpinner;
//import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class PrefIFrame extends JFrame implements ActionListener {
	private boolean shields;
	private boolean draw;
	
	// GUI Components
	public JButton btnCancel, btnOk, btnBrowse, btnBrowse2;
	private JCheckBox chckbxShields, chckbxDraw;
	private JTextField txtprplayerdecksdeckzip, txtprplayerdecksdeckzip_1, textField_2;
	private JFileChooser jfc;
	private JTextField textField_4;
	
	public Battlefield btf;

	/**
	 * Create the frame.
	 */
	public PrefIFrame(Battlefield b) {
		btf = b;
		shields = false;
		draw = true;
		jfc = new JFileChooser();
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		getContentPane().setBackground(Color.WHITE);
		
		JLabel lblDeck = new JLabel("Deck 1 :");
		lblDeck.setFont(new Font("DejaVu Serif", Font.PLAIN, 13));
		
		txtprplayerdecksdeckzip = new JTextField();
		txtprplayerdecksdeckzip.setText(Battlefield.userhome + "/.PRPlayer/decks/deck1.zip");
		txtprplayerdecksdeckzip.setColumns(10);
		
		btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(this);
		
		JLabel lblDeck_1 = new JLabel("Deck 2 :");
		lblDeck_1.setFont(new Font("DejaVu Serif", Font.PLAIN, 13));
		
		txtprplayerdecksdeckzip_1 = new JTextField();
		txtprplayerdecksdeckzip_1.setText(Battlefield.userhome + "/.PRPlayer/decks/deck2.zip");
		txtprplayerdecksdeckzip_1.setColumns(10);
		
		btnBrowse2 = new JButton("Browse...");
		btnBrowse2.addActionListener(this);
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(this);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		
		Box horizontalBox = Box.createHorizontalBox();
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		
		chckbxDraw = new JCheckBox("Draw");
		chckbxDraw.addActionListener(this);
		chckbxDraw.setSelected(true);
		horizontalBox_1.add(chckbxDraw);
		
		textField_2 = new JTextField();
		textField_2.setText("5");
		horizontalBox_1.add(textField_2);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblDeck, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtprplayerdecksdeckzip, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(btnBrowse))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblDeck_1, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(txtprplayerdecksdeckzip_1, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(btnBrowse2, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(113, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(24)
					.addComponent(btnOk)
					.addPreferredGap(ComponentPlacement.RELATED, 404, Short.MAX_VALUE)
					.addComponent(btnCancel)
					.addGap(22))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(horizontalBox, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
					.addGap(40)
					.addComponent(horizontalBox_1, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(221, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDeck, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtprplayerdecksdeckzip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBrowse))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDeck_1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(5)
							.addComponent(txtprplayerdecksdeckzip_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(btnBrowse2)))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(horizontalBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 179, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnOk)
								.addComponent(btnCancel))
							.addGap(25))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(horizontalBox_1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		
		chckbxShields = new JCheckBox("Shields");
		chckbxShields.addActionListener(this);
		horizontalBox.add(chckbxShields);
		
		//spinner = new JSpinner();
		textField_4 = new JTextField();
		textField_4.setEnabled(false);
		//spinner.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		horizontalBox.add(textField_4);
		
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
			jfc.showOpenDialog(this);
			if (jfc.getSelectedFile() != null) {
				txtprplayerdecksdeckzip.setText(jfc.getSelectedFile().getAbsolutePath());
			}
		} else if (arg0.getSource() == btnBrowse2) {
			jfc.showOpenDialog(this);
			if (jfc.getSelectedFile() != null) {
				txtprplayerdecksdeckzip_1.setText(jfc.getSelectedFile().getAbsolutePath());
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
		String deck1 = txtprplayerdecksdeckzip.getText();
		String deck2 = txtprplayerdecksdeckzip_1.getText();
		CScan cs = new CScan();
		
		if (deck1.endsWith(".zip")) {
			btf.deck1 = cs.scanZipFile(deck1);
		} else if (deck1.endsWith(".txt")) {
			btf.deck1 = cs.scan(new File(deck1));
//		} else if (deck1.endsWith(".xml")) {
//			btf.deck1 = cs.scanXMLFile(getClass().getResourceAsStream(deck1));
//		} else if (deck1.endsWith(".deck")) {
//			btf.deck1 = cs.scanDeckfile(getClass().getResource(deck1).toString());
		}
		
		if (deck2.endsWith(".zip")) {
			btf.deck2 = cs.scanZipFile(deck2);
		} else if (deck1.endsWith(".txt")) {
			btf.deck2 = cs.scan(new File(deck2));
//		} else if (deck2.endsWith(".xml")) {
//			btf.deck2 = cs.scanXMLFile(getClass().getResourceAsStream(deck2));
//		} else if (deck2.endsWith(".deck")) {
//			btf.deck2 = cs.scanDeckfile(getClass().getResource(deck2).toString());
		}
	}
}

