package elements;

import javafx.geometry.Rectangle2D;

/**
 * The porpuse of this class is helping in check collisions.
 * 
 * @author Maxsuel F. de Almeida
 */
public class Cell {
    private final int row;
    private final int col;
    private final double x;
    private final double y;
    private final int width;
    private final int height;
    private final Rectangle2D collider;
    /**
     * Constructor.
     * 
     * @param row The cell's row
     * @param col The cell's column
     */
    public Cell(int row, int col)
    {
        this.row = row;
        this.col = col;
        width = 27;
        height = 27;
        x = 28 * col;
        y = 28 * row;
        collider = new Rectangle2D(x, y, width, height);
    }
    
    /**
     * Given the maze, check if the cell is a wall.
     * @param maze The game data maze
     * @return boolean
     */
    public boolean isWall(char[][] maze)
    {
        return maze[row][col] == '#';
    }
    
    public boolean isGhostHouse(char[][] maze)
    {
        return maze[row][col] == '*';
    }
    
    public boolean isPacDot(char[][] maze)
    {
        return maze[row][col] == '.';
    }
    
    public boolean isPowerPill(char[][] maze)
    {
        return maze[row][col] == 'O';
    }
    
    public int getRow()
    {
        return row;
    }
    
    public int getColumn()
    {
        return col;
    }
    
    public Rectangle2D getCollider()
    {
        return collider;
    }
}
