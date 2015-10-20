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
	
	public static void main(String[] args) {
		Card c = new Card("Ring of Hope", "Ring", "Armour", "Raenid", "F4B2Q0", "2000", "F1B0Q0", "R", "");
//		c.name = "Ring of Hope";
//		c.type = "Armour";
//		c.subtype = "Ring";
		c.armourPowType = '+';
//		c.power = 2000;
//		c.eno[0] = 1;
//		c.eno[1] = 1;
//		c.dpbonus = 1;
//		c.cost[0] = 0;
//		c.cost[1] = 4;
//		c.cost[2] = 4;
		
//		ImageManipulator.createImage(c);
//		ImageManipulator.saveImage();
		System.out.println(c.toString());
	}
	
	public static ImageIcon decode(String encodedText) {
		Base64.Decoder dec = Base64.getDecoder();
		ImageIcon im = new ImageIcon(dec.decode(encodedText));
		return im;
	}
	
	public static BufferedImage createImage(Card c) {
		int width = 285, height = 405;
		BufferedImage cim = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = cim.createGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHints(rh);
		g.setStroke(new BasicStroke(2f));
		Font fnt = new Font("DejaVu Sans", Font.PLAIN, 14);
		g.setFont(fnt);
		FontMetrics fm = g.getFontMetrics(fnt);
		
		AlphaComposite acom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f);
		g.setComposite(acom);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(1, 1, width - 2, height - 2);
		g.setColor(Color.YELLOW);
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
		if (c.dpbonus != 0) {
			String dpb = " ";
			if (c.dpbonus > 0) {
				dpb = "+" + c.dpbonus.toString();
			} else if (c.dpbonus < 0) {
				dpb = "-" + c.dpbonus.toString();
			}
			g.drawString(dpb, width - 40, height - 17);
		}
		
		Integer[] cost = c.cost;
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
		
		BufferedImage im = null;
		try {
			im = (BufferedImage) scale(new ImageIcon(ImageIO.read(
					ImageManipulator.class.getResourceAsStream("/images/hibiscus-3.jpg")))
					, width - 14, height - 14)
					.getImage();
			g.drawImage(im, 7, 7, null);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		g.dispose();
		//showImage(cim);
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
