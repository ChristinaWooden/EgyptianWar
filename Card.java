public class Card
{
  public static final String FACES[] = {"ACE","TWO","THREE","FOUR",
                                        "FIVE","SIX","SEVEN","EIGHT","NINE","TEN","JACK","QUEEN","KING"};

  private String suit;
  private int face;

  //constructors
  public Card(){
  	suit = "SPADES";
  	face = 3;
  }

  public Card(String s){
  	suit = s;
  	face = 3;
  }

  public Card(int f){
  	suit = "SPADES";
  	face = f;
  }

  public Card(int f, String s){
  	suit = s;
  	face = f;
  }


  // modifiers
  public void setSuit(String s){
  	suit = s;
  }

  public void setFace(int f){
  	face = f;
  }
 

  //accessors
  public String getSuit(){
  	return suit;
  }

  public int getFace(){
  	return face;
  }

  public boolean equals(Object obj)
  {
  	if (((Card)obj).getSuit().equals(suit)){
  		if (((Card)obj).getFace() == face){
  			return true;
  		}
  	}
    return false;
  }

  //toString

  public String toString(){
     return FACES[face] + " of " + getSuit() + " | value = " + getValue();  	
  }
        
}