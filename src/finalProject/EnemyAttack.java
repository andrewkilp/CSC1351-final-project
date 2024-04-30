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
public class EnemyAttack extends Sprite{
    Picture attack;
    int damage;
    Thread clearEmptyAttacks = new Thread(() -> {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(building.class.getName()).log(Level.SEVERE, null, ex);
        }
        attack = new Picture("empty.png");
        setPicture(attack);
    });
    public EnemyAttack(Point enemyPos, Point attackPoint, int s, int d, SpriteComponent sc) {
        super(sc);
        damage =d;
        switch(s) {
            case 0 -> {
                if(attackPoint.x < enemyPos.x) {
                    attack = new Picture("meleeBasicAttackL.png");
                } else if(attackPoint.x > enemyPos.x) {
                    attack = new Picture("meleeBasicAttackR.png");
                } else if (attackPoint.y < enemyPos.y) {
                    attack = new Picture("meleeBasicAttackU.png");
                } else {
                    attack = new Picture("meleeBasicAttackD.png");
                }
                setPicture(attack);
                setX(attackPoint.x*32);
                setY(attackPoint.y*32);
            }
            case 2 -> {
                if(attackPoint.x < enemyPos.x) {
                    attack = new Picture("meleeAdvancedAttackL.png");
                    setX(attackPoint.x*32-24);
                    setY(attackPoint.y*32);
                } else if(attackPoint.x > enemyPos.x) {
                    attack = new Picture("meleeAdvancedAttackR.png");
                    setX(attackPoint.x*32+52);
                    setY(attackPoint.y*32);
                } else if (attackPoint.y < enemyPos.y) {
                    attack = new Picture("meleeAdvancedAttackU.png");
                    setX(attackPoint.x*32);
                    setY(attackPoint.y*32-24);
                } else {
                    attack = new Picture("meleeAdvancedAttackD.png");
                    setX(attackPoint.x*32);
                    setY(attackPoint.y*32+52);
                }
                setPicture(attack);
                
    
            }
            case 4 -> {
                if(attackPoint.x < enemyPos.x) {
                    attack = new Picture("meleeEliteAttackL.png");
                    setX(attackPoint.x*32-24);
                    setY(attackPoint.y*32-32);
                } else if(attackPoint.x > enemyPos.x) {
                    attack = new Picture("meleeEliteAttackR.png");
                    setX(attackPoint.x*32+86);
                    setY(attackPoint.y*32);
                } else if (attackPoint.y < enemyPos.y) {
                    attack = new Picture("meleeEliteAttackU.png");
                    setX(attackPoint.x*32);
                    setY(attackPoint.y*32-8);
                } else {
                    attack = new Picture("meleeEliteAttackD.png");
                    setX(attackPoint.x*32);
                    setY(attackPoint.y*32+86);
                }
                setPicture(attack);
                
            }
        }
        
        
    }
    
}
