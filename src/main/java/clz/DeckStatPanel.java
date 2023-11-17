package clz;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class DeckStatPanel extends JPanel {
	private HashMap<String, Integer> civCount, typeCount, stCount, catgCount;
	private HashMap<Integer, Integer> costCount;
	private Deck d;
	private int multi;
	
	public DeckStatPanel(Deck d) {
		this.d = d;
		civCount = new HashMap<String, Integer>();
		typeCount = new HashMap<String, Integer>();
		stCount = new HashMap<String, Integer>();
		costCount = new HashMap<Integer, Integer>();
		catgCount = new HashMap<String, Integer>();
		multi = 0;
		countStats(d);
		initPanel();
	}
	
	private void countStats(Deck d) {
		// Civility-wise count
		
		for (Card c : d) {
			String[] civs = c.civility.trim().split("/");
			if (civs.length > 1) {
				multi++;
			}
			
			String type = c.type.trim();
			String[] sts = c.subtype.trim().split("/");
			Integer cost = Arrays.stream(c.energy).mapToInt(i -> i.intValue()).sum();
			
			for (int j = 0; j < civs.length; j++) {
				String civ = civs[j].trim();
				if (civCount.containsKey(civ)) {
					civCount.put(civ, ((Integer) civCount.get(civ)) + 1);
				} else {
					civCount.put(civ, 1);
				} 
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
			
			for (int j = 0; j < sts.length; j++) {
				String st = sts[j].trim();
				if (stCount.containsKey(st)) {
					stCount.put(st, ((Integer) stCount.get(st)) + 1);
				} else {
					stCount.put(st, 1);
				} 
			}
			
			for (String catg : c.categories) {
				if (catgCount.containsKey(catg)) {
					catgCount.put(catg, ((Integer) catgCount.get(catg))+1 );
				} else {
					catgCount.put(catg, 1);
				}
			}
		}
		
		if (multi > 0) {
			civCount.put("Multi", multi);
		}
	}
	
	private void initPanel() {
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
//		this.setBackground(Color.gray);
		JLabel lblHeader = new JLabel();
		lblHeader.setText(
			"""	
				<html>
					<body>
						<center>
							<h1 style='font-weight:normal;text-decoration:underline;'>
								Card Statistics
							</h1>
						</center>
						</body>
				</html>""");
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
		centerPanel.setBackground(Color.white);
		
		JPanel pnlBar = new JPanel();
		pnlBar.setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		int j = 0;
		JLabel lblCivHead = new JLabel("<html><body><h2 style='text-decoration:underline;'>Color-wise Count</h2></body></html>");
//		lblCivHead.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
//		lblCivHead.setHorizontalAlignment(JLabel.CENTER);
		con.gridx = 0;
		con.gridy = j;
		con.gridheight = 2;
		con.gridwidth = 6;
		con.anchor = GridBagConstraints.CENTER;
		con.fill = GridBagConstraints.BOTH;
		pnlBar.add(lblCivHead, con);
		
		con.gridwidth = 1;
		con.gridheight = 1;
		j+=2;
		
		// Color Table
		for (Entry<String, Integer> e : civCount.entrySet()) {
			JProgressBar bar = new JProgressBar(JProgressBar.HORIZONTAL);
			bar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			bar.setValue(e.getValue());
			bar.setStringPainted(true);
			bar.setString(e.getValue().toString());
			bar.setMaximum(d.size());

			JLabel lblCiv = new JLabel(e.getKey());
			lblCiv.setFont(lblCiv.getFont().deriveFont(Font.BOLD, 15.0f));

			for (int i = 0; i < Constants.civilities.length; i++) {
				if (Constants.civilities[i].contains(e.getKey())) {
					Color c = Constants.colors[i];
					lblCiv.setForeground(c);
					bar.setForeground(c);
				}
			}

			con.gridx = 0;
			con.gridy = j;
			con.anchor = GridBagConstraints.LINE_START;
			pnlBar.add(lblCiv, con);

			con.gridx = 4;
			con.gridy = j;
			con.anchor = GridBagConstraints.LINE_END;
			pnlBar.add(bar, con);

			j++;
		}
		
		// Cost Table
		JLabel lblCHead = new JLabel("<html><body><h2 style='text-decoration:underline;'>Cost-wise Count</h2></body></html>");
		con.gridx = 0;
		con.gridy = j;
		con.gridheight = 2;
		con.gridwidth = 6;
		con.anchor = GridBagConstraints.CENTER;
		pnlBar.add(lblCHead, con);
		j+=2;
		con.gridwidth = 1;
		con.gridheight = 1;
		
		int sum = 0;
		for (Entry<Integer, Integer> e : costCount.entrySet()) {
			JProgressBar bar = new JProgressBar(JProgressBar.HORIZONTAL);
			bar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			bar.setValue(e.getValue());
			bar.setStringPainted(true);
			bar.setString(e.getValue().toString());
			bar.setMaximum(d.size());

			JLabel lblCost = new JLabel("Energy Cost " + e.getKey());

			con.gridx = 0;
			con.gridy = j;
			con.anchor = GridBagConstraints.LINE_START;
			pnlBar.add(lblCost, con);

			con.gridx = 4;
			con.gridy = j;
			con.anchor = GridBagConstraints.LINE_END;
			pnlBar.add(bar, con);

			j++;
			sum += e.getKey() * e.getValue();
		}
		
		double avg = sum/(d.size() * 1.0);
		JLabel lblAvg = new JLabel("<html><body style='font-size:12px;' ><b>Average : </b>" + avg + "</body></html>");
		con.gridx = 0;
		con.gridy = j;
		con.anchor = GridBagConstraints.CENTER;
		pnlBar.add(lblAvg, con);
		j++;
		
		// Type Table
		// going to right half of page
		j = 0;
		int x = 6;
		JLabel lblTypeHead= new JLabel("<html><body><h2 style='text-decoration:underline;'>Typewise Count</h2></body></html>");
		con.gridx = x;
		con.gridy = j;
		con.gridheight = 2;
		con.gridwidth = 6;
		con.anchor = GridBagConstraints.CENTER;
		con.fill = GridBagConstraints.HORIZONTAL;
		pnlBar.add(lblTypeHead, con);
		j+=2;
		con.gridwidth = 1;
		con.gridheight = 1;
		
		for (Entry<String, Integer> e : typeCount.entrySet()) {
			JProgressBar bar = new JProgressBar(JProgressBar.HORIZONTAL);
			bar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			bar.setValue(e.getValue());
			bar.setStringPainted(true);
			bar.setString(e.getValue().toString());
			bar.setMaximum(d.size());

			JLabel lblType = new JLabel(e.getKey());

			con.gridx = x;
			con.gridy = j;
			con.anchor = GridBagConstraints.LINE_START;
			pnlBar.add(lblType, con);

			con.gridx = x+4;
			con.gridy = j;
			con.anchor = GridBagConstraints.LINE_END;
			pnlBar.add(bar, con);

			j++;
		}
		
		// Subtype Table
		JLabel lblSTHead= new JLabel("<html><body><h2 style='text-decoration:underline;'>Subtype Count</h2></body></html>");
		con.gridx = x;
		con.gridy = j;
		con.gridwidth = 6;
		con.gridheight = 2;
		con.anchor = GridBagConstraints.CENTER;
		con.fill = GridBagConstraints.HORIZONTAL;
		pnlBar.add(lblSTHead, con);
		j+=2;
		con.gridwidth = 1;
		con.gridheight = 1;
		
		for (Entry<String, Integer> e : stCount.entrySet()) {
			JProgressBar bar = new JProgressBar(JProgressBar.HORIZONTAL);
			bar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			bar.setValue(e.getValue());
			bar.setStringPainted(true);
			bar.setString(e.getValue().toString());
			bar.setMaximum(d.size());
			
			JLabel lblST;
			
			if (e.getKey() == "") {
				lblST = new JLabel("No Subtype");
			} else {
				lblST = new JLabel(e.getKey());
			}

			con.gridx = x;
			con.gridy = j;
			con.anchor = GridBagConstraints.LINE_START;
			pnlBar.add(lblST, con);

			con.gridx = x+4;
			con.gridy = j;
			con.anchor = GridBagConstraints.LINE_END;
			pnlBar.add(bar, con);

			j++;
		}
		
		// Category Table
		// Moving right again
		x += 6;
		j = 0;
		JLabel lblCatgHead= new JLabel("<html><body><h2 style='text-decoration:underline;'>Categories</h2></body></html>");
		con.gridx = x;
		con.gridy = j;
		con.gridwidth = 6;
		con.gridheight = 2;
		con.anchor = GridBagConstraints.CENTER;
		con.fill = GridBagConstraints.HORIZONTAL;
		pnlBar.add(lblCatgHead, con);
		j+=2;
		con.gridwidth = 1;
		con.gridheight = 1;
		
		for (Entry<String, Integer> e : catgCount.entrySet()) {
			JProgressBar bar = new JProgressBar(JProgressBar.HORIZONTAL);
			bar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			bar.setValue(e.getValue());
			bar.setStringPainted(true);
			bar.setString(e.getValue().toString());
			bar.setMaximum(d.size());
			
			JLabel lblCatg;
			
			if (e.getKey() == "") {
				lblCatg = new JLabel("No Category");
			} else {
				lblCatg = new JLabel(e.getKey());
			}

			con.gridx = x;
			con.gridy = j;
			con.anchor = GridBagConstraints.LINE_START;
			pnlBar.add(lblCatg, con);

			con.gridx = x+4;
			con.gridy = j;
			con.anchor = GridBagConstraints.LINE_END;
			pnlBar.add(bar, con);

			j++;
		}
		
		j += 2;
		
		JLabel lblTot = new JLabel("<html><body style='font-size:15px;' ><b>Total : </b>" + d.size() + "</body></html>");
		JLabel lblExt = new JLabel("<html><body style='font-size:15px;' ><b>Extra : </b>" + d.getExtraDeck().size() + "</body></html>");
		lblTot.setHorizontalAlignment(JLabel.CENTER);
		lblExt.setHorizontalAlignment(JLabel.CENTER);
		con.gridx = x;
		con.gridy = j;
		con.gridwidth = 6;
		con.anchor = GridBagConstraints.LINE_END;
		pnlBar.add(lblTot, con);
		j++;
		con.gridx = x;
		con.gridy = j;
		con.gridwidth = 6;
		con.anchor = GridBagConstraints.CENTER;
		pnlBar.add(lblExt, con);
		j++;
		
		
		centerPanel.add(pnlBar);
		
		JPanel centerPanel2 = new JPanel();
		centerPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));
		centerPanel2.setBackground(Color.white);
		centerPanel2.add(lblHeader);
		
//		pnlBar.setBackground(Color.GRAY);
//		this.setBackground(Color.GRAY);
		this.add(centerPanel2);
		this.add(centerPanel);
	}
	
	private void initPanel2() {
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(Color.gray);
		JLabel lblHeader = new JLabel();
//		JLabel lblEntry = new JLabel();
//		JLabel lblEntry2 = new JLabel();
		JEditorPane lblEntry, lblEntry2;
		lblEntry = new JEditorPane();
		lblEntry2 = new JEditorPane();
		lblEntry.setContentType("text/html");
		lblEntry.setEditable(false);
		lblEntry2.setContentType("text/html");
		lblEntry2.setEditable(false);
		
		lblHeader.setText(
			"""
			<html>
				<body>
					<center>
						<h1 style='font-weight:normal;text-decoration:underline;'>
							Card Statistics
						</h1>
					</center>
				</body>
			</html>""");
		
		StringBuilder htmlBuf = new StringBuilder();
		htmlBuf.append("<html><body style='font-size:14px;'>");
		htmlBuf.append("<center>");
		
		// civility table
		htmlBuf.append("<table border='2' bgcolor='white'>");
		
		for (Entry<String, Integer> e : civCount.entrySet()) {
			boolean keyFound = false;
			for (int i = 0; i < Constants.civilities.length; i++) {
				if (Constants.civilities[i].contains(e.getKey())) {
					Color c = Constants.colors[i];
//				if (Constants.colProp.containsKey(e.getKey())) {
//					Color c = Color.decode(Constants.colProp.get(e.getKey()).toString());
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
		centerPanel.setBackground(Color.white);
		centerPanel.add(lblEntry);
		centerPanel.add(lblEntry2);
		JPanel centerPanel2 = new JPanel();
		centerPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));
		centerPanel2.setBackground(Color.white);
		centerPanel2.add(lblHeader);
		this.add(centerPanel2);
		this.add(centerPanel);
	}
}

