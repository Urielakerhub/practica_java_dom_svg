package mx.unison.svgdom.model;

import java.awt.Graphics2D;

public class CircleFigure extends SvgFigure {
    private double cx;
    private double cy;
    private double r;

    public CircleFigure(double cx, double cy, double r, SvgStyle style) {
        super(style);
        this.cx = cx;
        this.cy = cy;
        this.r = r;
    }

    public double getCx() {
        return cx;
    }

    public double getCy() {
        return cy;
    }

    public double getR() {
        return r;
    }

    @Override
    public void draw(Graphics2D g) {
        int x = (int) (cx - r);
        int y = (int) (cy - r);
        int diameter = (int) (r * 2);
        g.drawOval(x, y, diameter, diameter);
    }
}