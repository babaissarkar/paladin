package writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JFrame;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

/*
 * card.java
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

// This file is a small gui application for creating the deckfiles


@SuppressWarnings("serial")
public class CardWriter extends JFrame implements ActionListener {
	private JTextField textField1;
	private JTextField textField2;
	private JFileChooser jfc = new JFileChooser();
	private JButton btnCreate;
	private PrintStream fout;
	private JButton btnWrite;
	
	public CardWriter() {
		getContentPane().setBackground(new Color(255, 255, 102));
		setBackground(new Color(255, 255, 153));
		fout = null;
		
		setTitle("Card Writer");
		setSize(new Dimension(430, 257));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btnCreate = new JButton("Create");
		btnCreate.setForeground(new Color(0, 0, 0));
		btnCreate.setMnemonic(KeyEvent.VK_C);
		btnCreate.addActionListener(this);
		
		btnWrite = new JButton("Write");
		btnWrite.setForeground(new Color(0, 0, 0));
		btnWrite.addActionListener(this);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setForeground(new Color(0, 0, 0));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (fout != null) {
					fout.close();
				}
				System.exit(0);
			}
		});
		
		JLabel lblImagePath = new JLabel("Image Path : ");
		
		JLabel lblName = new JLabel("Name : ");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		
		textField1 = new JTextField(70);
		textField1.setColumns(10);
		
		textField2 = new JTextField(10);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(lblName)
							.addGap(29)
							.addComponent(textField1, GroupLayout.PREFERRED_SIZE, 265, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnCreate)
								.addComponent(lblImagePath))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(textField2, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(59)
									.addComponent(btnWrite)
									.addPreferredGap(ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
									.addComponent(btnExit)
									.addPreferredGap(ComponentPlacement.RELATED)))))
					.addContainerGap(26, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(22)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblName))
					.addGap(25)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblImagePath)
						.addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(93)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCreate)
						.addComponent(btnWrite)
						.addComponent(btnExit))
					.addContainerGap(80, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	public static void main (String args[]) {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e0) {
			try {
			 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}  catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
					e.printStackTrace();
			} catch (IllegalAccessException e) {
					e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
		}
		
		new CardWriter().setVisible(true);
	}

    public void create(String path) {
        File f = new File(path);
        try {
        	if (!f.exists()) {
        		f.createNewFile();
            }
			fout = new PrintStream(new FileOutputStream(f));
			fout.println(textField1.getText()+";");
			fout.print(textField2.getText());
			JOptionPane.showMessageDialog(this, "Write successful!");
		} catch (FileNotFoundException e) {
			System.out.println("Error! File not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error! IO Exception.");
			e.printStackTrace();
		} finally {
		}
    }
    
    public void write(PrintStream ps) {
    	ps.println(";");
    	ps.println(textField1.getText()+";");
		ps.print(textField2.getText());
		JOptionPane.showMessageDialog(this, "Write successful!");
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnCreate) {
				jfc.showSaveDialog(this);
				create(jfc.getSelectedFile().getAbsolutePath());
		} else if (arg0.getSource() == btnWrite) {
				write(fout);
		}
	}
}

