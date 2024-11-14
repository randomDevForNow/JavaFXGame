package com.sunsofgod.Scenes;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
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
            if (e.getButton() == MouseButton.PRIMARY) {  
               clickedMedia.stop();
               clickedMedia.play();
        }
        });
    }
         public static void applyParachuteEffect(Node node, double speed) {
       
        TranslateTransition bounce = new TranslateTransition(Duration.millis(1200), node);
        bounce.setByY(-20); 
        bounce.setAutoReverse(true); 
        bounce.setCycleCount(TranslateTransition.INDEFINITE); 
        bounce.setDelay(Duration.millis(0)); 
        bounce.play();

     
        RotateTransition tilt = new RotateTransition(Duration.millis(800), node);
        tilt.setByAngle(4); 
        tilt.setAutoReverse(true); 
        tilt.setCycleCount(RotateTransition.INDEFINITE);
        tilt.setDelay(Duration.millis(800)); 
        tilt.play();
    }

      public static void setCustomCursor(Pane pane, String cursorImagePath) {
        Image cursorImage = new Image(Animation.class.getResource("/assets/textures/cursor.png").toExternalForm());
        ImageCursor customCursor = new ImageCursor(cursorImage);
        pane.setCursor(customCursor);
    }

}
