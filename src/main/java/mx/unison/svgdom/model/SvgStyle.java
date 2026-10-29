package mx.unison.svgdom.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;


public record SvgStyle(Color fill, Color stroke, float strokeWidth, boolean fillEnabled, boolean strokeEnabled) {
    public static SvgStyle defaults() {
        return new SvgStyle(Color.BLACK, null, 1.0f, true, false);
    }

