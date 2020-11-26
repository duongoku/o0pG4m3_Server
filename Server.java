import java.io.*;
import java.net.*;

public class Server {
    private Socket socket = null;
    private ServerSocket server = null;
    private InputStreamReader in = null;
    private BufferedReader bf = null;
    private PrintWriter pr = null;
    private SubServer[] subServerList = null;
    private int subServerCount = 0;
    private String query = "";
    private String[] args = null;
    private Room demoRoom = null;

    private static final int MAXSUBSERVER = 100;

    public Server(int port) throws Exception {
        subServerList = new SubServer[MAXSUBSERVER];
        server = new ServerSocket(port);
        demoRoom = new Room();
    }

    public void start() throws Exception {
        System.out.println("Game server started");
        System.out.println("Waiting for a request...");
        while(true) {
            socket = server.accept();
            System.out.println("A Client connected");
            if(demoRoom.getPlayerCount() == 2) {
                demoRoom = new Room();
            }
            createSubServer(socket, demoRoom);
        }
    }

    public void createSubServer(Socket socket, Room demoRoom) throws Exception {
        subServerList[subServerCount] = new SubServer(socket, demoRoom);
        subServerList[subServerCount].start();
        subServerCount++;
    }

    public static void main(String args[]) throws Exception {
        Server mainServer = new Server(25565);
        mainServer.start();
    }
}