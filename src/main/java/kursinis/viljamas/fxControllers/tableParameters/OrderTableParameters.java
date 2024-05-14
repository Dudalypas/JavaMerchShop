package kursinis.viljamas.fxControllers.tableParameters;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderTableParameters
{
    private  SimpleIntegerProperty id = new SimpleIntegerProperty();
    private  SimpleStringProperty manager = new SimpleStringProperty();
    private  SimpleStringProperty customer = new SimpleStringProperty();
    private  SimpleStringProperty dateCreated = new SimpleStringProperty();

    public OrderTableParameters() {
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getManager() {
        return manager.get();
    }

    public SimpleStringProperty managerProperty() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager.set(manager);
    }

    public String getCustomer() {
        return customer.get();
    }

    public SimpleStringProperty customerProperty() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer.set(customer);
    }

    public String getDateCreated() {
        return dateCreated.get();
    }

    public SimpleStringProperty dateCreatedProperty() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated.set(dateCreated);
    }
}
