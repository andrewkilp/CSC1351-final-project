/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalProject;

import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import static finalProject.Game.layout;
import static finalProject.Game.layoutH;
import static finalProject.Game.layoutW;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andyk
 */
public class building extends Sprite {

    Picture building;
    stats bStats;
    Point bp;
    int bType;
    int warea;
    static int turretCount;
    Thread attackingLogic = new Thread(() -> {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(building.class.getName()).log(Level.WARNING, null, ex);
        }
        System.out.println("called");
        boolean valid = true;
       
         Point nearestEnemy;
        while (valid) {
            int sleepTime = 1000 / (bStats.speed);
            
                if (enemyInRange(bp.x, bp.y, bStats.range*32)) {
                    nearestEnemy = nearest(bp.x, bp.y);
                    if(nearestEnemy!=null) {
                    new Attack(Game.sc, bp.x, bp.y, nearestEnemy.x, nearestEnemy.y, bType, bStats.damage);
                    }
                }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(building.class.getName()).log(Level.WARNING, null, ex);
            }
            if (bStats.hp <= 0) {
                
                valid = false;
            }

        }
        turretCount--;
        System.out.println("ended");
    });

    public building(int type, int sXPos, int sYPos, SpriteComponent sc, int area) {
        super(sc);
        bp = new Point(placeXPosCalc(sXPos), placeYPosCalc(sYPos));
        bType = type;
        int placeXPos = placeXPosCalc(sXPos);
        int placeYPos = placeYPosCalc(sYPos);
        boolean validPlacement = true;
        if (Game.layout[placeYPos][placeXPos] > 0) {
            validPlacement = false;
        }
        switch (type) {
            case 0 -> {
                if (Game.pInv.wood >= 1) {
                    Game.pInv.wood = Game.pInv.wood - 1;
                    building = new Picture("basicWall.png");
                    bStats = new stats(5);
                    warea = 0;
                    layout[placeYPos][placeXPos] = type+10;
                    placeBuilding(bp, type);
                    Game.invalidTileUpdater(placeXPos, placeYPos, area);
                    Game.obstaclePositions.add(bp);
                    obstacleUpdater(placeXPos, placeYPos, 0);
                } else {
                    building = new Picture("empty.png");
                }
            }
            case 1 -> {
                if (Game.pInv.wood >= 15) {
                    validPlacement = validplacement(placeXPos, placeYPos, 1);
                    if (validPlacement && turretCount < 4) {
                        
                        Game.pInv.wood = Game.pInv.wood - 15;
                        warea = 1;
                        building = new Picture("basicTurret.png");
                        bStats = new stats(5, 1, 1, 6);
                        placeBuilding(bp, type);
                        Game.layoutTileUpdater(placeXPos, placeYPos, 1, type + 10);
                        Game.invalidTileUpdater(placeXPos, placeYPos, area);
                        obstacleUpdater(placeXPos, placeYPos, 1);
                        turretCount++;
                        startBuildingAttacking();
                    } else {
                    building = new Picture("empty.png");
                    }
                } else {
                    building = new Picture("empty.png");
                }
            }
        }
        if (validPlacement) {
            setPicture(building);
        }
    }

    public void obstacleUpdater(int x, int y, int area) {
        if (area == 0) {
            Game.obstaclePositions.add(bp);
        }
        for (int i = x - area; i <= x + area; i++) {
            for (int j = y - area; j <= y + area; j++) {
                if (i < 0 || j < 0 || i >= layoutW || j >= layoutH) {
                    continue;
                }
                int pointX = i;
                int pointY = j;
                Game.obstaclePositions.add(new Point(pointX, pointY));
            }
        }
    }

    private boolean validplacement(int x, int y, int area) {
        for (int i = x - area; i <= x + area; i++) {
            for (int j = y - area; j <= y + area; j++) {
                if (i < 0 || j < 0 || i >= Game.layoutW || j >= Game.layoutH) {
                    return false;
                }
                if (Game.layout[j][i] > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void placeBuilding(Point p, int type) {
        switch (type) {
            case 0 -> {
                setX(Game.tileW * p.x);
                setY(Game.tileH * p.y);
            }
            case 1 -> {
                p.x = p.x - 1;
                p.y = p.y - 1;
                setX(Game.tileW * p.x);
                setY(Game.tileH * p.y);
            }
            case 2 -> {
                setX(Game.tileW * p.x);
                setY(Game.tileH * p.y);
            }
        }

    }

    private int placeXPosCalc(int x) {
        int tileNumPlacement = x / 32;
        return tileNumPlacement;
    }

    private int placeYPosCalc(int y) {
        int tileNumPlacement = y / 32;
        return tileNumPlacement;
    }

    public void startBuildingAttacking() {
        attackingLogic.start();
    }

    private boolean enemyInRange(int x, int y, int range) {
        for (int i = x - range; i <= x + range; i++) {
            for (int j = y - range; j <= y + range; j++) {
                if (i < 0 || j < 0 || i >= layout.length || j >= layout[0].length) {
                    continue;
                }
                if (2 <= layout[i][j] && layout[i][j] <= 7) {
                    return true;
                }
            }
        }
        return false;
    }

    private Point nearest(int x, int y) {
        Queue<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[layout.length][layout[0].length];
        queue.offer(new Point(x, y));
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!queue.isEmpty()) {
            Point current = queue.poll();
            int xTest = current.x;
            int yTest = current.y;
            if (2 <= layout[yTest][xTest] && layout[yTest][xTest] <=7) {
                return current;
            }
            for (int[] dir : directions) {
                int newX = xTest + dir[0];
                int newY = yTest + dir[1];
                if (newY >= 0 && newY < layout.length && newX >= 0 && newX < layout[0].length && !visited[newY][newX]) {
                    queue.offer(new Point(newX, newY));
                    visited[newY][newX] = true;
                }
            }
        }
        return null;
    }

    public void upgrade() {
        System.out.println(bType);
        switch (bType) {
            case 0 -> {
                if (Game.pInv.rock >= 3) {
                    bType = bType + 2;
                    bStats.hp = 10;
                    building = new Picture("rockWall.png");
                    Game.layoutTileUpdater(bp.x, bp.y, 0, bType + 10);
                }
            }
            case 1 -> {
                if (Game.pInv.rock >= 5) {
                    bType = bType + 2;
                    bStats.hp = 10;
                    bStats.speed = 2;
                    bStats.range = 8;
                    bStats.damage = 2;
                    building = new Picture("rockTurret.png");
                    Game.layoutTileUpdater(bp.x + 1, bp.y + 1, 1, bType + 10);
                }
            }
            case 2 -> {
                if (Game.pInv.basicMonsterParts >= 3) {
                    Game.pInv.basicMonsterParts = Game.pInv.basicMonsterParts - 3;
                    bType = bType + 2;
                    bStats.hp = 25;
                    building = new Picture("basicMonsterWall.png");
                    Game.layoutTileUpdater(bp.x, bp.y, 0, bType + 10);
                }
            }
            case 3 -> {
                if (Game.pInv.basicMonsterParts >= 20) {
                    Game.pInv.basicMonsterParts = Game.pInv.basicMonsterParts - 20;
                    bType = bType + 2;
                    bStats.hp = 20;
                    bStats.range = 10;
                    bStats.damage = 3;
                    building = new Picture("basicMonsterTurret.png");
                    Game.layoutTileUpdater(bp.x + 1, bp.y + 1, 1, bType + 10);
                }
            }
            case 4 -> {
                if (Game.pInv.advancedMosnsterParts >= 3) {
                    Game.pInv.advancedMosnsterParts = Game.pInv.advancedMosnsterParts - 3;
                    bType = bType + 2;
                    bStats.hp = 50;
                    building = new Picture("advancedMonsterWall.png");
                    Game.layoutTileUpdater(bp.x, bp.y, 0, bType + 10);
                }
            }
            case 5 -> {
                if (Game.pInv.advancedMosnsterParts >= 15) {
                    Game.pInv.advancedMosnsterParts = Game.pInv.advancedMosnsterParts - 15;
                    bType = bType + 2;
                    bStats.hp = 40;
                    bStats.range = 14;
                    bStats.damage = 4;
                    building = new Picture("advancedMonsterTurret.png");
                    Game.layoutTileUpdater(bp.x + 1, bp.y + 1, 1, bType + 10);
                }
            }
            case 6 -> {
                if (Game.pInv.eliteMonsterParts >= 3) {
                    Game.pInv.eliteMonsterParts = Game.pInv.eliteMonsterParts - 3;
                    bType = bType + 2;
                    bStats.hp = 100;
                    building = new Picture("eliteMonsterWall.png");
                    Game.layoutTileUpdater(bp.x, bp.y, 0, bType + 10);
                }
            }
            case 7 -> {
                if (Game.pInv.eliteMonsterParts >= 10) {
                    Game.pInv.eliteMonsterParts = Game.pInv.eliteMonsterParts - 10;
                    bType = bType + 2;
                    bStats.hp = 60;
                    bStats.range = 100;
                    bStats.damage = 5;
                    building = new Picture("eliteMonsterTurret.png");
                    Game.layoutTileUpdater(bp.x + 1, bp.y + 1, 1, bType + 10);
                }
            }
        }
        setPicture(building);
    }
}
