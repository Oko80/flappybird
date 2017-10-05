package com.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.game.FlappyDemo;
import com.game.sprites.Bird;
import com.game.sprites.Tube;

import java.util.Calendar;

/**
 * Created by Onurkan on 8/13/2017.
 */

public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;
    private Bird bird;
    private Texture background;
    private Texture backgroundNG;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);



    private Array<Tube> tubes;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 200);
        cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);
        background = new Texture("DayBackground.png");
        ground = new Texture("Ground.png");
        backgroundNG = new Texture("NightBackground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth/2 , GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);


        tubes = new Array<Tube>();

        for(int i = 1 ; i<= TUBE_COUNT ; i++){
            tubes.add(new Tube( i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;
        for(Tube tube : tubes){
            if(cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT ));
            }

           if(tube.collides(bird.getBounds())){

                   gsm.set(new FinishState(gsm));
               dispose();

           }
        }
        if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET){
            gsm.set(new FinishState(gsm));
            dispose();
        }
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        if(hour > 8 && hour < 20 ) {
            sb.draw(background, cam.position.x - (cam.viewportWidth / 2), 0, cam.viewportWidth, cam.viewportHeight);
        }
        else{
            sb.draw(backgroundNG,cam.position.x - (cam.viewportWidth / 2), 0, cam.viewportWidth, cam.viewportHeight);
        }
        sb.draw(bird.getTexture(),bird.getPosition().x, bird.getPosition().y);
        for(Tube tube : tubes){
        sb.draw(tube.getTopTube(), tube.getPosTopTube().x , tube.getPosTopTube().y );
        sb.draw(tube.getBottomTube(), tube.getPosBotTube().x , tube.getPosBotTube().y);
        }

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.end();
    }

    @Override
    public void dispose() {
            background.dispose();
        bird.dispose();
        ground.dispose();
        for(Tube tube: tubes)
            tube.dispose();
    }

    private void updateGround(){
        if(cam.position.x - (cam.viewportWidth/2 )> groundPos1.x + ground.getWidth()){
            groundPos1.add(ground.getWidth() * 2, 0);

        }
        else if(cam.position.x - (cam.viewportWidth/2 )> groundPos2.x + ground.getWidth()){
            groundPos2.add(ground.getWidth() * 2, 0);

        }
    }
}
