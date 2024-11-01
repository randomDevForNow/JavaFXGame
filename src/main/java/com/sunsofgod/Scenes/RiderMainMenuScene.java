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
import javafx.scene.paint.Color;

public class RiderMainMenuScene extends FXGLMenu {

    public RiderMainMenuScene() {
        super(MenuType.MAIN_MENU);

        VBox vbox = new VBox(10);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);

        Button startButton = new Button("");
        startButton.setBackground(Background.EMPTY);
        Image image = new Image(getClass().getResource("/assets/textures/newgameButton.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(250); 
        imageView.setFitHeight(250); 

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
        optionView.setFitWidth(250); 
        optionView.setFitHeight(250); 
        optionButton.setGraphic(optionView);
        optionButton.setOnAction(e -> {
            System.out.println("CLICKED OPTIONS");
        });

        Button exitButton = new Button("");
        exitButton.setBackground(Background.EMPTY);
        Image exitImage = new Image(getClass().getResource("/assets/textures/exitButton.png").toExternalForm());
        ImageView exitView = new ImageView(exitImage);
        exitView.setFitWidth(250); 
        exitView.setFitHeight(250); 
        exitButton.setGraphic(exitView);
        exitButton.setOnAction(e -> {
            System.exit(0);
        });

        vbox.getChildren().addAll(startButton, optionButton, exitButton);

        getContentRoot().getChildren().add(vbox);

        // TO SET BACKGROUND, getRoot().getChildren().add(IMAGEVIEW OR KAHIT ANO NA
        // PANLAGAY CLIPPING ETC.)
        // Pede rin magsearch ng ano kung pano yung nagalaw na background na wallpaper
        // ng mga parcel tapos stripes background
    }
}
