package com.tornyak.planningpoker.cards;

public class Card
{
    private final Value value;
    private final String name;
    
    public Card(Value value, String name)
    {
        this.value = value;
        this.name = name;
    }

    /**
     * @return the value
     */
    public Value getValue()
    {
        return value;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

   

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Card [value=" + value + ", name=" + name + "]";
    }
    
    

}
