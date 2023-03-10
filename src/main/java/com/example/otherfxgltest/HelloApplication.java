package com.example.otherfxgltest;

import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.tiled.TMXLevelLoader;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;
import java.util.Map;

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
        onBtnDown(MouseButton.SECONDARY, () -> {
            player.getComponent(PhysicsComponent.class).setVelocityX(0);
            player.getComponent(PhysicsComponent.class).setVelocityY(0);
        });
        onBtnDown(MouseButton.PRIMARY, () ->
                spawn("bullet", player.getCenter()));
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(30 * 32);
        gameSettings.setHeight(20 * 32);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new GameFactory());
        Level level = getAssetLoader().loadLevel("tmx/map3.tmx", new TMXLevelLoader());
        getGameWorld().setLevel(level);
        player = spawn("player", getAppWidth() / 2, getAppHeight() / 2 );
        spawn("background");
    }

    @Override
    protected void initUI() {
        Label scoreLabel = new Label();
        scoreLabel.setTextFill(Color.BLACK);
        scoreLabel.setFont(Font.font(10.0));
        scoreLabel.textProperty().bind(FXGL.getip("score").asString("Score: %d"));
        FXGL.addUINode(scoreLabel, 0, 0);
    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().setGravity(0, 0);

        onCollisionBegin(EntityType.BULLET, EntityType.BRICK, (bullet, brick) -> {
            bullet.removeFromWorld();
            brick.removeFromWorld();
            FXGL.inc("score", 10);
        });

        onCollisionBegin(EntityType.BULLET, EntityType.MONSTER, (bullet, monster) -> {
            bullet.removeFromWorld();
            monster.removeFromWorld();
            FXGL.inc("score", 15);
        });

        onCollisionBegin(EntityType.BULLET, EntityType.WALL, (bullet, wall) -> {
            bullet.removeFromWorld();
        });

        onCollisionBegin(EntityType.PLAYER, EntityType.BRICK, (player, brick) -> {
            player.removeFromWorld();
        });

        onCollisionBegin(EntityType.PLAYER, EntityType.FRUIT, (player, fruit) -> {
            fruit.removeFromWorld();
        });

        onCollisionBegin(EntityType.PLAYER, EntityType.MONSTER, (player, monster) -> {
            player.removeFromWorld();
        });
    }
}