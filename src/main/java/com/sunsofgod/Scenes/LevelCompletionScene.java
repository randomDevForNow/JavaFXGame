package com.sunsofgod.Scenes;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class LevelCompletionScene extends FXGLMenu {

    public LevelCompletionScene(int levelCode) {
        super(MenuType.GAME_MENU);
        Media hoverSound = new Media(getClass().getResource("/assets/sounds/hoverSoundfx.mp3").toExternalForm());
        MediaPlayer hoverMedia = new MediaPlayer(hoverSound);
        Media clickedSound = new Media(getClass().getResource("/assets/sounds/clickedSoundfx.mp3").toExternalForm());
        MediaPlayer clickedMedia = new MediaPlayer(clickedSound);

        VBox blackVbox = new VBox(10);
        blackVbox.setAlignment(javafx.geometry.Pos.CENTER);

        blackVbox.setPrefWidth(1280);
        blackVbox.setPrefHeight(720);
        blackVbox.setMaxWidth(1280);
        blackVbox.setMaxHeight(720);

        HBox redHbox = new HBox(10);
        redHbox.setAlignment(javafx.geometry.Pos.CENTER);

        redHbox.setPrefWidth(860);
        redHbox.setPrefHeight(400);
        redHbox.setMaxWidth(860);
        redHbox.setMaxHeight(400);
        if (levelCode == 0) {
            redHbox.setStyle(
                    "-fx-background-color: transparent; " +
                            "-fx-border-width: 5; " +
                            "-fx-border-radius: 5; " +
                            "-fx-background-image: url('/assets/textures/background/levelEnd.png'); " +
                            "-fx-background-position: center; ");
        } else if (levelCode == 1) {
            redHbox.setStyle(
                    "-fx-background-color: transparent; " +
                            "-fx-border-width: 5; " +
                            "-fx-border-radius: 5; " +
                            "-fx-background-image: url('/assets/textures/background/levelFail.png'); " +
                            "-fx-background-position: center; ");
        }

        redHbox.setPadding(new Insets(90, 250, 0, 140));

        VBox blueHbox = new VBox(10);
        blueHbox.setAlignment(javafx.geometry.Pos.CENTER);

        blueHbox.setPrefWidth(150);
        blueHbox.setPrefHeight(100);

        VBox blueHbox2 = new VBox(10);
        blueHbox2.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        blueHbox2.setPrefWidth(150);
        blueHbox2.setPrefHeight(100);
        blueHbox2.setPadding(new Insets(0, 40, 0, 0));

        Button resumeButton = new Button("");
        resumeButton.setBackground(Background.EMPTY);
        Image resumeImage = new Image(
                getClass().getResource("/assets/textures/buttons/nextLevelButton.png").toExternalForm());
        ImageView resumeImageView = new ImageView(resumeImage);
        resumeImageView.setFitWidth(150);
        resumeImageView.setFitHeight(80);
        resumeButton.setGraphic(resumeImageView);
        Animation.applyHoverAndClickEffects(resumeButton, hoverMedia, clickedMedia);

        resumeButton.setOnAction(event -> {
            getSceneService().popSubScene();
        });

        Button menuButton2 = new Button("");
        menuButton2.setBackground(Background.EMPTY);

        Image menuImage = new Image(
                getClass().getResource("/assets/textures/buttons/menuButton2.png").toExternalForm());
        ImageView menuImageView = new ImageView(menuImage);
        menuImageView.setFitWidth(150);
        menuImageView.setFitHeight(80);
        menuButton2.setGraphic(menuImageView);
        Animation.applyHoverAndClickEffects(menuButton2, hoverMedia, clickedMedia);

        menuButton2.setOnMouseReleased(event -> {

            System.exit(0);

        });

        blackVbox.getChildren().addAll(redHbox);
        redHbox.getChildren().addAll(blueHbox, blueHbox2);
        blueHbox.getChildren().addAll(menuButton2);
        blueHbox2.getChildren().addAll(resumeButton);
        getContentRoot().getChildren().add(blackVbox);

    }

}