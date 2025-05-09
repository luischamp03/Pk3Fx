package edu.masanz.da.pk3.sprite.weaponry;

import edu.masanz.da.pk3.sprite.ASprite;
import javafx.scene.image.Image;

/**
 * Disparo, sin animación
 */
public abstract class AShot extends ASprite {

    public AShot(Image img, int rows, int cols) {
        super(img,rows,cols);
    }

}
