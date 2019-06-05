import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class EgyptianWarClient extends Canvas{

    private static final int PORT = 58901;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Deck deck;
    private ArrayList<Card> center;
    private Mahogany mahogany;
    private BufferedImage back;
    private int upperDisplay;

    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    public EgyptianWarClient(String serverAddress) throws Exception {
        socket = new Socket(serverAddress, PORT);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
        mahogany=new Mahogany(WIDTH,HEIGHT);
    }

    public void play() throws Exception implements KeyListener{
        try {
            var response = in.nextLine();
            System.out.println(response);
            while (in.hasNextLine()) {
                response = in.nextLine();
                if (response.startsWith("VALID_MOVE")) {
                    System.out.println("Valid move made..." + response);
                } else {
                    System.out.println("QUITTING");
                    break;
                }
            }
            out.println("QUIT");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }

//        public void start() {
//        //other setup things
//        setVisible(true);
//        new Thread(this).start();
//        this.addKeyListener(this);
//        }

        public void update(Graphics window) {
            paint(window);
        }

        public void paint(Graphics window) {
            Graphics2D twoDGraph=(Graphics2D)window;
            if (back==null)
                back = (BufferedImage)(createImage(getWidth(),getHeight()));
            Graphics graphToBack = back.createGraphics();
            //add methods here
		/*try{
      			URL url = getClass().getResource("images/mahogany.jpg");
      			image = ImageIO.read(url);
    		} catch(Exception e){}
		twoDGraph.drawImage(image, 0, 0, null);*/
//        mahogany.draw(graphToBack);
            center.add(deck.nextCard());
            upperDisplay = Math.min(4,center.size()-1);
            if(center.size()>0){
                for(int i = upperDisplay; i >= 0; i--){
                    (center.get(i)).draw(graphToBack,(10+(upperDisplay-i)*91),10,365,485);
                }
            }
            graphToBack.setColor(Color.WHITE);
            graphToBack.fillRect(150, 10, 500, 150);
            twoDGraph.drawImage(back, null, 0, 0);

            public void keyPressed(KeyEvent e) {
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

            public void keyTyped(KeyEvent e)
            {
                //I put this here because it's always been here
            }

    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }
        EgyptianWarClient client = new EgyptianWarClient(args[0]);
        client.play();
    }
}
