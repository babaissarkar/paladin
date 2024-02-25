package wscrap;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import clz.Card;
import clz.ImageUtils;

/** Fetch card images from DMwiki when the card name is given */

public class Scrapper {
	private static String lastImgURL = "";
	private static String baseURL;
	public static final String DM_URL = "https://duelmasters.fandom.com/wiki/";
	public static final String DMPS_URL = "https://duelmastersplays.fandom.com/wiki/";
	
	public static String getLastURL() {
		return Scrapper.lastImgURL;
	}
	
	private static void setLastURL(String url) {
		Scrapper.lastImgURL = url;
	}
	
	public static void setBaseURL(String url) {
		Scrapper.baseURL = url;
	}
	
	public static void save(String name) throws IOException {
		String rname = name.replace(" ", "_"); //.replace("/", "%2F");
		
		Document doc = Jsoup.connect(baseURL + rname).get();
		
		Element body = doc.body();
		Elements div = body.getElementsByTag("div");
		for (Element e : div) {
			if(e.attr("class").equals("mw-parser-output")) {
				System.out.println(name);
				for (Element e2 : e.select("a[href]")) {
					String linkname = e2.attr("href");
					if (linkname.contains("jpg")) {
						System.out.println(linkname + "\n");
						Scrapper.setLastURL(linkname);
						InputStream in = new URL(linkname).openStream();
						Files.copy(in, Paths.get("/home/ssarkar/" + name.replace("/", "__") + ".webp"), StandardCopyOption.REPLACE_EXISTING);
						in.close();
						break;
					}
				}
				
			}
		}
	}
	
	public static void showImage(String name) {
//		final int WIDTH = 500;
//		final int HEIGHT = 500;
		ImageIcon ico = null;
		JFrame frmMain = new JFrame();				
		frmMain.setTitle("Image");
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension size = kit.getScreenSize();

		
		JLabel lblImage = new JLabel();
		try {
			ico = ImageUtils.scale(getImage(name), 400);
			lblImage.setIcon(ico);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frmMain, "Error fetching image!");
		}
		int w = ico.getIconWidth() + 50;
		int h = ico.getIconHeight() + 50;
		frmMain.setSize(new Dimension(w, h));
		frmMain.setLocation((size.width-w)/2, (size.height-h)/2);
		
		lblImage.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		frmMain.add(lblImage);
		frmMain.setVisible(true);
	}
	
	public static void showText(String name) {
//		final int WIDTH = 500;
//		final int HEIGHT = 500;
		ImageIcon ico = null;
		JFrame frmMain = new JFrame();				
		frmMain.setTitle("Image");
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension size = kit.getScreenSize();

		
		int w = 400;
		int h = 400;
		frmMain.setSize(new Dimension(w, h));
		frmMain.setLocation((size.width-w)/2, (size.height-h)/2);
		
		JTextArea jta = new JTextArea(50, 5);
//		jta.setText(getData(name));
		
//		lblImage.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		frmMain.add(new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		frmMain.setVisible(true);
	}

	public static ImageIcon getImage(String name) throws IOException {
		BufferedImage img = null;
		String rname = name.replace(" ", "_"); //.replace("/", "%2F");
		
		Document doc = Jsoup.connect(baseURL + rname).get();
		
		Element body = doc.body();
		Elements div = body.getElementsByTag("div");
		for (Element e : div) {
			if(e.attr("class").equals("mw-parser-output")) {
				System.out.println("Name : " + name);
				for (Element e2 : e.select("a[href]")) {
//					System.out.println("URL : " + linkname + "\n");
					String linkname = e2.attr("href");
					if (linkname.contains("jpg")||linkname.contains("png")) {
						System.out.println("URL : " + linkname + "\n");
						InputStream in = new URL(linkname).openStream();
						img = ImageIO.read(in);
						in.close();
						break;
					}
				}
				
			}
		}
		
		return new ImageIcon(img);
	}
	
	public static Card getData(String name, boolean fetchImage, boolean isCustom, String custURL) throws IOException {
		Card c = new Card();
		
		String rname = name.replace(" ", "_"); //.replace("/", "%2F");
		Document doc = Jsoup.connect(baseURL + rname).get();
		Elements table = doc.body().getElementsByTag("table");
		
		if (fetchImage) {
			if (!isCustom) {
				Elements div = doc.body().getElementsByTag("div");
				for (Element e : div) {
					if (e.attr("class").equals("mw-parser-output")) {
						System.out.println("Name : " + name);
						for (Element e2 : e.select("a[href]")) {
							String linkname = e2.attr("href");
							if (linkname.contains("jpg")||linkname.contains("png")) {
								System.out.println("URL : " + linkname + "\n");
								Scrapper.setLastURL(linkname);
								InputStream in = new URL(linkname).openStream();
								c.imCard = new ImageIcon(ImageIO.read(in));
								in.close();
								break;
							}
						}

					}
				} 
			} else {
				Scrapper.setLastURL(custURL);
				InputStream in = new URL(custURL).openStream();
				c.imCard = new ImageIcon(ImageIO.read(in));
				in.close();
			}
		}
		
		for (Element e : table) {
			if(e.attr("class").equals("wikitable")) {
//				BufferedWriter writer = Files.newBufferedWriter(Path.of("/home/ssarkar/out.html"));
				Elements th = e.getElementsByTag("th");
				Elements tr = e.getElementsByTag("td");
				
				c.name = th.get(0).text();
				
				for(int i = 0; i < tr.size(); i++) {
//					writer.write(node.nodeName() + ":" + node.text() + "\n");
					if (tr.get(i).text().contains("Race")) {
						i++;
//						writer.write(tr.get(i).text());
						c.subtype = tr.get(i).text();
					} else if (tr.get(i).text().contains("Mana Cost")) {
						i++;
						c.energy[3] = Integer.parseInt(tr.get(i).text());
					} else if (tr.get(i).text().contains("Mana Number")) {
						i++;
						c.eno[0] = 0;
						c.eno[3] = Integer.parseInt(tr.get(i).text());
					} else if (tr.get(i).text().contains("Card Type")) {
						i++;
						c.type = tr.get(i).text();
					} else if (tr.get(i).text().contains("Civilization")) {
						i++;
						c.civility = tr.get(i).text();
					} else if (tr.get(i).text().contains("English Text")) {
						i++;
						String[] effects = tr.get(i).text().split("■");
						c.effects = new String[effects.length-1];
						for (int j = 0; j < c.effects.length; j++) {
							c.effects[j] = effects[j+1];
						}
					} else if (tr.get(i).text().contains("Power")) {
						i++;
						String s = tr.get(i).text();
						if (s.endsWith("+")) {
							String text = tr.get(i).text();
							c.power = Integer.parseInt(text.substring(0, text.length() - 1));
						} else {
							c.power = Integer.parseInt(tr.get(i).text());
						}
					}
				}
				
//				writer.write(c.toString());
//				writer.close();
			}
		}
		return c;

	}

	public static void main(String[] args) {
//		try {
//			BufferedReader reader = new BufferedReader(
//					Files.newBufferedReader(
//							Paths.get(URI.create("file:///home/ssarkar/GZ.untap.txt"))));
//			String line;
//			while( (line = reader.readLine()) != null ) {
//				//String[] tokens = line.split("\\s+");
//				
//				if (!(line.startsWith("//"))) {
//					//System.out.println(line.substring(2, line.length()));
//					new Scrapper(line.substring(2, line.length()));
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		String name = JOptionPane.showInputDialog("Enter Card Name :");
//		Scrapper.showImage(name);
		try {
			Scrapper.getData(name, true, false, "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
