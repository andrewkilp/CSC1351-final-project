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
public class buildingUpgrades extends Sprite {
    Picture uH;
    public buildingUpgrades(int x, int y, SpriteComponent sc) {
        super(sc);
        uH = new Picture("upgradeHelper.png");
        setX(x);
        setY(y);
        setPicture(uH);
    }
}
