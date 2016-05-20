package hu.unideb.inf.snake.model;

/*
 * #%L
 * Snake
 * %%
 * Copyright (C) 2016 University of Debrecen, Faculty of Informatics
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Az xml fájlon végezhető műveleteket tartalmazó osztály.
 *
 * @author Kokas István
 */
public class XMLManager implements XMLManagerDao {

    private static Logger logger = LoggerFactory.getLogger(XMLManager.class);
    private static DocumentBuilderFactory factory;
    private static DocumentBuilder builder;

    static {
        factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            logger.error("ParserConfigurationException while initializing DocumentBuilder.");
        }
    }

    /**
     * Létrehozza a megadott elérési útvonalon az players.xml fájlt.
     *
     * @param path az elérési útvonal
     */
    @Override
    public void createPlayersXML(Path path) {
        try {
            Document doc = builder.newDocument();
            Element gyoker = doc.createElement("players");
            doc.appendChild(gyoker);

            TransformerFactory tFact = TransformerFactory.newInstance();
            Transformer trans = tFact.newTransformer();
            DOMSource domForras = new DOMSource(doc);
            StreamResult ujFajl = new StreamResult(path.toFile());
            trans.transform(domForras, ujFajl);
        } catch (TransformerException ex) {
            logger.error("TransformerException while creating XML file.");
        }
    }

    /**
     * Visszaad egy az xml fájlból kiolvasott játékosok adatait tartalmazó
     * listát.
     *
     * @param path az xml fájl elérési útvonala
     * @return az xml fájlból kiolvasott játékosok adatait tartalmazó lista
     */
    @Override
    public List<Element> readPlayersFromXML(Path path) {
        try {
            File file = path.toFile();
            Document doc = builder.parse(file);
            NodeList nodeLista = doc.getElementsByTagName("player");
            List<Element> elemek = new ArrayList<>();
            for (int i = 0; i < nodeLista.getLength(); i++) {
                elemek.add((Element) nodeLista.item(i));
            }
            return elemek;

        } catch (SAXException ex) {
            logger.error("SAXException");

        } catch (IOException ex) {
            logger.error("IOException");
        }
        return null;
    }

    /**
     * Pontszám alapján csökkenő sorrendbe rendezi a listában szereplő
     * játékosokat.
     *
     * @param lista játékosok adatait tartalmazó listta
     * @return játékosokat tartalmazó lista pontszám szerint csökkenő sorrendben
     */
    @Override
    public List<Element> sortPlayersByScore(List<Element> lista) {
        //java8 required
        return lista.stream().sorted((t1, t2) -> Integer.parseInt(t2.getElementsByTagName("pont").item(0).getTextContent()) - Integer.parseInt(t1.getElementsByTagName("pont").item(0).getTextContent())).collect(Collectors.toList());
    }

    /**
     * Egy új játékost ad a players.xml fájlhoz.
     *
     * @param path az xml fájl elérési útvonala
     * @param jatekos a játékos adatait tartalmazó {@link Player} objektum
     */
    @Override
    public void addNewPlayerToXML(Path path, Player jatekos) {
        try {
            Document doc;
            Element gyoker;

            File file = path.toFile();

            doc = builder.parse(file);
            gyoker = doc.getDocumentElement();

            Element player = doc.createElement("player");
            Element pont = doc.createElement("pont");
            pont.appendChild(doc.createTextNode(Integer.toString(jatekos.getPont())));
            Element nev = doc.createElement("nev");
            nev.appendChild(doc.createTextNode(jatekos.getName()));
            Element date = doc.createElement("date");
            date.appendChild(doc.createTextNode(jatekos.getDate().toString()));

            player.appendChild(nev);
            player.appendChild(pont);
            player.appendChild(date);
            gyoker.appendChild(player);

            TransformerFactory tFact = TransformerFactory.newInstance();
            Transformer trans = tFact.newTransformer();
            DOMSource domForras = new DOMSource(doc);
            FileOutputStream asd = new FileOutputStream(path.toFile());
            StreamResult ujFajl = new StreamResult(asd);
            trans.transform(domForras, ujFajl);

        } catch (SAXException ex) {
            logger.error("SAXException");

        } catch (IOException ex) {
            logger.error("IOException");

        } catch (TransformerException ex) {
            logger.error("TransformerException");
        }
    }

    /**
     * Igazat ad vissza, ha az adott útvonalon lévő XML fájlban szerepelnek az
     * adott játékos adatai.
     *
     * @param path az xml fájl elérési útvonala
     * @param jatekos a játékos adatait tartalmazó {@link Player} objektum
     * @return <code>igaz</code>, ha a játékos adatai szerepelnek az XML
     * fájlban, egyébként <code>hamis</code>
     */
    @Override
    public boolean isPlayerInXML(Path path, Player jatekos) {
        List<Element> lista = new ArrayList<>();
        lista = readPlayersFromXML(path);
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getElementsByTagName("nev").item(0).getTextContent().equals(jatekos.getName())
                    && lista.get(i).getElementsByTagName("pont").item(0).getTextContent().equals(Integer.toString(jatekos.getPont()))) {
                return true;
            }
        }
        return false;
    }

}
