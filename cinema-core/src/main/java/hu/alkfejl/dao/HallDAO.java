package hu.alkfejl.dao;

import hu.alkfejl.model.Hall;

import java.util.List;

public interface HallDAO {

    List<Hall> allHall();

    void delete(Hall h);
    void udpate(Hall h, String lastname);
    void insert(Hall h);

}
