package kursinis.viljamas.fxControllers.tableParameters;

import javafx.beans.property.SimpleStringProperty;
import kursinis.viljamas.model.Customer;

public class CustomerTableParameters extends UserTableParameters {

    private final SimpleStringProperty cardNo = new SimpleStringProperty();
    private final SimpleStringProperty deliveryAddress = new SimpleStringProperty();
    private final SimpleStringProperty billingAddress = new SimpleStringProperty();
    private final SimpleStringProperty birthDate = new SimpleStringProperty();

    public CustomerTableParameters(Customer customer) {
        super(new SimpleStringProperty(customer.getLogin()), new SimpleStringProperty(customer.getName()), new SimpleStringProperty(customer.getSurname()), new SimpleStringProperty(customer.getPassword()));
        this.cardNo.set(customer.getCardNo());
        this.deliveryAddress.set(customer.getDeliveryAddress());
        this.billingAddress.set(customer.getBillingAddress());
        this.birthDate.set(customer.getBirthDate().toString());
    }

    public String getCardNo() {
        return cardNo.get();
    }

    public SimpleStringProperty cardNoProperty() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo.set(cardNo);
    }

    public String getDeliveryAddress() {
        return deliveryAddress.get();
    }

    public SimpleStringProperty deliveryAddressProperty() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress.set(deliveryAddress);
    }

    public String getBillingAddress() {
        return billingAddress.get();
    }

    public SimpleStringProperty billingAddressProperty() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress.set(billingAddress);
    }

    public String getBirthDate() {
        return birthDate.get();
    }

    public SimpleStringProperty birthDateProperty() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate.set(birthDate);
    }
}
