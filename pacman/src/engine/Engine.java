package engine;

import elements.Board;
import elements.Element;
import elements.Ghost;
import elements.GhostType;
import elements.PacMan;
import java.util.ArrayList;
import java.util.Random;
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
    public double colAux;
    public double rowAux;
    
    public Engine(double rowAux, double colAux)
    {
        direction = KeyCode.UNDEFINED;
        nextDirection = direction;
        this.colAux = colAux;
        this.rowAux = rowAux;
    }
    
    public void move(Element elem, Board board)
    {
        int col = elem.getColumn();
        int row = elem.getRow();
        double x = elem.getX();
        double y = elem.getY();
        int propX = elem.getProportionX();
        int propY = elem.getProportionY();
        double speed = elem.getSpeed();
        
        
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
                    colAux -=  speed;
                    col = (colAux > col - 1) ? col : col - 1;
                    x -= propX * speed;
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
                    colAux += speed;
                    col = (colAux < col + 1) ? col : col + 1;
                    x += propX * speed;
                }
                break;
                
            case UP:
                if(!board.cells.get(28 * row + col - 28).isWall(board.maze))
                {
                    rowAux -= speed;
                    row = (rowAux > row - 1) ? row : row - 1;
                    y -= propY * speed;
                }
                break;
                
            case DOWN:
                if(!board.cells.get(28 * row + col + 28).isWall(board.maze))
                {
                    rowAux += speed;
                    row = (rowAux < row + 1) ? row : row + 1;
                    y += propY * speed;
                }
                break; 
                
            default:
                break;
        }
        
        elem.setColumn(col);
        elem.setRow(row);
        elem.setX(x);
        elem.setY(y);
    }
    
    public void shiftDirection(Element elem, Board board)
    {
        int col = elem.getColumn();
        int row = elem.getRow();
        double x = elem.getX();
        double y = elem.getY();
        
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
                             && (Ghost.class.isInstance(elem) || !board.cells.get(28 * row + col + 28).isGhostHouse(board.maze)) 
                             && x >= (28 * col - 7) && (x + 28) <= (28 * (col + 1) + 7)) ? nextDirection : direction;
                break;
                
            default:
                System.out.println(elem.getClass() != Ghost.class);
                break;
        }
    }
    
    
    
    public ArrayList<Point> getPath(Ghost ghost, Board board, PacMan pacMan)
    {  
        return board.mazeGraph.shortestPath(new Point(ghost.getRow(), ghost.getColumn())
                                                        , new Point(pacMan.getRow(), pacMan.getColumn()));
    }
    
    public ArrayList<Point> backToGhostHouse(Ghost ghost, Board board)
    {
        ArrayList<Point> path;
        
        switch(ghost.type)
        {
            case INKY:
                path = board.mazeGraph.shortestPath(new Point(ghost.getRow(), ghost.getColumn()), new Point(14, 13));
                break;
                
            case CLYDE:
                path = board.mazeGraph.shortestPath(new Point(ghost.getRow(), ghost.getColumn()), new Point(14, 14));
                break;
                
            case PINKY:
                path = board.mazeGraph.shortestPath(new Point(ghost.getRow(), ghost.getColumn()), new Point(13, 13));
                break;
                
            default:
                path = board.mazeGraph.shortestPath(new Point(ghost.getRow(), ghost.getColumn()), new Point(13, 14));
                break;
        }
        return path;
    }
    
    public ArrayList<Point> getRandomPath(Ghost ghost, Board board)
    {
        Random generator = new Random();
        int row, col;
        int grow, gcol;
        int prow, pcol;
        
        grow = ghost.getRow();
        gcol = ghost.getColumn();
        prow = (ghost.path.isEmpty()) ? grow : ghost.path.get(ghost.path.size() - 1).getX();
        pcol = (ghost.path.isEmpty()) ? gcol : ghost.path.get(ghost.path.size() - 1).getY();
        
        do
        {
            row = generator.nextInt(30);
            col = generator.nextInt(27);
        }while(board.cells.get(28 * row + col).isWall(board.maze) 
                || board.cells.get(28 * row + col).isGhostHouse(board.maze));
        
        
        return board.mazeGraph.shortestPath(new Point(prow, pcol), new Point(row, col));
    }
}
