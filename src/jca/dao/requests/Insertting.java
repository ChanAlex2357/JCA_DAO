package jca.dao.requests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jca.dao.models.annotations.AnnotationChecker;
import jca.dao.models.exception.NotEntityException;
import jca.dao.requests.querry.builder.QuerryBuilder;
import jca.dao.requests.statement.builder.StatementBuilder;
import jca.dao.requests.statement.executor.StatementExecutor;

public class Insertting {
    /**
     * Inserer un objet entite dans la base de donner en prenant compte des attributs de son annotation
     * @param entite L'objet source
     * @throws SQLException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws Exception 
     */
    static public void insertEntite(Object entite,Connection conn) throws NotEntityException, IllegalArgumentException, IllegalAccessException, SQLException{
        /// Tester si il possede l'annotation entite
        if ( ! AnnotationChecker.isEtityModels(entite)) {
            throw new NotEntityException(entite);
        }
        /// Generer la requete sql pour l'entite
        String sql = QuerryBuilder.getInsertQuerry(entite);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = StatementBuilder.getInsertStatement(conn, entite, sql);
            StatementExecutor.updatePreparedStatement(preparedStatement);
        }
        finally{
            preparedStatement.close();
        }
    }
}
