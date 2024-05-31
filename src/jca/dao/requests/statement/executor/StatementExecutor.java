package jca.dao.requests.statement.executor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatementExecutor {
    /**
     * Executer une requete sql donnee 
     * @exemple insertion,select,
     * @param sql
     * @throws SQLException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    static public ResultSet executePreparedStatement(PreparedStatement preparedStatement) throws SQLException{
        ResultSet result = null;
        try {
           result = preparedStatement.executeQuery();
        } catch (Exception e) {
            throw e;
        }
        return result;
    }
    static public void updatePreparedStatement(PreparedStatement preparedStatement) throws SQLException{
        try {
            int result = preparedStatement.executeUpdate();
            System.out.println(result+" ligne de resultats");
        } catch (Exception e) {
            throw e;
        }
    }
}
