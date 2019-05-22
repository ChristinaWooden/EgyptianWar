public class Player{
	private int wins;
	private ArrayList<Card> hand;


	public Player(int num){
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

	public Card placeCard(){
		return hand.remove(hand.size()-1);
	}


	public boolean slap(){
		return true;
	}

	public Card burn(){
		return hand.remove(hand.size()-1);
	}



}