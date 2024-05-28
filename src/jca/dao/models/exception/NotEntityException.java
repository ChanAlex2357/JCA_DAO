package jca.dao.models.exception;

import jca.dao.models.annotations.EntityModels;

public class NotEntityException extends Exception{
    public NotEntityException(Object object){
        super(error_message(object));
    }

    public NotEntityException(Class<?> clazz){
        super(error_message(clazz));
    }

    static private String error_message(Class<?> clazz){
        return "L'objet de class "+clazz.getName()+" n'est pas un entite car il ne possede pas l'annotation "+EntityModels.class.getName();
    } 
    static private String error_message(Object object){
        return NotEntityException.error_message(object.getClass());
    } 
}
