/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalProject;

/**
 *
 * @author andyk
 */
public class resourceManager {

    static int maxSpawn = 4;
    static int treeCount, rockCount;
    public static void resourseUpdater() {
        treeSpawn();
        rockSpawn();
        maxSpawn = 2;
    }

    public static void resourseUpdater(int type) {
        System.out.println("called");
        if (type == 0 && treeCount < 8) {
            treeSpawn();
        }
        if (type == 1 && rockCount < 8) {
            rockSpawn();
        }

    }

    public static void rockSpawn() {
        int spawnCount = Game.rand.nextInt(maxSpawn) + 1;
        rockCount = spawnCount + rockCount;
        for (int i = 0; i < spawnCount; i++) {
            new resource(1, spawnCount, Game.sc);
        }
    }

    public static void treeSpawn() {
        int spawnCount = Game.rand.nextInt(maxSpawn) + 1;
        treeCount = spawnCount + treeCount;
        for (int i = 0; i < spawnCount; i++) {
            new resource(0, spawnCount, Game.sc);
        }
    }
}
