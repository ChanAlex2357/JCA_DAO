package jca.dao.requests.querry.builder;


public class QuerryBuilder {
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
    static public String getSelectQuerry(Object source, boolean criteria, String[] attribusNames,int offset,int nbPagination,String database){
        return SelectBuilder.getSelectQuerry(source, criteria, attribusNames, offset, nbPagination, database);
    }
     /**
     * Genere la requete sql d'insertion d'un objet donner 
     * @param obj
     * @param tabName
     * @return
     */
    static public String getInsertQuerry(Object obj){
        return InsertBuilder.getInsertQuerry(obj);
    }

}
