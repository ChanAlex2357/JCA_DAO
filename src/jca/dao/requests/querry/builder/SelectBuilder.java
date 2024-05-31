package jca.dao.requests.querry.builder;

import jca.dao.models.annotations.EntityModels;
import jca.dao.models.annotations.extractor.EntityExtractor;

class SelectBuilder {
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
    static String getSelectQuerry(Object source, boolean criteria, String[] attribusNames,int offset,int nbPagination,String database){
        EntityModels entiteClass = EntityExtractor.getEntityModels(source);
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
        String limitString = PaginationBuilder.getPagination(database, offset,nbPagination);
        return getSelectQuerry(collumns, tabName, conditions, limitString);
    }
    static private String getSelectQuerry(String collumns , String tabName , String conditions , String limits){
        return "select "+collumns+" from "+tabName+" "+conditions+" "+limits;
    }
  
}
