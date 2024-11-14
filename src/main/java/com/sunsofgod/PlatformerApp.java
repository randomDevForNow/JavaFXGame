package com.sunsofgod;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.audio.AudioPlayer;
import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.view.KeyView;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.sunsofgod.Scenes.*;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.sunsofgod.EntityType.*;

public class PlatformerApp extends GameApplication {

    private int globalTimerValue = 0;
    private boolean hasFell = false;

    String sfx_source = getClass().getResource("/assets/sounds/clickedSoundfx.mp3").toString();
    AudioClip deathSfx = new AudioClip(sfx_source);

    // function
    private boolean levelSelectLock = false;

    // handles the database of completed levels
    File file = new File("src/main/resources/database.json");
    ObjectMapper objectMapper = new ObjectMapper();
    private int levelSelect = 0;

    private boolean dialogShown = false;

    // sets the onUpdate funtion to on and off
    private boolean globalTimerPaused = false;

    // sets the timer to be off at the start
    private ArrayList<Entity> activePlayers = new ArrayList<>();
    private Entity background;

    /* Set to Private */
    private boolean[] players = { false, false, false, false };
    private int levelNum = 0;

    public boolean getDialogShown() {
        return dialogShown;
    }

    public void setDialogShown(boolean dialogShown) {
        this.dialogShown = dialogShown;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
    }

    public boolean[] getPlayers() {
        return players;
    }

    public void setPlayers(boolean[] players) {
        this.players = players;
    }

    private Entity spawnpoint;
    int x = 0;
    private boolean globalTimerOn = true;

