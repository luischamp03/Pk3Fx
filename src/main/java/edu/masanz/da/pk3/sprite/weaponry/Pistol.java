package edu.masanz.da.pk3.sprite.weaponry;

import edu.masanz.da.pk3.sprite.ASprite;
import edu.masanz.da.pk3.sprite.interfaces.IIsDisposable;

import java.util.ArrayList;
import java.util.List;

import static edu.masanz.da.pk3.game.AppConsts.*;
import static edu.masanz.da.pk3.game.AppConsts.HERO_RIGHT;

public class Pistol extends AWeapon implements IIsDisposable {

    public Pistol(ASprite owner) {
        super(owner);
        isRechargable = true;
        ammunition = PISTOL_INITIAL_AMMUNITION;
    }

    @Override
    public List<AShot> shoot() {
        List<AShot> list = new ArrayList<>();
        if (isRechargable) {
            if (ammunition == 0) {
                return list;
            }
            ammunition--;
        }
        AShot shot = shootOnce();
        list.add(shot);
        return list;
    }

    public AShot shootOnce() {
        AShot shot = null;
        // if the sprite is not moving, the shot is created depending on the side the hero is facing
        if (owner.getXSpeed() == 0 && owner.getYSpeed() == 0) {
            switch (owner.getSide()) {
                case HERO_DOWN:
                    shot = new Shot(HEROSHOT_SPRITE_IMAGE, 0, -SHOT_SPEED);
                    shot.setPos(owner.getX() + owner.getWidth()/2 - shot.getRect().width()/2, owner.getY() + owner.getHeight()/2);
                    break;
                case HERO_UP:
                    shot = new Shot(HEROSHOT_SPRITE_IMAGE, 0, SHOT_SPEED);
                    shot.setPos(owner.getX() + owner.getWidth()/2 - shot.getRect().width()/2, owner.getY() - shot.getRect().height());
                    break;
                case HERO_LEFT:
                    shot = new Shot(HEROSHOT_SPRITE_IMAGE, -SHOT_SPEED, 0);
                    shot.setPos(owner.getX(), owner.getY() + owner.getHeight()/2 - shot.getRect().height()/2);
                    break;
                case HERO_RIGHT:
                    shot = new Shot(HEROSHOT_SPRITE_IMAGE, SHOT_SPEED, 0);
                    shot.setPos(owner.getX() + owner.getWidth()/2, owner.getY() + owner.getHeight()/2 - shot.getRect().height()/2);
                    break;
            }
        }
        // if the sprite is moving, the shot is created depending on the direction of the movement
        else {
            shot = new Shot(HEROSHOT_SPRITE_IMAGE, owner.getXSpeed(), -owner.getYSpeed());
            switch (owner.getSide()) {
                case HERO_DOWN:
                    shot.setPos(owner.getX() + owner.getWidth()/2 - shot.getRect().width()/2, owner.getY() + owner.getHeight()/2);
                    break;
                case HERO_UP:
                    shot.setPos(owner.getX() + owner.getWidth()/2 - shot.getRect().width()/2, owner.getY() - shot.getRect().height());
                    break;
                case HERO_LEFT:
                    shot.setPos(owner.getX(), owner.getY() + owner.getHeight()/2 - shot.getRect().height()/2);
                    break;
                case HERO_RIGHT:
                    shot.setPos(owner.getX() + owner.getWidth()/2, owner.getY() + owner.getHeight()/2 - shot.getRect().height()/2);
                    break;
            }
        }
        return shot;
    }

    @Override
    public boolean isDisposable() {
        return false;
    }

}
