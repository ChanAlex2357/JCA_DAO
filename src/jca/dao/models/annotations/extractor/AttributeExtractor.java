package jca.dao.models.annotations.extractor;

import java.lang.reflect.Field;

import jca.dao.helpers.StringParser;
import jca.dao.models.annotations.Attribute;

public class AttributeExtractor {
    static public Attribute getAttibute(Field field){
        return field.getAnnotation(Attribute.class);
    }
    static public String getAttributeName(Field field){
        Attribute attribute = getAttibute(field);
        String name = attribute.name(); 
        if (name == "") {
            name = StringParser.parse(field.getName(),attribute.parseOption());
        }
        return name;
    }
}
