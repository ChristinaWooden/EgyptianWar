import javax.swing.JFrame;
import java.awt.Component;

public class TheGame extends JFrame
{
  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;
  
  public TheGame()
  {
    super("EGYPTIAN WAR");
    setSize(WIDTH,HEIGHT);
    EgyptianWar game = new EgyptianWar(WIDTH,HEIGHT);
    ((Component)game).setFocusable(true);
    getContentPane().add(game);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main(String args[])
  {
    TheGame run = new TheGame();
  }
}
