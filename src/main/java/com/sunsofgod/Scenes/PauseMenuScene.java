package com.sunsofgod.Scenes;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.input.UserAction;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import com.sunsofgod.karaoke.KaraokeWindow;

public class PauseMenuScene extends FXGLMenu {

    public PauseMenuScene() {
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

        VBox redVbox = new VBox(10);
        redVbox.setAlignment(javafx.geometry.Pos.CENTER);
        
        redVbox.setPrefWidth(650);
        redVbox.setPrefHeight(550);
        redVbox.setMaxWidth(650);
        redVbox.setMaxHeight(550);
        redVbox.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-border-width: 2; " +
            "-fx-border-radius: 5; " +
            "-fx-background-image: url('/assets/textures/background/pauseMenu.png'); " + 
            "-fx-background-size: cover; " + 
            "-fx-background-position: center;" 
        );
        redVbox.setPadding(new Insets(200, 140, 50, 140));

        HBox blueHbox = new HBox(10);
        blueHbox.setAlignment(javafx.geometry.Pos.CENTER);

        blueHbox.setPrefWidth(550);
        blueHbox.setPrefHeight(100);
        blueHbox.setMaxWidth(550);
        blueHbox.setMaxHeight(100);

        Button resumeButton = new Button("");
        resumeButton.setBackground(Background.EMPTY);
        Image resumeImage = new Image(getClass().getResource("/assets/textures/buttons/resumeButton.png").toExternalForm());
        ImageView resumeImageView = new ImageView(resumeImage);
        resumeImageView.setFitWidth(250);
        resumeImageView.setFitHeight(65);
        resumeButton.setGraphic(resumeImageView);
        Animation.applyHoverAndClickEffects(resumeButton, hoverMedia, clickedMedia);

        HBox blueHbox2 = new HBox(10);
        blueHbox2.setAlignment(javafx.geometry.Pos.CENTER);

        blueHbox2.setPrefWidth(550);
        blueHbox2.setPrefHeight(100);
        blueHbox2.setMaxWidth(550);
        blueHbox2.setMaxHeight(100);

        Button videokeButton = new Button("");
        videokeButton.setBackground(Background.EMPTY);
        Image videokeImage = new Image(getClass().getResource("/assets/textures/buttons/videokeButton.png").toExternalForm());
        ImageView videokeImageView = new ImageView(videokeImage);
        videokeImageView.setFitWidth(250);
        videokeImageView.setFitHeight(65);
        videokeButton.setGraphic(videokeImageView);
        Animation.applyHoverAndClickEffects(videokeButton, hoverMedia, clickedMedia);


        videokeButton.setOnAction(event -> {
            openMusicSelection();
        });

        HBox blueHbox3 = new HBox(10);
        blueHbox3.setAlignment(javafx.geometry.Pos.CENTER);
        blueHbox3.setPrefWidth(550);
        blueHbox3.setPrefHeight(100);
        blueHbox3.setMaxWidth(550);
        blueHbox3.setMaxHeight(100);

        Button menuButton = new Button("");
        menuButton.setBackground(Background.EMPTY);
        Image menuImage = new Image(getClass().getResource("/assets/textures/buttons/menuButton.png").toExternalForm());
        ImageView menuImageView = new ImageView(menuImage);
        menuImageView.setFitWidth(250);
        menuImageView.setFitHeight(65);
        menuButton.setGraphic(menuImageView);
        Animation.applyHoverAndClickEffects(menuButton, hoverMedia, clickedMedia);

        menuButton.setOnMouseReleased(event -> {
         getInput().clearAll();
         getSceneService().popSubScene();  
         getSceneService().pushSubScene(new RiderMainMenuScene()); 
         
        });
        HBox blueHbox4 = new HBox(10);
        blueHbox4.setAlignment(javafx.geometry.Pos.CENTER);

        blueHbox4.setPrefWidth(550);
        blueHbox4.setPrefHeight(100);
        blueHbox4.setMaxWidth(550);
        blueHbox4.setMaxHeight(100);

        blackVbox.getChildren().addAll(redVbox);
        redVbox.getChildren().addAll(blueHbox,blueHbox2,blueHbox3,blueHbox4);
        blueHbox.getChildren().addAll(resumeButton);
        blueHbox2.getChildren().addAll(videokeButton);
        blueHbox3.getChildren().addAll(menuButton);
        getContentRoot().getChildren().add(blackVbox);
        
    }

    private void openMusicSelection() {
        // Create karaoke window that returns to game when closed
        KaraokeWindow karaokeWindow = new KaraokeWindow(() -> {
            // No need to transition scenes since we're already in game
            // Just hide the window and continue playing
            System.out.println("Continuing game with new song...");
        });
        karaokeWindow.show();
    }
    
}
