package ru.geekbrains.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.exception.GameException;
import ru.geekbrains.math.Rect;

public class MainShip extends Sprite {

    private float velocity = 0.005f;
    private Vector2 v;

    private int pointer;
    private boolean pointerPressed;
    private boolean keyPressed;

    private Rect worldBounds;

    public MainShip(TextureAtlas atlas) throws GameException {
        super(cutTexture(atlas, "main_ship"));
        v = new Vector2(0f, 0f);
    }

    private static TextureRegion cutTexture(TextureAtlas atlas, String name) {
        int tileWidth = atlas.findRegion(name).getRegionWidth() / 2;
        int tileHeight = atlas.findRegion(name).getRegionHeight();
        TextureRegion[][] regionSplit = atlas.findRegion(name).split(tileWidth,tileHeight);
        return regionSplit[0][0];
    }

    @Override
    public void update(float delta) {
        if (pointerPressed || keyPressed)
            pos.add(v);

        if (getLeft() <= worldBounds.getLeft() || getRight() >= worldBounds.getRight())
            v.setZero();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        pos.set(0f, worldBounds.getBottom() + 0.15f);
        this.worldBounds = worldBounds;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (pointerPressed) {
            return false;
        }
        this.pointer = pointer;
        pointerPressed = true;
        if (touch.x > 0)
            v.set(velocity, 0);
        else if(touch.x < 0)
            v.set(-velocity, 0);
        else
            v.setZero();

        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (this.pointer != pointer || !pointerPressed) {
            return false;
        }
        pointerPressed = false;
        return false;
    }

    public boolean keyDown(int keycode) {
        if (!keyPressed && (keycode == 29 || keycode == 32)) {
            keyPressed = true;
            v.set(keycode == 29 ? -velocity : velocity, 0f);
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        if (keyPressed && (keycode == 29 || keycode == 32)) {
            keyPressed = false;
            v.setZero();
        }
        return false;
    }
}
