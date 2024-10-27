package com.sunsofgod.Scenes;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class LevelSelectScene extends FXGLMenu {

    public LevelSelectScene() {
        super(MenuType.MAIN_MENU);

        VBox vbox = new VBox(10);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);

        Button pSelectButton = new Button("Start Game");
        pSelectButton.setOnAction(e -> {
            fireNewGame();
        });

        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(e -> {
            getSceneService().popSubScene();
        });

        vbox.getChildren().addAll(pSelectButton, goBackButton);

        getContentRoot().getChildren().add(vbox);
    }

}
