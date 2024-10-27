package com.sunsofgod.Scenes;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class PlayerSelectScene extends FXGLMenu {

    public PlayerSelectScene() {
        super(MenuType.MAIN_MENU);

        VBox vbox = new VBox(10);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);

        Button pSelectButton = new Button("Create Player");
        pSelectButton.setOnAction(e -> {
            getSceneService().pushSubScene(new LevelSelectScene());
        });

        Button optionsButton = new Button("Options");
        optionsButton.setOnAction(e -> {
            System.out.println("CLICKED OPTIONS");
        });

        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(e -> {
            getSceneService().popSubScene();
        });

        vbox.getChildren().addAll(pSelectButton, optionsButton, goBackButton);

        getContentRoot().getChildren().add(vbox);
    }

}
