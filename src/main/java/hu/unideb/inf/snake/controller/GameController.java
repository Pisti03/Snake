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
import hu.unideb.inf.snake.model.Direction;
import hu.unideb.inf.snake.model.GameEngine;
import hu.unideb.inf.snake.model.Position;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kokas István
 */
public class GameController implements Initializable {

    private static Logger logger = LoggerFactory.getLogger(GameController.class);
    @FXML
    private GridPane gridPane;
    @FXML
    private Button buttonStart;
    @FXML
    private Button buttonPause;
    @FXML
    private Button buttonMenu;
    @FXML
    private Button buttonLeft;
    @FXML
    private Label labelScore;
    @FXML
    private ChoiceBox speedChoice;
    @FXML
    private CheckBox checkboxWall;

    public static final ObservableList<String> Speeds = FXCollections.observableArrayList("SLOW", "MEDIUM", "FAST", "EXTRA");
    private GameEngine engine;
    private SimpleBooleanProperty game;
    private IntegerProperty score;
    private IntegerProperty frameCount;
    private boolean started = false;

    @FXML
    private void handleButtonStart(ActionEvent event) {
        logger.info("Starting game.");
        started=true;
        if (buttonStart.getText().equals("Start") && game.getValue()) {
            engine.start();
            buttonStart.setText("Restart");
        } else {
            init();
            engine.start();
        }
    }

    @FXML
    private void handleButtonPause(ActionEvent event) {
        if (game.getValue() && started) {
            if (buttonPause.getText().equals("Pause")) {
                logger.info("Pause game");
                engine.pause();
                buttonPause.setText("Continue");
            } else {
                logger.info("Continue game.");
                engine.start();
                buttonPause.setText("Pause");
            }
        }
    }

    @FXML
    private void handleButtonMenu(ActionEvent event) {
        engine.stop();
        try {
            Stage stage;
            Parent root;

            stage = (Stage) buttonStart.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
            root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.error("IOException, couldn't load fxml.");
        }
    }

    @FXML
    private void keyListener(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                engine.getSnake().setNextDirection(Direction.UP);
                break;
            case A:
                engine.getSnake().setNextDirection(Direction.LEFT);
                break;
            case S:
                engine.getSnake().setNextDirection(Direction.DOWN);
                break;
            case D:
                engine.getSnake().setNextDirection(Direction.RIGHT);
                break;
            default:
                break;
        }

    }

    public void endgame() {
        engine.stop();
        logger.info("End of game.");
        try {
            Stage stage;
            Parent root;
            stage = new Stage();//uj ablak
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewScore.fxml"));
            root = loader.load();
            loader.<NewScoreController>getController().init(score.getValue());

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            logger.error("IOException");
        }
    }

    public void rajzol() {
        gridPane.getChildren().clear();
        Circle circ = new Circle(10, Color.RED);
        gridPane.add(circ, engine.getFood().getX(), engine.getFood().getY());
        Rectangle rect2 = new Rectangle(20, 20);
        rect2.setFill(Color.GREEN);
        gridPane.add(rect2, engine.getSnake().getBody().get(0).getX(), engine.getSnake().getBody().get(0).getY());

        for (int i = 1; i < engine.getSnake().getBody().size(); i++) {
            Rectangle rect = new Rectangle(20, 20);
            rect.setFill(Color.BLUE);
            gridPane.add(rect, engine.getSnake().getBody().get(i).getX(), engine.getSnake().getBody().get(i).getY());
        }
    }

    public void init() {
        labelScore.setText("Score: " + 0);
        if (engine != null) {
            engine.stop();
        }
        score = new SimpleIntegerProperty(0);
        game = new SimpleBooleanProperty(true);
        frameCount = new SimpleIntegerProperty(0);
        game.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                endgame();
            }
        });
        score.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                labelScore.setText("Score: " + score.getValue());
            }
        });

        frameCount.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                rajzol();
            }
        });

        ArrayList<Position> list = new ArrayList<>();
        Position seged = new Position(10, 10);
        list.add(seged);
        seged = new Position(9, 10);
        list.add(seged);
        seged = new Position(8, 10);
        list.add(seged);
        engine = new GameEngine(10, list, gridPane, game, score, frameCount);
        rajzol();
        engine.setWall(checkboxWall.isSelected());
        engine.changefps(speedChoice.getSelectionModel().getSelectedItem().toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        speedChoice.setItems(Speeds);
        speedChoice.getSelectionModel().select(Speeds.get(1));
        checkboxWall.setSelected(true);
        init();
        engine.stop();
        speedChoice.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        engine.changefps(speedChoice.getSelectionModel().getSelectedItem().toString());
                    }
                });
        checkboxWall.selectedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                engine.setWall(checkboxWall.isSelected());
            }
        });
    }
}
