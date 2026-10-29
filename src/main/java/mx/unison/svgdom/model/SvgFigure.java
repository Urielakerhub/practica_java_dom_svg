package mx.unison.svgdom.model;

import java.awt.Graphics2D;

/** Abstracción común: cada figura define cómo dibujarse. */
public abstract class SvgFigure {
    protected final SvgStyle style;
    protected SvgFigure(SvgStyle style) { this.style = style; }
    public abstract void draw(Graphics2D g);
}
