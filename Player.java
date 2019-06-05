import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


//public class Player implements Runnable{
//	private int wins;
//	private int place; //number of cards the player must place at any given moment
//	private ArrayList<Card> hand;
//	private Card recentCard = null;
//	private Socket socket;
//	private int playerNumber;
//	Scanner input;
//	PrintWriter output;
//	Player opponent;
//
//	public Player(Socket socket, int playerNumber){
//		hand = new ArrayList<Card>();
//		wins = 0;
//		place = 0;
//		this.socket = socket;
//		this.playerNumber = playerNumber;
//	}
//
//	public void addCard(Card c){
//		hand.add(c);
//	}
//
//	public int getHandSize(){
//		return hand.size();
//	}
//
//	public int getNumWins(){
//		return wins;
//	}
//
//	public void setNumWins(int num){
//		wins = num;
//	}
//
//	public void run() {
//		try {
//			setup();
//		} catch(Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {socket.close();} catch (IOException e) {}
//		}
//	}
//
//	public void setup() throws IOException {
//		input = new Scanner(socket.getInputStream());
//		output = new PrintWriter(socket.getOutputStream(),true);
//		output.println("WELCOME PLAYER " + playerNumber);
//		if (playerNumber == 1) {
//			currentPlayer = this;
//			output.println("MESSAGE Waiting for opponent to connect");
//		} else {
//			opponent = currentPlayer;
//			opponent.opponent = this;
//			opponent.output.println("MESSAGE Your move");
//		}
//	}
//
//	//the card played before the player determines the number of cards each player must play
//	public void setPlace(int p){
//		place = p;
//	}
//
//	public int getPlace(){
//		return place;
//	}
//
//	public int mustPlace(int face){
//		if (face > 0 && face < 10){
//			place = 1;
//		}
//		else if (face == 0){
//			place = 4;
//		}
//		else if (face == 10){
//			place = 1;
//		}
//		else if (face == 11){
//			place = 2;
//		}
//		else if (face == 12){
//			place = 3;
//		}
//		return place;
//	}
//
//	//call this method only after you have called placeCard and mustPlace, because the method counts on the recentCard being updated to what was just placed
//	public void payMoreCards(){
//		if (recentCard.isRoyal()){
//			place = 0;
//		}
//	}
//
//	//during each play, a player will place down the "last" card in their ArrayList in the center pile.  The returned Card will be added to the center deck.
//	//player will still be able to slap into the game if they have no cards left
//	public Card placeCard(){
//		if (hand.size() > 0){
//			recentCard = hand.get(hand.size()-1);
//			place = getPlace() -1;
//			return hand.remove(hand.size()-1);
//		}
//		return null;
//	}
//
//	public boolean slap(){
//		return true;
//	}
//
//	public Card burn(){
//		if (hand.size() > 0){
//			return hand.remove(hand.size()-1);
//		}
//		return null;
//	}
//}
