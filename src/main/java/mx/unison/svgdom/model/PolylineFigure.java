package mx.unison.svgdom.model;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Graphics2D;

public class PolylineFigure extends SvgFigure {
    private int[] xPoints;
    private int[] yPoints;

    public PolylineFigure(int[] xPoints, int[] yPoints, SvgStyle style) {
        super(style);
        this.xPoints = xPoints;
        this.yPoints = yPoints;
    }

    @Override
    public void draw(Graphics2D g) {
        if (xPoints == null || yPoints == null || xPoints.length < 2) {
            return;
        }

        int nPoints = Math.min(xPoints.length, yPoints.length);

        Color fillColor = SvgStyle.parseColor(style.getFill());
        if (fillColor != null) {
            g.setColor(fillColor);
            g.fillPolygon(xPoints, yPoints, nPoints);
        }

        Color strokeColor = SvgStyle.parseColor(style.getStroke());
        if (strokeColor != null) {
            g.setColor(strokeColor);
            g.setStroke(new BasicStroke((float) style.getStrokeWidth()));
            g.drawPolyline(xPoints, yPoints, nPoints);
        }
    }
}
