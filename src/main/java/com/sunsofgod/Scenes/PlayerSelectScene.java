package com.sunsofgod.Scenes;

import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.getSceneService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunsofgod.PlatformerApp;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.media.MediaPlayer;

public class PlayerSelectScene extends FXGLMenu {

    private boolean[] players = { false, false, false, false };

    public Button createCustomButton(
            String unclickedImagePath,
            String clickedImagePath,
            String playerKey,
            int count) {
        Button button = new Button("");
        button.setBackground(Background.EMPTY);

        // Load images
        Image unclickedImage = new Image(getClass().getResource(unclickedImagePath).toExternalForm());
        Image clickedImage = new Image(getClass().getResource(clickedImagePath).toExternalForm());

        ImageView imageView = new ImageView(unclickedImage);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        button.setGraphic(imageView);

        // Apply continuous bounce effect
        Animation.applyContinuousBounceEffect(button, 0);

        // Define hover and click media (hardcoded)
        Media hoverSound = new Media(getClass().getResource("/assets/sounds/hoverSoundfx.mp3").toExternalForm());
        Media clickSound = new Media(getClass().getResource("/assets/sounds/clickedSoundfx.mp3").toExternalForm());

        MediaPlayer hoverMedia = new MediaPlayer(hoverSound);
        MediaPlayer clickMedia = new MediaPlayer(clickSound);

        // Apply hover and click effects
        Animation.applyHoverAndClickEffects(button, hoverMedia, clickMedia);

        final boolean[] isClicked = { false };

        // Button click action logic
        button.setOnAction(e -> {
            isClicked[0] = !isClicked[0];
            if (isClicked[0])
                players[count] = true;
            else
                players[count] = false;
            imageView.setImage(isClicked[0] ? clickedImage : unclickedImage);
            System.out.println(isClicked[0] ? "Clicked " + playerKey : "Unclicked " + playerKey);
            System.out.println("Clicked " + count);
        });

        return button;
    }

    // Path to the JSON file
    String filePath = "src/main/resources/database.json";
    ObjectMapper objectMapper = new ObjectMapper();
    File file = new File(filePath);

