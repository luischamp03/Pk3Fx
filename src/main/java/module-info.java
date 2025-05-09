module com.aetxabao.invasoresfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires log4j;

    opens edu.masanz.da.pk3 to javafx.fxml;
    exports edu.masanz.da.pk3;
    exports edu.masanz.da.pk3.sprite;
    opens edu.masanz.da.pk3.sprite to javafx.fxml;
    exports edu.masanz.da.pk3.util;
    opens edu.masanz.da.pk3.util to javafx.fxml;
    exports edu.masanz.da.pk3.game;
    opens edu.masanz.da.pk3.game to javafx.fxml;
    exports edu.masanz.da.pk3.sprite.weaponry;
    opens edu.masanz.da.pk3.sprite.weaponry to javafx.fxml;
    exports edu.masanz.da.pk3.sprite.interfaces;
    opens edu.masanz.da.pk3.sprite.interfaces to javafx.fxml;
    exports edu.masanz.da.pk3.sprite.enemies;
    opens edu.masanz.da.pk3.sprite.enemies to javafx.fxml;
    exports edu.masanz.da.pk3.sprite.items;
    opens edu.masanz.da.pk3.sprite.items to javafx.fxml;
    exports edu.masanz.da.pk3.sprite.hud;
    opens edu.masanz.da.pk3.sprite.hud to javafx.fxml;
    exports edu.masanz.da.pk3.sprite.effects;
    opens edu.masanz.da.pk3.sprite.effects to javafx.fxml;
}