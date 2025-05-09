package edu.masanz.da.pk3.game;

import edu.masanz.da.pk3.sprite.Hero;

public class GameObject {
    private static GameObject instance;
    private Hero hero;
    private AppStatus appStatus;

    private GameObject() {}

    public static GameObject getInstance() {
        if (instance == null) {
            instance = new GameObject();
        }
        return instance;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Hero getHero() {
        return hero;
    }

    public AppStatus getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(AppStatus appStatus) {
        this.appStatus = appStatus;
    }


}
