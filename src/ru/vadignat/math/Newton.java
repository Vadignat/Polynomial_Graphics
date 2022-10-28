package ru.vadignat.math;

import ru.vadignat.Point;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Newton extends Polynomial{
    Map<Double,Double> d = new LinkedHashMap<>();
    Polynomial w_j;
    public Newton(Newton newton){
        this.d = newton.d;
        this.w_j = newton.w_j;
    }
    public Newton (Map<Double,Double> d){
        w_j = new Polynomial(new double[]{1});
        for(double x: d.keySet()){
            append(x,d.get(x));
        }
    }
    public Newton (ArrayList<Point> points){
        w_j = new Polynomial(new double[]{1});
        for (Point p: points) {
            append(p.getX(),p.getY());
        }
    }
    public void append(double x, double y){
        if(d.containsKey(x)) return;
        d.put(x,y);
        double s = 0;
        if(d.size() > 1) {
            for (double x_i : d.keySet()) {
                double temp = 1;
                for (double x_k : d.keySet()) {
                    if (Math.abs(x_i - x_k) > EPS) {
                        temp *= (x_i - x_k);
                    }
                }
                s += d.get(x_i) / temp;
            }
        } else
            s = y;
        coeff = plus(w_j.times(s)).coeff;
        w_j = w_j.times(new Polynomial(new double[]{-x,1}));
    }

}
