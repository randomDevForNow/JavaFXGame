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
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class RiderMainMenuScene extends FXGLMenu {

    public RiderMainMenuScene() {
        super(MenuType.MAIN_MENU);

        Image backgroundImage = new Image(getClass().getResource("/assets/textures/bgplaceHolder.jpg").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(1280); 
        backgroundView.setFitHeight(720); 

//black Vbox
        VBox blackVbox = new VBox(10);

        blackVbox.setPrefWidth(1280);
        blackVbox.setPrefHeight(720); 
        blackVbox.setMaxWidth(1280);
        blackVbox.setMaxHeight(720); 
        
//yellow hbox witin black
        HBox yellowHbox = new HBox(10);
        yellowHbox.setAlignment(javafx.geometry.Pos.CENTER);

        yellowHbox.setPrefWidth(1280);
        yellowHbox.setPrefHeight(300); 
        yellowHbox.setMaxWidth(1280);
        yellowHbox.setMaxHeight(300); 

        Text gameName = new Text("Kung ano man game name natin");

//orange hbox within black
        HBox orangeHbox = new HBox(10);
        orangeHbox.setAlignment(javafx.geometry.Pos.CENTER);

        orangeHbox.setPrefWidth(1280);
        orangeHbox.setPrefHeight(500);  
        orangeHbox.setMaxWidth(1280);
        orangeHbox.setMaxHeight(500); 

//red vbox within orange
        HBox redVbox = new HBox(10);
        redVbox.setAlignment(javafx.geometry.Pos.CENTER);
        
        redVbox.setPrefWidth(426);
        redVbox.setPrefHeight(400); 
        redVbox.setMaxWidth(426);
        redVbox.setMaxHeight(400); 
        redVbox.setPadding(new Insets(50,-200,100,0));

//green Vbox within orange
        HBox greenVbox = new HBox(10);
        greenVbox.setAlignment(javafx.geometry.Pos.CENTER);


        greenVbox.setPrefWidth(360);
        greenVbox.setPrefHeight(400); 
        greenVbox.setMaxWidth(426);
        greenVbox.setMaxHeight(400); 
        greenVbox.setPadding(new Insets(0,0,100,0));

//blue Vbox withing orange
        HBox blueVbox = new HBox(10);
        blueVbox.setAlignment(javafx.geometry.Pos.CENTER);

        blueVbox.setPrefWidth(426);
        blueVbox.setPrefHeight(400); 
        blueVbox.setMaxWidth(426);
        blueVbox.setMaxHeight(400); 
        blueVbox.setPadding(new Insets(50,200,100,0));

        Button startButton = new Button("");
        startButton.setBackground(Background.EMPTY);
        Image image = new Image(getClass().getResource("/assets/textures/buttons/newgameButton.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(180); 
        imageView.setFitHeight(180); 
        startButton.setGraphic(imageView);

//hover soundfx, clicked sound fx
        Media hoverSound = new Media(getClass().getResource("/assets/sounds/hoverSoundfx.mp3").toExternalForm());
        MediaPlayer hoverMedia = new MediaPlayer(hoverSound);
        Media clickedSound = new Media(getClass().getResource("/assets/sounds/clickedSoundfx.mp3").toExternalForm());
        MediaPlayer clickedMedia = new MediaPlayer(clickedSound);

        startButton.setOnMouseEntered(e -> {
            hoverMedia.stop(); 
            hoverMedia.play(); 

            startButton.setScaleX(1.1); 
            startButton.setScaleY(1.1);
        });

        startButton.setOnMouseExited(e -> {
            startButton.setScaleX(1.0); 
            startButton.setScaleY(1.0);
        });

        startButton.setOnAction(e -> {
            clickedMedia.stop(); 
            clickedMedia.play(); 
            getSceneService().pushSubScene(new PlayerSelectScene());
            // getSceneService().pushSubScene(new VideokeScene()); call this when videoke
            // ready
        });

        Button optionButton = new Button("");
        optionButton.setBackground(Background.EMPTY);
        Image optionImage = new Image(getClass().getResource("/assets/textures/buttons/optionButton.png").toExternalForm());
        ImageView optionView = new ImageView(optionImage);
        optionView.setFitWidth(150); 
        optionView.setFitHeight(150); 
        optionButton.setGraphic(optionView);

        optionButton.setOnMouseEntered(e -> {
            hoverMedia.stop(); 
            hoverMedia.play(); 

            optionButton.setScaleX(1.1); 
            optionButton.setScaleY(1.1);
        });

        optionButton.setOnMouseExited(e -> {
            optionButton.setScaleX(1.0); 
            optionButton.setScaleY(1.0);
        });

        optionButton.setOnAction(e -> {
            clickedMedia.stop(); 
            clickedMedia.play(); 
            System.out.println("CLICKED OPTIONS");
        });

        Button exitButton = new Button("");
        exitButton.setBackground(Background.EMPTY);
        Image exitImage = new Image(getClass().getResource("/assets/textures/buttons/exitButton.png").toExternalForm());
        ImageView exitView = new ImageView(exitImage);
        exitView.setFitWidth(150); 
        exitView.setFitHeight(150); 
        exitButton.setGraphic(exitView);

        exitButton.setOnMouseEntered(e -> {
            hoverMedia.stop(); 
            hoverMedia.play(); 

            exitButton.setScaleX(1.1); 
            exitButton.setScaleY(1.1);
        });

        exitButton.setOnMouseExited(e -> {
            exitButton.setScaleX(1.0); 
            exitButton.setScaleY(1.0);
        });

        exitButton.setOnAction(e -> {
            clickedMedia.stop(); 
            clickedMedia.play(); 
            System.exit(0);
        });

//hierarachy
        getContentRoot().getChildren().add(backgroundView);
        blackVbox.getChildren().addAll(yellowHbox,orangeHbox);
        yellowHbox.getChildren().addAll(gameName);
        redVbox.getChildren().addAll(exitButton);
        greenVbox.getChildren().addAll(startButton);
        blueVbox.getChildren().addAll(optionButton);
        orangeHbox.getChildren().addAll(redVbox,greenVbox,blueVbox);
        getContentRoot().getChildren().add(blackVbox);

        
        // PANLAGAY CLIPPING ETC.)
        // Pede rin magsearch ng ano kung pano yung nagalaw na background na wallpaper
        // ng mga parcel tapos stripes background
    }
}
