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
import static finalProject.Game.pInv;
import static finalProject.Game.rand;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andyk
 */
public class Enemy extends Sprite {

    Picture ep;
    stats mStats;
    Point enemyPos;
    int typeRef;
    Thread moveMan = new Thread(() -> {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Enemy.class.getName()).log(Level.WARNING, null, ex);
        }
        boolean valid = true;
        int sleepTime = mStats.speed * 250;
        int type = typeRef;
        int area = 0;
        Point moveTo;
        if (type >= 2) {
            area = 1;
        }
        if (type >= 4) {
            area = 2;
        }
        switch (type) {
            case 0 -> {
                while (valid) {
                    moveTo = moveTo(enemyPos, Game.pPoint, Game.obstaclePositions);
                    if (validMoveTile(moveTo, enemyPos.x, enemyPos.y, area)) {
                        layout[enemyPos.y][enemyPos.x] = 0;
                        layout[moveTo.y][moveTo.x] = type + 2;
                        enemyPos.y = moveTo.y;
                        enemyPos.x = moveTo.x;
                        setX(enemyPos.x * 32);
                        setY(enemyPos.y * 32);
                    } else {
                        new EnemyAttack(enemyPos, moveTo, type, mStats.damage, Game.sc);
                    }

                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (mStats.hp <= 0) {
                        valid = false;
                    }
                }
            }
            case 2 -> {
                while (valid) {
                    moveTo = moveTo(enemyPos, Game.pPoint, Game.obstaclePositions);
                    if (validMoveTile(moveTo, enemyPos.x, enemyPos.y, 1)) {
                        layoutTileUpdater(moveTo.x, moveTo.y, 1, type + 2);
                        enemyPos.y = moveTo.y;
                        enemyPos.x = moveTo.x;
                        setX(enemyPos.x * 32);
                        setY(enemyPos.y * 32);
                    } else {
                        new EnemyAttack(enemyPos, moveTo, type, mStats.damage, Game.sc);
                    }
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (mStats.hp <= 0) {
                        valid = false;
                    }
                }
            }
            case 4 -> {
                while (valid) {
                    moveTo = moveTo(enemyPos, Game.pPoint, Game.obstaclePositions);
                    if (validMoveTile(moveTo, enemyPos.x, enemyPos.y, area)) {
                        layoutTileUpdater(moveTo.x + 1, moveTo.y + 1, 2, type + 2);
                        enemyPos.y = moveTo.y;
                        enemyPos.x = moveTo.x;
                        setX(enemyPos.x * 32);
                        setY(enemyPos.y * 32);
                    } else {
                        new EnemyAttack(enemyPos, moveTo, type, mStats.damage, Game.sc);
                    }

                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (mStats.hp <= 0) {
                        valid = false;
                    }
                }
            }
            default -> {
            }
        }
        switch (typeRef) { 
            case 0 -> {
                pInv.basicMonsterParts = pInv.basicMonsterParts + rand.nextInt(3) + 1;
            }
            case 1 -> {
                pInv.basicMonsterParts = pInv.basicMonsterParts + rand.nextInt(5) + 1;
            }
            case 2 -> {
                pInv.advancedMosnsterParts = pInv.advancedMosnsterParts + rand.nextInt(3) + 1;
            }
            case 3 -> {
                pInv.advancedMosnsterParts = pInv.advancedMosnsterParts + rand.nextInt(5) + 1;
            }
            case 4 -> {
                pInv.eliteMonsterParts = pInv.eliteMonsterParts + rand.nextInt(3) + 1;
            }
            case 5 -> {
                pInv.eliteMonsterParts = pInv.eliteMonsterParts + rand.nextInt(5) + 1;
            }
        }
        
    });
    Thread rangedRat = new Thread(() -> {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(building.class.getName()).log(Level.SEVERE, null, ex);
        }
        Point moveTo;
        int type = 0;
        if (typeRef >= 2) {
            type = 1;
        } else if (typeRef >= 4) {
            type = 2;
        }
        boolean valid = true;
        int sleepTime = 1000 / (mStats.speed);
        switch (typeRef) {
            case 1 -> {
                while (valid) {
                    moveTo = moveTo(enemyPos, Game.pPoint, Game.obstaclePositions);
                    if (playerInRange(enemyPos.x, enemyPos.y, Game.xPos, Game.yPos, mStats.range)) {
                        new EnemyAttackR(enemyPos, Game.xPos, Game.yPos, typeRef, mStats.damage, Game.sc);
                    } else if (validMoveTile(moveTo, enemyPos.x, enemyPos.y, type)) {
                        layout[enemyPos.y][enemyPos.x] = 0;
                        layout[moveTo.y][moveTo.x] = typeRef + 2;
                        enemyPos.y = moveTo.y;
                        enemyPos.x = moveTo.x;
                        setX(enemyPos.x * 32);
                        setY(enemyPos.y * 32);
                    } else {
                        new EnemyAttackR(enemyPos, Game.xPos, Game.yPos, typeRef, mStats.damage, Game.sc);
                    }
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(building.class.getName()).log(Level.WARNING, null, ex);
                    }
                    if (mStats.hp <= 0) {
                        valid = false;
                    }
                }
            }
            case 3 -> {
                while (valid) {
                    moveTo = moveTo(enemyPos, Game.pPoint, Game.obstaclePositions);
                    if (playerInRange(enemyPos.x, enemyPos.y, Game.xPos, Game.yPos, mStats.range)) {
                        new EnemyAttackR(enemyPos, Game.xPos, Game.yPos, typeRef, mStats.damage, Game.sc);
                    } else if (validMoveTile(moveTo, enemyPos.x, enemyPos.y, type)) {
                        layoutTileUpdater(moveTo.x, moveTo.y, 1, type + 2);
                        enemyPos.y = moveTo.y;
                        enemyPos.x = moveTo.x;
                        setX(enemyPos.x * 32);
                        setY(enemyPos.y * 32);
                    } else {
                        new EnemyAttackR(enemyPos, Game.xPos, Game.yPos, typeRef, mStats.damage, Game.sc);
                    }
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(building.class.getName()).log(Level.WARNING, null, ex);
                    }
                    if (mStats.hp <= 0) {
                        valid = false;
                    }
                }
            }
            case 5 -> {
                while (valid) {
                    moveTo = moveTo(enemyPos, Game.pPoint, Game.obstaclePositions);
                    if (playerInRange(enemyPos.x, enemyPos.y, Game.xPos, Game.yPos, mStats.range)) {
                        new EnemyAttackR(enemyPos, Game.xPos, Game.yPos, typeRef, mStats.damage, Game.sc);
                    } else if (validMoveTile(moveTo, enemyPos.x, enemyPos.y, type)) {
                        layoutTileUpdater(moveTo.x + 1, moveTo.y + 1, 2, type + 2);
                        enemyPos.y = moveTo.y;
                        enemyPos.x = moveTo.x;
                        setX(enemyPos.x * 32);
                        setY(enemyPos.y * 32);
                    } else {
                        new EnemyAttackR(enemyPos, Game.xPos, Game.yPos, typeRef, mStats.damage, Game.sc);
                    }
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(building.class.getName()).log(Level.WARNING, null, ex);
                    }
                    if (mStats.hp <= 0) {
                        valid = false;
                    }
                }
            }
        }
        switch (typeRef) {
            case 0 -> {
                pInv.basicMonsterParts = pInv.basicMonsterParts + rand.nextInt(3) + 1;
            }
            case 1 -> {
                pInv.basicMonsterParts = pInv.basicMonsterParts + rand.nextInt(5) + 1;
            }
            case 2 -> {
                pInv.advancedMosnsterParts = pInv.advancedMosnsterParts + rand.nextInt(3) + 1;
            }
            case 3 -> {
                pInv.advancedMosnsterParts = pInv.advancedMosnsterParts + rand.nextInt(5) + 1;
            }
            case 4 -> {
                pInv.eliteMonsterParts = pInv.eliteMonsterParts + rand.nextInt(3) + 1;
            }
            case 5 -> {
                pInv.eliteMonsterParts = pInv.eliteMonsterParts + rand.nextInt(5) + 1;
            }
        }
        
    });

    public Enemy(int type, SpriteComponent sc) {
        super(sc);
        // type chart 0-basic enemy, 1-basic ranged , 2- advancedmelee , 3 - advanced ranged  , 4 - elite melee, 5 - elite ranged
        switch (type) {
            case 0 -> {
                mStats = new stats(3, 3, 1, 1);
                ep = new Picture("enemyBasic.png");
                typeRef = 0;
            }
            case 1 -> {
                mStats = new stats(3, 1, 1, 6);
                ep = new Picture("enemyRBasic.png");
                typeRef = 1;
            }
            case 2 -> {
                mStats = new stats(10, 4, 2, 1);
                ep = new Picture("enemyAdvanced.png");
                typeRef = 2;
            }
            case 3 -> {
                mStats = new stats(3, 1, 2, 6);
                ep = new Picture("enemyRAdvanced.png");
                typeRef = 3;
            }
            case 4 -> {
                mStats = new stats(20, 6, 3, 1);
                ep = new Picture("enemyElite.png");
                typeRef = 4;
            }
            case 5 -> {
                mStats = new stats(15, 2, 3, 200);
                ep = new Picture("enemyRElite.png");
                typeRef = 5;
            }
            default -> {
            }
        }

        //spawn point
        boolean valid = false;

        do {
            int rXPos = Game.rand.nextInt(Game.layoutW - 2);
            int rYPos = Game.rand.nextInt(Game.layoutH - 2);
            if (Game.validEnemySpawnTiles[rYPos][rXPos] == 0) {
                Game.layout[rYPos][rXPos] = type + 2;
                enemyPos = new Point(rXPos, rYPos);
                Game.invalidTileUpdater(rXPos, rYPos, 0);
                setX(Game.tileW * rXPos);
                setY(Game.tileH * rYPos);
                enemyPos = new Point(rXPos, rYPos);
                valid = true;
            } else {
                System.out.printf("attempted spawn at x %d y %d %n", rXPos, rYPos);
            }
        } while (!valid);

        setPicture(ep);

    }

    public void moveEnemy(int type) {
        if (type % 2 == 0) {
            moveMan.start();
        } else {
            rangedRat.start();
        }
    }

    static double calculateDistance(Point point1, Point point2) {
        return Math.sqrt(Math.pow((point2.x - point1.x), 2) + Math.pow((point2.y - point1.y), 2));
    }

    double calculateYeild(Point currentPos, Point targetPos, List<Point> obstaclePositions, double targetWeight, double obstaclesWeight) {
        double dTT = calculateDistance(currentPos, targetPos);
        double dTO = Integer.MAX_VALUE;
        for (Point obstacle : obstaclePositions) {
            double distance = calculateDistance(currentPos, obstacle);
            if (distance < dTO) {
                dTO = distance;
            }
        }

        return dTT * targetWeight + dTO * obstaclesWeight;
    }

    public Point moveTo(Point enemyPos, Point targetPos, List<Point> obstaclePositions) {

        double minYeild = Double.MAX_VALUE;
        Point nextMove = new Point(enemyPos.x, enemyPos.y);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0 || i == -1 && j == -1 || i == +1 && j == -1 || i == +1 && j == -1 || i == +1 && j == +1) {
                    continue;
                }
                Point newPos = new Point(enemyPos.x + i, enemyPos.y + j);
                double yeild = calculateYeild(newPos, targetPos, obstaclePositions, 5, 10);
                if (yeild < minYeild) {
                    minYeild = yeild;
                    nextMove = newPos;
                }

            }
        }
        return nextMove;
    }

    private boolean validMoveTile(Point p, int currentXPos, int currentYPos, int type) {
        switch (type) {
            case 0 -> {
                return !(layout[p.y][p.x] > 0);
            }
            case 1 -> {
                // 2 by 2
//                if (p.x < currentXPos) {
//                    System.out.println("1st");
//                    return !(layout[currentYPos][currentXPos-1] != 0 && 0 != layout[currentYPos+1][currentXPos-1]);
//                } else if (p.x > currentXPos) {
//                    System.out.println("2nd");
//                    return (layout[p.y + 1][p.x + 1] > 0 && layout[p.y][p.x+1] > 0);
//                } else if (p.y < currentYPos) {
//                    System.out.println("3rd");
//                    return (layout[p.y][p.x] > 0 && layout[p.y][p.x + 1] > 0);
//                } else {
//                    System.out.println("4th");
//                    return (layout[p.y+1][p.x] > 0 && layout[p.y + 1][p.x + 1] > 0);
//                }
                if (p.x > currentXPos) {
                    return (layout[p.y + 1][p.x + 1] == 0 && layout[p.y][p.x + 1] == 0 && layout[p.y + 1][p.x + 2] == 0 && layout[p.y][p.x + 2] == 0);
                } else if (p.x < currentXPos) {
                    return (layout[p.y + 1][p.x] == 0 && layout[p.y][p.x] == 0 && layout[p.y + 1][p.x - 1] == 0 && layout[p.y][p.x - 1] == 0);
                } else if (p.y < currentYPos) {
                    return (layout[p.y][p.x] == 0 && layout[p.y][p.x + 1] == 0 && layout[p.y - 1][p.x] == 0 && layout[p.y - 1][p.x + 1] == 0);
                } else {
                    return (layout[p.y + 1][p.x] == 0 && layout[p.y + 1][p.x + 1] == 0 && layout[p.y + 2][p.x] == 0 && layout[p.y + 2][p.x + 1] == 0);
                }
            }
            case 2 -> {
                // 3 by 3
                if (p.x > currentXPos) {
                    return (layout[p.y + 2][p.x + 2] == 0 && layout[p.y + 1][p.x + 2] == 0 && layout[p.y][p.x + 2] == 0 && layout[p.y + 2][p.x + 3] == 0 && layout[p.y + 1][p.x + 3] == 0 && layout[p.y][p.x + 3] == 0);
                } else if (p.x < currentXPos) {
                    return (layout[p.y + 1][p.x] == 0 && layout[p.y + 2][p.x] == 0 && layout[p.y][p.x] == 0 && layout[p.y + 1][p.x - 1] == 0 && layout[p.y + 2][p.x - 1] == 0 && layout[p.y][p.x - 1] == 0);
                } else if (p.y < currentYPos) {
                    return (layout[p.y][p.x] == 0 && layout[p.y][p.x + 1] == 0 && layout[p.y][p.x + 2] == 0 && layout[p.y - 1][p.x] == 0 && layout[p.y - 1][p.x + 1] == 0 && layout[p.y - 1][p.x + 2] == 0);
                } else {
                    return layout[p.y + 2][p.x] == 0 && layout[p.y + 2][p.x + 1] == 0 && layout[p.y + 2][p.x + 2] == 0 && layout[p.y + 3][p.x] == 0 && layout[p.y + 3][p.x + 1] == 0 && layout[p.y + 3][p.x + 2] == 0;
                }
            }
        }
        return false;
    }

    private boolean playerInRange(int x, int y, int pXPos, int pYPos, int range) {
        return Math.abs(pXPos - x) <= range && Math.abs(pYPos - y) <= range;
    }

    private static void layoutTileUpdater(int x, int y, int area, int num) {
        if (area == 0) {
            layout[y][x] = num;
        }
        if (area == 1) {
            for (int i = x - area; i <= x + area + 1; i++) {
                for (int j = y - area; j <= y + area + 1; j++) {
                    if (i < 0 || j < 0 || i >= layoutW || j >= layoutH) {
                        continue;
                    }
                    if (i == x - area || j == y - area || i == x + area + 1 || j == y + area + 1) {
                        layout[j][i] = 0;
                        continue;
                    }
                    layout[j][i] = num;
                }
            }
        }
        if (area == 2) {
            area =1;
            for (int i = x - area - 1; i <= x + area + 1; i++) {
                for (int j = y - area - 1; j <= y + area + 1; j++) {
                    if (i < 0 || j < 0 || i >= layoutW || j >= layoutH) {
                        continue;
                    }
                    if (i == x - area-1  || j == y - area-1  || i == x + area+1  || j == y + area+1 ) {
                        layout[j][i] = 0;
                        continue;
                    }
                    layout[j][i] = num;
                }
            }
        }
    }
    public void clearTiles() {
            Game.layout[enemyPos.y][enemyPos.x] =0;
        if(typeRef >=2 ) {
            Game.layout[enemyPos.y+1][enemyPos.x+1] =0;
            Game.layout[enemyPos.y+1][enemyPos.x] =0;
            Game.layout[enemyPos.y][enemyPos.x+1] =0;
        } 
        if(typeRef >=4) {
            Game.layout[enemyPos.y+2][enemyPos.x] =0;
            Game.layout[enemyPos.y+2][enemyPos.x+1] =0;
            Game.layout[enemyPos.y+2][enemyPos.x+2] =0;
            Game.layout[enemyPos.y][enemyPos.x+2] =0;
            Game.layout[enemyPos.y+1][enemyPos.x+2] =0;
        }
    }
    public void dealDamage(int dam) {
        mStats.hp = mStats.hp - dam;
    }
}
