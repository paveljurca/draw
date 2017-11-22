package drawsomething.canvas.shapes;

import drawsomething.canvas.Canvas;
import java.awt.Dimension;

/**
 * Class Rectangle() gets new rectangle with specified parameters
 * 
 * //This is supposed to be the simple demo of working with canvas in Java
 * //lesson #1
 * 
 * @author: Pavel Jurca
 * @version: 1.0
 */
public class Rectangle
{
    //data atributes
    private int _width;
    private int _height;
    private double _xPoint;
    private double _yPoint;
 
    //get reference to the current canvas
    Canvas pane = Canvas.getCanvas();

    /**
     * Set dimension and center a new rectangle defaultly
     * 
     * @param width     polygon width (integer)
     * @param height    polygon height (integer)
     */     
    public Rectangle(int width, int height) {
        _width = width;
        _height = height;
        _xPoint = ( pane.getSize().width / 2 ) - ( _width / 2 );
        _yPoint = ( pane.getSize().height / 2 ) - ( _height / 2 );
    }
    
    /**
     * Set coordinates and dimension of a new rectangle
     * 
     * @param xPoint    x-coordinate, left corner (integer)
     * @param yPoint    y-coordinate, left corner (integer)
     * @param width     rectangle width (integer)
     * @param height    rectangle height (integer)
     */     
    public Rectangle(double xPoint, double yPoint, int width, int height) {
        _xPoint = xPoint;
        _yPoint = yPoint;
        _width = width;
        _height = height;
    }
    
    /**
     * Set a new left corner of rectangle
     *
     * @param xPoint    x-coordinate, left corner (integer)
     * @param yPoint    y-coordinate, left corner (integer)
     */ 
    public void setLeftCorner(double xPoint, double yPoint) {
        _xPoint = xPoint;
        _yPoint = yPoint;
    }    
    
    /**
     * Set a new dimension of rectangle
     *
     * @param width     rectangle width (integer)
     * @param height    rectangle height (integer)
     */ 
    public void setSize(int width, int height) {
        _width = width;
        _height = height;
    }    
    
    /**
     * Get the dimension of rectangle
     */
    public Dimension getSize() {
        return new Dimension(_width, _height);
    }
    
    /**
     * Get a rectangle based on previously given parameters
     */
    public java.awt.geom.Rectangle2D.Double getRectangle() {
        java.awt.geom.Rectangle2D.Double rect = new java.awt.geom.Rectangle2D.Double(_xPoint, _yPoint, _width, _height);
        return rect;
    }

}

