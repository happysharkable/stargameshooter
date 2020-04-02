package ru.geekbrains.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.exception.GameException;
import ru.geekbrains.math.Rect;

public class Logo extends Sprite {

    private Vector2 direction;
    private Vector2 v;
    private Vector2 touch;
    private final float SPEED = 0.02f;

    public Logo(Texture texture) throws GameException {
        super(new TextureRegion(texture));
        direction = new Vector2(0f, 0f);
        v = new Vector2(0f, 0f);
        touch = new Vector2(0f, 0f);
    }

    @Override
    public void update(float delta) {
        if ((int)(touch.x * 100) == Math.round(pos.x * 100) && (int)(touch.y * 100) == Math.round(pos.y * 100))
            v.set(0f, 0f);

        pos.add(v.cpy().scl(SPEED));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        pos.set(worldBounds.pos);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.touch = touch;
        v.set(direction.set(touch.sub(pos)));
        setAngle(360 - direction.angle(new Vector2(0f, 1f)));
        return false;
    }
}
