package edu.masanz.da.pk3;

import edu.masanz.da.pk3.game.AppPk3Fx;

/*
mvn package
/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home/bin/jpackage
--name Pk2Fx
--icon src/main/resources/edu/masanz/da/pk2/icon/icon.icns
--input target
--main-jar Pk2Fx-1.0-SNAPSHOT.jar
--main-class edu.masanz.da.pk2.Main
 */
public class Main {
    public static void main(String[] args) {
        AppPk3Fx.main(args);
    }
}