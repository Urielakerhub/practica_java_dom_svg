package mx.unison.svgdom.model;

import java.awt.BasicStroke;

import java.awt.Color;
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

    @Override
    public void draw(Graphics2D g) {
        int x = (int) (cx - r);
        int y = (int) (cy - r);
        int diameter = (int) (r * 2);

        Color fillColor = SvgStyle.parseColor(style.getFill());
        if (fillColor != null) {
            g.setColor(fillColor);
            g.fillOval(x, y, diameter, diameter);
        }

        Color strokeColor = SvgStyle.parseColor(style.getStroke());
        if (strokeColor != null) {
            g.setColor(strokeColor);
            g.setStroke(new BasicStroke((float) style.getStrokeWidth()));
            g.drawOval(x, y, diameter, diameter);
        }
    }
}