/**
 * 
 */
package clz.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Deque;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import clz.Deck;
import clz.Card;

/**
 * @author subhraman
 *
 */
public class XMLCardWriter {
	private Deck cards = new Deck();
	
	public XMLCardWriter(Deck cards) {
		this.setCards(cards);
	}
	
	public void writeToFile() {
		//Initiate
		File f = null;
		JFileChooser jfc = new JFileChooser();
		int stat = jfc.showSaveDialog(new JFrame());
		if (stat == JFileChooser.APPROVE_OPTION) {
			f = jfc.getSelectedFile();
		}
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			if (f != null) {
				writeDeck(new FileOutputStream(f));
			}
		} catch (IOException | XMLStreamException e) {
			e.printStackTrace();
		}
	}

	public void writeDeck(OutputStream ostream) throws XMLStreamException {
		XMLOutputFactory xof = XMLOutputFactory.newFactory();
		XMLStreamWriter xw = xof.createXMLStreamWriter(ostream, "utf-8");
		xw.writeStartDocument("utf-8", "1.0");
		xw.writeCharacters("\n");
		xw.writeComment("Created by PRPlayer.");
		xw.writeCharacters("\n");
		xw.writeStartElement("Deck");
		for (Card c : cards) {
			//System.out.println("Writing main cards!");
			writeCard(xw, c, false);
		}

		if (cards.hasExtras()) {
			//System.out.println("Writing extra cards!");
			for (Card extra : cards.getExtraDeck()) {
				writeCard(xw, extra, true);
			}
		}

		xw.writeEndDocument();
		xw.close();
		JOptionPane.showMessageDialog(new JFrame(), "File successfully saved.");
	}

	private void writeCard(XMLStreamWriter xw, Card c, boolean isExtra) throws XMLStreamException {
		xw.writeCharacters("\n");
		xw.writeCharacters("\t");
		xw.writeStartElement("Card");
		xw.writeCharacters("\n");

		xw.writeCharacters("\t\t");
		xw.writeStartElement("name");
		xw.writeCharacters(c.name);
		xw.writeEndElement();
		xw.writeCharacters("\n");

		xw.writeCharacters("\t\t");
		xw.writeStartElement("civility");
		xw.writeCharacters(c.civility);
		xw.writeEndElement();
		xw.writeCharacters("\n");

		xw.writeCharacters("\t\t");
		xw.writeStartElement("type");
		xw.writeCharacters(c.type);
		xw.writeEndElement();
		xw.writeCharacters("\n");

		xw.writeCharacters("\t\t");
		xw.writeStartElement("subtype");
		xw.writeCharacters(c.subtype);
		xw.writeEndElement();
		xw.writeCharacters("\n");

		xw.writeCharacters("\t\t");
		xw.writeStartElement("id");
		xw.writeCharacters(c.id);
		xw.writeEndElement();
		xw.writeCharacters("\n");

		for (String effect : c.effects) {
			xw.writeCharacters("\t\t");
			xw.writeStartElement("effect");
			xw.writeCharacters(effect);
			xw.writeEndElement();
			xw.writeCharacters("\n");
		}

		xw.writeCharacters("\t\t");
		xw.writeStartElement("energy");
		xw.writeCharacters(Card.energyToString(c.energy));
		xw.writeEndElement();
		xw.writeCharacters("\n");

		xw.writeCharacters("\t\t");
		xw.writeStartElement("power");
		xw.writeCharacters(Integer.toString(c.power));
		xw.writeEndElement();
		xw.writeCharacters("\n");

		xw.writeCharacters("\t\t");
		xw.writeStartElement("eno");
		xw.writeCharacters(Card.energyToString(c.eno));
		xw.writeEndElement();
		xw.writeCharacters("\n");

		xw.writeCharacters("\t\t");
		xw.writeStartElement("dp");
		xw.writeCharacters("" + c.damage);
		xw.writeEndElement();
		xw.writeCharacters("\n");

		if (isExtra) {
			xw.writeCharacters("\t\t");
			xw.writeEmptyElement("isExtra");
			xw.writeCharacters("\n");
		}
		
		if (c.getParts().size() > 0) {
			for (Card part : c.getParts()) {
				xw.writeCharacters("\t\t");
				xw.writeStartElement("link");
				xw.writeCharacters(part.name);
				xw.writeEndElement();
				xw.writeCharacters("\n");
			}
		}

//			xw.writeCharacters("\t\t");
//			xw.writeStartElement("img");
//			if (c.imgpath != null) {
//				xw.writeCharacters(c.imgpath);
//			} else {
//				xw.writeCharacters(c.name + ".jpg");
//			}
//			xw.writeEndElement();
//			xw.writeCharacters("\n");

		xw.writeCharacters("\t");

		xw.writeEndElement();
		xw.writeCharacters("\n");
	}

	/**
	 * @return the deck
	 */
	public Deck getCards() {
		return cards;
	}

	/**
	 * @param cards2 the deck to set
	 */
	public void setCards(Deck cards2) {
		this.cards = cards2;
	}

}
