import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
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
    private boolean[] keys;
//    private BufferedImage back;
    private ArrayList<Player> players;
//    private int upperDisplay;
    private int playerCounter = 0;

    Player currentPlayer;

    public EgyptianWar()
    {
        //instance variables
        deck = new Deck();
        center = new ArrayList<Card>();
        keys = new boolean[3];
        players = new ArrayList<Player>();
    }

//	public Player addPlayer(Socket socket) {
//		Player player = new Player(socket, ++playerCounter);
//		players.add(player);
//		return player;
//	}

//    public void start() {
//        //other setup things
//        setVisible(true);
//        new Thread(this).start();
//        this.addKeyListener(this);
//    }

//    public void keyPressed(KeyEvent e)
//    {
//        if (e.getKeyCode()==KeyEvent.VK_1){
//            keys[0]=true;
//        }
//        if(e.getKeyCode()==KeyEvent.VK_SPACE){
//            keys[1]=true;
//        }
//        if(e.getKeyCode()==KeyEvent.VK_N){
//            keys[2]=true;
//        }
//        repaint();
//    }
//
//    public void keyReleased(KeyEvent e)
//    {
//        if (e.getKeyCode()==KeyEvent.VK_1){
//            keys[0]=false;
//        }
//        if(e.getKeyCode()==KeyEvent.VK_SPACE){
//            keys[1]=false;
//        }
//        if(e.getKeyCode()==KeyEvent.VK_N){
//            keys[2]=false;
//        }
//        repaint();
//    }

    public boolean isDouble(){
        if (center.size() >= 2){
            if (center.get(0).getFace()==center.get(1).getFace()&&center.get(0).getFace()<10&&center.get(1).getFace()<10){
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

//	public boolean isMarriage(){
//		if(center.size()>=2){
//	  		if(center.get(0).getFace()>10){
//	  			if(center.get(1).getFace()!=center.get(0).getFace()&&center.get(1).getFace()>10){
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//
//	public boolean isGay(){
//		if (center.size() >= 2){
//			if (center.get(0).getFace()==center.get(1).getFace()&&center.get(0).getFace()>=10&&center.get(1).getFace()>=10){
//				return true;
//			}
//		}
//		return false;
//	}

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
        int count = 0;
        for (int i = 0; i < players.size(); i++){
            if ((players.get(i)).getHandSize() == 0){
                count++;
            }
        }
        return (deck.size() == 0);
    }

//    public void keyTyped(KeyEvent e)
//    {
//        //I put this here because it's always been here
//    }

//    public void update(Graphics window)
//    {
//        paint(window);
//    }

    public void paint(Graphics window)
    {
//        Graphics2D twoDGraph=(Graphics2D)window;
//        if(back==null)
//            back = (BufferedImage)(createImage(getWidth(),getHeight()));
//        Graphics graphToBack = back.createGraphics();
////        mahogany.draw(graphToBack);
//        center.add(deck.nextCard());
//        upperDisplay=Math.min(4,center.size()-1);
//        if(center.size()>0){
//            for(int i=upperDisplay;i>=0;i--){
//                (center.get(i)).draw(graphToBack,(10+(upperDisplay-i)*91),10,365,485);
//            }
//        }
//        graphToBack.setColor(Color.WHITE);
//        graphToBack.fillRect(150, 10, 500, 150);
//        twoDGraph.drawImage(back, null, 0, 0);

        //long ass code block that basically makes a player place the right amount of cards
        do {

            for (int i = 0; i < players.size(); i++) {


                if (center.size() > 0) {
                    if (isAce(0)) {
                        (players.get(i)).setPlace(4);
                    } else if (isKing(0)) {
                        (players.get(i)).setPlace(3);
                    } else if (isQueen(0)) {
                        (players.get(i)).setPlace(2);
                    } else if (isJack(0)) {
                        (players.get(i)).setPlace(1);
                    } else {
                        (players.get(i)).setPlace(1);
                    }
                } else {
                    (players.get(i)).setPlace(1);
                }


                do {
                    if (keys[0]){
                        (players.get(i)).placeCard();
                    }
                } while ((players.get(i)).getPlace() > 0);


                if (isQueen(0) || isJack(0) || isKing(0) || isAce(0)){
                    (players.get(i)).setPlace(0);
                }

                //if player n does not play a face card after player n-1 does, player n-1 will gain the cards
                if (i > 0){
                    if (isJack(1) && !isFace(0)){
                        for (int e = center.size(); e > 0; e--){
                            (players.get(i-1)).addCard(center.remove(e));
                        }
                    } else if (isQueen(2) && !isFace(0)){
                        for (int e = center.size(); e > 0; e--){
                            (players.get(i-1)).addCard(center.remove(e));
                        }
                    } else if (isKing(3) && !isFace(0)){
                        for (int e = center.size(); e > 0; e--){
                            (players.get(i-1)).addCard(center.remove(e));
                        }
                    } else if (isAce(4) && !isFace(0)){
                        for (int e = center.size(); e > 0; e--){
                            (players.get(i-1)).addCard(center.remove(e));
                        }
                    }
                }
            }


            //code to check if slap is legal
            for (int i = 0; i < players.size(); i++){
                if (keys[1]){
                    if (isDouble() || isSandwich()){
                        for (int e = 0; e < center.size(); e++){
                            (players.get(i)).addCard(center.remove(e));
                        }
                    } else {
                        center.add((players.get(i)).burn());
                    }
                }
            }
        } while(!gameOver());
    }

//    public void run() {
//        try {
//            while(true) {
//                Thread.currentThread().sleep(5);
//                repaint();
//            }
//        } catch(Exception e) {}
//    }

    public class Player implements Runnable {
//        private int wins;
        private int place; //number of cards the player must place at any given moment
        private ArrayList<Card> hand;
        private Card recentCard = null;
        private Socket socket;
        private int playerNumber;
        Scanner input;
        PrintWriter output;
        Player opponent;

        public Player(Socket socket, int playerNumber){
            hand = new ArrayList<Card>();
//            wins = 0;
            place = 0;
            this.socket = socket;
            this.playerNumber = playerNumber;
        }

        public void addCard(Card c){
            hand.add(c);
        }

        public int getHandSize(){
            return hand.size();
        }

//        public int getNumWins(){
//            return wins;
//        }
//
//        public void setNumWins(int num){
//            wins = num;
//        }

        public void run() {
            try {
                setup();
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                try {socket.close();} catch (IOException e) {}
            }
        }

        public void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(),true);
            output.println("WELCOME PLAYER " + playerNumber);
            if (playerNumber == 1) {
                currentPlayer = this;
                output.println("MESSAGE Waiting for opponent to connect");
            } else {
                opponent = currentPlayer;
                opponent.opponent = this;
                opponent.output.println("MESSAGE Your move");
            }
        }

        //the card played before the player determines the number of cards each player must play
        public void setPlace(int p){
            place = p;
        }

        public int getPlace(){
            return place;
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
        public void payMoreCards(){
            if (recentCard.isRoyal()){
                place = 0;
            }
        }

        //during each play, a player will place down the "last" card in their ArrayList in the center pile.  The returned Card will be added to the center deck.
        //player will still be able to slap into the game if they have no cards left
        public Card placeCard(){
            if (hand.size() > 0){
                recentCard = hand.get(hand.size()-1);
                place = getPlace() -1;
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
}
