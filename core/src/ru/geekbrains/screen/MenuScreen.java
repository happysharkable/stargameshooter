package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.exception.GameException;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.sprites.Background;
import ru.geekbrains.sprites.ButtonChooseShip;
import ru.geekbrains.sprites.ButtonExit;
import ru.geekbrains.sprites.ButtonPlay;
import ru.geekbrains.sprites.GameText;
import ru.geekbrains.sprites.Star;

public class MenuScreen extends BaseScreen {

    private enum State {MENU, CHOOSE_SHIP, PAUSE}
    private State state;
    private State prevState;

    private static final int STAR_COUNT = 256;

    private final Game game;

    private Texture bg;
    private Background background;

    private TextureAtlas atlas;
    private TextureAtlas shipAtlas;
    private TextureAtlas bulletAtlas;
    private BulletPool bulletPool;

    private Music music;

    private Star[] stars;
    private GameText logo;
    private GameText chooseShip;
    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;
    private ButtonChooseShip buttonChooseShip1;
    private ButtonChooseShip buttonChooseShip2;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        bulletAtlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        shipAtlas = new TextureAtlas(Gdx.files.internal("textures/myTexturePack.atlas"));
        bulletPool = new BulletPool();
        initSprites();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/menu_music.mp3"));
        music.setLooping(true);
        music.play();
        state = State.MENU;
    }

    @Override
    public void render(float delta) {
       update(delta);
       draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        bg.dispose();
        atlas.dispose();
        shipAtlas.dispose();
        bulletAtlas.dispose();
        bulletPool.dispose();
        music.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        logo.resize(worldBounds);
        chooseShip.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
        buttonChooseShip1.resize(worldBounds, -0.1f);
        buttonChooseShip2.resize(worldBounds, 0.01f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        switch (state) {
            case MENU:
                buttonExit.touchDown(touch, pointer, button);
                buttonPlay.touchDown(touch, pointer, button);
                break;
            case CHOOSE_SHIP:
                buttonChooseShip1.touchDown(touch, pointer, button);
                buttonChooseShip2.touchDown(touch, pointer, button);
                break;
        }

        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        switch (state) {
            case MENU:
                buttonExit.touchUp(touch, pointer, button);
                buttonPlay.touchUp(touch, pointer, button);
                break;
            case CHOOSE_SHIP:
                buttonChooseShip1.touchUp(touch, pointer, button);
                buttonChooseShip2.touchUp(touch, pointer, button);
                break;
        }

        return false;
    }

    @Override
    public void pause() {
        prevState = state;
        state = MenuScreen.State.PAUSE;
    }

    @Override
    public void resume() {
        state = prevState;
    }

    public void changeState() {
        state = State.CHOOSE_SHIP;
    }

    private void initSprites() {
        try {
            background = new Background(bg);
            logo = new GameText(shipAtlas, "logo", 0.5f, -0.1f);
            chooseShip = new GameText(shipAtlas, "choose_ship", 0.05f, 0f);
            stars = new Star[STAR_COUNT];
            for (int i = 0; i < STAR_COUNT; i++) {
                stars[i] =  new Star(atlas);
            }
            buttonExit = new ButtonExit(shipAtlas);
            buttonPlay = new ButtonPlay(shipAtlas, game, this);
            buttonChooseShip1 = new ButtonChooseShip(shipAtlas, bulletAtlas, game, 1, bulletPool);
            buttonChooseShip2 = new ButtonChooseShip(shipAtlas, bulletAtlas, game, 2, bulletPool);
        } catch (GameException e) {
            throw new RuntimeException(e);
        }
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        if (state == State.CHOOSE_SHIP) {
            buttonChooseShip1.update(delta);
            buttonChooseShip2.update(delta);
            bulletPool.updateActiveSprites(delta);
        }
    }

    private void draw() {
        Gdx.gl.glClearColor(0.5f, 0.7f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        switch (state) {
            case MENU:
                logo.draw(batch);
                buttonExit.draw(batch);
                buttonPlay.draw(batch);
                break;
            case CHOOSE_SHIP:
                chooseShip.draw(batch);
                buttonChooseShip1.draw(batch);
                buttonChooseShip2.draw(batch);
                bulletPool.drawActiveSprites(batch, false);
                break;
        }
        batch.end();
    }

}
