package com.sunsofgod;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.view.KeyView;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.sunsofgod.EntityType.*;

public class PlatformerApp extends GameApplication {

    private static final int MAX_LEVEL = 5;
    private static final int STARTING_LEVEL = 0;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1280);
        settings.setHeight(720);

        settings.setMainMenuEnabled(true);
        System.out.println("Hello!");

        /* Set Loading Screen Here: */

        // settings.setSceneFactory(new SceneFactory() {
        // @Override
        // public LoadingScene newLoadingScene() {
        // return new MainLoadingScene();
        // }
        // });
        // settings.setApplicationMode(ApplicationMode.DEVELOPER);
    }

    /* Get for ending the game (Init level refunds and scoreboard) */
    // private LazyValue<LevelEndScene> levelEndScene = new LazyValue<>(() -> new
    // LevelEndScene());
    

    private Entity[] players = new Entity[2];

    private KeyCode[][] bindings = new KeyCode[][] {
        {KeyCode.W, KeyCode.A, KeyCode.D, KeyCode.E },
        {KeyCode.UP, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SLASH },
        
    };

    private void bindKeys(){
        int counter = 0;
        for (Entity player: players){
            getInput().addAction(new UserAction("Jump"+ counter) {
                @Override
                protected void onActionBegin() {
                    player.getComponent(PlayerComponent.class).jump();
                }
            },bindings[counter][0]);
    
            getInput().addAction(new UserAction("Left"+ counter) {
                @Override
                protected void onAction() {
                    player.getComponent(PlayerComponent.class).left();
                }
    
                @Override
                protected void onActionEnd() {
                    player.getComponent(PlayerComponent.class).stop();
                }
            }, bindings[counter][1]);
    
            getInput().addAction(new UserAction("Right"+ counter) {
                @Override
                protected void onAction() {
                    player.getComponent(PlayerComponent.class).right();
                }
    
                @Override
                protected void onActionEnd() {
                    player.getComponent(PlayerComponent.class).stop();
                }
            }, bindings[counter][2]);
    
           
            getInput().addAction(new UserAction("Use"+ counter) {
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
    
                                makeExitDoor();
                            });
                }
            }, bindings[counter][3]);
            counter ++;
        }
    }


    /* For Global Variables (Refunds of each player) */
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("level", STARTING_LEVEL);
        // vars.put("levelTime", 0.0);
        // vars.put("score", 0);
    }

    @Override
    protected void onPreInit() {
        getSettings().setGlobalMusicVolume(0.25);
        // loopBGM("BGM_dash_runner.wav");
        /* Add Media Player Code here that loops */
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new PlatformerFactory());

        for (Entity player: players){
            player = null;
        }

        System.out.println("ASOOOOOO");

        nextLevel();

        // player must be spawned after call to nextLevel, otherwise player gets removed
        // before the update tick _actually_ adds the player to game world
        for (int i = 0; i < players.length; i++) {
            players[i] = spawn("player", 50 + i * 100, 50);  // Spawn and assign players
            set("player" + i, players[i]);  // Optionally store in the global map
        }

        bindKeys();
        

        spawn("background");

        /* Follows the player (can be turned off) */
        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(-1500, 0, 250 * 70, getAppHeight());
        //viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        viewport.setLazy(true);
    }

    @Override
    protected void initPhysics() {
        
        getPhysicsWorld().setGravity(0, 760);
        getPhysicsWorld().addCollisionHandler(new PlayerButtonHandler());

        // Set collision of player and mail here:

        /* Physics Interaction */

        onCollision(PLAYER, PLAYER, (player1,player2)->{
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

    /* REMOVE THIS */
    @Override
    protected void initUI() {
        if (isMobile()) {
            var dpadView = getInput().createVirtualDpadView();
            var buttonsView = getInput().createXboxVirtualControllerView();

            addUINode(dpadView, 0, getAppHeight() - 290);
            addUINode(buttonsView, getAppWidth() - 280, getAppHeight() - 290);
        }
    }

    @Override
    protected void onUpdate(double tpf) {
        // inc("levelTime", tpf);

        // for (Entity player: players){
        //     if (player.getY() > getAppHeight()) {
        //         onPlayerDied();
        //     }
        // }
    }

    public void onPlayerDied() {
        setLevel(geti("level"));
    }

    private void setLevel(int levelNum) {

        for (Entity player:players){
            if (player != null) {
                player.getComponent(PhysicsComponent.class).overwritePosition(new Point2D(50, 50));
                player.setZIndex(Integer.MAX_VALUE);
            }
        }

        

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
