package elements;


import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This abstract classe provides methods for the renderization of all elements
 * in the game.
 * 
 * 
 * @author Maxsuel F. de Almeida
 */
public abstract class Element {
    
    private Image image;
    private double x;
    private double y;
    private double velocity;
    private int row;
    private int col;
    private final int proportionX = 28;
    private final int proportionY = 28;
    private Rectangle2D  collider;
    
    
    /**
     * Default constructor.
     * 
     * @param row Row that comprises the element in the 2D array maze.
     * @param col Column that comprises the element in the 2D array maze.
     * @param velocity
     * @param image Element's image.
     */
    public Element(int row, int col, double velocity, Image image)
    {
        this.row = row; 
        this.col = col;
        x = this.proportionX * col;
        y = this.proportionY * row;
        this.velocity = velocity;
        this.image = image;
        collider = new Rectangle2D(proportionX * col, proportionY * row, proportionX, proportionY);
    }
    
    public Element(int row, int col, double velocity)
    {
        this.row = row; 
        this.col = col;
        x = this.proportionX * col;
        y = this.proportionY * row;
        this.velocity = velocity;
        collider = new Rectangle2D(proportionX * col, proportionY * row, proportionX, proportionY);
    }
    
    /**
     * Get the image of  the element.
     * @return image
     */
    public Image getImage()
    {
        return image;
    }
    
    /**
     * Set the image for the element.
     * 
     * @param image
     */
    public void setImage(Image image)
    {
        this.image = image;
    }
    
    /**
     * Get the horizontal coordinate of  the element.
     * @return x
     */
    public double getX()
    {
        return x;
    }
    
    /**
     * Set the horizontal coordinate for the element.
     * 
     * @param x
     */
    public void setX(double x)
    {
        this.x = x;
    }
    
    /**
     * Get the vertical coordinate of  the element.
     * @return y
     */
    public double getY()
    {
        return y;
    }
    
    /**
     * Set the vertical coordinate for the element.
     * 
     * @param y
     */
    public void setY(double y)
    {
        this.y = y;
    }
    
    /**
     * Get the speed of  the element.
     * @return velocity
     */
    public double getVelocity()
    {
        return velocity;
    }
    
    /**
     * Set the speed for the element.
     * 
     * @param velocity
     */
    public void setVelocity(double velocity)
    {
        this.velocity = velocity;
    }
    
    /**
     * Get the row of  the element.
     * @return row
     */
    public int getRow()
    {
        return row;
    }
    
    /**
     * Set the row for the element.
     * 
     * @param row
     */
    public void setRow(int row)
    {
        this.row = row;
    }
    
    /**
     * Get the column of  the element.
     * @return col
     */
    public int getColumn()
    {
        return col;
    }
    
    /**
     * Set the column for the element.
     * 
     * @param col
     */
    public void setColumn(int col)
    {
        this.col = col;
    }
    
    /**
     * Get the relation between the backgrond's width and the data model's
     * numbers of columns.
     * 
     * @return  
     */
    public int getProportionX()
    {
        return proportionX;
    }
    
    /**
     *Get the relation between the backgrond's height and the data model's
     * numbers of rows.
     * 
     * @return
     */
    public int getProportionY()
    {
        return proportionY;
    }
    
    public void updateCollider(double x, double y)
    {
        collider = new Rectangle2D(x, y, 28, 28);
    }
    
    public Rectangle2D getCollider()
    {
        return collider;
    }
    
    /**
     * Draws the element in the canvas.
     * 
     * @param graphicsContext
     */
    public void render(GraphicsContext graphicsContext)
    {
        graphicsContext.drawImage(image, x, y);
    }
    
    /**
     * Abstract method to update de position of the elements.
     * 
     */
    public abstract void update();
    
    
    /**
     * Check collision with another element of the game.
     * 
     * @param rect Element to check collision with
     * @return boolean
     */
    public boolean isCollision(Rectangle2D rect)
    {
        return rect.intersects(this.collider);
    }
    
    public boolean isCollision(Element elem)
    {
        return elem.getCollider().intersects(this.collider);
    }
}
