package mx.unison.svgdom.model;

import java.awt.Font;
import java.awt.Graphics2D;

public final class TextFigure extends SvgFigure {
    private final double x,y; private final String text; private final String family; private final float size;
    public TextFigure(double x,double y,String text,String family,float size,SvgStyle style) {
        super(style);this.x=x;this.y=y;this.text=text;this.family=family;this.size=size;
    }
    @Override public void draw(Graphics2D g) {
        g.setFont(new Font(family, Font.PLAIN, Math.max(1,Math.round(size))));
        g.setColor(style.fillEnabled() ? style.fill() : java.awt.Color.BLACK);
        g.drawString(text,(float)x,(float)y); // y corresponde a la línea base SVG.
    }
}
