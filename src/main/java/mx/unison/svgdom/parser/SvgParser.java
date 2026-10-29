package mx.unison.svgdom.parser;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import mx.unison.svgdom.model.*;

public class SvgParser {

    private String filePath;

    public SvgParser(String filePath) {
        this.filePath = filePath;
    }

    public List<SvgFigure> parse() {
        List<SvgFigure> figures = new ArrayList<>();

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("El archivo no existe: " + file.getAbsolutePath());
                return figures;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getDocumentElement().getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String tagName = element.getTagName().toLowerCase();
                    SvgStyle style = parseStyle(element);

                    switch (tagName) {
                        case "line":
                            double x1 = getAttributeAsDouble(element, "x1", 0);
                            double y1 = getAttributeAsDouble(element, "y1", 0);
                            double x2 = getAttributeAsDouble(element, "x2", 0);
                            double y2 = getAttributeAsDouble(element, "y2", 0);
                            figures.add(new LineFigure(x1, y1, x2, y2, style));
                            break;

                        case "circle":
                            double cx = getAttributeAsDouble(element, "cx", 0);
                            double cy = getAttributeAsDouble(element, "cy", 0);
                            double r = getAttributeAsDouble(element, "r", 0);
                            figures.add(new CircleFigure(cx, cy, r, style));
                            break;

                        case "rect":
                            double rxPos = getAttributeAsDouble(element, "x", 0);
                            double ryPos = getAttributeAsDouble(element, "y", 0);
                            double width = getAttributeAsDouble(element, "width", 0);
                            double height = getAttributeAsDouble(element, "height", 0);
                            figures.add(new RectFigure(rxPos, ryPos, width, height, style));
                            break;

                        case "ellipse":
                            double ecx = getAttributeAsDouble(element, "cx", 0);
                            double ecy = getAttributeAsDouble(element, "cy", 0);
                            double rx = getAttributeAsDouble(element, "rx", 0);
                            double ry = getAttributeAsDouble(element, "ry", 0);
                            figures.add(new EllipseFigure(ecx, ecy, rx, ry, style));
                            break;

                        case "polyline":
                            String pointsStr = element.getAttribute("points");
                            if (pointsStr != null && !pointsStr.isEmpty()) {
                                int[] xPoints, yPoints;
                                String[] pairs = pointsStr.trim().split("\\s+");
                                int count = pairs.length;
                                xPoints = new int[count];
                                yPoints = new int[count];
                                for (int p = 0; p < count; p++) {
                                    String[] coords = pairs[p].split(",");
                                    if (coords.length == 2) {
                                        xPoints[p] = (int) Double.parseDouble(coords[0].trim());
                                        yPoints[p] = (int) Double.parseDouble(coords[1].trim());
                                    }
                                }
                                figures.add(new PolylineFigure(xPoints, yPoints, style));
                            }
                            break;

                        case "text":
                            double tx = getAttributeAsDouble(element, "x", 0);
                            double ty = getAttributeAsDouble(element, "y", 0);
                            String content = element.getTextContent();
                            figures.add(new TextFigure(tx, ty, content, style));
                            break;
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error al procesar el documento SVG: " + e.getMessage());
            e.printStackTrace();
        }

        return figures;
    }

    private SvgStyle parseStyle(Element element) {
        SvgStyle style = new SvgStyle();

        if (element.hasAttribute("fill")) {
            style.setFill(element.getAttribute("fill"));
        }
        if (element.hasAttribute("stroke")) {
            style.setStroke(element.getAttribute("stroke"));
        }
        if (element.hasAttribute("stroke-width")) {
            try {
                style.setStrokeWidth(Double.parseDouble(element.getAttribute("stroke-width")));
            } catch (NumberFormatException e) {
                style.setStrokeWidth(1.0);
            }
        }
        if (element.hasAttribute("font-family")) {
            style.setFontFamily(element.getAttribute("font-family"));
        }
        if (element.hasAttribute("font-size")) {
            try {
                style.setFontSize(Double.parseDouble(element.getAttribute("font-size")));
            } catch (NumberFormatException e) {
                style.setFontSize(12.0);
            }
        }

        return style;
    }

    private double getAttributeAsDouble(Element element, String attrName, double defaultValue) {
        if (element.hasAttribute(attrName)) {
            try {
                return Double.parseDouble(element.getAttribute(attrName));
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
}