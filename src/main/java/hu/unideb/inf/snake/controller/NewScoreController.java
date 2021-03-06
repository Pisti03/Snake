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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hu.unideb.inf.snake.model.XMLHandlerDao;

/**
 *
 * @author Kokas István
 */
public class NewScoreController implements Initializable {
    
    private static final Logger logger = LoggerFactory.getLogger(NewScoreController.class);
    
    @FXML
    private Button buttonYes;
    @FXML
    private Button buttonNo;
    @FXML
    private TextField nameField;
    @FXML
    private Label labelScore;
    @FXML
    private Label labelRequired;
    
    private final XMLHandlerDao handler = new XMLHandler();
    private static Path p = Paths.get(System.getProperty("user.home"), "Documents", ".Snake", "players.xml");
    private int score = 0;
    
    @FXML
    private void Yes(ActionEvent event) {
        labelRequired.setText("");
        if (!p.toFile().isFile()) {
            logger.debug("The players.xml file does not exist.");
            Path dir = Paths.get(System.getProperty("user.home"), "Documents", ".Snake");
            dir.toFile().mkdirs();
            try {
                Files.setAttribute(dir, "dos:hidden", true);
            } catch (IOException e) {
                logger.error("IOException, couldn't set directory attribute: hidden.");
            }
            handler.createPlayersXML(p);
        }
        
        if (!nameField.getText().isEmpty()) {
            logger.info("Player saved score.");
            Player player = new Player(nameField.getText(), score);
            if (!handler.isPlayerInXML(p, player)) {
                handler.addNewPlayerToXML(p, player);
            }
            Stage stage = (Stage) buttonYes.getScene().getWindow();
            stage.close();
        } else {
            logger.debug("Empty name field.");
            labelRequired.setTextFill(Paint.valueOf("RED"));
            labelRequired.setFont(Font.font(null, FontWeight.BOLD, 12));
            labelRequired.setText("*required");
            
        }
    }
    
    @FXML
    private void No(ActionEvent event) {
        logger.info("Player didn't save score.");
        Stage stage = (Stage) buttonYes.getScene().getWindow();
        stage.close();
    }
    
    @SuppressWarnings("checkstyle:javadocmethod")
    public void init(int score) {
        logger.trace("Player reached score: " + score);
        this.score = score;
        labelScore.setText("Your score: " + score);
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
