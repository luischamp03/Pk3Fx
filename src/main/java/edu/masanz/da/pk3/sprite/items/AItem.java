package edu.masanz.da.pk3.sprite.items;

import edu.masanz.da.pk3.sprite.ASprite;
import javafx.scene.image.Image;

public abstract class AItem extends ASprite {
    public AItem(Image img, int rows, int cols) {
        super(img, rows, cols);
    }

    public AItem(Image img, int rows, int cols, int rowSpace, int colSpace){
        super(img,rows,cols,rowSpace,colSpace);
    }



    public abstract void useItem();
}
