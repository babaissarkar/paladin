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
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class CScan extends JFrame {
	
	
	private static final long serialVersionUID = -1826147733065781359L;
	public int j;
	
	public CScan() {
		setTitle("Open");
	}
	

	public Deque<Card> scanNmeAndImg(String path) {
		
		Scanner s = null;
		List<String> clist = new ArrayList<String>();
		Deque<Card> deck = new ArrayDeque<Card>();
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
	
public Deque<Card> scanAllAtributes(String path) {
		
		File f = new File(path);
		Scanner s = null;
		List<String> clist = new ArrayList<String>();
		Deque<Card> deck = new ArrayDeque<Card>();
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

public Deque<Card> scanAllAtributesWithImg(String path) {
	
	File f = new File(path);
	Scanner s = null;
	List<String> clist = new ArrayList<String>();
	Deque<Card> deck = new ArrayDeque<Card>();
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
			byte[] bytes = Base64.decode(encimage);
			ImageIcon im = new ImageIcon(bytes);
			c.imCard = im;
			deck.addLast(c);
			id++;
		}
	} catch (FileNotFoundException e) {
		JOptionPane.showMessageDialog(new JFrame("Error."), "Deck Error.",
				"Sorry!", JOptionPane.ERROR_MESSAGE);
	} catch (Base64DecodingException e) {
		JOptionPane.showMessageDialog(new JFrame("Error."), "Decoding Error.",
				"Sorry!", JOptionPane.ERROR_MESSAGE);
	} finally {
		s.close();
	}
	return deck;
}

public Deque<Card> scanZipFile(String str) {
	Deque<Card> resDeck = new ArrayDeque<Card>();
	File sFile = new File(str);
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

//public Deque<Card> scanXMLFile(InputStream in) {
//	XMLCardReader xcr = new XMLCardReader(in);
//	Deque<Card> deck = new ArrayDeque<Card>(xcr.getCards());
//	return deck;
//}


private void showError(Exception e) {
	JOptionPane.showMessageDialog(this, "Error! " + e.getMessage(), e.getClass().getName(), JOptionPane.ERROR_MESSAGE);	
}

}
