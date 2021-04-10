package hu.alkfejl.dao;

import hu.alkfejl.model.Hall;
import hu.alkfejl.model.Movie;
import hu.alkfejl.model.Screening;

import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ScreeningDAO {
    Map<InputStream, String> ImageNameMap();
    List<String> HallNames();
    List<String> ScreeningIDs();
    List<Screening> movieScreening(String movieName);
    Screening specificScreening(int id);

    Screening getScreeingFromID(int value);
    void delete(Screening s);
    void insert(Screening s);
}
