package jca.dao.models.annotations.checker;

import java.lang.reflect.Field;

import jca.dao.models.annotations.Attribute;
import jca.dao.models.annotations.AutoIncrement;
import jca.dao.models.annotations.EntityModels;
import jca.dao.models.annotations.ForeignKey;
import jca.dao.models.annotations.PrimaryKey;

public class AttributeChecker {
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
