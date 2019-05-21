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

public class EgyptianWar{
	private Deck deck;
	private boolean[] keys;
	private BufferedImage back;

	public EgyptianWar(){
		deck = new Deck();
		keys = new boolean[3];
	}

	public void update(Graphics window){
		paint(window);
	}

	public void paint(Graphics window){
		
	}






	
}
