package graphicinterface;

import elements.Board;
import elements.Cell;
import elements.Food;
import elements.Ghost;
import elements.GhostType;
import elements.PacDot;
import elements.PacDots;
import elements.PacMan;
import elements.Speed;
import engine.Engine;
import engine.Point;
import java.util.ArrayList;
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
    
    //private Engine eng;
    
    private PacMan pacMan;
    private Board board;
    private ArrayList<Ghost> ghosts;
    private Food foods;
    
    public GameScreen()
    {
        Image boardImage = new Image("images/background.png", 784, 868, true, true);
        Image[] pacImages = new Image[16];
        Image[] blinkyImages = new Image[16];
        Image[] inkyImages = new Image[16];
        Image[] pinkyImages = new Image[16];
        Image[] clydeImages = new Image[16];
        Image[] foodImages = new Image[2];
        
        foodImages[0] = new Image("images/food.png", 7, 7, true, true);
        foodImages[1] = new Image("images/powerPill.png", 14, 14, true, true);
        
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                pacImages[4 * i + j] = new Image("images/pacman_" + i + "_" + j + ".png", 36, 36, false, false);
        
        for(int i = 0; i < 8; i++)
            blinkyImages[i] = new Image("images/ghost_0" + "_" + i + ".png", 36, 36, false, false);
        
        for(int i = 0; i < 8; i++)
            inkyImages[i] = new Image("images/ghost_2" + "_" + i + ".png", 36, 36, false, false);
        
        for(int i = 0; i < 8; i++)
            pinkyImages[i] = new Image("images/ghost_1" + "_" + i + ".png", 36, 36, false, false);
        
        for(int i = 0; i < 8; i++)
            clydeImages[i] = new Image("images/ghost_3" + "_" + i + ".png", 36, 36, false, false);
                
        for(int i = 0; i < 4; i++)
        {
            blinkyImages[i + 8] = new Image("images/ghost_died_" + i + ".png", 36, 36, false, false);
            blinkyImages[i + 12] = new Image("images/ghost_vulnerable_" + i + ".png", 36, 36, false, false);
            
            inkyImages[i + 8] = new Image("images/ghost_died_" + i + ".png", 36, 36, false, false);
            inkyImages[i + 12] = new Image("images/ghost_vulnerable_" + i + ".png", 36, 36, false, false);
            
            pinkyImages[i + 8] = new Image("images/ghost_died_" + i + ".png", 36, 36, false, false);
            pinkyImages[i + 12] = new Image("images/ghost_vulnerable_" + i + ".png", 36, 36, false, false);
            
            clydeImages[i + 8] = new Image("images/ghost_died_" + i + ".png", 36, 36, false, false);
            clydeImages[i + 12] = new Image("images/ghost_vulnerable_" + i + ".png", 36, 36, false, false);
        }
            
            
        
        board = new Board(boardImage);
        pacMan = new PacMan(pacImages, board);
        ghosts = new ArrayList<>();
        ghosts.add(new Ghost(GhostType.BLINKY, 13, 13, blinkyImages, board, pacMan));
        ghosts.add(new Ghost(GhostType.PINKY, 14, 15, pinkyImages, board, pacMan));
        ghosts.add(new Ghost(GhostType.INKY, 14, 12, inkyImages, board, pacMan));
        ghosts.add(new Ghost(GhostType.CLYDE, 13, 15, clydeImages, board, pacMan));
        foods = new Food(foodImages, board);
        ghosts.get(1).setSpeed(Speed.GHOST_NORMAL_SPEED - 0.008);
        pacMan.setGhosts(ghosts);
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
                foods.update();
                for(Ghost g : ghosts)
                    g.update();
                gc.clearRect(0, 0, stageWidth, stageWidth);
                
                board.render(gc);
                for(Ghost g : ghosts)
                    g.render(gc);
                pacMan.render(gc);
                foods.render(gc);
                
                
                //-------------------------------Temporary------------------------------------------------//
                /*for(Point p : blinky.path)
                {
                    gc.fillRect(28* p.getY(), 28 * p.getX(), 10, 10);
                }
                for(Point p : inky.path)
                {
                    gc.fillRect(28* p.getY(), 28 * p.getX(), 10, 10);
                }
                System.out.println(inky.graphPosition.getX() + " " + inky.graphPosition.getY());
                System.out.println(inky.getRow() + " " + inky.getColumn());
                */
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
