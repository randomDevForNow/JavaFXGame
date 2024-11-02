package com.sunsofgod.Scenes;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LevelSelectScene extends FXGLMenu {

    public LevelSelectScene() {
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

//yellow hbox within black
        HBox yellowHbox = new HBox(10);
        yellowHbox.setStyle("-fx-border-color: yellow; -fx-border-width: 2;");

        yellowHbox.setPrefWidth(1280);
        yellowHbox.setPrefHeight(60); 
        yellowHbox.setMaxWidth(1280);
        yellowHbox.setMaxHeight(60); 

//red2 hbox within black
        HBox red2Hbox = new HBox(10);
        red2Hbox.setStyle("-fx-border-color: red; -fx-border-width: 2;");

        red2Hbox.setPrefWidth(700);
        red2Hbox.setPrefHeight(60); 
        red2Hbox.setMaxWidth(700);
        red2Hbox.setMaxHeight(60); 

//red hbox within black
        HBox redHbox = new HBox(10);
        redHbox.setStyle("-fx-border-color: red; -fx-border-width: 2;");

        redHbox.setPrefWidth(80);
        redHbox.setPrefHeight(60); 
        redHbox.setMaxWidth(80);
        redHbox.setMaxHeight(60); 


//orange hbox within black
        VBox orangeHbox = new VBox(10);
        orangeHbox.setStyle("-fx-border-color: orange; -fx-border-width: 2;");

        orangeHbox.setPrefWidth(1280);
        orangeHbox.setPrefHeight(600); 
        orangeHbox.setMaxWidth(1280);
        orangeHbox.setMaxHeight(800); 
        VBox.setMargin(orangeHbox, new Insets(50, 50, 0, 50)); 
        orangeHbox.setPadding(new Insets(10, 10, 10, 10));
//green hbox within black
        HBox greenHbox = new HBox(10);
        greenHbox.setStyle("-fx-border-color: green; -fx-border-width: 2;");
        orangeHbox.setPadding(new Insets(20, 20, 20, 20));
        greenHbox.setPrefWidth(1280);
        greenHbox.setPrefHeight(250); 
        greenHbox.setMaxWidth(1280);
        greenHbox.setMaxHeight(250); 

//green hbox within green
        HBox greenHbox2 = new HBox(10);
        greenHbox2.setStyle("-fx-border-color: green; -fx-border-width: 2;");
        greenHbox2.setPrefWidth(1280);
        greenHbox2.setPrefHeight(250); 
        greenHbox2.setMaxWidth(1280);
        greenHbox2.setMaxHeight(250); 

//cyan hbox within green
        HBox cyanHbox = new HBox(10);
        cyanHbox.setStyle("-fx-border-color: cyan; -fx-border-width: 2;");

        cyanHbox.setPrefWidth(320);
        cyanHbox.setPrefHeight(250); 
        cyanHbox.setMaxWidth(320);
        cyanHbox.setMaxHeight(250); 

//cyan hbox within green
        HBox cyanHbox2 = new HBox(10);
        cyanHbox2.setStyle("-fx-border-color: cyan; -fx-border-width: 2;");

        cyanHbox2.setPrefWidth(320);
        cyanHbox2.setPrefHeight(250); 
        cyanHbox2.setMaxWidth(320);
        cyanHbox2.setMaxHeight(250); 

//cyan hbox within green
        HBox cyanHbox3 = new HBox(10);
        cyanHbox3.setStyle("-fx-border-color: cyan; -fx-border-width: 2;");

        cyanHbox3.setPrefWidth(320);
        cyanHbox3.setPrefHeight(250); 
        cyanHbox3.setMaxWidth(320);
        cyanHbox3.setMaxHeight(250); 

//cyan hbox within green
        HBox cyanHbox4 = new HBox(10);
        cyanHbox4.setStyle("-fx-border-color: cyan; -fx-border-width: 2;");

        cyanHbox4.setPrefWidth(320);
        cyanHbox4.setPrefHeight(250); 
        cyanHbox4.setMaxWidth(320);
        cyanHbox4.setMaxHeight(250); 

//cyan hbox within green2
        HBox cyanHbox5 = new HBox(10);
        cyanHbox5.setStyle("-fx-border-color: cyan; -fx-border-width: 2;");

        cyanHbox5.setPrefWidth(320);
        cyanHbox5.setPrefHeight(250); 
        cyanHbox5.setMaxWidth(320);
        cyanHbox5.setMaxHeight(250); 

//cyan hbox within green2
        HBox cyanHbox6 = new HBox(10);
        cyanHbox6.setStyle("-fx-border-color: cyan; -fx-border-width: 2;");

        cyanHbox6.setPrefWidth(320);
        cyanHbox6.setPrefHeight(250); 
        cyanHbox6.setMaxWidth(320);
        cyanHbox6.setMaxHeight(250); 

//cyan hbox within green2
        HBox cyanHbox7 = new HBox(10);
        cyanHbox7.setStyle("-fx-border-color: cyan; -fx-border-width: 2;");

        cyanHbox7.setPrefWidth(320);
        cyanHbox7.setPrefHeight(250); 
        cyanHbox7.setMaxWidth(320);
        cyanHbox7.setMaxHeight(250); 

//cyan hbox within green2
        HBox cyanHbox8 = new HBox(10);
        cyanHbox8.setStyle("-fx-border-color: cyan; -fx-border-width: 2;");

        cyanHbox8.setPrefWidth(320);
        cyanHbox8.setPrefHeight(250); 
        cyanHbox8.setMaxWidth(320);
        cyanHbox8.setMaxHeight(250); 
        
        Button level1 = new Button("");
        level1.setBackground(Background.EMPTY);
        Image level1Image = new Image(getClass().getResource("/assets/textures/levelPlaceholder.png").toExternalForm());
        ImageView level1View = new ImageView(level1Image);
        level1View.setFitWidth(253); 
        level1View.setFitHeight(250); 

        level1.setGraphic(level1View);
        level1.setOnAction(e -> {
            fireNewGame();
        
        });

        Button level2 = new Button("");
        level2.setBackground(Background.EMPTY);
        Image level2Image = new Image(getClass().getResource("/assets/textures/levelPlaceholder.png").toExternalForm());
        ImageView level2View = new ImageView(level2Image);
        level2View.setFitWidth(253); 
        level2View.setFitHeight(250); 

        level2.setGraphic(level2View);
        level2.setOnAction(e -> {
            fireNewGame();
        
        });
        
        Button level3 = new Button("");
        level3.setBackground(Background.EMPTY);
        Image level3Image = new Image(getClass().getResource("/assets/textures/levelPlaceholder.png").toExternalForm());
        ImageView level3View = new ImageView(level3Image);
        level3View.setFitWidth(253); 
        level3View.setFitHeight(250); 

        level3.setGraphic(level3View);
        level3.setOnAction(e -> {
            fireNewGame();
        
        });

        Button level4 = new Button("");
        level4.setBackground(Background.EMPTY);
        Image level4Image = new Image(getClass().getResource("/assets/textures/levelPlaceholder.png").toExternalForm());
        ImageView level4View = new ImageView(level4Image);
        level4View.setFitWidth(253); 
        level4View.setFitHeight(250); 

        level4.setGraphic(level4View);
        level4.setOnAction(e -> {
            fireNewGame();
        
        });

        Button level5 = new Button("");
        level5.setBackground(Background.EMPTY);
        Image level5Image = new Image(getClass().getResource("/assets/textures/levelPlaceholder.png").toExternalForm());
        ImageView level5View = new ImageView(level5Image);
        level5View.setFitWidth(253); 
        level5View.setFitHeight(250); 

        level5.setGraphic(level5View);
        level5.setOnAction(e -> {
            fireNewGame();
        
        });

        Button level6 = new Button("");
        level6.setBackground(Background.EMPTY);
        Image level6Image = new Image(getClass().getResource("/assets/textures/levelPlaceholder.png").toExternalForm());
        ImageView level6View = new ImageView(level6Image);
        level6View.setFitWidth(253); 
        level6View.setFitHeight(250); 

        level6.setGraphic(level6View);
        level6.setOnAction(e -> {
            fireNewGame();
        
        });

        Button level7 = new Button("");
        level7.setBackground(Background.EMPTY);
        Image level7Image = new Image(getClass().getResource("/assets/textures/levelPlaceholder.png").toExternalForm());
        ImageView level7View = new ImageView(level7Image);
        level7View.setFitWidth(253); 
        level7View.setFitHeight(250); 

        level7.setGraphic(level7View);
        level7.setOnAction(e -> {
            fireNewGame();
        
        });

        Button level8 = new Button("");
        level8.setBackground(Background.EMPTY);
        Image level8Image = new Image(getClass().getResource("/assets/textures/levelPlaceholder.png").toExternalForm());
        ImageView level8View = new ImageView(level8Image);
        level8View.setFitWidth(253); 
        level8View.setFitHeight(250); 

        level8.setGraphic(level8View);
        level8.setOnAction(e -> {
            fireNewGame();
        
        });
        Button pSelectButton = new Button("Start Game");
        pSelectButton.setOnAction(e -> {
            fireNewGame();
        });


        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(e -> {
            getSceneService().popSubScene();
        });
        getContentRoot().getChildren().add(backgroundView); 
        blackVbox.getChildren().addAll(yellowHbox,orangeHbox,pSelectButton);
        yellowHbox.getChildren().addAll(redHbox,red2Hbox);
        redHbox.getChildren().addAll(goBackButton);
        orangeHbox.getChildren().addAll(greenHbox,greenHbox2);
        greenHbox.getChildren().addAll(cyanHbox,cyanHbox2,cyanHbox3,cyanHbox4);
        greenHbox2.getChildren().addAll(cyanHbox5,cyanHbox6,cyanHbox7,cyanHbox8);
        
        cyanHbox.getChildren().addAll(level1);
        cyanHbox2.getChildren().addAll(level2);
        cyanHbox3.getChildren().addAll(level3);
        cyanHbox4.getChildren().addAll(level4);
        cyanHbox5.getChildren().addAll(level5);
        cyanHbox6.getChildren().addAll(level6);
        cyanHbox7.getChildren().addAll(level7);
        cyanHbox8.getChildren().addAll(level8);
        getContentRoot().getChildren().add(blackVbox);
    }

}
