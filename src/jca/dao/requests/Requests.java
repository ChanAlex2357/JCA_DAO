package jca.dao.requests;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jca.dao.exception.NotEntityException;

public class Requests {
    static public List<Object> findAll(Object entityObject,int offset,Connection conn) throws NotEntityException, SQLException {
        return Getting.findAll(entityObject, offset, conn);
    }
}