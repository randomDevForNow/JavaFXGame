package com.sunsofgod.Scenes;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PlayerSelectScene extends FXGLMenu {

    public PlayerSelectScene() {
        super(MenuType.MAIN_MENU);
        Image backgroundImage = new Image(getClass().getResource("/assets/textures/bgPlayerSelect.png").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(1280); 
        backgroundView.setFitHeight(720); 
//black vbox
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
 
//orange hbox within black
        HBox orangeHbox = new HBox(10);
        orangeHbox.setStyle("-fx-border-color: orange; -fx-border-width: 2;");
        orangeHbox.setAlignment(javafx.geometry.Pos.CENTER);

        orangeHbox.setPrefWidth(1280);
        orangeHbox.setPrefHeight(100); 
        orangeHbox.setMaxWidth(1280);
        orangeHbox.setMaxHeight(100); 
        Text playselectInfo = new Text("INFO HERE ---------------------------");

//red hbox within black
        HBox redHbox = new HBox(10);
        redHbox.setStyle("-fx-border-color: red; -fx-border-width: 2;");
        redHbox.setAlignment(javafx.geometry.Pos.CENTER);

        redHbox.setPrefWidth(1280);
        redHbox.setPrefHeight(100); 
        redHbox.setMaxWidth(1280);
        redHbox.setMaxHeight(100); 
        Text playselectInfo2 = new Text("INFO HERE ---------------------------");
//green hbox within black
        HBox greenHbox = new HBox(10);
        greenHbox.setStyle("-fx-border-color: green; -fx-greenborder-width: 2;");

        greenHbox.setPrefWidth(1280);
        greenHbox.setPrefHeight(500); 
        greenHbox.setMaxWidth(1280);
        greenHbox.setMaxHeight(500); 

//pink vbox within green
        HBox pinkVbox = new HBox(10);
        pinkVbox.setAlignment(javafx.geometry.Pos.CENTER);
        pinkVbox.setStyle("-fx-border-color: pink; -fx-border-width: 2;");
        
        pinkVbox.setPrefWidth(320);
        pinkVbox.setPrefHeight(400); 
        pinkVbox.setMaxWidth(320);
        pinkVbox.setMaxHeight(400); 

//indigo vbox within green
        HBox indigoVbox = new HBox(10);
        indigoVbox.setAlignment(javafx.geometry.Pos.CENTER);
        indigoVbox.setStyle("-fx-border-color: indigo; -fx-border-width: 2;");

        indigoVbox.setPrefWidth(320);
        indigoVbox.setPrefHeight(400); 
        indigoVbox.setMaxWidth(320);
        indigoVbox.setMaxHeight(400); 

//purple vbox within green
        HBox purpleVbox = new HBox(10);
        purpleVbox.setAlignment(javafx.geometry.Pos.CENTER);
        purpleVbox.setStyle("-fx-border-color: purple; -fx-border-width: 2;");

        purpleVbox.setPrefWidth(320);
        purpleVbox.setPrefHeight(400); 
        purpleVbox.setMaxWidth(320);
        purpleVbox.setMaxHeight(400); 

//brown vbox within green
        HBox brownVbox = new HBox(10);
        brownVbox.setAlignment(javafx.geometry.Pos.CENTER);
        brownVbox.setStyle("-fx-border-color: brown; -fx-border-width: 2;");

        brownVbox.setPrefWidth(320);
        brownVbox.setPrefHeight(400); 
        brownVbox.setMaxWidth(320);
        brownVbox.setMaxHeight(400); 

//cyan hbox within black
        HBox cyanHbox = new HBox(10);
        cyanHbox.setStyle("-fx-border-color: cyan; -fx-border-width: 2;");
        cyanHbox.setAlignment(javafx.geometry.Pos.CENTER);
        cyanHbox.setPrefWidth(1280);
        cyanHbox.setPrefHeight(60); 
        cyanHbox.setMaxWidth(1280);
        cyanHbox.setMaxHeight(60); 

        Button pSelectButton = new Button("Create Player");
        pSelectButton.setOnAction(e -> {
            getSceneService().pushSubScene(new LevelSelectScene());
        });

        Button pandaButton = new Button("");
        pandaButton.setBackground(Background.EMPTY);
       
        Image unclickedpandaImage = new Image(getClass().getResource("/assets/textures/buttons/unclickedpandaButton.png").toExternalForm());
        Image clickedpandaImage = new Image(getClass().getResource("/assets/textures/buttons/clickedpandaButton.png").toExternalForm());
        
        ImageView pandaView = new ImageView(unclickedpandaImage);
        pandaView.setFitWidth(200);
        pandaView.setFitHeight(200);
        pandaButton.setGraphic(pandaView);

        final boolean[] pandaClicked = {false};

        pandaButton.setOnAction(e -> {
            if (pandaClicked[0]) {
                pandaView.setImage(unclickedpandaImage);
                System.out.println("Unclicked panda");
            } else {
                pandaView.setImage(clickedpandaImage);
                System.out.println("Clicked panda");
            }
            pandaClicked[0] = !pandaClicked[0];
        });

        Button shoppeeButton = new Button("");
        shoppeeButton.setBackground(Background.EMPTY);
       
        Image unclickedshoppeeImage = new Image(getClass().getResource("/assets/textures/buttons/unclickedshoppeeButton.png").toExternalForm());
        Image clickedshoppeeImage = new Image(getClass().getResource("/assets/textures/buttons/clickedshoppeeButton.png").toExternalForm());
        
        ImageView shoppeeView = new ImageView(unclickedshoppeeImage);
        shoppeeView.setFitWidth(200);
        shoppeeView.setFitHeight(200);
        shoppeeButton.setGraphic(shoppeeView);

        final boolean[] shoppeeClicked = {false};

        shoppeeButton.setOnAction(e -> {
            if (shoppeeClicked[0]) {
                shoppeeView.setImage(unclickedshoppeeImage);
                System.out.println("Unclicked shoppee");
            } else {
                shoppeeView.setImage(clickedshoppeeImage);
                System.out.println("Clicked shoppee");
            }
            shoppeeClicked[0] = !shoppeeClicked[0];
        });


        Button lazadaButton = new Button("");
        lazadaButton.setBackground(Background.EMPTY);
       
        Image unclickedlazadaImage = new Image(getClass().getResource("/assets/textures/buttons/unclickedlazadaButton.png").toExternalForm());
        Image clickedlazadaImage = new Image(getClass().getResource("/assets/textures/buttons/clickedlazadaButton.png").toExternalForm());
        
        ImageView lazadaView = new ImageView(unclickedlazadaImage);
        lazadaView.setFitWidth(200);
        lazadaView.setFitHeight(200);
        lazadaButton.setGraphic(lazadaView);

        final boolean[] lazadaClicked = {false};

        lazadaButton.setOnAction(e -> {
            if (lazadaClicked[0]) {
                lazadaView.setImage(unclickedlazadaImage);
                System.out.println("Unclicked lazada");
            } else {
                lazadaView.setImage(clickedlazadaImage);
                System.out.println("Clicked lazada");
            }
            lazadaClicked[0] = !lazadaClicked[0];
        });


        Button zaloraButton = new Button("");
        zaloraButton.setBackground(Background.EMPTY);
       
        Image unclickedzaloraImage = new Image(getClass().getResource("/assets/textures/buttons/unclickedzaloraButton.png").toExternalForm());
        Image clickedzaloraImage = new Image(getClass().getResource("/assets/textures/buttons/clickedzaloraButton.png").toExternalForm());
        
        ImageView zaloraView = new ImageView(unclickedzaloraImage);
        zaloraView.setFitWidth(200);
        zaloraView.setFitHeight(200);
        zaloraButton.setGraphic(zaloraView);

        final boolean[] zaloraClicked = {false};

        zaloraButton.setOnAction(e -> {
            if (zaloraClicked[0]) {
                zaloraView.setImage(unclickedzaloraImage);
                System.out.println("Unclicked ZALORA");
            } else {
                zaloraView.setImage(clickedzaloraImage);
                System.out.println("Clicked ZALORA");
            }
            zaloraClicked[0] = !zaloraClicked[0];
        });

        // Button optionsButton = new Button("Options");
        // optionsButton.setOnAction(e -> {
        //     System.out.println("CLICKED OPTIONS");
        // });

        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(e -> {
            getSceneService().popSubScene();
        });
        getContentRoot().getChildren().add(backgroundView);
        blackVbox.getChildren().addAll(yellowHbox,orangeHbox,redHbox,greenHbox,cyanHbox);
        greenHbox.getChildren().addAll(pinkVbox,purpleVbox,brownVbox,indigoVbox);
        yellowHbox.getChildren().addAll(goBackButton);
        pinkVbox.getChildren().addAll(pandaButton);
        purpleVbox.getChildren().addAll(lazadaButton);
        indigoVbox.getChildren().addAll(zaloraButton);
        brownVbox.getChildren().addAll(shoppeeButton);
        orangeHbox.getChildren().addAll(playselectInfo);
        redHbox.getChildren().addAll(playselectInfo2);
        cyanHbox.getChildren().addAll(pSelectButton);
        getContentRoot().getChildren().add(blackVbox);
    }

}
