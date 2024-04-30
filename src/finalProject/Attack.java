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
public class Attack extends Sprite {
    Picture attack;
    int damage;
    public Attack(SpriteComponent sc, int bXPos, int bYPos, int enemyX, int enemyY, int type, int damage){
        super(sc);
        this.damage = damage;
        setX(bXPos*32+48);
        setY(bYPos*32+48);
        attack = new Picture("bullet.png");
        if(damage>=3){
            attack = new Picture("bulletT1.png");
        }
        setPicture(attack);
        double delx = enemyX*32 - ((bXPos * 32) + 48 / 2);
        double dely = enemyY*32 - ((bYPos * 32) + 48 / 2);
        double dist = Math.sqrt(delx * delx + dely * dely);
        double velX = 20 * (delx / dist);
        double velY = 20 * (dely / dist);
        setVel(velX,velY);
    }
    public Attack(SpriteComponent sc, player p, int x, int y){
        super(sc);
        damage = Game.pStats.damage;
        setX(Game.screenXPos+16);
        setY(Game.screenYPos+16);
        attack = new Picture("bullet.png");
        setPicture(attack);
        double delx = x-p.getX()-p.getWidth()/2;
        double dely = y-p.getY()-p.getHeight()/2;
        double dist = Math.sqrt(delx*delx+dely*dely);
        setVel(15*delx/dist,15*dely/dist);
    }
}
