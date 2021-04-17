package hu.alkfejl.dao;
import hu.alkfejl.model.Hall;
import hu.alkfejl.model.Movie;

import java.util.List;

public interface MovieDAO {

    int delete(Movie m);
    int update(Movie m);
    int insert(Movie m);
    List<Movie> allMovie();
    Movie specificMovie(String movie_name);
    List<Movie> search(String where, String what);
}
