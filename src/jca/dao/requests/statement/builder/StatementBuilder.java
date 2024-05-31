package jca.dao.requests.statement.builder;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jca.dao.models.annotations.AnnotationChecker;
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
    static public PreparedStatement getInsertStatement(Connection con , Object entite , String sqlInsert) throws SQLException, IllegalArgumentException, IllegalAccessException{
        PreparedStatement preparedStatement = con.prepareStatement(sqlInsert);
        /// Les attributs d'entite a inserer 
        Field[] attributs = AttributeExtractor.getEntiteAttributs(entite);
        /// Insertion des valeurs a inserer
        int index = 1;
        for (Field field : attributs) {
            if (AnnotationChecker.isPrimaryKey(field)) {
                continue;
            }
            preparedStatement.setObject(index, field.get(entite));
            index += 1; 
        }
        return preparedStatement;
    }
    
}
