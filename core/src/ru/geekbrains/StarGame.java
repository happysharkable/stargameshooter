package ru.geekbrains;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	TextureRegion region;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		region = new TextureRegion(img, 10, 10, 150, 150);

		Vector2 v1 = new Vector2(1f, 3f);
		Vector2 v2 = new Vector2(2f, 4f);
		v1.add(v2);
		System.out.println("v1 add v2 = " + v1.x + " " + v1.y);

		v1.set(5f, 7f);
		v2.set(3f, 3f);
		v1.sub(v2);
		System.out.println("v1 sub v2 = " + v1.x + " " + v1.y);

		v1.len();
		System.out.println("v1 len = " + v1.len());

		v1.nor();
		System.out.println("v1 len = " + v1.len());

		v1.set(5f, 7f);
		v2.set(3f, 3f);
		Vector2 v3 = v1.cpy().sub(v2);
		System.out.println("v1 = " + v1.x + " " + v1.y);
		System.out.println("v3 = " + v3.x + " " + v3.y);

		System.out.println("v1 len = " + v1.len());
		v1.scl(0.2f);
		System.out.println("v1 = " + v1.x + " " + v1.y);
		System.out.println("v1 len = " + v1.len());


		System.out.println("v1.dot(v2) = " + v1.dot(v2));

		v1.set(1, 1);
		v2.set(2, 2);
		v1.nor();
		v2.nor();
		System.out.println(Math.acos(v1.dot(v2)));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.7f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setColor(0.1f, 0.3f, 0.6f, 0.5f);
		batch.draw(img, 0, 0);
		batch.draw(region, 200, 200);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
