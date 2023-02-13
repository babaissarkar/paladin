package clz;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static clz.Constants.energy_types;

public class EnergyBar extends JPanel {
    private int[] counts; // This will be shown.
    private int[] counts2; // This will be hidden but used in calculations.
    private JProgressBar[] energybars;
    private int available_energy;
    private int used_energy;

    public EnergyBar() {
		counts = new int[energy_types.length];
        counts2 = new int[10];
        energybars = new JProgressBar[energy_types.length];
        
        Arrays.fill(counts, 0);
        Arrays.fill(counts2, 0);
        for (int i = 0; i < energybars.length; i++) {
            energybars[i] = new JProgressBar(JProgressBar.HORIZONTAL);
        }

        createGUI();
    }
    
    public EnergyBar(int count) {
		counts = new int[count];
        counts2 = new int[10];
        energybars = new JProgressBar[count];
        
        Arrays.fill(counts, 0);
        Arrays.fill(counts2, 0);
        for (int i = 0; i < energybars.length; i++) {
            energybars[i] = new JProgressBar(JProgressBar.HORIZONTAL);
        }

        createGUI();
    }

    private void createGUI() {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for (int i = 0; i < energybars.length; i++) {
            energybars[i].setStringPainted(true);
            energybars[i].setString("0");
            energybars[i].setForeground(Constants.energy_colors[i]);
            energybars[i].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            energybars[i].setValue(0);
//            bar.setValue(10);
            this.add(energybars[i]);
        }
    }

    private void update() {

       int sum = Arrays.stream(counts).sum();
//       System.out.println(sum);

        for (int i = 0; i < counts.length; i++) {
            if (sum != 0) {
                int percent = (counts[i] * 100)/sum;
//                System.out.println(i + ": " + percent);
                if (energybars[i] != null) {
                    energybars[i].setValue(percent);
                    energybars[i].setString(String.format("%d", counts[i]));
                    energybars[i].repaint();
                }
            } else {
                if (energybars[i] != null) {
                    energybars[i].setValue(0);
                    energybars[i].setString("0");
                    energybars[i].repaint();
                }
            }
        }
//
        SwingUtilities.updateComponentTreeUI(this);

    }

    public int getCivNo() {
        return counts.length;
    }

    public void addEnergy(int count, int type) {
        if (type < counts.length) {
            counts[type] += count;
        } else {
            int type2 = type - counts.length;
            if (type2 < counts2.length) {
                counts2[type2] += count;
            }
        }
        update();
    }

    public void removeEnergy(int count, int type) {
        if (type < counts.length) {
            counts[type] -= count;
        } else {
            int type2 = type - counts.length;
            if (type2 < counts2.length) {
                counts2[type2] -= count;
            }
        }
        update();
    }
    
    // Used for summoning/casting. Logic to be refined later.
    
    public int getTotal() {
    	return Arrays.stream(counts).sum();
    }
    
    public int getAvailable() {
    	available_energy = getTotal() - getUsed();
    	return available_energy;
    }
    
    public int getUsed() {
    	return this.used_energy;
    }
    
    private void setUsed(int used) {
    	used_energy = used;
    }
    
    public void use(int used) {
    	// Logic to be refined later
    	setUsed(getUsed() + used);
    	System.out.println("Energy bar count :" + getAvailable() + "/" + getTotal());
    }
    
    public void refresh() {
    	for (int i = 0; i < counts.length; i++) {
    		counts[i] = 0;
    	}
    	
    	setUsed(0);
    	
    	update();
    }
    
    ////////////////////////////////////////////////////////////
}
