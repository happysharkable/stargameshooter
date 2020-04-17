package ru.geekbrains.sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.ScaledButton;
import ru.geekbrains.exception.GameException;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.MenuScreen;

public class ButtonPlay extends ScaledButton {

    protected final Game game;
    private MenuScreen screen;

    public ButtonPlay(TextureAtlas atlas, Game game, MenuScreen screen) throws GameException {
        super(atlas.findRegion("button_start"));
        this.game = game;
        this.screen = screen;
    }

    public ButtonPlay(TextureRegion region, Game game) throws GameException {
        super(region);
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.1f);
        setLeft(worldBounds.getLeft() + 0.05f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void action() {
        screen.changeState();
    }
}
