/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalProject;

import basicgraphics.BasicContainer;
import basicgraphics.BasicFrame;
import basicgraphics.ClockWorker;
import basicgraphics.SpriteComponent;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author andyk
 */
public class Game {

    static SpriteComponent sc;
    final static int layoutH = 30;
    final static int layoutW = 40;
    // grid size is second val * 32 by first val * 32
    // legend for tiles 0= empty 1 = player 2-7 = enemy and teir 8 - 9 resourse 10 + buildings
    static int[][] layout = new int[layoutH][layoutW];
    // legand for valid tiles 0 = valid 1 = not valid
    static int[][] validEnemySpawnTiles = new int[layoutH][layoutW];
    // building deletion helper
    static int[][] buildings = new int[layoutH][layoutW];
    //used for yeild calc
    public static List<Point> obstaclePositions = new ArrayList<>();
    final static int screenW = 32 * layout[0].length;
    final static int screenH = 32 * layout.length;
    final static int tileW = screenW / layoutW;
    final static int tileH = screenH / layoutH;
    final static int halfW = (layoutW / 2) - 1;
    final static int halfH = (layoutH / 2) - 1;
    public static int xPos = halfW;
    public static int yPos = halfH;
    public static int screenXPos = xPos * 32;
    public static int screenYPos = yPos * 32;
    public static int playerMode = 0;
    public static int buildingType = 0;
    static stats pStats;
    public static Point pPoint = new Point(xPos,yPos);
    final static Color bg = new Color(38, 69, 46);
    final static Color bgLines = new Color(38, 100, 46);
    final public static Random rand = new Random();
    public Game g;
    static player p;
    static BasicFrame bf;
    public static inventory pInv;
    public static void main(String[] args) {
        Game g = new Game();
        g.setUp();
    }

    public void setUp() {
        frameSetup();
        playerDecleration();

    }
    
    public static boolean validWalkingTile(int x, int y, int diffInX, int diffInY) {
        return !(layout[y + diffInY][x] >= 2 || layout[y][x + diffInX] >= 2);
    }

    public void movement(int x, int y, int diffInX, int diffInY) {
        if (diffInX != 0) {
            if (diffInX > 0) {
                xPos++;
                screenXPos = screenXPos + 32;
                pPoint.x++;
               
            } else {
                xPos--;
                screenXPos = screenXPos - 32;
                 pPoint.x--;
            }
        } else {
            if (diffInY > 0) {
                yPos++;
                screenYPos = screenYPos + 32;
                pPoint.y++;
               
            } else {
                yPos--;
                screenYPos = screenYPos - 32;
                pPoint.y--;
            }
        }
        if (layout[yPos][xPos] <= 1) {
            p.setY(tileW * yPos);
            p.setX(tileH * xPos);
            layout[yPos][xPos] = 1;
            layout[yPos - diffInY][xPos - diffInX] = 0;
            invalidTileUpdater(xPos, yPos, 5, diffInX, diffInY);
        }

    }

    public static void invalidTileUpdater(int x, int y, int area, int xDiff, int yDiff) {
        if (area == 0) {
            validEnemySpawnTiles[y][x] = 1;
        }
        for (int i = x - area; i <= x + area; i++) {
            for (int j = y - area; j <= y + area; j++) {
                if (i < 0 || j < 0 || i >= layoutW || j >= layoutH) {
                    continue;
                }
                if(validEnemySpawnTiles[j][i] == 2) continue;
                validEnemySpawnTiles[j][i] = 1;
            }
        }
        //make 0s
        if ((x - 5 > 0 || y - 5 > 0 || x + 5 < layoutW || y + 5 < layoutH)) {
            if (xDiff != 0) {
                //A
                if (xDiff < 0) {
                    for (int i = y - area; i < area + y + 1; i++) {
                        int j = x - area - xDiff;

                         if (i < 0 || j < 0 || j >= layoutW || i >= layoutH) {
                            continue;
                        }
                        if(validEnemySpawnTiles[i][j] == 2) continue;
                        validEnemySpawnTiles[i][j] = 0;
                    }
                }
                //D
                if (xDiff > 0) {
                    for (int i = y - area; i < area + y + 1; i++) {
                        int j = x - area - xDiff;

                         if (i < 0 || j < 0 || j >= layoutW || i >= layoutH) {
                            continue;
                        }
                        if(validEnemySpawnTiles[i][x - area - xDiff] == 2) continue;
                        validEnemySpawnTiles[i][x - area - xDiff] = 0;
                    }
                }
            } else {
                //W
                if (yDiff < 0) {
                    for (int i = x - area; i < area + x + 1; i++) {
                        int j = y + area - yDiff;

                        if (i < 0 || j < 0 || i >= layoutW || j >= layoutH) {
                            continue;
                        }
                      if(validEnemySpawnTiles[y + area - yDiff][i] == 2) continue;
                        validEnemySpawnTiles[y + area - yDiff][i] = 0;
                    }
                }
                //S
                if (yDiff > 0) {
                    for (int i = x - area; i < area + x + 1; i++) {
                        int j = y - area - yDiff;
                        if (i < 0 || j < 0 || i >= layoutW || j >= layoutH) {
                            continue;
                        }
                       if(validEnemySpawnTiles[y - area - yDiff][i] == 2) continue;
                        validEnemySpawnTiles[y - area - yDiff][i] = 0;
                    }
                }
            }
        }
    }

