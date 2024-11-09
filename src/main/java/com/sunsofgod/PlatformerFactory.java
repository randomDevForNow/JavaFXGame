package com.sunsofgod;

import com.almasb.fxgl.dsl.components.LiftComponent;
import com.almasb.fxgl.dsl.views.ScrollingBackgroundView;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.input.view.KeyView;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.SensorCollisionHandler;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.ui.FontType;

import javafx.beans.binding.Bindings;
import javafx.geometry.Point2D;
import javafx.scene.CacheHint;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.sunsofgod.EntityType.*;

public class PlatformerFactory implements EntityFactory {

        @Spawns("background")
        public Entity newBackground(SpawnData data) {
                return entityBuilder()
                                .view(new ScrollingBackgroundView(texture("background/manila.png").getImage(),
                                                getAppWidth(),
                                                getAppHeight()))
                                .zIndex(-1)
                                .with(new IrremovableComponent())
                                .build();
        }

        @Spawns("platform")
        public Entity newPlatform(SpawnData data) {
                return entityBuilder(data)
                                .type(PLATFORM)
                                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),
                                                data.<Integer>get("height"))))
                                .with(new PhysicsComponent())
                                .build();
        }

        @Spawns("exitTrigger")
        public Entity newExitTrigger(SpawnData data) {
                return entityBuilder(data)
                                .type(EXIT_TRIGGER)
                                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),
                                                data.<Integer>get("height"))))
                                .with(new CollidableComponent(true))
                                .build();
        }

        @Spawns("doorTop")
        public Entity newDoorTop(SpawnData data) {
                return entityBuilder(data)
                                .type(DOOR_TOP)
                                .opacity(0)
                                .build();
        }

        @Spawns("doorBot")
        public Entity newDoorBot(SpawnData data) {
                return entityBuilder(data)
                                .type(DOOR_BOT)
                                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),
                                                data.<Integer>get("height"))))
                                .opacity(0)
                                .with(new CollidableComponent(false))
                                .build();
        }

        @Spawns("player")
        public Entity newPlayer(SpawnData data) {

                PlayerComponent playerComponent = new PlayerComponent();
                PhysicsComponent physics = new PhysicsComponent();

                physics.setBodyType(BodyType.DYNAMIC);

                // Add ground sensor for detecting ground contact
                physics.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(1, 40), BoundingShape.box(30, 2)));

                // Avoid sticking to walls
                physics.setFixtureDef(new FixtureDef().friction(0.0f));

                // Player HitBox (starting from 0, 0 [top left] to Bounding Box [bottom right])
                HitBox playerBBox = new HitBox(new Point2D(0, 0), BoundingShape.box(32, 42));

                // Build the player entity
                Entity player = entityBuilder(data)
                                .type(PLAYER)
                                .bbox(playerBBox)
                                .with(physics)
                                .with(new CollidableComponent(true))
                                .with(new IrremovableComponent())
                                .with(playerComponent)
                                .build();

                // Return the player entity
                return player;
        }

        @Spawns("exitSign")
        public Entity newExit(SpawnData data) {
                return entityBuilder(data)
                                .type(EXIT_SIGN)
                                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),
                                                data.<Integer>get("height"))))
                                .with(new CollidableComponent(true))
                                .build();
        }

        @Spawns("keyPrompt")
        public Entity newPrompt(SpawnData data) {
                return entityBuilder(data)
                                .type(KEY_PROMPT)
                                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),
                                                data.<Integer>get("height"))))
                                .with(new CollidableComponent(true))
                                .build();
        }

        @Spawns("keyCode")
        public Entity newKeyCode(SpawnData data) {
                String key = data.get("key");

                KeyCode keyCode = KeyCode.getKeyCode(key);

                var lift = new LiftComponent();
                lift.setGoingUp(true);
                lift.yAxisDistanceDuration(6, Duration.seconds(0.76));

                var view = new KeyView(keyCode, Color.YELLOW, 24);
                view.setCache(true);
                view.setCacheHint(CacheHint.SCALE);

                return entityBuilder(data)
                                .view(view)
                                .with(lift)
                                .zIndex(100)
                                .build();
        }

        // TO EDIT
        @Spawns("button")
        public Entity newButton(SpawnData data) {
                var keyEntity = getGameWorld().create("keyCode",
                                new SpawnData(data.getX(), data.getY() - 50).put("key", "E"));
                keyEntity.getViewComponent().setOpacity(0);

                return entityBuilder(data)
                                .type(BUTTON)
                                .viewWithBBox(texture("button.png", 20, 18))
                                .with(new CollidableComponent(true))
                                .with("keyEntity", keyEntity)
                                .build();
        }
}
