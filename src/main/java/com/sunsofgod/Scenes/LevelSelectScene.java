package com.sunsofgod.Scenes;

import static com.almasb.fxgl.dsl.FXGL.getApp;
import static com.almasb.fxgl.dsl.FXGL.getSceneService;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunsofgod.PlatformerApp;

public class LevelSelectScene extends FXGLMenu {

    private int level = 0;
    int playerCounter = 0;
    boolean[] players = ((PlatformerApp) FXGL.getApp()).getPlayers();

    // Path to the JSON file
    String filePath = "src/main/resources/database.json";
    ObjectMapper objectMapper = new ObjectMapper();
    File file = new File(filePath);


    ImageView level1View;
    ImageView level2View;
    ImageView level3View;
    ImageView level4View;

    public LevelSelectScene() {
        super(MenuType.MAIN_MENU);
        System.out.println(Arrays.toString(players)); 

        for (int i = 0; i < 4; i++) {
            if (players[i]) {
                playerCounter++;
            }
        }

        System.out.println("PLAYER COUNT" + playerCounter);



        Image backgroundImage = new Image(
                getClass().getResource("/assets/textures/bgPlayerSelect.png").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(1280);
        backgroundView.setFitHeight(720);
        Media hoverSound = new Media(getClass().getResource("/assets/sounds/hoverSoundfx.mp3").toExternalForm());
        MediaPlayer hoverMedia = new MediaPlayer(hoverSound);
        Media clickedSound = new Media(getClass().getResource("/assets/sounds/clickedSoundfx.mp3").toExternalForm());
        MediaPlayer clickedMedia = new MediaPlayer(clickedSound);
        
        // black vbox
        VBox blackVbox = new VBox(10);

        blackVbox.setPrefWidth(1280);
        blackVbox.setPrefHeight(720);
        blackVbox.setMaxWidth(1280);
        blackVbox.setMaxHeight(720);

        // yellow hbox within black
        HBox yellowHbox = new HBox(10);

        yellowHbox.setPrefWidth(1280);
        yellowHbox.setPrefHeight(80);
        yellowHbox.setMaxWidth(1280);
        yellowHbox.setMaxHeight(60);
        yellowHbox.setPadding(new Insets(20, 0, 0, 40));
        // orange hbox within black
        HBox orangeHbox = new HBox(10);
        orangeHbox.setAlignment(javafx.geometry.Pos.CENTER);

        orangeHbox.setPrefWidth(1280);
        orangeHbox.setPrefHeight(100);
        orangeHbox.setMaxWidth(1280);
        orangeHbox.setMaxHeight(100);

        Text playselectInfo = new Text("Select Level");
        playselectInfo.setFont(Font.font("Tahoma", FontWeight.BOLD, 60));
        playselectInfo.setFill(Color.WHITE);

        Text playselectInfoOutline = new Text("Select Level");
        playselectInfoOutline.setFont(Font.font("Tahoma", FontWeight.BOLD, 60));
        playselectInfoOutline.setFill(Color.TRANSPARENT);
        playselectInfoOutline.setStroke(Color.web("#2d5d8c"));
        playselectInfoOutline.setStrokeWidth(8);
        StackPane textStack = new StackPane();
        // red hbox within black

        // green hbox within black
        HBox greenHbox = new HBox();
        greenHbox.setAlignment(javafx.geometry.Pos.CENTER);

        greenHbox.setPrefWidth(1280);
        greenHbox.setPrefHeight(500);
        greenHbox.setMaxWidth(1280);
        greenHbox.setMaxHeight(500);
        greenHbox.setPadding(new Insets(0, 0, 0, 40));

        // pink vbox within green
        HBox pinkVbox = new HBox(10);
        pinkVbox.setAlignment(javafx.geometry.Pos.CENTER);

        pinkVbox.setPrefWidth(320);
        pinkVbox.setPrefHeight(400);
        pinkVbox.setMaxWidth(320);
        pinkVbox.setMaxHeight(400);
        pinkVbox.setPadding(new Insets(-80, 0, 100, 0));

        // indigo vbox within green
        HBox indigoVbox = new HBox(10);
        indigoVbox.setAlignment(javafx.geometry.Pos.CENTER);
        indigoVbox.setPrefWidth(100);
        indigoVbox.setPrefHeight(400);
        indigoVbox.setMaxWidth(100);
        indigoVbox.setMaxHeight(400);
        HBox.setMargin(indigoVbox, new Insets(0, 50, 0, 0));

        // purple vbox within green
        HBox purpleVbox = new HBox(10);
        purpleVbox.setAlignment(javafx.geometry.Pos.CENTER);
        purpleVbox.setPrefWidth(320);
        purpleVbox.setPrefHeight(400);
        purpleVbox.setMaxWidth(320);
        purpleVbox.setMaxHeight(400);
        purpleVbox.setPadding(new Insets(100, 0, 0, 0));

        // brown vbox within green
        HBox brownVbox = new HBox(10);
        brownVbox.setAlignment(javafx.geometry.Pos.CENTER);
        brownVbox.setPrefWidth(320);
        brownVbox.setPrefHeight(400);
        brownVbox.setMaxWidth(320);
        brownVbox.setMaxHeight(400);
        brownVbox.setPadding(new Insets(-150, 0, 0, 0));

        // cyan hbox within black
        HBox cyanHbox = new HBox(10);
        cyanHbox.setAlignment(javafx.geometry.Pos.CENTER);
        cyanHbox.setPrefWidth(1280);
        cyanHbox.setPrefHeight(60);
        cyanHbox.setMaxWidth(1280);
        cyanHbox.setMaxHeight(60);

        // cyan hbox within cyan
        HBox cyanHbox3 = new HBox(10);
        cyanHbox3.setAlignment(javafx.geometry.Pos.CENTER);
        cyanHbox3.setPrefWidth(250);
        cyanHbox3.setPrefHeight(60);
        cyanHbox3.setMaxWidth(1280);
        cyanHbox3.setMaxHeight(60);
        cyanHbox.setPadding(new Insets(-80, 0, 0, 0));

        Button startgameButton = new Button("");
        startgameButton.setBackground(Background.EMPTY);
        Image image = new Image(
                getClass().getResource("/assets/textures/buttons/startgameButton.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(225);
        imageView.setFitHeight(70);
        startgameButton.setGraphic(imageView);
        Animation.applyHoverAndClickEffects(startgameButton, hoverMedia, clickedMedia);
        startgameButton.setOnAction(e -> {
            startGame();
        });

        Button goBackButton = new Button("");
        goBackButton.setBackground(Background.EMPTY);
        Image backImage = new Image(getClass().getResource("/assets/textures/buttons/backButton.png").toExternalForm());
        ImageView backView = new ImageView(backImage);
        backView.setFitWidth(100);
        backView.setFitHeight(70);
        goBackButton.setGraphic(backView);
        Animation.applyHoverAndClickEffects(goBackButton, hoverMedia, clickedMedia);

        goBackButton.setOnAction(e -> {
            getSceneService().popSubScene();
        });

        Image unclickedlevel1Image = new Image(
                getClass().getResource("/assets/textures/buttons/level1.png").toExternalForm());
        Image clickedlevel1Image = new Image(
                getClass().getResource("/assets/textures/buttons/clickedlevel1.png").toExternalForm());

        Button level1Button = new Button("");
        level1Button.setBackground(Background.EMPTY);
        Image level1Image = new Image(
                getClass().getResource("/assets/textures/buttons/level1.png").toExternalForm());

        level1View = new ImageView(level1Image);
        level1View.setFitWidth(225);
        level1View.setFitHeight(225);
        level1Button.setGraphic(level1View);
        Animation.applyParachuteEffect(level1Button, 0);
        Animation.applyHoverAndClickEffects(level1Button, hoverMedia, clickedMedia);

        Image unclickedlevel2Image = new Image(
                getClass().getResource("/assets/textures/buttons/level2.png").toExternalForm());
        Image clickedlevel2Image = new Image(
                getClass().getResource("/assets/textures/buttons/clickedlevel2.png").toExternalForm());

        Button level2Button = new Button("");
        level2Button.setBackground(Background.EMPTY);
        Image level2Image = new Image(
                getClass().getResource("/assets/textures/buttons/level2.png").toExternalForm());

        level2View = new ImageView(level2Image);
        level2View.setFitWidth(275);
        level2View.setFitHeight(275);
        level2Button.setGraphic(level2View);
        Animation.applyParachuteEffect(level2Button, 1000);
        Animation.applyHoverAndClickEffects(level2Button, hoverMedia, clickedMedia);

        Image unclickedlevel3Image = new Image(
                getClass().getResource("/assets/textures/buttons/level3.png").toExternalForm());
        Image clickedlevel3Image = new Image(
                getClass().getResource("/assets/textures/buttons/clickedlevel3.png").toExternalForm());

        Button level3Button = new Button("");
        level3Button.setBackground(Background.EMPTY);
        Image level3Image = new Image(
                getClass().getResource("/assets/textures/buttons/level3.png").toExternalForm());

        level3View = new ImageView(level3Image);
        level3View.setFitWidth(275);
        level3View.setFitHeight(275);
        level3Button.setGraphic(level3View);
        Animation.applyParachuteEffect(level3Button, 300);
        Animation.applyHoverAndClickEffects(level3Button, hoverMedia, clickedMedia);

        Image unclickedlevel4Image = new Image(
                getClass().getResource("/assets/textures/buttons/level4.png").toExternalForm());
        Image clickedlevel4Image = new Image(
                getClass().getResource("/assets/textures/buttons/clickedlevel4.png").toExternalForm());

        Button level4Button = new Button("");
        level4Button.setBackground(Background.EMPTY);
        Image level4Image = new Image(
                getClass().getResource("/assets/textures/buttons/level4.png").toExternalForm());

        level4View = new ImageView(level4Image);
        level4View.setFitWidth(350);
        level4View.setFitHeight(350);
        level4Button.setGraphic(level4View);
        Animation.applyParachuteEffect(level4Button, 0);
        Animation.applyHoverAndClickEffects(level4Button, hoverMedia, clickedMedia);

        boolean[] level1ImageClicked = { false };
        boolean[] level2ImageClicked = { false };
        boolean[] level3ImageClicked = { false };
        boolean[] level4ImageClicked = { false };

        level1Button.setOnAction(e -> {
            if (level1ImageClicked[0]) {
                level1View.setImage(unclickedlevel1Image);
                checkCompleted();

            } else {
                level = 1;
                
               
                
                level2View.setImage(unclickedlevel2Image);
                level3View.setImage(unclickedlevel3Image);
                level4View.setImage(unclickedlevel4Image);
                level1View.setImage(clickedlevel1Image);
                checkCompletedClicked(1);
                level2ImageClicked[0] = false;
                level3ImageClicked[0] = false;
                level4ImageClicked[0] = false;

            }
            level1ImageClicked[0] = !level1ImageClicked[0];
        });

        level2Button.setOnAction(e -> {
            if (level2ImageClicked[0]) {
                level2View.setImage(unclickedlevel2Image);
                checkCompleted();
                

            } else {

                level = 2;
        
                level1View.setImage(unclickedlevel1Image);
                level3View.setImage(unclickedlevel3Image);
                level4View.setImage(unclickedlevel4Image);
                level2View.setImage(clickedlevel2Image);
                checkCompletedClicked(2);
                level1ImageClicked[0] = false;
                level3ImageClicked[0] = false;
                level4ImageClicked[0] = false;

            }
            level2ImageClicked[0] = !level2ImageClicked[0];
        });

        level3Button.setOnAction(e -> {
            if (level3ImageClicked[0]) {
                level3View.setImage(unclickedlevel3Image);
                checkCompleted();

            } else {

                level = 3;
                
                
                
                level1View.setImage(unclickedlevel1Image);
                level2View.setImage(unclickedlevel2Image);
                level4View.setImage(unclickedlevel4Image);
                level3View.setImage(clickedlevel3Image);
                checkCompletedClicked(3);
                level1ImageClicked[0] = false;
                level2ImageClicked[0] = false;
                level4ImageClicked[0] = false;
                

            }
            level3ImageClicked[0] = !level3ImageClicked[0];
        });

        level4Button.setOnAction(e -> {
            if (level4ImageClicked[0]) {
                level4View.setImage(unclickedlevel4Image);
                checkCompleted();
                

            } else {
                level = 4;

                
                
                level1View.setImage(unclickedlevel1Image);
                level2View.setImage(unclickedlevel2Image);
                level3View.setImage(unclickedlevel3Image);
                level4View.setImage(clickedlevel4Image);
                checkCompletedClicked(4);
                level1ImageClicked[0] = false;
                level2ImageClicked[0] = false;
                level3ImageClicked[0] = false;
                
                

            }
            level4ImageClicked[0] = !level4ImageClicked[0];
        });

        checkCompleted();
        

        Animation.setCustomCursor(getContentRoot(),"/assets/textures/cursor.png");
        getContentRoot().getChildren().add(backgroundView);
        blackVbox.getChildren().addAll(yellowHbox, orangeHbox, greenHbox, cyanHbox);
        greenHbox.getChildren().addAll(pinkVbox, purpleVbox, brownVbox, indigoVbox);
        yellowHbox.getChildren().addAll(goBackButton);
        pinkVbox.getChildren().addAll(level1Button);
        purpleVbox.getChildren().addAll(level2Button);
        indigoVbox.getChildren().addAll(level4Button);
        brownVbox.getChildren().addAll(level3Button);
        orangeHbox.getChildren().addAll(textStack);
        cyanHbox.getChildren().addAll(cyanHbox3);
        cyanHbox3.getChildren().addAll(startgameButton);
        getContentRoot().getChildren().add(blackVbox);
        textStack.getChildren().addAll(playselectInfoOutline, playselectInfo);
    }

    private void checkCompleted(){
            if (playerCounter == 1) {

                for(int i =1 ; i<5; i++){
                    String levelToCheck = "level" + (i); // The level you want to check
                    boolean isLevelTrue = checkLevelStatus("src/main/resources/database.json", levelToCheck);
                    if(isLevelTrue){
                        if(i == 1){
                            level1View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L1_Completed.png").toExternalForm()));
                        }else if(i == 2){
                            level2View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L2_Completed.png").toExternalForm()));
                        }
                        else if(i == 3){
                            level3View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L3_Completed.png").toExternalForm()));
                        }
                        else if(i == 4){
                            level4View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L4_Completed.png").toExternalForm()));
                        }
                    }
                }
                
            } else if (playerCounter  == 2) {
                for(int i=5 ; i<9; i++){
                    String levelToCheck = "level" + (i); // The level you want to check
                    boolean isLevelTrue = checkLevelStatus("src/main/resources/database.json", levelToCheck);
                    if(isLevelTrue){
                        if(i == 5){
                            level1View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L1_Completed.png").toExternalForm()));
                        }else if(i == 6){
                            level2View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L2_Completed.png").toExternalForm()));
                        }
                        else if(i == 7){
                            level3View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L3_Completed.png").toExternalForm()));
                        }
                        else if(i == 8){
                            level4View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L4_Completed.png").toExternalForm()));
                        }
                    }
                }
                
            } else if (playerCounter  == 3) {
                for(int i=9 ; i<13; i++){
                    String levelToCheck = "level" + (i); // The level you want to check
                    boolean isLevelTrue = checkLevelStatus("src/main/resources/database.json", levelToCheck);
                    if(isLevelTrue){
                        if(i == 9){
                            level1View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L1_Completed.png").toExternalForm()));
                        }else if(i == 10){
                            level2View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L2_Completed.png").toExternalForm()));
                        }
                        else if(i == 11){
                            level3View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L3_Completed.png").toExternalForm()));
                        }
                        else if(i == 12){
                            level4View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L4_Completed.png").toExternalForm()));
                        }
                    }
                }
                
            } else if (playerCounter  == 4) {
                for(int i=13 ; i<17; i++){
                    String levelToCheck = "level" + (i); // The level you want to check
                    boolean isLevelTrue = checkLevelStatus("src/main/resources/database.json", levelToCheck);
                    if(isLevelTrue){
                        if(i == 13){
                            level1View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L1_Completed.png").toExternalForm()));
                        }else if(i == 14){
                            level2View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L2_Completed.png").toExternalForm()));
                        }
                        else if(i == 15){
                            level3View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L3_Completed.png").toExternalForm()));
                        }
                        else if(i == 16){
                            level4View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L4_Completed.png").toExternalForm()));
                        }
                    }
                }
            }
        }

       
        //Completed Level Clicked w highlight
        private void checkCompletedClicked(int levelChecker){
            if (playerCounter == 1) {

                for(int i =1 ; i<5; i++){
                    String levelToCheck = "level" + (i); // The level you want to check
                    boolean isLevelTrue = checkLevelStatus("src/main/resources/database.json", levelToCheck);
                    if(isLevelTrue){
                        if(i == 1){
                            if(levelChecker == 1){
                                level1View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L1_Completed_Clicked.png").toExternalForm()));
                            }else{
                                level1View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L1_Completed.png").toExternalForm()));
                            }
                            
                        }else if(i == 2){
                            if(levelChecker == 2){
                                level2View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L2_Completed_Clicked.png").toExternalForm()));
                            }else{
                                level2View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L2_Completed.png").toExternalForm()));
                            }
                        }
                        else if(i == 3){
                            if(levelChecker == 3){
                                level3View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L3_Completed_Clicked.png").toExternalForm()));
                            }else{
                                level3View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L3_Completed.png").toExternalForm()));
                            }
                        }
                        else if(i == 4){
                            if(levelChecker == 4){
                                level4View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L4_Completed_Clicked.png").toExternalForm()));
                            }else{
                                level4View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L4_Completed.png").toExternalForm()));
                            }
                        }
                    }
                }
                
            } else if (playerCounter  == 2) {
                for(int i=5 ; i<9; i++){
                    String levelToCheck = "level" + (i); // The level you want to check
                    boolean isLevelTrue = checkLevelStatus("src/main/resources/database.json", levelToCheck);
                    if(isLevelTrue){
                        if(i == 5){
                            if(levelChecker == 1){
                                level1View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L1_Completed_Clicked.png").toExternalForm()));
                            }else{
                                level1View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L1_Completed.png").toExternalForm()));
                            }
                        }else if(i == 6){
                            if(levelChecker == 2){
                                level2View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L2_Completed_Clicked.png").toExternalForm()));
                            }else{
                                level2View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L2_Completed.png").toExternalForm()));
                            }
                        }
                        else if(i == 7){
                            if(levelChecker == 3){
                                level3View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L3_Completed_Clicked.png").toExternalForm()));
                            }else{
                                level3View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L3_Completed.png").toExternalForm()));
                            }
                        }
                        else if(i == 8){
                            if(levelChecker == 4){
                                level4View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L4_Completed_Clicked.png").toExternalForm()));
                            }else{
                                level4View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L4_Completed.png").toExternalForm()));
                            }
                        }
                    }
                }
                
            } else if (playerCounter  == 3) {
                for(int i=9 ; i<13; i++){
                    String levelToCheck = "level" + (i); // The level you want to check
                    boolean isLevelTrue = checkLevelStatus("src/main/resources/database.json", levelToCheck);
                    if(isLevelTrue){
                        if(i == 9){
                            if(levelChecker == 1){
                                level1View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L1_Completed_Clicked.png").toExternalForm()));
                            }else{
                                level1View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L1_Completed.png").toExternalForm()));
                            }
                        }else if(i == 10){
                            if(levelChecker == 2){
                                level2View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L2_Completed_Clicked.png").toExternalForm()));
                            }else{
                                level2View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L2_Completed.png").toExternalForm()));
                            }
                        }
                        else if(i == 11){
                            if(levelChecker == 3){
                                level3View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L3_Completed_Clicked.png").toExternalForm()));
                            }else{
                                level3View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L3_Completed.png").toExternalForm()));
                            }
                        }
                        else if(i == 12){
                            if(levelChecker == 4){
                                level4View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L4_Completed_Clicked.png").toExternalForm()));
                            }else{
                                level4View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L4_Completed.png").toExternalForm()));
                            }
                        }
                    }
                }
                
            } else if (playerCounter  == 4) {
                for(int i=13 ; i<17; i++){
                    String levelToCheck = "level" + (i); // The level you want to check
                    boolean isLevelTrue = checkLevelStatus("src/main/resources/database.json", levelToCheck);
                    if(isLevelTrue){
                        if(i == 13){
                            if(levelChecker == 1){
                                level1View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L1_Completed_Clicked.png").toExternalForm()));
                            }else{
                                level1View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L1_Completed.png").toExternalForm()));
                            }
                        }else if(i == 14){
                            if(levelChecker == 2){
                                level2View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L2_Completed_Clicked.png").toExternalForm()));
                            }else{
                                level2View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L2_Completed.png").toExternalForm()));
                            }
                        }
                        else if(i == 15){
                            if(levelChecker == 3){
                                level3View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L3_Completed_Clicked.png").toExternalForm()));
                            }else{
                                level3View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L3_Completed.png").toExternalForm()));
                            }
                        }
                        else if(i == 16){
                            if(levelChecker == 4){
                                level4View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L4_Completed_Clicked.png").toExternalForm()));
                            }else{
                                level4View.setImage(new Image(getClass().getResource("/assets/textures/buttons/L4_Completed.png").toExternalForm()));
                            }
                        }
                    }
                }
            }
        }

        

    public static boolean checkLevelStatus(String filePath, String level) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Read the JSON file into a JsonNode
            JsonNode rootNode = objectMapper.readTree(new File(filePath));
            
            // Check if the level exists and is true
            JsonNode levelNode = rootNode.get(level);
            return levelNode != null && levelNode.asBoolean();
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Return false if there's an error reading the file
        }
    }

    private void startGame() {
        
        if (level != 0) {
            ((PlatformerApp) FXGL.getApp()).setLevelNum(level);
            level = 0;
            fireNewGame();
        }
    }

}
