package edu.masanz.da.pk3.game;

import edu.masanz.da.pk3.sprite.ASprite;
import edu.masanz.da.pk3.sprite.effects.SpriteTemp;
import edu.masanz.da.pk3.sprite.weaponry.AShot;
import edu.masanz.da.pk3.util.Position;
import edu.masanz.da.pk3.util.Rect;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import static edu.masanz.da.pk3.game.AppConsts.*;

public class Renderer0 {

    GraphicsContext gc;
    GameManager gameManager;

    public Renderer0(GraphicsContext gc, GameManager gameManager) {
        this.gc = gc;
        this.gameManager = gameManager;
    }

    public void drawStart() {
//        clearScreen(Color.BLACK);

        Image img = COVER_IMAGE;
        Rect src = new Rect(0, 0, (int) (img.getWidth()), (int) (img.getHeight()));
        Rect dst = new Rect(0, 0, GameArea.rect.width(), (int) (HUD_HEIGHT + GameArea.rect.height()));
        gc.drawImage(img, src.left, src.top, src.width(), src.height(),
                dst.left, dst.top, dst.width(), dst.height());

        double x = GameArea.rect.width() / 6;
        double y = 3.5 * GameArea.rect.height() / 5;
        double ay = GameArea.rect.height() / 10;
        write(x,y,ay, TXT_PRESS_ENTER, TXT_TO_START);

    }

    private void write(double x, double y, double ay, String txt1, String txt2){
        gc.setFill( Color.PURPLE );
        gc.setFont( FONT_HELVETICA_52 );
        if (txt1!=null) gc.fillText( txt1, x, y  );
        if (txt2!=null) gc.fillText( txt2, x, y + ay);
        x -= 5;
        y -= 5;
        gc.setFill( Color.ORANGE);//.LIMEGREEN );
        if (txt1!=null) gc.fillText( txt1, x, y );
        if (txt2!=null) gc.fillText( txt2, x, y + ay);
    }

    private void clearScreen(Color color) {
        double x,y,w,h, r;
        gc.setFill(color);
        gc.setStroke(Color.BLACK);
        x = 0;
        y = 0;
        w = GameArea.rect.width();
        h = GameArea.rect.height() + HUD_HEIGHT;
        r = 0;
        gc.fillRect(x, y, w ,h);
        gc.strokeRoundRect(x, y, w ,h, r, r);
    }

