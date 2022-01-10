package elements;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class represents the pacDot of the game.
 * 
 * @author Maxsuel F. de Almeida
 */
public class PacDot extends Element{
    
    private final int value;
    
    public PacDot(int row, int col, Image image)
    {
        super(row, col, image);
        value = 10;
    }
    
    @Override 
    public void update()
    {
        
    }
    
    @Override
    public void render(GraphicsContext graphicsContext)
    {
        graphicsContext.drawImage(this.getImage(), this.getX() + 14, this.getY() + 14);
    }
    
    public int getValue()
    {
        return value;
    }
}
