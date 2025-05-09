package edu.masanz.da.pk3.sprite.enemies;

import edu.masanz.da.pk3.sprite.ASprite;
import edu.masanz.da.pk3.sprite.interfaces.ICanShoot;
import edu.masanz.da.pk3.sprite.weaponry.AShot;
import edu.masanz.da.pk3.sprite.weaponry.AWeapon;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Los enemigos van a poder disparar si tienen un arma
 */
public abstract class AEnemy extends ASprite implements ICanShoot {

    private AWeapon weapon;

    public AEnemy(Image img, int rows, int cols) {
        super(img, rows, cols);
    }

    public AEnemy(Image img, int rows, int cols, int rowSpace, int colSpace) {
        super(img, rows, cols, rowSpace, colSpace);
    }

    public void setWeapon(AWeapon weapon) {
        this.weapon = weapon;
    }

    public AWeapon getWeapon() {
        return weapon;
    }

    public boolean hasWeapon(){
        return weapon != null;
    }

    public List<AShot> shoot(){
        List<AShot> list = new ArrayList<>();
        if (weapon!=null) {
            list = weapon.shoot();
        }
        return list;
    }

}
