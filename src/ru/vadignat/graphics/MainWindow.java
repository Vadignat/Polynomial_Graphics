package ru.vadignat.graphics;

import ru.vadignat.Converter;
import ru.vadignat.GraphicsPanel;
import ru.vadignat.Painter;
import ru.vadignat.Point;
import ru.vadignat.math.Newton;
import ru.vadignat.math.Polynomial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class MainWindow extends JFrame {
    private Dimension minSize = new Dimension(600,450);
    private static final int  std = 8;

    private JPanel mainPanel;
    private JPanel controlPanel;
    private JPanel clr1;
    private JPanel clr2;
    private JPanel clr3;

    private JLabel xmin;
    private JLabel xmax;
    private JLabel ymin;
    private JLabel ymax;
    private JLabel clrnm1;
    private JLabel clrnm2;
    private JLabel clrnm3;

    private JSpinner sp1;
    private JSpinner sp2;
    private JSpinner sp3;
    private JSpinner sp4;

    private SpinnerNumberModel nmsp1;
    private SpinnerNumberModel nmsp2;
    private SpinnerNumberModel nmsp3;
    private SpinnerNumberModel nmsp4;

    private Checkbox cbx1;
    private Checkbox cbx2;
    private Checkbox cbx3;
    private GroupLayout gl;
    private GroupLayout cpgl;
    public MainWindow(){
        setSize(minSize);
        setMinimumSize(minSize);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        gl = new GroupLayout(this.getContentPane());
        setLayout(gl);

        SpinnerNumberModel nmsp1 = new SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1);
        SpinnerNumberModel nmsp2 = new SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1);
        SpinnerNumberModel nmsp3 = new SpinnerNumberModel(5.0, -4.9, 100.0, 0.1);
        SpinnerNumberModel nmsp4 = new SpinnerNumberModel(5.0, -4.9, 100.0, 0.1);

        JSpinner sp1 = new JSpinner(nmsp1);
        JSpinner sp2 = new JSpinner(nmsp2);
        JSpinner sp3 = new JSpinner(nmsp3);
        JSpinner sp4 = new JSpinner(nmsp4);

        JPanel clr1 = new JPanel();
        JPanel clr2 = new JPanel();
        JPanel clr3 = new JPanel();

        Checkbox cbx1 = new Checkbox("Точки");
        Checkbox cbx2 = new Checkbox("Полином");
        Checkbox cbx3 = new Checkbox("Производная");
        cbx1.setState(true);
        cbx2.setState(true);
        cbx3.setState(false);

        clr1.setBackground(Color.RED);
        clr2.setBackground(Color.CYAN);
        clr3.setBackground(Color.GREEN);

        CartesianPainter crt = new CartesianPainter();
        final ArrayList<Point>[] points = new ArrayList[]{new ArrayList<>()};

        PointPainter pp = new PointPainter(clr1.getBackground());
        ArrayList<Painter> painters = new ArrayList<Painter>();
        painters.add(crt);
        final Newton[] n1 = {new Newton(points[0])};
        final Polynomial[] p1 = {new Polynomial(n1[0].der())};
        FunctionPainter fp2 = new FunctionPainter(clr2.getBackground()) {
            @Override
            public double invoke(double x) {
                return n1[0].invoke(x);
            }
        };
        FunctionPainter fp3 = new FunctionPainter(clr3.getBackground()) {
            @Override
            public double invoke(double x) {
                return p1[0].invoke(x);
            }
        };

        fp2.show = false;
        fp3.show = false;

        painters.add(fp2);
        painters.add(fp3);
        painters.add(pp);

        GraphicsPanel mainPanel = new GraphicsPanel(painters);
        JPanel controlPanel = new JPanel();

        pp.changeConverter(new Converter((Double)nmsp1.getValue(),(Double)nmsp3.getValue(),
                (Double)nmsp2.getValue(), (Double)nmsp4.getValue(),
                mainPanel.getWidth(), mainPanel.getHeight()));
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Converter converter = pp.getConverter();
                double x = converter.xScr2Crt(e.getX());
                double y = converter.yScr2Crt(e.getY());
                boolean containsX = false;
                for (double x_i : pp.getX()) {
                    if(Math.abs(converter.xCrt2Scr(x_i) - e.getX()) <= pp.getRadius()) {
                        containsX = true;
                        break;
                    }
                }
                if(!containsX && e.getButton() == MouseEvent.BUTTON1) {
                    if (pp.getPointsCount() == 0) {
                        fp2.show = cbx2.getState();
                        fp3.show = cbx3.getState();
                    }
                    pp.addPoint(x, y);
                    points[0] = pp.getPoints();
                    n1[0].append(x, y);
                }
                if(containsX && e.getButton() == MouseEvent.BUTTON3){
                    for (Point p : pp.getPoints()) {
                        double x_i = p.getX();
                        double y_i = p.getY();
                        if(Math.abs(converter.xCrt2Scr(x_i) - e.getX()) <= pp.getRadius() &&
                                Math.abs(converter.yCrt2Scr(y_i) - e.getY()) <= pp.getRadius()) {
                            points[0].remove(p);
                            pp.removePoint(p);
                            if (pp.getPointsCount() == 0)
                            {
                                fp2.show = false;
                                fp3.show = false;
                            }
                            n1[0] = new Newton(points[0]);
                            break;
                        }
                    }
                }
                p1[0] = new Polynomial(n1[0].der());
                mainPanel.repaint();
            }
        });

        cbx1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                pp.show = !pp.show;
                mainPanel.repaint();
            }
        });
        cbx2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if(pp.getPointsCount() > 0)
                    fp2.show = !fp2.show;
                mainPanel.repaint();
            }
        });
        cbx3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if(pp.getPointsCount() > 0)
                    fp3.show = !fp3.show;
                mainPanel.repaint();
            }
        });

        clr1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Color newColor =
                        JColorChooser.showDialog(
                                MainWindow.this, "Выбор цвета точки", Color.WHITE);
                if(newColor != null) {
                    clr1.setBackground(newColor);
                    pp.changeColor(newColor);
                    mainPanel.repaint();
                }
            }
        });

        clr2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Color newColor =
                        JColorChooser.showDialog(
                                MainWindow.this, "Выбор цвета полинома", Color.WHITE);
                if(newColor != null) {
                    clr2.setBackground(newColor);
                    fp2.changeColor(newColor);
                    mainPanel.repaint();
                }
            }
        });

        clr3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Color newColor =
                        JColorChooser.showDialog(
                                MainWindow.this, "Выбор цвета производной", Color.WHITE);
                if(newColor != null)
                {
                    clr3.setBackground(newColor);
                    fp3.changeColor(newColor);
                    mainPanel.repaint();
                }
            }
        });

        cpgl = new GroupLayout(controlPanel);
        controlPanel.setLayout(cpgl);

        mainPanel.setBackground(Color.WHITE);
        controlPanel.setBackground(Color.WHITE);

        JLabel xmin = new JLabel();
        JLabel xmax = new JLabel();
        JLabel ymin = new JLabel();
        JLabel ymax = new JLabel();
        JLabel clrnm1 = new JLabel();
        JLabel clrnm2 = new JLabel();
        JLabel clrnm3 = new JLabel();

        clrnm1.setText("Цвет точки");
        clrnm2.setText("Цвет полинома");
        clrnm3.setText("Цвет производной");
        xmin.setText("xmin");
        xmax.setText("xmax");
        ymin.setText("ymin");
        ymax.setText("ymax");


        sp1.addChangeListener(e -> {
            nmsp3.setMinimum((Double)nmsp1.getValue() + 2*(Double)nmsp3.getStepSize());
            Converter cvrt = new Converter((Double)nmsp1.getValue(),(Double)nmsp3.getValue(),
                    (Double)nmsp2.getValue(), (Double)nmsp4.getValue(),
                    mainPanel.getWidth(), mainPanel.getHeight());
            crt.changeSize(cvrt);
            pp.changeConverter(cvrt);
            fp2.changeConventer(cvrt);
            mainPanel.repaint();
        });

        sp3.addChangeListener(e -> {
            nmsp1.setMaximum((Double)nmsp3.getValue() - 2*(Double)nmsp1.getStepSize());
            Converter cvrt = new Converter((Double)nmsp1.getValue(),(Double)nmsp3.getValue(),
                    (Double)nmsp2.getValue(), (Double)nmsp4.getValue(),
                    mainPanel.getWidth(), mainPanel.getHeight());
            crt.changeSize(cvrt);
            pp.changeConverter(cvrt);
            fp2.changeConventer(cvrt);
            mainPanel.repaint();
        });

        sp2.addChangeListener(e -> {
            nmsp4.setMinimum((Double)nmsp2.getValue() + 2*(Double)nmsp4.getStepSize());
            Converter cvrt = new Converter((Double)nmsp1.getValue(),(Double)nmsp3.getValue(),
                    (Double)nmsp2.getValue(), (Double)nmsp4.getValue(),
                    mainPanel.getWidth(), mainPanel.getHeight());
            crt.changeSize(cvrt);
            pp.changeConverter(cvrt);
            fp2.changeConventer(cvrt);
            mainPanel.repaint();
        });

        sp4.addChangeListener(e -> {
            nmsp2.setMaximum((Double)nmsp4.getValue() - 2*(Double)nmsp2.getStepSize());
            Converter cvrt = new Converter((Double)nmsp1.getValue(),(Double)nmsp3.getValue(),
                    (Double)nmsp2.getValue(), (Double)nmsp4.getValue(),
                    mainPanel.getWidth(), mainPanel.getHeight());
            crt.changeSize(cvrt);
            pp.changeConverter(cvrt);
            fp2.changeConventer(cvrt);
            mainPanel.repaint();
        });

        cpgl.setHorizontalGroup(cpgl.createSequentialGroup()
                .addGap(std)
                .addGroup(cpgl.createParallelGroup()
                        .addComponent(xmin)
                        .addComponent(ymin)
                )
                .addGap(std)
                .addGroup(cpgl.createParallelGroup()
                        .addComponent(sp1)
                        .addComponent(sp2)
                )
                .addGap(std)
                .addGroup(cpgl.createParallelGroup()
                        .addComponent(xmax)
                        .addComponent(ymax)
                )
                .addGap(std)
                .addGroup(cpgl.createParallelGroup()
                        .addComponent(sp3)
                        .addComponent(sp4)
                )
                .addGap(std)
                .addGroup(cpgl.createParallelGroup()
                        .addComponent(cbx1)
                        .addComponent(cbx2)
                        .addComponent(cbx3)
                )
                .addGap(std)
                .addGroup(cpgl.createParallelGroup()
                        .addComponent(clr1,20, 20, 20)
                        .addComponent(clr2,20, 20, 20)
                        .addComponent(clr3,20, 20, 20)
                )
                .addGap(std)
                .addGroup(cpgl.createParallelGroup()
                        .addComponent(clrnm1)
                        .addComponent(clrnm2)
                        .addComponent(clrnm3)
                )
                .addGap(std)

        );

        cpgl.setVerticalGroup(cpgl.createSequentialGroup()
                .addGap(std)
                .addGroup(cpgl.createParallelGroup()
                        .addComponent(xmin)
                        .addComponent(sp1)
                        .addComponent(xmax)
                        .addComponent(sp3)
                        .addComponent(cbx1)
                        .addComponent(clr1, 20, 20, 20)
                        .addComponent(clrnm1)
                )
                .addGap(std)
                .addGroup(cpgl.createParallelGroup()
                        .addComponent(cbx2)
                        .addComponent(clr2,20, 20, 20)
                        .addComponent(clrnm2)
                )
                .addGap(std)
                .addGroup(cpgl.createParallelGroup()
                        .addComponent(ymin)
                        .addComponent(sp2)
                        .addComponent(ymax)
                        .addComponent(sp4)
                        .addComponent(cbx3)
                        .addComponent(clr3,20, 20, 20)
                        .addComponent(clrnm3)
                )
                .addGap(std)
        );

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGap(std)
                .addGroup(gl.createParallelGroup()
                        .addComponent(mainPanel)
                        .addComponent(controlPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Integer.MAX_VALUE)
                )
                .addGap(std)
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGap(10)
                .addComponent(mainPanel)
                .addGap(7)
                .addComponent(controlPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(10)
        );

    }
}
