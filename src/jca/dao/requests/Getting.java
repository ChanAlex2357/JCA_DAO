package jca.dao.requests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import jca.dao.models.annotations.AnnotationChecker;
import jca.dao.models.exception.NotEntityException;

class Getting {
    /**
     * Recuperer une liste de donnee dans la base de donnee conrrespondant a l'objet entite source donnee
     * On peut specifier si la recuperation des donnees se fait avec des criteres ou non
     * @param refObject L'objet source
     * @param criteria Specifier si on veut prendre en compte des criteres specifies
     * @return La liste des donnees
     * @throws SQLException si il y a eu un probleme au niveau de la connexion au database
     * @throws NotEntityException si le refObject n'est pas un entite 
     */
    static public List<Object> find(Object refObject , boolean criteria , String[] attribusNames,int offset,int nbPagination, Connection conn , String database) throws NotEntityException, SQLException{
        List<Object> results = null;
        Class<?> ref_class = refObject.getClass();
        if ( AnnotationChecker.isEtityModels(ref_class)) {
            throw new NotEntityException(refObject);
        }
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = QuerryBuilder.getSelectQuerry(refObject,criteria,attribusNames,offset,nbPagination,database);
        try {
            preparedStatement = StatementBuilder.getSelectStatement(conn,refObject, sql, criteria);
            resultSet = preparedStatement.executeQuery();
            results = ResultParser.getResultList(resultSet, refObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            resultSet.close();
            preparedStatement.close();
        }
        return results;
    }
}
