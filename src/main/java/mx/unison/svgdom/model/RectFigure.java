package mx.unison.svgdom.model;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Graphics2D;

public class RectFigure extends SvgFigure {
    private double x;
    private double y;
    private double width;
    private double height;

    public RectFigure(double x, double y, double width, double height, SvgStyle style) {
        super(style);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D g) {
        int ix = (int) x;
        int iy = (int) y;
        int iw = (int) width;
        int ih = (int) height;

        Color fillColor = SvgStyle.parseColor(style.getFill());
        if (fillColor != null) {
            g.setColor(fillColor);
            g.fillRect(ix, iy, iw, ih);
        }

        Color strokeColor = SvgStyle.parseColor(style.getStroke());
        if (strokeColor != null) {
            g.setColor(strokeColor);
            g.setStroke(new BasicStroke((float) style.getStrokeWidth()));
            g.drawRect(ix, iy, iw, ih);
        }
    }
}
