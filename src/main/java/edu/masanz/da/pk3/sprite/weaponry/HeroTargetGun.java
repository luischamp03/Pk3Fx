package edu.masanz.da.pk3.sprite.weaponry;

import edu.masanz.da.pk3.game.GameObject;
import edu.masanz.da.pk3.sprite.ASprite;
import edu.masanz.da.pk3.sprite.Hero;

import java.util.ArrayList;
import java.util.List;

import static edu.masanz.da.pk3.game.AppConsts.SHOT_SPEED;

public class HeroTargetGun extends AWeapon {

    public HeroTargetGun(ASprite owner) {
        super(owner);
    }

    @Override
    public List<AShot> shoot() {
        List<AShot> list = new ArrayList<>();
        AShot shot = new Cannonball();
        shot.setPos(owner.getRect().centerX(), owner.getRect().bottom);
        Hero hero = GameObject.getInstance().getHero();
        shot.setSpeed(SHOT_SPEED, (int) Math.toDegrees(Math.atan2(shot.getY() - hero.getY(), hero.getX() - shot.getX())));
        list.add(shot);
        return list;
    }

}
