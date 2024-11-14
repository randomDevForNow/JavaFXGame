package com.sunsofgod;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.SensorCollisionHandler;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;

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
            physics.setVelocityY(-450);
            jumps--;

            // Play jump sound
            play("jump.wav");
        }
    }
}
