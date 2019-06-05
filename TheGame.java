import javax.swing.JFrame;
import java.awt.Component;
import java.util.Scanner;

public class TheGame extends JFrame
{
  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;
  private int players;
  
  public TheGame()
  {
    super("EGYPTIAN WAR");
    Scanner k=new Scanner(System.in);
    System.out.print("Enter number of players:: ");
    players=k.nextInt();
    //if (players == 1){
      //do{
        //System.out.println("Number of players must be greater than 1.  Enter number of players:: ");
        //players=k.nextInt();
      //}while(players == 1);
    //}

    setSize(WIDTH,HEIGHT);
    EgyptianWar game = new EgyptianWar(WIDTH, HEIGHT, players);
    ((Component)game).setFocusable(true);
    getContentPane().add(game);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public static void main(String args[])
  {
    TheGame run = new TheGame();
  }
}
