package ru.geekbrains.sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.exception.GameException;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.screen.GameScreen;

public class ButtonChooseShip extends ButtonPlay {

    private int shipType;
    private TextureRegion bulletRegion;
    private BulletPool bulletPool;
    private Vector2 bulletV;
    private Vector2[] bulletPosList;
    private float bulletHeight;
    private int damage;

    private float reloadInterval;
    private float reloadTimer;

    private Rect worldBounds;

    public ButtonChooseShip(TextureAtlas atlas, TextureAtlas bulletAtlas, Game game, int shipType, BulletPool bulletPool) throws GameException {
        super(cutTexture(atlas, "player_ship0", shipType), game);
        this.shipType = shipType;
        this.bulletPool = bulletPool;
        bulletRegion = bulletAtlas.findRegion("bulletMainShip");
        bulletV = new Vector2(0, 0.5f);
        bulletPosList = new Vector2[shipType];
        for (int i = 0; i < shipType; i++) {
            bulletPosList[i] = new Vector2();
        }
        reloadInterval = 0.2f;
        reloadTimer = reloadInterval;
        bulletHeight = 0.01f;
        damage = 0;
    }
    private static TextureRegion cutTexture(TextureAtlas atlas, String name, int shipType) {
        TextureRegion[][] regionSplit;
        if (shipType == 1) {
            regionSplit = atlas.findRegion(name + shipType).split(124,135);
        } else {
            regionSplit = atlas.findRegion(name + shipType).split(170,102);
        }

        return regionSplit[0][0];
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
    }

    public void resize(Rect worldBounds, float delta) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        if (delta > 0) {
            setLeft(worldBounds.pos.x + delta);
        } else {
            setRight(worldBounds.pos.x + delta);
        }
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void action() {
        getTogether();
        game.setScreen(new GameScreen(shipType));
    }

    private void autoShoot(float delta, Vector2[] bulletPosList) {
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            for (Vector2 bulletPos : bulletPosList) {
                shoot(bulletPos);
            }
        }
    }

    private void shoot(Vector2 bulletPos) {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage);
    }

    private void getTogether() { }
}
