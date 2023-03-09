package com.example.otherfxgltest;

import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.tiled.TMXLevelLoader;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import static com.almasb.fxgl.dsl.FXGL.*;

public class HelloApplication extends GameApplication {

    public static void main(String[] args) {
        launch(args);
    }

    private Entity player;

    @Override
    protected void initInput() {
        onKey(KeyCode.W, () -> player.getComponent(PhysicsComponent.class).setVelocityY(-160));
        onKey(KeyCode.S, () -> player.getComponent(PhysicsComponent.class).setVelocityY(160));
        onKey(KeyCode.A, () -> player.getComponent(PhysicsComponent.class).setVelocityX(-160));
        onKey(KeyCode.D, () -> player.getComponent(PhysicsComponent.class).setVelocityX(160));
        onBtnDown(MouseButton.PRIMARY, () ->
                spawn("bullet", player.getCenter()));

    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(20 * 64);
        gameSettings.setHeight(15 * 64);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new GameFactory());
        Level level = getAssetLoader().loadLevel("tmx/map1.tmx", new TMXLevelLoader());
        getGameWorld().setLevel(level);
        player = spawn("player", getAppWidth() / 2, getAppHeight() / 2 );

    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().setGravity(0, 0);

        onCollisionBegin(EntityType.BULLET, EntityType.BRICK, (bullet, brick) -> {
            bullet.removeFromWorld();
            brick.removeFromWorld();
        });

        onCollisionBegin(EntityType.BULLET, EntityType.WALL, (bullet, wall) -> {
            bullet.removeFromWorld();
        });

        onCollisionBegin(EntityType.PLAYER, EntityType.BRICK, (player, brick) -> {
            player.removeFromWorld();
        });
    }
}