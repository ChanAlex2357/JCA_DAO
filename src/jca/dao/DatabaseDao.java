package jca.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jca.dao.conn.ConnBuilder;
import jca.dao.conn.ConnConfig;
import jca.dao.conn.PostgresqlConnConfing;
import jca.dao.conn.exception.DriverException;
import jca.dao.requests.Requests;

public class DatabaseDao {
    final static public String POSTGRES_DATABASE = PostgresqlConnConfing.DATABASE;
    private ConnConfig connConfig;
    public ConnConfig getConnConfig() {
        return connConfig;
    }
    public void setConnConfig(ConnConfig connConfig) {
        this.connConfig = connConfig;
    }
    public DatabaseDao( ConnConfig connConfig ){
       setConnConfig(connConfig);
    }
    public Connection getDatabaseConnection() throws DriverException, SQLException{
        return ConnBuilder.connect(getConnConfig());
    }
    public List<Object> findAll(Object entityObject , boolean criteria , int offset , int nbPagination) throws Exception{
        getConnConfig().setNbPagination(nbPagination);
        Connection dbConn = getDatabaseConnection();
        List<Object> result = null;
        try {
            result = Requests.findAll(entityObject, criteria , offset , connConfig.getNbPagination(), dbConn , connConfig.getDatabase());
        }
        catch (Exception err) {
            err.printStackTrace();
        }
        return result;
    }
}
