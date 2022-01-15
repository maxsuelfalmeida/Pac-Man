package graphicinterface;

import elements.Board;
import elements.Element;
import elements.Fruit;
import elements.Ghost;
import enums.GhostType;
import elements.LengthConstants;
import enums.Mode;
import elements.PacDot;
import elements.PacMan;
import elements.PowerPill;
import elements.Speed;
import engine.Engine;
import engine.Point;
import enums.FruitType;
import enums.Status;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * This class is responsable for the graphic interface of the game.
 * 
 * @author Maxsuel F. de Almeida
 */
public class GameScreen extends Application {
    
    private GraphicsContext gc;
    private Scene scene;
    private final int stageWidth = LengthConstants.STAGE_WIDTH;
    private final int stageHeight = LengthConstants.STAGE_HEIGHT;
    private Status gameStatus;
    private final Engine eng;
    private final PacMan pacMan;
    private final Board board;
    private final ArrayList<Ghost> ghosts;
    private final ArrayList<Element> foods;
    
    /* Images of the fruits for the levels*/
    Image[] levelsImages = new Image[4];
            
    /**
     * The class constructor.
     */
    public GameScreen() {
        /* Image for the background */
        Image boardImage = new Image("images/background.png", 784, 868, true, true);
        
        /* This fruit images represents the levels*/
        levelsImages[0] = new Image("images/cherry.png", 36, 36, true, true);
        levelsImages[1] = new Image("images/strawberry.png", 36, 36, true, true);
        levelsImages[2] = new Image("images/orange.png", 36, 36, true, true);
        levelsImages[3] = new Image("images/apple.png", 36, 36, true, true);
        
        
        /* Set the game stauts to MENU*/
        gameStatus = Status.MENU;
        
        /* Instantiate the engine of the game*/
        eng = new Engine();
        /* Instantiate the board*/
        board = new Board(boardImage);   
        
        /* Add the pacman*/
        pacMan = this.addPacMan();
        
        /*Add ghosts*/
        ghosts = this.addGhosts();
        
        /* Add pacdots and power pills */
        foods = this.addFoods();
    }
    
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        scene = new Scene(root, stageWidth, stageHeight);
        Canvas canvas = new Canvas(stageWidth, stageHeight);        
        gc = canvas.getGraphicsContext2D();          
        root.getChildren().add(canvas);
        
        /* Set stage title*/
        stage.setTitle("PACMAN GAME");
        /* Set stage icon*/
        stage.getIcons().add(new Image("images/pacman.png"));
        /* Set stage scene*/
        stage.setScene(scene);
        
