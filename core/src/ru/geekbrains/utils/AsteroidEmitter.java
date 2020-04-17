package ru.geekbrains.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.AsteroidPool;
import ru.geekbrains.sprites.Asteroid;

public class AsteroidEmitter {

    private static final int ASTEROID_HP = 1;
    private static final int ASTEROID_DAMAGE = 10;
    private static final float ASTEROID_ONE_HEIGHT = 0.05f;
    private static final float ASTEROID_ONE_RELOAD_INTERVAL = 3f;


    private static final float ASTEROID_TWO_HEIGHT = 0.06f;
    private static final float ASTEROID_TWO_RELOAD_INTERVAL = 4f;

    private static final float ASTEROID_THREE_HEIGHT = 0.07f;
    private static final float ASTEROID_THREE_RELOAD_INTERVAL = 1f;

    private Rect worldBounds;

    private float generateInterval = 5f;
    private float generateTimer;

    private final TextureRegion[] asteroidOne = new TextureRegion[2];
    private final TextureRegion[] asteroidTwo = new TextureRegion[2];
    private final TextureRegion[] asteroidThree = new TextureRegion[2];

    private final Vector2 asteroidSmallV;
    private final Vector2 asteroidMediumV;
    private final Vector2 asteroidBigV;

    private final AsteroidPool asteroidPool;

    public AsteroidEmitter(Texture texture, AsteroidPool asteroidPool, Rect worldBounds) {
        this.worldBounds = worldBounds;
        this.asteroidPool = asteroidPool;
        this.asteroidOne[0] = new TextureRegion(texture, 0, 0, 110, 80);
        this.asteroidTwo[0] = new TextureRegion(texture, 0, 80, 110, 80);
        this.asteroidThree[0] = new TextureRegion(texture, 0, 160, 110, 80);
        this.asteroidOne[1] = new TextureRegion(texture, 0, 0, 110, 80);
        this.asteroidTwo[1] = new TextureRegion(texture, 0, 80, 110, 80);
        this.asteroidThree[1] = new TextureRegion(texture, 0, 160, 110, 80);
        this.asteroidSmallV = new Vector2(0, -0.25f);
        this.asteroidMediumV = new Vector2(0, -0.035f);
        this.asteroidBigV = new Vector2(0, -0.05f);
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Asteroid asteroid = asteroidPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                asteroid.set(
                        asteroidOne,
                        asteroidSmallV,
                        ASTEROID_DAMAGE,
                        ASTEROID_ONE_RELOAD_INTERVAL,
                        ASTEROID_HP,
                        ASTEROID_ONE_HEIGHT
                );
            } else if (type < 0.8f) {
                asteroid.set(
                        asteroidTwo,
                        asteroidMediumV,
                        ASTEROID_DAMAGE,
                        ASTEROID_TWO_RELOAD_INTERVAL,
                        ASTEROID_HP,
                        ASTEROID_TWO_HEIGHT
                );
            } else {
                asteroid.set(
                        asteroidThree,
                        asteroidBigV,
                        ASTEROID_DAMAGE,
                        ASTEROID_THREE_RELOAD_INTERVAL,
                        ASTEROID_HP,
                        ASTEROID_THREE_HEIGHT
                );
            }
            asteroid.pos.x = Rnd.nextFloat(worldBounds.getLeft() + asteroid.getHalfWidth(), worldBounds.getRight() - asteroid.getHalfWidth());
            asteroid.setBottom(worldBounds.getTop());
        }
    }
}

