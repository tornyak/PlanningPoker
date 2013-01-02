package com.tornyak.planningpoker.cards;

public class Deck
{
    
    private Card[] cards;

    public Deck()
    {
        cards = new Card[Value.values().length];
        
        for(int i = 0; i < cards.length; i++)
            cards[i] = new Card(Value.values()[i], Value.values()[i].toString());
        
    }
    
    public int getCardCount()
    {
        return cards.length;
    }

    public Card getCard(int index)
    {
        if(index < 0 || index >= cards.length )
            return null;
        else
            return cards[index];
    }
}
