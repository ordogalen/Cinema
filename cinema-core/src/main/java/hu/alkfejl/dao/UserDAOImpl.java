package hu.alkfejl.dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import hu.alkfejl.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    final String ADDUSER = "INSERT INTO user(email, nev, jelszo) VALUES ('%s', '%s', '%s')";

    Connection conn;
    DBConnector connection;

    public UserDAOImpl() {
        connection = new DBConnector();
        conn = connection.connect();
    }

    @Override
    public void addUser(User user) {
        try {
            String newPwd = BCrypt.withDefaults().hashToString(12, user.getJelszo().toCharArray());
            user.setJelszo(newPwd);
            connection.executeQuery(String.format(ADDUSER, user.getEmail(), user.getNev(), user.getJelszo()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User login(String username, String password) {
        try {
            ResultSet rs = connection.selectQuery("Select * from user WHERE nev = '" + username + "'");
            if (rs.next()) {
                String dbPass = rs.getString("jelszo");
                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), dbPass);
                if (result.verified) {
                    User u = new User();
                    u.setEmail(rs.getString("email"));
                    u.setJelszo(rs.getString("jelszo"));
                    u.setNev(rs.getString("nev"));
                    return u;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }
}
