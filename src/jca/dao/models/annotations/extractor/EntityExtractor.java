package jca.dao.models.annotations.extractor;

import jca.dao.models.annotations.EntityModels;

public class EntityExtractor {
    static public EntityModels getEntityModels(Object source){
        return getEntityModels(source.getClass());
    }
    static public EntityModels getEntityModels(Class<?> source){
        return source.getAnnotation(EntityModels.class);
    }
}
