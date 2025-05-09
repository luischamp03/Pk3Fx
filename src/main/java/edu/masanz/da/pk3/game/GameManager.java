package edu.masanz.da.pk3.game;

import edu.masanz.da.pk3.game.enums.EAppStatus;
import edu.masanz.da.pk3.sprite.ASprite;
import edu.masanz.da.pk3.sprite.Hero;
import edu.masanz.da.pk3.sprite.effects.SpriteTemp;
import edu.masanz.da.pk3.sprite.enemies.AEnemy;
import edu.masanz.da.pk3.sprite.hud.LifesSprite;
import edu.masanz.da.pk3.sprite.interfaces.ICanSpawn;
import edu.masanz.da.pk3.sprite.interfaces.IHaveShield;
import edu.masanz.da.pk3.sprite.items.*;
import edu.masanz.da.pk3.sprite.weaponry.AShot;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static edu.masanz.da.pk3.game.AppConsts.*;

public class GameManager {

    // region attributes
    private Hero hero;
    protected LifesSprite lifesSprite;
    protected List<AShot> heroShots = new ArrayList<>();
    protected List<AShot> enemyShots = new ArrayList<>();
    protected List<AEnemy> enemies = new ArrayList<>();
    protected List<SpriteTemp> temps = new ArrayList<>();
    protected List<AItem> items = new ArrayList<>();                  // descomentar

    public int score = 0;
    private AppStatus appStatus;

    private static Logger log = Logger.getLogger(GameManager3.class);

    // endregion

    public GameManager(AppStatus appStatus) {
        PropertyConfigurator.configure(LOG4J_PROPERTIES);

        this.appStatus = appStatus;
        hero = new Hero();
        lifesSprite = new LifesSprite(LIFES_SPRITE_IMAGE, appStatus);
        GameObject.getInstance().setHero(hero);
        GameObject.getInstance().setAppStatus(appStatus);
    }

    public void start(){
        appStatus.start();
        log.debug("start level=" + appStatus.getLevel());
        score = 0;
        SceneLoader.loadScene(appStatus.getLevel());
        clearLevel();
        hero.setAlive(true);
        hero.rechargePistol();
        lifesSprite.update();
        enemies.clear();
        items.clear();                                                // descomentar
//        SceneLoader.loadEnemiesItems(enemies);                          // comentar
        SceneLoader.loadEnemiesItems(enemies, items);                 // descomentar
    }

    public void nextLevel(){
        appStatus.nextLevel();
        log.debug("nextLevel level=" + appStatus.getLevel());
        SceneLoader.loadScene(appStatus.getLevel());
        clearLevel();
        enemies.clear();
        items.clear();                                                // descomentar
        hero.setAlive(true);
//        SceneLoader.loadEnemiesItems(enemies);                          // comentar
        SceneLoader.loadEnemiesItems(enemies, items);                 // descomentar
    }

    public void sameLevel(){
        appStatus.sameLevel();
        log.debug("sameLevel level=" + appStatus.getLevel());
        clearLevel();
        lifesSprite.update();
        hero.setAlive(true);
    }

    private void clearLevel(){
        temps.clear();
        heroShots.clear();
        enemyShots.clear();
        hero.setInitPos();
    }

    public void finish(){
        appStatus.finish();
    }

    public int getLevel() {
        return appStatus.getLevel();
    }

    public Hero getHero(){
        return hero;
    }

    public void shot(){
        List<AShot> shots = hero.shoot();
        if (shots!=null) {
            heroShots.addAll(shots);
        }
    }

