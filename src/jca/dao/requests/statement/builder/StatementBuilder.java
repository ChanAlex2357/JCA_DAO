package jca.dao.requests.statement.builder;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jca.dao.models.annotations.checker.AttributeChecker;
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
            field.setAccessible(true);
            Object value =  field.get(entite);
            if (value != null) {
                preparedStatement.setObject(index,value);
                index += 1;
            }
            field.setAccessible(false);
        }
        return preparedStatement;
    }
    static public PreparedStatement getInsertStatement(Connection con , Object entite , String sqlInsert) throws SQLException, IllegalArgumentException, IllegalAccessException{
        PreparedStatement preparedStatement = con.prepareStatement(sqlInsert);
        /// Les attributs d'entite a inserer 
        Field[] attributs = AttributeExtractor.getEntiteAttributs(entite);
        /// Insertion des valeurs a inserer
        int index = 1;
        for (Field field : attributs) {
            if (AttributeChecker.isPrimaryKeyAutoIncremented(field)) {
                continue;
            }
            field.setAccessible(true);
            Object value = field.get(entite);
            System.out.println("value => "+value+"\n");
            preparedStatement.setObject(index,value);
            field.setAccessible(false);
            index += 1; 
        }
        return preparedStatement;
    }
    
}
