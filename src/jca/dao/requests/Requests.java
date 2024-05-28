package jca.dao.requests;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jca.dao.models.exception.NotEntityException;

public class Requests {
    static public List<Object> findAll(Object entityObject,boolean criteria ,int offset, int nbPagination,Connection conn,String database) throws NotEntityException, SQLException {
        return find(entityObject, criteria, null, offset , nbPagination, conn , database);
    }
    static public List<Object> find(Object refObject , boolean criteria , String[] attribusNames,int offset, int nbPagination, Connection conn,String database) throws NotEntityException, SQLException{
        return Getting.find(refObject, criteria, attribusNames, offset,nbPagination, conn , database);
    }
}