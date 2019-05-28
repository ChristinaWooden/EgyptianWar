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
		setBackground(Color.WHITE);
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
		upperDisplay=Math.min(4,center.size()-1);
		for(int i=upperDisplay;i>=0;i--){
		  center[i].draw(10+(upperDisplay-i)*91,10,365,485);
		}
		twoDGraph.drawImage(back, null, 0, 0);
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

	public boolean isDouble(ArrayList<Card> center){
		if (center.size() >= 2){
			if (((center.get(0)).getFace()) == ((center.get(1)).getFace())){
				return true;
			}
			return false;
		}
	}

	public boolean isSandwich(ArrayList<Card> center){
		if (center.size() >= 3){
			if (!isDouble(center)){
				if ((center.get(0)).getFace() == (center.get(2)).getFace()){
					return true;
				}
			}
		}
		return false;
	}

	public boolean isQueen(ArrayList<Card> center){
		if ((center.get(0)).getFace() == 11){
			return true;
		}
		return false;
	}

	public boolean isJack(ArrayList<Card> center){
		if ((center.get(0)).getFace() == 10){
			return true;
		}
		return false;
	}

	public boolean isKing(ArrayList<Card> center){
		if ((center.get(0)).getFace() == 12){
			return true;
		}
		return false;
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
