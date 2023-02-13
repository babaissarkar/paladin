package clz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Deque;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class CSVIO {
	
	public static void writeCSV(Deque<Card> lib) {
		JFileChooser files = new JFileChooser();
		int stat = files.showSaveDialog(new JFrame());
		File f = null;
			if (stat == JFileChooser.APPROVE_OPTION) {
				f = files.getSelectedFile();
				try {
					if (!f.exists()) {
						f.createNewFile();
					}
					PrintWriter w = new PrintWriter(new BufferedWriter(new FileWriter(f)));
					w.println("\"ID\", \"Name\", \"Type\", \"Subtype\", \"Civility\", "
							+ "\"Energy\", \"Power\", \"Energy No.\", \"Effects\"");
					for (Card c : lib) {
						w.print("\"");
						w.print(c.id);
						w.print("\"");
						w.print(", ");
						w.print("\"");
						w.print(c.name);
						w.print("\"");
						w.print(", ");
						w.print("\"");
						w.print(c.type);
						w.print("\"");
						w.print(", ");
						w.print("\"");
						w.print(c.subtype);
						w.print("\"");
						w.print(", ");
						w.print("\"");
						w.print(c.civility);
						w.print("\"");
						w.print(", ");
						w.print("\"");
						w.print(Card.energyToString(c.energy));
						w.print("\"");
						w.print(", ");
						w.print("\"");
						w.print(c.power);
						w.print("\"");
						w.print(", ");
						w.print("\"");
						w.print(Card.energyToString(c.eno));
						w.print("\"");
						for (String ef : c.effects) {
							w.print(", ");
							w.print(ef);
						}
						w.println();
					}
					w.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}

}
