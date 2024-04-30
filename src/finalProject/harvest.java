/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalProject;

import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andyk
 */
public class harvest extends Sprite {

    Picture harvestTool;
    Thread noMoreFloatingHarvestTools = new Thread(() -> {
        try {
            Thread.sleep(250);
        } catch (InterruptedException ex) {
            Logger.getLogger(Enemy.class.getName()).log(Level.WARNING, null, ex);
        }
        harvestTool = new Picture("empty.png");
        setPicture(harvestTool);
    });

    public harvest(int x, int y, int pXPos, int pYPos, SpriteComponent sc) {
        super(sc);
        int selectedXPos = x / 32;
        int selectedYPos = y / 32;
        if (resourceInRange(pXPos, pYPos, selectedXPos, selectedYPos, 4)) {
            harvestTool = new Picture("harvestTool.png");
            setX(x);
            setY(y);
            setPicture(harvestTool);
            noMoreFloatingHarvestTools.start();

        }
    }

    // find a way to use the resourse range of a certain tile not player
    private boolean resourceInRange(int x, int y, int sXPos, int sYPos, int range) { 
        return Math.abs(sXPos - x) <= range && Math.abs(sYPos - y) <= range;
    }
}
