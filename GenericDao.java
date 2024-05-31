package dao;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import annotation.EntiteAttribut;
import annotation.EntiteClass;

public class GenericDao {
    /**
     * La configuration de base de donnee a utiliser
     */
    private UtilDb utilDb;

    /**
     * La classe d'annotation de class d'une entite
     */
    private static final Class<EntiteClass> ENTITE_CLASS = EntiteClass.class;

    /**
     * La class d'annotation d'attribut d'une entite
     */
    private static final Class<EntiteAttribut> ATTRIBUT_CLASS = EntiteAttribut.class;

    static Class<?>[] nullClasses = null;
    static Object[] nullObjects = null;

    String database;

    int nbPagination;


    /**
     * Recuperer la configuration de l'acces au donner configurer
     * @return configuration de la base de donnee
     */
    public UtilDb getUtilDb() {
        return utilDb;
    }


    /**
     * Specifier l'acces au base de donnee a utiliser
     * @param utilDb configuration de la base de donnee
     */
    public void setUtilDb(UtilDb utilDb) {
        this.utilDb = utilDb;
    }


    /**
     * Recuperer la class d'annotation d'attribut d'entite utilise
     * @return Annotation d'attribut 
     */
    public static Class<EntiteAttribut> GetAttributClass() {
        return ATTRIBUT_CLASS;
    }


    /**
     * Recuperer la class d'annotation de class d'entite utilise
     * @return Annotaion de class
     */
    public static Class<EntiteClass> GetEntiteClass() {
        return ENTITE_CLASS;
    }



    /**
     * Tester si l'objet de la class donne est une entite , 
     * elle teste si la classe possede l'annotation EntiteClass
     * @param object_class class de l'objet source
     * @return true si c'est une entite sinon false
     */
    private boolean is_entite(Class<?> object_class){
        return object_class.isAnnotationPresent( GenericDao.ENTITE_CLASS );
    }


