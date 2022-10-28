package ru.vadignat.math;

import ru.vadignat.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lagrange extends Polynomial{
    Map<Double,Double> d;
    public Lagrange(Map<Double,Double> d){
        this.d = new HashMap<>(d);
        var L = createLagrange();
        coeff = L.coeff.clone();
    }
    public Lagrange(ArrayList<Point> points){
        d = new HashMap<>();
        for (Point p: points) {
            d.put(p.getX(),p.getY());
        }
        var L = createLagrange();
        coeff = L.coeff.clone();
    }

    private Polynomial createLagrange() {
        Polynomial L = new Polynomial();
        for(double x : d.keySet()){
            L = L.plus(createFundamental(x).times(d.get(x)));
        }
        return L;
    }

    private Polynomial createFundamental(double x_k){
        Polynomial l = new Polynomial(new double[]{1});
        for(double x_j : d.keySet()){
            if(Math.abs(x_k - x_j) > EPS) {
                try {
                    l = l.times(new Polynomial(new double[]{-x_j,1}).div(x_k - x_j));
                }
                catch (Exception e){}
            }
        }
        return l;
    }
}
