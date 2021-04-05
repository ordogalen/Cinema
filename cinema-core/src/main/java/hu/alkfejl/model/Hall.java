package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Hall {
    private StringProperty terem_nev = new SimpleStringProperty(this,"terem_nev");
    private IntegerProperty sor = new SimpleIntegerProperty(this,"sor");
    private IntegerProperty oszlop = new SimpleIntegerProperty(this,"oszlop");

    public String getTerem_nev() {
        return terem_nev.get();
    }

    public StringProperty terem_nevProperty() {
        return terem_nev;
    }

    public void setTerem_nev(String terem_nev) {
        this.terem_nev.set(terem_nev);
    }

    public int getSor() {
        return sor.get();
    }

    public IntegerProperty sorProperty() {
        return sor;
    }

    public void setSor(int sor) {
        this.sor.set(sor);
    }

    public int getOszlop() {
        return oszlop.get();
    }

    public IntegerProperty oszlopProperty() {
        return oszlop;
    }

    public void setOszlop(int oszlop) {
        this.oszlop.set(oszlop);
    }
}
