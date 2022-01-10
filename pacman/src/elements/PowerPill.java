package elements;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class represents the power pills fo the game.
 * @author Maxsuel F. Almeida
 */
public class PowerPill extends Element {
    private final int value;
    
    public PowerPill(int row, int col, Image image)
    {
        super(row, col, image);
        value = 50;
        
    }
    
    @Override 
    public void update()
    {
        
    }
    
    @Override
    public void render(GraphicsContext graphicsContext)
    {
        boolean isVisible = (int) (System.nanoTime() * 0.0000000075) % 2 == 0;
        
        if(isVisible)
            graphicsContext.drawImage(this.getImage(), this.getX() + 10, this.getY() + 10);
    }
    
    public int getValue()
    {
        return value;
    }
    
}
