package edu.masanz.da.pk3.sprite.enemies;

import edu.masanz.da.pk3.sprite.interfaces.IHaveShield;

import static edu.masanz.da.pk3.game.AppConsts.*;

public class BombEnemy extends AEnemy implements IHaveShield {
    private int shield;

    public BombEnemy() {
        super(E_BOMB_SPRITE_IMAGE, E_BOMB_SPRITE_ROWS, E_BOMB_SPRITE_COLS);
        shield = E_BOMB_SHIELD;
        setSpeed(E_BOMB_SPEED,0);
    }

    @Override
    public void update() {
        currentFrame = ++currentFrame%cols;
    }

    @Override
    public boolean impact() {
        shield--;
        return shield == 0;
    }
}
