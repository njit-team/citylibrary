package edu.njit.citylibrary.citylibrary.repository;

import edu.njit.citylibrary.citylibrary.domain.Copy;
import edu.njit.citylibrary.citylibrary.domain.Reader;
import edu.njit.citylibrary.citylibrary.domain.Reserves;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class ReaderRepo {
    static Logger logger = LoggerFactory.getLogger(ReaderRepo.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    private static Reader mapRow(ResultSet rs, int rowNum) throws SQLException {
        Reader reader = new Reader();
        reader.setReaderId(rs.getInt("ReaderID"));
        reader.setrType(rs.getString("RType"));
        reader.setrName(rs.getString("RName"));
        reader.setrAddress(rs.getString("RAddress"));
        reader.setrPhone(rs.getLong("Phone_Number"));
        reader.setMemStart(rs.getDate("MemStart").toString());
        reader.setFine(rs.getDouble("Fine"));
        reader.setNumResDocs(rs.getInt("NumResDocs"));
        reader.setNumBorDocs(rs.getInt("NumBorDocs"));
        return reader;
    }

    public List<Reader> enterCardNo(int cardnumber){
        List<Reader> readerID = jdbcTemplate.query(
                "SELECT * FROM Reader WHERE ReaderID = ?", new Object[]{cardnumber},
                ReaderRepo::mapRow

        );

        return readerID;
    }

    public List<Reader> getTopTenBorrowers() {
        List<Reader> readers = jdbcTemplate.query(
                "SELECT * from Reader ORDER BY NumBorDocs DESC LIMIT 10",
                ReaderRepo::mapRow
        );
        return readers;
    }

    public Reserves doCheckout(int readerId, int documentId, int copyNo, int libId){

        List<Integer> lastPrimaryKey = jdbcTemplate.query("select max(ResNumber) from Reserves",
                (rs, rowNum) -> {
                    return rs.getInt(1);
                });
        Integer lastResNumber = lastPrimaryKey.get(0);
        int resNum = lastResNumber + 1;
        Date date = new Date();

        jdbcTemplate.update("INSERT INTO Reserves (ResNumber, ReaderID, DocID, CopyNo, LibID, RTimeStamp) VALUES (?, ?, ?, ?, ?, ?)",
                resNum, readerId, documentId, copyNo, libId, new Timestamp(date.getTime()));



        List<Reserves> reservesList = jdbcTemplate.query(
                "SELECT * FROM Reserves WHERE ResNumber = ?", new Object[]{resNum},
                (rs, rowNum) -> {
                    Reserves reserves = new Reserves();
                    reserves.setDocId(rs.getInt("DocID"));
                    reserves.setCopyNo(rs.getInt("CopyNo"));
                    reserves.setLibId(rs.getInt("LibID"));
                    reserves.setResNumber(rs.getInt("ResNumber"));
                    reserves.setReaderId(rs.getInt("ReaderID"));
                    reserves.setTimestamp(rs.getTimestamp("RTimeStamp"));
                    return reserves;
                }
        );

        return reservesList.get(0);
    }

    public List<Copy> findCopy(String docId){
        List<Copy> copyID = jdbcTemplate.query(
                "SELECT * FROM Copy WHERE DocID = ?", new Object[]{docId},
                (rs, rowNum) -> {
                    Copy copy = new Copy();
                    copy.setDocId(rs.getInt("DocID"));
                    copy.setCopyNo(rs.getInt("CopyNo"));
                    copy.setLibId(rs.getInt("LibID"));
                    copy.setPosition(rs.getString("Position"));
                    return copy;
                }
        );
        return copyID;
    }


    public int returnDocument(int readerID, int docID){
        int update = jdbcTemplate.update("DELETE FROM Reserves where ReaderID  = ? AND DocID = ?",
                readerID, docID);
        return update;
    }
}
