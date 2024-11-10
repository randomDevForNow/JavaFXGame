package com.sunsofgod.Scenes;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Animation {
//bounce animation
    public static void applyContinuousBounceEffect(Node node, double delay) {
        TranslateTransition bounce = new TranslateTransition(Duration.millis(600), node);
        bounce.setByY(-5);
        bounce.setAutoReverse(true);
        bounce.setCycleCount(TranslateTransition.INDEFINITE);
        bounce.setDelay(Duration.millis(delay));
        bounce.play();
    }

//hover effect + media
     public static void applyHoverAndClickEffects(Button button, MediaPlayer hoverMedia, MediaPlayer clickedMedia) {
        button.setOnMouseEntered(e -> {
            hoverMedia.stop();
            hoverMedia.play();
            button.setScaleX(1.1);
            button.setScaleY(1.1);
        });

        button.setOnMouseExited(e -> {
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });

        button.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {  // Check if it's a right-click
               clickedMedia.stop();
               clickedMedia.play();
        }
        });
    }
}
