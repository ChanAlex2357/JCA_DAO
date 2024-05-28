package jca.dao.requests;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jca.dao.models.annotations.AnnotationExtractor;
import jca.dao.models.annotations.Attribute;
import jca.dao.models.reflections.AttributeExtractor;

public class ResultParser {
    /**
     * Convertir une ligne de resultat dans resultset a  partir d'un objet de reference
     * @param resultSet contenant la ligne de donnee
     * @param ref L'objet entite de reference pour connaitre les attribut a recuperer
     * @return L'objet correspondant a une ligne de donnee
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws SQLException
     */
    static public Object getResultOf(ResultSet resultSet , Object ref ) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException{
        Object result = null;
        Class<?> refClass =  ref.getClass();
        result = refClass.getDeclaredConstructor(Nullish.classes).newInstance(Nullish.parameters);
        Field[] attributs = AttributeExtractor.getEntiteAttributs(refClass);
        for (Field field : attributs) {
            Attribute entiteAttribut = AnnotationExtractor.getAttibute(field);
            Object attribut = resultSet.getObject( entiteAttribut.name() );
            field.set(result, attribut);
        }
        return result;
    }
        /**
     * Recuperer la des objet resultat a partir d'une resultset
     * @param resultSet La resultset contenant la liste des resultat
     * @param refObject L'objet de reference pour convertir les resultats
     * @return Liste des objets converti a partir du resultset
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    static public List<Object> getResultList(ResultSet resultSet , Object refObject) throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
        List<Object> results = new ArrayList<>();
        while (resultSet.next()) {
            results.add(getResultOf(resultSet, refObject));
        }
        return results;
    }
}
