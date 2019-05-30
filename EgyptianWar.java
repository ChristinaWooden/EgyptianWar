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
	private Image image;
	
	public EgyptianWar(int w,int h,int num)
	{

		//instance variables
		deck = new Deck();
		center = new ArrayList<Card>();
		keys = new boolean[3];
		players = new ArrayList<Player>();
		width=w;
		height=h;

		
		//add players to game
		//number of players should be determined in TheGame.java
		for (int i = 0; i < num; i++){
			players.add(new Player());
		}

		//deal cards to each player
		do{
			for (int i = 0; i < players.size(); i++){
				(players.get(i)).addCard((deck.remove(0)));
			}
		}while(deck.size() > 0);


		//other setup things
		"images/mahogany.jpg");
		setVisible(true);  
		new Thread(this).start();
		this.addKeyListener(this);




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
		try
    		{
      			URL url = getClass().getResource("images/mahogany.jpg");
      			image = ImageIO.read(url);
    		}
    		catch(Exception e)
    		{
      
    		}
		twoDGraph.drawImage(image, 0, 0, null);
		upperDisplay=Math.min(4,center.size()-1);
		for(int i=upperDisplay;i>=0;i--){
		  (center.get(i)).draw(graphToBack,(10+(upperDisplay-i)*91),10,365,485);
		  (center.get(i)).draw(graphToBack,(10+(upperDisplay-i)*91),10,365,485);
		}
		twoDGraph.drawImage(back, null, 0, 0);


		//in order to either burn or gain cards after slapping
		for (int i = 0; i < players.size(); i++){
			if ((players.get(i)).slap()){
				if (isSandwich || isDouble || isMarriage || isGay){
					//take the center pile and add it to the player's hand
					for (int i = center.size(); i > 0; i--){
						(players.get(i)).addCard(center.remove(i));
					}
				}
				else {
					center.add(0, (players.get(i)).burn());
				}
			}


	}

	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode()==KeyEvent.VK_1){
			keys[0]=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			keys[1]=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_N){
			keys[2]=true;
		}
		repaint();
	}
	
	public void keyReleased(KeyEvent e)
	{
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
			if (center.get(0).getFace()==center.get(1).getFace()&&center.get(0).getFace()<10&&center.get(0).getFace()<10){
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
			if (center.get(0).getFace()==center.get(1).getFace()&&center.get(0).getFace()>=10&&center.get(0).getFace()>=10){
				return true;
			}
		}
		return false;
	}
	
	public boolean isQueen(){
		if (center.size() > 0){
			if ((center.get(0)).getFace() == 11){
				return true;
			}
		}
		return false;
	}

	public boolean isJack(){
		if (center.size() > 0){
			if ((center.get(0)).getFace() == 10){
				return true;
			}
		}
		return false;
	}

	public boolean isKing(){
		if (center.size() > 0){
			if ((center.get(0)).getFace() == 12){
				return true;
			}
		}
		return false;
	}

	public boolean isAce(){
		if (center.size() > 0){
			if ((center.get(0)).getFace() == 0){
				return true;
			}
		}
		return false;
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
	
	public void keyTyped(KeyEvent e)
	{
		//I put this here because it's always been here
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
		}
  }
}
