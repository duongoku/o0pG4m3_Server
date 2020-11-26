import java.io.*;
import java.net.*;

import java.util.ArrayList;
import java.awt.event.KeyEvent;

public class Room {
    private Socket socket = null;
    private int[] putBombList = null;
    private int[][] playerPos = null;
    private int playerCount = 0;
    private int seed = -1;
    private int[] imageID = null;

    public Room() {
        playerCount = 0;
        playerPos = new int[][] {
            {40, 40}, 
            {720, 520}
        };
        imageID = new int[] {0, 0};
        putBombList = new int[]{0, 0};
    }

    public int getSeed() {
        if(seed == -1) {
            seed = ((int)(100 * Math.random()));
        }
        return seed;
    }

    public String getRoomData(int getterID, String posX, String posY, String iID) {
        String str = "";
        if(playerCount < 2) {
            str = "NEP";
            return str;
        }
        try {
            imageID[getterID] = Integer.parseInt(iID);
            playerPos[getterID][0] = Integer.parseInt(posX);
            playerPos[getterID][1] = Integer.parseInt(posY);
        } catch(Exception e) {
            e.printStackTrace();
        }
        for(int i=0;i<playerCount;i++) {
            str += String.valueOf(playerPos[i][0]);
            str += " ";
            str += String.valueOf(playerPos[i][1]);
            str += " ";
            str += String.valueOf(imageID[i]);
            str += " ";
        }
        for(int i=0;i<playerCount;i++) {
            str += String.valueOf(putBombList[i]);
            str += " ";
        }
        str = str.substring(0, str.length()-1);
        return str;
    }

    public void addKey(int playerID, int keyCode) {
        if(keyCode == KeyEvent.VK_SPACE) {
            putBombList[playerID] = 1;
        }
    }

    public void removeKey(int playerID, int keyCode) {
        if(keyCode == KeyEvent.VK_SPACE) {
            putBombList[playerID] = 0;
        }
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int join() {
        playerCount++;
        return (playerCount-1);
    }
}