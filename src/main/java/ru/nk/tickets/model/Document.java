package ru.nk.tickets.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by NK on 13.11.2016.
 */
public class Document {
    private IntegerProperty document_id;
    private StringProperty document_type;

    public Document(){
        document_id = new SimpleIntegerProperty();
        document_type = new SimpleStringProperty();
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

    public String getDocument_type() {
        return document_type.get();
    }

    public StringProperty document_typeProperty() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type.set(document_type);
    }
}
