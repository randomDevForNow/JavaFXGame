package com.sunsofgod;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.SensorCollisionHandler;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.image;

public class PlayerComponent extends Component {

    private PhysicsComponent physics;
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;
    private int jumps = 1;
    private boolean isJumping = false;
    private boolean isOnGround = true;

    // List of players standing on top of this player
    private List<Entity> topPlayers = new ArrayList<>();

    public PlayerComponent() {
        Image image = image("player.png");

        animIdle = new AnimationChannel(image, 4, 32, 42, Duration.seconds(1), 0, 3);
        animWalk = new AnimationChannel(image, 4, 32, 42, Duration.seconds(0.66), 0, 3);

        texture = new AnimatedTexture(animIdle);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);

        physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
            if (isOnGround) {
                if (jumps == 0) { // if has no jump
                    // check if block has friction?
                    jumps = 1;
                    physics.setFixtureDef(new FixtureDef().friction(0.0f));

                }
                isJumping = false;

                this.isOnGround = true;
            } else
                this.isOnGround = false;
        });

        // Rectangle topSensorVisual = new Rectangle(25, 8, Color.RED); // Visualize the
        // sensor
        // entity.getViewComponent().addChild(topSensorVisual);

        // Define the sensor at the top of the player
        HitBox topSensor = new HitBox("TOP_SENSOR", new Point2D(0, 0), BoundingShape.box(16, 8));

        SensorCollisionHandler s = new SensorCollisionHandler() {
            @Override
            protected void onCollisionBegin(Entity other) {
                if (other.getType() == EntityType.PLAYER) {
                    jumps = 0;
                    topPlayers.add(other);
                    System.out.println("Player detected above! Total riders: " + topPlayers.size());
                }
            }

            @Override
            protected void onCollisionEnd(Entity other) {
                if (other.getType() == EntityType.PLAYER) {
                    if (isOnGround) {
                        jumps = 1;
                    }
                    topPlayers.remove(other);
                    System.out.println("Player above moved away! Total riders: " + topPlayers.size());
                }
            }
        };
        physics.addSensor(topSensor, s);
    }

    @Override
    public void onUpdate(double tpf) {

        if (physics.isMovingX()) {
            if (texture.getAnimationChannel() != animWalk) {
                texture.loopAnimationChannel(animWalk);
            }
        } else {
            if (texture.getAnimationChannel() != animIdle) {
                texture.loopAnimationChannel(animIdle);
            }
        }
    }

    public void left() {
        getEntity().setScaleX(-1);
        physics.setVelocityX(-170);

        // Move each top player when moving left
        for (Entity rider : topPlayers) {
            rider.getComponent(PhysicsComponent.class).setVelocityX(-170);
        }
    }

    public void right() {
        getEntity().setScaleX(1);
        physics.setVelocityX(170);

        // Move each top player when moving right
        for (Entity rider : topPlayers) {
            rider.getComponent(PhysicsComponent.class).setVelocityX(170);
        }
    }

    public void stop() {
        physics.setVelocityX(0);
        physics.setAngularVelocity(0);

        // Stop each top player when stopping
        for (Entity rider : topPlayers) {
            rider.getComponent(PhysicsComponent.class).setVelocityX(0);
        }
    }

    public void jump() {
        if (jumps > 0) {
            physics.setVelocityY(-300);
            jumps--;
            isJumping = true;
        }
    }
}
