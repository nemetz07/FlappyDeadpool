package flappypool;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Az eredményeket tároló XML fájlok kezeléséért felelős osztály.
 */
public class ScoreManager {
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document document;
    private NodeList scores;
    private Element root;

    public ScoreManager(){
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.parse("score.xml");
            root = document.getDocumentElement();
            scores = root.getElementsByTagName("score");
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            setDefault();
        }
    }

    public void reload(){
        try {
            document = builder.parse("score.xml");
            root = document.getDocumentElement();
            scores = root.getElementsByTagName("score");
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            setDefault();
        }
    }

    /**
     * Készít egy üres XML fájlt.
     */
    public void setDefault(){
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();

            document = builder.newDocument();
            root = document.createElement("highscores");
            document.appendChild(root);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("score.xml"));
            transformer.transform(source, result);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Felvesz az eredmények közé egy újat.
     * @param value Az elért pontszám.
     * @param name A játékos neve.
     */
    public void addScore(int value, String name, String difficulty){
        Element score = document.createElement("score");
        score.setAttribute("value", String.valueOf(value));
        score.setAttribute("name", name);
        score.setAttribute("difficulty", difficulty);
        root.appendChild(score);
    }

    /**
     * Frissíti az XML fájl tartalmát.
     */
    public void save(){
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("score.xml"));
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Score> getScores(){
        ArrayList<Score> highscores = new ArrayList<>();

        for(int i=0;i<scores.getLength();++i){
            Node node = scores.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element)node;
                highscores.add(new Score(Integer.valueOf(element.getAttribute("value")), element.getAttribute("name"), element.getAttribute("difficulty")));
            }
        }

        return highscores;
    }

}
