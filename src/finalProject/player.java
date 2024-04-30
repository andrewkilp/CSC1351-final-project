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
public class player extends Sprite{
    Picture player;
    Picture playerB;
    Picture playerH;
    Picture playerU;
    public player(SpriteComponent sc) {
        super(sc);
        freezeOrientation = true;
        player = new Picture("player.png");
        playerB = new Picture("playerBuilder.png");
        playerH = new Picture("playerHarvest.png");
        playerU = new Picture("playerUpgrade.png");
    }
}
