module app.novacodex.novacodex {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;


    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.core;
    requires spring.beans;


    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    exports app.novacodex.novacodex;

    opens app.novacodex.novacodex to
            spring.core,
            spring.beans,
            spring.context,
            javafx.fxml;

    opens app.novacodex.novacodex.model to
            org.hibernate.orm.core,
            spring.core,
            javafx.base;
}