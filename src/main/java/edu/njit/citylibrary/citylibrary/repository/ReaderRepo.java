package edu.njit.citylibrary.citylibrary.repository;

import edu.njit.citylibrary.citylibrary.domain.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReaderRepo {
    static Logger logger = LoggerFactory.getLogger(ReaderRepo.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean enterCardNo(int cardnumber){
        List<Reader> readerID = jdbcTemplate.query(
                "SELECT * FROM Reader WHERE ReaderID = ?", new Object[]{cardnumber},
                (rs, rowNum) -> {
                    Reader reader = new Reader();
                    reader.setReaderId(rs.getInt("ReaderID"));
                    return reader;
                }
        );

        if (readerID.size()==1){
            return true;
        }else return false;

    }

}
