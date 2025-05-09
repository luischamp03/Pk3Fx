package edu.masanz.da.pk3.sprite;

import edu.masanz.da.pk3.game.GameArea;
import edu.masanz.da.pk3.game.GameManager;
import edu.masanz.da.pk3.game.SceneLoader;
import edu.masanz.da.pk3.sprite.interfaces.IIsDisposable;
import edu.masanz.da.pk3.sprite.weaponry.*;
import edu.masanz.da.pk3.util.Rect;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static edu.masanz.da.pk3.game.AppConsts.*;

public class Hero extends ASprite {

    private boolean isAlive;
    private List<AWeapon> weapons;

    private static Logger log = Logger.getLogger(Hero.class);

    public Hero() {
        super(HERO_SPRITE_IMAGE, HERO_SPRITE_ROWS, HERO_SPRITE_COLS, HERO_SPRITE_ROWSPACE, HERO_SPRITE_COLSPACE);
        xSpeed = HERO_MAX_SPEED;
        ySpeed = HERO_MAX_SPEED;
        setInitPos();
        isAlive = true;
        weapons = new ArrayList<>();
        weapons.add( new Pistol(this) );
//        addWeapon( new CrossGun(this) );            // comentar y descomentar esta línea para probar CrossGun
    }

    public void setInitPos(){
        x = (int) (SceneLoader.getHeroBoardPosIni().getX() + SceneLoader.getTileWidth()/2 - width/2);
        y = (int) (SceneLoader.getHeroBoardPosIni().getY() + SceneLoader.getTileHeight());
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
    public void moveRight(){
        if (xSpeed != HERO_MAX_SPEED){
            xSpeed++;
        }
        side = HERO_RIGHT;
    }

    public void moveLeft(){
        if (xSpeed != -HERO_MAX_SPEED){
            xSpeed--;
        }
        side = HERO_LEFT;
    }

    public void moveUp(){
        if (ySpeed != -HERO_MAX_SPEED){
            ySpeed--;
        }
        side = HERO_UP;
    }

    public void moveDown(){
        if (ySpeed != HERO_MAX_SPEED){
            ySpeed++;
        }
        side = HERO_DOWN;
    }

    public void waiting(){      //moveNoWhere
        xSpeed = 0;
        ySpeed = 0;
        currentFrame = 0;
    }

    public void stopVert(){
        ySpeed = 0;
    }

    public void stopHoriz(){
        xSpeed = 0;
    }

    public void updateFrame(){
        if (xSpeed != 0 || ySpeed != 0){
            currentFrame = ++currentFrame%cols;
        }else {
            currentFrame = 0;
        }
    }

    @Override
    public Rect[] getRects(){
        Rect[] rects;
        if (xSpeed==0){
            rects = new Rect[3];
            rects[0] = new Rect((int)(x+(0.47*width)),y,(int)(x+(0.53*width)),y+height);
            rects[1] = new Rect((int)(x+(0.37*width)),(int)(y+(0.22*height)),(int)(x+(0.63*width)),y+height);
            rects[2] = new Rect(x,(int)(y+(0.4*height)),x+width,(int)(y+(0.55*height)));
        }else{
            rects = new Rect[2];
            rects[0] = new Rect(x+width/3,y,x+2*width/3,y+height);
            rects[1] = new Rect((int)(x+(0.3*width)),(int)(y+(0.4*height)),(int)(x+(0.7*width)),y+height);
        }
        return rects;
    }

    @Override
    public void update() {
        int nextX = x;
        int nextY = y;
        // check if the next position is inside the gameRect
        if (x <= GameArea.rect.right - width - xSpeed && x >= GameArea.rect.left - xSpeed ) {
            nextX = x + xSpeed;
        }
        if (y <= GameArea.rect.bottom - height - ySpeed && y >= GameArea.rect.top - ySpeed ){
            nextY = y + ySpeed;
        }
        // check if the next position is inside a path, this is '0' value in the map
        // as two axis are checked, the sprite can move diagonally if possible,
        // or in one axis if the other is blocked, this is good for bending the corners
        // +width/2 is used to check the horizontal center of the sprite, more realistic
        // -height/4 is used to check the feet of the sprite due to zenital view
        if (SceneLoader.getPosValue(nextX+width/2, nextY-height/4) <= SL_V_FLOOR){
            x = nextX;
            y = nextY;
        }else if (SceneLoader.getPosValue(x+width/2, nextY-height/4) <= SL_V_FLOOR){
            y = nextY;
        }else if (SceneLoader.getPosValue(nextX+width/2, y-height/4) <= SL_V_FLOOR){
            x = nextX;
        }
    }

    public boolean isAtFinishPosition(){
        return SceneLoader.getPosValue(x+width/2, y-height/3) == SL_V_FIN;
    }

    public List<AShot> shoot() {
        List<AShot> list;
        Iterator<AWeapon> it = weapons.iterator();
        while (it.hasNext()) {
            AWeapon weapon = it.next();
            list = weapon.shoot();
            if (list != null && !list.isEmpty()) {
                return list;
            }else{
                if (weapon instanceof IIsDisposable){
                    if (((IIsDisposable) weapon).isDisposable()) {
                        weapons.remove(weapon);
                        log.debug("weapons=" + weapons);
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    public int getAmmunition() {
        int ammunition = 0;
        for (AWeapon weapon : weapons) {
            if (weapon.isRechargable()){
                ammunition += weapon.getAmmunition();
            }
        }
        return ammunition;
    }

    public void rechargeAmmunition(int ammunition) {
        for (AWeapon weapon : weapons) {
            if (weapon.isRechargable()){
                weapon.recharge(ammunition);
                break;
            }
        }
        log.debug("rechargeAmmunition " + ammunition);
        log.debug("weapons=" + weapons);
    }

    public void rechargePistol() {
        for (AWeapon weapon : weapons) {
            if (weapon instanceof Pistol){
                weapon.setAmmunition(PISTOL_INITIAL_AMMUNITION);
                break;
            }
        }
        log.debug("rechargePistol");
        log.debug("weapons=" + weapons);
    }

    public void addWeapon(AWeapon weapon){
        for (AWeapon w : weapons) {
            if (w.equals(weapon)){
                w.recharge(weapon.getAmmunition());
                log.debug("weapon=" + weapon.getClass().getSimpleName() + " recharged with " + weapon.getAmmunition() + " bullets");
                log.debug("weapons=" + weapons);
                return;
            }
        }
        weapons.add(0, weapon);
        log.debug("weapon=" + weapon.getClass().getSimpleName() + " added with " + weapon.getAmmunition() + " bullets");
        log.debug("weapons=" + weapons);
    }

}
