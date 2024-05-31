package jca.dao.requests.querry.builder;

import java.lang.reflect.Field;

import jca.dao.models.annotations.AnnotationChecker;
import jca.dao.models.annotations.AnnotationExtractor;
import jca.dao.models.annotations.Attribute;
import jca.dao.models.annotations.EntityModels;
import jca.dao.models.reflections.AttributeExtractor;

class InsertBuilder {
    /**
     * Genere la requete sql d'insertion d'un objet donner 
     * @param obj
     * @param tabName
     * @return
     */
    static public String getInsertQuerry(Object obj){
        return getInsertQuerry(obj.getClass());
    }
    static private String getInsertQuerry(Class<?> obj){
        EntityModels entite_annotation = AnnotationExtractor.getEntityModels(obj);
        String tabName = entite_annotation.name();
        /// Les Attribut qui representent les collones de l'entite
        Field[] attributs = AttributeExtractor.getEntiteAttributs(obj);
        String sql_querry = "Insert into "+tabName;
        /// Generer la partie values (colonnes)
        sql_querry += getInsertCollumns(attributs);
        /// Generer les valeurs a inserer
        sql_querry += getInsertValues(attributs);
        return sql_querry;
    }
    /**
     * Genere la partie ' values(col1,col2,...)' de la requete d'insertion de donne e
     * @param obj Objet de reference pour la generation de la requete 
     * @return Le String a ajouter a l'ensemble des requete generer auparavant
     */
    static private String getInsertCollumns(Field[] attributs){
        String result = " ";
        if (attributs.length > 0) {
            result+="(";
            /// Traitement de chaque attribut 
            for (int i = 0; i < attributs.length; i++) {
                Attribute attr = AnnotationExtractor.getAttibute(attributs[i]);
                /// Ajouter le nom de la colonne dans le resultat
                result+= attr.name();
                /// Prend un prefixe ',' tant que c'est pas le dernier
                if (i+1 < attributs.length) {
                    result+=',';
                }
            }
            result+=")";
        }
        return result;
    }
    /**
     * 
     * @param attributs
     * @return
     */
    static private String getInsertValues(Field[] attributs){
        String result = " values ";
        if (attributs.length > 0) {
            result+="(";
            /// Traitement de chaque attribut 
            for (int i = 0; i < attributs.length; i++) {
                /// Ajouter le nom de la colonne dans le resultat
                result+= getPreparedStatementInput(attributs[i]);
                /// Prend un prefixe ',' tant que c'est pas le dernier
                if (i+1 < attributs.length) {
                    result+=',';
                }
            }
            result+=")";
        }
        return result;
    }
    /** 
     * Recupere la formulation en string d'insertion en prepared statement de l'attribut 
     * @param attribut
     * @param entiteAttribut
     * @return
     */
    static private String getPreparedStatementInput(Field attribut){
        String result = "?";
        /// Si elle en auto increment et un primary key on fait DEFAULT
        if ( AnnotationChecker.isPrimaryKeyAutoIncremented(attribut) ) {
            result = "DEFAULT";
        }
        return result;
    }
}
