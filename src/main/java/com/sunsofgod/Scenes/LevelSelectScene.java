package com.sunsofgod.Scenes;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;

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
import com.fasterxml.jackson.databind.ObjectMapper;

public class LevelSelectScene extends FXGLMenu {

    // Path to the JSON file
    String filePath = "src/main/resources/database.json";
    ObjectMapper objectMapper = new ObjectMapper();
    File file = new File(filePath);

    public LevelSelectScene() {
        
        super(MenuType.MAIN_MENU);
        Image backgroundImage = new Image(getClass().getResource("/assets/textures/bgPlayerSelect.png").toExternalForm());
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
        yellowHbox.setPadding(new Insets(20,0,0,40));
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
        Animation.applyContinuousBounceEffect(textStack, 250);
        // red hbox within black
      
        // green hbox within black
        HBox greenHbox = new HBox();
        greenHbox.setAlignment(javafx.geometry.Pos.CENTER);

        greenHbox.setPrefWidth(1280);
        greenHbox.setPrefHeight(500);
        greenHbox.setMaxWidth(1280);
        greenHbox.setMaxHeight(500);
        greenHbox.setPadding(new Insets(0,0,0,40));

        // pink vbox within green
        HBox pinkVbox = new HBox(10);
        pinkVbox.setAlignment(javafx.geometry.Pos.CENTER);

        pinkVbox.setPrefWidth(320);
        pinkVbox.setPrefHeight(400);
        pinkVbox.setMaxWidth(320);
        pinkVbox.setMaxHeight(400);
        pinkVbox.setPadding(new Insets(-80,0,100,0));

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
        purpleVbox.setPadding(new Insets(100,0,0,0));

        // brown vbox within green
        HBox brownVbox = new HBox(10);
        brownVbox.setAlignment(javafx.geometry.Pos.CENTER);
        brownVbox.setPrefWidth(320);
        brownVbox.setPrefHeight(400);
        brownVbox.setMaxWidth(320);
        brownVbox.setMaxHeight(400);
        brownVbox.setPadding(new Insets(-150,0,0,0));


        
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
        cyanHbox.setPadding(new Insets(-80,0,0,0));
    

 

        //level8.setGraphic(level8View);
        //level8.setOnAction(e -> {
        //    fireNewGame();
        
        //});
        
        Button startgameButton = new Button("");
        startgameButton.setBackground(Background.EMPTY);
        Image image = new Image(getClass().getResource("/assets/textures/buttons/startgameButton.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(225);
        imageView.setFitHeight(70);
        startgameButton.setGraphic(imageView);
        Animation.applyContinuousBounceEffect(startgameButton, 450);
        Animation.applyHoverAndClickEffects(startgameButton, hoverMedia, clickedMedia);
        startgameButton.setOnAction(e -> {
            fireNewGame();
        });


        Button goBackButton = new Button("");
        goBackButton.setBackground(Background.EMPTY);
        Image backImage = new Image(getClass().getResource("/assets/textures/buttons/backButton.png").toExternalForm());
        ImageView backView = new ImageView(backImage);
        backView.setFitWidth(100);
        backView.setFitHeight(70);
        goBackButton.setGraphic(backView);
        Animation.applyContinuousBounceEffect(goBackButton, 0);
        Animation.applyHoverAndClickEffects(goBackButton, hoverMedia, clickedMedia);

        goBackButton.setOnAction(e -> {
            getSceneService().popSubScene();
        });

        Image unclickedlevel1Image = new Image(
            getClass().getResource("/assets/textures/buttons/level1.png").toExternalForm());
        Image clickedlevel1Image = new Image(
            getClass().getResource("/assets/textures/buttons/level3.png").toExternalForm());

        Button level1Button = new Button("");
        level1Button.setBackground(Background.EMPTY);
        Image level1Image = new Image(
        getClass().getResource("/assets/textures/buttons/level1.png").toExternalForm());

        ImageView level1View = new ImageView(level1Image);
        level1View.setFitWidth(225);
        level1View.setFitHeight(225);
        level1Button.setGraphic(level1View);
        Animation.applyContinuousBounceEffect(level1Button, 0);
        Animation.applyHoverAndClickEffects(level1Button, hoverMedia, clickedMedia);

        Image unclickedlevel2Image = new Image(
            getClass().getResource("/assets/textures/buttons/level2.png").toExternalForm());
        Image clickedlevel2Image = new Image(
            getClass().getResource("/assets/textures/buttons/level4.png").toExternalForm());

        Button level2Button = new Button("");
        level2Button.setBackground(Background.EMPTY);
        Image level2Image = new Image(
        getClass().getResource("/assets/textures/buttons/level2.png").toExternalForm());

        ImageView level2View = new ImageView(level2Image);
        level2View.setFitWidth(275);
        level2View.setFitHeight(275);
        level2Button.setGraphic(level2View);
        Animation.applyContinuousBounceEffect(level2Button, 0);
        Animation.applyHoverAndClickEffects(level2Button, hoverMedia, clickedMedia);

        Image unclickedlevel3Image = new Image(
            getClass().getResource("/assets/textures/buttons/level3.png").toExternalForm());
        Image clickedlevel3Image = new Image(
            getClass().getResource("/assets/textures/buttons/level1.png").toExternalForm());

        Button level3Button = new Button("");
        level3Button.setBackground(Background.EMPTY);
        Image level3Image = new Image(
        getClass().getResource("/assets/textures/buttons/level3.png").toExternalForm());

        ImageView level3View = new ImageView(level3Image);
        level3View.setFitWidth(275);
        level3View.setFitHeight(275);
        level3Button.setGraphic(level3View);
        Animation.applyContinuousBounceEffect(level3Button, 0);
        Animation.applyHoverAndClickEffects(level3Button, hoverMedia, clickedMedia);

        Image unclickedlevel4Image = new Image(
            getClass().getResource("/assets/textures/buttons/level4.png").toExternalForm());
        Image clickedlevel4Image = new Image(
            getClass().getResource("/assets/textures/buttons/level2.png").toExternalForm());

        Button level4Button = new Button("");
        level4Button.setBackground(Background.EMPTY);
        Image level4Image = new Image(
        getClass().getResource("/assets/textures/buttons/level4.png").toExternalForm());

        ImageView level4View = new ImageView(level4Image);
        level4View.setFitWidth(350);
        level4View.setFitHeight(350);
        level4Button.setGraphic(level4View);
        Animation.applyContinuousBounceEffect(level4Button, 0);
        Animation.applyHoverAndClickEffects(level4Button, hoverMedia, clickedMedia);

        boolean[] level1ImageClicked = { false };
        boolean[] level2ImageClicked = { false };
        boolean[] level3ImageClicked = { false };
        boolean[] level4ImageClicked = { false };

        level1Button.setOnAction(e -> {
            if (level1ImageClicked[0]) {
                level1View.setImage(unclickedlevel1Image);
                System.out.println("Unclicked level1");

                try {

                Map<String, Boolean> jsonMap = objectMapper.readValue(file, new TypeReference<Map<String, Boolean>>() {});
                jsonMap.put("level1", false);
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, jsonMap);
                System.out.println("Updated JSON: " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap));

                } catch (IOException s) {
                    s.printStackTrace();
                }
                
                
            } else {
                level1View.setImage(clickedlevel1Image);
                System.out.println("Clicked level1");
                level2View.setImage(unclickedlevel2Image);
                level3View.setImage(unclickedlevel3Image);
                level4View.setImage(unclickedlevel4Image);
                level2ImageClicked[0] = false;
                level3ImageClicked[0] = false;
                level4ImageClicked[0] = false;

                try {

                    Map<String, Boolean> jsonMap = objectMapper.readValue(file, new TypeReference<Map<String, Boolean>>() {});
                    jsonMap.put("level1", true);
                    jsonMap.put("level2", false);
                    jsonMap.put("level3", false);
                    jsonMap.put("level4", false);
                    objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, jsonMap);
                    System.out.println("Updated JSON: " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap));

                    } catch (IOException s) {
                        s.printStackTrace();
                    }
            }
            level1ImageClicked[0] = !level1ImageClicked[0];
        });

        
        level2Button.setOnAction(e -> {
            if (level2ImageClicked[0]) {
                level2View.setImage(unclickedlevel2Image);
                System.out.println("Unclicked level2");
                
                

                try {

                Map<String, Boolean> jsonMap = objectMapper.readValue(file, new TypeReference<Map<String, Boolean>>() {});
                jsonMap.put("level2", false);
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, jsonMap);
                System.out.println("Updated JSON: " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap));

                } catch (IOException s) {
                    s.printStackTrace();
                }
                
                
            } else {
                level2View.setImage(clickedlevel2Image);
                System.out.println("Clicked level2");
                level1View.setImage(unclickedlevel1Image);
                level3View.setImage(unclickedlevel3Image);
                level4View.setImage(unclickedlevel4Image);
                level1ImageClicked[0] = false;
                level3ImageClicked[0] = false;
                level4ImageClicked[0] = false;

                try {

                    Map<String, Boolean> jsonMap = objectMapper.readValue(file, new TypeReference<Map<String, Boolean>>() {});
                    jsonMap.put("level1", false);
                    jsonMap.put("level2", true);
                    jsonMap.put("level3", false);
                    jsonMap.put("level4", false);
                    objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, jsonMap);
                    System.out.println("Updated JSON: " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap));

                    } catch (IOException s) {
                        s.printStackTrace();
                    }
            }
            level2ImageClicked[0] = !level2ImageClicked[0];
        });

        level3Button.setOnAction(e -> {
            if (level3ImageClicked[0]) {
                level3View.setImage(unclickedlevel3Image);
                System.out.println("Unclicked level3");
                
                
                try {

                Map<String, Boolean> jsonMap = objectMapper.readValue(file, new TypeReference<Map<String, Boolean>>() {});
                jsonMap.put("level3", false);
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, jsonMap);
                System.out.println("Updated JSON: " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap));

                } catch (IOException s) {
                    s.printStackTrace();
                }
                
                
            } else {
                level3View.setImage(clickedlevel3Image);
                System.out.println("Clicked level3");
                level1View.setImage(unclickedlevel1Image);
                level2View.setImage(unclickedlevel2Image);
                level4View.setImage(unclickedlevel4Image);
                level1ImageClicked[0] = false;
                level2ImageClicked[0] = false;
                level4ImageClicked[0] = false;

                try {

                    Map<String, Boolean> jsonMap = objectMapper.readValue(file, new TypeReference<Map<String, Boolean>>() {});
                    jsonMap.put("level1", false);
                    jsonMap.put("level2", false);
                    jsonMap.put("level3", true);
                    jsonMap.put("level4", false);
                    objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, jsonMap);
                    System.out.println("Updated JSON: " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap));

                    } catch (IOException s) {
                        s.printStackTrace();
                    }
            }
            level3ImageClicked[0] = !level3ImageClicked[0];
        });

        level4Button.setOnAction(e -> {
            if (level4ImageClicked[0]) {
                level4View.setImage(unclickedlevel4Image);
                System.out.println("Unclicked level4");
                
                
                try {

                Map<String, Boolean> jsonMap = objectMapper.readValue(file, new TypeReference<Map<String, Boolean>>() {});
                jsonMap.put("level4", false);
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, jsonMap);
                System.out.println("Updated JSON: " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap));

                } catch (IOException s) {
                    s.printStackTrace();
                }
                
                
            } else {
                level4View.setImage(clickedlevel4Image);
                System.out.println("Clicked level4");
                level1View.setImage(unclickedlevel1Image);
                level2View.setImage(unclickedlevel2Image);
                level3View.setImage(unclickedlevel3Image);
                level1ImageClicked[0] = false;
                level2ImageClicked[0] = false;
                level3ImageClicked[0] = false;

                try {

                    Map<String, Boolean> jsonMap = objectMapper.readValue(file, new TypeReference<Map<String, Boolean>>() {});
                    jsonMap.put("level1", false);
                    jsonMap.put("level2", false);
                    jsonMap.put("level3", false);
                    jsonMap.put("level4", true);
                    objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, jsonMap);
                    System.out.println("Updated JSON: " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap));

                    } catch (IOException s) {
                        s.printStackTrace();
                    }
            }
            level4ImageClicked[0] = !level4ImageClicked[0];
        });


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

}
