package mx.unison.svgdom.model;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public final class CircleFigure extends SvgFigure {
    private final double cx, cy, r;
    public CircleFigure(double cx, double cy, double r, SvgStyle style) { super(style); this.cx=cx; this.cy=cy; this.r=r; }
    @Override public void draw(Graphics2D g) {
        Ellipse2D shape = new Ellipse2D.Double(cx-r,cy-r,2*r,2*r);
        if (style.fillEnabled()) { g.setColor(style.fill()); g.fill(shape); }
        if (style.strokeEnabled()) { g.setColor(style.stroke()); style.applyStroke(g); g.draw(shape); }
    }
}//SVG FIGURA CIRCLE
