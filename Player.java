import java.util.List;
import java.util.ArrayList;


public class Player{
	private int wins;
	private ArrayList<Card> hand;

	public Player(){
		hand = new ArrayList<Card>();
		wins = 0;
	}

	public void addCard(Card c){
		hand.add(c);
	}

	public int getHandSize(){
		return hand.size();
	}

	public int getNumWins(){
		return wins;
	}

	public void setNumWins(int num){
		wins = num;
	}

	//during each play, a player will place down the "last" card in their ArrayList in the center pile.  The returned Card will be added to the center deck.
	//player will still be able to slap into the game if they have no cards left
	public Card placeCard(){
		if (hand.size() > 0){
			return hand.remove(hand.size()-1);
		}
		return null;
	}


	public boolean slap(){
		return true;
	}

	public Card burn(){
		if (hand.size() > 0){
			return hand.remove(hand.size()-1);
		}
		return null;
	}



}