    /* Getters and Setters */

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1280);
        settings.setHeight(720);

        settings.setMainMenuEnabled(true);

        settings.setDeveloperMenuEnabled(true);
        /* Set Loading Screen Here: */

        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public LoadingScene newLoadingScene() {
                return new MainLoadingScene();
            }

            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                return new RiderMainMenuScene();
            }

            @NotNull
            @Override
            public FXGLMenu newGameMenu() {
                return new PauseMenuScene();
            }

        });
    }

    /* Get for ending the game (Init level refunds and scoreboard) */
    // private LazyValue<LevelEndScene> levelEndScene = new LazyValue<>(() -> new
    // LevelEndScene());

    private KeyCode[][] bindings = new KeyCode[][] {
            { KeyCode.W, KeyCode.A, KeyCode.D, KeyCode.E },
            { KeyCode.UP, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SLASH },
            { KeyCode.I, KeyCode.J, KeyCode.L, KeyCode.O },
            { KeyCode.G, KeyCode.V, KeyCode.N, KeyCode.H },
    };

    private void bindKeys() {

        getInput().clearAll();
        int i = 0;
        for (Entity player : activePlayers) {
            getInput().addAction(new UserAction("Jump" + i) {
                @Override
                protected void onActionBegin() {
                    player.getComponent(PlayerComponent.class).jump();
                }
            }, bindings[i][0]);

            getInput().addAction(new UserAction("Left" + i) {
                @Override
                protected void onAction() {
                    if (!dialogShown) {
                        player.getComponent(PlayerComponent.class).left();
                    } else {
                        player.getComponent(PlayerComponent.class).stop();
                    }
                }

                @Override
                protected void onActionEnd() {
                    player.getComponent(PlayerComponent.class).stop();
                }
            }, bindings[i][1]);

            getInput().addAction(new UserAction("Right" + i) {
                @Override
                protected void onAction() {
                    if (!dialogShown) {
                        player.getComponent(PlayerComponent.class).right();
                    } else {
                        player.getComponent(PlayerComponent.class).stop();
                    }
                }

                @Override
                protected void onActionEnd() {
                    player.getComponent(PlayerComponent.class).stop();
                }
            }, bindings[i][2]);

            getInput().addAction(new UserAction("Use" + i) {
                @Override
                protected void onActionBegin() {
                    /* For Getting Mail */
                    getGameWorld().getEntitiesByType(BUTTON)
                            .stream()
                            .filter(btn -> btn.hasComponent(CollidableComponent.class) && player.isColliding(btn))
                            .forEach(btn -> {

                                finishLevel();
                            });
                }

                @Override
                protected void onActionEnd() {

                }
            }, bindings[i][3]);
            i++;
        }
        if (activePlayers.size() == 4) {
            bindMousePlayer();
        }

    }

    private void bindMousePlayer() {
        try {
            MouseUIController controller = new MouseUIController();
            controller.setPlayer(activePlayers.get(3));
            controller.setButton(getGameWorld().getEntitiesByType(BUTTON));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mouseUi.fxml"));

            System.out.println(loader);
            loader.setController(controller);
            Parent root = loader.load();

            AnchorPane anchorPane = (AnchorPane) root;
            anchorPane.setLayoutX(FXGL.getAppWidth() - 300);
            anchorPane.setLayoutY(FXGL.getAppHeight() - 150);
            FXGL.getGameScene().addUINode(anchorPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* For Global Variables (Refunds of each player) */
    @Override
    protected void initGameVars(Map<String, Object> vars) {

        vars.put("globalTimer", 10000);
    }

    @Override
    protected void initUI() {
        Label globalTimerLabel = new Label();
        globalTimerLabel.setTextFill(Color.BLACK);
        globalTimerLabel.setFont(Font.font(20.0));
        globalTimerLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            int timer = FXGL.getip("globalTimer").get();
            int seconds = (timer / 1000); // Convert to seconds
            int milliseconds = (timer % 1000) / 10; // Get the milliseconds and convert to two digits
            return String.format("Timer: %02d:%02d", seconds, milliseconds); // Display seconds and milliseconds
        }, FXGL.getip("globalTimer")));
        FXGL.addUINode(globalTimerLabel, 20, 10);

    }

    @Override
    protected void onPreInit() {
        getSettings().setGlobalMusicVolume(0.25);
        // loopBGM("BGM_dash_runner.wav");
        /* Add Media Player Code here that loops */
    }

    private void createLevel() {

        int playerNumbers2 = 0;
        for (int i = 0; i < 4; i++) {
            if (players[i]) {
                playerNumbers2++;
            }
        }

        if (playerNumbers2 == 1) {
            levelSelect = levelNum;
        } else if (playerNumbers2 == 2) {
            levelSelect = levelNum + 4;
        } else if (playerNumbers2 == 3) {
            levelSelect = levelNum + 8;
        } else if (playerNumbers2 == 4) {
            levelSelect = levelNum + 12;
        }

        globalTimerValue = getLevelValue(levelSelect);
        set("globalTimer", globalTimerValue);
        System.out.println("Selected level" + levelSelect);

        setLevelFromMap("tmx/level" + levelSelect + ".tmx");
    }

    public void finishLevel() {
        // reset all counts
        x = 0;

        // sets the finished level on the database.json
        try {
            // Parse the JSON from the file into a JsonNode
            JsonNode rootNode = objectMapper.readTree(file);

            int playerNumbers = activePlayers.size();

            if (!levelSelectLock) {

                if (playerNumbers == 1) {
                    levelSelect = levelNum;
                } else if (playerNumbers == 2) {
                    levelSelect = levelNum + 4;
                } else if (playerNumbers == 3) {
                    levelSelect = levelNum + 8;
                } else if (playerNumbers == 4) {
                    levelSelect = levelNum + 12;
                }

                levelSelectLock = true;
            }

            ((ObjectNode) rootNode).put("level" + levelSelect, true);

            objectMapper.writeValue(file, rootNode);

        } catch (IOException s) {
            s.printStackTrace();
        }

        // GIAN reset timer here
        System.out.println(levelSelect);
        if (levelSelect % 4 == 0) {
            getSceneService().pushSubScene(new LevelEndScene());
            return;
        }

        getSceneService().pushSubScene(new LevelCompletionScene(1));
        dialogShown = false;

        levelSelect++;
        setLevelFromMap("tmx/level" + levelSelect + ".tmx");
        getGameWorld().removeEntity(background);

        if (levelSelect % 2 == 0) {
            background = spawn("background");
        } else {
            background = spawn("background2");
        }

        globalTimerValue = getLevelValue(levelSelect);
        set("globalTimer", globalTimerValue);

        spawnPlayers();

    }

    private void spawnPlayers() {
        spawnpoint = getGameWorld().getEntitiesByType(SPAWNPOINT).get(0);

        if (activePlayers.isEmpty()) {
            for (int i = 0; i < 4; i++) {
                if (players[i]) {
                    activePlayers.add(spawn("player" + (i + 1), spawnpoint.getX() + (i * 50), spawnpoint.getY()));
                }
            }
        } else {
            activePlayers.forEach(player -> {
                player.getComponent(PhysicsComponent.class)
                        .overwritePosition(new Point2D(spawnpoint.getX() + x, spawnpoint.getY()));
                player.setZIndex(Integer.MAX_VALUE);
                player.getComponent(PhysicsComponent.class).setVelocityX(0);
            });
        }
        x += 50;
    }

    public static int getLevelValue(int levelNum) {
        // Create an ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Read the JSON file and parse it into a JsonNode
            JsonNode rootNode = objectMapper.readTree(new File("src/main/resources/timer.json"));

            // Get the value of the specified level
            JsonNode levelNode = rootNode.get("level" + levelNum);
            if (levelNode != null) {
                return levelNode.asInt(); // Return the integer value
            } else {
                throw new IllegalArgumentException("Invalid level: " + levelNum);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read or parse the JSON file", e);
        }
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new PlatformerFactory());

        activePlayers.clear();

        createLevel();

        spawnPlayers();
        bindKeys();

        background = spawn("background2");

        // setViewPort();
        // resetUINodes();

        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(-1500, 0, 250 * 70, getAppHeight());
        viewport.bindToEntity(activePlayers.get(0), getAppWidth() / 2, getAppHeight() / 2);
        viewport.setLazy(true);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 980);

        // Player Parcel Interaction
        getPhysicsWorld().addCollisionHandler(new PlayerButtonHandler());

        /* Player & Blocks Physics */
        Image curImage = new Image(getClass().getResource("/assets/textures/cursor.png").toExternalForm());
        getGameScene().setCursor(new ImageCursor(curImage));
        onCollision(PLAYER, PLATFORM, (player, platform) -> {
            String platformType = platform.getString("type");

            if ("NORMAL".equals(platformType)) {
                player.getComponent(PlayerComponent.class).setSlip(false);
                player.getComponent(PlayerComponent.class).setPlayerSpeed(170);

                if (!player.getComponent(PlayerComponent.class).getStopped()) {
                    player.getComponent(PlayerComponent.class).stop();
                    player.getComponent(PlayerComponent.class).setStopped(true);
                } else
                    player.getComponent(PlayerComponent.class).setPlayerSpeed(170);
            } else if ("SLOW".equals(platformType)) {
                player.getComponent(PlayerComponent.class).setSlip(false);

                player.getComponent(PlayerComponent.class).setPlayerSpeed(85);
            } else if ("SLIP".equals(platformType)) {
                player.getComponent(PlayerComponent.class).setSlip(true);
            } else {
                player.getComponent(PlayerComponent.class).setSlip(false);
                player.getComponent(PlayerComponent.class).setPlayerSpeed(170);
            }
        });

        onCollision(PLAYER, PLAYER, (player1, player2) -> {
            player1.getComponent(PlayerComponent.class).stop();
            player2.getComponent(PlayerComponent.class).stop();
        });

        onCollisionOneTimeOnly(PLAYER, EXIT_SIGN, (player, sign) -> {
            var texture = texture("exit_sign.png").brighter();
            texture.setTranslateX(sign.getX() + 9);
            texture.setTranslateY(sign.getY() + 13);

            var gameView = new GameView(texture, 150);

            getGameScene().addGameView(gameView);

            runOnce(() -> getGameScene().removeGameView(gameView),
                    Duration.seconds(1.6));
        });

        onCollisionOneTimeOnly(PLAYER, DOOR_BOT, (player, door) -> {
            // levelEndScene.get().onLevelFinish();

            finishLevel();
        });

        onCollisionOneTimeOnly(PLAYER, MESSAGE_PROMPT, (player, prompt) -> {
            prompt.setOpacity(1);

            despawnWithDelay(prompt, Duration.seconds(4.5));
        });

        onCollisionBegin(PLAYER, KEY_PROMPT, (player, prompt) -> {
            String key = prompt.getString("key");

            var entity = getGameWorld().create("keyCode", new SpawnData(prompt.getX(),

                    prompt.getY()).put("key", key));
            spawnWithScale(entity, Duration.seconds(1),
                    Interpolators.ELASTIC.EASE_OUT());

            runOnce(() -> {
                despawnWithScale(entity, Duration.seconds(1),
                        Interpolators.ELASTIC.EASE_IN());
            }, Duration.seconds(2.5));
        });

    }

    @Override
    protected void onUpdate(double tpf) {
        if (globalTimerPaused) {
            return;
        }

        for (Entity player : activePlayers) {
            if (player != null && player.getY() > getAppHeight()) {
                if (!hasFell) {
                    deathSfx.play();
                    hasFell = true;
                }
                activePlayers.forEach(p -> {
                    p.getComponent(PhysicsComponent.class)
                            .overwritePosition(new Point2D(spawnpoint.getX() + (x * 50), spawnpoint.getY()));
                    p.setZIndex(Integer.MAX_VALUE);
                    player.getComponent(PhysicsComponent.class).setVelocityX(0);
                });
            }
            x++;
        }
        x = 0;
        hasFell = false;

        if (globalTimerOn) {
            if (FXGL.geti("globalTimer") > 0) {
                inc("globalTimer", -1);
            }
        }

        if (FXGL.geti("globalTimer") == 0) {
            getSceneService().pushSubScene(new LevelCompletionScene(0));
            activePlayers.forEach(p -> {
                p.getComponent(PhysicsComponent.class)
                        .overwritePosition(new Point2D(spawnpoint.getX() + (x * 50), spawnpoint.getY()));
                p.setZIndex(Integer.MAX_VALUE);
                p.getComponent(PhysicsComponent.class).setVelocityX(0);
            });
            set("globalTimer", globalTimerValue);

        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
