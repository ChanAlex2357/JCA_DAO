package jca.dao.requests.results;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PGInterval;

import jca.dao.models.annotations.Attribute;
import jca.dao.models.annotations.extractor.AttributeExtractor;
import jca.dao.models.field.FieldExtractor;

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
        Field[] attributs = FieldExtractor.getEntiteAttributs(refClass);
        for (Field field : attributs) {
            setAttributValue(result, field, resultSet);
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

    static private void setAttributValue( Object entity ,Field field , ResultSet resultSet) throws IllegalArgumentException, IllegalAccessException, SQLException{
        Attribute attributeAnnotation = AttributeExtractor.getAttibute(field);
        Object value = resultSet.getObject( attributeAnnotation.name());
        if (value != null) {
            field.setAccessible(true);
            if (value instanceof Date date) {
                field.set(entity, date.toLocalDate());
            } else if (value instanceof Time time) {
                field.set(entity, time.toLocalTime());
            } else if (value instanceof PGInterval pgInterval) {
                int hours = pgInterval.getHours();
                int minutes = pgInterval.getMinutes();
                double seconds = pgInterval.getSeconds();
                int nanos = pgInterval.getMicroSeconds();
                LocalTime localTime = LocalTime.of(hours, minutes, (int) seconds, nanos);
                field.set(entity, localTime);
            } else if (value instanceof Long longValue){
                field.set(entity, longValue.intValue());
            } else if (value instanceof BigDecimal bigDecimal) {
                field.set(entity, bigDecimal.doubleValue());
            } else {
                field.set(entity, value);  
            }
            field.setAccessible(false);
        }
    }
}
