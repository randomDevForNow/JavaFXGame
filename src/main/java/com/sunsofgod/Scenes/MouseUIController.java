package com.sunsofgod.Scenes;

import java.util.List;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.sunsofgod.PlatformerApp;
import com.sunsofgod.PlayerComponent;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MouseUIController {
    @FXML
    private Button leftButton;

    @FXML
    private Button rightButton;

    @FXML
    private AnchorPane rootPane;

    private Entity player;
    private List<Entity> button;
    private PlatformerApp platformerApp;

    private AnimationTimer leftButtonTimer;
    private AnimationTimer rightButtonTimer;
    private boolean isMouseOverLeftButton = false;
    private boolean isMouseOverRightButton = false;

    public void setPlayer(Entity player) {
        this.player = player;
    }

    public void setButton(List<Entity> button) {
        this.button = button;
    }

    @FXML
    private void initialize() {
        platformerApp = (PlatformerApp) FXGL.getApp();

        leftButton.setPrefSize(100, 100);
        rightButton.setPrefSize(100, 100);

        leftButton.setFocusTraversable(false);
        rightButton.setFocusTraversable(false);

        leftButton.setOnMouseEntered(event -> {
            isMouseOverLeftButton = true;
            if (!platformerApp.getDialogShown()) {
                player.getComponent(PlayerComponent.class).left();
            } else {
                player.getComponent(PlayerComponent.class).stop();
            }

            leftButtonTimer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (isMouseOverLeftButton && !platformerApp.getDialogShown()) {
                        player.getComponent(PlayerComponent.class).left();
                    }
                }
            };
            leftButtonTimer.start();
        });

        leftButton.setOnMouseExited(event -> {
            isMouseOverLeftButton = false;
            // Stop the left button action
            if (leftButtonTimer != null) {
                leftButtonTimer.stop();
            }
            player.getComponent(PlayerComponent.class).stop();
        });

        rightButton.setOnMouseEntered(event -> {
            isMouseOverRightButton = true;
            if (!platformerApp.getDialogShown()) {
                player.getComponent(PlayerComponent.class).right();
            } else {
                player.getComponent(PlayerComponent.class).stop();
            }

            rightButtonTimer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (isMouseOverRightButton && !platformerApp.getDialogShown()) {
                        player.getComponent(PlayerComponent.class).right();
                    }
                }
            };
            rightButtonTimer.start();
        });

        rightButton.setOnMouseExited(event -> {
            isMouseOverRightButton = false;
            if (rightButtonTimer != null) {
                rightButtonTimer.stop();
            }
            player.getComponent(PlayerComponent.class).stop();
        });

        rootPane.addEventFilter(MouseEvent.MOUSE_PRESSED, this::uiClick);
    }

    @FXML
    private void uiClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            player.getComponent(PlayerComponent.class).jump();
        } else if (event.getButton() == MouseButton.SECONDARY) {
            button.stream()
                    .filter(btn -> btn.hasComponent(CollidableComponent.class) && player.isColliding(btn))
                    .forEach(btn -> {
                        platformerApp.finishLevel();
                    });
        }
        event.consume();
    }

}
