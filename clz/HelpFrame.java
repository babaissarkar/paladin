/*
 *      HelpFrame.java
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

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.io.IOException;

@SuppressWarnings("serial")
public class HelpFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public HelpFrame() {
		setTitle("Help - Rules");
		setFont(new Font("Century Catalogue", Font.PLAIN, 17));
		setBackground(Color.WHITE);
		setBounds(100, 100, 803, 514);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JEditorPane textPane = new JEditorPane();
		textPane.setFont(new Font("Century Catalogue", Font.PLAIN, 15));
		textPane.setEditable(false);
		try {
			textPane.setPage(this.getClass().getResource("/doc/PRR.html"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		scrollPane.setViewportView(textPane);
	}

}
