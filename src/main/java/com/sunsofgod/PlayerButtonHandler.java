package com.sunsofgod;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.sunsofgod.EntityType;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;

public class PlayerButtonHandler extends CollisionHandler {

    public PlayerButtonHandler() {
        super(EntityType.PLAYER, EntityType.BUTTON);
    }

}
