package com.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.FlappyDemo;

/**
 * Created by Onurkan on 8/13/2017.
 */

public class MenuState extends State {

    private Texture background,
            playButton,
            highScore,
            title;
    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);

        background = new Texture("DayBackground.png");
        playButton = new Texture("PlayBtn.png");
        title = new Texture("FlappyBirdtxtView.png");
        highScore = new Texture("HighScoreBtn.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));

        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.draw(background, 0, 0);
        sb.draw(playButton, cam.position.x - playButton.getWidth() / 2 + 70 , cam.position.y);
        sb.draw(title, cam.position.x - title.getWidth() / 2  , cam.position.y + 100);
        sb.draw(highScore, cam.position.x - highScore.getWidth() / 2 - 70 , cam.position.y);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
        System.out.println("Menu State Disposed");
    }
}
