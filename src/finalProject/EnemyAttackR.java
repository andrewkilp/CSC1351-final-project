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
public class EnemyAttackR extends Sprite {
    Picture attack;
    int damage;
    public EnemyAttackR(Point enemyPos, int pXPos, int pYPos, int s, int d, SpriteComponent sc) {
        super(sc);
        damage =d;
        switch(s) {
            case 1-> {
            setX(enemyPos.x*32+16);
            setY(enemyPos.y*32+16);
            attack = new Picture("bulletEnemyBasic.png");
            setPicture(attack);
            double delx = ((pXPos*32)-(enemyPos.x *32))/2;
            double dely = ((pYPos*32)-(enemyPos.y *32))/2;
            double dist = Math.sqrt(delx*delx+dely*dely);
            setVel(5*delx/dist,5*dely/dist);
            }
            case 3 -> {
            setX(enemyPos.x*32+32);
            setY(enemyPos.y*32+32);
            attack = new Picture("bulletAdvancedEnemy.png");
            setPicture(attack);
            double delx = ((pXPos*32)-(enemyPos.x *32))/2;
            double dely = ((pYPos*32)-(enemyPos.y *32))/2;
            double dist = Math.sqrt(delx*delx+dely*dely);
            setVel(10*delx/dist,10*dely/dist);    
            }
            case 5 -> {
            setX(enemyPos.x*32+48);
            setY(enemyPos.y*32+48);
            attack = new Picture("bulletEliteEnemy.png");
            setPicture(attack);
            double delx = ((pXPos*32)-(enemyPos.x *32))/2;
            double dely = ((pYPos*32)-(enemyPos.y *32))/2;
            double dist = Math.sqrt(delx*delx+dely*dely);
            setVel(15*delx/dist,15*dely/dist);    
            }
        } 
        setPicture(attack);
    }
}
