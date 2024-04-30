/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalProject;


import basicgraphics.BasicFrame;
import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import static finalProject.Game.screenH;
import static finalProject.Game.screenW;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author andyk
 */
public class border extends Sprite{
    public border(SpriteComponent sc) {
        // draw a line arround the map so when bullet touches they disappear
        super(sc);
        
        BufferedImage image = new BufferedImage(screenW, screenH, BufferedImage.TRANSLUCENT);
        Graphics g = image.getGraphics();
        g.setColor(Color.black);
        g.drawLine(1,1,screenW-1, 1);
        g.drawLine(1,1,1, screenH-1);
        g.drawLine(1, screenH-1 ,screenW-1,screenH-1);
        g.drawLine(screenW-1,1,screenW-1, screenH-1);
        Picture pic = new Picture(image);
        setPicture(pic);
    }
}
