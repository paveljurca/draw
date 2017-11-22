package drawsomething.canvas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Class Canvas() is implemented as Singleton and ensures space for drawing
 * 
 * //This is supposed to be the simple demo of working with canvas in Java
 * //lesson #1
 * 
 * @author: Pavel Jurca
 * @version: 1.0
 */
public class Canvas {
   
    //create static object whose value is null
    private static Canvas myCanvas;
    
    //data atributes of an instance
    private JFrame frame;
    private CanvasPane pane;
    private Graphics2D graphic;
    private Color _background;
    private Color _foreground;
    private String _title;
    private int _width;
    private int _height;
    private Image canvasImage;
    private int padding;
    private javax.swing.Timer timer;
    private int seconds = 0;
    private int shapesC;
    private Boolean _standard;
    
    //listen to the window (JFrame) resizing
    private HierarchyBoundsListener windowResized = new HierarchyBoundsListener()
    {
        @Override public void ancestorMoved(HierarchyEvent event) {  
        }

        @Override public void ancestorResized(HierarchyEvent event) {
            frame.setTitle(_title + " | " + pane.getWidth() + " x " + pane.getHeight());
                //Dimension size = pane.getSize();
                //canvasImage = pane.createImage(size.width, size.height);
                //graphic = (Graphics2D)canvasImage.getGraphics();
                //graphic.scale(10, 10);
        }
    };
    
    /**
     * Create canvas
     * 
     * @param title         canvas title shown in a border-top of a form
     * @param width         canvas width
     * @param height        canvas height
     * @param background    canvas background color
     * @param foreground    canvas foreground color
     */
    private Canvas(String title, int width, int height, Color background, Color foreground) { //create private constructor
        frame = new JFrame();
        pane = new CanvasPane();

        pane.setPreferredSize(new Dimension(width, height));     
        frame.setContentPane(pane);
        frame.pack();
        
        _title = title;
        _width = width;
        _height = height;
        _background = background;
        _foreground = foreground;

        frame.setTitle(_title);
        frame.setBackground(_background);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); //center window on the screen
    }

    /**
     * If canvas has not existed yet create it and always return its reference
     */
    public static Canvas getCanvas() {

        //if variable myCanvas equals null then create new Canvas object
        if (myCanvas == null) {
            myCanvas = new Canvas("DrawSomething", 800, 400, new Color(222,222,222), new Color(0,165,235));
        }
        return myCanvas;
    }
    
    /**
     * show a standard canvas (true) or a canvas prepared for animation (false)
     * 
     * @param standard  determines canvas appearance (Boolean)
     */
    public void show(Boolean standard)
    {
        _standard = standard;
        if (graphic == null) {
            if (!_standard) {
                this.makePlayer();
                frame.setResizable(false); //deny resizing of a frame
            } else {
                canvasImage = pane.createImage(_width, _height);
                graphic = (Graphics2D)canvasImage.getGraphics();
                graphic.setBackground(_background);
                
                pane.addHierarchyBoundsListener(windowResized);
                frame.setMinimumSize(new Dimension(_width/2+_width/3, _height/2+_height/3));
            }
        }
        frame.setVisible(true);
    }
    
    /**
     * @return reference to a current graphic component
     */
    public java.awt.Graphics getGraphics() {
        if (graphic != null) {
            return graphic;   
        }
        return null;
    }
    
    public void repaintCan() {
        pane.repaint();
    }
    /**
     * set it all like the canvas looks like movie player
     */
    private void makePlayer() {
        padding = _width/15;
        Dimension sizeNew = new Dimension(
                pane.getSize().width + padding*2, 
                pane.getSize().height + (padding*2)+padding
                );
        pane.setPreferredSize(sizeNew);
        frame.pack();
        
        canvasImage = pane.createImage(
                pane.getSize().width,
                pane.getSize().height
                );
        graphic = (Graphics2D)canvasImage.getGraphics();
        
        graphic.translate(padding, padding);
        graphic.setBackground(_background);
        
        //clear the current canvas
        this.clear();
        
        //paint pause button with black color
        graphic.setColor(new Color(156,176,188));
        graphic.fillRect(_width/2-padding/2,_height+padding/2,padding,padding);
        graphic.clearRect(_width/2-padding/4,_height+padding/2,(padding/4)*2,padding);
        
        //start timer counting length of the animation
        timer = new javax.swing.Timer(1000, new MyTimer());
        timer.setInitialDelay(50);
        timer.start();
    }
    
    /**
     * change the pause button for the play button when the animation finished
     * and also stop the counting timer
     */
    public void playPic() { //this makes sense only when "animated canvas" is in use
        if (timer != null) {
            graphic.clearRect(0,_height,_width,padding*2);
            graphic.setColor(new Color(156,176,188));
            graphic.fillPolygon(
                    new int[] {_width/2-padding/2,_width/2+padding/2,_width/2-padding/2},
                    new int[] {_height+padding/2,_height+padding/2+padding/2,_height+padding/2+padding},
                    3
                    );
            
            pane.repaint();
            timer.stop(); //animation finished
            frame.setTitle(_title + " | " + frame.getTitle());
        }
    }

    /**
     * clear drawings from the current canvas
     * 
     */
    public void clear() {
        if (graphic != null) {
            if (!_standard) {
                //when animation is active, canvas looks differently
                graphic.setColor(new Color(235,235,235));
                graphic.fillRoundRect(0, 0, _width, _height, _width/25, _width/25);
            } else {
                graphic.clearRect(0, 0, _width, _height);
            }
            pane.repaint();
        }
    }

    /**
     * Get dimension of the current canvas
     */
    public Dimension getSize() {
        return new Dimension(_width, _height);
    }

    /**
     * Draw each shape on the current canvas
     * 
     * @param shapes    shapes ready to drawn (shapes[])
     * @param draw      specifies if the given shapes should be drawn or painted (Boolean)
     */
    public void drawShapes(Shape[] shapes, Boolean draw) {
        if (graphic != null) {
            graphic.setColor(_foreground);
            for (int i=0; i < shapes.length; i++) {
                if ( ( (i+1) % 2 ) == 0 || ( (shapesC+1) % 2 ) == 0) {
                    graphic.setColor(new Color(26,210,95)); //every second shape has this color
                }
                if (draw) { graphic.draw(shapes[i]); } else { graphic.fill(shapes[i]); }
                pane.repaint();
                shapesC++;
            }
        }
    }
    
    /************************************************************************
     * Nested class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class CanvasPane extends JPanel {
        
        @Override public void paint(Graphics g) {
            g.drawImage(canvasImage, 0, 0, null);
        }
    }
    
    /**
     * Class MyTimer which catches any triggered action, here actually
     * driven by javax.swing.Timer which invokes every second
     * 
     */
    private class MyTimer implements ActionListener {
        
        @Override public void actionPerformed(ActionEvent e) {
            seconds++;
            frame.setTitle(String.format("%02d min : %02d sec", seconds/60, seconds%60));
        }
    }
    
}
   
