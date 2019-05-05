package edu.njit.citylibrary.citylibrary.repository;

import edu.njit.citylibrary.citylibrary.domain.Admin;
import edu.njit.citylibrary.citylibrary.domain.Branch;
import edu.njit.citylibrary.citylibrary.domain.Copy;
import edu.njit.citylibrary.citylibrary.domain.Reader;
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

    public List<Branch> getBranches() {
        List<Branch> branches = jdbcTemplate.query(
                "SELECT * FROM Branch",
                (rs, rowNum) -> {
                    Branch branch = new Branch();
                    branch.setLibID(rs.getInt("LibID"));
                    branch.setlLocation(rs.getString("LLocation"));
                    branch.setlName(rs.getString("LName"));
                    return branch;
                }
        );
        return branches;
    }

    public int addCopy(Copy copy) {
        int update = jdbcTemplate.update("INSERT INTO Copy (DocID, CopyNo, LibID, Position) VALUES (?, ?, ?, ?)",
                copy.getDocId(), copy.getCopyNo(), copy.getLibId(), copy.getPosition());
        return update;
    }

    public int addReader(Reader reader) {
        int update = jdbcTemplate.update("INSERT INTO Reader (RType, RName, RAddress, Phone_Number, MemStart, Fine, NumResDocs, NumBorDocs) " +
                        "VALUES (?, ?, ?, ?, ?, 0, 0, 0);",
                reader.getrType(), reader.getrName(), reader.getrAddress(), reader.getrPhone(), reader.getMemStart());
        return update;
    }
}
