package clz;

import java.awt.BorderLayout;
import java.util.Deque;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

@SuppressWarnings("serial")
public class CDBViewer extends JFrame {
	
	public CDBViewer(Deque<Card> library) {
		setTitle("Card Database Viewer");
		setSize(1300, 400);
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
		headers.add("Effects 2");
		headers.add("Effects 3");
		headers.add("Effects 4");
		headers.add("Effects 5");
		headers.add("Effects 6");
		JTable table = new JTable(vLib, headers);
//		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
//		table.setRowSorter(sorter);
		table.setAutoCreateRowSorter(true);
		TableColumnModel columns = table.getColumnModel();
		columns.getColumn(0).setPreferredWidth(50);
		columns.getColumn(1).setPreferredWidth(215);
		columns.getColumn(2).setPreferredWidth(50);
		columns.getColumn(3).setPreferredWidth(90);
		columns.getColumn(4).setPreferredWidth(90);
		columns.getColumn(5).setPreferredWidth(90);
		columns.getColumn(6).setPreferredWidth(50);
		columns.getColumn(7).setPreferredWidth(120);
//		table.setEnabled(false);
		JScrollPane scroll = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
				);
		getContentPane().add(scroll, BorderLayout.CENTER);
	}
}
