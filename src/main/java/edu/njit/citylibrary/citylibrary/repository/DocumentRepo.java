package edu.njit.citylibrary.citylibrary.repository;

import edu.njit.citylibrary.citylibrary.domain.Document;
import edu.njit.citylibrary.citylibrary.domain.TopTenBorrowedDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DocumentRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static Document mapRow(ResultSet rs, int rowNum) throws SQLException {
        Document document = new Document();
        document.setDocId(rs.getInt("DocID"));
        document.setpDate(rs.getDate("PDate"));
        document.setPublisherId(rs.getInt("PublisherID"));
        document.setTitle(rs.getString("Title"));
        document.setPublisherName(rs.getString("PubName"));
        document.setPublisherAddress(rs.getString("PubAddress"));
        return document;
    }

    public List<Document> searchDocumentById(int docId){
        List<Document> documentID = jdbcTemplate.query(
                "select Document.DocID,\n" +
                        "       Document.Title,\n" +
                        "       Document.PDate,\n" +
                        "       Document.PublisherID,\n" +
                        "       Publisher.PubName,\n" +
                        "       Publisher.PubAddress\n" +
                        "from Document,\n" +
                        "     Publisher\n" +
                        "where Document.DocID=? AND Document.PublisherID = Publisher.PublisherID;", new Object[]{docId},
                DocumentRepo::mapRow
        );
        return documentID;
    }

    public List<Document> searchDocumentByTitle(String title){
        List<Document> documentID = jdbcTemplate.query(
                "select Document.DocID,\n" +
                        "       Document.Title,\n" +
                        "       Document.PDate,\n" +
                        "       Document.PublisherID,\n" +
                        "       Publisher.PubName,\n" +
                        "       Publisher.PubAddress\n" +
                        "from Document,\n" +
                        "     Publisher\n" +
                        "where Document.Title=? AND Document.PublisherID = Publisher.PublisherID;", new Object[]{title},
                DocumentRepo::mapRow
        );
        return documentID;
    }

    public List<Document> searchDocumentByPublisherId(int publisherId){
        List<Document> documentID = jdbcTemplate.query(
                "select Document.DocID,\n" +
                        "       Document.Title,\n" +
                        "       Document.PDate,\n" +
                        "       Document.PublisherID,\n" +
                        "       Publisher.PubName,\n" +
                        "       Publisher.PubAddress\n" +
                        "from Document,\n" +
                        "     Publisher\n" +
                        "where Document.PublisherID=? AND Document.PublisherID = Publisher.PublisherID;", new Object[]{publisherId},
                DocumentRepo::mapRow
        );
        return documentID;
    }

    public List<Document> searchDocumentByPubName(String publisherName) {
        List<Document> documentID = jdbcTemplate.query(
                "select Document.DocID,\n" +
                        "       Document.Title,\n" +
                        "       Document.PDate,\n" +
                        "       Document.PublisherID,\n" +
                        "       Publisher.PubName,\n" +
                        "       Publisher.PubAddress\n" +
                        "from Document,\n" +
                        "     Publisher\n" +
                        "where Publisher.PubName=? AND Document.PublisherID = Publisher.PublisherID;", new Object[]{publisherName},
                DocumentRepo::mapRow
        );
        return documentID;
    }

    public List<TopTenBorrowedDocument> getTopTenBorrowedBooks() {
        List<TopTenBorrowedDocument> topTenBorrowedDocumentList = jdbcTemplate.query(
                "SELECT title, count(*) as count FROM Borrows NATURAL JOIN Document GROUP BY DocID ORDER BY COUNT(*) DESC LIMIT 10",
                (rs, rowNum) -> {
                    TopTenBorrowedDocument topTenBorrowedDocument = new TopTenBorrowedDocument();
                    topTenBorrowedDocument.setCount(rs.getInt("count"));
                    topTenBorrowedDocument.setTitle(rs.getString("title"));
                    return topTenBorrowedDocument;
                }
        );
        return topTenBorrowedDocumentList;
    }
}
