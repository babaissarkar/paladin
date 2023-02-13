package clz;


import java.util.*;

public class Deck extends ArrayDeque<Card> {
    private List<Card> extras = new ArrayList<Card>();
    //private HashMap<Card, Integer> counts;

    public Deck() {
    }

    public Deck(Collection<Card> list) {
        super(list);
    }

    public void addAsExtra(Card... extraCards) {
        for (Card extraCard : extraCards) {
            extras.add(extraCard);
        }
    }

    public Card getExtra(int i) {
        return extras.get(i);
    }

    public List<Card> getExtraDeck() {
        return extras;
    }
    
    public void setExtraDeck(List<Card> extraDeck) {
    	this.extras = extraDeck;
    }

    public boolean hasExtras() {
//        boolean hasExtra = (extras != null) && (extras.size() > 0);
//        return hasExtra;
        //if (this.extras != null) {
        //System.out.println(this.extras.size());
        if (this.extras.size() > 0) {
            return true;
        } else {
            return false;
        }
        //} else {
           // return false;
        //}
    }

    @Override
    public boolean isEmpty() {
        return (super.isEmpty() || (this.hasExtras() && this.getExtraDeck().isEmpty()));
    }
}
