package ru.geekbrains.sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.exception.GameException;
import ru.geekbrains.math.Rect;

public class ButtonNewGame extends ButtonPlay {

    public ButtonNewGame(TextureAtlas atlas, Game game) throws GameException {
        super(atlas.findRegion("button_new_game"), game);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
        setTop(-0.03f);
    }
}
