package clz;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public final class ImageManipulator {
	private ImageManipulator() {
	};
	
	public static ImageIcon decode(String encodedText) {
		Base64.Decoder dec = Base64.getDecoder();
		ImageIcon im = new ImageIcon(dec.decode(encodedText));
		return im;
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
}
