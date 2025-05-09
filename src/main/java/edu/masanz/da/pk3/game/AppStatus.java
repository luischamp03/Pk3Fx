package edu.masanz.da.pk3.game;

import edu.masanz.da.pk3.game.enums.EAppStatus;

import static edu.masanz.da.pk3.game.AppConsts.INIT_LEVEL;
import static edu.masanz.da.pk3.game.AppConsts.INIT_LIFES;
import static edu.masanz.da.pk3.game.enums.EAppStatus.*;

public class AppStatus {

    private boolean paused = false;
    private EAppStatus value;

    private int level;
    private Integer lifes;

    public AppStatus() {
        value = E_APP_START;
        level = INIT_LEVEL;
        lifes = INIT_LIFES;
    }

    public boolean isPaused() {
        return paused;
    }
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void switchPause() {
        paused = !paused;
    }

    public int getLevel(){
        return level;
    }

    public int getLifes(){
        return lifes;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    public void increaseLifes() {
        lifes++;
    }

    public EAppStatus getValue(){
        return value;
    }


    public void quit() {
        value = E_APP_QUIT;
    }

    public void start() {
        value = E_APP_PLAYING;
        level = INIT_LEVEL;
        lifes = INIT_LIFES;
    }

    public void nextLevel() {
        value = E_APP_PLAYING;
        level++;
    }

    public void sameLevel() {
        value = E_APP_PLAYING;
    }

    public void finish() {
        value = E_APP_START;
    }

    public void oneLessLife() {
        lifes--;
        if (lifes==0){
            value = E_APP_LOST;
        }else{
            value = E_APP_ONELESSLIFE;
        }
    }

    public void newLevel() {
        value = E_APP_NEWLEVEL;
    }

}
