/*
 *      ImageUtils.java
 *      
 *      Copyright 2016-2024 Subhraman Sarkar <subhraman@subhraman-Inspiron>
 *      
 *      This program is free software; you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation; either version 2 of the License, or
 *      (at your option) any later version.
 *      
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *      
 *      You should have received a copy of the GNU General Public License
 *      along with this program; if not, write to the Free Software
 *      Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *      MA 02110-1301, USA.
 *      
 *      
 */


package clz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

public final class ImageUtils {
	private static BufferedImage lastImage;
	
	private ImageUtils() {
	};

	public static void main(String[] args) {
		Card c = new Card("Ring of Hope", "Armour", "Ring", "Raenid", "F4R0Q0(3)", "2000", "F1R0Q0", "0", "", "0");
		c.effects = new String[2];
		c.effects[0] = "When the armored character is destroyed, decrease its power by 5000 for one turn instead.";
		c.effects[1] = "Hope never ends.";
		try {
			c.imCard = new ImageIcon(ImageIO.read(ImageUtils.class.getResourceAsStream("/images/Ring2.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageUtils.createImage(c);
		ImageUtils.showImage(ImageUtils.rotate90(new ImageIcon(lastImage)).getImage());
	}
	
	public static BufferedImage createImage(Card c) {
		int width = 350, height = 462;
		BufferedImage cim = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = cim.createGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		RenderingHints rh2 = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.addRenderingHints(rh);
		g.addRenderingHints(rh2);
		g.setStroke(new BasicStroke(2f));
		Font fnt = new Font("Free Serif", Font.PLAIN, 14);
		g.setFont(fnt);
		FontMetrics fm = g.getFontMetrics(fnt);
		
//		if (c.imCard != null) {
//			AlphaComposite acom = AlphaComposite.getInstance(
//					AlphaComposite.SRC_OVER, 0.65f);
//			g.setComposite(acom);
//			BufferedImage im = null;
//			im = (BufferedImage) scale(c.imCard, width - 14, height - 14)
//					.getImage();
//			g.drawImage(im, 7, 7, null);
//		} else {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, width, height);
//		}
		g.setColor(Color.BLACK);
		g.drawRect(1, 1, width - 2, height - 2);

		g.setColor(Constants.mapCiv.get(c.civility));

		g.fillRect(2, 2, width - 4, height - 4);
		g.setColor(Color.WHITE);
		g.fillRect(7, 7, width - 14, height - 14);
		g.setColor(Color.BLACK);
		g.drawRect(7, 7, width - 14, height - 14);
		drawCircle(g, width);
		g.drawLine(7, 50, width - 9, 50);
		g.drawLine(7, 60 + fm.getHeight(), width/2 - 27, 60 + fm.getHeight());
		g.drawLine(width/2 - 27 + 52, 60 + fm.getHeight(), width - 9, 60 + fm.getHeight());
		g.drawLine(7, height - 40, width - 9, height - 40);
		g.drawLine(width/2 - 25, height - 40, width/2 - 20, height - 9);
		g.drawLine(width/2 + 25, height - 40, width/2 + 30, height - 9);
		
		String name = c.name.toUpperCase();
		Font titleFont = new Font(g.getFont().getFontName(), Font.BOLD, 22);
		if (g.getFontMetrics(titleFont).stringWidth(name) + 20 > width) {
			g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 16));
		} else {
			g.setFont(titleFont);
		}
		g.drawString(name, 16, 35);
		String type = c.type.toUpperCase();
		g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 18));
		g.drawString(type, 16, 70);
		String subtype = c.subtype;
		g.setFont(new Font("URW Palladio L", Font.ITALIC, 22));
		g.drawString(subtype, width/2 + 33, 70);
		String power;
		if (c.type.equalsIgnoreCase("Armour")) {
			if (c.power < 0) {
				power = "- " + c.power.toString();
			} else {
				power = "+ " + c.power.toString();
			}
		} else {
			if (c.power != 0) {
				power = c.power.toString();
			} else {
				power = " ";
			}
		}
		g.setFont(new Font("DejaVu Serif", Font.BOLD, 18));
		g.drawString(power, 18, height - 17);
		StringBuffer strEno = new StringBuffer();
		Integer[] enos = c.eno;
		for (int i = 0; i < enos.length; i++) {
			if (enos[i] > 1) {
				strEno.append(Constants.symbols[i] + "" + enos[i].toString());
			} else if (enos[i] == 1) {
				strEno.append(Constants.symbols[i]);
			}
		}
		//g.setFont(new Font(fnt.getName(), Font.BOLD, 16));
		g.drawString(strEno.toString(), width/2 - 18, height - 17);
		String dpb = " ";
		if (c.type.equalsIgnoreCase("Armour")) {
			if (c.damage > 0) {
				dpb = "+" + c.damage.toString();
			} else if (c.damage < 0) {
				dpb = "-" + c.damage.toString();
			} else {
				dpb = "+" + c.damage.toString();
			}
		} else {
			dpb = c.damage.toString();
		}
		if ((c.damage != 0) && (!c.subtype.equalsIgnoreCase("Character"))) {
			g.drawString(dpb, width - 40, height - 17);
		} else if ((c.damage == 0) && (c.subtype.equalsIgnoreCase("Character"))) {
			g.drawString(dpb, width - 40, height - 17);
		}
		Integer[] cost = c.energy;
		int y;
		if ((cost[0] != 0) && (cost[1] != 0) && (cost[2] != 0)) {
			y = 67;
		} else {
			y = 75;
		}
		if (cost[0] != 0) {
			g.drawString(Constants.symbols[0] + cost[0].toString(), width/2 - 15, y);
			y += g.getFontMetrics().getHeight() - 4;
		}
		if (cost[1] != 0) {
			g.drawString(Constants.symbols[1] + cost[1].toString(), width/2 - 15, y);
			y += g.getFontMetrics().getHeight() - 4;
		}
		if (cost[2] != 0) {
			g.drawString(Constants.symbols[2] + cost[2].toString(), width/2 - 15, y);
		}
		if ((c.generic == true) && (cost[3] != 0)) {
			g.drawString("(" + cost[3] + ")", width/2 - 12, y);
		}
		
//		g.setFont(new Font("URW Palladio L", Font.PLAIN, 26));
		g.setFont(new Font("URW Palladio L", Font.PLAIN, 20));
		int y2 = 120;
		Vector<String> vlines = new Vector<String>();
		StringBuffer sbLine = new StringBuffer();
		for (String effect : c.effects) {
			if (!effect.equals("")) {
				String[] words = effect.split(" ");
				int lineSpacing = 3, paraSpacing = 6;
				int wordsPerLine = 5; //Changed.
				for (int i = 0; i < (words.length / wordsPerLine) + 1; i++) {
					for (int j1 = 0; j1 < wordsPerLine; j1++) {
						int k = j1 + i * wordsPerLine;
						if (k < words.length) {
							String str = words[k];
							sbLine.append(str);
							if (k != words.length - 1) {
								sbLine.append(" ");
							}
						}
					}
					vlines.add(sbLine.toString());
					sbLine = new StringBuffer();
				}
				for (String line : vlines) {
					g.drawString(line, 15, y2);
					y2 += fm.getHeight() + lineSpacing;
				}
				vlines.removeAllElements();
				y2 += paraSpacing;
			}
		}
		
		g.setFont(new Font("Free Serif", Font.PLAIN, 14));
		g.dispose();
//		showImage(cim);
//		for(String s : ImageIO.getWriterFormatNames()) {
//			System.out.println(s);
//		}
		lastImage = cim;
		return lastImage;
	}
	
