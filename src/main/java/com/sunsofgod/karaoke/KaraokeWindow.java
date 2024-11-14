package com.sunsofgod.karaoke;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class KaraokeWindow extends FXGLMenu {
    private Stage stage;
    private TextField inputDisplay;
    private WebView youtubePlayer;
    private StringBuilder currentInput;
    private Label songInfo;
    private final SongList songList;
    private Runnable onStartGame;
    private static WebView activePlayer = null;

    public KaraokeWindow(Runnable onStartGame) {
        super(MenuType.MAIN_MENU);
        this.songList = SongList.getInstance();
        this.currentInput = new StringBuilder();
        this.onStartGame = onStartGame;
        createWindow();
        
        stage.setOnCloseRequest(e -> {
            cleanup();
            stage.close();
        });
    }

    private void createWindow() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Delivery Rush Karaoke");
    
        // Create main layout as vertical
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #1a2a6c, #b21f1f, #fdbb2d);");
    
        // Title section
        Text titleText = new Text("Delivery Rush Karaoke");
        titleText.setFont(Font.font("Tahoma", FontWeight.BOLD, 36));
        titleText.setFill(Color.WHITE);
        titleText.setEffect(new DropShadow(10, Color.BLACK));
        root.getChildren().add(titleText);
    
        // Info label
        Label infoLabel = createStyledLabel("Press 'M' during game to change songs", 14);
        root.getChildren().add(infoLabel);
    
        // Initialize components
        initializeComponents();
    
        // YouTube player section at the top
        VBox playerSection = new VBox(10);
        playerSection.setAlignment(Pos.CENTER);
        playerSection.setPadding(new Insets(10));
        playerSection.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 10px;");
        youtubePlayer.setPrefSize(900, 506); // 16:9 ratio
        youtubePlayer.setVisible(false);
        playerSection.getChildren().add(youtubePlayer);
        root.getChildren().add(playerSection);
    
        // Controls section below player
        HBox controlsSection = new HBox(20);
        controlsSection.setAlignment(Pos.CENTER);
    
        // Left side - Categories and Input
        VBox leftControls = new VBox(10);
        leftControls.setAlignment(Pos.TOP_CENTER);
        leftControls.setPrefWidth(400);
    
        // Input section
        VBox inputBox = createInputSection();
        
        // Categories (scrollable)
        VBox categoryBox = createCategoryBox();
        ScrollPane categoryScroll = new ScrollPane(categoryBox);
        categoryScroll.setFitToWidth(true);
        categoryScroll.setPrefHeight(200);
        categoryScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
    
        leftControls.getChildren().addAll(inputBox, categoryScroll);
    
        // Right side - Number pad and controls
        VBox rightControls = new VBox(10);
        rightControls.setAlignment(Pos.TOP_CENTER);
        rightControls.setPrefWidth(400);
    
        // Create new 5x2 number pad
        GridPane numberPad = createStyledNumberPad();
        
        // Control buttons
        HBox buttonBox = createControlButtons();
    
        rightControls.getChildren().addAll(numberPad, buttonBox);
    
        // Add both sides to controls section
        controlsSection.getChildren().addAll(leftControls, rightControls);
        root.getChildren().add(controlsSection);
    
        // Create scene
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        
        // Add fade-in animation
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    
        stage.setWidth(1000);
        stage.setHeight(900);
        stage.setScene(scene);
    }
    
    private GridPane createStyledNumberPad() {
        GridPane numberPad = new GridPane();
        numberPad.setHgap(15);
        numberPad.setVgap(15);
        numberPad.setAlignment(Pos.CENTER);
        numberPad.setPadding(new Insets(15));
        numberPad.setStyle(
            "-fx-background-color: rgba(0,0,0,0.7); " +
            "-fx-padding: 15px; " +
            "-fx-background-radius: 10px;"
        );
    
        // First row (1-5)
        for (int i = 1; i <= 5; i++) {
            Button btn = createStyledNumberButton(String.valueOf(i));
            numberPad.add(btn, i-1, 0);
        }
    
        // Second row (6-0)
        for (int i = 6; i <= 9; i++) {
            Button btn = createStyledNumberButton(String.valueOf(i));
            numberPad.add(btn, i-6, 1);
        }
    
        // Add 0 button
        Button btn0 = createStyledNumberButton("0");
        numberPad.add(btn0, 4, 1);
    
        // Add control buttons in a new row
        Button clearBtn = createStyledActionButton("Clear");
        clearBtn.setOnAction(e -> clearInput());
        numberPad.add(clearBtn, 0, 2, 2, 1); // Span 2 columns
    
        Button enterBtn = createStyledActionButton("Enter");
        enterBtn.setOnAction(e -> processInput());
        numberPad.add(enterBtn, 3, 2, 2, 1); // Span 2 columns
    
        return numberPad;
    }
    
    private Button createStyledNumberButton(String number) {
        Button button = new Button(number);
        button.setPrefSize(70, 70); // Slightly smaller buttons
        button.setStyle(
            "-fx-font-size: 24px; " +
            "-fx-background-color: #4CAF50; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 35; " + // Maintain circular shape
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0, 0, 2);"
        );
        
        // Add hover effect
        button.setOnMouseEntered(e -> {
            button.setStyle(
                "-fx-font-size: 24px; " +
                "-fx-background-color: #45a049; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 35; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 15, 0, 0, 3);"
            );
        });
        
        button.setOnMouseExited(e -> {
            button.setStyle(
                "-fx-font-size: 24px; " +
                "-fx-background-color: #4CAF50; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 35; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0, 0, 2);"
            );
        });
        
        button.setOnAction(e -> appendNumber(number));
        return button;
    }
    
    private Button createStyledActionButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(150, 60); // Wider buttons for actions
        button.setStyle(
            "-fx-font-size: 18px; " +
            "-fx-background-color: #2196F3; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 30; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0, 0, 2);"
        );
        
        // Add hover effect
        button.setOnMouseEntered(e -> {
            button.setStyle(
                "-fx-font-size: 18px; " +
                "-fx-background-color: #1976D2; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 30; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 15, 0, 0, 3);"
            );
        });
        
        button.setOnMouseExited(e -> {
            button.setStyle(
                "-fx-font-size: 18px; " +
                "-fx-background-color: #2196F3; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 30; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0, 0, 2);"
            );
        });
        
        return button;
    }
    
    private VBox createCategoryBox() {
        VBox categoryBox = new VBox(5);
        categoryBox.setStyle(
            "-fx-background-color: rgba(240,240,240,0.9); " +
            "-fx-padding: 10px; " +  // Reduced padding
            "-fx-background-radius: 10px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0, 0, 2);"
        );
        
        Label categoryTitle = new Label("Song Categories:");
        categoryTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 16)); // Reduced font size
        categoryTitle.setTextFill(Color.web("#2d5d8c"));
        
        categoryBox.getChildren().addAll(
            categoryTitle,
            createCategoryLabel("001-099: Classic Hits"),
            createCategoryLabel("101-199: Pop Hits"),
            createCategoryLabel("201-299: Rock Classics"),
            createCategoryLabel("301-399: Filipino Hits"),
            createCategoryLabel("401-499: Modern Pop"),
            createCategoryLabel("501-599: Ballads"),
            createCategoryLabel("601-699: Party Songs"),
            createCategoryLabel("701-799: Recent Hits")
        );
        
        return categoryBox;
    }
    
    private Label createCategoryLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14)); // Reduced font size
        label.setTextFill(Color.BLACK);
        return label;
    }
    

    private Label createStyledLabel(String text, double fontSize) {
        Label label = new Label(text);
        label.setFont(Font.font("Tahoma", FontWeight.NORMAL, fontSize));
        label.setTextFill(Color.WHITE);
        label.setEffect(new DropShadow(5, Color.BLACK));
        return label;
    }

    private VBox createInputSection() {
        VBox inputBox = new VBox(5);  // Reduced spacing
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setStyle(
            "-fx-background-color: rgba(0,0,0,0.7); " +
            "-fx-padding: 10px; " +  // Reduced padding
            "-fx-background-radius: 10px;"
        );
    
        Label inputLabel = createStyledLabel("Enter Song Code:", 20);  // Reduced font size
        
        // Style input display
        inputDisplay.setPrefWidth(250);  // Adjusted width
        inputDisplay.setStyle(
            "-fx-font-size: 20px; " +  // Reduced font size
            "-fx-background-color: rgba(255,255,255,0.9); " +
            "-fx-background-radius: 5px; " +
            "-fx-text-fill: #2d5d8c; " +
            "-fx-font-weight: bold;"
        );
        
        // Style song info
        songInfo.setStyle(
            "-fx-font-size: 16px; " +  // Reduced font size
            "-fx-text-fill: white; " +
            "-fx-padding: 5px; " +     // Reduced padding
            "-fx-font-weight: bold;"
        );
    
        inputBox.getChildren().addAll(inputLabel, inputDisplay, songInfo);
        return inputBox;
    }

    private HBox createControlButtons() {
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button continueButton = new Button("Continue Game");
        styleControlButton(continueButton, "#4CAF50");
        continueButton.setOnAction(e -> {
            stage.hide();
            if (onStartGame != null) {
                onStartGame.run();
            }
        });
        
        Button backButton = new Button("Back");
        styleControlButton(backButton, "#f44336");
        backButton.setOnAction(e -> {
            clearInput();
            stage.close();
        });

        buttonBox.getChildren().addAll(continueButton, backButton);
        return buttonBox;
    }

    private void styleControlButton(Button button, String baseColor) {
        button.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-padding: 10px 20px; " +
            "-fx-background-color: " + baseColor + "; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 5px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0, 0, 2);"
        );
        
        // Add hover effect
        button.setOnMouseEntered(e -> {
            button.setStyle(
                "-fx-font-size: 16px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-background-color: derive(" + baseColor + ", -10%); " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 5px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 15, 0, 0, 3);"
            );
        });
        
        button.setOnMouseExited(e -> {
            button.setStyle(
                "-fx-font-size: 16px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-background-color: " + baseColor + "; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 5px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0, 0, 2);"
            );
        });
    }

    private void initializeComponents() {
        // YouTube player
        youtubePlayer = new WebView();
        
        // If there's an existing active player, stop and clear it
        if (activePlayer != null) {
            try {
                activePlayer.getEngine().load(null);
                activePlayer.getEngine().loadContent("");
            } catch (Exception e) {
                System.out.println("Error clearing previous player: " + e.getMessage());
            }
        }
        activePlayer = youtubePlayer;
        
        // Input display
        inputDisplay = new TextField();
        inputDisplay.setEditable(false);
        inputDisplay.setPrefWidth(200);
        inputDisplay.setAlignment(Pos.CENTER);
        inputDisplay.setStyle("-fx-font-size: 24px;");

        // Song info
        songInfo = new Label();
        songInfo.setStyle("-fx-font-size: 18px; -fx-padding: 10px;");
    }

    private void appendNumber(String number) {
        if (currentInput.length() < 4) {
            currentInput.append(number);
            inputDisplay.setText(currentInput.toString());
            searchSong(currentInput.toString());
        }
    }

    private void processInput() {
        String code = currentInput.toString();
        if (songList.hasSong(code)) {
            loadSong(songList.getSong(code));
        }
    }

    private void searchSong(String code) {
        if (songList.hasSong(code)) {
            SongEntry song = songList.getSong(code);
            songInfo.setText(song.getTitle() + " - " + song.getArtist());
        } else {
            songInfo.setText("No song found");
        }
    }

    private void loadSong(SongEntry song) {
        try {
            // First, aggressively stop any existing content
            cleanup();

            // Create new player with updated content and explicit stop/pause handling
            String htmlContent = String.format(
                "<html><body style='margin:0;padding:0;background:black;'>" +
                "<div id='player'></div>" +
                "<script src='https://www.youtube.com/iframe_api'></script>" +
                "<script>" +
                "let currentPlayer = null;" +
                "function onYouTubeIframeAPIReady() {" +
                "  if (currentPlayer) {" +
                "    try {" +
                "      currentPlayer.destroy();" +
                "    } catch(e) {}" +
                "  }" +
                "  currentPlayer = new YT.Player('player', {" +
                "    width: '640'," +
                "    height: '360'," +
                "    videoId: '%s'," +
                "    playerVars: {" +
                "      'autoplay': 1," +
                "      'controls': 1," +
                "      'rel': 0," +
                "      'fs': 1," +
                "      'modestbranding': 1" +
                "    }," +
                "    events: {" +
                "      'onReady': function(event) {" +
                "        event.target.playVideo();" +
                "      }," +
                "      'onError': function(e) {" +
                "        if(e.data === 101 || e.data === 150) {" +
                "          window.location.href = 'https://www.youtube.com/watch?v=%s';" +
                "        }" +
                "      }" +
                "    }" +
                "  });" +
                "  window.addEventListener('beforeunload', function() {" +
                "    if (currentPlayer) {" +
                "      try {" +
                "        currentPlayer.stopVideo();" +
                "        currentPlayer.destroy();" +
                "      } catch(e) {}" +
                "    }" +
                "  });" +
                "}" +
                "</script>" +
                "</body></html>",
                song.getYoutubeId(),
                song.getYoutubeId()
            );

            youtubePlayer.getEngine().setJavaScriptEnabled(true);
            youtubePlayer.getEngine().loadContent(htmlContent);
            youtubePlayer.setVisible(true);

            songInfo.setText(song.getTitle() + " - " + song.getArtist());
            songInfo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        } catch (Exception e) {
            System.out.println("Error loading song: " + e.getMessage());
            cleanup();
        }
    }

    public void cleanup() {
        try {
            if (youtubePlayer != null && youtubePlayer.getEngine() != null) {
                // Execute multiple cleanup approaches
                youtubePlayer.getEngine().executeScript(
                    "try {" +
                    "  if (typeof currentPlayer !== 'undefined' && currentPlayer) {" +
                    "    currentPlayer.stopVideo();" +
                    "    currentPlayer.destroy();" +
                    "  }" +
                    "  var iframes = document.getElementsByTagName('iframe');" +
                    "  for(var i = 0; i < iframes.length; i++) {" +
                    "    iframes[i].src = '';" +
                    "    iframes[i].remove();" +
                    "  }" +
                    "  if(document.getElementById('player')) {" +
                    "    document.getElementById('player').remove();" +
                    "  }" +
                    "} catch(e) { console.log('Cleanup error:', e); }"
                );
                youtubePlayer.getEngine().load(null);
                youtubePlayer.getEngine().loadContent("");
            }
        } catch (Exception e) {
            System.out.println("Error in cleanup: " + e.getMessage());
        }
    }

    private void openMusicSelection() {
        // Create karaoke window that returns to game when closed
        KaraokeWindow karaokeWindow = new KaraokeWindow(() -> {
            // No need to transition scenes since we're already in game
            // Just hide the window and continue playing
            System.out.println("Continuing game with new song...");
        });
        karaokeWindow.show();
    }
    
    private void clearInput() {
        currentInput.setLength(0);
        inputDisplay.setText("");
        songInfo.setText("");
        cleanup();
        youtubePlayer.setVisible(false);
    }    

    public void show() {
        stage.show();
        cleanup();
    }
}