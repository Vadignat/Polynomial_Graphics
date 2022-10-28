package ru.vadignat.graphics;

import ru.vadignat.Converter;
import ru.vadignat.Painter;

import java.awt.*;

public abstract class FunctionPainter implements Painter {
    private Converter cvrt;
    private Color color = Color.BLACK;
    public boolean show = true;

    public FunctionPainter(){}
    public FunctionPainter(Color color){this.color = color;}

    public void changeColor(Color color){
        this.color = color;
    }
    public void changeConventer(Converter converter){
        cvrt = converter;
    }

    @Override
    public void paint(Graphics g, int width, int height) {
        if(cvrt == null) {
        cvrt = new Converter(-5.0,5.0,-5.0,5.0,width,height);
    }
        cvrt = new Converter(cvrt.getxMin(), cvrt.getxMax(), cvrt.getyMin(), cvrt.getyMax(), width, height);
        g.setColor(color);
        if(show) {
            PointPainter pp = new PointPainter(color);
            pp.changeConverter(cvrt);
            pp.changeRadius(1);
            pp.addPoint(cvrt.getxMin(), invoke(cvrt.getxMin()));
            double delta = 0.0001;
            for (double x = cvrt.getxMin() + delta; x < cvrt.getxMax(); x += delta) {
                pp.addPoint(x, invoke(x));
                g.drawLine(cvrt.xCrt2Scr(x - delta), cvrt.yCrt2Scr(invoke(x - delta)), cvrt.xCrt2Scr(x), cvrt.yCrt2Scr(invoke(x)));
            }
            pp.paint(g, width, height);
        }
    }

    public abstract double invoke(double x);
}
