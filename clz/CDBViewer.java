package clz;

import java.awt.BorderLayout;
import java.util.Deque;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

@SuppressWarnings("serial")
public class CDBViewer extends JFrame {
	
	public CDBViewer(Deque<Card> library) {
		setTitle("Card Database Viewer");
		setSize(700, 400);
		getContentPane().setLayout(new BorderLayout());
		Vector<Vector<String>> vLib = new Vector<Vector<String>>();
		for (Card c : library) {
			vLib.add(c.getData());
		}
		Vector<String> headers = new Vector<String>();
		headers.add("ID");
		headers.add("Name");
		headers.add("Civility");
		headers.add("Type");
		headers.add("Subtype");
		headers.add("Energy");
		headers.add("Power");
		headers.add("Energy Number");
		headers.add("Effects");
		JTable table = new JTable(vLib, headers);
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		table.setEnabled(false);
		JScrollPane scroll = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
				);
		getContentPane().add(scroll, BorderLayout.CENTER);
	}
}
