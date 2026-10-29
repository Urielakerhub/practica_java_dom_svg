package mx.unison.svgdom.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/** Propiedades visuales básicas admitidas por esta práctica. */
public record SvgStyle(Color fill, Color stroke, float strokeWidth, boolean fillEnabled, boolean strokeEnabled) {
    public static SvgStyle defaults() {
        return new SvgStyle(Color.BLACK, null, 1.0f, true, false);
    }

    public void applyStroke(Graphics2D g) {
        g.setStroke(new BasicStroke(Math.max(0.1f, strokeWidth), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        if (strokeEnabled) g.setColor(stroke);
    }
}
