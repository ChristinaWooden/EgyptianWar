import java.io.File;
import java.net.URL;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;


public class EgyptianWar extends Canvas implements KeyListener, Runnable
{
	private Deck deck;
	private ArrayList<Card> center;
	private boolean[] keys;
	private BufferedImage back;
	private ArrayList<Player> players;
	private int width;
	private int height;
	private int upperDisplay;
	private Mahogany mahogany;

	
	public EgyptianWar(int w,int h,int num)
	{

		//instance variables
		deck = new Deck();
		deck.shuffle();
		System.out.println(deck.nextCard());
		center = new ArrayList<Card>();
		keys = new boolean[3];
		players = new ArrayList<Player>();
		width=w;
		height=h;
		mahogany=new Mahogany(w,h);

		
		//add players to game
		//number of players should be determined in TheGame.java
		for (int i = 0; i < num; i++){
			players.add(new Player());
		}

		//deal cards to each player
		do{
			for (int i = 0; i < players.size(); i++){
				Card c = deck.remove(0);
				(players.get(i)).addCard(c);
			}
		}while(deck.size() > 0);


		//other setup things
		setVisible(true);  
		this.addKeyListener(this);
		new Thread(this).start();

	}

	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode()==KeyEvent.VK_1){
			keys[0]=true;
			System.out.println("Card plced");
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			keys[1]=true;
			System.out.println("Card burnt");
		}
		if(e.getKeyCode()==KeyEvent.VK_N){
			keys[2]=true;
			System.out.println("n pressed");
		}
		repaint();
	}
	
	public void keyReleased(KeyEvent e)	{
		if (e.getKeyCode()==KeyEvent.VK_1){
			keys[0]=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			keys[1]=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_N){
			keys[2]=false;
		}
		repaint();
	}

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
	
	public boolean isMarriage(){
		if(center.size()>=2){
	  		if(center.get(0).getFace()>10){
	  			if(center.get(1).getFace()!=center.get(0).getFace()&&center.get(1).getFace()>10){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isGay(){
		if (center.size() >= 2){
			if (center.get(0).getFace()==center.get(1).getFace()&&center.get(0).getFace()>=10&&center.get(1).getFace()>=10){
				return true;
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
		int count = players.size();
		for (int i = 0; i < players.size(); i++){
			if ((players.get(i)).getHandSize() == 0){
				count--;
			}
		}
		return (count == 1);
	}
	
	public void keyTyped(KeyEvent e)
	{
		//I put this here because it's always been here
	}


	public void update(Graphics window)
	{
		paint(window);
	}
	
	public void paint(Graphics window)
	{
		Graphics2D twoDGraph=(Graphics2D)window;
		if(back==null)
			back = (BufferedImage)(createImage(getWidth(),getHeight()));
		Graphics graphToBack = back.createGraphics();
		//add methods here
		/*try
    		{
      			URL url = getClass().getResource("images/mahogany.jpg");
      			image = ImageIO.read(url);
    		}
    		catch(Exception e)
    		{
      
    		}
		twoDGraph.drawImage(image, 0, 0, null);*/
		mahogany.draw(graphToBack);
		center.add(new Card());
		center.add(new Card());
		center.add(new Card());
		center.add(new Card());
		center.add(new Card());
		center.add(new Card());

		//testing placeCard
		Card c = (players.get(0)).placeCard(); //placeCard method works and returns a card, but does not show up in graphics...
		System.out.println(c);
		center.add(0,c);

		//testing burn
		Card c2 = (players.get(0)).burn();
		System.out.println(c2);
		center.add(0, c2);

		graphToBack.setColor(Color.WHITE);
		graphToBack.fillRect(150, 10, 500, 150);
		graphToBack.setColor(Color.BLACK);
		graphToBack.drawString("EGYPTIAN WAR", 350, 25);
		drawCenter(graphToBack);

		do{
			twoDGraph.drawImage(back, null, 0, 0);
			playGame();
		}while(!gameOver());


		if (gameOver()){
			System.out.println("Game over");
		}


		//twoDGraph.drawImage(back, null, 0, 0);


	}

	public void drawCenter(Graphics graphToBack){
		upperDisplay=Math.min(4,center.size()-1);
		if(center.size()>0){
			for(int i=upperDisplay-1;i>=0;i--){
		  		(center.get(i)).draw(graphToBack, (150+((upperDisplay - i)*69)), 200, 100, 150); 
			}
		}
	}

	public void playGame(){

			for (int i = 0; i < players.size(); i++){


				if (center.size() > 0){
					if (isAce(0)){
						(players.get(i)).setPlace(4);
					}
					else if (isKing(0)){
						(players.get(i)).setPlace(3);
					}
					else if (isQueen(0)){
						(players.get(i)).setPlace(2);
					}
					else if (isJack(0)){
						(players.get(i)).setPlace(1);
					}
					else{
						(players.get(i)).setPlace(1);
					}
				}
				else {
					(players.get(i)).setPlace(1);
				}

				//add a card while you are still supposed to
				do {
					if ((players.get(i)).getHandSize() > 0 && keys[0]){
						System.out.println("card placed");
						Card c = (players.get(i)).placeCard();
						if (c != null){
							center.add(0, c);
						}
					}
				}while ((players.get(i)).getPlace() > 0);


				if (isQueen(0) || isJack(0) || isKing(0) || isAce(0)){
					(players.get(i)).setPlace(0);
				}

				//if player n does not play a face card after player n-1 does, player n-1 will gain the cards
				if (i > 0 && center.size() > 1){
					if (isJack(1) && !isFace(0)){
						for (int e = center.size()-1; e >= 0; e--){
							(players.get(i-1)).addCard(center.remove(e));
						}
					}
					else if (isQueen(2) && !isFace(0) && center.size() > 2){
						for (int e = center.size()-1; e >= 0; e--){
							(players.get(i-1)).addCard(center.remove(e));
						}
					}
					else if (isKing(3) && !isFace(0) && center.size() > 3){
						for (int e = center.size()-1; e >= 0; e--){
							(players.get(i-1)).addCard(center.remove(e));
						}
					}
					else if (isAce(4) && !isFace(0) && center.size() > 4){
						for (int e = center.size()-1; e >= 0; e--){
							(players.get(i-1)).addCard(center.remove(e));
						}
					}
				}


			}


			//code to check if slap is legal
			for (int i = 0; i < players.size(); i++){
				if (keys[1]){
					if (isDouble() || isSandwich() || isGay()){
						for (int e = 0; e < center.size(); e++){
							(players.get(i)).addCard(center.remove(e));
						}
					}
					else {
						Card c = (players.get(i)).burn();
						if (c != null){
							center.add(c);
						}
					}
				}
			}
		}
	
	
	public void run()
	{

		try
		{
			while(true)
			{
				Thread.currentThread().sleep(5);
				repaint();
			}
		}catch(Exception e)
		{
			System.out.println("Exception caught ????");
		}
  	}

  }
