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
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.image;

public class PlayerComponent extends Component {

    private PhysicsComponent physics;
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;

    // Player Dimensions
    public int pWidth = 32;
    public int pHeight = 42;

    // Player Attributes
    private int movementSpeed = 170;
    private int jumps = 1;
    private boolean isJumping = false;
    private boolean isOnGround = true;

    public int getPlayerSpeed() {
        return movementSpeed;
    }

    public void setPlayerSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void resetPlayerSpeed() {
        movementSpeed = 170;
    }

    // List of players standing on top of this player
    private List<Entity> topPlayers = new ArrayList<>();

    /* For Adding Animations */
    public PlayerComponent() {
        Image image = image("player.png");

        animIdle = new AnimationChannel(image, 8, 32, 42, Duration.seconds(1), 0, 3);
        animWalk = new AnimationChannel(image, 8, 32, 42, Duration.seconds(0.66), 4, 7);

        texture = new AnimatedTexture(animIdle);
        texture.loop();
    }

    @Override
    public void onAdded() {

        entity.getTransformComponent().setScaleOrigin(new Point2D(pWidth / 2, pHeight));

        texture.setScaleX(1.5);
        texture.setScaleY(1.5);
        texture.setTranslateY(-10);

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

        HitBox topSensor = new HitBox("TOP_SENSOR", new Point2D(1, 0), BoundingShape.box(30, 1));

        SensorCollisionHandler s = new SensorCollisionHandler() {
            @Override
            protected void onCollisionBegin(Entity other) {
                if (other.getType() == EntityType.PLAYER) {
                    topPlayers.add(other); // Add the player on top
                    // Check if the player above has any riders and propagate
                    propagateRiders(other);
                }
            }

            @Override
            protected void onCollisionEnd(Entity other) {
                if (other.getType() == EntityType.PLAYER) {
                    topPlayers.remove(other); // Remove the player when it moves away
                }
            }
        };
        physics.addSensor(topSensor, s);
    }

    private void propagateRiders(Entity playerAbove) {
        PlayerComponent abovePlayerComp = playerAbove.getComponent(PlayerComponent.class);
        for (Entity rider : abovePlayerComp.topPlayers) {
            if (!topPlayers.contains(rider)) {
                topPlayers.add(rider);
                // Recursively propagate if that rider also has riders
                propagateRiders(rider);
            }
        }
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
        System.out.println(topPlayers.size());
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
            physics.setVelocityY(-400);
            jumps--;
            isJumping = true;
        }
    }
}
