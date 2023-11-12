package clz;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DeckStatPanel extends JPanel {
	private HashMap<String, Integer> civCount, typeCount, stCount, catgCount;
	private HashMap<Integer, Integer> costCount;
	private Deck d;
	
	public DeckStatPanel(Deck d) {
		this.d = d;
		civCount = new HashMap<String, Integer>();
		typeCount = new HashMap<String, Integer>();
		stCount = new HashMap<String, Integer>();
		costCount = new HashMap<Integer, Integer>();
		catgCount = new HashMap<String, Integer>();
		countStats(d);
		initPanel();
	}
	
	private void countStats(Deck d) {
		// Civility-wise count
		for (Card c : d) {
			String civ = c.civility.trim();
			String type = c.type.trim();
			String st = c.subtype.trim();
			Integer cost = Arrays.stream(c.energy).mapToInt(i -> i.intValue()).sum();
			
			if (civCount.containsKey(civ)) {
				civCount.put(civ, ((Integer) civCount.get(civ))+1 );
			} else {
				civCount.put(civ, 1);
			}
			
			if (costCount.containsKey(cost)) {
				costCount.put(cost, ((Integer) costCount.get(cost))+1 );
			} else {
				costCount.put(cost, 1);
			}
			
			if (typeCount.containsKey(type)) {
				typeCount.put(type, ((Integer) typeCount.get(type))+1 );
			} else {
				typeCount.put(type, 1);
			}
			
			if (stCount.containsKey(st)) {
				stCount.put(st, ((Integer) stCount.get(st))+1 );
			} else {
				stCount.put(st, 1);
			}
			
			for (String catg : c.categories) {
				if (catgCount.containsKey(catg)) {
					catgCount.put(catg, ((Integer) catgCount.get(catg))+1 );
				} else {
					catgCount.put(catg, 1);
				}
			}
		}
	}
	
	private void initPanel() {
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JLabel lblHeader = new JLabel();
		JLabel lblEntry = new JLabel();
		JLabel lblEntry2 = new JLabel();
		
		lblHeader.setText("<html><body><center><h1 style='font-weight:normal;'>Card Statistics</h1></center></body></html>");
		
		StringBuilder htmlBuf = new StringBuilder();
		htmlBuf.append("<html><body style='font-size:14px;'>");
		htmlBuf.append("<center>");
//		htmlBuf.append("<h1 style='font-weight:normal;'>Card Statistics</h1>");
		
		// civility table
		htmlBuf.append("<table border='2' bgcolor='white'>");
		
		for (Entry<String, Integer> e : civCount.entrySet()) {
			boolean keyFound = false;
			for (int i = 0; i < Constants.civilities.length; i++) {
				if (Constants.civilities[i].contains(e.getKey())) {
					Color c = Constants.colors[i];
					String col = String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
					htmlBuf.append( String.format("<tr><td><font color=%s>%s</font></td><td> %s</td></tr>", col, e.getKey(), e.getValue()) );
					keyFound = true;
					break;
				}
			}
			
			if(!keyFound)  {
				htmlBuf.append("<tr><td>" + e.getKey() + "</td><td>" + e.getValue() + "</td></tr>");
			}
		}
		
		htmlBuf.append("<tr><td bgcolor='#CCCCFF'><font color='#000066'>Total</font></td><td>" + d.size()+ "</td></tr>");
		htmlBuf.append("<tr><td bgcolor='#FFCCCC'><font color='#330000'>Extra Deck</font></td><td>" + d.getExtraDeck().size() + "</td></tr>");
		
		htmlBuf.append("</table><br/>");
		
		//cost table
		htmlBuf.append("<table border='2' bgcolor='white'>");
		
		int sum = 0;
		for (Entry<Integer, Integer> e : costCount.entrySet()) {
			htmlBuf.append("<tr><td>Energy " + e.getKey() + "</td><td>" + e.getValue() + "</td></tr>");
			sum += e.getKey() * e.getValue();
		}
		double avg = sum/(d.size() * 1.0);
		
		htmlBuf.append("<tr><td bgcolor='#CCCCFF'><font color='#000066'>Average</font></td><td>" + avg + "</td></tr>");
		htmlBuf.append("</table><br/>");
		
		htmlBuf.append("</center>");
		htmlBuf.append("</body></html>");
		
		lblEntry.setText(htmlBuf.toString());
		
		StringBuilder htmlBuf2 = new StringBuilder();
		htmlBuf2.append("<html><body style='font-size:14px;'>");
		htmlBuf2.append("<center>");
		
		//type table
		htmlBuf2.append("<table border='2' bgcolor='white'>");
		for (Entry<String, Integer> e : typeCount.entrySet()) {
			htmlBuf2.append("<tr><td> " + e.getKey() + "</td><td>" + e.getValue() + "</td></tr>");
		}
		htmlBuf2.append("</table><br/>");
		
		//subtype table
		htmlBuf2.append("<table border='2' bgcolor='white'>");
		for (Entry<String, Integer> e : stCount.entrySet()) {
			if (e.getKey() == "") {
				htmlBuf2.append("<tr><td> No Subtype </td><td>" + e.getValue() + "</td></tr>");
			} else {
				htmlBuf2.append("<tr><td> " + e.getKey() + "</td><td>" + e.getValue() + "</td></tr>");
			}
		}
		htmlBuf2.append("</table><br/>");
		
		//category table
		htmlBuf2.append("<table border='2' bgcolor='white'>");
		for (Entry<String, Integer> e : catgCount.entrySet()) {
			if (e.getKey() == "") {
				htmlBuf2.append("<tr><td> No Category </td><td>" + e.getValue() + "</td></tr>");
			} else {
				htmlBuf2.append("<tr><td> " + e.getKey() + "</td><td>" + e.getValue() + "</td></tr>");
			}
		}
		htmlBuf2.append("</table><br/>");
		
		htmlBuf2.append("</center>");
		htmlBuf2.append("</body></html>");
		
		lblEntry2.setText(htmlBuf2.toString());
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		centerPanel.add(lblEntry);
		centerPanel.add(lblEntry2);
		JPanel centerPanel2 = new JPanel();
		centerPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));
		centerPanel2.add(lblHeader);
		this.add(centerPanel2);
		this.add(centerPanel);
	}
}

