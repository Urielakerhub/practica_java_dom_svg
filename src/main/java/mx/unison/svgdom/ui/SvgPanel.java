package mx.unison.svgdom.ui;

import mx.unison.svgdom.model.SvgFigure;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

public final class SvgPanel extends JPanel {
    private List<SvgFigure> figures=List.of();
    public SvgPanel() { setBackground(Color.WHITE); }
    public void setFigures(List<SvgFigure> figures) { this.figures=List.copyOf(figures); repaint(); }
    @Override protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g=(Graphics2D)graphics.create();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            for(SvgFigure figure:figures) figure.draw(g);
        } finally { g.dispose(); }
    }
}
