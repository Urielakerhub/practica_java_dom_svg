package mx.unison.svgdom.model;

public class SvgStyle {
    private String fill;
    private String stroke;
    private double strokeWidth;

    public SvgStyle() {
        this.fill = "none";
        this.stroke = "black";
        this.strokeWidth = 1.0;
    }

    public SvgStyle(String fill, String stroke, double strokeWidth) {
        this.fill = fill;
        this.stroke = stroke;
        this.strokeWidth = strokeWidth;
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
}