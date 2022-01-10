package elements;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class represents all the pacdots ih tne game.
 * 
 * @author Maxsuel F. Almeida
 */
public class PacDots {
    private ArrayList <PacDot> pacDots;
    private Board board;
    public PacDots(Image image, Board board)
    {
        pacDots = new ArrayList<>();
        for(Cell c : board.cells)
            if(c.isPacDot(board.maze))
                pacDots.add(new PacDot(c.getRow(), c.getColumn(), image));
        
        this.board = board;
    }
    
    public synchronized void update()
    {
        
        pacDots.forEach(p -> {
            int row = p.getRow();
            int col = p.getColumn();
            if (!board.cells.get(28 *row  + col).isPacDot(board.maze)) {
                pacDots.remove(p);
            }         
        });
    }
    
    public void render(GraphicsContext graphicsContext)
    {
        for(PacDot p : pacDots)
            p.render(graphicsContext);
    }
}
