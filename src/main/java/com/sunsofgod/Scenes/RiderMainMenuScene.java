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

public class RiderMainMenuScene extends FXGLMenu {

    public RiderMainMenuScene() {
        super(MenuType.MAIN_MENU);

        VBox vbox = new VBox(10);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);

        // Create buttons
        Button startButton = new Button("Start New Game");
        startButton.setOnAction(e -> {
            getSceneService().pushSubScene(new PlayerSelectScene());
            // getSceneService().pushSubScene(new VideokeScene()); call this when videoke
            // ready
        });

        Button optionsButton = new Button("Options");
        optionsButton.setOnAction(e -> {
            System.out.println("CLICKED OPTIONS");
        });

        Button quitButton = new Button("Quit Game");
        quitButton.setOnAction(e -> {
            System.exit(0);
        });

        vbox.getChildren().addAll(startButton, optionsButton, quitButton);

        getContentRoot().getChildren().add(vbox);

        // TO SET BACKGROUND, getRoot().getChildren().add(IMAGEVIEW OR KAHIT ANO NA
        // PANLAGAY CLIPPING ETC.)
        // Pede rin magsearch ng ano kung pano yung nagalaw na background na wallpaper
        // ng mga parcel tapos stripes background
    }
}
