package jca.dao.models.annotations;

import java.lang.reflect.Field;

public class AnnotationExtractor {
    static public EntityModels getEntityModels(Object source){
        return getEntityModels(source.getClass());
    }
    static public EntityModels getEntityModels(Class<?> source){
        return source.getAnnotation(EntityModels.class);
    }
    static public Attribute getAttibute(Field field){
        return field.getAnnotation(Attribute.class);
    } 
}
