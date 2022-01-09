/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import engine.Engine;
import engine.Point;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

/**
 * This class represents the ghost.
 * 
 * @author Maxsuel F. Almeida
 */
public class Ghost extends Element {
   
   private PacMan pacMan;
   private Engine eng;
   private Board board;
   public Mode mode;
   public final GhostType type;
   public ArrayList<Point> path;
   public int savePathSize;
   public AnimatedImage animatedImage;
   public Point graphPosition;
   
   public Ghost(GhostType type, int row, int col,  Image[] frames, Board board, PacMan pacMan)
   {
       super(row, col, 0.11);
       this.board = board;
       this.eng = new Engine(row, col);
       this.pacMan = pacMan;
       mode = Mode.NORMAL;
       this.type = type;
       path = new ArrayList<>();
       savePathSize = 0;
       animatedImage = new AnimatedImage();
       animatedImage.frames = frames;
       graphPosition = new Point(row, col);
   }
   
   @Override
   public void update()
   {
       switch(this.type)
       {
            case BLINKY:
            case PINKY:
               path = eng.getPath(this, board, pacMan);
               break;
               
            default:
                if(path.size() < 2)
                    path.addAll(eng.getPath(this, board, pacMan));
                
       }
       
       
       if((this.getRow() == graphPosition.getX() && this.getColumn() == graphPosition.getY())
               || (graphPosition.getY() == -1 || graphPosition.getY() == 28))
       {
           graphPosition = path.get(0);
           path.remove(0);
       }

       if(this.getColumn() > graphPosition.getY())
           eng.nextDirection = KeyCode.LEFT;
       else if(this.getColumn() < graphPosition.getY())
           eng.nextDirection  = KeyCode.RIGHT;
       else if(this.getRow() > graphPosition.getX())
           eng.nextDirection  = KeyCode.UP;
       else if(this.getRow() < graphPosition.getX())
           eng.nextDirection  = KeyCode.DOWN;
       
       eng.shiftDirection(this, board);
       eng.move(this, board);
   }
   
   @Override
    public void render(GraphicsContext graphicsContext)
    {
        switch(eng.direction)
        {
            case LEFT:
                this.setImage(animatedImage.getFrame(2, 2));
                break;
            case RIGHT:
                this.setImage(animatedImage.getFrame(0, 2));
                break;
            case UP:
                this.setImage(animatedImage.getFrame(3, 2));
                break;
            case DOWN:
                this.setImage(animatedImage.getFrame(1, 2));
                break;
            default:
                this.setImage(animatedImage.getFrame(0, 2));
                break;
        }
        graphicsContext.drawImage(this.getImage(), this.getX(), this.getY());
    }
}