    /**
     * Tester si l'attribut donner est un attribut d'entite ,
     * teste si elle possede l'annotation EntiteAttribut
     * @param field L'attribut d'une class
     * @return true si c'est un attribut d'entite sinon false
     */
    private boolean is_attribut(Field field){
        return field.isAnnotationPresent( GenericDao.ATTRIBUT_CLASS);
    }
/// ANNOTATIONS
    public EntiteClass getEntiteClassAnnotaiton(Class<?> source){
        return source.getAnnotation(GenericDao.GetEntiteClass());
    }
    public EntiteClass getEntiteClassAnnotaiton(Object source){
        return getEntiteClassAnnotaiton(source.getClass());
    }
    public EntiteAttribut getEntiteAttributAnnotation(Field attribut){
        return attribut.getAnnotation(GenericDao.GetAttributClass());
    }
/// STATEMENTS
    /**
     * Executer une requete sql donnee 
     * @exemple insertion,select,
     * @param sql
     * @throws SQLException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    private ResultSet executePreparedStatement(PreparedStatement preparedStatement) throws SQLException{
        ResultSet result = null;
        try {
           result = preparedStatement.executeQuery();
        } catch (Exception e) {
            throw e;
        }
        return result;
    }
    private void updatePreparedStatement(PreparedStatement preparedStatement) throws SQLException{
        try {
            int result = preparedStatement.executeUpdate();
            System.out.println(result+" ligne de resultats");
        } catch (Exception e) {
            throw e;
        }
    }


/// INSERTION
    /**
     * Inserer un objet entite dans la base de donner en prenant compte des attributs de son annotation
     * @param entite L'objet source
     * @throws Exception 
     */
    public void insertEntite(Object entite) throws Exception{
        Connection conn = getUtilDb().getConn(false);
        try {
            insertEntite(entite, conn);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        }
        finally{
            conn.close();
        }
    }
    public void insertEntite(Object entite,Connection conn) throws Exception{
        Class<?> object_class = entite.getClass();
        /// Tester si il possede l'annotation entite
        if ( !is_entite(object_class)) {
            throw new NotEntiteException(entite,"GenericDao.insertEntite");
        }
        /// Generer la requete sql pour l'entite
        String sql = getInsertQuerry( object_class );
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getInsertStatement( conn, entite, sql);
            updatePreparedStatement(preparedStatement);
        }
        finally{
            preparedStatement.close();
        }
    }
    public PreparedStatement getInsertStatement(Connection con , Object entite , String sqlInsert) throws SQLException, IllegalArgumentException, IllegalAccessException{
        PreparedStatement preparedStatement = con.prepareStatement(sqlInsert);
        /// Les attributs d'entite a inserer 
        Field[] attributs = getEntiteAttributs(entite.getClass());
        /// Insertion des valeurs a inserer
        int index = 1;
        for (Field field : attributs) {
            if (getEntiteAttributAnnotation(field).primaryKey()) {
                continue;
            }
            preparedStatement.setObject(index, field.get(entite));
            index += 1; 
        }
        return preparedStatement;
    }
    /**
     * Genere la requete sql d'insertion d'un objet donner 
     * @param obj
     * @param tabName
     * @return
     */
    private String getInsertQuerry(Class<?> obj){
        EntiteClass entite_annotation = obj.getAnnotation(GenericDao.ENTITE_CLASS);
        String tabName = entite_annotation.name();
        /// Les Attribut qui representent les collones de l'entite
        Field[] attributs = getEntiteAttributs(obj);
        String sql_querry = "Insert into "+tabName;
        /// Generer la partie values (colonnes)
        sql_querry += getInsertCollumns(attributs);
        /// Generer les valeurs a inserer
        sql_querry += getInsertValues(attributs);
        return sql_querry;
    }
    /**
     * Genere la partie ' values(col1,col2,...)' de la requete d'insertion de donne e
     * @param obj Objet de reference pour la generation de la requete 
     * @return Le String a ajouter a l'ensemble des requete generer auparavant
     */
    private String getInsertCollumns(Field[] attributs){
        String result = " ";
        if (attributs.length > 0) {
            result+="(";
            /// Traitement de chaque attribut 
            for (int i = 0; i < attributs.length; i++) {
                EntiteAttribut attr = attributs[i].getAnnotation(ATTRIBUT_CLASS);
                /// Ajouter le nom de la colonne dans le resultat
                result+= attr.name();
                /// Prend un prefixe ',' tant que c'est pas le dernier
                if (i+1 < attributs.length) {
                    result+=',';
                }
            }
            result+=")";
        }
        return result;
    }
    /**
     * 
     * @param attributs
     * @return
     */
    private String getInsertValues(Field[] attributs){
        String result = " values ";
        if (attributs.length > 0) {
            result+="(";
            /// Traitement de chaque attribut 
            for (int i = 0; i < attributs.length; i++) {
                /// Ajouter le nom de la colonne dans le resultat
                result+= getPreparedStatementInput(attributs[i]);
                /// Prend un prefixe ',' tant que c'est pas le dernier
                if (i+1 < attributs.length) {
                    result+=',';
                }
            }
            result+=")";
        }
        return result;
    }


    /**
     * Recupere la formulation en string d'insertion en prepared statement de l'attribut 
     * @param attribut
     * @param entiteAttribut
     * @return
     */
    private String getPreparedStatementInput(Field attribut){
        String result = "?";
        EntiteAttribut entiteAttribut = attribut.getAnnotation(ATTRIBUT_CLASS);
        /// Si elle en auto increment et un primary key on fait DEFAULT
        if ( entiteAttribut.autoIncrement() && entiteAttribut.primaryKey()) {
            result = "DEFAULT";
        }
        return result;
    }


    /**
     * Recuperer la liste des attributs d'une objet qui sont
     * annoter <EntiteAttribut>
     * @param obj La classe de l'objet source
     * @return La liste a trributs annoter
     */
    public Field[] getEntiteAttributs(Class<?> obj){
        List<Field> fieldList = new ArrayList<>();
        Field[] attributs = obj.getDeclaredFields();
        for (Field field : attributs) {
            if ( is_attribut(field) ) {
               fieldList.add(field);
            }
        }
        return fieldList.toArray( new Field[0] );
    }


    /**
     * Recuperer la liste des attributs d'une objet qui sont
     * annoter <EntiteAttribut>
     * @param obj La classe de l'objet source
     * @return La liste a trributs annoter
     */
    public Field[] getEntiteAttributs(Object obj){
        return getEntiteAttributs(obj.getClass());
    }

