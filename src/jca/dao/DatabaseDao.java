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
        return getDatabaseConnection(false);
    }
    public Connection getDatabaseConnection( boolean autocommit) throws DriverException, SQLException{
        Connection conn = ConnBuilder.connect(getConnConfig());
        conn.setAutoCommit(autocommit);
        return conn;
    }
    public List<Object> findAll(Object entityObject , boolean criteria,int offset , int nbPagination) throws Exception{
        return find(entityObject, criteria, null, offset, nbPagination);
    }
    public List<Object> findAll(Object entityObject , boolean criteria ) throws Exception{
        return findAll(entityObject, criteria, 0, 0);
    }
    public List<Object> find( Object entityObject , boolean criteria , String[] attributsName , int offset , int nbPagination) throws Exception{
        getConnConfig().setNbPagination(nbPagination);
        Connection dbConn = getDatabaseConnection();
        List<Object> result = null;
        try {
            result = Requests.find(entityObject , criteria,attributsName ,offset , connConfig.getNbPagination(), dbConn , connConfig.getDatabase());
        }
        catch (Exception err) {
            err.printStackTrace();
        }
        finally{
            dbConn.close();
        }
        return result;
    }
    /**
     * Inserer un objet entite dans la base de donner en prenant compte des attributs de son annotation
     * @param entite L'objet source
     * @throws Exception 
     */
    public void insertEntite(Object entite , boolean commit) throws Exception{
        Connection dbConn = getDatabaseConnection(false);
        try {
            Requests.insertEntite(entite, dbConn);
            if (commit) {
                dbConn.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            dbConn.rollback();
        }
        finally{
            dbConn.close();
        }
    }
    public void insertEntite(Object entite) throws Exception{
        insertEntite(entite, true);
    }
}