    public void drawBoard(int sceneNumber) {
        clearScreen(Color.WHITE);
        drawScene(sceneNumber);
        if (gameManager.getHero().isAlive()) {
            gameManager.getHero().draw(gc);
        }
//        for (ASprite item : gameManager.items) {                                // descomentar
//            item.draw(gc);                                                      // descomentar
//        }                                                                       // descomentar
        for (ASprite enemy : gameManager.enemies) {
            enemy.draw(gc);
        }
        for (AShot enemyShot : gameManager.enemyShots) {
            enemyShot.draw(gc);
        }
        for (AShot heroShot : gameManager.heroShots) {
            heroShot.draw(gc);
        }
        for (SpriteTemp temp : gameManager.temps) {
            temp.draw(gc);
        }
        // Banner
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, BOARD_WIDTH, HUD_HEIGHT);
        double x,y;
        // Nivel
        x = MARGIN_LEFT;
        y = MARGIN_TOP;
        gc.setFill( Color.ORANGE);//.LIMEGREEN );
        gc.setFont( FONT_HELVETICA_16 );
        String txt = TXT_LEVEL.substring(0,1).toUpperCase() + TXT_LEVEL.substring(1).toLowerCase();
        gc.fillText( txt + " : " + gameManager.getLevel(), x, y );
        // Disparos
        x = 1  * GameArea.rect.width() / 5;
        y = MARGIN_TOP;
        gc.setFill( Color.ORANGE);//.LIMEGREEN );
        gc.setFont( FONT_HELVETICA_16 );
        int ammunition = gameManager.getHero().getAmmunition();
        gc.fillText( TXT_AMMUNITION + " : " + ammunition , x, y );
        // Puntos
        x = 4  * GameArea.rect.width() / 9;
        y = MARGIN_TOP;
        gc.setFill( Color.ORANGE);//.LIMEGREEN );
        gc.setFont( FONT_HELVETICA_16 );
        gc.fillText( TXT_POINTS + " : " + gameManager.score , x, y );
        // Vidas
        gameManager.lifesSprite.update();
        gameManager.lifesSprite.draw(gc);

    }

    private void drawScene(int sceneNumber) {
        Position ini = null, fin = null;
        char[][] m = SceneLoader.loadScene(sceneNumber);
        // Tiles
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                switch (m[i][j]) {
                    case SL_V_PATH://'0':
                        gc.setFill(Color.WHITE);
                        break;
                    case SL_V_INI://'1':
                        gc.setFill(Color.LIGHTGRAY);
                        ini = new Position(j, i);
                        break;
                    case SL_V_FIN://'2':
                        gc.setFill(Color.GREY);
                        fin = new Position(j, i);
                        break;
                    case SL_V_WATER://'3':
                        gc.setFill(Color.BLUE);
                        break;
                    case SL_V_WALL://'4':
                        gc.setFill(Color.BROWN);
                        break;
                    default:
                        gc.setFill(Color.BLACK);
                        break;
                }
                gc.fillRect(j * 16, HUD_HEIGHT + i * 16, 16, 16);
            }
        }
        // Horizontal lines
        for (int i = 0; i <= 40; i++) {
            gc.setStroke(Color.ORANGE);
            gc.strokeLine(0, HUD_HEIGHT + i * 16, GameArea.rect.width(), HUD_HEIGHT + i * 16);
        }
        // Vertical lines
        for (int i = 0; i <= 30; i++) {
            gc.setStroke(Color.ORANGE);
            gc.strokeLine(i * 16, HUD_HEIGHT, i*16, HUD_HEIGHT +  GameArea.rect.height());
        }
        // Ini
        if (ini != null) {
            gc.setStroke(Color.GREEN);
            gc.strokeRect(ini.getX() * 16, HUD_HEIGHT + ini.getY() * 16, 16, 16);
        }
        // Fin
        if (fin != null) {
            gc.setStroke(Color.RED);
            gc.strokeRect(fin.getX() * 16, HUD_HEIGHT + fin.getY() * 16, 16, 16);
        }
    }

    public void drawYouWon() {
        drawFinish(TXT_YOU_WON);
    }

    public void drawYouLost() {
        drawFinish(TXT_YOU_LOST);
    }

    private void drawFinish(String txt) {
        double x,y,ay;

        x = 2.0 * GameArea.rect.width() / 8;
        y = GameArea.rect.height() / 3;
        ay = 0;
        write(x,y,ay,txt,null);

        pressEnterToContinue();
    }

    public void drawNewLevel(int level) {
        double x,y,ay;

        x = 2.5 * GameArea.rect.width() / 8;
        y = GameArea.rect.height() / 3;
        ay = 0;
        write(x,y,ay, TXT_LEVEL + " " + level,null);

        pressEnterToContinue();
    }

    public void drawOneLessLife() {
        double x,y,ay;

        x = 2.0 * GameArea.rect.width() / 8;
        y = GameArea.rect.height() / 3;
        ay = 0;
        write(x,y,ay, TXT_YOU_CAN,null);

        pressEnterToContinue();
    }

    private void pressEnterToContinue() {
        double x,y,ay;
        x = GameArea.rect.width() / 6;
        y = 3.5 * GameArea.rect.height() / 5;
        ay = GameArea.rect.height() / 10;
        write(x,y,ay, TXT_PRESS_ENTER, TXT_TO_CONTINUE);
    }

}
