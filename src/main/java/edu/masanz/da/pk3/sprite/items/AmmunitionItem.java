package edu.masanz.da.pk3.sprite.items;

import edu.masanz.da.pk3.game.GameObject;
import javafx.scene.image.Image;

import static edu.masanz.da.pk3.game.AppConsts.*;

public class AmmunitionItem extends AItem {
    private int ammunition;
    public AmmunitionItem(int ammunition) {
        super(I_AMMUNITION_SPRITE_IMAGE_SMALL,I_AMMUNITION_SPRITE_ROWS,I_AMMUNITION_SPRITE_COLS);
        this.ammunition = ammunition;
        if(ammunition > 5){
            setImg(I_AMMUNITION_SPRITE_IMAGE_LARGE);
        } else {
            setImg(I_AMMUNITION_SPRITE_IMAGE_SMALL);
        }
    }

    @Override
    public void useItem() {
        GameObject.getInstance().getHero().rechargeAmmunition(ammunition);
    }

    @Override
    public void update() {

    }
}
