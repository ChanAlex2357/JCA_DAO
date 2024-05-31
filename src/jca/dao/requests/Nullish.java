package jca.dao.requests;

import java.lang.reflect.Constructor;

public class Nullish {
    static public final Class<?>[] classes = null;
    static public final Object[] parameters = null;
    static public Constructor<?> getDefaultConstructor(Object source) throws Exception{
        return getDefaultConstructor(source.getClass());
    }
    static public Constructor<?> getDefaultConstructor(Class<?> source) throws Exception{
        try {
            return source.getDeclaredConstructor(Nullish.classes);
        } catch (NoSuchMethodException e) {
            throw new Exception("La classe "+source.getName()+" ne possede pas de constructeur vide par defaut , veuillez surcharger le constructeur vide",e);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }
}
