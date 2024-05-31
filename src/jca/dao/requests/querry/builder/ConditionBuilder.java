package jca.dao.requests.querry.builder;

import java.lang.reflect.Field;

import jca.dao.models.annotations.AnnotationExtractor;
import jca.dao.models.annotations.Attribute;
import jca.dao.models.reflections.AttributeExtractor;

class ConditionBuilder {
    /**
     * Creation de la condition de selection des donnees a partir 
     * des donnees d'attibute initialiser dans l'objet source 
     * @param source l'objet source du select
     * @return le string correspondant au condition a realiser
     */
    static public String getSelectCondition( Object source ) {
        String condition = " Where ";
        Field[] attributs = AttributeExtractor.getEntiteAttributs(source);
        int  index = 0;
        for (Field field : attributs) {
            try {
                // Si l'attribut ne possede pas de valeur on ne le prend pas en compte
                if (field.get(source) == null) {
                    continue;
                }
            } catch (Exception e) {
                // Exception si le field n'existe pas dans la source
                // Non lever car les fields viennent de l'objet entrer en parametre
            }
            if (index > 0) {
                condition += " AND ";
            }
            condition += getAttributCondition(field);
            index += 1;
        }
        return condition;
    }  
    static public String getAttributCondition(Field field){
        String condition ="";
        Attribute entiteAttribut = AnnotationExtractor.getAttibute(field);
        condition+="(";
        condition+=entiteAttribut.name()+"=?";
        condition += ")";
        return condition;
    }
}
