package hu.alkfejl.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private StringProperty email = new SimpleStringProperty(this,"email");
    private StringProperty nev = new SimpleStringProperty(this,"nev");
    private StringProperty jelszo = new SimpleStringProperty(this,"jelszo");

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getNev() {
        return nev.get();
    }

    public StringProperty nevProperty() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev.set(nev);
    }

    public String getJelszo() {
        return jelszo.get();
    }

    public StringProperty jelszoProperty() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo.set(jelszo);
    }
}
