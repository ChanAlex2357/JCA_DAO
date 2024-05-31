package jca.dao.models.annotations.extractor;

import java.lang.reflect.Field;

import jca.dao.models.annotations.Attribute;

public class AttributeExtractor {
    static public Attribute getAttibute(Field field){
        return field.getAnnotation(Attribute.class);
    } 
}
