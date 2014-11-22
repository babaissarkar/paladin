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
			textPane.setPage(this.getClass().getResource("/doc/Paladins_Returned_Rules.html"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		scrollPane.setViewportView(textPane);
	}

}
