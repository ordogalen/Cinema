package hu.alkfejl.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Ticket {
    private IntegerProperty jegy_id = new SimpleIntegerProperty(this,"jegy_id");
    private IntegerProperty jegyar = new SimpleIntegerProperty(this,"jegyar");
    private IntegerProperty vetites_id = new SimpleIntegerProperty(this,"vetites_id");
    private StringProperty email = new SimpleStringProperty(this,"email");
    private StringProperty szekek = new SimpleStringProperty(this,"szekek");

    public int getJegy_id() {
        return jegy_id.get();
    }

    public IntegerProperty jegy_idProperty() {
        return jegy_id;
    }

    public void setJegy_id(int jegy_id) {
        this.jegy_id.set(jegy_id);
    }

    public int getJegyar() {
        return jegyar.get();
    }

    public IntegerProperty jegyarProperty() {
        return jegyar;
    }

    public void setJegyar(int jegyar) {
        this.jegyar.set(jegyar);
    }


    public int getVetites_id() {
        return vetites_id.get();
    }

    public IntegerProperty vetites_idProperty() {
        return vetites_id;
    }

    public void setVetites_id(int vetites_id) {
        this.vetites_id.set(vetites_id);
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

    public String getSzekek() {
        return szekek.get();
    }

    public StringProperty szekekProperty() {
        return szekek;
    }

    public void setSzekek(String szekek) {
        this.szekek.set(szekek);
    }
}
