// KaraokeScene.java
package com.sunsofgod.Scenes;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.sunsofgod.karaoke.SongEntry;
import com.sunsofgod.karaoke.SongList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;

public class KaraokeScene extends FXGLMenu {
    private TextField inputDisplay;
    private WebView youtubePlayer;
    private StringBuilder currentInput;
    private Label songInfo;
    private final SongList songList;

    public KaraokeScene() {
        super(MenuType.GAME_MENU);
        this.songList = SongList.getInstance();
        this.currentInput = new StringBuilder();
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        // Initialize YouTube player
        youtubePlayer = new WebView();
        youtubePlayer.setPrefSize(640, 360);
        youtubePlayer.setVisible(false); // Hide initially

        // Initialize input display
        inputDisplay = new TextField();
        inputDisplay.setEditable(false);
        inputDisplay.setPrefWidth(200);
        inputDisplay.setAlignment(Pos.CENTER);
        inputDisplay.setStyle("-fx-font-size: 24px;");

        // Initialize song info display
        songInfo = new Label();
        songInfo.setStyle("-fx-font-size: 18px;");
    }

    private void layoutComponents() {
        VBox root = new VBox(20); // 20px spacing
        root.setAlignment(Pos.CENTER);

        // Add YouTube player
        root.getChildren().add(youtubePlayer);

        // Add input display
        VBox inputBox = new VBox(10);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.getChildren().addAll(
            new Label("Enter Song Code:"),
            inputDisplay,
            songInfo
        );
        root.getChildren().add(inputBox);

        // Create and add number pad
        root.getChildren().add(createNumberPad());

        // Add navigation buttons
        Button backButton = new Button("Back to Player Select");
        backButton.setOnAction(e -> getSceneService().popSubScene());
        root.getChildren().add(backButton);

        getContentRoot().getChildren().add(root);
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
        clearBtn.setOnAction(e -> clearInput());
        numberPad.add(clearBtn, 0, 3);

        // Add Enter button
        Button enterBtn = new Button("Enter");
        enterBtn.setOnAction(e -> processInput());
        numberPad.add(enterBtn, 2, 3);

        return numberPad;
    }

    private Button createNumberButton(String number) {
        Button button = new Button(number);
        button.setPrefSize(50, 50);
        button.setOnAction(e -> appendNumber(number));
        return button;
    }

    private void appendNumber(String number) {
        if (currentInput.length() < 4) { // Limit to 4 digits
            currentInput.append(number);
            inputDisplay.setText(currentInput.toString());
            searchSong(currentInput.toString());
        }
    }

    private void clearInput() {
        currentInput.setLength(0);
        inputDisplay.setText("");
        songInfo.setText("");
        youtubePlayer.setVisible(false);
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
        String embedUrl = String.format(
            "https://www.youtube.com/embed/%s?autoplay=1&controls=1",
            song.getYoutubeId()
        );
        
        WebEngine engine = youtubePlayer.getEngine();
        engine.load(embedUrl);
        youtubePlayer.setVisible(true);
    }
}