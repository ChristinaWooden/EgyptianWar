import static java.lang.System.*;
import java.awt.Color;

public class CardTestOne
{
  public static void main( String args[] )
  {
    Card one = new Card();
    out.println(one);

    Card two = new Card(1,"DIAMONDS");
    out.println(two);

    Card three = new Card(4,"CLUBS");
    out.println(three);
                
    Card four = new Card(12,"SPADES");
    out.println(four);
        
    Card five = new Card(12,"HEARTS");
    out.println(five);  
                
    Card six = new Card(9,"SPADES");
    out.println(six);                           

    out.println(one.equals(two));
    out.println(one.equals(one));               
    out.println(four.equals(five));     
    out.println(three.equals(four));                                            
  }
}