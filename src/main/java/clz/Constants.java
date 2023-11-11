package clz;

import java.awt.Color;
import java.util.HashMap;

public class Constants {
    static String[] attr_names = {"Name", "Element", "Type", "Energy", "Subtype", "Effects", "Power", "Energy No.", "Damage Points", "ID"};
    static String[] symbols = {"\u2191", "\u2193", "~", "(", " "};

    /* Colors corresponding to each civility */
    static String[] civilities = {"Raenid", "Asarn", "Niaz", "Zivar", "Mayarth", "Kshiti", "Fire", "Water", "Nature", "Light", "Darkness"};
    //static String[] civ_symbols = {"🟡", "🔴", "🔵", "⚫", "🟠", "🟢"};
    static Color[] colors = {Color.YELLOW, Color.RED, Color.BLUE, Color.BLACK, Color.ORANGE, new Color(0, 102, 0), Color.red, Color.blue, new Color(0, 102, 0), Color.yellow, new Color(153, 51, 255)};
    
    static String[] energy_types = {"F", "R", "W", "G"};
    static Color[] energy_colors = {Color.YELLOW, Color.PINK, Color.CYAN, Color.DARK_GRAY};

    static HashMap<String, Color> mapCiv = new HashMap<String, Color>();
    static HashMap<String, Integer> numCiv = new HashMap<String, Integer>();
    static HashMap<Integer, String> numCivInv = new HashMap<Integer, String>();

	static String[] sorSubtypes = {"Aid", "Assail", "Attacker", "Convey", "Locate", "Protect"};
	static String[] trpSubtypes = {"Aid", "Assail", "Convey", "Defender", "Locate", "Protect"};

    static {
        for (int i = 0; i < colors.length; i++) {
            mapCiv.put(civilities[i], colors[i]);
            numCiv.put(civilities[i], i);
            numCivInv.put(i, civilities[i]);
        }
    }
}
