package jca.dao.models.annotations;

import java.lang.reflect.Field;

public class AnnotationChecker {
    static public boolean isEtityModels(Object obj){
        return AnnotationChecker.isEtityModels(obj.getClass());
    }
    static public boolean isEtityModels(Class<?> objClass){
        return objClass.isAnnotationPresent(EntityModels.class);
    }

    static public boolean isAttribute(Field field){
        return field.isAnnotationPresent(Attribute.class);
    }

    static public boolean isPrimaryKey(Field field){
        return field.isAnnotationPresent(PrimaryKey.class);
    }

    static public boolean isForeignKey(Field field){
        return field.isAnnotationPresent(ForeignKey.class);
    }

    static public boolean isAutoIncrement(Field field){
        return field.isAnnotationPresent(AutoIncrement.class);
    }

    static public boolean isPrimaryKeyAutoIncremented(Field field){
        return isPrimaryKey(field) && isAutoIncrement(field) ;
    }
}
