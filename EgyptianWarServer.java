import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class EgyptianWarServer {

    private static final int PORT = 58901;
    private static final int MAX_THREADS = 2;

    public static void main(String[] args) throws Exception {
        try(ServerSocket listener = new ServerSocket(PORT)) {
            System.out.println("Egyptian War Server is Running...");
            ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
            ThreadPoolExecutor pool = (ThreadPoolExecutor)executor;
            while (true) {
                EgyptianWar game = new EgyptianWar();
                pool.execute(game.new Player(listener.accept(),1));
                pool.execute(game.new Player(listener.accept(),2));
            }
        }
    }
}

class EgyptianWar {
    private Deck deck;
    private ArrayList<Card> center;

    Player player;

    public EgyptianWar()
    {
        deck = new Deck();
        deck.shuffle();
        center = new ArrayList<Card>();
    }

    public boolean isDouble(){
        if (center.size() >= 2){
            if (center.get(0).getFace()==center.get(1).getFace()&&center.get(0).getFace()<10&&center.get(1).getFace()<10) {
                return true;
            }
        }
        return false;
    }

    public boolean isSandwich(){
        if (center.size() >= 3){
            if (!isDouble()){
                if ((center.get(0)).getFace() == (center.get(2)).getFace()){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isQueen(int num){
        if (center.size() > 0){
            if ((center.get(num)).getFace() == 11){
                return true;
            }
        }
        return false;
    }

    public boolean isJack(int num){
        if (center.size() > 0){
            if ((center.get(num)).getFace() == 10){
                return true;
            }
        }
        return false;
    }

    public boolean isKing(int num){
        if (center.size() > 0){
            if ((center.get(num)).getFace() == 12){
                return true;
            }
        }
        return false;
    }

    public boolean isAce(int num){
        if (center.size() > 0){
            if ((center.get(num)).getFace() == 0){
                return true;
            }
        }
        return false;
    }

    public boolean isFace(int num){
        return (isAce(num) || isKing(num) || isQueen(num) || isJack(num));
    }

    public boolean gameOver(){
        return player.getHandSize() == 0 || player.opponent.getHandSize() == 0;
    }

    public void playGame(){
//        for (int i = 0; i < players.size(); i++){
//            if (center.size() > 0){
//                if (isAce(0)){
//                    (players.get(i)).setPlace(4);
//                } else if (isKing(0)){
//                    (players.get(i)).setPlace(3);
//                } else if (isQueen(0)){
//                    (players.get(i)).setPlace(2);
//                } else if (isJack(0)){
//                    (players.get(i)).setPlace(1);
//                } else{
//                    (players.get(i)).setPlace(1);
//                }
//            } else {
//                (players.get(i)).setPlace(1);
//            }
//
//            //add a card while you are still supposed to
//            int place = (players.get(i)).getPlace();
//
//            do {
//                if (keys[0] && (players.get(i)).getHandSize() > 0){
//                    Card c = (players.get(i)).placeCard();
//                    if (c != null){
//                        System.out.println("card placed from EgyptianWar");
//                        center.add(0, c);
//                        (players.get(i)).setPlace((players.get(i)).getPlace() - 1);
//                        System.out.println("place is decremented");
//                    }
//                    System.out.println("place value:: " + place);
//                }
//            }while (place > 0);
//
//
//            if (isQueen(0) || isJack(0) || isKing(0) || isAce(0)){
//                (players.get(i)).setPlace(0);
//            }
//
//            //if player n does not play a face card after player n-1 does, player n-1 will gain the cards
//            if (i > 0 && center.size() > 1){
//                if (isJack(1) && !isFace(0)){
//                    for (int e = center.size()-1; e >= 0; e--){
//                        (players.get(i-1)).addCard(center.remove(e));
//                    }
//                } else if (center.size() > 2 && isQueen(2) && !isFace(0)){
//                    for (int e = center.size()-1; e >= 0; e--){
//                        (players.get(i-1)).addCard(center.remove(e));
//                    }
//                } else if (center.size() > 3 && !isFace(0) && isKing(3)){
//                    for (int e = center.size()-1; e >= 0; e--){
//                        (players.get(i-1)).addCard(center.remove(e));
//                    }
//                } else if (center.size() > 4 && isAce(4) && !isFace(0)){
//                    for (int e = center.size()-1; e >= 0; e--){
//                        (players.get(i-1)).addCard(center.remove(e));
//                    }
//                }
//            }
//        }
//
//        //code to check if slap is legal
//        for (int i = 0; i < players.size(); i++){
//            if (keys[1]){
//                if (isDouble() || isSandwich()){
//                    for (int e = 0; e < center.size(); e++){
//                        (players.get(i)).addCard(center.remove(e));
//                    }
//                } else {
//                    Card c = (players.get(i)).burn();
//                    if (c != null){
//                        center.add(c);
//                    }
//                }
//            }
//        }
    }

    public void dealCardsToPlayers() {
        //deal cards to each player
        while (deck.getSize() > 0 && deck.getSize() % 2 == 0) {
            // player 1
            player.addCard(deck.removeFromDeck(0));
            // player 2
            player.opponent.addCard(deck.removeFromDeck(0));
        }
    }

    public class Player implements Runnable {
        private int place; //number of cards the player must place at any given moment
        private ArrayList<Card> hand;
        private Card recentCard;
        private Socket socket;
        private int playerNumber;
        Scanner input;
        PrintWriter output;
        Player opponent;

        public Player(Socket socket, int playerNumber){
            hand = new ArrayList<Card>();
            place = 1;
            this.socket = socket;
            this.playerNumber = playerNumber;
        }

        public void addCard(Card c) {
            hand.add(c);
        }

        public int getHandSize(){
            return hand.size();
        }

        public void run() {
            try {
                setup();
                processCommands();
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                if (opponent != null && opponent.output != null) {
                    opponent.output.println("OTHER_PLAYER_LEFT");
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }

        public void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(),true);

            output.println("PLAYER_ASSIGNED " + playerNumber);
            output.println("MESSAGE Welcome Player " + playerNumber + "!");

            if (playerNumber == 1) {
                player = this;
                output.println("MESSAGE Waiting for opponent to connect");
            } else {
                opponent = player;
                opponent.opponent = this;
                opponent.output.println("MESSAGE Your move");
            }

            if (player != null && opponent != null) {
                dealCardsToPlayers();
            }
        }

        private void processCommands() {
            while (input.hasNextLine()) {
                var command = input.nextLine();
                if (command.startsWith("QUIT")) {
                    return;
                } else if (command.startsWith("ACTION")) {
                    processAction(command.substring(7));
            }
        }
    }

    private void processAction(String action) {
        try {
            output.println("VALID_ACTION");
            opponent.output.println("OPPONENT_ACTION " + action);
            if (gameOver()) {
                output.println("VICTORY");
                opponent.output.println("DEFEAT");
            } else if (action.equals("PLACE_CARD")) {
                System.out.println("Placing a card omg woo!");
                // TODO: IMPLEMENT
            } else if (action.equals("SLAP")) {
                System.out.println("Slap chop");
                // TODO: IMPLEMENT
            }
        } catch (Exception e) {
            output.println("MESSAGE " + e.getMessage());
        }
    }

        //the card played before the player determines the number of cards each player must play
        public void setPlace(int p){
            place = p;
        }

        public int getPlace(){
            if (hand.size() < 0){
                return place;
            }
            return 0;
        }

        public int mustPlace(int face){
            if (face > 0 && face < 10){
                place = 1;
            } else if (face == 0){
                place = 4;
            } else if (face == 10){
                place = 1;
            } else if (face == 11){
                place = 2;
            } else if (face == 12){
                place = 3;
            }
            return place;
        }

        //call this method only after you have called placeCard and mustPlace, because the method counts on the recentCard being updated to what was just placed
        /*public void payMoreCards(){
            if (recentCard.isRoyal()){
                place = 0;
            }
        }*/

        //during each play, a player will place down the "last" card in their ArrayList in the center pile.  The returned Card will be added to the center deck.
        //player will still be able to slap into the game if they have no cards left
        public Card placeCard(){
            if (hand.size() > 0){
                System.out.println("card placed from player method");
                recentCard = hand.get(0);
                place = getPlace() - 1;
                return hand.remove(0);
            }
            return null;
        }

        public boolean slap(){
            return true;
        }

        public Card burn(){
            if (hand.size() > 0){
                return hand.remove(0);
            }
            return null;
        }
    }
}