/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns.happines.Graphics;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author draque
 */
public class TilePanel extends JPanel {
    public final static int TILE_SIZE = 8; // in pixels
    public final static int TILE_DATA_SIZE = TILE_SIZE * 2; // in bytes
    private int[] data;
    private Color color0;
    private Color color1;
    private Color color2;
    private Color color3;
    
    public TilePanel() {
        data = new int[TILE_DATA_SIZE];
        
        color0 = new Color(0, 0, 0);
        color1 = new Color(83, 83, 83);
        color2 = new Color(166, 166, 166);
        color3 = new Color(255, 255, 255);
    }
    
    public void setData(int[] _data) throws Exception {
        if (_data.length != TILE_DATA_SIZE) {
            throw new Exception("Data must be of size: " + TILE_DATA_SIZE);
        }
        
        data = _data;
    }
    
    @Override 
    public void paint(Graphics g) {
        super.paint(g);
        
        int pixelWidth = (this.getSize().width / TILE_SIZE);
        int pixelHeight = (this.getSize().height / TILE_SIZE);
        
        for (int y = 0; y < TILE_SIZE; y++) {
            for (int x = 0; x < TILE_SIZE; x++) {
                int xPos = x * pixelWidth;
                int yPos = y * pixelHeight;
                
                g.setColor(getColor(x, y));
                g.fillRect(xPos, yPos, pixelWidth - 2, pixelHeight - 2);
            }
        }
    }
    
    private Color getColor(int x, int y) {
        int layer1 = data[y];
        int layer2 = data[y + TILE_SIZE];
        
        int value = 0;
        
        if (((layer1 >> x) & 1) == 1) {
            value = 1;
        }
        
        if (((layer2 >> x) & 1) == 1) {
            value += 2;
        }
        
        switch(value) {
            case 1:
                return color1;
            case 2:
                return color2;
            case 3:
                return color3;
        }
        
        return color0;
    }
}
