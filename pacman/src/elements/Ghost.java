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
   public AnimatedImage animatedImage;
   public Image[] frames;
   public Point graphPosition;
  
   
   public Ghost(GhostType type, int row, int col,  Image[] frames, Board board, PacMan pacMan)
   {
       super(row, col, Speed.GHOST_NORMAL_SPEED);
       this.board = board;
       this.eng = new Engine(row, col);
       this.pacMan = pacMan;
       mode = Mode.NORMAL;
       this.type = type;
       path = new ArrayList<>();
       animatedImage = new AnimatedImage();
       this.frames = frames;
       this.setFrameAnimation();
       graphPosition = new Point(row, col);
   }
   
   @Override
   public void update()
   {
       switch(mode)
       {
           case NORMAL:
               if(type == GhostType.BLINKY || type == GhostType.PINKY)
                   path = eng.getPath(this, board, pacMan);
               else if(path.size() < 2)
                   path.addAll(eng.getRandomPath(this, board));
               break;
               
           case VULNERABLE:
               path.removeAll(path);
               if(path.size() < 2)
                   path.addAll(eng.getRandomPath(this, board));
               break;
               
           case DIED:
               //this.setSpeed(GhostSpeed.DIED_SPEED);
               path = eng.backToGhostHouse(this, board);
               break;
               
           default:
               break;
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
    
    public void setFrameAnimation()
    {
        switch(mode)
        {
            case NORMAL:
                System.arraycopy(frames, 0, animatedImage.frames, 0, 8);
                break;
                
            case VULNERABLE:
                System.arraycopy(frames, 12, animatedImage.frames, 0, 4);
                break;
                
            case DIED:
                System.arraycopy(frames, 8, animatedImage.frames, 0, 4);
                break;
        }
    }
}
