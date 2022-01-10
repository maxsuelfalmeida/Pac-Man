package elements;


import engine.Engine;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * Represents the pacman element.
 * 
 * @author Maxsuel F. de Almeida
 */
public class PacMan extends Element {
    
    
    private Board board;
    public Engine eng;
    private final AnimatedImage animatedImage;
    private int eatenPacDots;
    private int eatenPowerPills;
    private ArrayList<Ghost> ghosts;
    
    public PacMan(Image[] frames, Board board)
    {
        super(23, 14, 0.11);
        this.board = board;
        this.eng = new Engine(23, 14); 
        animatedImage = new AnimatedImage();
        animatedImage.frames = frames;
        eatenPacDots = 0;
        eatenPowerPills = 0;
    }
    
    @Override
    public void update()
    {
        int row = this.getRow();
        int col = this.getColumn();
        eng.shiftDirection(this, board);
        eng.move(this, board); 
        if(board.cells.get(28 * row  + col).isPacDot(board.maze))
        {
            eatenPacDots++;
            board.clearCell(row, col);
        }
        else if(board.cells.get(28 * row  + col).isPowerPill(board.maze))
        {
            eatenPowerPills++;
            board.clearCell(row, col);
            for(Ghost g : ghosts)
            {
                g.mode = Mode.VULNERABLE;
                g.setFrameAnimation();
            }
        }
        
    }
    
    @Override
    public void render(GraphicsContext graphicsContext)
    {
        switch(eng.direction)
        {
            case LEFT:
                this.setImage(animatedImage.getFrame(2, 4));
                break;
            case RIGHT:
                this.setImage(animatedImage.getFrame(0, 4));
                break;
            case UP:
                this.setImage(animatedImage.getFrame(3, 4));
                break;
            case DOWN:
                this.setImage(animatedImage.getFrame(1, 4));
                break;
            default:
                this.setImage(animatedImage.getFrame(0, 4));
                break;
        }
        graphicsContext.drawImage(this.getImage(), this.getX(), this.getY());
    }
    
    public int getEantenPacDots()
    {
        return eatenPacDots;
    }
    
    public int getEantenPowerPills()
    {
        return eatenPowerPills;
    }
    
    public void setGhosts(ArrayList<Ghost> ghosts)
    {
        this.ghosts = ghosts; 
    }
}
