/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import javafx.scene.image.Image;

/**
 * Animates the elements image.
 * 
 * @author Maxsuel F. Almeida
 */
public class AnimatedImage {
    public Image[] frames;
    public double duration;

    /**
     *
     * @param direction
     * @return
     */
    public Image getFrame(int direction)
    {
        int index;
        index = 4 * direction + (int)(System.nanoTime() * 0.00000002) % 4;
        return frames[index];
    }
}
