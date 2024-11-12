package com.sunsofgod;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.view.KeyView;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;

import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.sunsofgod.Scenes.*;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.sunsofgod.EntityType.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;

public class PlatformerApp extends GameApplication {

    // sets the timer to be off at the start
    private boolean globalTimerOn = true;
    

    private static final int MAX_LEVEL = 5;
    private static int STARTING_LEVEL = 0;

    // finds the starting level
    public void loadGameConfig(String configFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Read the JSON file into a strongly-typed Map
            Map<String, Boolean> gameConfig = objectMapper.readValue(
                    new File(configFilePath), new TypeReference<Map<String, Boolean>>() {
                    });

            // Iterate through the levels to find which one is true
            for (int level = 1; level <= 4; level++) {
                if (gameConfig.get("level" + level)) {
                    // Update startingLevel based on which level is true
                    // starting level is -1 as we start from 0
                    STARTING_LEVEL = level - 1;
                    break; // Exit the loop once the starting level is found
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getStartingLevel() {
        return STARTING_LEVEL;
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1280);
        settings.setHeight(720);

        settings.setMainMenuEnabled(true);
        System.out.println("Hello!");

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

            /*
             * Uncomment nyo ito para maedit yung
             * Pause Menu natin yung kapag
             * Nagpress ng escape yung user/s
             * May lalabas na resume, option,
             * go to main menu, go to level select
             * goto
             */
            // @NotNull
            // @Override
            // public FXGLMenu newGameMenu() {
            // return new PauseMenuScene();
            // }

        });
    }

    /* Get for ending the game (Init level refunds and scoreboard) */
    // private LazyValue<LevelEndScene> levelEndScene = new LazyValue<>(() -> new
    // LevelEndScene());

    private Entity[] players = new Entity[4];

    private KeyCode[][] bindings = new KeyCode[][] {
            { KeyCode.W, KeyCode.A, KeyCode.D, KeyCode.E },
            { KeyCode.UP, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SLASH },
            { KeyCode.I, KeyCode.J, KeyCode.L, KeyCode.O },
            { KeyCode.G, KeyCode.V, KeyCode.N, KeyCode.H },
    };

    private void bindKeys() {
        Map<String, Boolean> playerStatus = loadPlayerStatusFromJson(); // Load player status

        int counter = 0;

        for (Entity player : players) {
            // Check if the player is active based on the JSON value
            if (playerStatus.getOrDefault("player" + (counter + 1), false)) {
                getInput().addAction(new UserAction("Jump" + counter) {
                    @Override
                    protected void onActionBegin() {
                        player.getComponent(PlayerComponent.class).jump();
                    }
                }, bindings[counter][0]);

                getInput().addAction(new UserAction("Left" + counter) {
                    @Override
                    protected void onAction() {
                        player.getComponent(PlayerComponent.class).left();
                    }

                    @Override
                    protected void onActionEnd() {
                        player.getComponent(PlayerComponent.class).stop();
                    }
                }, bindings[counter][1]);

                getInput().addAction(new UserAction("Right" + counter) {
                    @Override
                    protected void onAction() {
                        player.getComponent(PlayerComponent.class).right();
                    }

                    @Override
                    protected void onActionEnd() {
                        player.getComponent(PlayerComponent.class).stop();
                    }
                }, bindings[counter][2]);

                getInput().addAction(new UserAction("Use" + counter) {
                    @Override
                    protected void onActionBegin() {
                        /* For Getting Mail */
                        getGameWorld().getEntitiesByType(BUTTON)
                                .stream()
                                .filter(btn -> btn.hasComponent(CollidableComponent.class) && player.isColliding(btn))
                                .forEach(btn -> {
                                    btn.removeComponent(CollidableComponent.class);
                                    Entity keyEntity = btn.getObject("keyEntity");
                                    keyEntity.setProperty("activated", true);

                                    KeyView view = (KeyView) keyEntity.getViewComponent().getChildren().get(0);
                                    view.setKeyColor(Color.RED);

                                    //function to disable 
                                    globalTimerOn = false;

                                    makeExitDoor();
                                });
                    }
                }, bindings[counter][3]);
            }
            counter++;
        }
    }

    /* For Global Variables (Refunds of each player) */
    @Override
    protected void initGameVars(Map<String, Object> vars) {

        PlatformerApp config = new PlatformerApp();
        config.loadGameConfig("src/main/resources/database.json");
        System.out.println("The starting level is: " + STARTING_LEVEL);
        vars.put("level", STARTING_LEVEL);

        // vars.put("levelTime", 0.0);
        // vars.put("score", 0);

        // global variables for timers
        vars.put("globalTimer", 1000);
    }


    @Override
    protected void initUI() {
        Label globalTimerLabel = new Label();
        globalTimerLabel.setTextFill(Color.BLACK);
        globalTimerLabel.setFont(Font.font(20.0));
        globalTimerLabel.textProperty().bind(FXGL.getip("globalTimer").asString("Timer:"+ "%d"));
        FXGL.addUINode(globalTimerLabel, 20, 10);

    }

    

