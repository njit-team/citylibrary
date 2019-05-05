package edu.njit.citylibrary.citylibrary.repository;

import edu.njit.citylibrary.citylibrary.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Admin adminLogin(String username, String password) {
        List<Admin> admins = jdbcTemplate.query(
                "SELECT * FROM AdminLogin WHERE AdminID = ? AND LoginPassword = ?", new Object[]{username, password},
                (rs, rowNum) -> {
                    Admin admin = new Admin();
                    admin.setUsername(rs.getString("AdminID"));
                    admin.setPassword(rs.getString("LoginPassword"));
                    return admin;
                }
        );
        if (admins.size() > 0) {
            return admins.get(0);
        } else {
            return null;
        }
    }
}
