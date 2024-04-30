/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalProject;

/**
 *
 * @author andyk
 */
public class stats {
    int hp;
    int speed;
    int range;
    int damage;
    // this is used to call walls
    public stats(int hparg) {
        hp = hparg;
    }
    //used to call towers
    public stats(int hparg, int damagearg, int rangearg) {
        hp = hparg;
        damage = damagearg;
        range = rangearg;
    }
    // this is used for calling the players stats
    public stats(int hparg, int damagearg) {
        hp = hparg;
        damage = damagearg;
    }
    // this is used for calling enemys stats
     public stats(int hparg, int speedarg, int damagearg, int rangearg) {
        hp = hparg;
        speed = speedarg;
        range = rangearg;
        damage = damagearg;
    }
}
