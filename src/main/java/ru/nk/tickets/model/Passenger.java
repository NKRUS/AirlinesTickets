package ru.nk.tickets.model;

import javafx.beans.property.*;

import java.sql.Date;

/**
 * Created by NK on 13.11.2016.
 */
public class Passenger {
    private IntegerProperty passenger_id;
    private IntegerProperty order_id;
    private IntegerProperty flight_id;
    private IntegerProperty class_id;
    private StringProperty surname;
    private StringProperty name;
    private StringProperty patronymic;
    private BooleanProperty isMan;
    private SimpleObjectProperty<Date> date_of_birth;
    private IntegerProperty document_id;
    private IntegerProperty document_number;


    public Passenger() {
        passenger_id = new SimpleIntegerProperty();
        order_id = new SimpleIntegerProperty();
        flight_id = new SimpleIntegerProperty();
        class_id = new SimpleIntegerProperty();
        surname = new SimpleStringProperty();
        name = new SimpleStringProperty();
        patronymic = new SimpleStringProperty();
        isMan = new SimpleBooleanProperty();
        date_of_birth = new SimpleObjectProperty<Date>();
        document_id = new SimpleIntegerProperty();
        document_number = new SimpleIntegerProperty();

    }


    public int getPassenger_id() {
        return passenger_id.get();
    }

    public IntegerProperty passenger_idProperty() {
        return passenger_id;
    }

    public void setPassenger_id(int passenger_id) {
        this.passenger_id.set(passenger_id);
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

    public int getFlight_id() {
        return flight_id.get();
    }

    public IntegerProperty flight_idProperty() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id.set(flight_id);
    }

    public int getClass_id() {
        return class_id.get();
    }

    public IntegerProperty class_idProperty() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id.set(class_id);
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

    public String getPatronymic() {
        return patronymic.get();
    }

    public StringProperty patronymicProperty() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic.set(patronymic);
    }

    public boolean isIsMan() {
        return isMan.get();
    }

    public BooleanProperty isManProperty() {
        return isMan;
    }

    public void setIsMan(boolean isMan) {
        this.isMan.set(isMan);
    }

    public Date getDate_of_birth() {
        return date_of_birth.get();
    }

    public SimpleObjectProperty<Date> date_of_birthProperty() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth.set(date_of_birth);
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
