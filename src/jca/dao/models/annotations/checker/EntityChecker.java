package jca.dao.models.annotations.checker;

import jca.dao.models.annotations.EntityModels;

public class EntityChecker {
    static public boolean isEntityModels(Object obj){
        return EntityChecker.isEntityModels(obj.getClass());
    }
    static public boolean isEntityModels(Class<?> objClass){
        return objClass.isAnnotationPresent(EntityModels.class);
    }
}
