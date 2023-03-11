package com.example.otherfxgltest.Components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class PlayerComponent extends Component {
        private static final double speed = FXGL.geti("playerSpeed");
        private final AnimatedTexture texture;
        private final AnimationChannel left;
        private final AnimationChannel right;
        private final AnimationChannel upDown;
        private final AnimationChannel stop;
        private PhysicsComponent physicsComponent;

    public PlayerComponent() {
        left = new AnimationChannel(FXGL.image("poro--left.png"), Duration.seconds(0.5), 4);
        right = new AnimationChannel(FXGL.image("poro--right.png"), Duration.seconds(0.5), 4);
        upDown = new AnimationChannel(FXGL.image("poro--updown.png"), Duration.seconds(0.5), 4);
        stop = new AnimationChannel(FXGL.image("poro.png"), Duration.seconds(0.5), 1);
        texture = new AnimatedTexture(upDown);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(upDown);
    }

    public void left() {
        physicsComponent.setVelocityX(-speed);
        if (texture.getAnimationChannel() != left) {
            texture.loopAnimationChannel(left);
        }
    }

    public void right() {
        physicsComponent.setVelocityX(speed);
        if (texture.getAnimationChannel() != right) {
            texture.loopAnimationChannel(right);
        }
    }

    public void up() {
        physicsComponent.setVelocityY(-speed);
        if (texture.getAnimationChannel() != upDown) {
            texture.loopAnimationChannel(upDown);
        }
    }

    public void down() {
        physicsComponent.setVelocityY(speed);
        if (texture.getAnimationChannel() != upDown) {
            texture.loopAnimationChannel(upDown);
        }
    }

    public void stop() {
        physicsComponent.setVelocityX(0);
        physicsComponent.setVelocityY(0);
        if (texture.getAnimationChannel() != stop) {
            texture.loopAnimationChannel(stop);
        }
    }
}
