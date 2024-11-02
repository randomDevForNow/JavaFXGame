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

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
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
        blackVbox.setStyle("-fx-border-color: black; -fx-border-width: 2;");

        blackVbox.setPrefWidth(1280);
        blackVbox.setPrefHeight(720); 
        blackVbox.setMaxWidth(1280);
        blackVbox.setMaxHeight(720); 
        
//yellow hbox witin black
        HBox yellowHbox = new HBox(10);
        yellowHbox.setAlignment(javafx.geometry.Pos.CENTER);
        yellowHbox.setStyle("-fx-border-color: yellow; -fx-border-width: 2;");

        yellowHbox.setPrefWidth(1280);
        yellowHbox.setPrefHeight(300); 
        yellowHbox.setMaxWidth(1280);
        yellowHbox.setMaxHeight(300); 

        Text gameName = new Text("Kung ano man game name natin");

//orange hbox within black
        HBox orangeHbox = new HBox(10);
        orangeHbox.setStyle("-fx-border-color: orange; -fx-border-width: 2;");

        orangeHbox.setPrefWidth(1280);
        orangeHbox.setPrefHeight(500); 
        orangeHbox.setMaxWidth(1280);
        orangeHbox.setMaxHeight(500); 

//red vbox within orange
        HBox redVbox = new HBox(10);
        redVbox.setAlignment(javafx.geometry.Pos.CENTER);
        redVbox.setStyle("-fx-border-color: red; -fx-border-width: 2;");
        
        redVbox.setPrefWidth(426);
        redVbox.setPrefHeight(400); 
        redVbox.setMaxWidth(426);
        redVbox.setMaxHeight(400); 

//green Vbox within orange
        HBox greenVbox = new HBox(10);
        greenVbox.setAlignment(javafx.geometry.Pos.CENTER);
        greenVbox.setStyle("-fx-border-color: green; -fx-border-width: 2;");

        greenVbox.setPrefWidth(426);
        greenVbox.setPrefHeight(400); 
        greenVbox.setMaxWidth(426);
        greenVbox.setMaxHeight(400); 

//blue Vbox withing orange
        HBox blueVbox = new HBox(10);
        blueVbox.setAlignment(javafx.geometry.Pos.CENTER);
        blueVbox.setStyle("-fx-border-color: blue; -fx-border-width: 2;");

        blueVbox.setPrefWidth(426);
        blueVbox.setPrefHeight(400); 
        blueVbox.setMaxWidth(426);
        blueVbox.setMaxHeight(400); 

        Button startButton = new Button("");
        startButton.setBackground(Background.EMPTY);
        Image image = new Image(getClass().getResource("/assets/textures/newgameButton.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200); 
        imageView.setFitHeight(200); 

        startButton.setGraphic(imageView);
        startButton.setOnAction(e -> {
            getSceneService().pushSubScene(new PlayerSelectScene());
            // getSceneService().pushSubScene(new VideokeScene()); call this when videoke
            // ready
        });

        Button optionButton = new Button("");
        optionButton.setBackground(Background.EMPTY);
        Image optionImage = new Image(getClass().getResource("/assets/textures/optionButton.png").toExternalForm());
        ImageView optionView = new ImageView(optionImage);
        optionView.setFitWidth(200); 
        optionView.setFitHeight(200); 
        optionButton.setGraphic(optionView);
        optionButton.setOnAction(e -> {
            System.out.println("CLICKED OPTIONS");
        });

        Button exitButton = new Button("");
        exitButton.setBackground(Background.EMPTY);
        Image exitImage = new Image(getClass().getResource("/assets/textures/exitButton.png").toExternalForm());
        ImageView exitView = new ImageView(exitImage);
        exitView.setFitWidth(200); 
        exitView.setFitHeight(200); 
        exitButton.setGraphic(exitView);
        exitButton.setOnAction(e -> {
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
