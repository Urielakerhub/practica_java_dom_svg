package mx.unison.svgdom.model;

import java.awt.Color;

public class SvgStyle {
    private String fill;
    private String stroke;
    private double strokeWidth;
    private String fontFamily;
    private double fontSize;

    public SvgStyle() {
        this.fill = "black";
        this.stroke = "none";
        this.strokeWidth = 1.0;
        this.fontFamily = "Dialog";
        this.fontSize = 12.0;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    public String getStroke() {
        return stroke;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public double getFontSize() {
        return fontSize;
    }

    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
    }

    public static Color parseColor(String colorStr) {
        if (colorStr == null || colorStr.equals("none") || colorStr.trim().isEmpty()) {
            return null;
        }
        colorStr = colorStr.trim().toLowerCase();
        switch (colorStr) {
            case "red": return Color.RED;
            case "green": return Color.GREEN;
            case "blue": return Color.BLUE;
            case "yellow": return Color.YELLOW;
            case "black": return Color.BLACK;
            case "white": return Color.WHITE;
            case "orange": return Color.ORANGE;
            case "gray": case "grey": return Color.GRAY;
            case "lightblue": return new Color(173, 216, 230);
            case "lightgreen": return new Color(144, 238, 144);
            case "navy": return new Color(0, 0, 128);
            default:
                if (colorStr.startsWith("#")) {
                    try {
                        return Color.decode(colorStr);
                    } catch (NumberFormatException e) {
                        return Color.BLACK;
                    }
                }
                return Color.BLACK;
        }
    }
}