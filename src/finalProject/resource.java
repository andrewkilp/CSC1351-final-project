/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalProject;

import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;

/**
 *
 * @author andyk
 */
public class resource extends Sprite {

    Picture resource;
    int type;
    stats rStats;
    Point rP;
    public resource(int resourseType, int count, SpriteComponent sc) {
        super(sc);
        
        type = resourseType;
        if (resourseType == 0) {
            resource = new Picture("tree.png");
            rStats = new stats(5);
        } else if (resourseType == 1) {
            resource = new Picture("rock.png");
            rStats = new stats(10);
        }
        
        
            boolean valid = false;
            do {
                int rXPos = Game.rand.nextInt(Game.layoutW - 2);
                int rYPos = Game.rand.nextInt(Game.layoutH - 2);
                if (Game.layout[rYPos][rXPos] == 0) {
                    setX(Game.tileW * rXPos);
                    setY(Game.tileH * rYPos);
                    Game.layoutTileUpdater(rXPos, rYPos, 0, resourseType + 8);
                    Game.invalidTileUpdater(rXPos, rYPos, 0);
                    rP = new Point(rXPos, rYPos);
                    valid = true;
                    setPicture(resource);
                } else {
                System.out.printf("attempted spawn at x %d y %d %n", rXPos, rYPos);
                }
            } while (!valid);
        
        
    }
    private void spawn() {
        
    }
}
