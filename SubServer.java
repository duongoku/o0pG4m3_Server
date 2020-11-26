import java.io.*;
import java.net.*;

class SubServer extends Thread {
    private Socket socket = null;
    private InputStreamReader in = null;
    private BufferedReader bf = null;
    private PrintWriter pr = null;
    private Room demoRoom = null;
    private int playerID = 0;

    private static final int MAXSUBSERVER = 100;

    public SubServer(Socket socket, Room demoRoom) throws Exception {
        this.socket = socket;
        this.demoRoom = demoRoom;
        in = new InputStreamReader(socket.getInputStream());
        pr = new PrintWriter(socket.getOutputStream(), true);
        bf = new BufferedReader(in);
    }

    public void run() {
        try {
            String str = "";
            String[] args = null;
            while(!str.equals("DISCONNECT")) {
                str = bf.readLine();
                args = str.split(" ", -1);
                switch(args[0]) {
                    case "GET":
                        pr.println(demoRoom.getRoomData(playerID, args[1], args[2], args[3]));
                        break;
                    case "MOV":
                        System.out.println("CLIENT: " + str);
                        demoRoom.addKey(playerID, Integer.parseInt(args[1]));
                        break;
                    case "STP":
                        System.out.println("CLIENT: " + str);
                        demoRoom.removeKey(playerID, Integer.parseInt(args[1]));
                        break;
                    case "JOIN":
                        System.out.println("CLIENT: " + str);
                        playerID = demoRoom.join();
                        pr.println(playerID);
                        break;
                    case "SEED":
                        System.out.println("CLIENT: " + str);
                        pr.println(demoRoom.getSeed());
                        break;
                    default:
                        break;
                }
            }
        } catch(Exception e) {
            close();
        }
        close();
    }

    public void close(){
        System.out.println("A client has disconnected");
        try {
            socket.close();
            in.close();
            pr.close();
            bf.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}