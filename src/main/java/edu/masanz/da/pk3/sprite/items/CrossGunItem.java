package edu.masanz.da.pk3.sprite.items;

import edu.masanz.da.pk3.game.GameObject;
import edu.masanz.da.pk3.sprite.Hero;
import edu.masanz.da.pk3.sprite.weaponry.CrossGun;

import static edu.masanz.da.pk3.game.AppConsts.*;

public class CrossGunItem extends AItem{
    public CrossGunItem() {
        super(I_CROSSGUN_SPRITE_IMAGE,I_CROSSGUN_SPRITE_ROWS,I_CROSSGUN_SPRITE_COLS);
    }

    @Override
    public void useItem() {
        Hero hero = GameObject.getInstance().getHero();
        hero.addWeapon(new CrossGun(hero,true,I_CROSSGUN_AMMUNITION,true));
    }

    @Override
    public void update() {

    }
}
