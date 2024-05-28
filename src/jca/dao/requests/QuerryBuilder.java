package jca.dao.requests;

import jca.dao.models.annotations.AnnotationExtractor;
import jca.dao.models.annotations.EntityModels;

class QuerryBuilder {
    /**
     * Genrer la requete sql pour faire de la selection , dans la table
     * specifier par l'annotation d'entite de l'objet source.
     * On peut egalement specifier si l'objet source contient des valeurs de condition
     * a prendre en compte lors de la generation de la requete sql
     * @param source Objet source de generation
     * @param criteria Specifier si il faut generer des conditions ou non
     * @param limits 
     * @param attribusNames 
     * @return La requete sql de selection dans la base de donnee
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    static public String getSelectQuerry(Object source, boolean criteria, String[] attribusNames, Object[][] limits){
        EntityModels entiteClass = AnnotationExtractor.getEntityModels(source);
        String tabName = entiteClass.name();
        /// Select des colonnes
        String collumns = "*";
        if (attribusNames != null) {
            // Collumns specifique
        }
        String conditions = "";
        if (criteria) {
            conditions = ConditionBuilder.getSelectCondition(source);
        }
        String limitString = "";
        if ((attribusNames!=null) && (limits != null)) {
            limitString = "";
        }
        return getSelectQuerry(collumns, tabName, conditions, limitString);
    }
    static private String getSelectQuerry(String collumns , String tabName , String conditions , String limits){
        return "select "+collumns+" from"+tabName+conditions+limits;
    }
  
}
