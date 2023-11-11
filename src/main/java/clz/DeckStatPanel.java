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
	private HashMap<String, Integer> civCount;
	private HashMap<Integer, Integer> costCount;
	private Deck d;
	
	public DeckStatPanel(Deck d) {
		this.d = d;
		civCount = new HashMap<String, Integer>();
		costCount = new HashMap<Integer, Integer>();
		countStats(d);
		initPanel();
	}
	
	private void countStats(Deck d) {
		// Civility-wise count
		for (Card c : d) {
			String civ = c.civility.trim();
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
		}
	}
	
	private void initPanel() {
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JLabel lblEntry = new JLabel();
		
		StringBuilder htmlBuf = new StringBuilder();
		htmlBuf.append("<html><body style='font-size:14px;'>");
		htmlBuf.append("<center>");
		htmlBuf.append("<h1 style='font-weight:normal;'>Card Statistics</h1>");
		
		for (Entry<String, Integer> e : civCount.entrySet()) {
			boolean keyFound = false;
			for (int i = 0; i < Constants.civilities.length; i++) {
				if (Constants.civilities[i].contains(e.getKey())) {
					Color c = Constants.colors[i];
					String col = String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
					htmlBuf.append( String.format("<font color=%s>%s :</font> %s<br/>", col, e.getKey(), e.getValue()) );
					keyFound = true;
					break;
				}
			}
			
			if(!keyFound)  {
				htmlBuf.append(e.getKey() + " : " + e.getValue() + "<br/>");
			}
		}
		
		htmlBuf.append("<hr/>Total : " + d.size()+ "<br/>");
		htmlBuf.append("<hr/>Extra Deck : " + d.getExtraDeck().size() + "<br/><hr/>");
		
		int sum = 0;
		for (Entry<Integer, Integer> e : costCount.entrySet()) {
			htmlBuf.append("Energy " + e.getKey() + " : " + e.getValue() + "<br/>");
			sum += e.getKey() * e.getValue();
		}
		double avg = sum/(d.size() * 1.0);
		
		htmlBuf.append("<hr/>Average : " + avg + "<br/>");
		
		htmlBuf.append("</center>");
		htmlBuf.append("</body></html>");
		
		lblEntry.setText(htmlBuf.toString());
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		centerPanel.add(lblEntry);
		this.add(centerPanel);
	}
}

