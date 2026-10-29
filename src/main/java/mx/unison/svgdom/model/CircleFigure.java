package mx.unison.svgdom.model;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public final class CircleFigure extends SvgFigure {
    private final double cx, cy, r;
    public CircleFigure(double cx, double cy, double r, SvgStyle style) { super(style); this.cx=cx; this.cy=cy; this.r=r; }

}//SVG FIGURA CIRCLE
