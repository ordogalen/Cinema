package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Movie {
    private StringProperty  film_nev = new SimpleStringProperty(this,"film_nev");
    private StringProperty hossz = new SimpleStringProperty(this,"hossz");
    private StringProperty korhatar = new SimpleStringProperty(this,"korhatar");
    private StringProperty  rendezo = new SimpleStringProperty(this,"rendezo");
    private StringProperty  szereplok = new SimpleStringProperty(this,"szereplok");
    private StringProperty  leiras = new SimpleStringProperty(this,"leiras");
    private StringProperty  boritokep = new SimpleStringProperty(this,"boritokep");

    public String getFilm_nev() {
        return film_nev.get();
    }

    public StringProperty film_nevProperty() {
        return film_nev;
    }

    public void setFilm_nev(String film_nev) {
        this.film_nev.set(film_nev);
    }

    public String getHossz() {
        return hossz.get();
    }

    public StringProperty hosszProperty() {
        return hossz;
    }

    public void setHossz(String hossz) {
        this.hossz.set(hossz);
    }

    public String getKorhatar() {
        return korhatar.get();
    }

    public StringProperty korhatarProperty() {
        return korhatar;
    }

    public void setKorhatar(String korhatar) {
        this.korhatar.set(korhatar);
    }

    public String getRendezo() {
        return rendezo.get();
    }

    public StringProperty rendezoProperty() {
        return rendezo;
    }

    public void setRendezo(String rendezo) {
        this.rendezo.set(rendezo);
    }

    public String getSzereplok() {
        return szereplok.get();
    }

    public StringProperty szereplokProperty() {
        return szereplok;
    }

    public void setSzereplok(String szereplok) {
        this.szereplok.set(szereplok);
    }

    public String getLeiras() {
        return leiras.get();
    }

    public StringProperty leirasProperty() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras.set(leiras);
    }

    public String getBoritokep() {
        return boritokep.get();
    }

    public StringProperty boritokepProperty() {
        return boritokep;
    }

    public void setBoritokep(String boritokep) {
        this.boritokep.set(boritokep);
    }
}
