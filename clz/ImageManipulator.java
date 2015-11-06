package clz;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

public final class ImageManipulator {
	private static BufferedImage lastImage;
	
	private ImageManipulator() {
	};
	
	public static ImageIcon decode(String encodedText) {
		Base64.Decoder dec = Base64.getDecoder();
		ImageIcon im = new ImageIcon(dec.decode(encodedText));
		return im;
	}
	
	public static void main(String[] args) {
		Card c = new Card("Ring of Hope", "Armour", "Ring", "Raenid", "F4R0Q0", "2000", "F1R0Q0", "0", "", "0");
		c.effects = new String[2];
		c.effects[0] = "When the armored character is destroyed, decrease its power by 5000 for one turn instead.";
		c.effects[1] = "Hope never ends.";
		c.imCard = null;
		ImageManipulator.createImage(c);
		ImageManipulator.saveImage();
	}
	
	public static BufferedImage createImage(Card c) {
		int width = 300, height = 415;
		BufferedImage cim = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = cim.createGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		RenderingHints rh2 = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.addRenderingHints(rh);
		g.addRenderingHints(rh2);
		g.setStroke(new BasicStroke(2f));
		Font fnt = new Font("DejaVu Sans", Font.PLAIN, 14);
		g.setFont(fnt);
		FontMetrics fm = g.getFontMetrics(fnt);
		
		if (c.imCard != null) {
			AlphaComposite acom = AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, 0.55f);
			g.setComposite(acom);
		}
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(1, 1, width - 2, height - 2);
		if (c.civility.equalsIgnoreCase("Raenid")) {
			g.setColor(Color.YELLOW);
		} else if (c.civility.equalsIgnoreCase("Asarn")) {
			g.setColor(Color.RED);
		} else if (c.civility.equalsIgnoreCase("Mayarth")) {
			g.setColor(Color.ORANGE);
		} else if (c.civility.equalsIgnoreCase("Zivar")) {
			g.setColor(Color.BLACK);
		} else if (c.civility.equalsIgnoreCase("Niaz")) {
			g.setColor(Color.CYAN);
		} else if (c.civility.equalsIgnoreCase("Kshiti")) {
			g.setColor(Color.GREEN);
		}
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
		g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 20));
		g.drawString(name, 20, 35);
		String type = c.type.toUpperCase();
		g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 16));
		g.drawString(type, 16, 70);
		String subtype = c.subtype;
		g.setFont(new Font("URW Palladio L", Font.ITALIC, 20));
		g.drawString(subtype, width/2 + 40, 70);
		String power = new Character(c.armourPowType).toString() + " " + c.power.toString();
		g.setFont(new Font("DejaVu Sans", Font.BOLD, 18));
		g.drawString(power, 18, height - 17);
		StringBuffer strEno = new StringBuffer();
		Integer[] enos = c.eno;
		for (int i = 0; i < enos.length; i++) {
			if (enos[i] > 1) {
				strEno.append(Card.SYMBOLS[i] + "" + enos[i].toString());
			} else if (enos[i] == 1) {
				strEno.append(Card.SYMBOLS[i]);
			}
		}
		g.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
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
			g.drawString(dpb, width - 40, height - 17);
		
		Integer[] cost = c.energy;
		int y;
		if ((cost[0] != 0) && (cost[1] != 0) && (cost[2] != 0)) {
			y = 67;
		} else {
			y = 75;
		}
		if (cost[0] != 0) {
			g.drawString(Card.SYMBOLS[0] + cost[0].toString(), width/2 - 15, y);
			y += g.getFontMetrics().getHeight() - 4;
		}
		if (cost[1] != 0) {
			g.drawString(Card.SYMBOLS[1] + cost[1].toString(), width/2 - 15, y);
			y += g.getFontMetrics().getHeight() - 4;
		}
		if (cost[2] != 0) {
			g.drawString(Card.SYMBOLS[2] + cost[2].toString(), width/2 - 15, y);
		}
		
		g.setFont(new Font("URW Bookman L", Font.PLAIN, 16));
		int y2 = 120;
		Vector<String> vlines = new Vector<String>();
		StringBuffer sbLine = new StringBuffer();
		for (String effect : c.effects) {
			if (!effect.equals("")) {
				String[] words = effect.split(" ");
				int wordsPerLine = 5;
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
					y2 += fm.getHeight() + 2;
				}
				vlines.removeAllElements();
				y2 += 6;
			}
		}
		
		if (c.imCard != null) {
			BufferedImage im = null;
			im = (BufferedImage) scale(c.imCard, width - 14, height - 14)
					.getImage();
			g.drawImage(im, 7, 7, null);
		}
		g.dispose();
//		showImage(cim);
//		for(String s : ImageIO.getWriterFormatNames()) {
//			System.out.println(s);
//		}
		lastImage = cim;
		return lastImage;
	}
	
	public static void saveImage() {
		JFileChooser files = new JFileChooser();
		int stat = files.showSaveDialog(new JFrame());
		if (stat == JFileChooser.APPROVE_OPTION) {
			File f = files.getSelectedFile();
			try {
			if (!f.exists()) {
				f.createNewFile();
			}
				ImageIO.write(lastImage, "png", f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}

	private static void drawCircle(Graphics2D g, int width) {
		g.drawOval(width/2 - 25, 50, 50, 50);
	}

	public static byte[] encode(File selImage) {
		Base64.Encoder benc = Base64.getEncoder();
		byte[] imbytes = null;
		if (selImage != null) {
			try {
				BufferedImage im = ImageIO.read(selImage);
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				ImageIO.write(im, "jpg", out);
				out.flush();
				imbytes = benc.encode(out.toByteArray());
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return imbytes;
	}
	
	public static ImageIcon scale(Icon im, int width, int height) {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.createGraphics();
		g.drawImage(((ImageIcon) im).getImage(), 0, 0, width, height, 0, 0, im.getIconWidth(), im.getIconHeight(), null);
		g.dispose();
		ImageIcon im2 = new ImageIcon(bi);
		return im2;
	}
	
	public static ImageIcon rotate(Icon im) {
		Image img = ((ImageIcon) im).getImage();
		int h = im.getIconHeight();
		int w = im.getIconWidth();
		BufferedImage bi = new BufferedImage(h, w, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		g.translate(h, 0);
		g.rotate(Math.toRadians(90));
		g.drawImage(img, 0, 0, null);
		g.dispose();
		ImageIcon im2 = new ImageIcon(bi);
		return im2;
	}
	
	public static void showImage(Image im) {
		JFrame imgFrame = new JFrame("Image");
		imgFrame.setSize(500, 500);
		ImageIcon icon = new ImageIcon(im);
		JLabel lblImg = new JLabel(icon);
		imgFrame.getContentPane().add(lblImg);
		imgFrame.setVisible(true);
	}
}
