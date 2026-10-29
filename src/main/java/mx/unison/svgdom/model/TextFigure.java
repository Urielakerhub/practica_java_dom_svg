package mx.unison.svgdom.model;

import java.awt.Graphics2D;

public class TextFigure extends SvgFigure {
    private double x;
    private double y;
    private String text;

    public TextFigure(double x, double y, String text, SvgStyle style) {
        super(style);
        this.x = x;
        this.y = y;
        this.text = text;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getText() {
        return text;
    }

    @Override
    public void draw(Graphics2D g) {
        if (text != null) {
            g.drawString(text, (int) x, (int) y);
        }
    }
}