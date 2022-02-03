package clz;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Deck extends ArrayDeque<Card> {
    private List<Card> extras;

    public Deck() {
        extras = new ArrayList<Card>();
    }

    public Deck(Collection<Card> list) {
        super(list);
    }

    public void addAsExtra(Card extraCard) {
        extras.add(extraCard);
    }

    public Card getExtra(int i) {
        return extras.get(i);
    }

    public List<Card> getExtraDeck() {
        return extras;
    }

    public boolean hasExtras() {
        boolean hasExtra = false;
        if (extras.size() > 0) {
            hasExtra = true;
        }
        return hasExtra;
    }
}
