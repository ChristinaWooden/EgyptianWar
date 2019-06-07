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
        try (ServerSocket listener = new ServerSocket(PORT)) {
            System.out.println("Egyptian War Server is Running...");
            ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
            ThreadPoolExecutor pool = (ThreadPoolExecutor) executor;
            while (true) {
                EgyptianWar game = new EgyptianWar();
                pool.execute(game.new Player(listener.accept(), 1));
                pool.execute(game.new Player(listener.accept(), 2));
            }
        }
    }
}

class EgyptianWar {
    private Deck deck;
    private ArrayList<Card> center;

    Player player;

    public EgyptianWar() {
        deck = new Deck();
        deck.shuffle();
        center = new ArrayList<Card>();
    }

    public synchronized void move(Player plr) {
        if (plr != player) {
            throw new IllegalStateException("Not your turn");
        } else if (player.opponent == null) {
            throw new IllegalStateException("You don't have an opponent yet");
        }
    }

    public boolean isDouble() {
        if (center.size() >= 2) {
            if (center.get(0).getFace() == center.get(1).getFace()) {
                return true;
            }
        }
        return false;
    }

    public boolean isSandwich() {
        if (center.size() >= 3) {
            if (!isDouble()) {
                if ((center.get(0)).getFace() == (center.get(2)).getFace()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isQueen(int num) {
        if (center.size() > 0) {
            if ((center.get(num)).getFace() == 11) {
                return true;
            }
        }
        return false;
    }

    public boolean isJack(int num) {
        if (center.size() > 0) {
            if ((center.get(num)).getFace() == 10) {
                return true;
            }
        }
        return false;
    }

    public boolean isKing(int num) {
        if (center.size() > 0) {
            if ((center.get(num)).getFace() == 12) {
                return true;
            }
        }
        return false;
    }

    public boolean isAce(int num) {
        if (center.size() > 0) {
            if ((center.get(num)).getFace() == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isFace(int num) {
        return (isAce(num) || isKing(num) || isQueen(num) || isJack(num));
    }

    public boolean gameOver() {
        return player.getHandSize() == 0 || player.opponent.getHandSize() == 0;
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

        public Player(Socket socket, int playerNumber) {
            hand = new ArrayList<Card>();
            place = 1;
            this.socket = socket;
            this.playerNumber = playerNumber;
        }

        public void addCard(Card c) {
            hand.add(c);
        }

        //during each play, a player will place down the "last" card in their ArrayList in the center pile.  The returned Card will be added to the center deck.
        //player will still be able to slap into the game if they have no cards left
        public Card placeCard() {
            System.out.println("card placed from player method");
            recentCard = hand.get(0);
            place = getPlace() - 1;
            return hand.remove(0);
        }

        public int getHandSize() {
            return hand.size();
        }

        public void run() {
            try {
                setup();
                processCommands();
            } catch (Exception e) {
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
            output = new PrintWriter(socket.getOutputStream(), true);

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
                move(player);
                output.println("VALID_ACTION");
                opponent.output.println("OPPONENT_ACTION " + action);
                if (gameOver()) {
                    output.println("VICTORY");
                    opponent.output.println("DEFEAT");
                } else if (action.equals("PLACE_CARD")) {
                    Card placedCard = player.placeCard();
                    center.add(0, placedCard);
                    System.out.println("Card placed: " + placedCard);

                    int cardFace = placedCard.getFace();

                    int newPlayerPlace = isFace(cardFace) ? 0 : Math.max(0, player.getPlace() - 1);
                    player.setPlace(newPlayerPlace);

                    if (player.getPlace() == 0 && opponent.getPlace() == 0) {
                        collectCards(player);
                    } else {
                        int newOpponentPlace = isAce(cardFace) ? 4 : isKing(cardFace) ? 3 : isQueen(cardFace) ? 2 : 1;
                        opponent.setPlace(newOpponentPlace);
                    }

                    // checks who gets cards after cards are placed, if it should be collected
//                if (center.size() > 1){
//                    if (isJack(center.get(1).getFace()) && !isFace(placed.getFace())){
//                            collectCards(opponent);
//                    } else if (center.size() > 2 && isQueen(center.get(2).getFace()) && !isFace(placed.getFace())){
//                            collectCards(opponent);
//                    } else if (center.size() > 3 && isKing(center.get(3).getFace()) && !isFace(placed.getFace())){
//                            collectCards(opponent);
//                    } else if (center.size() > 4 && isAce(center.get(4).getFace()) && !isFace(placed.getFace())){
//                            collectCards(opponent);
//                    }
//                }

                } else if (action.equals("SLAP")) {
                    System.out.println("Slap chop");
                    System.out.println(player.getHandSize());
                    // TODO: IMPLEMENT
                   if (isDouble() || isSandwich()){
                       collectCards(player);
                   } else {
                       Card c = (player.burn());
                       System.out.println("You burned!!");
                       System.out.println(player.getHandSize());
                       if (c != null){
                           center.add(c);
                       }
                   }
                }
            } catch (Exception e) {
                output.println("MESSAGE " + e.getMessage());
            }
        }

        private void collectCards(Player recipient) {
            for (int i = center.size() - 1; i >= 0; i--) {
                recipient.addCard(center.remove(i));
            }
        }

        //the card played before the player determines the number of cards each player must play
        public void setPlace(int p) {
            place = p;
        }

        public int getPlace() {
            if (hand.size() < 0) {
                return place;
            }
            return 0;
        }

        public int mustPlace(int face) {
            if (face > 0 && face < 10) {
                place = 1;
            } else if (face == 0) {
                place = 4;
            } else if (face == 10) {
                place = 1;
            } else if (face == 11) {
                place = 2;
            } else if (face == 12) {
                place = 3;
            }
            return place;
        }

//         public boolean slap() {
//             return true;
//         }

        public Card burn() {
            if (hand.size() > 0) {
                return hand.remove(0);
            }
            return null;
        }
    }
}
