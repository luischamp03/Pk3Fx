package edu.masanz.da.pk3.sprite.enemies;

import edu.masanz.da.pk3.sprite.interfaces.ICanMove;
import javafx.scene.image.Image;

import static edu.masanz.da.pk3.game.AppConsts.*;
import static edu.masanz.da.pk3.game.AppConsts.E_DLRU_LEFT;

public abstract class AMovableEnemy extends AEnemy implements ICanMove {

    public AMovableEnemy(Image img, int rows, int cols) {
        super(img, rows, cols);
    }

    public AMovableEnemy(Image img, int rows, int cols, int rowSpace, int colSpace) {
        super(img, rows, cols, rowSpace, colSpace);
    }

    @Override
    public void update() {
        if (canGoToNextPosition()) {
            goToNextPosition();
            updateFrame();
        }else {
            changeRoute();
        }
    }

    public void updateFrame(){
        if (Math.abs(xSpeed) < Math.abs(ySpeed) && ySpeed < 0) {
            side = E_DLRU_UP;
        }else if (Math.abs(xSpeed) < Math.abs(ySpeed) && ySpeed > 0) {
            side = E_DLRU_DOWN;
        }else if (Math.abs(xSpeed) > Math.abs(ySpeed) && xSpeed > 0) {
            side = E_DLRU_RIGHT;
        }else if (Math.abs(xSpeed) > Math.abs(ySpeed) && xSpeed < 0) {
            side = E_DLRU_LEFT;
        }
        if (xSpeed != 0 || ySpeed != 0){
            currentFrame = ++currentFrame%cols;
        }else {
            currentFrame = 0;
        }
    }


}
