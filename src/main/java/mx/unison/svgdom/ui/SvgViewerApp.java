package mx.unison.svgdom.ui;

import mx.unison.svgdom.parser.SvgParser;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

public final class SvgViewerApp {
    private final JFrame frame=new JFrame("Visualizador SVG — Java DOM");
    private final SvgPanel panel=new SvgPanel();
    private final JLabel status=new JLabel("Selecciona un archivo SVG para comenzar.");
    private final SvgParser parser=new SvgParser();

    private void createAndShow() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton open=new JButton("Abrir SVG…"); open.addActionListener(e->openFile());
        JPanel top=new JPanel(new BorderLayout()); top.add(open,BorderLayout.WEST); top.add(status,BorderLayout.CENTER);
        frame.add(top,BorderLayout.NORTH);
        JScrollPane scroll=new JScrollPane(panel); frame.add(scroll,BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(640,480));
        frame.setSize(850,650); frame.setLocationRelativeTo(null); frame.setVisible(true);
    }
    private void openFile() {
        JFileChooser chooser=new JFileChooser();
        chooser.setDialogTitle("Seleccionar documento SVG");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos SVG (*.svg)","svg"));
        if(chooser.showOpenDialog(frame)!=JFileChooser.APPROVE_OPTION) return;
        File file=chooser.getSelectedFile();
        try {
            SvgParser.SvgDocument document=parser.parse(file);
            panel.setFigures(document.figures());
            panel.setPreferredSize(new Dimension(document.width(),document.height()));
            panel.revalidate(); panel.repaint();
            status.setText(file.getName()+" — "+document.figures().size()+" elementos interpretados");
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(frame,"No se pudo procesar el SVG:\n"+ex.getMessage(),"Error al abrir SVG",JOptionPane.ERROR_MESSAGE);
            status.setText("Error al cargar el archivo.");
        }
    }
    public static void main(String[] args) { SwingUtilities.invokeLater(()->new SvgViewerApp().createAndShow()); }
}
