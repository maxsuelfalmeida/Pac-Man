package elements;

import javafx.scene.image.Image;

/**
 * Createa the elements' animation.
 * 
 * @author Maxsuel F. Almeida
 */
public class AnimatedImage {
    // All the images that make the animation.
    protected Image[] frames;
    
    /**
     *
     * @param length
     */
    public AnimatedImage(int length) {
        frames = new Image[length];
    }
    
    /**
     * Get the frame to be painted.
     * @param direction The current direction of the element. 
     * @param imagesPerDirection The number of image that is part of the 
     * animation in one direction.
     * @return The frame to be painted.
     */
    public Image getFrame(int direction, int imagesPerDirection) {
        return frames[imagesPerDirection * direction + (int)(System.nanoTime() * 0.00000002) % imagesPerDirection];
    }
    
    /*public Image getFrame(int direction, int imagesPerDirection, boolean slowAnimation) {
        return frames[imagesPerDirection * direction + (int)(System.nanoTime() * 0.000000008) % imagesPerDirection];
    }*/
}
//return frames[imagesPerDirection * direction + (int)(System.nanoTime() * 0.00000002) % imagesPerDirection];