    public PlayerSelectScene() {
        super(MenuType.MAIN_MENU);
        Image backgroundImage = new Image(
                getClass().getResource("/assets/textures/bgPlayerSelect.png").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(1280);
        backgroundView.setFitHeight(720);
        // black vbox
        VBox blackVbox = new VBox(10);

        blackVbox.setPrefWidth(1280);
        blackVbox.setPrefHeight(720);
        blackVbox.setMaxWidth(1280);
        blackVbox.setMaxHeight(720);

        // yellow hbox within black
        HBox yellowHbox = new HBox(10);

        yellowHbox.setPrefWidth(1280);
        yellowHbox.setPrefHeight(80);
        yellowHbox.setMaxWidth(1280);
        yellowHbox.setMaxHeight(60);
        yellowHbox.setPadding(new Insets(20, 0, 0, 40));
        // orange hbox within black
        HBox orangeHbox = new HBox(10);
        orangeHbox.setAlignment(javafx.geometry.Pos.CENTER);

        orangeHbox.setPrefWidth(1280);
        orangeHbox.setPrefHeight(100);
        orangeHbox.setMaxWidth(1280);
        orangeHbox.setMaxHeight(100);

        Text playselectInfo = new Text("Choose your Rider");
        playselectInfo.setFont(Font.font("Tahoma", FontWeight.BOLD, 60));
        playselectInfo.setFill(Color.WHITE);

        Text playselectInfoOutline = new Text("Choose your Rider");
        playselectInfoOutline.setFont(Font.font("Tahoma", FontWeight.BOLD, 60));
        playselectInfoOutline.setFill(Color.TRANSPARENT);
        playselectInfoOutline.setStroke(Color.web("#2d5d8c"));
        playselectInfoOutline.setStrokeWidth(8);
        StackPane textStack = new StackPane();
        Animation.applyContinuousBounceEffect(textStack, 250);
        // red hbox within black
        HBox redHbox = new HBox(10);
        redHbox.setAlignment(javafx.geometry.Pos.CENTER);

        redHbox.setPrefWidth(1280);
        redHbox.setPrefHeight(100);
        redHbox.setMaxWidth(1280);
        redHbox.setMaxHeight(100);
        Text playselectInfo2 = new Text("Select multiple for Multiplayer");
        playselectInfo2.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
        playselectInfo2.setFill(Color.WHITE);

        Text playselectInfoOutline2 = new Text("Select multiple for Multiplayer");
        playselectInfoOutline2.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
        playselectInfoOutline2.setFill(Color.TRANSPARENT);
        playselectInfoOutline2.setStroke(Color.web("#2d5d8c"));
        playselectInfoOutline2.setStrokeWidth(3);
        StackPane textStack2 = new StackPane();
        Animation.applyContinuousBounceEffect(textStack2, 750);

        // green hbox within black
        HBox greenHbox = new HBox(10);

        greenHbox.setPrefWidth(1280);
        greenHbox.setPrefHeight(500);
        greenHbox.setMaxWidth(1280);
        greenHbox.setMaxHeight(500);

        // pink vbox within green
        HBox pinkVbox = new HBox(10);
        pinkVbox.setAlignment(javafx.geometry.Pos.CENTER);

        pinkVbox.setPrefWidth(320);
        pinkVbox.setPrefHeight(400);
        pinkVbox.setMaxWidth(320);
        pinkVbox.setMaxHeight(400);

        // indigo vbox within green
        HBox indigoVbox = new HBox(10);
        indigoVbox.setAlignment(javafx.geometry.Pos.CENTER);

        indigoVbox.setPrefWidth(320);
        indigoVbox.setPrefHeight(400);
        indigoVbox.setMaxWidth(320);
        indigoVbox.setMaxHeight(400);

        // purple vbox within green
        HBox purpleVbox = new HBox(10);
        purpleVbox.setAlignment(javafx.geometry.Pos.CENTER);

        purpleVbox.setPrefWidth(320);
        purpleVbox.setPrefHeight(400);
        purpleVbox.setMaxWidth(320);
        purpleVbox.setMaxHeight(400);

        // brown vbox within green
        HBox brownVbox = new HBox(10);
        brownVbox.setAlignment(javafx.geometry.Pos.CENTER);

        brownVbox.setPrefWidth(320);
        brownVbox.setPrefHeight(400);
        brownVbox.setMaxWidth(320);
        brownVbox.setMaxHeight(400);

        // cyan hbox within black
        HBox cyanHbox = new HBox(10);
        cyanHbox.setAlignment(javafx.geometry.Pos.CENTER);
        cyanHbox.setPrefWidth(1280);
        cyanHbox.setPrefHeight(60);
        cyanHbox.setMaxWidth(1280);
        cyanHbox.setMaxHeight(60);

        // cyan hbox within cyan
        HBox cyanHbox3 = new HBox(10);
        cyanHbox3.setAlignment(javafx.geometry.Pos.CENTER);
        cyanHbox3.setPrefWidth(250);
        cyanHbox3.setPrefHeight(60);
        cyanHbox3.setMaxWidth(1280);
        cyanHbox3.setMaxHeight(60);
        cyanHbox.setPadding(new Insets(0, 0, 30, 450));
        HBox.setMargin(cyanHbox3, new Insets(0, 0, 0, 250));

        Text playselectInfo3 = new Text("Single Player");
        playselectInfo3.setFont(Font.font("Tahoma", FontWeight.BOLD, 60));
        playselectInfo3.setFill(Color.WHITE);

        Text playselectInfoOutline3 = new Text("Single Player");
        playselectInfoOutline3.setFont(Font.font("Tahoma", FontWeight.BOLD, 60));
        playselectInfoOutline3.setFill(Color.TRANSPARENT);
        playselectInfoOutline3.setStroke(Color.web("#2d5d8c"));
        playselectInfoOutline3.setStrokeWidth(8);
        StackPane textStack3 = new StackPane();
        Animation.applyContinuousBounceEffect(textStack3, 950);

        Media hoverSound = new Media(getClass().getResource("/assets/sounds/hoverSoundfx.mp3").toExternalForm());
        MediaPlayer hoverMedia = new MediaPlayer(hoverSound);
        Media clickedSound = new Media(getClass().getResource("/assets/sounds/clickedSoundfx.mp3").toExternalForm());
        MediaPlayer clickedMedia = new MediaPlayer(clickedSound);

        Button pSelectButton = new Button("");
        pSelectButton.setBackground(Background.EMPTY);
        Image image = new Image(getClass().getResource("/assets/textures/buttons/selectButton.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(130);
        imageView.setFitHeight(55);
        pSelectButton.setGraphic(imageView);
        Animation.applyContinuousBounceEffect(pSelectButton, 500);
        Animation.applyHoverAndClickEffects(pSelectButton, hoverMedia, clickedMedia);

        pSelectButton.setOnAction(e -> {
            checkBtns();
        });

        Button pandaButton = createCustomButton(
                "/assets/textures/buttons/unclickedpandaButton.png",
                "/assets/textures/buttons/clickedpandaButton.png",
                "player1", 0);

        Button shoppeeButton = createCustomButton(
                "/assets/textures/buttons/unclickedshoppeeButton.png",
                "/assets/textures/buttons/clickedshoppeeButton.png",
                "player3", 1);

        Button lazadaButton = createCustomButton(
                "/assets/textures/buttons/unclickedlazadaButton.png",
                "/assets/textures/buttons/clickedlazadaButton.png",
                "player2", 2);

        Button zaloraButton = createCustomButton(
                "/assets/textures/buttons/unclickedzaloraButton.png",
                "/assets/textures/buttons/clickedzaloraButton.png",
                "player4", 3);

        // Button optionsButton = new Button("Options");
        // optionsButton.setOnAction(e -> {
        // System.out.println("CLICKED OPTIONS");
        // });

        Button goBackButton = new Button("");
        goBackButton.setBackground(Background.EMPTY);
        Image backImage = new Image(getClass().getResource("/assets/textures/buttons/backButton.png").toExternalForm());
        ImageView backView = new ImageView(backImage);
        backView.setFitWidth(100);
        backView.setFitHeight(70);
        goBackButton.setGraphic(backView);
        Animation.applyContinuousBounceEffect(goBackButton, 0);
        Animation.applyHoverAndClickEffects(goBackButton, hoverMedia, clickedMedia);

        goBackButton.setOnAction(e -> {
            getSceneService().popSubScene();
        });
        getContentRoot().getChildren().add(backgroundView);
        blackVbox.getChildren().addAll(yellowHbox, orangeHbox, redHbox, greenHbox, cyanHbox);
        greenHbox.getChildren().addAll(pinkVbox, purpleVbox, brownVbox, indigoVbox);
        yellowHbox.getChildren().addAll(goBackButton);
        pinkVbox.getChildren().addAll(pandaButton);
        purpleVbox.getChildren().addAll(lazadaButton);
        indigoVbox.getChildren().addAll(zaloraButton);
        brownVbox.getChildren().addAll(shoppeeButton);
        orangeHbox.getChildren().addAll(textStack);
        redHbox.getChildren().addAll(textStack2);
        cyanHbox.getChildren().addAll(textStack3, cyanHbox3);
        cyanHbox3.getChildren().addAll(pSelectButton);
        getContentRoot().getChildren().add(blackVbox);
        textStack.getChildren().addAll(playselectInfoOutline, playselectInfo);
        textStack2.getChildren().addAll(playselectInfoOutline2, playselectInfo2);
        textStack3.getChildren().addAll(playselectInfoOutline3, playselectInfo3);
    }

    private void checkBtns() {
        // check if global vars are committed
        for (boolean b : players) {
            if (b) {
                System.out.println("i work");

                goToLevelSelect();
                return;
            }
        }
        System.out.println("Please select a player!");

    }

    private void goToLevelSelect() {
        ((PlatformerApp) FXGL.getApp()).playersI = players;
        getSceneService().popSubScene();
        getSceneService().pushSubScene(new LevelSelectScene());
    }
}