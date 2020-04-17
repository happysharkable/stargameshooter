package ru.geekbrains.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.exception.GameException;
import ru.geekbrains.math.Rect;

public class GameText extends Sprite {

    private float height;
    private float bottom;

    public GameText(TextureAtlas atlas, String name, float height, float bottom) throws GameException {
        super(atlas.findRegion(name));
        this.height = height;
        this.bottom = bottom;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(height);
        setBottom(bottom);
    }
}
