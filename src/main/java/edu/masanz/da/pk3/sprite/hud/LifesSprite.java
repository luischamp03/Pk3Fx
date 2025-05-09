package edu.masanz.da.pk3.sprite.hud;

import edu.masanz.da.pk3.game.AppStatus;
import edu.masanz.da.pk3.game.GameArea;
import edu.masanz.da.pk3.sprite.ASprite;
import edu.masanz.da.pk3.util.Rect;
import edu.masanz.da.pk3.game.AppConsts;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class LifesSprite extends ASprite {

    AppStatus appStatus;

    public LifesSprite(Image img, AppStatus appStatus) {
        super(img, AppConsts.LIFES_ROWS, AppConsts.LIFES_COLS);
        this.appStatus = appStatus;
        x = GameArea.rect.width()-width;
        y = 0;
        update();
    }

    @Override
    public void update() {
        updateFrame();
    }

    public void updateFrame(){
        currentFrame = 5 - appStatus.getLifes();
        if (currentFrame<0) currentFrame = 0;
    }

    @Override
    public void draw(GraphicsContext gc) {
        int srcX = 0;
        int srcY = currentFrame * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, (int)(x+ AppConsts.LIFES_ALFA * width), (int)(y+ AppConsts.LIFES_ALFA * height));
        gc.drawImage(img, src.left, src.top, src.width(), src.height(),
                          dst.left, dst.top, dst.width(), dst.height());
    }

}
