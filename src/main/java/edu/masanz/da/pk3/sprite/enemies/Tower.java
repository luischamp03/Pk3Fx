package edu.masanz.da.pk3.sprite.enemies;

import edu.masanz.da.pk3.sprite.weaponry.HeroTargetGun;

import static edu.masanz.da.pk3.game.AppConsts.*;

public class Tower extends AEnemy{

    public Tower() {
        super(E_TOWER_SPRITE_IMAGE, E_TOWER_SPRITE_ROWS, E_TOWER_SPRITE_COLS);
        currentFrame = (int) (Math.random()*cols);
        setWeapon(new HeroTargetGun(this));
    }

    @Override
    public void update() {
        updateFrame();
    }

    public void updateFrame() {
        currentFrame = ++currentFrame%cols;
    }

}
