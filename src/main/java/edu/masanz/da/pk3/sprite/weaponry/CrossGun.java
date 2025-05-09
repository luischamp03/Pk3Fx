package edu.masanz.da.pk3.sprite.weaponry;

import edu.masanz.da.pk3.sprite.ASprite;
import edu.masanz.da.pk3.sprite.interfaces.IIsDisposable;

import java.util.ArrayList;
import java.util.List;

import static edu.masanz.da.pk3.game.AppConsts.*;

public class CrossGun extends AWeapon implements IIsDisposable {
    private boolean isDisposable;

    public CrossGun(ASprite owner) {
        super(owner);
        isRechargable = true;
        isDisposable = true;
        ammunition = I_CROSSGUN_AMMUNITION;
    }

    public CrossGun(ASprite owner, boolean isRechargable, int ammunition, boolean isDisposable) {
        this(owner);
        this.owner = owner;
        this.isRechargable = isRechargable;
        this.ammunition = ammunition;
        this.isDisposable = isDisposable;
    }

    @Override
    public boolean isDisposable() {
        return isDisposable;
    }

    @Override
    public List<AShot> shoot() {
        List<AShot> list = new ArrayList<>();

        if (isRechargable) {
            if (ammunition == 0) {
                return list;
            }
            AShot shot1 = new Cannonball();
            shot1.setSpeed(SHOT_SPEED,0);
            AShot shot2 = new Cannonball();
            shot2.setSpeed(SHOT_SPEED,90);
            AShot shot3 = new Cannonball();
            shot3.setSpeed(SHOT_SPEED,180);
            AShot shot4 = new Cannonball();
            shot4.setSpeed(SHOT_SPEED,270);
            shot1.setPos(owner.getX() + owner.getWidth()/2, owner.getY() + owner.getHeight()/2);
            shot2.setPos(owner.getX() + owner.getWidth()/2, owner.getY() + owner.getHeight()/2);
            shot3.setPos(owner.getX() + owner.getWidth()/2, owner.getY() + owner.getHeight()/2);
            shot4.setPos(owner.getX() + owner.getWidth()/2, owner.getY() + owner.getHeight()/2);
            list.add(shot1);
            list.add(shot2);
            list.add(shot3);
            list.add(shot4);
            ammunition--;
        }
        return list;
    }

}
