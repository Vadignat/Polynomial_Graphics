package ru.vadignat.graphics;

import ru.vadignat.Converter;
import ru.vadignat.Painter;

import java.awt.*;

public class CartesianPainter implements Painter {
    private double px = 1, py = 1;
    private Converter cvrt;
    public void changeSize(Converter converter){
        cvrt = converter;
        if(cvrt.getxMax() <= 0)
            px = 0;
        else {
            if(cvrt.getxMin() >= 0)
                px = -1;
            else px = Math.abs(cvrt.getxMax()/cvrt.getxMin());
        }

        if(cvrt.getyMax() <= 0)
            py = 0;
        else {
            if(cvrt.getyMin() >= 0)
                py = -1;
            else py = Math.abs(cvrt.getyMax()/cvrt.getyMin());
        }
    }
    @Override
    public void paint(Graphics g, int width, int height) {
        if(cvrt == null)
        {
            cvrt = new Converter(-5.0,5.0,-5.0,5.0,width,height);
        }
        cvrt = new Converter(cvrt.getxMin(), cvrt.getxMax(), cvrt.getyMin(), cvrt.getyMax(), width, height);
        g.setColor(Color.BLACK);
        int scrwidth = (px > 0) ? (int) Math.round (width /(px+1) - 1): width - 1;
        int scrheight = (py > 0)? height - 1 - (int) (height/(py+1)): 0;
        if(px == -1)
            scrwidth = 0;
        if(py == -1)
            scrheight = height - 1;
        int sclht = 2;




        if(px <= 0) {
            for (double y_j = Converter.round(cvrt.getyMin(), 1); y_j < (cvrt.getyMin() + cvrt.getyMax()) / 2; y_j += 0.1) {
                sclht = 2;
                double y_j_round = Converter.round(y_j,1);
                int y = (int) Math.round(10 * y_j_round);
                int yscr = cvrt.yCrt2Scr(y_j_round);
                if (y % 10 == 0) {
                    sclht = 8;
                    if (y != 0)
                    {
                        String str = "" + y / 10;
                        int deltay = (int) (Math.round(g.getFontMetrics().getStringBounds(str, g).getHeight()/4));
                        g.drawString(str,sclht + 4, yscr + deltay);
                    }
                }
                if (Math.abs(y % 10) == 5)
                    sclht = 5;
                g.drawLine(0, yscr, sclht, yscr);
            }
            for (double y_j = Converter.round((cvrt.getyMin() + cvrt.getyMax()) / 2, 1); y_j < cvrt.getyMax(); y_j += 0.1){
                sclht = 2;
                double y_j_round = Converter.round(y_j, 1);
                int y = (int) Math.round(10 * y_j);
                int yscr = cvrt.yCrt2Scr(y_j_round);
                if (y % 10 == 0) {
                    sclht = 8;
                    if (y != 0)
                    {
                        String str = "" + y / 10;
                        int deltay = (int) (Math.round(g.getFontMetrics().getStringBounds(str, g).getHeight()/4));
                        int deltax = (int) (Math.round(g.getFontMetrics().getStringBounds(str, g).getWidth()));
                        g.drawString(str, width - 2 - sclht - deltax, yscr + deltay);
                    }
                }
                if (Math.abs(y % 10) == 5)
                    sclht = 5;
                g.drawLine(width - 1 - sclht, yscr, width - 1, yscr);
            }
        }
        else{
            g.drawLine(scrwidth,0,scrwidth,height - 1);
            for (double y_i = Converter.round(cvrt.getyMin(), 1); y_i < cvrt.getyMax(); y_i += 0.1) {
                sclht = 2;
                double y_i_round = Converter.round(y_i,1);
                int y = (int) Math.round(10 * y_i_round);
                int yscr = cvrt.yCrt2Scr(y_i_round);
                if (y % 10 == 0) {
                    sclht = 8;
                    if (y != 0)
                    {
                        String str = "" + y / 10;
                        int deltay = (int) (Math.round(g.getFontMetrics().getStringBounds(str, g).getHeight()/4));
                        g.drawString(str, scrwidth + sclht + 4, yscr + deltay);
                    }
                }
                if (Math.abs(y % 10) == 5)
                    sclht = 5;
                if(y != 0)
                    g.drawLine(scrwidth - sclht, yscr, scrwidth + sclht, yscr);
            }
        }




        if(py <= 0) {
        for(double x_j = Converter.round(cvrt.getxMin(),1); x_j < (cvrt.getxMin() + cvrt.getxMax())/2; x_j+= 0.1)
        {
            sclht = 2;
            double x_i_round = Converter.round(x_j, 1);
            int x = (int) Math.round(10 * x_i_round);
            int xscr = cvrt.xCrt2Scr(x_i_round);
            if(x % 10 == 0 )
            {
                sclht = 8;
                if(x != 0)
                {
                    String str = "" + x / 10;
                    int deltax = (int) (Math.round(g.getFontMetrics().getStringBounds(str, g).getWidth()/2));
                    int deltay = (int) (Math.round(g.getFontMetrics().getStringBounds(str, g).getHeight()));
                    g.drawString("" + x / 10, xscr - deltax, height - 3 - sclht);
                }
            }
            if (Math.abs(x % 10) == 5)
                sclht = 5;
            g.drawLine(xscr,height - 1 - sclht, xscr, height - 1);
        }

            for (double x_j = Converter.round((cvrt.getxMin() + cvrt.getxMax()) / 2, 1); x_j < cvrt.getxMax(); x_j += 0.1) {
                sclht = 2;
                double x_i_round = Converter.round(x_j, 1);
                int x = (int) Math.round(10 * x_i_round);
                int xscr = cvrt.xCrt2Scr(x_i_round);
                if (x % 10 == 0) {
                    sclht = 8;
                    if (x != 0)
                    {
                        String str = "" + x / 10;
                        int deltax = (int) (Math.round(g.getFontMetrics().getStringBounds(str, g).getWidth()/2));
                        int deltay = (int) (Math.round(g.getFontMetrics().getStringBounds(str, g).getHeight()));
                        g.drawString("" + x / 10, xscr - deltax, deltay + sclht + 1);
                    }
                }
                if (Math.abs(x % 10) == 5)
                    sclht = 5;
                g.drawLine(xscr, 0, xscr, sclht);
            }
        }
        else {
            g.drawLine(0, scrheight, width - 1, scrheight);
            for(double x_i = Converter.round(cvrt.getxMin(),1); x_i < cvrt.getxMax(); x_i+= 0.1)
            {
                sclht = 2;
                double x_i_round = Converter.round(x_i, 1);
                int x = (int) Math.round(10 * x_i_round);
                int xscr = cvrt.xCrt2Scr(x_i_round);
                if(x % 10 == 0)
                {
                    sclht = 8;
                    if(x != 0)
                    {
                        String str = "" + x / 10;
                        int deltax = (int) (Math.round(g.getFontMetrics().getStringBounds(str, g).getWidth()/2));
                        g.drawString(str, xscr - deltax, scrheight - sclht - 1);
                    }

                    else
                        g.drawString("" + 0, xscr + 4, scrheight - 3);
                }
                if (Math.abs(x % 10) == 5)
                    sclht = 5;
                if(x != 0)
                    g.drawLine(xscr,scrheight - sclht,xscr,scrheight + sclht);
            }
        }
    }



}
