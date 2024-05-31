package jca.dao.models.annotations.extractor;

import java.lang.reflect.Field;

import jca.dao.helpers.StringParser;

import jca.dao.models.annotations.EntityModels;

public class EntityExtractor {
    static public EntityModels getEntityModels(Object source){
        return getEntityModels(source.getClass());
    }
    static public EntityModels getEntityModels(Class<?> source){
        return source.getAnnotation(EntityModels.class);
    }
    static public String getEntityName(Object entity){
        return getEntityName(entity.getClass());
    }
    static public String getEntityName(Class<?> entity){
        EntityModels entModel = getEntityModels(entity);
        String name = entModel.name(); 
        if (name == "") {
            name = StringParser.parse(entity.getClass().getSimpleName(),entModel.parseOption());
        }
        return name;
    }
}