/// FIND
    /**
     * Permet de recuperer une liste d'objet depuis la base de donner selon l'objet source 
     * configurer en tant qu'Entite
     * @param refObject Objet entite source
     * @return La liste des donnees
     * @throws Exception 
     */
    public List<Object> findAll(Object refObject, int offset)throws Exception{
        Connection conn = getUtilDb().getConn(false);
        List<Object> results = new ArrayList<>();
        try {
            results= findAll(refObject, offset,conn);
            conn.commit();
        }
        catch (Exception err) {
            conn.rollback();
        }
        finally{
            conn.close();
        }
        return results;
    }
    public List<Object> findAll(Object refObject , int offset  ,Connection conn)throws Exception{
        return find(refObject, false ,null,null ,offset,conn);
    }


    /**
     * Recuperer liste d'objet provenant de la base donnee selon des critere specifier non null
     * dans l'objet source 
     * @param refObject L'objet source
     * @return La liste des donnees verifiants les criteres donnees
     * @throws Exception
     */
    public List<Object> findByCriteria(Object refObject,int offset) throws Exception{
        Connection conn = getUtilDb().getConn(false);
        List<Object> results = null;
        try {
            results = findByCriteria(refObject, offset,conn);
            conn.commit();
        }
        catch (Exception err) {
            conn.rollback();
        }
        finally{
            conn.close();
        }
        return results;
    }
    public List<Object> findByCriteria(Object refObject,int offset,Connection conn) throws Exception{
        return find(refObject, true,null,null ,offset,conn);
    }


    /**
     * Recuperer une liste de donnee dans la base de donnee conrrespondant a l'objet entite source donnee
     * On peut specifier si la recuperation des donnees se fait avec des criteres ou non
     * @param refObject L'objet source
     * @param criteria Specifier si on veut prendre en compte des criteres specifies
     * @return La liste des donnees
     * @throws Exception
     */
    public List<Object> find(Object refObject , boolean criteria , int offset) throws Exception{
        Connection conn = getUtilDb().getConn(false);
        List<Object> results = null;
        try {
            results = find(refObject, criteria,null,null, offset,conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            conn.close();
        }
        return results;
    }
    public List<Object> find(Object refObject , boolean criteria , String[] attribusNames, Object[][] limits ,int offset, Connection conn) throws Exception{
        if (conn == null) {
            return find(refObject, criteria , offset);
        }
        List<Object> results = null;
        Class<?> ref_class = refObject.getClass();

        if ( !is_entite(ref_class)) {
            throw new NotEntiteException(refObject,"find all");
        }

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = getSelectQuerry(refObject,criteria,attribusNames,limits);
        sql += getPagination(offset);

        try {
            preparedStatement = getSelectStatement(conn,refObject, sql, criteria);
            resultSet = executePreparedStatement(preparedStatement);
            results = getResultList(resultSet, refObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            resultSet.close();
            preparedStatement.close();
        }
        return results;
    }

    public List<Object> findInIntervale( Object refObject , String[] attributsNames , Object[][] intervales , int offset) throws Exception
    {
        List<Object> results = null;
        Connection conn = getUtilDb().getConn(false);
        try {
            results = find(refObject, false, attributsNames, intervales, offset, conn);
            conn.commit();
        }
        catch (Exception err) {
            conn.rollback();
        }
        finally {
            conn.close();
        }
        return results;
    }
    public List<Object> findInIntervale( Object refObject , String[] attributsNames , Object[][] intervales , int offset , Connection conn) throws Exception
    {
        return find(refObject, false, attributsNames, intervales, offset, conn);
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
    public List<Object> getResultList(ResultSet resultSet , Object refObject) throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
        List<Object> results = new ArrayList<>();
        while (resultSet.next()) {
            results.add(getResultOf(resultSet, refObject));
        }
        return results;
    }


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
    public Object getResultOf(ResultSet resultSet , Object ref ) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException{
        Object result = null;
        Class<?> refClass =  ref.getClass();
        result = refClass.getDeclaredConstructor(nullClasses).newInstance(nullObjects);
        Field[] attributs = getEntiteAttributs(refClass);
        for (Field field : attributs) {
            EntiteAttribut entiteAttribut = getEntiteAttributAnnotation(field);
            Object attribut = resultSet.getObject( entiteAttribut.name() );
            field.set(result, attribut);
        }
        return result;
    }


    /**
     * Genrer la requete sql pour faire de la selection , dans la table
     * specifier par l'annotation d'entite de l'objet source.
     * On peut egalement specifier si l'objet source contient des valeurs de condition
     * a prendre en compte lors de la generation de la requete sql
     * @param source Objet source de generation
     * @param criteria Specifier si il faut generer des conditions ou non
     * @param limits 
     * @param attribusNames 
     * @return La requete sql de selection dans la base de donnee
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public String getSelectQuerry(Object source, boolean criteria, String[] attribusNames, Object[][] limits) throws IllegalArgumentException, IllegalAccessException{
        EntiteClass entiteClass = getEntiteClassAnnotaiton(source);
        String tabName = entiteClass.name();
        String sql = "select * from "+tabName;
        if (criteria) {
            sql += getSelectCondition(source);
        }
        if ((attribusNames!=null) && (limits != null)) {
            sql += "";
        }
        return sql;
    }
    


    public String getSelectCondition( Object source ) throws IllegalArgumentException, IllegalAccessException{
        String condition = " Where ";
        Field[] attributs = getEntiteAttributs(source);

        int  index = 0;
        for (Field field : attributs) {
            if (field.get(source) == null) {
                continue;
            }
            if (index > 0) {
                condition += " AND ";
            }
            condition += getAttributCondition(field);
            index += 1;
        }
        return condition;
    }


    public String getAttributCondition(Field field){
        String condition ="";
        EntiteAttribut entiteAttribut = getEntiteAttributAnnotation(field);

        condition+="(";
        condition+=entiteAttribut.name()+"=?";
        condition += ")";

        return condition;
    }


    public PreparedStatement getSelectStatement(Connection con , Object entite , String sqlInsert , boolean criteria) throws SQLException, IllegalArgumentException, IllegalAccessException{
        PreparedStatement preparedStatement = con.prepareStatement(sqlInsert);
        if (!criteria) {
            return preparedStatement;
        }
        /// Les attributs d'entite a inserer 
        Field[] attributs = getEntiteAttributs(entite.getClass());
        /// Insertion des valeurs a inserer
        int index = 1;
        for (Field field : attributs) {
            Object value =  field.get(entite);
            if (value == null) {
                continue;
            }
            preparedStatement.setObject(index,value);
            index += 1; 
        }
        return preparedStatement;
    }


    public static Class<EntiteClass> getEntiteClass() {
        return ENTITE_CLASS;
    }


    public static Class<EntiteAttribut> getAttributClass() {
        return ATTRIBUT_CLASS;
    }


    public static Class<?>[] getNullClasses() {
        return nullClasses;
    }


    public static void setNullClasses(Class<?>[] nullClasses) {
        GenericDao.nullClasses = nullClasses;
    }


    public static Object[] getNullObjects() {
        return nullObjects;
    }


    public static void setNullObjects(Object[] nullObjects) {
        GenericDao.nullObjects = nullObjects;
    }


    public String getDatabase() {
        return database;
    }


    public void setDatabase(String database) {
        this.database = database;
    }


    public int getNbPagination() {
        return nbPagination;
    }


    public void setNbPagination(int nbPagination) {
        this.nbPagination = nbPagination;
    }

    public String getPagination(int offset){
        return getPagination(getDatabase(), offset);
    }
    public String getPagination(String database,int offset){
        String limit = "";
        if (offset < 0) {
            return limit;
        }
        /// Pagination pour postgresql
        if (database.equals("postgresql")) {
            limit = " LIMIT "+getNbPagination()+" OFFSET "+offset;
        }
        return limit;
    }

}