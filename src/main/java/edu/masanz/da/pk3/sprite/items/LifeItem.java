package edu.masanz.da.pk3.sprite.items;

import edu.masanz.da.pk3.game.GameObject;
import javafx.scene.image.Image;

import static edu.masanz.da.pk3.game.AppConsts.*;

public class LifeItem extends AItem{
    public LifeItem() {
        super(I_LIFE_SPRITE_IMAGE,I_LIFE_SPRITE_ROWS,I_LIFE_SPRITE_COLS);
        currentFrame =  (int)(Math.random() * I_LIFE_SPRITE_COLS);
    }

    @Override
    public void useItem() {
        GameObject.getInstance().getAppStatus().increaseLifes();
    }

    @Override
    public void update() {
        currentFrame = ++currentFrame%cols;
    }
}
