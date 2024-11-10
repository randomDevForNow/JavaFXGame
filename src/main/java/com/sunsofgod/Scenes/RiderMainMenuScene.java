package com.sunsofgod.Scenes;

import static com.almasb.fxgl.dsl.FXGL.getGameController;
import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.getGameTimer;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.getSceneService;
import static com.almasb.fxgl.dsl.FXGL.getService;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class RiderMainMenuScene extends FXGLMenu {

    public RiderMainMenuScene() {
        super(MenuType.MAIN_MENU);

        // hover soundfx + clicked sound fx
        Media hoverSound = new Media(getClass().getResource("/assets/sounds/hoverSoundfx.mp3").toExternalForm());
        MediaPlayer hoverMedia = new MediaPlayer(hoverSound);
        Media clickedSound = new Media(getClass().getResource("/assets/sounds/clickedSoundfx.mp3").toExternalForm());
        MediaPlayer clickedMedia = new MediaPlayer(clickedSound);

    
        // Image backgroundImage = new
        // Image(getClass().getResource("/assets/textures/backgroundMainMenu.gif").toExternalForm());
        // ImageView backgroundView = new ImageView(backgroundImage);
        // backgroundView.setFitWidth(1280);
        // backgroundView.setFitHeight(720);

        // black Vbox
        VBox blackVbox = new VBox(10);

        blackVbox.setPrefWidth(1280);
        blackVbox.setPrefHeight(720);
        blackVbox.setMaxWidth(1280);
        blackVbox.setMaxHeight(720);

        // yellow hbox witin black
        HBox yellowHbox = new HBox(10);
        yellowHbox.setAlignment(javafx.geometry.Pos.CENTER);

        yellowHbox.setPrefWidth(1280);
        yellowHbox.setPrefHeight(300);
        yellowHbox.setMaxWidth(1280);
        yellowHbox.setMaxHeight(300);

        // pinagpatong ko text here para makagawa outline hihihihi
        Text gameName = new Text("Delivery Rush");
        gameName.setFont(Font.font("Tahoma", FontWeight.BOLD, 90));
        gameName.setFill(Color.WHITE);

        Text gameNameOutline = new Text("Delivery Rush");
        gameNameOutline.setFont(Font.font("Tahoma", FontWeight.BOLD, 90));
        gameNameOutline.setFill(Color.TRANSPARENT);
        gameNameOutline.setStroke(Color.web("#2d5d8c"));
        gameNameOutline.setStrokeWidth(11);
        StackPane textStack = new StackPane();
        Animation.applyContinuousBounceEffect(textStack, 1500);
        
        // orange hbox within black
        HBox orangeHbox = new HBox(10);
        orangeHbox.setAlignment(javafx.geometry.Pos.CENTER);

        orangeHbox.setPrefWidth(1280);
        orangeHbox.setPrefHeight(500);
        orangeHbox.setMaxWidth(1280);
        orangeHbox.setMaxHeight(500);

        // red vbox within orange
        HBox redVbox = new HBox(10);
        redVbox.setAlignment(javafx.geometry.Pos.CENTER);

        redVbox.setPrefWidth(426);
        redVbox.setPrefHeight(400);
        redVbox.setMaxWidth(426);
        redVbox.setMaxHeight(400);
        redVbox.setPadding(new Insets(50, -200, 100, 0));

        // green Vbox within orange
        HBox greenVbox = new HBox(10);
        greenVbox.setAlignment(javafx.geometry.Pos.CENTER);

        greenVbox.setPrefWidth(360);
        greenVbox.setPrefHeight(400);
        greenVbox.setMaxWidth(426);
        greenVbox.setMaxHeight(400);
        greenVbox.setPadding(new Insets(0, 0, 100, 0));

        // blue Vbox withing orange
        HBox blueVbox = new HBox(10);
        blueVbox.setAlignment(javafx.geometry.Pos.CENTER);

        blueVbox.setPrefWidth(426);
        blueVbox.setPrefHeight(400);
        blueVbox.setMaxWidth(426);
        blueVbox.setMaxHeight(400);
        blueVbox.setPadding(new Insets(50, 200, 100, 0));

        Button startButton = new Button("");
        startButton.setBackground(Background.EMPTY);
        Image image = new Image(getClass().getResource("/assets/textures/buttons/newgameButton.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(180);
        imageView.setFitHeight(180);
        startButton.setGraphic(imageView);
        Animation.applyContinuousBounceEffect(startButton, 500);
        Animation.applyHoverAndClickEffects(startButton, hoverMedia, clickedMedia);

        
        startButton.setOnAction(e -> {
            getSceneService().pushSubScene(new PlayerSelectScene());
            // getSceneService().pushSubScene(new VideokeScene()); call this when videoke
            // ready
        });

        Button optionButton = new Button("");
        optionButton.setBackground(Background.EMPTY);
        Image optionImage = new Image(
                getClass().getResource("/assets/textures/buttons/optionButton.png").toExternalForm());
        ImageView optionView = new ImageView(optionImage);
        optionView.setFitWidth(150);
        optionView.setFitHeight(150);
        optionButton.setGraphic(optionView);
        Animation.applyContinuousBounceEffect(optionButton, 1000);
        Animation.applyHoverAndClickEffects(optionButton, hoverMedia, clickedMedia);

        Button exitButton = new Button("");
        exitButton.setBackground(Background.EMPTY);
        Image exitImage = new Image(getClass().getResource("/assets/textures/buttons/exitButton.png").toExternalForm());
        ImageView exitView = new ImageView(exitImage);
        exitView.setFitWidth(150);
        exitView.setFitHeight(150);
        exitButton.setGraphic(exitView);
        Animation.applyContinuousBounceEffect(exitButton, 0);
        Animation.applyHoverAndClickEffects(exitButton, hoverMedia, clickedMedia);
        exitButton.setOnAction(e -> {
            System.out.println("Exit button pressed. Terminating program.");
            System.exit(0); // This will close the application
        });

        // hierarachy
        // getContentRoot().getChildren().add(backgroundView);
        blackVbox.getChildren().addAll(yellowHbox, orangeHbox);
        textStack.getChildren().addAll(gameNameOutline, gameName);
        yellowHbox.getChildren().addAll(textStack);
        redVbox.getChildren().addAll(exitButton);
        greenVbox.getChildren().addAll(startButton);
        blueVbox.getChildren().addAll(optionButton);
        orangeHbox.getChildren().addAll(redVbox, greenVbox, blueVbox);
        getContentRoot().getChildren().add(blackVbox);

        // PANLAGAY CLIPPING ETC.)
        // Pede rin magsearch ng ano kung pano yung nagalaw na background na wallpaper
        // ng mga parcel tapos stripes background
    }
}
