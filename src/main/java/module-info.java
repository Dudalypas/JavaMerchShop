module kursinis.viljamas {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires mysql.connector.j;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires org.kordamp.bootstrapfx.core;
    requires spring.security.crypto;
    requires jdk.compiler;

    opens kursinis.viljamas to javafx.fxml;
    exports kursinis.viljamas;
    opens kursinis.viljamas.fxControllers.tableParameters to javafx.fxml, javafx.base;
    exports kursinis.viljamas.fxControllers.tableParameters to javafx.fxml, java.base;
    opens kursinis.viljamas.model to javafx.fxml, org.hibernate.orm.core, jakarta.persistence;
    exports kursinis.viljamas.model to javafx.fxml, org.hibernate.orm.core, jakarta.persistence;
    exports kursinis.viljamas.fxControllers;
}