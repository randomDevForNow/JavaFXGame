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
import com.almasb.fxgl.physics.PhysicsComponent;

import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.sunsofgod.Scenes.*;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.sunsofgod.EntityType.*;

public class PlatformerApp extends GameApplication {

    // sets the timer to be off at the start
    public int level = 0;
    private boolean[] selectedPlayers = {};
    ArrayList<Entity> players = new ArrayList<>();

    /* Set to Private */
    public boolean[] playersI = { false, false, false, false };
    public int levelNum = 0;

    private Entity spawnpoint;
    int x = 0;
    private boolean globalTimerOn = true;

    private static final int MAX_LEVEL = 5;
    private static int STARTING_LEVEL = 0;

    private ArrayList<Entity> lvlPlayers = new ArrayList<>();

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

    private KeyCode[][] bindings = new KeyCode[][] {
            { KeyCode.W, KeyCode.A, KeyCode.D, KeyCode.E },
            { KeyCode.UP, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SLASH },
            { KeyCode.I, KeyCode.J, KeyCode.L, KeyCode.O },
            { KeyCode.G, KeyCode.V, KeyCode.N, KeyCode.H },
    };

    private void bindKeys() {

        int i = 0;
        for (Entity player : players) {
            System.out.println(player);
            getInput().addAction(new UserAction("Jump" + i) {
                @Override
                protected void onActionBegin() {
                    player.getComponent(PlayerComponent.class).jump();
                }
            }, bindings[i][0]);

            getInput().addAction(new UserAction("Left" + i) {
                @Override
                protected void onAction() {
                    player.getComponent(PlayerComponent.class).left();
                }

                @Override
                protected void onActionEnd() {
                    player.getComponent(PlayerComponent.class).stop();
                }
            }, bindings[i][1]);

            getInput().addAction(new UserAction("Right" + i) {
                @Override
                protected void onAction() {
                    player.getComponent(PlayerComponent.class).right();
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
                                btn.removeComponent(CollidableComponent.class);

                                finishLevel();
                            });
                }
            }, bindings[i][3]);
            i++;
        }

    }

    /* For Global Variables (Refunds of each player) */
    @Override
    protected void initGameVars(Map<String, Object> vars) {

        PlatformerApp config = new PlatformerApp();
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
        globalTimerLabel.textProperty().bind(FXGL.getip("globalTimer").asString("Timer:" + "%d"));
        FXGL.addUINode(globalTimerLabel, 20, 10);

    }

    @Override
    protected void onPreInit() {
        getSettings().setGlobalMusicVolume(0.25);
        // loopBGM("BGM_dash_runner.wav");
        /* Add Media Player Code here that loops */
    }

    int xInit = 0;

    private void createLevel() {
        /* Uncomment if levels up to 12 */

        // switch (players.size()) {
        // case 2:
        // levelNum += 4;
        // break;
        // case 3:
        // levelNum += 8;
        // break;
        // case 4:
        // levelNum += 12;
        // }
        setLevelFromMap("tmx/level" + levelNum + ".tmx");
    }

    private void resetLevel() {
        getGameScene().getViewport().fade(() -> {
            // teleport everyone to spawn
            spawnPlayers();

            // reset timer and etc.
            // GIAN reset timer here
            x = 0;

            resumeBGMusic();
            set("globalTimer", 1000);
            setLevelFromMap("tmx/level" + levelNum + ".tmx");

        });
    }

    private void finishLevel() {
        // reset all counts
        x = 0;

        // GIAN reset timer here
        set("globalTimer", 1000);

        if (levelNum % 4 == 0) {
            // level end scene
            return;
        }
        // NON-BLOCKING dialogue here

        // TEMP
        levelNum++;
        setLevelFromMap("tmx/level" + levelNum + ".tmx");

        spawnPlayers();

    }

    private void spawnPlayers() {
        spawnpoint = getGameWorld().getEntitiesByType(SPAWNPOINT).get(0);

        System.out.println(spawnpoint.getX());
        System.out.println(spawnpoint.getY());
        if (players.isEmpty()) {
            for (int i = 0; i < 4; i++) {
                if (playersI[i]) {
                    players.add(spawn("player" + ++i, spawnpoint.getX() + x, spawnpoint.getY()));
                }
            }
            return;
        } else {
            players.forEach(player -> {
                player.getComponent(PhysicsComponent.class)
                        .overwritePosition(new Point2D(spawnpoint.getX() + x, spawnpoint.getY()));
                player.setZIndex(Integer.MAX_VALUE);
            });
        }
        x += 50;
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new PlatformerFactory());

        players.clear();

        createLevel();

        spawnPlayers();
        bindKeys();

        spawn("background");

        // setViewPort();
        // resetUINodes();

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
        // inc("levelTime", tpf);

        for (Entity player : players) {
            if (player != null && player.getY() > getAppHeight()) {
                onPlayerDied();
            }
        }
        // resets the properties of the buttons
        if (globalTimerOn) {
            if(FXGL.geti("globalTimer") > 0){
                inc("globalTimer", -1);
            }
        }

        if (FXGL.geti("globalTimer") == 0) {
            onPlayerDied();
        }

    }

    public void onPlayerDied() {

        /* ADD THE IF STATEMENT FOR VIDEOKE */
        // if (!bgMusicPaused) {
        pauseBGMusic();
        playDeathSFX();
        // }
        // Add videoke sound here
    }

    private void pauseBGMusic() {
        System.out.println("pausing...");
    }

    private void resumeBGMusic() {
        System.out.println("resuming...");
    }

    private void playDeathSFX() {
        /* PLAY DEATH SFX HERE */

        resetLevel();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
