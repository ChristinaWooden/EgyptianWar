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
import java.util.ArrayList;

public class EgyptianWar
{
	private Deck deck;
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
	
	}

	public void keyPressed(KeyEvent e)
	{
		switch(toUpperCase(e.getKeyChar()))
		{
			case 'SPACE':keys[0]=true;break;
			case '1':keys[1]=true;break;
			case 'N':keys[2]=true;break;
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		switch(toUpperCase(e.getKeyChar()))
		{
			case 'SPACE':keys[0]=false;break;
			case '1':keys[1]=false;break;
			case 'N':keys[2]=false;break;
		}
	}
}
