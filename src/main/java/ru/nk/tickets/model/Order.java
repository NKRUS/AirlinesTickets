package ru.nk.tickets.model;

import javafx.beans.property.*;

/**
 * Created by NK on 13.11.2016.
 */
public class Order {
    private IntegerProperty order_id;
    private StringProperty surname;
    private StringProperty name;
    private StringProperty organization_name;
    private StringProperty phone_number;
    private StringProperty email;
    private BooleanProperty payed;
    private IntegerProperty document_id;
    private IntegerProperty document_number;

    public Order(){
        order_id = new SimpleIntegerProperty();
        surname = new SimpleStringProperty();
        name = new SimpleStringProperty();
        organization_name = new SimpleStringProperty();
        phone_number = new SimpleStringProperty();
        email = new SimpleStringProperty();
        payed = new SimpleBooleanProperty();
        document_id = new SimpleIntegerProperty();
        document_number = new SimpleIntegerProperty();
    }

    public int getOrder_id() {
        return order_id.get();
    }

    public IntegerProperty order_idProperty() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id.set(order_id);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getOrganization_name() {
        return organization_name.get();
    }

    public StringProperty organization_nameProperty() {
        return organization_name;
    }

    public void setOrganization_name(String organization_name) {
        this.organization_name.set(organization_name);
    }

    public String getPhone_number() {
        return phone_number.get();
    }

    public StringProperty phone_numberProperty() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number.set(phone_number);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public boolean isPayed() {
        return payed.get();
    }

    public BooleanProperty payedProperty() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed.set(payed);
    }

    public int getDocument_id() {
        return document_id.get();
    }

    public IntegerProperty document_idProperty() {
        return document_id;
    }

    public void setDocument_id(int document_id) {
        this.document_id.set(document_id);
    }

    public int getDocument_number() {
        return document_number.get();
    }

    public IntegerProperty document_numberProperty() {
        return document_number;
    }

    public void setDocument_number(int document_number) {
        this.document_number.set(document_number);
    }
}
