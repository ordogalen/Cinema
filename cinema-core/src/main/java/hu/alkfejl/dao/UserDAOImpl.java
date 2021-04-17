package hu.alkfejl.dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import hu.alkfejl.model.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDAOImpl implements UserDAO {
    final String ADDUSER = "INSERT INTO user(email, nev, jelszo) VALUES (?,?,?)";

    private String dbURL;
    private final Properties properties = new Properties();


    public UserDAOImpl() {
        try {
            properties.load(getClass().getResourceAsStream("/application.properties"));
            dbURL = properties.getProperty("db.url");
            Class.forName("org.sqlite.JDBC");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(User user) {
        try (Connection c = DriverManager.getConnection(dbURL)){
            String newPwd = BCrypt.withDefaults().hashToString(12, user.getJelszo().toCharArray());
            user.setJelszo(newPwd);
            PreparedStatement pstm = c.prepareStatement(ADDUSER);
            pstm.setString(1,user.getEmail());
            pstm.setString(2,user.getNev());
            pstm.setString(3,user.getJelszo());
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User login(String username, String password) {
        try (Connection c = DriverManager.getConnection(dbURL)){
            PreparedStatement pstm = c.prepareStatement("SELECT * FROM user WHERE nev = ?");
            pstm.setString(1,username);
            ResultSet rs = pstm.executeQuery();
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