        /* Start the game loop*/
        gameLoop(stage);
    }
    
    /**
     * Loop for the game.
     * @param stage 
     */
    public void gameLoop(Stage stage) {
        /* Animation for the died pacman*/
        PacManDiedAnimation pacDiedAnimation 
                                    = new PacManDiedAnimation(pacMan, gc);
        AnimationTimer animationTimer;
        
        animationTimer = new AnimationTimer() {
            /* Current time in miliseconds*/
            long startMiliTime;
            /* Current time in miliseconds*/
            long currMiliTime;
            
            /*Image for the pacman lives*/
            Image pacLife = new Image("images/pacman_life.png", 36, 36, true, true);
            
            @Override
            public void handle(long currentNanoTime) {
                /* Clears the game screen*/
                gc.clearRect(0, 0, stageWidth, stageHeight);
                
                /* Set the white color for the retangles*/
                gc.setFill(Color.BLACK);
                
                /* Fill the background*/
                gc.fillRect(0, 0, stageWidth, stageHeight);
                
                switch(gameStatus) {
                    case MENU:
                        /* To make the text animated*/
                        boolean isVisible = 
                                (int) (System.nanoTime() * 0.0000000030) % 2 == 0;
                        
                        /* Set the Arial font*/
                        gc.setFont(new Font("Arial", 35));
                        
                        /* Set the white color for the text*/
                        gc.setFill(Color.WHITE);
                        
                        if(isVisible)
                            /* Write the text*/
                            gc.fillText("PRESS SPACE TO START THE GAME", 78, 434);
                        
                        /* Arial font with a smaller size*/
                        gc.setFont(new Font("Arial", 30));
                        
                        /* Write the author's name in the screen*/
                        gc.fillText("DEVELOPED BY MAXSUEL F. ALMEIDA", 100, 860);
                        
                        /* Draws the Menu Logo*/
                        gc.drawImage(new Image("images/title.png", 546, 138, true, true), 119, 56);
                        
                        /* Get one key typed by the player. If the key is space
                           starts the game.
                        */
                        scene.setOnKeyPressed((KeyEvent e) -> {
                            KeyCode k = e.getCode();
                            if(k == KeyCode.SPACE) {
                                gameStatus = Status.READY;
                                eng.resetLives();
                                eng.resetScore();
                                resetAll(pacMan, ghosts, foods);
                                startMiliTime = System.currentTimeMillis();
                            }
                        });
                        break;
                    
                    case READY:
                        /* Draws the board*/
                        board.render(gc);
                        
                        /*Draws all the ghosts*/
                        for(Ghost g : ghosts)
                            g.render(gc);
                        /* Draws the pacman*/
                        pacMan.render(gc);
                        
                        /* Draws the ready logo */
                        gc.drawImage(new Image("images/ready.png", 138, 21, true, true), 323, 518);
                        
                        /* Get the current time in miliseconds*/
                        currMiliTime = System.currentTimeMillis();
                        
                        /* Spend five seconds in the ready modo */
                        if(currMiliTime - startMiliTime > 3000)
                            gameStatus = Status.PLAYING;
                        break;
                    case PLAYING:
                        if(pacMan.mode.equals(Mode.NORMAL)) {
                            /* Set the next direction of the pacman by taking 
                               the key pressed by de player*/
                            pacMan.setNextDirection(scene);
                            
                            /* Updates all the elements positions and check for
                               collisions between elements*/
                            eng.elementsUpdate(board, pacMan, ghosts, foods);
                            
                            /* If the number of eaten pacdots are 70 or 140, add 
                               a fruit in the board*/
                            if(pacMan.eatenPacDots == 70 || pacMan.eatenPacDots == 140)
                            {
                                foods.add(addFruit(eng.getLevel()));
                                startMiliTime = System.currentTimeMillis();
                            }
                            
                            /* After 20 seconds the fruit disappear*/
                            currMiliTime = System.currentTimeMillis();
                            if(currMiliTime - startMiliTime > 5000 
                               && !foods.isEmpty()
                               && Fruit.class.isInstance(foods.get(foods.size() - 1))) {
                                foods.remove(foods.get(foods.size() - 1));
                            }
                            /* Draws the board*/
                            board.render(gc);
                            
                            /* Draws all the pacdots and power pills that still 
                              in the game*/
                            for(Element f : foods)
                                f.render(gc);
                            
                            /*Draws all the ghosts*/
                            for(Ghost g : ghosts) {
                                g.setFrameAnimation();
                                g.render(gc);
                            }
                                
                            
                            /* Draws the pacman*/
                            pacMan.render(gc);
                            
                            /* Color for the score */
                            gc.setFill( Color.WHITE);
                            /* Set the Arial font */
                            Font scoreFont = Font.font( "Arial", FontWeight.BOLD, 32);
                            gc.setFont(scoreFont);
                            /* Draws the score*/
                            gc.fillText("SCORE: " + eng.getScore(), 28, 32);
                            /* Draws the lives*/
                            gc.fillText("LIVES: ", 28, 938);
                            for(int i = 0; i < eng.getLives(); i++) {
                                gc.drawImage(pacLife, 162 + 36 * i, 910);
                            }
                            
                            /* Draws the level*/
                            gc.fillText("LEVEL: ", 612, 938);
                            gc.drawImage(levelsImages[(eng.getLevel() - 1) % 4], 734, 910);
                            
                            if(foods.isEmpty()) {
                                levelUp(pacMan, ghosts, foods, eng);
                                
                                /* Timers for one pause*/
                                startMiliTime = System.currentTimeMillis();
                                currMiliTime = System.currentTimeMillis();
                                
                                
                                while(currMiliTime - startMiliTime < 3000) {
                                    currMiliTime = System.currentTimeMillis();
                                }
                                startMiliTime = System.currentTimeMillis();
                            }
                        }
                        else if(pacMan.mode.equals(Mode.DIED) && eng.getLives() > 0) {
                            /* Draws the board*/
                            board.render(gc);
                            
                            /* Starts the animation of the died pacman*/
                            pacDiedAnimation.start();
                            
                            /* Get the current time in miliseconds*/
                            currMiliTime = System.currentTimeMillis();
                            
                            /* Stop the animation after 4 seconds*/
                            if(currMiliTime - pacMan.diedTime > 4000) {
                                pacDiedAnimation.stop();
                                /* Reset the position of all dynamic elements*/
                                resetDynamicElems(pacMan, ghosts);
                                /* Turn back the pacman mode to normal*/
                                pacMan.mode = Mode.NORMAL;
                                startMiliTime = System.currentTimeMillis();
                                /* Change the game status to ready*/
                                gameStatus = Status.READY;
                            }
                        }
                        else if(eng.getLives() == 0) {
                            /* Change the game status to game over */
                            gameStatus = Status.GAMEOVER;
                            startMiliTime = System.currentTimeMillis();
                        }
                        break;
                    case GAMEOVER:
                        /* Restart the leve */
                        eng.setLevel(1);
                        /* Draws the board*/
                        board.render(gc);
                        
                        /* Starts the animation of the died pacman*/
                        pacDiedAnimation.start();
                        
                        /* Get the current time in miliseconds*/
                        currMiliTime = System.currentTimeMillis();
                            
                        /* Stop the animation after 4 seconds*/
                        if(currMiliTime - pacMan.diedTime > 4000) {
                            pacDiedAnimation.stop();
                            /* Draws the game over logo */
                            gc.drawImage(new Image("images/gameover.png", 142, 14, true, true), 321, 427);
                        }
                        
                        /* Get the current time in miliseconds*/
                        currMiliTime = System.currentTimeMillis();
                        
                        /* After ten seconds, go back to the menu*/
                        if(currMiliTime - startMiliTime > 10000) {
                            gameStatus = Status.MENU;
                        }
                        break;
                }   
                
            }
        };
        animationTimer.start();
        stage.show();
    }
    
    /**
     * This class is responsable for the animation when pacman dies.
     * 
     * @author Maxsuel F. de Almeida
     */
    public class PacManDiedAnimation extends AnimationTimer {

        PacMan pacMan;
        Image[] frames;
        GraphicsContext gc;

        /**
         * The class constructor.
         * 
         * @param pacMan One reference for the pacman.
         * @param gc Reference for the graphicsContext.
         */
        public PacManDiedAnimation(PacMan pacMan, GraphicsContext gc) {
            this.pacMan = pacMan;
            frames = new Image[30];
            System.arraycopy(pacMan.frames, 0, frames, 0, 30);
            this.gc = gc;
        }
        
        @Override
        public void handle(long now) {
            gc.drawImage(frames[16 + (int)(System.nanoTime() * 0.000000003) % 14], 378, 686);
        }
    }
    
    /**
     * Add the pacdots and the power pills in the game.
     * @return ArrayList comprising the foods(pacdots and power pills).
     */
    public ArrayList<Element> addFoods() {
        /* Images for pacdots and power pills*/
        Image[] foodImages = new Image[2];

        foodImages[0] = new Image("images/food.png", 7, 7, true, true);
        foodImages[1] = new Image("images/powerPill.png", 14, 14, true, true);

        /* Instantiate the foods' list*/
        ArrayList<Element> foodList = new ArrayList<>();

        /* Add pacdots and power pills in the board*/
        for(int i = 0; i < board.maze.length; i++) {
            for(int j = 0; j < board.maze[0].length; j++) {
                if(board.maze[i][j] == '.')
                    foodList.add(new PacDot(i, j, foodImages[0]));
                if(board.maze[i][j] == 'O')
                    foodList.add(new PowerPill(i, j, foodImages[1]));
            }
        }
        
        return foodList;
    }

    /**
     * Add the ghosts in the game.
     * @return ArrayList comprising the ghosts.
     */
    public ArrayList<Ghost> addGhosts() {
        /* Images for each kind of ghost*/
        Image[] blinkyImages = new Image[32];
        Image[] inkyImages = new Image[32];
        Image[] pinkyImages = new Image[32];
        Image[] clydeImages = new Image[32];

        /* Initiale the images*/
        for(int i = 0; i < 8; i++) {
            /* Initiate the blinky images*/
            blinkyImages[i] = new Image("images/ghost_0" + "_" + i + ".png", 36, 36, false, false);
            blinkyImages[i + 8] = new Image("images/ghost_vulnerable_0_" + i + ".png", 36, 36, false, false);
            blinkyImages[i + 16] = new Image("images/ghost_vulnerable_1_" + i + ".png", 36, 36, false, false);
            blinkyImages[i + 24] = new Image("images/ghost_died_" + i + ".png", 36, 36, false, false);
            
            /* Initiate the inky images*/
            inkyImages[i] = new Image("images/ghost_2" + "_" + i + ".png", 36, 36, false, false);
            inkyImages[i + 8] = new Image("images/ghost_vulnerable_0_" + i + ".png", 36, 36, false, false);
            inkyImages[i + 16] = new Image("images/ghost_vulnerable_1_" + i + ".png", 36, 36, false, false);
            inkyImages[i + 24] = new Image("images/ghost_died_" + i + ".png", 36, 36, false, false);
            
            /* Initiate the pinky images*/
            pinkyImages[i] = new Image("images/ghost_1" + "_" + i + ".png", 36, 36, false, false);
            pinkyImages[i + 8] = new Image("images/ghost_vulnerable_0_" + i + ".png", 36, 36, false, false);
            pinkyImages[i + 16] = new Image("images/ghost_vulnerable_1_" + i + ".png", 36, 36, false, false);
            pinkyImages[i + 24] = new Image("images/ghost_died_" + i + ".png", 36, 36, false, false);
            
            /* Initiate the clyde images*/
            clydeImages[i] = new Image("images/ghost_3" + "_" + i + ".png", 36, 36, false, false);
            clydeImages[i + 8] = new Image("images/ghost_vulnerable_0_" + i + ".png", 36, 36, false, false);
            clydeImages[i + 16] = new Image("images/ghost_vulnerable_1_" + i + ".png", 36, 36, false, false);
            clydeImages[i + 24] = new Image("images/ghost_died_" + i + ".png", 36, 36, false, false);
        }
        /* Instantiate the ghosts' list*/
        ArrayList<Ghost> ghostList = new ArrayList<>();

        /* Creat all the ghosts and put them in the ghosts' list*/
        ghostList.add(new Ghost(GhostType.BLINKY, 13, 12, blinkyImages));
        ghostList.add(new Ghost(GhostType.PINKY, 14, 15, pinkyImages));
        ghostList.add(new Ghost(GhostType.INKY, 14, 12, inkyImages));
        ghostList.add(new Ghost(GhostType.CLYDE, 13, 15, clydeImages));

        /* PINKY needs to be a little bit slower than BLINKY*/
        ghostList.get(1).setSpeed(Speed.GHOST_NORMAL_SPEED - 0.008);
        
        return ghostList;
    }

    /**
     * Add the pacman in the game.
     * @return Pacman.
     */
    public PacMan addPacMan() {
        /* Images for the pacman*/
        Image[] pacImages = new Image[30];

        /* Instanciate the normal images*/
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                pacImages[4 * i + j] = new Image("images/pacman_" + i + "_" + j + ".png", 36, 36, false, false);
        
        /* Instanciate the died images*/
        for(int i = 0; i < 14; i++)
            pacImages[i + 16] = new Image("images/pacman_died_" + i + ".png", 36, 36, false, false);

        /* Instanciate the pacman*/
        return new PacMan(pacImages);
    }
    
    public Fruit addFruit(int level) {
        /* Images of the fruits*/
        Image[] fruitImages = new Image[4];
        
        fruitImages[0] = new Image("images/cherry.png", 36, 36, true, true);
        fruitImages[1] = new Image("images/strawberry.png", 36, 36, true, true);
        fruitImages[2] = new Image("images/orange.png", 36, 36, true, true);
        fruitImages[3] = new Image("images/apple.png", 36, 36, true, true);
        
        /* Instatiate the fruit*/
        Fruit fruit;
        /* This index helps to decide each kind of fruit will placed in the
           board according to the level*/
        int index = (level - 1) % 4;
        
        /* Arithmetic progression is not that useless hahaha*/
        int value = 100 + (level - 1) * 200;
        
        switch(index) {
            case 1:
                fruit = new Fruit(17, 15, FruitType.CHERRY, value, fruitImages[index]);
                break;
                
            case 2:
                fruit = new Fruit(17, 15, FruitType.STRAWBERRY, value, fruitImages[index]);
                break; 
            
            case 3:
                fruit = new Fruit(17, 15, FruitType.ORANGE, value, fruitImages[index]);
                break;
                
            default:
                fruit = new Fruit(17, 15, FruitType.APPLE, value, fruitImages[index]);
                break;
        }
        
        return fruit;
    }
    /**
     * Put all the dynamic elements in their initial positions
     * @param pacMan
     * @param ghosts
     */
    public void resetDynamicElems(PacMan pacMan, ArrayList<Ghost> ghosts) {
        /* Auxiliar variables */
        int row, col;
        
        /* Initializing the aux variables with the pacman initial position*/
        col = 14;
        row = 23;
        
        /*Reset pacman position*/
        pacMan.setColumn(col);
        pacMan.setRow(row);
        pacMan.doubleCol = col;
        pacMan.doubleRow = row;
        pacMan.setX(pacMan.getProportionX() * col);
        pacMan.setY(pacMan.getProportionY() * row + LengthConstants.BACKGROUND_DOWNWARD_OFFSET);
        pacMan.direction = KeyCode.UNDEFINED;
        pacMan.nextDirection = KeyCode.UNDEFINED;
        
        /* Reset ghosts positions*/
        for(Ghost g : ghosts) {
            switch(g.type) {
                case BLINKY:
                    row = 13;
                    col = 12;
                    break;
                case CLYDE:
                    row = 13;
                    col = 15;
                    break;
                case INKY:
                    row = 14;
                    col = 12;
                    break;
                default: // PINKY
                    row = 14;
                    col = 15;
                    break;
            }  
            
            
            g.setColumn(col);
            g.setRow(row);
            g.doubleCol = col;
            g.doubleRow = row;
            g.setX(g.getProportionX() * col);
            g.setY(g.getProportionY() * row + LengthConstants.BACKGROUND_DOWNWARD_OFFSET);
            g.path.removeAll(g.path);
            g.graphPosition = new Point(col, row);
            g.mode = Mode.NORMAL;
            g.setFrameAnimation();
        }
    }

    /**
     * Reset all the elements in the game.
     * @param pacMan
     * @param ghosts
     * @param foods
     */
    public void resetAll(PacMan pacMan, ArrayList<Ghost> ghosts, ArrayList<Element> foods) {
        /* Reset the positions of pacman and ghosts*/
        resetDynamicElems(pacMan, ghosts);
        /* Revome all remaining food in the board*/
        foods.removeAll(foods);
        /* Add foods in all possible positions*/
        foods.addAll(addFoods());
    }
    
    /**
     * Update the level of the game.
     * @param pacMan
     * @param ghosts
     * @param foods
     * @param eng
     */
    public void levelUp(PacMan pacMan, ArrayList<Ghost> ghosts, ArrayList<Element> foods, Engine eng) {
        /* Update the level*/
        eng.setLevel(eng.getLevel() + 1);
        /* Set one speed factor according to the level*/
        double speedFactor = 1 + eng.getLevel() * 0.02;
        
        /* Back the status of the game to ready*/
        gameStatus = Status.READY;
        /* Reset all the elements*/
        resetAll(pacMan, ghosts, foods);
        /* Increases the ghosts speed */
        for(Ghost g: ghosts) {
            if(g.type.equals(GhostType.PINKY))
                g.setSpeed(speedFactor * Speed.GHOST_NORMAL_SPEED - 0.008);
            else
                g.setSpeed(speedFactor * Speed.GHOST_NORMAL_SPEED);
        }
        
        /* Reset the number of pacDots eaten */
        pacMan.eatenPacDots = 0;
    }
}
