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
 * F
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kokas István
 */
public class MenuController implements Initializable {

    private Logger logger = LoggerFactory.getLogger(MenuController.class);
    @FXML
    private Button buttonStart;
    @FXML
    private Button buttonHighScores;
    @FXML
    private Button buttonExit;

    @FXML
    private void Start(ActionEvent event){
        try {
            Stage stage;
            Parent root;

            stage = (Stage) buttonStart.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Game.fxml"));
            root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.error("IOException, couldn't load fxml.");
        }
    }

    @FXML
    private void HighScores(ActionEvent event) {
        try {
            Stage stage;
            Parent root;

            stage = (Stage) buttonHighScores.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HighScores.fxml"));
            root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.error("IOException, couldn't load fxml.");
        }
    }

    @FXML
    private void Exit(ActionEvent event) {
        Platform.exit();
    }

    @Override

    public void initialize(URL url, ResourceBundle rb) {
    }
}
