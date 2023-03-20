package main;

public class WaveHandler {

    private int enemyCount = 10;
    private int waveNum;

    public WaveHandler(int waveNum) {
        System.out.println("Wave handler startnig wave " + waveNum);
        this.waveNum = waveNum;

        for (int i = 0; i < waveNum; i++) {
            enemyCount = (int) (enemyCount * 1.3);
        }
    }

    public void nextWave() {
        enemyCount = (int) (enemyCount * 1.3);
        waveNum++;
    }

    public int getEnemyCount() {
        return enemyCount;
    }
}
