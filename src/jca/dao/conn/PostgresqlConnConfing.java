package jca.dao.conn;

public class PostgresqlConnConfing extends ConnConfig{
    final static public     String   LOCALHOST   = "localhost";
    final static public     int      PORT        = 5432;
    final static public    String    DRIVER      = "org.postgresql.Driver";
    final static public    String    DATABASE    = "postgresql";  

    public PostgresqlConnConfing (String host,int port,String dbname ,String username , String password){
        super(  PostgresqlConnConfing.DRIVER , 
                PostgresqlConnConfing.DATABASE,
                host,port,dbname, username, password
        );
    }
    public PostgresqlConnConfing (String host,String dbname ,String username , String password){
        super(  PostgresqlConnConfing.DRIVER , 
                PostgresqlConnConfing.DATABASE,
                host,
                PostgresqlConnConfing.PORT,
                dbname, username, password
        );
    }
}
