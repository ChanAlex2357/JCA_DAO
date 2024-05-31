package jca.dao.models.reflections;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jca.dao.models.annotations.checker.AttributeChecker;

public class AttributeExtractor {
    /**
     * Recuperer la liste des attributs d'une objet qui sont
     * annoter <EntiteAttribut>
     * @param obj La classe de l'objet source
     * @return La liste a trributs annoter
     */
    static public Field[] getEntiteAttributs(Class<?> obj){
        List<Field> fieldList = new ArrayList<>();
        Field[] attributs = obj.getDeclaredFields();
        for (Field field : attributs) {
            if ( AttributeChecker.isAttribute(field) ) {
               fieldList.add(field);
            }
        }
        return fieldList.toArray( new Field[0] );
    }
    /**
     * Recuperer la liste des attributs d'une objet qui sont
     * annoter <EntiteAttribut>
     * @param obj La classe de l'objet source
     * @return La liste a trributs annoter
     */
    static public Field[] getEntiteAttributs(Object obj){
        return getEntiteAttributs(obj.getClass());
    }
   
}
