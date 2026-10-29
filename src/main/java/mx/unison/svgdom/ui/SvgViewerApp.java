package mx.unison.svgdom.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import mx.unison.svgdom.model.SvgFigure;
import mx.unison.svgdom.parser.SvgParser;

public class SvgViewerApp extends JFrame {
    private SvgPanel svgPanel;
    private JLabel statusLabel;

    public SvgViewerApp() {
        setTitle("Visualizador SVG DOM");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        cargarSvgAutomatico();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        svgPanel = new SvgPanel();
        svgPanel.setBackground(Color.WHITE);
        add(svgPanel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnCargar = new JButton("Cargar svg/svg.svg");
        statusLabel = new JLabel("Estado: Listo");

        btnCargar.addActionListener(e -> cargarSvgAutomatico());

        topPanel.add(btnCargar);
        topPanel.add(statusLabel);
        add(topPanel, BorderLayout.NORTH);
    }

    private void cargarSvgAutomatico() {
        File file = new File("svg/svg.svg");
        if (!file.exists()) {
            statusLabel.setText("Error: No se encuentra el archivo svg/svg.svg");
            JOptionPane.showMessageDialog(this, "El archivo svg/svg.svg no existe en la ruta esperada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SvgParser parser = new SvgParser(file.getPath());
        List<SvgFigure> figures = parser.parse();

        svgPanel.setFigures(figures);
        statusLabel.setText("Cargadas " + figures.size() + " figuras correctamente.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SvgViewerApp().setVisible(true);
        });
    }
}