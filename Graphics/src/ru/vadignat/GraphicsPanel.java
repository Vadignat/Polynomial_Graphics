package ru.vadignat;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class GraphicsPanel extends JPanel{
    private ArrayList<Painter> p = new ArrayList<>();

    public GraphicsPanel(Painter p){
        this.p.add(p);
    }
    public GraphicsPanel(Collection<Painter> pts){
        p.addAll(pts);
    }

    public void addPainter(Painter p){
        this.p.add(p);
        repaint();
    }

    public void removePainter(Painter p){
        this.p.remove(p);
    }

    @Override
    public void paint (Graphics g){
        super.paint(g);
        for (Painter p : this.p) {
            p.paint(g,getWidth(),getHeight());
        }
    }
}
