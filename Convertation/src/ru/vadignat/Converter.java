package ru.vadignat;

public class Converter {

    private double xMin, xMax;
    private double xDen;
    private double yDen;
    private double yMin, yMax;
    private int width, height;

    public Converter(double xMin, double xMax,
                     double yMin, double yMax,
                     int width, int height){
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.width = width - 1;
        this.height = height - 1;

        xDen = this.width / (xMax - xMin);
        yDen = this.height / (yMax - yMin);
    }

    public Converter(Converter cvrt) {
        this.xMin = cvrt.xMin;
        this.xMax = cvrt.xMax;
        this.yMin = cvrt.yMin;
        this.yMax = cvrt.yMax;
        this.width = cvrt.width;
        this.height = cvrt.height;

        this.xDen = cvrt.xDen;
        this.yDen = cvrt.yDen;
    }

    public int xCrt2Scr(double x){
        int v = (int) (xDen * (x - xMin));
        if (v < -width) v = -width;
        if (v > 2 * width) v = 2 * width;
        return v;
    }

    public int yCrt2Scr(double y){
        int v = (int) (yDen * (yMax - y));
        if (v < -height) v = -height;
        if (v > 2 * height) v = 2 * height;
        return v;
    }

    public double xScr2Crt(int x){
        return xMin + x / xDen;
    }

    public double yScr2Crt(int y){
        return yMax - y / yDen;
    }
    public double getxDen(){return xDen;}
    public double getyDen(){return yDen;}

    public double getxMin() {
        return xMin;
    }
    public double getxMax(){
        return xMax;
    }

    public double getyMax() {
        return yMax;
    }
    public double getyMin(){
        return yMin;
    }

    public static double round(double a, int n){
        var tmp = Math.pow(10, n);
        return Math.round(a * tmp) / tmp;
    }

    public double xScrShape2Crt(int x){
        return x * xDen;
    }

}
