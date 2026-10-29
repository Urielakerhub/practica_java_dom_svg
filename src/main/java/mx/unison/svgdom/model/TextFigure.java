package mx.unison.svgdom.model;

import java.awt.Font;
import java.awt.Graphics2D;

public final class TextFigure extends SvgFigure {
    private final double x,y; private final String text; private final String family; private final float size;
    public TextFigure(double x,double y,String text,String family,float size,SvgStyle style) {
        super(style);this.x=x;this.y=y;this.text=text;this.family=family;this.size=size;
    }
    @Over