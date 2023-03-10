package com.example.otherfxgltest.Components.Enemy;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;

import java.util.Random;

public class EnemyComponent extends Component {
    private final String name;
    private final double x;
    private final double y;

    private final Texture left;
    private final Texture right;
    private final Texture upDown;

    public static final double speed = FXGL.geti("enemySpeed");
    private static final Random RANDOM = new Random();
    private double dx = 0.0;
    private double dy = -speed;

    public EnemyComponent(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
        left = FXGL.texture(name + "-left.png");
        right = FXGL.texture(name + "-right.png");
        upDown = FXGL.texture(name + "-updown.png");
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(upDown);
    }

    @Override
    public void onUpdate(double tpf) {
        entity.translateX(dx * tpf);
        entity.translateY(dy * tpf);
    }

    public void turn() {
        if (dx < 0.0) {
            entity.translateX(2);
            dx = 0.0;
            dy = getRandomSpeedAndDirection();
        } else if (dx > 0.0) {
            entity.translateX(-2);
            dx = 0.0;
            dy = getRandomSpeedAndDirection();
        } else if (dy < 0.0) {
            entity.translateY(2);
            dy = 0.0;
            dx = getRandomSpeedAndDirection();
        } else {
            entity.translateY(-2);
            dy = 0.0;
            dx = getRandomSpeedAndDirection();
        }

        if (dx < 0.0) {
            entity.getViewComponent().removeChild(upDown);
            entity.getViewComponent().addChild(left);
        } else if (dx > 0.0) {
            entity.getViewComponent().removeChild(upDown);
            entity.getViewComponent().addChild(right);
        } else {
            entity.getViewComponent().removeChild(left);
            entity.getViewComponent().removeChild(right);
            entity.getViewComponent().addChild(upDown);
        }
    }

    private double getRandomSpeedAndDirection() {
        return RANDOM.nextBoolean() ? speed : -speed;
    }

    public void respawn() {
        entity.removeFromWorld();
        FXGL.spawn("Ghost", new SpawnData(x, y).put("name", name));
    }
}