	public static void saveImage() {
		saveImage(lastImage);
	}
	
	public static void saveImage(BufferedImage im) {
		JFileChooser files = new JFileChooser();
		int stat = files.showSaveDialog(new JFrame());
		if (stat == JFileChooser.APPROVE_OPTION) {
			File f = files.getSelectedFile();
			try {
			if (!f.exists()) {
				f.createNewFile();
			}
				ImageIO.write(im, "png", f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void drawCircle(Graphics2D g, int width) {
		g.drawOval(width/2 - 25, 50, 50, 50);
	}
	
	public static ImageIcon scale(Icon im, int side) {
		// Preserve aspect ratio while scaling
		float aspectRatio = ((float) im.getIconWidth())/((float) im.getIconHeight());
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		RenderingHints rh2 = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Graphics2D g;
		BufferedImage bi;
		if (aspectRatio > 1) {
			// Landscape orientation
			// use side as height and calculate width from that
			int ratioHeight = Math.round(side/aspectRatio);
			bi = new BufferedImage(side, ratioHeight, BufferedImage.TYPE_INT_ARGB);
			g = bi.createGraphics();
			g.addRenderingHints(rh);
			g.addRenderingHints(rh2);

			g.drawImage(((ImageIcon) im).getImage(), 0, 0, side, ratioHeight, 0, 0, im.getIconWidth(), im.getIconHeight(), null);
		} else {
			// Portrait orientation
			// use side as width and calculate height from that
			int ratioWidth = Math.round(side*aspectRatio);
			bi = new BufferedImage(ratioWidth, side, BufferedImage.TYPE_INT_ARGB);
			g = bi.createGraphics();
			g.addRenderingHints(rh);
			g.addRenderingHints(rh2);

			g.drawImage(((ImageIcon) im).getImage(), 0, 0, ratioWidth, side, 0, 0, im.getIconWidth(), im.getIconHeight(), null);
		}
		g.dispose();
		ImageIcon im2 = new ImageIcon(bi);
		return im2;
	}
	
	@Deprecated
	public static BufferedImage scale(BufferedImage im, int width, int height) {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.createGraphics();
		g.drawImage(im, 0, 0, width, height, 0, 0, im.getWidth(), im.getHeight(), null);
		g.dispose();
		return bi;
	}
	
	public static ImageIcon rotate90(Icon im) {
		Image img = ((ImageIcon) im).getImage();
		int h = im.getIconHeight();
		int w = im.getIconWidth();
		BufferedImage bi = new BufferedImage(h, w+25, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		g.translate(h, 20);
		g.rotate(Math.toRadians(90));
		g.drawImage(img, 0, 0, null);
		g.dispose();
		ImageIcon im2 = new ImageIcon(bi);
		return im2;
	}
	
	public static ImageIcon flipVert(Icon im) {
		Image img = ((ImageIcon) im).getImage();
		int h = im.getIconHeight();
		int w = im.getIconWidth();
		//System.out.format("h=%d w=%d\n", h, w);
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		g.translate(w, h);
		g.rotate(Math.toRadians(180));
		g.drawImage(img, 0, 0, null);
		g.dispose();
		ImageIcon im2 = new ImageIcon(bi);
		return im2;
	}

	public static ImageIcon rotate270(Icon im) {
		return ImageUtils.rotate90(ImageUtils.flipVert(im));
	}
	
	public static void showImage(Image im) {
		JFrame imgFrame = new JFrame("Image");
		imgFrame.setSize(500, 500);
		ImageIcon icon = new ImageIcon(im);
		JLabel lblImg = new JLabel(icon);
		imgFrame.getContentPane().add(lblImg);
		imgFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		imgFrame.setVisible(true);
	}
}
