/*
 * CScan.java
 * 
 * Copyright 2014 Subhraman Sarkar <subhraman@subhraman-Inspiron-660s>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */
 
package clz;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Base64;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.stream.XMLStreamException;

import clz.xml.XMLCardReader;
import clz.xml.XMLCardWriter;

public class CScan extends JFrame {
	
	
	private static final long serialVersionUID = -1826147733065781359L;
	public int j;
	public Base64.Decoder dec;
	
	public CScan() {
		setTitle("Open");
		dec = Base64.getDecoder();
	}
	

	public Deck scanNmeAndImg(String path) {
		
		Scanner s = null;
		List<String> clist = new ArrayList<String>();
		Deck deck = new Deck();
		Iterator<String> i;
		@SuppressWarnings("unused")
		int id = 0;
		try {
			s = new Scanner(new BufferedInputStream(getClass().getResourceAsStream(path)));
			s.useDelimiter(";\n");
			while (s.hasNext()) {
				clist.add(s.next());
			}
			i = clist.iterator();
			while (i.hasNext()) {
				deck.addLast(new Card(i.next(), i.next()));
				id++;
			}
		} finally {
			s.close();
		}
		return deck;
	}
	
public Deck scanAllAtributes(InputStream in) {
	Scanner s = null;
	List<String> clist = new ArrayList<String>();
	Deck deck = new Deck();
	Iterator<String> i;
	@SuppressWarnings("unused")
	int id = 0;
	try {
		s = new Scanner(new BufferedInputStream(in));
		s.useDelimiter(";\n");
		while (s.hasNext()) {
			clist.add(s.next());
		}
		i = clist.iterator();
		while (i.hasNext()) {
			Card c = new Card(i.next(), i.next(), i.next(), i.next(), i.next(), i.next(), i.next(), i.next(), i.next(), i.next());
			deck.addLast(c);
			id++;
		}
	} finally {
		s.close();
	}
	return deck;
}
	
public Deck scanAllAtributes(String path) {
		
		File f = new File(path);
		Scanner s = null;
		List<String> clist = new ArrayList<String>();
		Deck deck = new Deck();
		Iterator<String> i;
		@SuppressWarnings("unused")
		int id = 0;
		try {
			s = new Scanner(new BufferedInputStream(new FileInputStream(f)));
			s.useDelimiter(";\n");
			while (s.hasNext()) {
				clist.add(s.next());
			}
			i = clist.iterator();
			while (i.hasNext()) {
				Card c = new Card(i.next(), i.next(), i.next(), i.next(), i.next(), i.next(), i.next(), i.next(), i.next(), i.next());
				deck.addLast(c);
				id++;
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(new JFrame("Error."), "Deck Error.",
					"Sorry!", JOptionPane.ERROR_MESSAGE);
		} finally {
			s.close();
		}
		return deck;
	}

public Deck scanAllAtributesWithImg(String path) {
	
	File f = new File(path);
	Scanner s = null;
	List<String> clist = new ArrayList<String>();
	Deck deck = new Deck();
	Iterator<String> i;
	@SuppressWarnings("unused")
	int id = 0;
	try {
		s = new Scanner(new BufferedInputStream(new FileInputStream(f)));
		s.useDelimiter(";\n");
		while (s.hasNext()) {
			clist.add(s.next());
		}
		i = clist.iterator();
		while (i.hasNext()) {
			Card c = new Card(i.next(), i.next(), i.next(), i.next(), i.next(), i.next(), i.next(), i.next(), i.next(), i.next());
			String encimage = i.next();
			byte[] bytes = dec.decode(encimage);
			ImageIcon im = new ImageIcon(bytes);
			c.imCard = im;
			deck.addLast(c);
			id++;
		}
	} catch (FileNotFoundException e) {
		JOptionPane.showMessageDialog(new JFrame("Error."), "Deck Error.",
				"Sorry!", JOptionPane.ERROR_MESSAGE);
	} catch (IllegalArgumentException e) {
		JOptionPane.showMessageDialog(new JFrame("Error."), "Error decoding base64 encoded image!",
				"Sorry!", JOptionPane.ERROR_MESSAGE);
	} finally {
		s.close();
	}
	return deck;
}

public Deck scanZipFile(String str) {
	return scanZipFileWithXML(new File(str));
}

public Deck scanZipFileWithoutXML(File sFile) {
	Deck resDeck = new Deck();
	if (sFile != null) {
		try {
			ZipFile zf = new ZipFile(sFile);
			Enumeration<? extends ZipEntry> e = zf.entries();
			while (e.hasMoreElements()) {
				ZipEntry ze = (ZipEntry) e.nextElement();
				String name = ze.getName();
				Image crdImage = ImageIO.read(zf.getInputStream(ze));
				ImageIcon crdIcon = new ImageIcon(crdImage);
				Card card = new Card(name, crdIcon);
				resDeck.add(card);
			}
			zf.close();
		} catch (ZipException e) {
			showError(e);
		} catch (IOException e) {
			showError(e);
		}
	}
	return resDeck;
}

public Deck scanZipFileWithXML(File sFile) {
	Deck resDeck = new Deck();
	if (sFile != null) {
		try {
			ZipFile zf = new ZipFile(sFile);
			ZipEntry ze = zf.getEntry("metadata.xml");
			InputStream istream = zf.getInputStream(ze);
			XMLCardReader reader = new XMLCardReader(istream);
			resDeck = reader.getDeck();

			System.out.println("No of main cards : " + resDeck.size());
			System.out.println("No of extra cards : " + resDeck.getExtraDeck().size());

			// Adding images
			for (Card c : resDeck) {
				ZipEntry card_img = zf.getEntry(c.name);
				Image crdImage = ImageIO.read(zf.getInputStream(card_img));
				ImageIcon crdIcon = new ImageIcon(crdImage);
				c.setImCard(crdIcon);
			}

			if (resDeck.hasExtras()) {
				for (Card c : resDeck.getExtraDeck()) {
					ZipEntry card_img = zf.getEntry("extras/" + c.name);
					Image crdImage = ImageIO.read(zf.getInputStream(card_img));
					ImageIcon crdIcon = new ImageIcon(crdImage);
					c.setImCard(crdIcon);
				}
			}

			zf.close();
		} catch (ZipException e) {
			showError(e);
		} catch (IOException e) {
			showError(e);
		}
	}
	return resDeck;
}

public Deck scanXMLFile() {
	XMLCardReader xcr = new XMLCardReader();
	if (xcr.getDeck() != null) {
		Deck deck = new Deck(xcr.getDeck());
		return deck;
	} else {
		return null;
	}
}

public Deck scanXMLFile(InputStream in) {
	XMLCardReader xcr = new XMLCardReader(in);
	if (xcr.getDeck() != null) {
		Deck deck = new Deck(xcr.getDeck());
		return deck;
	} else {
		return null;
	}
}


private void showError(Exception e) {
	JOptionPane.showMessageDialog(this, "Error! " + e.getMessage(), e.getClass().getName(), JOptionPane.ERROR_MESSAGE);	
}


public void writeToZip(Deck deck) {
	JFileChooser files = new JFileChooser();
	int status = files.showSaveDialog(new JFrame());
	if (status == JFileChooser.APPROVE_OPTION) {
		File f = files.getSelectedFile();
		if (f != null) {
			try {
				ZipOutputStream zstream = new ZipOutputStream(new FileOutputStream(f));
				zstream.setLevel(9);
				
				for (Card c : deck) {
					BufferedImage buff = (BufferedImage) c.getImCard().getImage();					
					ZipEntry ze = new ZipEntry(c.name);
					zstream.putNextEntry(ze);
					ImageIO.write(buff, "png", zstream);
					zstream.closeEntry();
				}

				if (deck.hasExtras()) {
					for (Card c2 : deck.getExtraDeck()) {
						BufferedImage buff = (BufferedImage) c2.getImCard().getImage();
						ZipEntry ze = new ZipEntry("extras/" + c2.name);
						zstream.putNextEntry(ze);
						ImageIO.write(buff, "png", zstream);
						zstream.closeEntry();
					}
				}
				
				ZipEntry ze = new ZipEntry("metadata.xml");
				zstream.putNextEntry(ze);
				writeMetadata(deck, zstream);
				zstream.closeEntry();
				zstream.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}


private void writeMetadata(Deck deck, ZipOutputStream zstream) {
	XMLCardWriter writer = new XMLCardWriter(deck);
	System.out.println("No of main cards : " + deck.size());
	System.out.println("No of extra cards : " + deck.getExtraDeck().size());
	try {
		writer.writeDeck(zstream);
	} catch (XMLStreamException e) {
		e.printStackTrace();
	}
}

}
