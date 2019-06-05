import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EgyptianWarClient {

    private static final int PORT = 58901;

    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    public EgyptianWarClient(String serverAddress) throws Exception {
        socket = new Socket(serverAddress, PORT);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void play() throws Exception {
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
