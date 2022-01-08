package engine;

import elements.Board;
import elements.PacMan;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;

/**
 * This class comprises all the methods responsable for the moviment of all
 * dynamic elements.
 * 
 * @author Maxsuel F. de Almeida
 */
public class Engine {
    
    public KeyCode direction;
    public KeyCode nextDirection;
    private double colAux;
    private double rowAux;
    
    public Engine()
    {
        direction = KeyCode.UNDEFINED;
        nextDirection = direction;
        colAux = 14;
        rowAux = 23;
    }
    
    public void pacMove(PacMan pacMan, Board board)
    {
        int col = pacMan.getColumn();
        int row = pacMan.getRow();
        double x = pacMan.getX();
        double y = pacMan.getY();
        int propX = pacMan.getProportionX();
        int propY = pacMan.getProportionY();
        double vel = pacMan.getVelocity();
        
        
        switch(direction)
        {
            case LEFT: 
                if(row == (int)(board.maze.length / 2 - 1) && col == 0)
                {
                    
                    col = board.maze[0].length;
                    colAux = col;
                    x = 784;
                }
                else if(!board.cells.get(28 * row + col - 1).isWall(board.maze))
                {
                    colAux -=  vel;
                    col = (colAux > col - 1) ? col : col - 1;
                    x -= propX * vel;
                }
                break;
                
            case RIGHT:
                if(row == (int)(board.maze.length / 2 - 1) && col == board.maze[0].length - 1)
                {
                    col = -1;
                    colAux = col;
                    x = -28;
                }
                else if(!board.cells.get(28 * row + col + 1).isWall(board.maze))
                {
                    colAux += vel;
                    col = (colAux < col + 1) ? col : col + 1;
                    x += propX * vel;
                }
                break;
                
            case UP:
                if(!board.cells.get(28 * row + col - 28).isWall(board.maze))
                {
                    rowAux -= vel;
                    row = (rowAux > row - 1) ? row : row - 1;
                    y -= propY * vel;
                }
                break;
                
            case DOWN:
                if(!board.cells.get(28 * row + col + 28).isWall(board.maze))
                {
                    rowAux += vel;
                    row = (rowAux < row + 1) ? row : row + 1;
                    y += propY * vel;
                }
                break; 
                
            default:
                break;
        }
        
        pacMan.setColumn(col);
        pacMan.setRow(row);
        pacMan.setX(x);
        pacMan.setY(y);
    }
    
    public void shiftDirection(PacMan pacMan, Board board)
    {
        int col = pacMan.getColumn();
        int row = pacMan.getRow();
        double x = pacMan.getX();
        double y = pacMan.getY();
        
        switch(nextDirection)
        {
            case LEFT:
                direction = (!board.cells.get(28 * row + col - 1).isWall(board.maze) 
                             && y >= (28 * row - 7) && (y + 28) <= (28 * (row + 1) + 7)) ? nextDirection : direction;
                break;
            
            case RIGHT:
                direction = (!board.cells.get(28 * row + col + 1).isWall(board.maze) 
                             && y >= (28 * row - 7) && (y + 28) <= (28 * (row + 1) + 7)) ? nextDirection : direction;
                break;
                
            case UP:
                direction = (!board.cells.get(28 * row + col - 28).isWall(board.maze)
                              && x >= (28 * col - 7) && (x + 28) <= (28 * (col + 1) + 7)) ? nextDirection : direction;
                break;
                
            case DOWN:
                direction = (!board.cells.get(28 * row + col + 28).isWall(board.maze) 
                             && !board.cells.get(28 * row + col + 28).isGhostHouse(board.maze)
                             && x >= (28 * col - 7) && (x + 28) <= (28 * (col + 1) + 7)) ? nextDirection : direction;
                break;
                
            default:
                break;
        }
    }
}
