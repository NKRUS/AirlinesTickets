package ru.nk.tickets.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.nk.tickets.model.Document;
import ru.nk.tickets.util.DBUtil;

import javax.print.Doc;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by NK on 23.11.2016.
 */
public class DocumentDAO {
    public static ObservableList<Document> getDocuments() throws SQLException, ClassNotFoundException{
        String selectStmt = "SELECT * FROM documents";

        try {
            ResultSet rsCts = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Document> documentList = getDocumentList(rsCts);
            return documentList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static ObservableList<Document> getDocumentList(ResultSet rs) throws SQLException {
        ObservableList<Document> documentList = FXCollections.observableArrayList();

        while (rs.next()){
            Document document = new Document();
            document.setDocument_id(rs.getInt("id"));
            document.setDocument_type(rs.getString("document_type"));
            documentList.add(document);
        }
        return documentList;
    }
}
