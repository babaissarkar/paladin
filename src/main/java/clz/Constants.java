package clz;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Constants {
	public static int CARD_WIDTH = 90;
	
//    public static String[] attr_names;
    //= {"Name", "Element", "Type", "Energy", "Subtype", "Effects", "Power", "Energy No.", "Damage Points", "ID"};
    
    //static String[] civ_symbols = {"🟡", "🔴", "🔵", "⚫", "🟠", "🟢"};
    public static String[] civilities;
    public static Color[] colors;
    public static Properties mainProp, colProp;
    
    static String[] symbols = {"\u2191", "\u2193", "~", "(", " "};
    static String[] energy_types = {"F", "R", "W", "G"};
    static Color[] energy_colors = {Color.YELLOW, Color.PINK, Color.CYAN, Color.DARK_GRAY};

    static HashMap<String, Color> mapCiv = new HashMap<String, Color>();
    static HashMap<String, Integer> numCiv = new HashMap<String, Integer>();
    static HashMap<Integer, String> numCivInv = new HashMap<Integer, String>();

	static String[] sorSubtypes = {"Aid", "Assail", "Attacker", "Convey", "Locate", "Protect"};
	static String[] trpSubtypes = {"Aid", "Assail", "Convey", "Defender", "Locate", "Protect"};

    static {
    	// Load from properties file instead of hard-coded constants.
    	colProp = new Properties();
    	mainProp = new Properties();
    	try {
			colProp.load(Constants.class.getResourceAsStream("/config/color.properties"));
			int size = colProp.size();
			civilities = new String[size];
			colors = new Color[size];
			int i = 0;
			for (var entry : colProp.entrySet()) {
				civilities[i] = entry.getKey().toString();
				colors[i] = Color.decode(entry.getValue().toString());
				i++;
			}
			
			mainProp.load(Constants.class.getResourceAsStream("/config/main.properties"));
//			attr_names = new String[mainProp.size()];
//			i = 0;
//			for (var entry : mainProp.entrySet()) {
//				attr_names[i] = entry.getValue().toString();
//				i++;
//			}
			
		} catch (IOException e) {
			System.err.println("Missing resource file config/color.properties.");
			e.printStackTrace();
			System.exit(1);
		}
    	
        for (int i = 0; i < colors.length; i++) {
            mapCiv.put(civilities[i], colors[i]);
            numCiv.put(civilities[i], i);
            numCivInv.put(i, civilities[i]);
        }
    }
}
