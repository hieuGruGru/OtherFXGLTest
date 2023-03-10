package com.example.otherfxgltest;

import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.example.otherfxgltest.Components.Enemy.EnemyComponent;
import com.example.otherfxgltest.Components.PlayerComponent;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class GameFactory implements EntityFactory {

    @Spawns("background")
    public Entity newBackground(SpawnData data) {
        return entityBuilder()
                .view(new Rectangle(30 * 32, 20 * 32, Color.rgb(255, 206, 222)))
                .zIndex(-100)
                .with(new IrremovableComponent())
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setFixtureDef(new FixtureDef().friction(0).density(0.1f));
        BodyDef bd = new BodyDef();
        bd.setFixedRotation(true);
        bd.setType(BodyType.DYNAMIC);
        physics.setBodyDef(bd);

        return entityBuilder()
                .from(data)
                .type(EntityType.PLAYER)
                .viewWithBBox("poro.png")
                .with(physics)
                .with(new PlayerComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("bullet")
    public Entity newBullet(SpawnData data) {
        Entity player = getGameWorld().getSingleton(EntityType.PLAYER);
        Point2D direction = getInput().getMousePositionWorld()
                .subtract(player.getCenter());

        return entityBuilder()
                .from(data)
                .type(EntityType.BULLET)
                .viewWithBBox(new Rectangle(10, 2, Color.BLACK))
                .collidable()
                .with(new ProjectileComponent(direction, 2000))
                .with(new OffscreenCleanComponent())
                .build();
    }

    @Spawns("wall")
    public Entity newWall(SpawnData data) {
        return entityBuilder()
                .from(data)
                .type(EntityType.WALL)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("brick")
    public Entity newBrick(SpawnData data) {
        return entityBuilder()
                .from(data)
                .type(EntityType.BRICK)
                .viewWithBBox("brick.png")
                .collidable()
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("monster")
    public Entity newMonster(SpawnData data) {
        return entityBuilder()
                .from(data)
                .type(EntityType.MONSTER)
                .bbox(new HitBox(BoundingShape.box(20, 20)))
                .with(new EnemyComponent(data.get("type"), data.getX(), data.getY()))
                .collidable()
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("devil")
    public Entity newDevil(SpawnData data) {
        return entityBuilder()
                .from(data)
                .type(EntityType.DEVIL)
                .bbox(new HitBox(BoundingShape.box(20, 20)))
                .with(new EnemyComponent(data.get("type"), data.getX(), data.getY()))
                .collidable()
                .with(new PhysicsComponent())
                .build();
    }

}
