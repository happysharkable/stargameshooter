package ru.geekbrains.sprites;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Ship;
import ru.geekbrains.exception.GameException;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;

public class MainShip extends Ship {

    private static final int HP = 100;
    private static final float SHIP_HEIGHT = 0.15f;
    private static final float BOTTOM_MARGIN = 0.05f;
    private static final int INVALID_POINTER = -1;

    private boolean pressedLeft;
    private boolean pressedRight;

    private int shipType;
    private Star[] stars;
    private Vector2 parallaxV;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    public MainShip(TextureAtlas atlas, TextureAtlas mainShipAtlas, BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound, int shipType, Star[] stars) throws GameException {
        super(mainShipAtlas.findRegion("player_ship0" + shipType), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.shootSound = shootSound;
        this.shipType = shipType;
        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletV = new Vector2(0, 0.5f);
        bulletPos = new Vector2();
        setShipType(shipType);
        v0 = new Vector2(0.5f, 0);
        v = new Vector2();
        reloadInterval = 0.2f;
        reloadTimer = reloadInterval;
        bulletHeight = 0.01f;
        damage = 1;
        hp = HP;
        this.stars = stars;
        parallaxV = new Vector2(0f, 0f);
    }

    public void startNewGame(Rect worldBounds) {
        flushDestroy();
        hp = HP;
        pressedLeft = false;
        pressedRight = false;
        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
        stop();
        pos.x = worldBounds.pos.x;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(SHIP_HEIGHT);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    public void resize(Rect worldBounds, float posX, float posY) {
        this.worldBounds = worldBounds;
        setHeightProportion(SHIP_HEIGHT);
        this.pos.x = posX;
        this.pos.y = posY;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        switch (shipType) {
            case 1:
                bulletPosList[0].set(pos.x, pos.y + getHalfHeight() * 0.8f);
                break;

            case 2:
                bulletPosList[0].set(pos.x - getHalfWidth() * 0.85f, pos.y + getHalfHeight() * 0.25f);
                bulletPosList[1].set(pos.x + getHalfWidth() * 0.84f, pos.y + getHalfHeight() * 0.25f);
                break;

            default:
                break;
        }

        autoShoot(delta, bulletPosList);
        starsParallax();
        drawFlame();

        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom());
    }

    private void drawFlame() {

    }

    private void moveRight() {
        v.set(v0);
        parallaxV.set(-0.001f, 0f);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
        parallaxV.set(0.001f, 0f);
    }

    private void stop() {
        v.setZero();
        parallaxV.setZero();
    }

    private void starsParallax() {
        for (Star star : stars) {
            star.pos.add(parallaxV);
        }
    }

    private void setShipType(int shipType) {
        this.shipType = shipType;
        bulletPosList = new Vector2[shipType];
        for (int i = 0; i < shipType; i++) {
            bulletPosList[i] = new Vector2();
        }
        hp = HP;
    }
}
