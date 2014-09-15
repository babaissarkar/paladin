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

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JFrame;

public class CScan extends JFrame {
	
	
	private static final long serialVersionUID = -1826147733065781359L;
	public int j;
	
	public CScan() {
		setTitle("Open");
	}
	

	public Stack<Card> scan(String path) {
		
		Scanner s = null;
		List<String> clist = new ArrayList<String>();
		Stack<Card> deck = new Stack<Card>();
		Iterator<String> i;
		@SuppressWarnings("unused")
		int id = 0;
		try {
			s = new Scanner(new BufferedInputStream(getClass().getResourceAsStream(path)));
			s.useDelimiter(";\n");
			while (s.hasNext()) {
				clist.add(s.next());
				//System.out.println(s.next()); //for debuging
			}
			i = clist.iterator();
			while (i.hasNext()) {
				//System.out.println(i.next()); //for debuging
				
				/*deck.addElement(new Card(i.next(), i.next(), i.next(), i.next(),
				i.next(), i.next(), i.next(), i.next(), i.next())); 
				Used when a deckfile states all atrributes of a card*/
				deck.addElement(new Card(i.next(), i.next()));
				id++;
			}
		} finally {
			s.close();
		}
		return deck;
	}

}
