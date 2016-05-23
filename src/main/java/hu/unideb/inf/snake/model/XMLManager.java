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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
 * Az xml fájlon végezhető műveleteket megvalósító osztály.
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
     * {@inheritDoc}
     */
    @Override
    public void createPlayersXML(Path path) {
        try {
            Document doc = builder.newDocument();
            Element root = doc.createElement("players");
            doc.appendChild(root);

            TransformerFactory tFact = TransformerFactory.newInstance();
            Transformer trans = tFact.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            StreamResult newFile = new StreamResult(path.toFile());
            trans.transform(domSource, newFile);
        } catch (TransformerException ex) {
            logger.error("TransformerException while creating XML file.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> readPlayersFromXML(Path path) {
        try {
            File file = path.toFile();
            Document doc = builder.parse(file);
            NodeList nodeList = doc.getElementsByTagName("player");
            List<Player> players = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String name = element.getElementsByTagName("nev").item(0).getTextContent();
                int point = Integer.parseInt(element.getElementsByTagName("pont").item(0).getTextContent());
                LocalDate date = LocalDate.parse(element.getElementsByTagName("date").item(0).getTextContent(), DateTimeFormatter.ISO_DATE);
                Player player = new Player(name, point, date);
                players.add(player);
            }
            return players;
        } catch (SAXException ex) {
            logger.error("SAXException");
        } catch (IOException ex) {
            logger.error("IOException");
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> sortPlayersByScore(List<Player> lista) {
        return lista.stream().sorted((t1, t2) -> t2.getPoint() - t1.getPoint()).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewPlayerToXML(Path path, Player jatekos) {
        try {
            Document doc;
            Element root;

            File file = path.toFile();

            doc = builder.parse(file);
            root = doc.getDocumentElement();

            Element player = doc.createElement("player");
            Element pont = doc.createElement("pont");
            pont.appendChild(doc.createTextNode(Integer.toString(jatekos.getPoint())));
            Element nev = doc.createElement("nev");
            nev.appendChild(doc.createTextNode(jatekos.getName()));
            Element date = doc.createElement("date");
            date.appendChild(doc.createTextNode(jatekos.getDate().toString()));

            player.appendChild(nev);
            player.appendChild(pont);
            player.appendChild(date);
            root.appendChild(player);

            TransformerFactory tFact = TransformerFactory.newInstance();
            Transformer trans = tFact.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            FileOutputStream asd = new FileOutputStream(path.toFile());
            StreamResult newFile = new StreamResult(asd);
            trans.transform(domSource, newFile);

        } catch (SAXException ex) {
            logger.error("SAXException");

        } catch (IOException ex) {
            logger.error("IOException");

        } catch (TransformerException ex) {
            logger.error("TransformerException");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlayerInXML(Path path, Player player) {
        List<Player> lista = readPlayersFromXML(path);
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getName().equals(player.getName())
                    && lista.get(i).getPoint() == player.getPoint()) {
                return true;
            }
        }
        return false;
    }

}
