package hu.unideb.inf.snake.controller;

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
import hu.unideb.inf.snake.model.Player;
import hu.unideb.inf.snake.model.XMLHandler;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hu.unideb.inf.snake.model.XMLHandlerDao;

/**
 * @author Kokas István
 */
public class HighScoresController implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(HighScoresController.class);
    @FXML
    private Button buttonMenu;
    @FXML
    private GridPane gridPane;
    private final XMLHandlerDao handler = new XMLHandler();
    private static Path p = Paths.get(System.getProperty("user.home"), "Documents", ".Snake", "players.xml");
    @FXML
    private void Menu(ActionEvent event) {
        try {
            Stage stage;
            Parent root;

            stage = (Stage) buttonMenu.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
            root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.error("IOException, couldn't load fxml.");
        }
    }

    private void updateGridPane() {
        List<Player> list = handler.readPlayersFromXML(p);
        list = handler.sortPlayersByScore(list);
        for (int i = 0; i < (list.size() <= 10 ? list.size() : 10); i++) {
            Label seged = new Label(list.get(i).getName());
            gridPane.add(seged, 1, i + 1);
            seged = new Label(Integer.toString(list.get(i).getPoint()));
            gridPane.add(seged, 2, i + 1);
            seged = new Label(list.get(i).getDate().toString());
            gridPane.add(seged, 3, i + 1);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            if (!p.toFile().isFile()) {
                logger.debug("The players.xml file does not exist.");
                Path dir = Paths.get(System.getProperty("user.home"), "Documents", ".Snake");
                dir.toFile().mkdirs();
                Files.setAttribute(dir, "dos:hidden", true);
                handler.createPlayersXML(p);
            }
        } catch (IOException ex) {
            logger.error("IOException, couldn't set directory attribute: hidden.");
        }
        updateGridPane();
    }
}
