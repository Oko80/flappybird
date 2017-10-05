package com.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.FlappyDemo;

/**
 * Created by Onurkan on 8/13/2017.
 */



public class FinishState extends State {

    private Texture background,
             GameOver;


    public FinishState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);
        background = new Texture("DayBackground.png");
        GameOver = new Texture("GameOver.png");
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0, FlappyDemo.WIDTH, FlappyDemo.HEIGHT);
        sb.draw(GameOver, cam.position.x - GameOver.getWidth() / 2  , cam.position.y );
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        GameOver.dispose();
        System.out.println("Finish State Disposed");
    }
}
