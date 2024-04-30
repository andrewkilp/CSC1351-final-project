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
public class buildingPrev extends Sprite {

    Picture preveiw;

    public buildingPrev(SpriteComponent sc) {
        super(sc);
        switch (Game.buildingType) {
            case 0 -> {
                preveiw = new Picture("basicWallPrev.png");
            }
            case 1 -> {
                preveiw = new Picture("basicTurretPrev.png");
            }
            case 2 -> {

            }
        }
        var mousePos = Game.sc.getMousePosition();
        int tileX = mousePos.x / 32;
        int tileY = mousePos.y / 32;
        setX(tileX * 32);
        setY(tileY * 32);
        setPicture(preveiw);
        preveiw();

    }

    private void preveiw() {
        Thread buildingPreveiw = new Thread(() -> {

            int currentType = 0;
            while (Game.playerMode == 1) {
                var mousePos = Game.sc.getMousePosition();
                if (mousePos != null) {
                    int tileX = mousePos.x / 32;
                    int tileY = mousePos.y / 32;
                    if (Game.buildingType == 1) {
                        setX((tileX * 32) - 32);
                        setY((tileY * 32) - 32);
                    } else {
                        setX(tileX * 32);
                        setY(tileY * 32);
                    }
                    if (currentType != Game.buildingType) {
                        switch (Game.buildingType) {
                            case 0 -> {
                                currentType = 0;
                                preveiw = new Picture("basicWallPrev.png");
                            }
                            case 1 -> {
                                currentType = 1;
                                preveiw = new Picture("basicTurretPrev.png");

                            }
                            case 2 -> {

                            }
                        }
                        setPicture(preveiw);
                    }
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            preveiw = new Picture("empty.png");
            setPicture(preveiw);
        });
        buildingPreveiw.start();

    }

}
