package mx.unison.svgdom.model;

import java.awt.Color;

import java.awt.Font;
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

    @Override
    public void draw(Graphics2D g) {
        if (text == null || text.trim().isEmpty()) {
            return;
        }

        Color fillColor = SvgStyle.parseColor(style.getFill());
        if (fillColor != null) {
            g.setColor(fillColor);
        } else {
            g.setColor(Color.BLACK);
        }

        Font font = new Font(style.getFontFamily(), Font.PLAIN, (int) style.getFontSize());
        g.setFont(font);

        g.drawString(text, (int) x, (int) y);
    }
}