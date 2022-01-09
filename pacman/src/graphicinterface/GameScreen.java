package graphicinterface;

import elements.Board;
import elements.Ghost;
import elements.GhostType;
import elements.PacMan;
import engine.Engine;
import engine.Point;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Maxsuel F. de Almeida
 */
public class GameScreen extends Application {
    
    private GraphicsContext gc;
    private Scene scene;
    private final int stageWidth = 784;
    private final int stageHeight = 938;
    
    private Engine eng;
    
    private PacMan pacMan;
    private Board board;
    private Ghost blinky;
    private Ghost inky;
    
    public GameScreen()
    {
        //Image pacImage = new Image("images/Pacman.gif", 35, 35, false, false);
        Image boardImage = new Image("images/background.png", 784, 868, true, true);
        Image[] pacImages = new Image[16];
        Image[] blinkyImages = new Image[8];
        Image[] inkyImages = new Image[8];
        
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                pacImages[4 * i + j] = new Image("images/pacman_" + i + "_" + j + ".png", 36, 36, false, false);
        
        for(int i = 0; i < 8; i++)
            blinkyImages[i] = new Image("images/ghost_0" + "_" + i + ".png", 36, 36, false, false);
        for(int i = 0; i < 8; i++)
            inkyImages[i] = new Image("images/ghost_2" + "_" + i + ".png", 36, 36, false, false);
                
            
            
        //eng = new Engine();
        board = new Board(boardImage);
        pacMan = new PacMan(pacImages, board);
        blinky = new Ghost(GhostType.BLINKY, 5, 6, blinkyImages, board, pacMan);
        inky = new Ghost(GhostType.INKY, 6, 6, inkyImages, board, pacMan);
    }
    
    @Override
    public void start(Stage stage)
    {
        Group root = new Group();
        scene = new Scene(root, stageWidth, stageHeight);
        Canvas canvas = new Canvas(stageWidth, stageHeight);        
        gc = canvas.getGraphicsContext2D();          
        root.getChildren().add(canvas);
        
        stage.setTitle("PACMAN GAME - BY MAXSUEL F. DE ALMEIDA");
        stage.setScene(scene);
        
        getKey();
        
        gameLoop(stage);
    }
    
    public void gameLoop(Stage stage)
    {
        gc.setFill(Color.RED);
        gc.setStroke(Color.RED);
        
        new AnimationTimer()
        {
            @Override
            public void handle(long currentNanoTime)
            {
                //System.out.println(eng.direction);
                pacMan.update();
                board.update();
                blinky.update();
                inky.update();
                gc.clearRect(0, 0, stageWidth, stageWidth);
                
                board.render(gc);
               /*for(int i = 0; i < board.maze.length; i++)
               {
                   
                   for(int j = 0; j < board.maze[0].length; j++)
                   {
                       if(board.maze[i][j] == '#')
                       {
                           
                           
                           //gc.rect(26.7* j, 28 * i, 26.7, 28);
                           gc.fillRect(28* j, 28 * i, 27, 27);
                       }
                      
                       
                           
                   }
               }*/
               blinky.render(gc);
               inky.render(gc);
                pacMan.render(gc);
                for(Point p : blinky.path)
                {
                    gc.fillRect(28* p.getY(), 28 * p.getX(), 10, 10);
                }
                for(Point p : inky.path)
                {
                    gc.fillRect(28* p.getY(), 28 * p.getX(), 10, 10);
                }
                System.out.println(inky.graphPosition.getX() + " " + inky.graphPosition.getY());
                System.out.println(inky.getRow() + " " + inky.getColumn());
                
            }
        }.start();
        stage.show();
    }
    
    /**
     * Read the arrow key typed by the player.
     * 
     */
    public void getKey()
    {
        scene.setOnKeyPressed((KeyEvent e) -> 
        {
            KeyCode key = e.getCode();
            if(key == KeyCode.LEFT || key == KeyCode.RIGHT || key == KeyCode.UP || key == KeyCode.DOWN)
            {
                pacMan.eng.nextDirection = key;
            }
                
                
        });
    }
}
