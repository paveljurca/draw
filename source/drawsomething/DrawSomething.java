package drawsomething;

import drawsomething.canvas.Canvas;
import drawsomething.canvas.shapes.*;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Font;
import java.awt.FontMetrics;

/**
 * Class DrawSomething()'s main method is a dashboard for managing canvas we are
 * drawing on JPanel!
 *
 * //This is supposed to be the simple demo of working with canvas in Java
 * //lesson #1
 *
 * @author: Pavel Jurca
 * @version: 1.0
 */
public class DrawSomething {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DrawSomething d = new DrawSomething();

        //demos
        if (!true) {
            d.drawingDemo(true);
        } else {
            d.animationDemo();
        }
    }

    /**
     * @param createStar set true for painting a start; set false for drawing
     * two tetragons
     */
    private void drawingDemo(Boolean createStar) {

        //initialize canvas
        Canvas can = Canvas.getCanvas();
        can.show(true); //set true for standard canvas

        int w = can.getSize().width;
        int h = can.getSize().height;

        if (createStar) {
            //initialize shapes
            Polygon star = new Polygon(w / 2, h / 2);

            //star No. 1
            w = star.getSize().width;
            h = star.getSize().height;
            star.setPoints(
                    new int[]{0, w / 2, w, 0, w},
                    new int[]{h, h / 3 + h / 18, h, h / 2, h / 2});

            //paint given shapes on the current canvas
            can.drawShapes(new Shape[]{
                        star.getPolygon()
                    }, false); //set false for filling shapes with color, no drawing
        } else {
            //initialize shapes
            Polygon tetra = new Polygon(w / 2, h / 2);
            Polygon tetra2 = new Polygon(w / 2 - w / 5, h / 2 + h / 5);

            //tetragon No. 1
            w = tetra.getSize().width;
            h = tetra.getSize().height;
            tetra.setPoints(
                    new int[]{0, w, w, 0},
                    new int[]{0, h, 0, h});
            //tetragon No. 2 rotated by 90°
            w = tetra2.getSize().width;
            h = tetra2.getSize().height;
            tetra2.setPoints(
                    new int[]{0, w, 0, w},
                    new int[]{0, 0, h, h});

            //draw given shapes on the current canvas
            can.drawShapes(new Shape[]{
                        tetra.getPolygon(),
                        tetra2.getPolygon()
                    }, true); //set true for drawing shapes, no painting
        }
    }

    /**
     * let's see what it do!
     */
    private void animationDemo() {

        //initialize canvas
        Canvas can = Canvas.getCanvas();
        can.show(false); //set false for "movie canvas"

        int w = can.getSize().width;
        int h = can.getSize().height;
        int cx = can.getSize().width / 2;
        int cy = can.getSize().height / 2;
        //initialize shapes
        Polygon star = new Polygon(w / 25, h / 25);

        //star
        w = star.getSize().width;
        h = star.getSize().height;
        star.setPoints(
                new int[]{w / 4, w / 2, w - w / 4, w / 8, w - w / 8},
                new int[]{h, 0, h, h / 2 - h / 7, h / 2 - h / 7});
        int radius = cy - w;
        double angle;
        int speed = 4; //speed of a rotation
        int how_many_stars = 18;
        String[] faculties = {"FFÚ", "FMV", "FPH", "FIS", " NF", " FM"};
        Font txtFont = new Font(Font.SERIF, Font.ITALIC, cy / 4);
        FontMetrics metricsTxt = can.getGraphics().getFontMetrics(txtFont);
        int j = how_many_stars / faculties.length; //geeky
        int _j = 0;

        try {
            synchronized (this) {
                int upper = 360 * 1; //N times clockwise, N times counterclockwise
                for (int i = 0; i < upper; i += speed) {
                    can.clear();
                    for (int k = i; k < 360 + i; k += (360 / how_many_stars)) {
                        angle = Math.toRadians(k);
                        star.setLeftCorner(
                                (cx + Math.cos(angle) * radius) - w / 2,
                                (cy - Math.sin(angle) * radius) - h / 2);
                        can.drawShapes(new Shape[]{star.getPolygon()}, false);
                        if (j % 3 == 0) {
                            if (_j < faculties.length) {
                                Color _fg = can.getGraphics().getColor();
                                can.getGraphics().setColor(new Color(235, 235, 235));
                                can.getGraphics().fillOval(
                                        cx - radius + w,
                                        cy - radius + h,
                                        (radius - w) * 2,
                                        (radius - h) * 2);
                                can.getGraphics().setColor(_fg);
                                can.getGraphics().setFont(txtFont);
                                can.getGraphics().drawString(
                                        faculties[_j],
                                        cx - metricsTxt.stringWidth(faculties[_j]) / 2,
                                        cy + metricsTxt.getHeight() / 3);
                                can.repaintCan();
                                _j++; //move on to next index in faculties[] arr
                                if (_j >= faculties.length) {
                                    speed *= 2; //now rotate faster
                                }
                            } else {
                                can.getGraphics().setColor(new Color(0, 165, 235));
                                can.getGraphics().setFont(new Font(Font.SERIF, Font.BOLD, cy / 4));
                                can.getGraphics().drawString(
                                        "VŠE",
                                        cx - metricsTxt.stringWidth("VŠE") / 2,
                                        cy + metricsTxt.getHeight() / 3);
                            }
                        }
                        j++; //num of steps
                        if (i == 0) {
                            this.wait(500 / Math.abs(speed));
                        }
                    }
                    if (i != 0) {
                        this.wait(750 / Math.abs(speed));
                    }
                    //counterclockwise
                    if (i + speed > upper - 1) {
                        speed *= -1;
                        this.wait(Math.abs(200 * speed) / 5);
                    } else if (i <= speed * -1) {
                        break;
                    }
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }

        can.playPic(); //when your animation is at the end
        /*    
         can.drawShapes(new Shape[] {
         star.getPolygon()
         }, false );
         */
    }
}