    public void updateGame() {
        //Detección si el protagonista ha tocado algún item
        for (Iterator<AItem> itItem = items.iterator(); itItem.hasNext(); ) {                          // descomentar
            AItem item = itItem.next();                                                                // descomentar
            if(hero.isAlive() && item.collides(hero)){                                                 // descomentar
                item.useItem();                                                                        // descomentar
                itItem.remove();                                                                       // descomentar
                temps.add(new SpriteTemp(temps, hero.getRect().centerX(), hero.getRect().centerY(),    // descomentar
                        CHISPAS_10_SPRITE_IMAGE, 10));                                           // descomentar
            }                                                                                          // descomentar
        }                                                                                              // descomentar

        //Detección de colisión entre balas
         for (Iterator<AShot> itHeroShot = heroShots.iterator(); itHeroShot.hasNext(); ) {
            AShot heroShot = itHeroShot.next();
            for (Iterator<AShot> itEnemyShots = this.enemyShots.iterator(); itEnemyShots.hasNext(); ) {
                AShot enemyShot = itEnemyShots.next();
                if (heroShot.collides(enemyShot)) {
                    itHeroShot.remove();
                    itEnemyShots.remove();
                    temps.add(new SpriteTemp(temps, enemyShot.getRect().centerX(), enemyShot.getRect().centerY(),
                            EXPLOSION_12_SPRITE_IMAGE, 12));
                    break;
                }
            }
        }

        //Detección de colisiones de los enemigos con los disparos del protagonista
        for (Iterator<AShot> itBullet = heroShots.iterator(); itBullet.hasNext(); ) {
            AShot heroShot = itBullet.next();
            for (Iterator<AEnemy> itSprite = enemies.iterator(); itSprite.hasNext(); ) {
                ASprite sprite = itSprite.next();
                if (heroShot.collides(sprite)){
                    if (sprite instanceof IHaveShield){
                        temps.add(new SpriteTemp(temps, sprite.getRect().centerX(), sprite.getRect().centerY(),
                                                 EXPLOSION_9_SPRITE_IMAGE, 9));
                        if (((IHaveShield) sprite).impact()){
                            itSprite.remove();
                        }
                    }else{
                        temps.add(new SpriteTemp(temps, sprite.getRect().centerX(), sprite.getRect().centerY(),
                                                 EXPLOSION_9_SPRITE_IMAGE, 9));
                        itSprite.remove();
                    }
                    score += PTS_ENEMYSHIP;
                    itBullet.remove();
                    break;
                }
            }
            if (GameArea.rect.contains(heroShot.getRect())){
                heroShot.update();
            }else{
                itBullet.remove();
            }
        }

        //Actualización de los items
        for (ASprite item : items) {                                  // descomentar
            item.update();                                            // descomentar
        }                                                             // descomentar
        //Actualización del protagonista
        if (hero.isAlive()) {
            hero.update();
        }
        //Actualización de los sprites temporales
        for (int i=temps.size()-1;i>=0;i--) {
            temps.get(i).update();
        }
        //Actualización de los enemigos
        for (ASprite enemy : enemies) {
            enemy.update();
        }
        //Generación de nuevos enemigos
        List<AEnemy> newList = new ArrayList<>();
        for (ASprite enemy : enemies) {
            if (enemy instanceof ICanSpawn){
                newList.addAll(
                    ((ICanSpawn) enemy).spawn()
                );
            }
        }
        enemies.addAll(newList);
        //Comprobación del estado de la partida
        if (!hero.isAlive()&&(temps.isEmpty())){
            appStatus.oneLessLife();
            if (appStatus.getValue() == EAppStatus.E_APP_LOST){
                log.debug("updateGame E_APP_LOST enemies=" + enemies.size() + " enemyShots=" + enemyShots.size() + " heroShots=" + heroShots.size());
            }else{
                log.debug("updateGame E_APP_ONELESSLIFE enemies=" + enemies.size() + " enemyShots=" + enemyShots.size() + " heroShots=" + heroShots.size());
            }
            return;
        }
        //Comprobación del estado de la partida
        if (hero.isAtFinishPosition()){
            appStatus.newLevel();
            score += PTS_NEWLEVEL;
            return;
        }
        //Detección si los disparos de los enemigos han impactado con el protagonista
        //o se han salido del área se juego
        for (Iterator<AShot> itShot = enemyShots.iterator(); itShot.hasNext(); ) {
            AShot AShot = itShot.next();
            if (hero.isAlive() && AShot.collides(hero)) {
                hero.setAlive(false);
                temps.add(new SpriteTemp(temps, hero.getRect().centerX(), hero.getRect().centerY(),
                        EXPLOSION_12_SPRITE_IMAGE, 12));
            }else {
                if (GameArea.rect.contains(AShot.getRect())) {
                    AShot.update();
                } else {
                    itShot.remove();
                }
            }
        }
        //Detección si los enemigos han impactado con el protagonista
        //o se han salido del área se juego
        for (Iterator<AEnemy> itSprite = enemies.iterator(); itSprite.hasNext(); ) {
            ASprite sprite = itSprite.next();
            if(hero.isAlive() && sprite.collides(hero)){
                hero.setAlive(false);
                temps.add(new SpriteTemp(temps, hero.getRect().centerX(), hero.getRect().centerY(),
                        EXPLOSION_12_SPRITE_IMAGE, 12));
            }
            if (GameArea.rect.bottom < sprite.getRect().top - sprite.getRect().height() ) {
                itSprite.remove();
            }
        }

        //Generación de disparos por parte de los enemigos
        int n = heroShots.size() - enemyShots.size();
        ArrayList<AEnemy> shooterEnemies = new ArrayList<>();
        for (AEnemy enemy : enemies) {
            if (enemy.hasWeapon()) {
                shooterEnemies.add(enemy);
            }
        }
        if (!shooterEnemies.isEmpty()){
            for (int i = 0; i < n; i++) {
                int j = (int)(Math.random()*shooterEnemies.size());
                List<AShot> shots = shooterEnemies.get(j).shoot();
                enemyShots.addAll(shots);
            }
        }
    }

}
