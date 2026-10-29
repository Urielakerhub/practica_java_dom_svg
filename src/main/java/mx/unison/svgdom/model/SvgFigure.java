package mx.unison.svgdom.model;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Graphics2D;

public class LineFigure extends SvgFigure {
    private double x1;
    private double y1;
    private double x2;
    private double y2;

    public LineFigure(double x1, double y1, double x2, double y2, SvgStyle style) {
        super(style);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(Graphics2D g) {
        Color strokeColor = SvgStyle.parseColor(style.getStroke());
        if (strokeColor != null) {
            g.setColor(strokeColor);
            g.setStroke(new BasicStroke((float) style.getStrokeWidth()));
            g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        }
    }
}