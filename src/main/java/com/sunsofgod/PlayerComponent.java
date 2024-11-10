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

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class PlayerComponent extends Component {

    private PhysicsComponent physics;
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;

    private static final double MAX_VELOCITY_X = 300.0;

    // Player Dimensions
    public int pWidth = 32;
    public int pHeight = 42;

    // Player Attributes
    private int movementSpeed = 170;
    private int jumps = 1;
    private boolean isOnGround = true;
    private boolean stopped = true;
    private boolean slip = false;

    public boolean getSlip() {
        return slip;
    }

    public void setSlip(boolean slip) {
        this.slip = slip;
    }

    public boolean getStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public int getPlayerSpeed() {
        return movementSpeed;
    }

    public void setPlayerSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void resetPlayerSpeed() {
        movementSpeed = 170;
    }

    private List<Entity> topPlayers = new ArrayList<>();

    /* For Adding Animations */
    public PlayerComponent(Image img) {
        Image image = img;

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
            this.isOnGround = isOnGround;
            if (isOnGround) {
                if (jumps == 0) {
                    jumps = 1;
                }
                this.isOnGround = true;
            } else {
                if (jumps > 0)
                    jumps = 0;
            }
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
        abovePlayerComp.setPlayerSpeed(170);
        abovePlayerComp.setSlip(false);
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

        if (!isOnGround) {
            stopped = false;
        }

        double carrierVelocityX = physics.getVelocityX();

        if (Math.abs(carrierVelocityX) > MAX_VELOCITY_X) {
            carrierVelocityX = Math.signum(carrierVelocityX) * MAX_VELOCITY_X;
            physics.setVelocityX(carrierVelocityX);
        }

        for (Entity rider : topPlayers) {
            PhysicsComponent riderPhysics = rider.getComponent(PhysicsComponent.class);
            double riderVelocityX = riderPhysics.getVelocityX();

            if (Math.abs(riderVelocityX) > MAX_VELOCITY_X) {
                riderVelocityX = Math.signum(riderVelocityX) * MAX_VELOCITY_X;
                riderPhysics.setVelocityX(riderVelocityX);
            }

            if (slip) {
                riderPhysics.setVelocityX(carrierVelocityX + riderVelocityX);
            } else if (!slip && Math.abs(carrierVelocityX) > 0) {
                if (Math.abs(riderVelocityX) < Math.abs(carrierVelocityX)) {
                    riderPhysics.setVelocityX(carrierVelocityX);
                }
            }
        }

    }

    public void left() {
        texture.setScaleX(-1.5);

        if (slip) {
            physics.applyForce(new Point2D(-movementSpeed, 0), entity.getPosition());
        } else
            physics.setVelocityX(-movementSpeed);
    }

    public void right() {

        texture.setScaleX(1.5);

        if (slip) {
            physics.applyForce(new Point2D(movementSpeed, 0), entity.getPosition());
        } else
            physics.setVelocityX(movementSpeed);
    }

    public void stop() {

        if (slip) {

        } else {
            physics.setVelocityX(0);
            physics.setAngularVelocity(0);
        }
    }

    public void jump() {
        if (jumps > 0) {
            physics.setVelocityY(-400);
            jumps--;
        }
    }
}