    public static void invalidTileUpdater(int x, int y, int area) {
        if (area == 0) {
            validEnemySpawnTiles[y][x] = 1;
        }
        for (int i = x - area; i <= x + area; i++) {
            for (int j = y - area; j <= y + area; j++) {
                if (i < 0 || j < 0 || i >= layoutW || j >= layoutH) {
                    continue;
                }
                validEnemySpawnTiles[j][i] = 2;
            }
        }
    }
    
    public static void layoutTileUpdater(int x, int y, int area, int num) {
        
        if (area == 0) {
            layout[y][x] = num;
        }
        for (int i = x - area; i <= x + area; i++) {
            for (int j = y - area; j <= y + area; j++) {
                if (i < 0 || j < 0 || i >= layoutW || j >= layoutH) {
                    continue;
                }
                layout[j][i] = num;
            }
        }
    }
    public static void layoutTileUpdater(Point pLay, int type, int num) {
        int area = 0;
        if(type  <= 1) {
            area =0;
        } else if(type <=3) {
            layout[pLay.y][pLay.x] =num;
            layout[pLay.y+1][pLay.x] = num;
            layout[pLay.y][pLay.x+1] = num;
            layout[pLay.y+1][pLay.x+1] = num;
            return;
        } else if(type <= 5) {
            area =1;
        }
        if (area == 0) {
            layout[pLay.y][pLay.x] = num;
        }
        for (int i = pLay.x - area; i <= pLay.x + area; i++) {
            for (int j = pLay.y - area; j <= pLay.y + area; j++) {
                if (i < 0 || j < 0 || i >= layoutW || j >= layoutH) {
                    continue;
                }
                layout[pLay.y][pLay.x] = num;
            }
        }
    }
    //player decleration
    public static void playerDecleration() {
        p = new player(sc);
        pInv = new inventory();
        pStats = new stats(3, 1);
        p.setPicture(p.player);
        p.setX(tileW * halfW);
        p.setY(tileH * halfH);
        invalidTileUpdater(halfW, halfH, 5, 0,0);
    }

