package jca.dao.models.annotations.extractor;

import java.lang.reflect.Field;

import jca.dao.models.annotations.Attribute;
import jca.dao.models.annotations.EntityModels;

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