    @Override
    protected void onPreInit() {
        getSettings().setGlobalMusicVolume(0.25);
        // loopBGM("BGM_dash_runner.wav");
        /* Add Media Player Code here that loops */
    }

    private static Map<String, Boolean> loadPlayerStatusFromJson() {
        try {
            // Create an ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Read the JSON file into a Map<String, Boolean>
            return objectMapper.readValue(new File("src/main/resources/database.json"),
                    objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Boolean.class));
        } catch (IOException e) {
            e.printStackTrace();
            return Map.of(); // Return an empty map in case of error
        }
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new PlatformerFactory());

        for (Entity player : players) {
            player = null;
        }

        nextLevel();

        // player must be spawned after call to nextLevel, otherwise player gets removed
        // before the update tick a_actually_ adds the player to game world
        Map<String, Boolean> playerStatus = loadPlayerStatusFromJson();

        for (int i = 0; i < players.length; i++) {
            // Construct the player key dynamically (player1, player2, etc.)
            String playerKey = "player" + (i + 1);

            // Check if the player should be spawned based on the JSON value
            if (playerStatus.getOrDefault(playerKey, false)) {
                // Dynamically adjust the player type based on the loop index (player1, player2,
                // etc.)
                players[i] = spawn(playerKey, 50 + i * 100, 50); // Spawn and assign players

                // Optionally store in the global map
                set(playerKey, players[i]); // Store the player in the global map with its key
            } else {
                // If not spawned, set to null
                players[i] = null;
            }
        }

        bindKeys();

        spawn("background");

        /* Follows the player (can be turned off) */
        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(-1500, 0, 250 * 70, getAppHeight());
        // viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        viewport.setLazy(true);
    }

    @Override
    protected void initPhysics() {

        getPhysicsWorld().setGravity(0, 980);

        // Player Parcel Interaction
        getPhysicsWorld().addCollisionHandler(new PlayerButtonHandler());

        /* Player & Blocks Physics */

        onCollision(PLAYER, PLATFORM, (player, platform) -> {
            String platformType = platform.getString("type");

            if ("NORMAL".equals(platformType)) {
                player.getComponent(PlayerComponent.class).setSlip(false);
                System.out.println(player.getComponent(PlayerComponent.class).getSlip());

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
                System.out.println(player.getComponent(PlayerComponent.class).getSlip());
            }
        });

        /* Physics Interaction */

        /* PLAYER COLLISION HANLDERS */

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

        onCollisionOneTimeOnly(PLAYER, EXIT_TRIGGER, (player, trigger) -> {
            makeExitDoor();
        });

        onCollisionOneTimeOnly(PLAYER, DOOR_BOT, (player, door) -> {
            // levelEndScene.get().onLevelFinish();

            // the above runs in its own scene, so fade will wait until
            // the user exits that scene
            getGameScene().getViewport().fade(() -> {
                nextLevel();
            });
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

    private void makeExitDoor() {
        var doorTop = getGameWorld().getSingleton(DOOR_TOP);
        var doorBot = getGameWorld().getSingleton(DOOR_BOT);

        doorBot.getComponent(CollidableComponent.class).setValue(true);

        doorTop.setOpacity(1);
        doorBot.setOpacity(1);
    }

    private void nextLevel() {
        if (geti("level") == MAX_LEVEL) {
            showMessage("You finished the demo!");
            return;
        }

        inc("level", +1);

        setLevel(geti("level"));
    }

    @Override
    protected void onUpdate(double tpf) {
        // inc("levelTime", tpf);

        // for (Entity player: players){
        // if (player.getY() > getAppHeight()) {
        // onPlayerDied();
        // }
        // }
        // resets the properties of the buttons
        if (globalTimerOn) {
            inc("globalTimer", -1);
        }

        if (FXGL.geti("globalTimer") == 0){
            onPlayerDied();
        }
        

    }

    public void onPlayerDied() {
        setLevel(geti("level"));
    }

    private void setLevel(int levelNum) {

        for (Entity player : players) {
            if (player != null) {
                player.getComponent(PhysicsComponent.class).overwritePosition(new Point2D(50, 50));
                player.setZIndex(Integer.MAX_VALUE);
            }
        }

        set("globalTimer", 1000);
        globalTimerOn = true;

        // set("levelTime", 0.0);

        Level level = setLevelFromMap("tmx/level" + levelNum + ".tmx");

        /* For Global Vars */
        // var shortestTime = level.getProperties().getDouble("star1time");
        //
        // var levelTimeData = new LevelEndScene.LevelTimeData(shortestTime * 2.4,
        // shortestTime * 1.3, shortestTime);
        //
        // set("levelTimeData", levelTimeData);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