    // making the frame
    public void frameSetup() {
        bf = new BasicFrame("Wave survival");
        final Container content = bf.getContentPane();
        final CardLayout cards = new CardLayout();
        content.setLayout(cards);
        BasicContainer bc1 = new BasicContainer();
        content.add(bc1,"Splash");
        final BasicContainer bc2 = new BasicContainer();
        content.add(bc2,"Game");    
        sc = new SpriteComponent() {
            @Override
            public void paintBackground(Graphics g) {
                g.setColor(bg);
                g.fillRect(0, 0, screenW, screenH);
                g.setColor(bgLines);
                for (int i = 0; i < layout[0].length; i++) {
                    g.drawLine(tileW * i, 0, tileW * i, screenH);
                }
                for (int i = 0; i < layout.length; i++) {
                    g.drawLine(0, tileH * i, screenW, tileH * i);
                }
            }
        };
        sc.setPreferredSize(new Dimension(screenW,screenH));
        String[][] splashLayout = {
            {"Title"},
            {"Button"},
            
        };
        bc1.setStringLayout(splashLayout);
        bc1.add("Title",new JLabel("Wave survival"));
        JButton jstart = new JButton("Start");
        jstart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cards.show(content,"Game");
                bc2.requestFocus();
                resourceManager.resourseUpdater();
                waveManager.spawner();
            }
        });
        bc1.add("Button",jstart);
        String[][] gameLayout = {{"Wave survival"}};
        bc2.setStringLayout(gameLayout);
        bc2.add("Wave survival",sc);
        bc2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> {
                        if (yPos <= 0 || !validWalkingTile(xPos, yPos, 0, -1)) {
                        } else {
                            movement(xPos, yPos, 0, -1);
                        }
                    }
                    case KeyEvent.VK_A -> {
                        if (xPos <= 0 || !validWalkingTile(xPos, yPos, -1, 0)) {
                        } else {
                            movement(xPos, yPos, -1, 0);
                        }
                    }
                    case KeyEvent.VK_S -> {
                        if (yPos >= layoutH - 1 || !validWalkingTile(xPos, yPos, 0, 1)) {
                        } else {
                            movement(xPos, yPos, 0, 1);
                        }
                    }
                    case KeyEvent.VK_D -> {
                        if (xPos >= layoutW - 1 || !validWalkingTile(xPos, yPos, 1, 0)) {
                        } else {
                            movement(xPos, yPos, 1, 0);
                        }
                    }
                    case KeyEvent.VK_Q -> {
                        p.setPicture(p.player);
                        playerMode = 0;
                    }
                    case KeyEvent.VK_B -> {
                        if(playerMode != 1) {
                         p.setPicture(p.playerB);
                         playerMode = 1;
                         new buildingPrev(sc);
                        }
                    }
                    case KeyEvent.VK_V -> {
                        p.setPicture(p.playerU);
                        playerMode = 2;
                    }
                    case KeyEvent.VK_F -> {
                        p.setPicture(p.playerH);
                        playerMode = 3;
                    }
                    case KeyEvent.VK_1 -> {
                        buildingType = 0;  
                    }
                    case KeyEvent.VK_2 -> {
                        buildingType = 1;
                    }
                    case KeyEvent.VK_I -> {
                        JOptionPane.showMessageDialog(bf.getContentPane(), "Current Inventory \n"
                                + "1. Wood " + pInv.wood
                                + "\n2. Rock " + pInv.rock
                                + "\n3. basic Monster parts " + pInv.basicMonsterParts
                                + "\n4. Advanced Monster parts " + pInv.advancedMosnsterParts
                                + "\n5. Elite Monster parts " + pInv.eliteMonsterParts
                        );
                    }
                    default -> {
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                p.setVel(0, 0);
            }
        });
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                switch (playerMode) {
                    case 0 -> {
                        new Attack(sc, p, me.getX(), me.getY());
                    }
                    case 1 -> {
                        switch (buildingType) {
                            case 0 -> {
                                new building(buildingType, me.getX(), me.getY(), sc, 1);
                            }
                            case 1 -> {
                                new building(buildingType, me.getX(), me.getY(), sc, 3);
                            }
                            case 2 -> {
                              new building(buildingType, me.getX(), me.getY(), sc, 1);
                            }
                        }
                    }
                    case 2 -> {
                        // upgrade Mode
                        new buildingUpgrades(me.getX(), me.getY(), sc);  
                    }
                    case 3 -> {
                        // harvest Mode
                        new harvest(me.getX(), me.getY(), xPos, yPos, sc);
                    }
                }
            }
        };
        
        sc.addMouseListener(ma);
        sc.addSpriteSpriteCollisionListener(finalProject.Enemy.class, finalProject.Attack.class, (finalProject.Enemy sp1, finalProject.Attack sp2) -> {
            sp1.dealDamage(sp2.damage);
            if (sp1.mStats.hp <= 0) {
                waveManager.numEnemy--;
                sp1.clearTiles();
                sp1.setActive(false);
            }
            sp2.setActive(false);
        });
        sc.addSpriteSpriteCollisionListener(finalProject.player.class, finalProject.EnemyAttack.class, (finalProject.player sp1, finalProject.EnemyAttack sp2) -> {
            pStats.hp =  pStats.hp - sp2.damage;
            if (pStats.hp <= 0) {
                sp1.setActive(false);
                JOptionPane.showMessageDialog(bf.getContentPane(), "Game Over :/");
                System.exit(0);
            }
            sp2.setActive(false);
        });
        sc.addSpriteSpriteCollisionListener(finalProject.player.class, finalProject.EnemyAttackR.class, (finalProject.player sp1, finalProject.EnemyAttackR sp2) -> {
            pStats.hp =  pStats.hp - sp2.damage;
            if (pStats.hp <= 0) {
                sp1.setActive(false);
                JOptionPane.showMessageDialog(bf.getContentPane(), "Game Over :/");
                System.exit(0);
            }
            sp2.setActive(false);
        });
        sc.addSpriteSpriteCollisionListener(finalProject.building.class, finalProject.EnemyAttack.class, (finalProject.building sp1, finalProject.EnemyAttack sp2) -> {
            sp1.bStats.hp = sp1.bStats.hp - sp2.damage;
            if (sp1.bStats.hp <= 0) {
                layout[sp1.bp.y][sp1.bp.x] = 0;
                if(sp1.bType == 1) {
                    layoutTileUpdater(sp1.bp.x+1, sp1.bp.y+1, sp1.warea, 0);
                } else {
                    layoutTileUpdater(sp1.bp.x, sp1.bp.y, sp1.warea, 0);
                }
                sp1.setActive(false);
            }
            sp2.setActive(false);
        });
        sc.addSpriteSpriteCollisionListener(finalProject.building.class, finalProject.EnemyAttackR.class, (finalProject.building sp1, finalProject.EnemyAttackR sp2) -> {
            sp1.bStats.hp = sp1.bStats.hp - sp2.damage;
            if (sp1.bStats.hp <= 0) {
                layout[sp1.bp.y][sp1.bp.x] = 0;
                if(sp1.bType%2 == 1) {
                    layoutTileUpdater(sp1.bp.x+1, sp1.bp.y+1, sp1.warea, 0);
                } else {
                    layoutTileUpdater(sp1.bp.x, sp1.bp.y, sp1.warea, 0);
                }
                sp1.setActive(false);
            }
            sp2.setActive(false);
        });
        sc.addSpriteSpriteCollisionListener(finalProject.border.class, finalProject.Attack.class, (finalProject.border sp1, finalProject.Attack sp2) -> {
            sp2.setActive(false);
        });
        sc.addSpriteSpriteCollisionListener(finalProject.EnemyAttack.class, finalProject.EnemyAttack.class, (finalProject.EnemyAttack sp1, finalProject.EnemyAttack sp2) -> {
            sp1.setActive(false);
            sp2.setActive(false);
        });
        sc.addSpriteSpriteCollisionListener(finalProject.Enemy.class, finalProject.EnemyAttack.class, (finalProject.Enemy sp1, finalProject.EnemyAttack sp2) -> {
            sp2.setActive(false);
        });
        sc.addSpriteSpriteCollisionListener(finalProject.buildingUpgrades.class, finalProject.building.class, (finalProject.buildingUpgrades sp1, finalProject.building sp2) -> {
            sp2.upgrade();
            sp1.setActive(false);
        });
        sc.addSpriteSpriteCollisionListener(finalProject.resource.class, finalProject.harvest.class, (finalProject.resource sp1, finalProject.harvest sp2) -> {
            sp1.rStats.hp= sp1.rStats.hp-pStats.damage;
            if (sp1.type == 0) {
                pInv.wood++;
            } else if (sp1.type == 1) {
                pInv.rock++;
            }
            if(sp1.rStats.hp <= 0) {
                if(sp1.type == 0) {
                    resourceManager.treeCount--;
                } else if (sp1.type == 1) {
                    resourceManager.rockCount--;
                }
                layoutTileUpdater(sp1.rP.x, sp1.rP.y, 0, 0);
                resourceManager.resourseUpdater(sp1.type);
                sp1.setActive(false);
            }
            sp2.setActive(false);
        });
        sc.addSpriteSpriteCollisionListener(finalProject.resource.class, finalProject.EnemyAttack.class, (finalProject.resource sp1, finalProject.EnemyAttack sp2) -> {
            sp1.rStats.hp = sp1.rStats.hp - sp2.damage;
            if(sp1.rStats.hp <= 0) {
                layoutTileUpdater(sp1.rP.x, sp1.rP.y, 0, 0);
                resourceManager.resourseUpdater(sp1.type);
                sp1.setActive(false);
            }
            sp2.setActive(false);
        });
        sc.addSpriteSpriteCollisionListener(finalProject.resource.class, finalProject.EnemyAttackR.class, (finalProject.resource sp1, finalProject.EnemyAttackR sp2) -> {
            sp1.rStats.hp = sp1.rStats.hp - sp2.damage;
            if(sp1.rStats.hp <= 0) {
                layoutTileUpdater(sp1.rP.x, sp1.rP.y, 0, 0);
                resourceManager.resourseUpdater(sp1.type);
                sp1.setActive(false);
            }
            sp2.setActive(false);
        });
        var d = new Dimension(screenW, screenH);
        border border = new border(sc);
        ClockWorker.addTask(sc.moveSprites());
        ClockWorker.initialize(10);
        sc.setPreferredSize(d);
        bf.show();

    }
    
    // debugging methods
    public static void printarr2d(int[][] array) {
        for (int i = 0; i < layoutH; i++) {
            System.out.printf("[");
            for (int j = 0; j < layoutW; j++) {
                System.out.printf("%2d, ", array[i][j]);
            }
            System.out.printf("], %n");
        }
        System.out.printf("%n");
    }
}
