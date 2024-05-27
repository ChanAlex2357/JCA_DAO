package jca.dao.conn;

public class PostgresqlConnConfing extends ConnConfig{
    final static private    String  host        = "localhost";
    final static private    int     port        = 5432;
    final static private    String  driver      = "org.postgresql.Driver";
    final static private    String  database    = "postgresql";  
    public PostgresqlConnConfing (String dbname ,String username , String password){
        super(  PostgresqlConnConfing.driver , 
                PostgresqlConnConfing.database,
                PostgresqlConnConfing.host, 
                PostgresqlConnConfing.port,
                dbname, username, password
            );
    } 
    public PostgresqlConnConfing (String host,int port,String dbname ,String username , String password){
        super(  PostgresqlConnConfing.driver , 
                PostgresqlConnConfing.database,
                host,port,dbname, username, password
            );
    } 

}
