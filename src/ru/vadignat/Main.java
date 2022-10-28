package ru.vadignat;

import ru.vadignat.graphics.MainWindow;
import ru.vadignat.math.Lagrange;
import ru.vadignat.math.Newton;
import ru.vadignat.math.Polynomial;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Polynomial p2 = new Polynomial(new double[]{9,-1,1});
        Polynomial p1 = new Polynomial(new double[]{1,2,3,-5,-1});
        Polynomial p3 = p1.plus(p2);

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);

        Polynomial p4 = new Polynomial(new double[]{5,3,1});
        Polynomial p5 = new Polynomial(new double[]{5,0,-10,-1,2});

        /*System.out.println(p4.times(0));
        System.out.println(p4.times(new Polynomial()));
        try {
            System.out.println(p4.div(0));
        }
        catch (Exception e){
            System.out.println(e);
        }
        */

        var d = new HashMap<Double,Double>();
        for(int i = 0; i < 2;i++)
        {
            d.put(-1000 + 2000 * Math.random(),-1000 + 2000 * Math.random());
        }


        long start = System.currentTimeMillis();
        Newton newton = new Newton(d);
        //System.out.println("Newton: " + newton);
        System.out.println("Newton time: ");
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        Lagrange lagrange = new Lagrange(d);
        //System.out.println("Lagrange:" + lagrange);
        //System.out.println("Der: " + lagrange.der());
        System.out.println("Lagrange time: ");
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        newton.append(-1.,1.);
        //System.out.println("Newton + 1 point : " + newton);
        System.out.println("Newton + 1 time: ");
        System.out.println(System.currentTimeMillis() - start);

        d.put(-1.,1.);
        start = System.currentTimeMillis();
        Lagrange lagrangenew = new Lagrange(d);
        //System.out.println("Lagrange:" + lagrangenew);
        System.out.println("Lagrange + 1 time: ");
        System.out.println(System.currentTimeMillis() - start);


        var wnd = new MainWindow();
        wnd.setVisible(true);
    }
}
