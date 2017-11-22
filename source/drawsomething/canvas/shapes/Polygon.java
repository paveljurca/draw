package drawsomething.canvas.shapes;

import drawsomething.canvas.Canvas;
import java.awt.Dimension;

/**
 * Class Polygon gets new polygon with specified parameters
 * 
 * //This is supposed to be the simple demo of working with canvas in Java
 * //lesson #1
 * 
 * @author: Pavel Jurca
 * @version: 1.0
 */
public class Polygon
{
    //data atributes
    private int _width;
    private int _height;
    private double _xPoint;
    private double _yPoint;
    private int[] _xpointS;
    private int[] _ypointS;
    
    //get reference to the current canvas
    Canvas pane = Canvas.getCanvas();

    /**
     * Set dimension and default centered coordinates of a new Polygon
     * 
     * @param width     polygon width (integer)
     * @param height    polygon height (integer)
     */     
    public Polygon(int width, int height) {
        _width = width;
        _height = height;
        _xPoint = ( pane.getSize().width / 2 ) - ( _width / 2 );
        _yPoint = ( pane.getSize().height / 2 ) - ( _height / 2 );
    }
    
    /**
     * Set dimension and center a new polygon defaultly
     * 
     * @param xPoint    x-coordinate, left corner (integer)
     * @param yPoint    y-coordinate, left corner (integer)
     * @param width     polygon width (integer)
     * @param height    polygon height (integer)
     */     
    public Polygon(double xPoint, double yPoint, int width, int height) {
        _xPoint = xPoint;
        _yPoint = yPoint;
        _width = width;
        _height = height;
    }

    /**
     * Set all x and y coordinates of polygon
     * 
     * @param xpoints    x-coordinate (integer[])
     * @param ypoints    y-coordinate (integer[])
     */ 
    public void setPoints(int[] xpoints, int[] ypoints) {
        _xpointS = new int[xpoints.length];
        _ypointS = new int[ypoints.length];
        
        //create copies 1:1 of given arrays
        System.arraycopy(xpoints, 0, _xpointS, 0, xpoints.length);
        System.arraycopy(ypoints, 0, _ypointS, 0, ypoints.length);
    }

    /**
     * Set a new left corner of polygon
     * 
     * @param xPoint    x-coordinate, left corner (integer)
     * @param yPoint    y-coordinate, left corner (integer)
     */ 
    public void setLeftCorner(double xPoint, double yPoint) {
        _xPoint = xPoint;
        _yPoint = yPoint;
    }
    
    /**
     * Set a new dimension of polygon
     *
     * @param width     rectangle width (integer)
     * @param height    rectangle height (integer)
     */ 
    public void setSize(int width, int height) {
        _width = width;
        _height = height;
    }    
    
    /**
     * Get the dimension of polygon
     */
    public Dimension getSize() {
        return new Dimension(_width, _height);
    }
    
    /**
     * Get a polygon based on previously given parameters
     */
    public java.awt.Polygon getPolygon() {
        //there must be the same count of x and y coordinates and also you need 3 points at least
        if (_xpointS.length == _ypointS.length && _xpointS.length >= 3) {
            java.awt.Polygon poly = new java.awt.Polygon(_xpointS, _ypointS, _xpointS.length);
            poly.translate((int)_xPoint, (int)_yPoint); //adjust left corner of a polygon
            return poly;
        }
        return null;
    }

}

