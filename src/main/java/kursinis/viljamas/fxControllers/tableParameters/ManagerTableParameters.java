package kursinis.viljamas.fxControllers.tableParameters;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import kursinis.viljamas.model.Manager;
import kursinis.viljamas.model.Warehouse;

public class ManagerTableParameters extends UserTableParameters {

    private final SimpleBooleanProperty isAdmin = new SimpleBooleanProperty();
    private final SimpleObjectProperty<Warehouse> warehouse = new SimpleObjectProperty<>();

    // Modify the constructor to include isAdmin and warehouse properties
    public ManagerTableParameters(Manager manager) {
        super(new SimpleStringProperty(manager.getLogin()), new SimpleStringProperty(manager.getName()), new SimpleStringProperty(manager.getSurname()), new SimpleStringProperty(manager.getPassword()));
        this.isAdmin.set(manager.isAdmin());
        this.warehouse.set(manager.getWarehouse());
    }
    public boolean isAdmin() {
        return isAdmin.get();
    }

    public SimpleBooleanProperty isAdminProperty() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin.set(isAdmin);
    }

    public Warehouse getWarehouse() {
        return warehouse.get();
    }

    public SimpleObjectProperty<Warehouse> warehouseProperty() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse.set(warehouse);
    }
}
