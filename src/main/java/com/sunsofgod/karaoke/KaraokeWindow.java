package com.sunsofgod.karaoke;

import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.geometry.Insets;

public class KaraokeWindow {
    private Stage stage;
    private TextField inputDisplay;
    private WebView youtubePlayer;
    private StringBuilder currentInput;
    private Label songInfo;
    private final SongList songList;
    private Runnable onStartGame; // Callback for starting the game
    private static WebView activePlayer = null;

    public KaraokeWindow(Runnable onStartGame) {
        this.songList = SongList.getInstance();
        this.currentInput = new StringBuilder();
        this.onStartGame = onStartGame;
        createWindow();
        
        // Add cleanup on window close
        stage.setOnCloseRequest(e -> {
            cleanup();
            stage.close();
        });
    }

    private void createWindow() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Karaoke Song Selection");

        // Create main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // Add information label at the top
        Label infoLabel = new Label("Press 'M' at any time during the game to change songs");
        infoLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;");
        root.getChildren().add(infoLabel);

        // Initialize components
        initializeComponents();

        // YouTube player section
        youtubePlayer.setPrefSize(640, 360);
        youtubePlayer.setVisible(false);
        root.getChildren().add(youtubePlayer);

        // Song information display
        VBox songInfoBox = new VBox(5);
        songInfoBox.setAlignment(Pos.CENTER);
        
        // Add category list
        VBox categoryBox = new VBox(5);
        categoryBox.setStyle("-fx-padding: 10px; -fx-background-color: #f0f0f0; -fx-background-radius: 5px;");
        categoryBox.getChildren().addAll(
            new Label("Song Categories:"),
            new Label("001-099: Classic Hits"),
            new Label("101-199: Pop Hits"),
            new Label("201-299: Rock Classics"),
            new Label("301-399: Filipino Hits"),
            new Label("401-499: Modern Pop"),
            new Label("501-599: Ballads"),
            new Label("601-699: Party Songs"),
            new Label("701-799: Recent Hits")
        );
        root.getChildren().add(categoryBox);

        // Input section
        VBox inputBox = new VBox(10);
        inputBox.setAlignment(Pos.CENTER);
        Label inputLabel = new Label("Enter Song Code:");
        inputLabel.setStyle("-fx-font-size: 16px;");
        inputBox.getChildren().addAll(inputLabel, inputDisplay, songInfo);
        root.getChildren().add(inputBox);

        // Number pad
        root.getChildren().add(createNumberPad());

        // Add buttons box for Start/Continue Game and Back
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button startGameButton = new Button("Continue Game");
        startGameButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        startGameButton.setOnAction(e -> {
            stage.hide();
            if (onStartGame != null) {
                onStartGame.run();
            }
        });
        
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px;");
        backButton.setOnAction(e -> {
            clearInput();
            stage.close();
        });

        buttonBox.getChildren().addAll(startGameButton, backButton);
        root.getChildren().add(buttonBox);

        // Create scene with adjusted height for new elements
        Scene scene = new Scene(root, 800, 1000);
        stage.setScene(scene);
        
        // Add window close handler
        stage.setOnCloseRequest(e -> {
            clearInput();
            stage.close();
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

    private GridPane createNumberPad() {
        GridPane numberPad = new GridPane();
        numberPad.setHgap(10);
        numberPad.setVgap(10);
        numberPad.setAlignment(Pos.CENTER);

        // Add number buttons (0-9)
        for (int i = 1; i <= 9; i++) {
            Button btn = createNumberButton(String.valueOf(i));
            numberPad.add(btn, (i - 1) % 3, (i - 1) / 3);
        }

        // Add 0 button
        Button btn0 = createNumberButton("0");
        numberPad.add(btn0, 1, 3);

        // Add Clear button
        Button clearBtn = new Button("Clear");
        clearBtn.setPrefWidth(80);
        clearBtn.setOnAction(e -> clearInput());
        numberPad.add(clearBtn, 0, 3);

        // Add Enter button
        Button enterBtn = new Button("Enter");
        enterBtn.setPrefWidth(80);
        enterBtn.setOnAction(e -> processInput());
        numberPad.add(enterBtn, 2, 3);

        return numberPad;
    }

    private Button createNumberButton(String number) {
        Button button = new Button(number);
        button.setPrefSize(80, 80);
        button.setStyle("-fx-font-size: 24px;");
        button.setOnAction(e -> appendNumber(number));
        return button;
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
            songInfo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

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

    private void clearInput() {
        currentInput.setLength(0);
        inputDisplay.setText("");
        songInfo.setText("");
        cleanup(); // Add cleanup call here
        youtubePlayer.setVisible(false);
    }    

    public void show() {
        stage.show();
        cleanup();
    }
}