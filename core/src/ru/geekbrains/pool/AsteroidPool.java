package ru.geekbrains.pool;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprites.Asteroid;

public class AsteroidPool extends SpritesPool<Asteroid> {

    private ExplosionPool explosionPool;
    private Rect worldBounds;

    public AsteroidPool(ExplosionPool explosionPool, Rect worldBounds) {
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
    }

    @Override
    protected Asteroid newObject() {
        return new Asteroid(explosionPool, worldBounds);
    }

}
