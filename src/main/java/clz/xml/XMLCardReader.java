/**
 * 
 */
package clz.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import clz.Card;
import clz.Deck;

/**
 * @author subhraman
 *
 */
public class XMLCardReader {
	public Deck cards = new Deck();
	public StringBuffer effects = new StringBuffer();
	public Card c = null;
	public boolean isExtra;
	private HashMap<String, String> links = new HashMap<String, String>();
	
	public XMLCardReader() {
		isExtra = false;

		XMLInputFactory fac = XMLInputFactory.newInstance();
		JFileChooser files = new JFileChooser();
		int status = files.showOpenDialog(new JFrame());
		if (status == JFileChooser.APPROVE_OPTION) {
			File f = files.getSelectedFile();
			if (f != null) {
				try {
					XMLStreamReader reader = fac.createXMLStreamReader(new FileInputStream(f));
					while (reader.hasNext()) {
						parse(reader.next(), reader);
					}
					reader.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (XMLStreamException e) {
					e.printStackTrace();
				}
			} 
		} else {
			cards = null;
		}
	}
	
	public XMLCardReader(InputStream in) {
		XMLInputFactory fac = XMLInputFactory.newInstance();
		if (in != null) {
			try {
				XMLStreamReader reader = fac
						.createXMLStreamReader(in);
				while (reader.hasNext()) {
					parse(reader.next(), reader);
				}
				reader.close();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * @param next
	 * @param reader 
	 * @throws XMLStreamException
	 * Card images are attached in CScan
	 */
	private void parse(int next, XMLStreamReader reader) throws XMLStreamException {
		switch(next) {
			case XMLEvent.START_ELEMENT :
				String stag = reader.getName().toString();
				if (stag.equals("Card")) {
					c = new Card();
					c.effects = new String[20];
				} else if (stag.equalsIgnoreCase("isExtra")) {
//					System.out.println("Identifying Card as extra.");
					this.isExtra = true;
				} else if ((reader.next() == XMLEvent.CHARACTERS) && (c != null)) {
					if (stag.equalsIgnoreCase("name")) {
						c.name = reader.getText();
					} else if (stag.equalsIgnoreCase("civility")) {
						c.civility = reader.getText();
					} else if (stag.equalsIgnoreCase("type")) {
						c.type = reader.getText();
					} else if (stag.equalsIgnoreCase("subtype")) {
						c.subtype = reader.getText();
					} else if (stag.equalsIgnoreCase("ID")) {
						c.id = reader.getText();
					} else if (stag.equalsIgnoreCase("effect")) {
						effects.append(reader.getText() + "\n");
					} else if (stag.equalsIgnoreCase("energy")) {
						String text = reader.getText();
						c.energy = Card.stringToEnergy(text);
					} else if (stag.equalsIgnoreCase("power")) {
						c.power =  Integer.parseInt(reader.getText());
					} else if (stag.equalsIgnoreCase("dp")) {
						c.damage =  Integer.parseInt(reader.getText());
					} else if (stag.equalsIgnoreCase("eno")) {
						c.eno = Card.stringToEnergy(reader.getText());
					} else if (stag.equalsIgnoreCase("category")) {
						c.addCategory(reader.getText());
					} else if (stag.equalsIgnoreCase("link")) {
						String linkName = reader.getText();
						links.put(c.name, linkName);
					}
				}
			
			break;
			case XMLEvent.END_ELEMENT :
				String etag = reader.getName().toString();
				if (etag.equals("Card")) {
					c.effects = effects.toString().split("\n");
					c.createImage();
					if (!isExtra) {
						cards.add(c);
					} else {
						cards.addAsExtra(c);
					}

					isExtra = false;
					effects.delete(0, effects.length());
				} else if (etag.equals("Deck")) {
					
					// Add all links
					int i = 0;
					
					for (Card c : cards) {
						String linkname = links.get(c.name);
						if (linkname != null) {
							for (Card exCard : cards.getExtraDeck()) {
								if (exCard.name.equals(linkname)) {
									exCard.addPart(c);
									c.addPart(exCard);
									cards.update(c, i);
								}
							}
						}
						i++;
					}
					
					for (Card c : cards.getExtraDeck()) {
						String linkname = links.get(c.name);
						if (linkname != null) {
							for (Card exCard : cards.getExtraDeck()) {
								if (exCard.name.equals(linkname)) {
									exCard.addPart(c);
									c.addPart(exCard);
									cards.update(c, i);
								}
							}
						}
						i++;
					}
					
					ArrayList<Card> updatedExDeck = new ArrayList<Card>();
					
					for (Card exCard : cards.getExtraDeck()) {
						boolean hasCard = false;
//						System.out.println(exCard.name);
						for (var entry : links.entrySet()) {
//							System.out.println(String.format("%s -> %s", entry.getKey(), entry.getValue()));
							hasCard = hasCard || entry.getKey().equals(exCard.name);
						}
						
						if (!hasCard) {
//							System.out.println("No link : " + exCard.name);
						} else {
							updatedExDeck.add(exCard);
						}
					}
					cards.setExtraDeck(updatedExDeck);
				}
				break;


		}
//		System.out.println("No of main cards : " + cards.size());
//		System.out.println("No of extra cards : " + cards.getExtraDeck().size());
	}
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		XMLCardReader xcr = new XMLCardReader();
//		for (Card c : xcr.getDeck()) {
//			System.out.println(c.toString());
//		}
//	}
	/**
	 * @return the cards
	 */
	public Deck getDeck() {
		return cards;
	}

}
