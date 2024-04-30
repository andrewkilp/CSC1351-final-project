/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalProject;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andyk
 */
public class waveManager {
    static int waveNum = 1;
    static double difficultyS = 1;
    static int maxLevel = 5;
    public static int numEnemy = 0;
    public static int remainingEnemy =0;
    static Thread waitSpawn = new Thread(() -> {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(waveManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean valid = true;
        spawnNextWave();
        while(valid) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(waveManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        spawnNextWave();
        }
    });
    public static Enemy[] spawnNextWave() {
        difficultyS = difficultyS * 1.1;
        int waveDifficulty = (int) difficultyS;
        Random r = new Random();
        numEnemy = r.nextInt(waveDifficulty*6) +2;
        if(numEnemy < waveDifficulty+2) {
            numEnemy = r.nextInt(waveDifficulty*6);
        }
        remainingEnemy = remainingEnemy + numEnemy;
//        Enemy[] wave = new Enemy[numEnemy];
        Enemy[] wave = new Enemy[1];
        
        for(int i=0; i<wave.length; i++) {
            int enemyType = r.nextInt(waveDifficulty);
            enemyType=2;
            if(enemyType > 5) enemyType = maxLevel;
            wave[i] = new Enemy(enemyType, Game.sc);
            wave[i].moveEnemy(enemyType);
        }
        waveNum++;
        return wave;
    }
    public static void spawner() {
        waitSpawn.start();
    }
}
