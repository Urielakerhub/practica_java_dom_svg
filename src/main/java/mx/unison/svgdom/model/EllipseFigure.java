package mx.unison.svgdom.model;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Graphics2D;

public class EllipseFigure extends SvgFigure {
    private double cx;
    private double cy;
    private double rx;
    private double ry;

    public EllipseFigure(double cx, double cy, double rx, double ry, SvgStyle style) {
        super(style);
        this.cx = cx;
        this.cy = cy;
        this.rx = rx;
        this.ry = ry;
    }

    @Override
    public void draw(Graphics2D g) {
        int x = (int) (cx - rx);
        int y = (int) (cy - ry);
        int w = (int) (rx * 2);
        int h = (int) (ry * 2);

        Color fillColor = SvgStyle.parseColor(style.getFill());
        if (fillColor != null) {
            g.setColor(fillColor);
            g.fillOval(x, y, w, h);
        }

        Color strokeColor = SvgStyle.parseColor(style.getStroke());
        if (strokeColor != null) {
            g.setColor(strokeColor);
            g.setStroke(new BasicStroke((float) style.getStrokeWidth()));
            g.drawOval(x, y, w, h);
        }
    }
}