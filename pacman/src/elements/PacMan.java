package elements;


import engine.Engine;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * Represents the pacman element.
 * 
 * @author Maxsuel F. de Almeida
 */
public class PacMan extends Element {
    
    
    private Board board;
    private Engine eng;
    private AnimatedImage animatedImage;
    
    public PacMan(Image[] frames, Board board, Engine eng)
    {
        super(23, 14, 0.11);
        this.board = board;
        this.eng = eng; 
        animatedImage = new AnimatedImage();
        animatedImage.frames = frames;
    }
    
    @Override
    public void update()
    {
        eng.shiftDirection(this, board);
        eng.pacMove(this, board); 
    }
    
    @Override
    public void render(GraphicsContext graphicsContext)
    {
        switch(eng.direction)
        {
            case LEFT:
                this.setImage(animatedImage.getFrame(2));
                break;
            case RIGHT:
                this.setImage(animatedImage.getFrame(0));
                break;
            case UP:
                this.setImage(animatedImage.getFrame(3));
                break;
            case DOWN:
                this.setImage(animatedImage.getFrame(1));
                break;
            default:
                this.setImage(animatedImage.getFrame(0));
                break;
        }
        graphicsContext.drawImage(this.getImage(), this.getX(), this.getY());
    }
}
