package edu.masanz.da.pk3.sprite.enemies;

import edu.masanz.da.pk3.game.GameArea;
import edu.masanz.da.pk3.sprite.weaponry.CrossGun;

import static edu.masanz.da.pk3.game.AppConsts.*;

public class FlyingBouncerEnemy extends AMovableEnemy {

    public FlyingBouncerEnemy() {
        super(E_VWX_SPRITE_IMAGE, E_VWX_SPRITE_ROWS, E_VWX_SPRITE_COLS);
        setSpeed(E_VWX_SPEED, 0);
        setWeapon(new CrossGun(this));
    }

    public FlyingBouncerEnemy(int angdeg){
        this();
        setSpeed(E_VWX_SPEED,angdeg);
    }

    @Override
    public boolean canGoToNextPosition() {
        if (x > GameArea.rect.right - width - xSpeed || x + xSpeed < GameArea.rect.left) {
            return false;
        }
        if (y + height + ySpeed > GameArea.rect.bottom || y + ySpeed < GameArea.rect.top) {
            return false;
        }
        return true;
    }

    @Override
    public void goToNextPosition() {
        x = x + xSpeed;
        y = y + ySpeed;
    }

    @Override
    public void changeRoute() {
        if (x > GameArea.rect.right - width - xSpeed || x + xSpeed < GameArea.rect.left) {
           xSpeed = -xSpeed;
        }
        if (y + height + ySpeed > GameArea.rect.bottom || y + ySpeed < GameArea.rect.top) {
            ySpeed = -ySpeed;
        }
    }

    @Override
    public void updateFrame() {
        if(currentFrame == 0) {
            currentFrame++;
        } else {
            currentFrame = 0;
        }
    }
}
