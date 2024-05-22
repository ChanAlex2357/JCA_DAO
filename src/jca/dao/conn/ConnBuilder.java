package jca.dao.conn;
/**
 * La classe Connection etablie la connection avec la base de donnee
 * selon le driver specifier et l'url de connection specifier 
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jca.dao.exception.DriverException;

public class ConnBuilder {
    /**
     * Le driver du sgbd a utiliser
     */
    private String driver;
    public ConnBuilder(String driver){
        setDriver(driver);
    }
/// GET AND SETS
    public String getDriver() {
        return driver;
    }
    public void setDriver(String driver) {
        this.driver = driver;
    }

/// CONNEXION A LA BASE DE DONNEE
    /**
     * Etablie la connection avec la base de donnees selon le driver configurer et l'url de connction a utiliser
     * @param url de connection a utiliser
     * @return la connection etablie
     * @throws DriverException Le driver de la base de donnee est introuvable ou incorrecte
     * @throws SQLException La connection avec la base de donnee avec l'url n'a pas pu se faire
     */
    public Connection connect(String url) throws DriverException, SQLException{
        return ConnBuilder.connect(getDriver(), url);
    }
    /**
     * Etablie la connection avec la base de donnees selon le driver configurer et l'url de connction a utiliser
     * @param url de connection a utiliser
     * @param username
     * @param password
     * @return la connection etablie
     * @throws DriverException Le driver de la base de donnee est introuvable ou incorrecte
     * @throws SQLException La connection avec la base de donnee avec les configurations n'ont pas pues se faire
     */
    public Connection connect(String url,String username,String password) throws DriverException, SQLException{
        return ConnBuilder.connect(getDriver(), url,username,password);
    }
    /**
     * Etablie la connection avec une base de donnee selon le driver a utiliser et les configurations de connection url , username et password
     * @param driver le driver de la base de donnee , voir la librairie de la base de donnee
     * @param url l'url de connection a utiliser
     * @param username le nom de l'utilisateur a utiliser a l'authentification de la connection
     * @param password le mot de passe d'authentification de l'utilisateur
     * @return la connection etablie avec la base de donnee
     * @throws DriverException Le driver de la base de donnee est introuvable ou incorrecte
     * @throws SQLException La connection avec la base de donnee avec l'url n'a pas pu se faire
     */
    static public Connection connect( String driver,String url , String username , String password) throws DriverException, SQLException{
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (ClassNotFoundException e) {
            throw new DriverException(driver, e);
        } catch (SQLException se) {
            throw se;
        }
    }
    /**
     * Etablie la connection avec une base de donnee selon le driver a utiliser et la configurations de connection url 
     * @param driver le driver de la base de donnee , voir la librairie de la base de donnee
     * @param url l'url de connection a utiliser
     * @return la connection etablie avec la base de donnee
     * @throws DriverException Le driver de la base de donnee est introuvable ou incorrecte
     * @throws SQLException La connection avec la base de donnee avec l'url n'a pas pu se faire
     */
    static public Connection connect( String driver,String url)throws DriverException, SQLException{
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url);
            return conn;
        } catch (ClassNotFoundException e) {
            throw new DriverException(driver, e);
        } catch (SQLException se) {
            throw se;
        }
    }
}
