package jca.dao.requests;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jca.dao.models.reflections.AttributeExtractor;

public class StatementBuilder {
    static public PreparedStatement getSelectStatement(Connection con , Object entite , String sqlInsert , boolean criteria) throws SQLException, IllegalArgumentException, IllegalAccessException{
        PreparedStatement preparedStatement = con.prepareStatement(sqlInsert);
        if (!criteria) {
            return preparedStatement;
        }
        /// Les attributs d'entite a inserer 
        Field[] attributs = AttributeExtractor.getEntiteAttributs(entite.getClass());
        /// Insertion des valeurs a inserer
        int index = 1;
        for (Field field : attributs) {
            Object value =  field.get(entite);
            if (value == null) {
                continue;
            }
            preparedStatement.setObject(index,value);
            index += 1; 
        }
        return preparedStatement;
    }
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
