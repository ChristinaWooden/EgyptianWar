import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;



// ALL OF THIS IS TAKEN FROM ONLINE- CHANGE

/**
 * A server program which accepts requests from clients to capitalize strings. When
 * a client connects, a new thread is started to handle it. Receiving client data,
 * capitalizing it, and sending the response back is all done on the thread, allowing
 * much greater throughput because more clients can be handled concurrently.
 */
<<<<<<< HEAD
public class GameServer {
=======
<<<<<<< HEAD
public class GameServer {
=======
public class CapitalizeServer {
>>>>>>> 4595780741b22c9fc185e1eecadeacbee4f5420f
>>>>>>> 086617117aac510571c3b62951932f81ba11e742

    /**
     * Runs the server. When a client connects, the server spawns a new thread to do
     * the servicing and immediately returns to listening. The application limits the
     * number of threads via a thread pool (otherwise millions of clients could cause
     * the server to run out of resources by allocating too many threads).
     */
    public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(6966696)) {
            System.out.println("The Egyptian War server is running...");
            var pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute(new Player(listener.accept()));
            }
        }
    }

    private static class Game implements Runnable {
        private Socket socket;

        Game(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Connected: " + socket);
            try {
                var in = new Scanner(socket.getInputStream());
                var out = new PrintWriter(socket.getOutputStream(), true);
                while (in.hasNextLine()) {
                    out.println(in.nextLine().toUpperCase());
                }
            } catch (Exception e) {
                System.out.println("Error:" + socket);
            } finally {
                try { socket.close(); } catch (IOException e) {}
                System.out.println("Closed: " + socket);
            }
        }
    }
}