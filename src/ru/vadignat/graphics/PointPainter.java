package ru.vadignat.graphics;

import ru.vadignat.Converter;
import ru.vadignat.Painter;
import ru.vadignat.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PointPainter implements Painter {
    private Color color = Color.RED;
    private int radius = 3;
    public boolean show = true;
    private ArrayList<Point> pts = new ArrayList<>();
    private HashSet<Double> X = new HashSet<>();
    private Converter cvrt;
    public PointPainter() {}
    public PointPainter(Color color){this.color = color;}
    public PointPainter(ArrayList<Point> points){
        pts = points;
    }
    public  PointPainter(ArrayList<Point> points, Color color){
        this.color = color;
        pts = points;
    }
    public void addPoint(double x, double y){
        if(!X.contains(x)) {
            X.add(x);
            pts.add(new Point(x, y));
        }
    }
    public void addPoint(Point p){
        if(!X.contains(p.getX())) {
            X.add(p.getX());
            pts.add(p);
        }
    }
    public void removePoint(Point p){
        pts.remove(p);
        X.remove(p.getX());
    }
    public int getPointsCount(){
        return pts.size();
    }
    public void changeColor(Color color){
        this.color = color;
    }
    public void changeConverter(Converter converter){
        cvrt = converter;
    }
    public Converter getConverter(){return cvrt;}
    public int getRadius(){
        return radius;
    }
    public void changeRadius(int r){radius = r;}
    public ArrayList<Point> getPoints(){
        return pts;
    }
    public HashSet<Double> getX(){
        return X;
    }
    @Override
    public void paint(Graphics g, int width, int height) {
        if(cvrt == null)
        {
            cvrt = new Converter(-5.0,5.0,-5.0,5.0,width,height);
        }
        cvrt = new Converter(cvrt.getxMin(), cvrt.getxMax(), cvrt.getyMin(), cvrt.getyMax(), width, height);
        g.setColor(color);
        if(show) {
            for (Point pt : pts) {
                int xscr_i = cvrt.xCrt2Scr(pt.getX()) - radius;
                int yscr_i = cvrt.yCrt2Scr(pt.getY()) - radius;
                g.fillOval(xscr_i, yscr_i, 2 * radius, 2 * radius);
            }
        }
    }
}
