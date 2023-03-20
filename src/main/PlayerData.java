package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class PlayerData {

    private int coins, diamonds;
    private int kills;
    private int waveNum;

    public PlayerData() {
        readSavedData();
    }

    public void readSavedData() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("data/player/playerData.txt"));
            in.readLine();

            String[] data = in.readLine().split(",");
            coins = Integer.parseInt(data[0]);
            diamonds = Integer.parseInt(data[1]);
            kills = Integer.parseInt(data[2]);
            waveNum = Integer.parseInt(data[3]);

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        try {
            PrintWriter out = new PrintWriter(new FileWriter("data/player/playerData.txt"));

            out.println("[COINS], [DIAMONDS], [KILLS], [WAVE]");
            int[] data = {coins, diamonds, kills, waveNum};
            for (int i = 0; i < data.length; i++) {
                if (i == data.length - 1) {
                    out.print(data[i]);
                    continue;
                }
                out.print(data[i] + ",");
            }

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCoins(int amount) {
        coins += amount;
    }

    public void addDiamonds(int amount) {
        diamonds += amount;
    }

    public void addKills(int amount) {
        kills += amount;
    }

    public void addWaves(int amount) {
        waveNum += amount;
    }

    // TODO: GETTERS
    public int getCoins() {
        return coins;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public int getKills() {
        return kills;
    }

    public int getWaveNum() {
        return waveNum;
    }
}
