package mx.unison.svgdom.model;

import java.awt.Graphics2D;

public abstract class SvgFigure {
    protected SvgStyle style;

    public SvgFigure() {
        this.style = new SvgStyle();
    }

    public SvgFigure(SvgStyle style) {
        this.style = style;
    }

    public SvgStyle getStyle() {
        return style;
    }

    public void setStyle(SvgStyle style) {
        this.style = style;
    }

    public abstract void draw(Graphics2D g);
}