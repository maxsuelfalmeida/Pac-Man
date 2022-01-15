package elements;

import engine.Graph;
import engine.Point;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class represents the board of the game.
 * 
 * @author Maxsuel F. de Almeida
 */
public class Board extends Element {
    
    
    public char[][] maze;
    // Each cell of the maze. It will be useful to check collision with the walls
    public ArrayList<Cell> cells;
    // Used in the ghosts' search methods.
    public final Graph mazeGraph;
    
    
    /**
     * Default constructor.
     * @param image The background.
     */
    public Board(Image image) {
        super(0, 0, image);
        
        // Create the maze array.
        this.maze = new char[][]{{'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}, 
                                 {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'}, 
                                 {'#', '.', '#', '#', '#', '#', '.', '#', '#', '#', '#', '#', '.', '#', '#', '.', '#', '#', '#', '#', '#', '.', '#', '#', '#', '#', '.', '#'},
                                 {'#', 'O', '#', '#', '#', '#', '.', '#', '#', '#', '#', '#', '.', '#', '#', '.', '#', '#', '#', '#', '#', '.', '#', '#', '#', '#', 'O', '#'},
                                 {'#', '.', '#', '#', '#', '#', '.', '#', '#', '#', '#', '#', '.', '#', '#', '.', '#', '#', '#', '#', '#', '.', '#', '#', '#', '#', '.', '#'},
                                 {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
                                 {'#', '.', '#', '#', '#', '#', '.', '#', '#', '.', '#', '#', '#', '#', '#', '#', '#', '#', '.', '#', '#', '.', '#', '#', '#', '#', '.', '#'},
                                 {'#', '.', '#', '#', '#', '#', '.', '#', '#', '.', '#', '#', '#', '#', '#', '#', '#', '#', '.', '#', '#', '.', '#', '#', '#', '#', '.', '#'},
                                 {'#', '.', '.', '.', '.', '.', '.', '#', '#', '.', '.', '.', '.', '#', '#', '.', '.', '.', '.', '#', '#', '.', '.', '.', '.', '.', '.', '#'},
                                 {'#', '#', '#', '#', '#', '#', '.', '#', '#', '#', '#', '#', ' ', '#', '#', ' ', '#', '#', '#', '#', '#', '.', '#', '#', '#', '#', '#', '#'},
                                 {'#', '#', '#', '#', '#', '#', '.', '#', '#', '#', '#', '#', ' ', '#', '#', ' ', '#', '#', '#', '#', '#', '.', '#', '#', '#', '#', '#', '#'},
                                 {'#', '#', '#', '#', '#', '#', '.', '#', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', '#', '.', '#', '#', '#', '#', '#', '#'},
                                 {'#', '#', '#', '#', '#', '#', '.', '#', '#', ' ', '#', '#', '#', '*', '*', '#', '#', '#', ' ', '#', '#', '.', '#', '#', '#', '#', '#', '#'},
                                 {'#', '#', '#', '#', '#', '#', '.', '#', '#', ' ', '#', '*', '*', '*', '*', '*', '*', '#', ' ', '#', '#', '.', '#', '#', '#', '#', '#', '#'},
                                 {' ', ' ', ' ', ' ', ' ', ' ', '.', ' ', ' ', ' ', '#', '*', '*', '*', '*', '*', '*', '#', ' ', ' ', ' ', '.', ' ', ' ', ' ', ' ', ' ', ' '},
                                 {'#', '#', '#', '#', '#', '#', '.', '#', '#', ' ', '#', '*', '*', '*', '*', '*', '*', '#', ' ', '#', '#', '.', '#', '#', '#', '#', '#', '#'},
                                 {'#', '#', '#', '#', '#', '#', '.', '#', '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', '#', '.', '#', '#', '#', '#', '#', '#'},
                                 {'#', '#', '#', '#', '#', '#', '.', '#', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', '#', '.', '#', '#', '#', '#', '#', '#'},
                                 {'#', '#', '#', '#', '#', '#', '.', '#', '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', '#', '.', '#', '#', '#', '#', '#', '#'},
                                 {'#', '#', '#', '#', '#', '#', '.', '#', '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', '#', '.', '#', '#', '#', '#', '#', '#'},
                                 {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
                                 {'#', '.', '#', '#', '#', '#', '.', '#', '#', '#', '#', '#', '.', '#', '#', '.', '#', '#', '#', '#', '#', '.', '#', '#', '#', '#', '.', '#'},
                                 {'#', '.', '#', '#', '#', '#', '.', '#', '#', '#', '#', '#', '.', '#', '#', '.', '#', '#', '#', '#', '#', '.', '#', '#', '#', '#', '.', '#'},
                                 {'#', 'O', '.', '.', '#', '#', '.', '.', '.', '.', '.', '.', '.', ' ', ' ', '.', '.', '.', '.', '.', '.', '.', '#', '#', '.', '.', 'O', '#'},
                                 {'#', '#', '#', '.', '#', '#', '.', '#', '#', '.', '#', '#', '#', '#', '#', '#', '#', '#', '.', '#', '#', '.', '#', '#', '.', '#', '#', '#'},
                                 {'#', '#', '#', '.', '#', '#', '.', '#', '#', '.', '#', '#', '#', '#', '#', '#', '#', '#', '.', '#', '#', '.', '#', '#', '.', '#', '#', '#'},
                                 {'#', '.', '.', '.', '.', '.', '.', '#', '#', '.', '.', '.', '.', '#', '#', '.', '.', '.', '.', '#', '#', '.', '.', '.', '.', '.', '.', '#'},
                                 {'#', '.', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '.', '#', '#', '.', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '.', '#'},
                                 {'#', '.', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '.', '#', '#', '.', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '.', '#'},
                                 {'#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
                                 {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}};
        
        // Create the maze graph.
        mazeGraph = new Graph();
        for(int i = 1; i < maze.length - 1; i++) {
            for(int j = 0; j < maze[0].length; j++) {
                if(maze[i][j] == '.' || maze[i][j] == 'O' || maze[i][j] == '*' || maze[i][j] == ' ') {   
                    Point p = new Point(i, j);
                    if(maze[i-1][j] == '.' || maze[i-1][j] == 'O' || maze[i-1][j] == '*' || maze[i-1][j] == ' ')
                        mazeGraph.addEdge(p, new Point(i-1, j));
                    if(maze[i+1][j] == '.' || maze[i+1][j] == 'O' || maze[i+1][j] == '*' || maze[i+1][j] == ' ')
                        mazeGraph.addEdge(p, new Point(i+1, j));
                    if(j != 0 && (maze[i][j-1] == '.' || maze[i][j-1] == 'O' ||  maze[i][j-1] == '*' || maze[i][j-1] == ' '))
                        mazeGraph.addEdge(p, new Point(i, j-1));
                    if(j != maze[0].length - 1 && (maze[i][j+1] == '.' || maze[i][j+1] == 'O' || maze[i][j+1] == '*' || maze[i][j+1] == ' '))
                        mazeGraph.addEdge(p, new Point(i, j+1));
                    if(i == (int)(maze.length / 2 - 1) && j == 0) {
                        Point q = new Point(i, -1);
                        Point r = new Point(i, maze[0].length);
                        mazeGraph.addEdge(p, q);
                        mazeGraph.addEdge(q, r);
                        mazeGraph.addEdge(r, new Point (i, maze[0].length - 1));
                    }   
                }
            }
        }
        
        // Create the cells
        cells = new ArrayList<>();
        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze[0].length; j++) {
                cells.add(new Cell(i, j));
            }
        }
        
    }
    
    /**
     * Given a position, clears the cell.
     * @param row
     * @param col
     */
    public void clearCell(int row, int col) {
        maze[row][col] = ' ';
    }
    
    @Override 
    public void render(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(this.getImage(), this.getX(), this.getY());
    }
    
}
