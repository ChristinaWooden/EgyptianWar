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

public class EgyptianWar
{
	private Deck center;
	private boolean[] keys;
	private BufferedImage back;
	
	public EgyptianWar()
	{
		deck = new Deck();
		keys = new boolean[3];
		setBackground(Color.WHITE);
		setVisible(true);  
		new Thread(this).start();
		addKeyListener(this);
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
		twoDGraph.drawImage(back, null, 0, 0);
	}

	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode()==KeyEvent.VK_1){
			key[0]=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			key[1]=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_N){
			key[2]=true;
		}
		repaint();
	}
	
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode()==KeyEvent.VK_1){
			key[0]=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			key[1]=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_N){
			key[2]=false;
		}
		repaint();
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
