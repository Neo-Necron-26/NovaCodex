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
    requires org.fxmisc.richtext;
    requires org.slf4j;

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

    exports app.novacodex.novacodex.editor.controller;
    exports app.novacodex.novacodex.loader;
    opens app.novacodex.novacodex.editor.controller to javafx.fxml, spring.core;
    opens app.novacodex.novacodex.loader to javafx.fxml, spring.beans, spring.context, spring.core;
}