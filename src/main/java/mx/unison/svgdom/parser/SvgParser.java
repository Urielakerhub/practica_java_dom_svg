package mx.unison.svgdom.parser;

import mx.unison.svgdom.model.*;
import org.w3c.dom.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Convierte el árbol DOM de un SVG en una lista ordenada de figuras. */
public final class SvgParser {
    public record SvgDocument(int width, int height, List<SvgFigure> figures) {}

    public SvgDocument parse(File file) throws Exception {
        if (file == null || !file.isFile() || !file.canRead()) throw new IllegalArgumentException("El archivo no existe o no se puede leer.");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        Element root = doc.getDocumentElement();
        if (root == null || !"svg".equals(localName(root))) throw new IllegalArgumentException("El elemento raíz del archivo debe ser <svg>.");
        int width = integerDimension(root.getAttribute("width"), 640);
        int height = integerDimension(root.getAttribute("height"), 480);
        List<SvgFigure> figures = new ArrayList<>();
        visit(root, figures);
        return new SvgDocument(Math.max(1,width), Math.max(1,height), List.copyOf(figures));
    }

    private void visit(Element parent, List<SvgFigure> figures) {
        NodeList children = parent.getChildNodes();
        for (int i=0;i<children.getLength();i++) {
            Node node=children.item(i);
            if (!(node instanceof Element el)) continue;
            String tag=localName(el);
            try {
                SvgStyle style=style(el);
                switch(tag) {
                    case "g", "svg" -> visit(el,figures);
                    case "line" -> figures.add(new LineFigure(num(el,"x1",0),num(el,"y1",0),num(el,"x2",0),num(el,"y2",0),style));
                    case "circle" -> figures.add(new CircleFigure(num(el,"cx",0),num(el,"cy",0),positive(num(el,"r",0)),style));
                    case "rect" -> figures.add(new RectFigure(num(el,"x",0),num(el,"y",0),positive(num(el,"width",0)),positive(num(el,"height",0)),style));
                    case "ellipse" -> figures.add(new EllipseFigure(num(el,"cx",0),num(el,"cy",0),positive(num(el,"rx",0)),positive(num(el,"ry",0)),style));
                    case "polyline" -> figures.add(new PolylineFigure(parsePoints(el.getAttribute("points")),style));
                    case "text" -> figures.add(new TextFigure(num(el,"x",0),num(el,"y",0),el.getTextContent().trim(),el.getAttribute("font-family").isBlank()?"SansSerif":el.getAttribute("font-family"), (float)positive(num(el,"font-size",16)),style));
                    default -> { /* Elemento fuera del subconjunto solicitado. */ }
                }
            } catch (RuntimeException ex) {
                throw new IllegalArgumentException("Atributo inválido en <"+tag+">: "+ex.getMessage(),ex);
            }
        }
    }

    private SvgStyle style(Element el) {
        String fillText=attr(el,"fill","black");
        String strokeText=attr(el,"stroke","none");
        float sw=(float)positive(number(attr(el,"stroke-width","1")));
        boolean fillOn=!"none".equalsIgnoreCase(fillText);
        boolean strokeOn=!"none".equalsIgnoreCase(strokeText);
        return new SvgStyle(fillOn?parseColor(fillText):null,strokeOn?parseColor(strokeText):null,sw,fillOn,strokeOn);
    }
    private static String attr(Element e,String key,String fallback) { String v=e.getAttribute(key); return v.isBlank()?fallback:v.trim(); }
    private static double num(Element e,String key,double fallback) { String v=e.getAttribute(key); return v.isBlank()?fallback:number(v); }
    private static double number(String value) { double d=Double.parseDouble(value.trim()); if (!Double.isFinite(d)) throw new IllegalArgumentException("se esperaba un número finito: "+value); return d; }
    private static double positive(double n) { return Math.max(0,n); }
    private static int integerDimension(String s,int fallback) { if(s==null||s.isBlank())return fallback; try{return (int)Math.ceil(number(s));}catch(RuntimeException ex){return fallback;} }
    private static String localName(Element e) { return e.getLocalName()==null?e.getTagName().replaceFirst("^.*:",""):e.getLocalName(); }
    private static List<Point2D.Double> parsePoints(String value) {
        List<Point2D.Double> pts=new ArrayList<>();
        if(value==null||value.isBlank()) return pts;
        String[] tokens=value.trim().split("[\\s,]+" );
        if(tokens.length%2!=0) throw new IllegalArgumentException("points debe contener pares x,y");
        for(int i=0;i<tokens.length;i+=2) pts.add(new Point2D.Double(number(tokens[i]),number(tokens[i+1])));
        return pts;
    }
    private static Color parseColor(String value) {
        String s=value.trim().toLowerCase(Locale.ROOT);
        if(s.startsWith("#")) {
            String hex=s.substring(1);
            if(hex.length()==3) hex=""+hex.charAt(0)+hex.charAt(0)+hex.charAt(1)+hex.charAt(1)+hex.charAt(2)+hex.charAt(2);
            if(hex.length()==6) return new Color(Integer.parseInt(hex,16));
            throw new IllegalArgumentException("color hexadecimal inválido: "+value);
        }
        return switch(s) {
            case "black" -> Color.BLACK; case "white" -> Color.WHITE; case "red" -> Color.RED;
            case "green" -> new Color(0,128,0); case "blue" -> Color.BLUE; case "yellow" -> Color.YELLOW;
            case "cyan" -> Color.CYAN; case "magenta" -> Color.MAGENTA; case "gray", "grey" -> Color.GRAY;
            case "orange" -> Color.ORANGE; case "pink" -> Color.PINK; case "navy" -> new Color(0,0,128);
            case "lightblue" -> new Color(173,216,230); case "lightgreen" -> new Color(144,238,144);
            case "purple" -> new Color(128,0,128); case "brown" -> new Color(165,42,42);
            default -> throw new IllegalArgumentException("color no soportado: "+value);
        };
    }
}
