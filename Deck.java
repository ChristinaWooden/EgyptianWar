import java.util.ArrayList;
import java.util.Collections;

public class Deck
{
  public static final int NUMFACES = 13;
  public static final int NUMSUITS = 4;
  public static final int NUMCARDS = 52;

  public static final String SUITS[] = {"CLUBS","SPADES","DIAMONDS","HEARTS"};

  private int topCardIndex;
  private ArrayList<Card> stackOfCards;

  public Deck ()
  {
    //initialize data - stackOfCards - topCardIndex
    stackOfCards = new ArrayList<Card>();         
    Card c1 = new Card();   
    topCardIndex = NUMCARDS -1 ;
                
    for (int s = 0; s < 4; s++){
    	for (int f = 0; f < 13; f++){
    		c1 = new Card(f,SUITS[s]);
    		stackOfCards.add(c1);
    	}
    }
    //loop through suits
    //loop through faces
    //add in a new card
                
  }

  //modifiers
  public void shuffle ()
  {
    Collections.shuffle(stackOfCards);
    topCardIndex = stackOfCards.size() -1;
  }

  //accessors
  public int size ()
  {
    return stackOfCards.size();
  }

  public int numCardsLeft()
  {
    return topCardIndex + 1;
  }

  public Card nextCard()
  {
  	if (topCardIndex < 0){
  		return null;
  	}
    return stackOfCards.get(topCardIndex--);
  }

  public String toString()
  {
    return stackOfCards + "   topCardIndex = " + topCardIndex;
  } 
}