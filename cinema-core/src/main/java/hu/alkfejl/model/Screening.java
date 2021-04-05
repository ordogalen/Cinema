package hu.alkfejl.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Screening {
    private IntegerProperty id = new SimpleIntegerProperty(this,"id");
    private StringProperty film_nev = new SimpleStringProperty(this,"film_nev");
    private StringProperty terem_nev = new SimpleStringProperty(this,"terem_nev");
    private ObjectProperty<LocalDate> datum = new SimpleObjectProperty<>(this,"datum");
    private StringProperty nap = new SimpleStringProperty(this,"nap");
    private IntegerProperty jegyar = new SimpleIntegerProperty(this,"jegyar");

    public int getJegyar() {
        return jegyar.get();
    }

    public IntegerProperty jegyarProperty() {
        return jegyar;
    }

    public void setJegyar(int jegyar) {
        this.jegyar.set(jegyar);
        System.out.println(jegyar);
    }

    public void setNap(String nap) {
        this.nap.set(nap);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFilm_nev() {
        return film_nev.get();
    }

    public StringProperty film_nevProperty() {
        return film_nev;
    }

    public void setFilm_nev(String film_nev) {
        this.film_nev.set(film_nev);
    }

    public String getTerem_nev() {
        return terem_nev.get();
    }

    public StringProperty terem_nevProperty() {
        return terem_nev;
    }

    public void setTerem_nev(String terem_nev) {
        this.terem_nev.set(terem_nev);
    }

    public LocalDate getDatum() {
        return datum.get();
    }

    public ObjectProperty<LocalDate> datumProperty() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum.set(datum);
    }

    public String getNap() {
        return nap.get();
    }

    public StringProperty napProperty() {
        return nap;
    }
}
