/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalProject;

/**
 *
 * @author andyk
 */
public class inventory {
    public int wood, rock, basicMonsterParts, advancedMosnsterParts, eliteMonsterParts;
    public inventory() {
        wood = 0;
        rock = 0;
        basicMonsterParts = 0;
        advancedMosnsterParts = 0;
        eliteMonsterParts = 0;
    }
    // when made from settings menu 
    public inventory(int wood, int rock, int basicMonsterParts, int advancedMosnsterParts, int eliteMonsterParts) {
        this.wood = wood;
        this.rock = rock;
        this.basicMonsterParts = basicMonsterParts;
        this.advancedMosnsterParts =advancedMosnsterParts;
        this.eliteMonsterParts = eliteMonsterParts;
    }
}
