package elements;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author Maxsuel F. de Almeida
 */
public class Food {
    private ArrayList <Element> foods;
    private Board board;
    
    public Food(Image[] image, Board board)
    {
        foods = new ArrayList<>();
        for(Cell c : board.cells)
            if(c.isPacDot(board.maze))
                foods.add(new PacDot(c.getRow(), c.getColumn(), image[0]));
            else if(c.isPowerPill(board.maze))
                foods.add(new PowerPill(c.getRow(), c.getColumn(), image[1]));        
        
        this.board = board;
    }
    
    public synchronized void update()
    {
        
        foods.forEach(f -> {
            int row = f.getRow();
            int col = f.getColumn();
            if (!board.cells.get(28 *row  + col).isPacDot(board.maze) 
                    && !board.cells.get(28 *row  + col).isPowerPill(board.maze)) {
                foods.remove(f);
            }         
        });
    }
    
    public void render(GraphicsContext graphicsContext)
    {
        foods.forEach(f -> {
            f.render(graphicsContext);
        });
    }
}
